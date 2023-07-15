package com.example.impl;

import com.example.interfaces.Tyres;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class MichelinTyres implements Tyres {
    @Override
    public String rotate() {
        return "Moving with Michelin tyres";
    }
}
