package ru.kamotora.lab3.service;

import android.os.Binder;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SimpleServiceBinder extends Binder {
    private final SimpleService simpleService;
}
