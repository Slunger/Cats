package com.cats.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by andrey on 07.02.17.
 */
@Configuration
@ComponentScan({"com.cats.services",
        "com.cats.dao"})
@PropertySource(value = "file:${user.home}/application.properties")
public class SpringRootConfiguration {
}
