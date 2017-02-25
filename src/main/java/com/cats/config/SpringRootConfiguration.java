package com.cats.config;

import com.cats.config.security.OAuth2ServerConfig;
import com.cats.config.security.SecurityConfig;
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
        SecurityConfig.class,
        OAuth2ServerConfig.class,
        WebSocketConfig.class})
@PropertySource(value = "resources/application.properties")
public class SpringRootConfiguration {
}
