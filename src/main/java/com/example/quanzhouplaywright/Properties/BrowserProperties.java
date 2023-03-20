package com.example.quanzhouplaywright.Properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Configurable
@ConfigurationProperties(prefix = "browser", ignoreUnknownFields = false)
@PropertySource("classpath:application.properties")
@Data
@Component
public class BrowserProperties {

	private Boolean headless;
}
