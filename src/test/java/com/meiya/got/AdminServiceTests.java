package com.meiya.got;

import com.meiya.got.dao.AdminDAO;
import com.meiya.got.dao.FoodDao;
import com.meiya.got.dao.StoreDAO;
import com.meiya.got.po.Foods;
import com.meiya.got.po.MsgConnection;
import com.meiya.got.po.Student;
import com.meiya.got.rabbitmq.sender.FanoutSender;
import com.meiya.got.rabbitmq.sender.HelloSender;
import com.meiya.got.rabbitmq.sender.TopicSender;
import com.meiya.got.service.FoodsService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = GotApplication.class)
//@Sql("/*.sql")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GotApplication.class)
public class AdminServiceTests {

  /*  @Autowired
    private AdminDAO adminDAO;
    @Autowired
    private StoreDAO storeDAO;

    @Autowired
    private FoodDao foodDao;
    @Autowired
    private FoodsService foodsService;*/

    @Autowired
    private FanoutSender fanoutSender;


    @Autowired
    private TopicSender topicSender;
    /*@Autowired
    private TopicSender topicSender;
    @Autowired
    private DirectSender directSender;*/


    @Test
    public void testFanout() throws Exception {
        Student student=new Student();
        student.setId("1");
        student.setName("pwl");
        fanoutSender.send(student);
    }

/*    @Test
    public void testFanout2() throws Exception {
        MsgConnection msgConnection=new MsgConnection(Long.valueOf("123"),1,0,1,1);

        fanoutSender.send(msgConnection);
    }*/

    /*@Test
    public void testTopic() throws Exception {

        Long a=Long.valueOf("123");
        MsgConnection msgConnection=new MsgConnection(a,1,,1,1,1);

        topicSender.sendMsgToUser(msgConnection);
    }*/


/*    @Test
    public void testCRUD3(){

        System.out.println(foodsService.selectAll(1).getData());
    }*/

    @Autowired
    private HelloSender helloSender;

    /**
     * 单生产者-单个消费者
     */




//    @Test
//    public void addStore(){
//        storeDAO.addStore("lisi","10010","111111111");
//    }
/*
    @Test
    public void testCRUD() {
        Assert.assertEquals(1, adminDAO.checkUsername("13888888888"));
    }
    @Test
    public void testCRUD2() {Assert.assertEquals(1,storeDAO.selectByPhone("10086"));}

    @Test
    public void test() {Assert.assertEquals(1,storeDAO.selectByPhone("10086"));}*/
}
