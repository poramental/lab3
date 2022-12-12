package com.lab3.lab3.botapi.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import lombok.Getter;

@Component
@Getter
public class ZeidelCommand {

    
        
        private final String commandName = "/zeidel";
    
        private final String discription = "решить уравнение методом Зейделя";
        
    
    
        public String getAnswer(){
            return "Отправьте ваше уравнение.";
        }
    
        public BotCommand getBotCommand(){
            return new BotCommand(commandName,discription);
        }
    
    }