package com.delrima.webgene.server.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.delrima.webgene.server.rest.MemberController;
import com.delrima.webgene.server.services.AbstractWebgeneActionHandler;

@Configuration
@ComponentScan(basePackageClasses = { MemberController.class, AbstractWebgeneActionHandler.class })
@EnableWebMvc
public class SpringRestConfiguration {
}
