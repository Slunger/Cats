package com.cats.services;

import com.cats.config.HibernateConfigDev;
import com.cats.config.WebSocketConfig;
import com.cats.config.security.SecurityConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by andrey on 07.02.17.
 */
@Configuration
@ComponentScan({"com.cats.services",
        "com.cats.dao"})
@Import({HibernateConfigDev.class,
        SecurityConfig.class,
        WebSocketConfig.class})
public class ServicesTestConfig {
}
