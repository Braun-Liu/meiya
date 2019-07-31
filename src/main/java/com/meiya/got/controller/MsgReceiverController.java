package com.meiya.got.controller;

import com.meiya.got.Config.RabbitmqConfig;
import com.meiya.got.dao.OrderDao;
import com.meiya.got.dao.StoreDAO;
import com.meiya.got.po.MsgConnection;
import com.meiya.got.po.Student;
import com.meiya.got.service.StoreOrderService;
import com.meiya.got.util.JedisUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.servlet.http.HttpSession;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketSession;

/**
 * Created by qs5 on 2018/10/11.
 * @author wangzhen
 * @version 1.0
 */
@Slf4j
@ServerEndpoint(value = "/socket/{store_id}")
@Component
@Controller
@RabbitListener(queues = RabbitmqConfig.STORE_QUEUE)
public class MsgReceiverController {

    @Autowired
    private StoreDAO storeDAO;

    @Autowired
    private StoreOrderService storeOrderService;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private JedisUtil jedisUtil;


    public static String msg;
    private static Map<Long,MsgReceiverController> socketMap=new HashMap<>();
    private static Logger logger = LoggerFactory.getLogger(MsgReceiverController.class);
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<MsgReceiverController> wsClientMap = new CopyOnWriteArraySet<>();
    private static CopyOnWriteArraySet<Session> SessionSet = new CopyOnWriteArraySet<Session>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    public Session session;
    //右键监听结果





    @RabbitHandler
    public void receiver(@Payload MsgConnection msgConnection, Channel channel, @Headers Map<String,Object> headers) throws IOException {
        Long store_id = msgConnection.getStore_id();
        long deliveryTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
        if (socketMap.get(store_id)==null){
            channel.basicNack(deliveryTag,false,true);
            return;
        }

        logger.info("<=============监听到user_queue队列消息============>"+msgConnection);
        System.out.println("监听到了msgconnection-----"+msgConnection.toString());

        System.out.println("得到的session");



            channel.basicAck(deliveryTag,false);

            switch (msgConnection.getUser_mid()){
                case 1: sendMessage("您有新订单了",store_id);break;
                case 2:
                    sendMessage("买家已付款",store_id);
                     if (storeDAO.findMyAutorecieveStatus(store_id)==1){

                         storeOrderService.addSalesBySid(store_id);
                     }
                    break;
                case 3: sendMessage("买家取消订单",store_id);break;
                case 4: sendMessage("您收到一个催单请求，请您尽快配送订单哦！",store_id);break;
                case 5: sendMessage("买家已收货！餐款已到账！",store_id);break;
                case 6: sendMessage("您有新的评论了！",store_id);break;
                default:return ;
            }

        /*    if (msgConnection.getUser_mid()==1){

//                System.out.println("商家在线----------来到了1方法");


                sendMessage("您有新订单了！",store_id);

            }else if(msgConnection.getUser_mid()==2){

            sendMessage("卖家已付款！",store_id);

            }else if(msgConnection.getUser_mid()==3){

            sendMessage("卖家取消订单，请您安排退款哦！",store_id);

            }else if(msgConnection.getUser_mid()==4){

                sendMessage("您收到一个催单请求，请您尽快配送订单哦！",store_id);

             }else if (msgConnection.getUser_mid()==5){

                sendMessage("卖家已收货！餐款已到账！",store_id);

            }*/



            //channel.basicReject(deliveryTag,true);
           // System.out.println("商家不在线，返回此消息到队列中-------------------");

       /* if("成功".equals(msg)){
            sendMessage(msg);
        }else{
            sendMessage(msg);
        }*/
    }
/*    @RequestMapping("/getMessage")
    public String receiver(String body,String taskId){
        return msg;
    }*/
    /**
     * 连接建立成功调用的方法
     * @param session 当前会话session
     */
    @OnOpen
    public void onOpen (@PathParam(value="store_id") Long store_id,Session session){


        System.out.println("商家id为------"+store_id);
        socketMap.put(store_id,this);
        this.session = session;
        SessionSet.add(session);
        System.out.println("此时的session-----------"+session);
        wsClientMap.add(this);
        addOnlineCount();

        logger.info(session.getId()+"有新链接加入，当前链接数为：" + wsClientMap.size());

    }
    /**
     * 连接关闭
     */
    @OnClose
    public void onClose (){
        wsClientMap.remove(this);
        subOnlineCount();
        logger.info("有一链接关闭，当前链接数为：" + wsClientMap.size());
    }
    /**
     * 收到客户端消息
     * @param message 客户端发送过来的消息
     * @param session 当前会话session
     * @throws IOException
     */
   /* @OnMessage
    public void onMessage (String message, Session session) throws IOException {
        logger.info("客户端发送过来的消息:" + message);
//        String message1="你吃饭了吗？";
//        sendMessage(message1);
    }*/
    /**
     * 发生错误
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.info("wsClientMap发生错误!");
        error.printStackTrace();
    }

    /**
     * 给所有客户端群发消息
     * @param message 消息内容
     * @throws IOException
     */
    public void sendMsgToAll(String message) throws IOException {
        for ( MsgReceiverController item : wsClientMap ){
            item.session.getBasicRemote().sendText(message);
        }
        logger.info("成功群送一条消息:" + wsClientMap.size());
    }
    //实现服务器主动推送

    public void sendMessage (String message,Long store_id) throws IOException {
        System.out.println("------------------------------");
        /*Session session=null;*/
        System.out.println("storeID的值为----------"+store_id);
        MsgReceiverController item  =socketMap.get(store_id);
        System.out.println("此时itme的地址--------------"+item);
        if (item!=null){

            item.session.getBasicRemote().sendText(message);
        }
        /*System.out.println("message++++++++++++"+message);
        if (session.isOpen()){

            this.session.getBasicRemote().sendText(message);
            logger.info("成功发送一条消息:" + message);
        }
        logger.info("消息发送失败:" + message);*/
    }

    public static synchronized  int getOnlineCount (){
        return MsgReceiverController.onlineCount;
    }

    public static synchronized void addOnlineCount (){
        MsgReceiverController.onlineCount++;
    }

    public static synchronized void subOnlineCount (){
        MsgReceiverController.onlineCount--;
    }
}

