package com.cats.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by andrey on 07.02.17.
 */
@Configuration
@ComponentScan({"com.cats.services",
        "com.cats.dao"})
@Import({HibernateConfig.class,
        HibernateConfigDev.class,
        SecurityConfig.class})
@PropertySource(value = "resources/application.properties")
public class SpringRootConfiguration {
}
