package com.meiya.got.Config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitmqConfig {


    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    //重新注册监听消息手动接收
    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }



    //topic
    public static final String TOPIC_QUEUE1 = "topic.queue1";
    public static final String TOPIC_QUEUE2 = "topic.queue2";
    public static final String TOPIC_EXCHANGE = "topic.exchange";

    //fanout
    public static final String FANOUT_QUEUE1 = "fanout.queue1";
    public static final String FANOUT_QUEUE2 = "fanout.queue2";
    public static final String FANOUT_EXCHANGE = "fanout.exchange";

    //redirect模式
    public static final String DIRECT_QUEUE1 = "direct.queue1";
    public static final String DIRECT_EXCHANGE = "direct.exchange";
    public static final String USER_EXCHANGE = "user.exchange";
    public static final String USER_QUEUE ="user.queue" ;

    public static final String STORE_EXCHANGE = "store.exchange";
    public static final String STORE_QUEUE ="store.queue" ;



    /**
     * Topic模式
     *
     * @return
     */
    @Bean
    public Queue topicQueue1() {
        return new Queue(TOPIC_QUEUE1);
    }

    @Bean
    public Queue topicQueue2() {
        return new Queue(TOPIC_QUEUE2);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Binding topicBinding1() {
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("connection.message");
    }

    @Bean
    public Binding topicBinding2() {
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("lzc.#");
    }


    /**
     * Fanout模式
     * Fanout 就是我们熟悉的广播模式或者订阅模式，给Fanout交换机发送消息，绑定了这个交换机的所有队列都收到这个消息。
     * @return
     */
    @Bean
    public Queue fanoutQueue1() {
        return new Queue(FANOUT_QUEUE1);
    }

    @Bean
    public Queue fanoutQueue2() {
        return new Queue(FANOUT_QUEUE2);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Binding fanoutBinding1() {
        return BindingBuilder.bind(fanoutQueue1()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutBinding2() {
        return BindingBuilder.bind(fanoutQueue2()).to(fanoutExchange());
    }

    /**
     * direct模式
     * 消息中的路由键（routing key）如果和 Binding 中的 binding key 一致， 交换器就将消息发到对应的队列中。路由键与队列名完全匹配
     * @return
     */
    @Bean
    public Queue directQueue1() {
        return new Queue(DIRECT_QUEUE1);
    }

    @Bean
    public Queue UserQueue1() {
        return new Queue(USER_QUEUE);
    }
    @Bean
    public DirectExchange UserExchange() {
        return new DirectExchange(USER_EXCHANGE);
    }
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE);
    }



    @Bean
    public Binding directBinding1() {
        return BindingBuilder.bind(directQueue1()).to(directExchange()).with("direct.pwl");
    }

    @Bean
    public Binding UserBinding1() {
        return BindingBuilder.bind(UserQueue1()).to(UserExchange()).with("store.user");
    }


    @Bean
    public Queue StoreQueue1() {
        return new Queue(STORE_QUEUE);
    }

    @Bean
    public DirectExchange StoreExchange() {
        return new DirectExchange(STORE_EXCHANGE);
    }


    @Bean
    public Binding StoreBinding1() {
        return BindingBuilder.bind(StoreQueue1()).to(StoreExchange()).with("user.store");
    }


    /**
     * 测试消息确认
     * @return
     */
    @Bean
    public Queue QueueA() {
        return new Queue("hello");
    }

    @Bean
    public Queue QueueB() {
        return new Queue("helloObj");
    }

    /**
     * Fanout 就是我们熟悉的广播模式或者订阅模式，给Fanout交换机发送消息，绑定了这个交换机的所有队列都收到这个消息。
     * @return
     */
    @Bean
    FanoutExchange TestfanoutExchange() {
        return new FanoutExchange("ABExchange");
    }


    @Bean
    Binding bindingExchangeA(Queue QueueA, FanoutExchange TestfanoutExchange) {
        return BindingBuilder.bind(QueueA).to(TestfanoutExchange);
    }

    @Bean
    Binding bindingExchangeB(Queue QueueB, FanoutExchange TestfanoutExchange) {
        return BindingBuilder.bind(QueueB).to(TestfanoutExchange);
    }

    //消息手动确认配置

}
