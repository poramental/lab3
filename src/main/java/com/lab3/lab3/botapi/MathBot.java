package com.lab3.lab3.botapi;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.lab3.lab3.botapi.commands.BotStartCommand;
import com.lab3.lab3.botapi.commands.GaussCommand;
import com.lab3.lab3.botapi.commands.ZeidelCommand;
import com.lab3.lab3.botapi.config.MathBotConfig;

import lombok.Setter;




@Setter
public class MathBot extends TelegramLongPollingBot{

    String botName;
    String botToken;
    TelegramFacade telegramFacade;
    

    private List<BotCommand> listOfCommands = new ArrayList<>();


    public MathBot(TelegramFacade telegramFacade, MathBotConfig config) {
        super();
        this.telegramFacade = telegramFacade;
        this.botName = config.getBotName();
        this.botToken = config.getBotToken();
        this.listOfCommands.add(new BotStartCommand().getBotCommand());
        this.listOfCommands.add(new GaussCommand().getBotCommand());
        this.listOfCommands.add(new ZeidelCommand().getBotCommand());
        try{
            execute(new SetMyCommands(this.listOfCommands, new BotCommandScopeDefault(),null));
            
        }catch(TelegramApiException e){
            System.out.println(e.getMessage());
        }

    }


    @Override
    public void onUpdateReceived(Update arg0) {
        try {
            sendMessage(telegramFacade.onUpdateReceived(arg0));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } 
        
    }

    @Override
    public String getBotToken() {
        return this.botToken;
    }

    @Override
    public String getBotUsername() {
        return this.botName;
    }

    public void sendMessage(BotApiMethod<?> message) throws TelegramApiException{
        execute(message);
    }
    

    public void botConnect() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(this);
            
        } catch (TelegramApiRequestException e) {
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                return;
            }
            botConnect();
        }
    }
}
