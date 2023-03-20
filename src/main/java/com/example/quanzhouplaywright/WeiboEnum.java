package com.example.quanzhouplaywright;

public enum WeiboEnum {

	评论数("//*[@id=\"app\"]/div[1]/div[2]/div[2]/main/div[1]/div/div[2]/article/footer/div/div[2]/div/span"),
	点赞数("xpath=//*[@id=\"app\"]/div[1]/div[2]/div[2]/main/div[1]/div/div[2]/article/footer/div/div[3]/div/button/span[2]"),
	评论("//*[@id=\"scroller\"]/div"),
	每条评论(".vue-recycle-scroller__item-view"),
	评论区("xpath=//*[@id=\"app\"]/div[1]/div[2]/div[2]/main/div[1]/div/div[2]/div[2]/div[3]/div/div[2]"),
	评论精选("xpath=//*[@id=\"app\"]/div[1]/div[2]/div[2]/main/div[1]/div/div[2]/div[2]/div[3]/div[2]/div/span");


	private String Selector;

	WeiboEnum(String selector) {
		Selector = selector;
	}

	public String getSelector() {
		return Selector;
	}

	public void setSelector(String selector) {
		Selector = selector;
	}
}

