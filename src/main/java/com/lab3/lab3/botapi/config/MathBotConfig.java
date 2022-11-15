package com.lab3.lab3.botapi.config;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;



@Getter
@Component
@PropertySource("application.properties")
public class MathBotConfig {
    
    @Value("${bot.token}")
    private String botToken;
    
    @Value("${bot.name}")
    private String botName;
}