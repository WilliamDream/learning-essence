package com.william.kafka.controller;


import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/test")
public class NewsController {

    private static final Logger LOG = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping(value="/sendMessage",method= RequestMethod.GET)
    public void send(@RequestParam(required=true) String message){

//        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("news_topic", message.getBytes());
//        future.addCallback(success -> LOG.info("KafkaMessageProducer 发送消息成功！"),
//                fail -> LOG.error("KafkaMessageProducer 发送消息失败！"));
        ProducerRecord<String,String> record = new ProducerRecord<>("news_topic",message);
        kafkaTemplate.send(record);
    }



}
