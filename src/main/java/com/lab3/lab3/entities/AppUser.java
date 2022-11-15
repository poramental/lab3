package com.lab3.lab3.entities;

import org.springframework.stereotype.Component;

import com.lab3.lab3.botapi.state.States;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Component
@NoArgsConstructor
@Setter
@Getter
public class AppUser {
    long userId;
    States userState;
    

}
