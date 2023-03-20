package com.example.quanzhouplaywright.Entity;

import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;

/**
 * 微博
 */
public class Posts {

	ArrayList<Comments> 留言;
	Integer 转发数;
	Integer 留言数;
	CharSequence 点赞数;


	@Override
	public String toString() {
		String s1 = StrUtil.format("转发数：{}，留言数：{}，点赞数，{}", this.转发数, this.留言数, this.点赞数);
		StringBuilder sb = new StringBuilder();
		for (Comments comments : 留言) {
			sb.append(comments.toString());
		}
		return s1 + sb.toString();
	}

	public Posts() {
		this.留言 = new ArrayList<Comments>();
	}

	public ArrayList<Comments> get留言() {
		return 留言;
	}

	public Boolean add留言(Comments comments) {
		return this.留言.add(comments);
	}

	public Integer get转发数() {
		return 转发数;
	}

	public void set转发数(Integer 转发数) {
		this.转发数 = 转发数;
	}

	public Integer get留言数() {
		return 留言数;
	}

	public void set留言数(Integer 留言数) {
		this.留言数 = 留言数;
	}

	public CharSequence get点赞数() {
		return 点赞数;
	}

	public void set点赞数(CharSequence 点赞数) {
		this.点赞数 = 点赞数;
	}
}
