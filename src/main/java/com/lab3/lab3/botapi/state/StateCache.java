package com.lab3.lab3.botapi.state;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.lab3.lab3.entities.AppUser;


@Component
public class StateCache {
    private Map<Long, AppUser> usersAndStates = new HashMap<>();
    

    public Boolean isHaveState(long userId){
        for(long id : usersAndStates.keySet()){
            if(id == userId){
                return true;
            }
        }
        return false;
    }

    public void add(long id, AppUser user){
        usersAndStates.put(id,user);
    }

    public States getStateById(long id){
        try{
        return usersAndStates.get(id).getUserState();
        }catch (NullPointerException e){
            return States.DEFAULT;
        }
    }

    public Iterable<AppUser> getValues(){
        return usersAndStates.values();
    }

    public void delete(long id){
        usersAndStates.remove(id);
    }

    public AppUser getUserById(long id){
        return usersAndStates.get(id);
    }
}