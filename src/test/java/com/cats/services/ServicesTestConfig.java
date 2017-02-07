package com.cats.services;

import com.cats.config.HibernateConfigDev;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by andrey on 07.02.17.
 */
@Configuration
@ComponentScan({"com.cats.services",
        "com.cats.dao"})
@Import({HibernateConfigDev.class})
public class ServicesTestConfig {
}
