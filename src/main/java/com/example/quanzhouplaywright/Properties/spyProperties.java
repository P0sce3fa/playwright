package com.example.quanzhouplaywright.Properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configurable
//@ConfigurationProperties(prefix = "weibo", ignoreUnknownFields = false)
@PropertySource("classpath:application.properties")
@Data
@Component
public class spyProperties {

	@Value("${weibo.pljx}")
	private String 微博评论精选;

	@Value("${weibo.dzs}")
	private String 微博点赞数;

	@Value("${weibo.pls}")
	private String 微博评论数;

	@Value("${douyin.dzs}")
	private String 抖音点赞数;

	@Value("${douyin.pls}")
	private String 抖音评论数;

	@Value("${douyin.zfs}")
	private String 抖音转发数;

	@Value("${douyin.playbtn}")
	private String 抖音播放按钮;
}
