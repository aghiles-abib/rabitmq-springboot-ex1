package com.demo.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
public class Person implements Serializable {

    private Long id;
    private String name;
}
