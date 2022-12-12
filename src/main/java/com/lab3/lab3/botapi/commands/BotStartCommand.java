package com.lab3.lab3.botapi.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import lombok.Getter;


@Getter
@Component
public class BotStartCommand  {
    
    private final String commandName = "/start";

    private final String discription = "стартуем";
    
   

    public String getAnswer(String name){
        return "Здравствйте, " + name + "! Выберете команду в меню.";
    }

    public BotCommand getBotCommand(){
        return new BotCommand(commandName,discription);
    }

}
