package cn.dfx.activeMQ_demo.topic;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 订阅消息的消息接收方01
 * @author Administrator
 *
 */
public class MQConsumer01 {
	
	public static void main(String[] args) throws IOException{
		
		/**
		 * 1.创建连接工厂
		 * 2.创建连接实例
		 * 3、启动连接
		 * 4、  创建接收或发送的线程实例（消费者就不需要开启事务了）
		 * 5、创建队列（消息发送的目的地）
		 * 6、创建消息接收者
		 * 7、注册消息监听
		 */
		
		ConnectionFactory connFactory = null;
		Connection conn = null;
		Session session = null;
		Destination destination = null;
		
		//创建连接工厂需要的参数
		String name  = "system";
		String password = "manager";
		String url = "failover://tcp://localhost:61616";
		
		try{
			
			//创建连接工厂
			connFactory = new ActiveMQConnectionFactory(name,password,url);
			
			//创建连接实例
			conn = connFactory.createConnection();
			
			//启动连接
			conn.start();
			
			//创建session（创建接收或发送的线程实例（消费者就不需要开启事务了））
			session = conn.createSession(true, Session.AUTO_ACKNOWLEDGE);
			
			//创建消息目的地（消费者从这里读取消息）
			destination = session.createTopic("FirstTopic");
			
			//创建消费者
			MessageConsumer messageConsumer = session.createConsumer(destination);
			
			//消费者读取消息，监听消息
			messageConsumer.setMessageListener(new Listener01());
			
			System.out.println("订阅者01已经准备好接收消息！");
			
//			//8、程序等待接收用户消息
//	        System.in.read();
//	        //9、关闭资源
//	        messageConsumer.close();
//	        session.close();
//	        conn.close();
	        
		}catch(JMSException e){
			e.printStackTrace();
		}
	}
}
