package com.example.quanzhouplaywright.Controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.quanzhouplaywright.Properties.spyProperties;
import com.example.quanzhouplaywright.Serivce.PlaywrightSerivce;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitUntilState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/")
@Slf4j
public class BaseController {
    private volatile boolean initialized = false;
    final PlaywrightSerivce playwrightSerivce;
    private final spyProperties spyProperties;
    protected BrowserContext browserContext;
    private final Environment environment;
    private Browser browser;

    public BaseController(PlaywrightSerivce playwrightSerivce, spyProperties spyProperties, Environment environment) {
        this.playwrightSerivce = playwrightSerivce;
        this.spyProperties = spyProperties;
        this.browser = playwrightSerivce.defaultBrowser();
        this.browserContext = playwrightSerivce.createContext();
        this.environment = environment;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!initialized) {
            // 在这里执行初始化操作
            initialized = true;
            this.browserContext.newPage().navigate("http://127.0.0.1:" + environment.getProperty("server.port"));
            //this.browserContext.newPage().navigate("https://jp.mercari.com/");
            this.browserContext.pages().get(0).bringToFront();
        }
    }


    @RequestMapping(value = "/mercari/search", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object spyWeibo(@RequestParam(name = "name", required = false) String name) {
        Page defaultPage = this.browserContext.pages().get(0);
        Page page = this.browserContext.newPage();
        defaultPage.bringToFront();
        JSONArray items = null;
        try {
            changeHTML(defaultPage, "state", "导航至煤炉");
            page.navigate("https://jp.mercari.com/", new Page.NavigateOptions().setWaitUntil(WaitUntilState.NETWORKIDLE));
            changeHTML(defaultPage, "state", "寻找搜索框...");

            page.getByRole(AriaRole.SEARCHBOX, new Page.GetByRoleOptions().setName("検索キーワードを入力")).fill(name);
            changeHTML(defaultPage, "state", "检索商品...");

            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("検索")).nth(1).click();
            changeHTML(defaultPage, "state", "等待返回...");

            page.waitForTimeout(1000);
            page.locator("#search-result").getByRole(AriaRole.CHECKBOX).check();
            Response response = page.waitForResponse("https://api.mercari.jp/v2/entities:search", () -> {
                page.waitForLoadState(LoadState.NETWORKIDLE);

            });
            JSONObject body = JSONUtil.parseObj(response.text());
            changeHTML(defaultPage, "state", "OK!!!");
            items = body.getJSONArray("items");
            return items.toString();
        } catch (Exception e) {
            log.error("发生错误:{}", e.toString());
        } finally {
            page.close();
        }
        return items;
    }

    private static void changeHTML(Page page, String id, String text) {
        page.evaluate("() => document.querySelector('#" + id + " ').innerHTML=\"" + text + "\"");
    }

    @RequestMapping(value = "/douyin/spy", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void spyDouyin(@RequestParam(name = "url") String url) {
        log.info("【douyin】：{}", url);
        Page page = browserContext.newPage();
        page.navigate(url, new Page.NavigateOptions().setWaitUntil(WaitUntilState.NETWORKIDLE));
        Locator ROOT = page.locator("#root");
        ROOT.waitFor();
        while (!Objects.equals(page.url(), url)) {
            page.waitForTimeout(5000);
            page.navigate(url, new Page.NavigateOptions().setWaitUntil(WaitUntilState.NETWORKIDLE));
        }
        // login-mask-enter-done
        // dy-account-close
        // 关闭登录
        // page.waitForSelector(".dy-account-close", new Page.WaitForSelectorOptions().setTimeout(5000)).click();
        CharSequence 评论数 = page.waitForSelector(spyProperties.get抖音评论数()).innerText();
        CharSequence 点赞数 = page.waitForSelector(spyProperties.get抖音点赞数()).innerText();
        CharSequence 转发数 = page.waitForSelector(spyProperties.get抖音转发数()).innerText();
        // 暂停播放
        if (!page.waitForSelector(".xgplayer-errornote", new Page.WaitForSelectorOptions().setTimeout(1000))
                .innerText().contains("不支持")
        ) {
            page.querySelector(spyProperties.get抖音播放按钮()).click();
        }
        for (ElementHandle el : page.querySelectorAll(".comment-mainContent>div")) {
            if (Objects.equals(el.innerText(), "加载中")) continue;
            log.info(StrUtil.format("{}:{}-{}",
                            el.querySelector(".comment-item-info-wrap>div").innerText()),
                    StrUtil.removeAllLineBreaks(el.querySelector("p").innerText()),
                    el.querySelector("div div:nth-of-type(2) p + div").innerText()
            );
        }
        page.close();
    }
}
