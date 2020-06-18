package com.atguigu.gulimall.wms.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@EnableRabbit
@Configuration
public class RabbitConfig {


    @Bean
    public  MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }



    @Bean("skuStockCreateExchange")
    public Exchange skuStockCreateExchange(){

        /**
         * String name, boolean durable, boolean autoDelete, Map<String, Object> arguments
         */
        return new DirectExchange("skuStockCreateExchange",true,false,null);
    }

    @Bean("deadskuStockStorageQueue")  //死信队列，千万不要有人消费
    public Queue deadskuStockStorageQueue(){
        /**
         * String name, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object>
         */
        Map<String,Object> properties = new HashMap<>();
        properties.put("x-dead-letter-exchange","skuStockDeadExchange"); //信死了以后发给那个交换机，而不是丢弃
        properties.put("x-dead-letter-routing-key","dead.skuStock");
        properties.put("x-message-ttl",1000*30);//ms为单位
        return new Queue("deadskuStockStorageQueue",true,false,false,properties);
    }

    @Bean("deadskuStockRoutingBinding")
    public Binding deadskuStockRoutingBinding(){
        /**
         * String destination,
         * DestinationType destinationType,
         * String exchange,
         * String routingKey,
         * Map<String, Object> arguments
         */
        return new Binding("deadskuStockStorageQueue",
                Binding.DestinationType.QUEUE,
                "skuStockCreateExchange",
                "create.skuStock",null);
    }

    //============以上订单创建的信息能保存到死信队列里面==============
    @Bean("skuStockDeadExchange")
    public Exchange skuStockDeadExchange(){

        return new DirectExchange("skuStockDeadExchange",true,false,null);
    }

    @Bean("closeskuStockQueue")
    public Queue deadskuStockQueue(){
        return new Queue("closeskuStockQueue",true,false,false,null);
    }

    @Bean("deadBinding")
    public Binding deadBinding(){
        return new Binding("closeskuStockQueue",
                Binding.DestinationType.QUEUE,
                "skuStockDeadExchange",
                "dead.skuStock",
                null);
    }

}
