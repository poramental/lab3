package com.lab3.lab3.botapi.handlers;


import java.util.Objects;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.lab3.lab3.botapi.commands.BotStartCommand;
import com.lab3.lab3.botapi.commands.GaussCommand;
import com.lab3.lab3.botapi.commands.ZeidelCommand;
import com.lab3.lab3.botapi.state.StateCache;
import com.lab3.lab3.botapi.state.States;
import com.lab3.lab3.entities.AppUser;
import com.lab3.lab3.labapi.Expression;
import com.lab3.lab3.labapi.exceptions.ExpressionParseException;
import com.lab3.lab3.labapi.exceptions.GaussException;
import com.lab3.lab3.labapi.zeidel.Zeidel;
import com.lab3.lab3.labapi.gauss.Gauss;

@Component
public class MessageHandler {
    @Autowired
    BotStartCommand startCommand;

    @Autowired
    GaussCommand gausCommand;

    @Autowired
    ZeidelCommand zeidelCommand;

    public BotApiMethod<?> handle(Update update, StateCache cache){
        var messageFromUser = update.getMessage();
        long userId = messageFromUser.getChatId();
        SendMessage sendmessage = new SendMessage();
        sendmessage.setChatId(String.valueOf(userId));
        AppUser appUser = new AppUser();
        appUser.setUserId(userId);
        if(Objects.equals(messageFromUser.getText(), startCommand.getCommandName())){
            
            sendmessage.setText(startCommand.getAnswer(messageFromUser.getFrom().getFirstName()));
            return sendmessage;
        }

        if(Objects.equals(messageFromUser.getText(), gausCommand.getCommandName())){
            
            sendmessage.setText("Введите своё уравнение.");
            appUser.setUserState(States.GETTINGGAUSSEXPRESSION);
            cache.add(userId, appUser);
            return sendmessage;
        }

        if(Objects.equals(messageFromUser.getText(),zeidelCommand.getCommandName() )){
            sendmessage.setText("Введите своё уравнение.");
            appUser.setUserState(States.GETTINGZEIDELEXPRESSION);
            cache.add(userId, appUser);
            return sendmessage;
        }


        if(cache.getStateById(userId) == States.GETTINGGAUSSEXPRESSION){
            try{
                Expression expr = new Expression();
                expr.parseExpression(messageFromUser.getText());
                
                double[] x = Gauss.solve(expr);
                String textToSend = "";
                int i = 1;
                for(double d : x){
                    textToSend += "x"+i +" = "+ d+ "\n";
                    i++;
                
                }
                
                textToSend += "Ваши ответы 👍";
                sendmessage.setText(textToSend);
                cache.delete(userId);
                return sendmessage;
            }catch(GaussException e){
                sendmessage.setText(e.getMessage());
                return sendmessage;
            }catch(ExpressionParseException e){
                sendmessage.setText(e.getMessage());
                return sendmessage;
            }
            catch(Exception e){
                sendmessage.setText("ошибка(попробуйте заменить запятые на точку,а знаки тире на знак минуса).");
                cache.delete(userId);
                e.printStackTrace();
                return sendmessage;
            }
            
            
        }

        if(cache.getStateById(userId) == States.GETTINGZEIDELEXPRESSION){
            try{
                Expression expr = new Expression();
                expr.parseExpression(messageFromUser.getText());
                
                double[] x = Zeidel.solve(expr);
                String textToSend = "";
                int i = 1;
                for(double d : x){
                    textToSend += "x"+i +" = "+ d+ "\n";
                    i++;
                
                }
                
                textToSend += "Ваши ответы 👍";
                sendmessage.setText(textToSend);
                cache.delete(userId);
                return sendmessage;
            
            }catch(ExpressionParseException e){
                sendmessage.setText(e.getMessage());
                return sendmessage;
            }
            catch(Exception e){
                sendmessage.setText("ошибка(попробуйте заменить запятые на точку,а знаки тире на знак минуса).");
                cache.delete(userId);
                e.printStackTrace();
                return sendmessage;
            }
        }

        return null;
    }
}
