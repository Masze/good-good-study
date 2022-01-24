package com.wj.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Producer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @GetMapping("send/{msg}")
    public void send(@PathVariable String msg) {
        kafkaTemplate.send("someTopic", msg);
    }

}
