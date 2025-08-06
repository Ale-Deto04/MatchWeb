package org.partiteweb.matchweb.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.partiteweb.matchweb")
@EnableFeignClients(basePackages = "org.partiteweb.matchweb")
public class OpenFeignConfig { }
