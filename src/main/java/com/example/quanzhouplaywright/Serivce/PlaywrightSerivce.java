package com.example.quanzhouplaywright.Serivce;

import com.example.quanzhouplaywright.Properties.BrowserProperties;
import com.microsoft.playwright.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@EnableConfigurationProperties(BrowserProperties.class)
@Service
public class PlaywrightSerivce {

    private Playwright playwright;
    private Browser browser;
    private final BrowserProperties browserProperties;
    private final ConfigurableApplicationContext context;

    public PlaywrightSerivce(BrowserProperties browserProperties, ConfigurableApplicationContext context) {
        this.browserProperties = browserProperties;
        this.context = context;
    }

    @PostConstruct
    public void initialize() {
        this.playwright = Playwright.create();
        this.browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(browserProperties.getHeadless()));
        this.browser.onDisconnected(browser -> {
            context.close();
        });
    }

    @PreDestroy
    public void shutdown() {
        this.browser.close();
        this.playwright.close();
        System.exit(0);
    }

    public Browser defaultBrowser() {
        return this.browser;
    }

    /**
     * 返回默认值的浏览器上下文
     *
     * @return 浏览器上下文
     */
    public BrowserContext createContext() {
        List<String> permissions = new ArrayList<>();
        permissions.add("geolocation");
        return this.browser.newContext(new Browser.NewContextOptions().setLocale("zh-CN").setGeolocation(41.889938, 12.492507).
                setPermissions(permissions));
    }

    /**
     * 返回浏览器上下文
     *
     * @param newContextOptions 上下文的选项
     * @return 浏览器上下文
     */
    public BrowserContext createContext(Browser.NewContextOptions newContextOptions) {
        return this.browser.newContext(newContextOptions);
    }

    @Deprecated
    public Page createPage(BrowserContext browserContext) {
        return browserContext.newPage();
    }

}
