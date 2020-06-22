package com.william.kafka.consumer;


import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@Component
public class NewsConsumer {

    private static final Logger logger = LoggerFactory.getLogger(NewsConsumer.class);

/*    @KafkaListener(topics={"news_topic"})
    public void receive(@Payload String message, @Headers MessageHeaders headers){
        logger.info("KafkaMessageConsumer 接收到消息："+message);
        headers.keySet().forEach(key->logger.info("{}: {}",key,headers.get(key)));
    }

    @KafkaListener(topics={"news_topic"})
    public void receive1(KafkaStream<byte[], byte[]> kafkaStream){
        ConsumerIterator<byte[], byte[]> iterator = kafkaStream.iterator();
        while(iterator.hasNext()) {
            try {
                MessageAndMetadata<byte[], byte[]> messageAndMetadata = iterator.next();
                byte[] message = messageAndMetadata.message();
                String res = new String(message,"UTF-8");
                logger.info("接收消息："+res);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    @KafkaListener(topics={"news_topic"})
    public void receive2(ConsumerRecord record){
        logger.info("接收消息="+record.value());
    }*/

    @KafkaListener(id="myListener",topicPartitions ={@TopicPartition(topic = "news_topic", partitions = { "0"})})
    public void listenPartition1(List<ConsumerRecord<String, String>> records) {
        records.forEach((record)->{
            logger.info("kafka的key: " + record.key());
            logger.info("kafka的value: " + new String(record.value()));
        });
        /*logger.info("Id1 Listener, Thread ID: " + Thread.currentThread().getId());
        logger.info("Id1 records size " + records.size());

        for (ConsumerRecord<?, ?> record : records) {
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
            logger.info("Received: " + record);
            if (kafkaMessage.isPresent()) {
                Object message = record.value();
                String topic = record.topic();
                logger.info("p1 Received message={}", message);
            }
        }*/
    }

}
