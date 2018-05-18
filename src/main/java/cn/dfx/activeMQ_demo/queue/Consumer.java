package cn.dfx.activeMQ_demo.queue;

import javax.jms.Session;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
public class Consumer {
	
	public static void main(String[] args) {
		System.out.println("进入消费者--");
        //连接信息设置
        String username = "system";
        String password = "manager";
        String brokerURL =  "failover://tcp://localhost:61616";
        //默认的路径：ActiveMQConnection.DEFAULT_BROKER_URL;
        //连接工厂
        ConnectionFactory connectionFactory = null;
        //连接
        Connection connection = null;
        //会话 接受或者发送消息的线程
        Session session = null;
        //消息的目的地
        Destination destination = null;
        //消息消费者
        MessageConsumer messageConsumer = null;
        //实例化连接工厂
        connectionFactory = new ActiveMQConnectionFactory(username, password, brokerURL);

        try {
  		    System.out.println("消费者开始建立连接-----");
            //通过连接工厂获取连接
            connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            System.out.println("消费者开始启动连接-----");
            //创建session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //创建一个连接QueueTest的消息队列
            destination = session.createQueue("QueueTest");
            //创建消息消费者
            messageConsumer = session.createConsumer(destination);
            while (true) {
                TextMessage textMessage = (TextMessage) messageConsumer.receive(100000);
                if(textMessage != null){
                    System.out.println("成功接收消息:" + textMessage.getText());
                }else {
                    break;
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
	}
}
