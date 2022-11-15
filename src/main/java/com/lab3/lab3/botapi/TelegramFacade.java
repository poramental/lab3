package com.lab3.lab3.botapi;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.lab3.lab3.botapi.handlers.CallBackQueryHandler;
import com.lab3.lab3.botapi.handlers.MessageHandler;
import com.lab3.lab3.botapi.state.StateCache;


@Component
public class TelegramFacade {


    private SendMessage defaultSendMessage = new SendMessage();

    @Autowired
    MessageHandler messageHandler;

    @Autowired 
    CallBackQueryHandler callbackQueryHandler;

    @Autowired
    StateCache stateCache;

    public BotApiMethod<?> onUpdateReceived(Update update){

        BotApiMethod<?> answer;
       
        if(update.hasMessage()){
            answer = messageHandler.handle(update, stateCache);
            if(answer == null){
                return getDefaultSendMessage(String.valueOf(update.getMessage().getChatId()));
            }else{
                return answer;
            }
            
        }
        if(update.hasCallbackQuery()){
            answer = callbackQueryHandler.handle(update);
            if(answer == null){
                return getDefaultSendMessage(String.valueOf(update.getMessage().getChatId()));
            }else{
                return answer;
            }
        }
        return null;
    }
    
    private SendMessage getDefaultSendMessage(String chatId){
        defaultSendMessage.setChatId(chatId);
        defaultSendMessage.setText("не понимаю :(");
        return defaultSendMessage;
    }
    
}
