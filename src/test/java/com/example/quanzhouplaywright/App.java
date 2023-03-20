package com.example.quanzhouplaywright;

import com.microsoft.playwright.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class App {
	public static void main(String[] args) {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
			BrowserContext context = browser.newContext();
			Page pageOne = context.newPage();
			pageOne.navigate("https://weibo.    com");
			Page pageTwo = context.newPage();
			pageOne.navigate("https://douyin.com");
			List<Page> allPages = context.pages();
			log.info("end");
		}
	}
}
