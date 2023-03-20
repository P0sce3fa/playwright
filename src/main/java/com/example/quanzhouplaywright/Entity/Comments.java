package com.example.quanzhouplaywright.Entity;

import cn.hutool.core.util.StrUtil;

/**
 * 微博下的留言
 */
public class Comments {

	private String 留言用户;
	private String 留言内容;
	private String 留言时间;

	public Comments() {
	}

	@Override
	public String toString() {
		return StrUtil.format("留言用户：{}，留言内容：{}，留言时间：{}", this.留言用户,
				this.留言内容, this.留言时间);
	}

	public String get留言用户() {
		return 留言用户;
	}

	public void set留言用户(String 留言用户) {
		this.留言用户 = 留言用户;
	}

	public String get留言内容() {
		return 留言内容;
	}

	public void set留言内容(String 留言内容) {
		this.留言内容 = 留言内容;
	}

	public String get留言时间() {
		return 留言时间;
	}

	public void set留言时间(String 留言时间) {
		this.留言时间 = 留言时间;
	}
}
