package com.delrima.webgene.server.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.delrima.webgene.server.rest.MemberController;

@Configuration
@ComponentScan(basePackageClasses = { MemberController.class })
@EnableWebMvc
public class SpringRestConfiguration {
}
