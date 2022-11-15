package com.lab3.lab3.botapi.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;



@Component
public class CallBackQueryHandler {
    
    public BotApiMethod<?> handle(Update update){
        return null;
    }


}
