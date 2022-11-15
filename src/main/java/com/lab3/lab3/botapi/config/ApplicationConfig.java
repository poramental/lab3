package com.lab3.lab3.botapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.lab3.lab3.botapi.MathBot;
import com.lab3.lab3.botapi.TelegramFacade;

@Configuration
public class ApplicationConfig {

    @Autowired
    private  MathBotConfig botConfig;

    @Autowired
    private TelegramFacade telegramFacade;
   

   
    @Bean
    public MathBot springWebhookBot() throws TelegramApiException {
        MathBot bot = new MathBot( telegramFacade, botConfig);
        bot.botConnect();

        return bot;
    }
    }
