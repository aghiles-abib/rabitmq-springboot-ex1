package com.demo.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

@RestController
@RequestMapping("/api/v1")
public class TestController {
    @Autowired
    RabbitTemplate rabbitTemplate;


    @GetMapping("/test2/{name}")
    public String test2API(@PathVariable("name") String name){
        Person p = new Person(1L,name);
        rabbitTemplate.convertAndSend("Mobile",p);
        rabbitTemplate.convertAndSend("Direct-Exchange","mobile",p);
        rabbitTemplate.convertAndSend("Fanout-Exchange","",p);
        rabbitTemplate.convertAndSend("Topic-Exchange","tv.mobile.ac",p);
        return "Success";
    }

    @GetMapping("/test/{name}")
    public String testAPI(@PathVariable("name") String name) {
        Person p = new Person(1L, name);
        MessagePostProcessor m = message -> {
            message.getMessageProperties().setHeader("item1", "mobile");
            message.getMessageProperties().setHeader("item2", "television");
            return message;
        };
        rabbitTemplate.convertAndSend(
                "Headers-Exchange",
                "", // Routing key ignorée
                p,
                m
        );
        return "Success";
    }



}
