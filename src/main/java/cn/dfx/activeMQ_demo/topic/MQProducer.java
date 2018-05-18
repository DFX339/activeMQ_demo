package cn.dfx.activeMQ_demo.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 订阅消息的发送消息方
 * @author Administrator
 *
 */
public class MQProducer {
	
	public static void main(String[] args){
		
		/**
		 * 1.创建连接工厂
		 * 2.创建连接实例
		 * 3、启动连接
		 * 4、创建session创建接收或发送的线程实例（创建session的时候定义是否要启用事务，
		 * 且事务类型是Auto_ACKNOWLEDGE也就是消费者成功在Listern中获得消息返回时，会话自动确定用户收到消息）
		 * 5、创建队列（消息发送的目的地）
		 * 6、创建消息发送者
		 * 7、创建消息
		 * 8、发送消息
		 * 9、session.commit();提交千万不要忘记了 
		 */
		
		ConnectionFactory connFactory = null;
		Connection conn = null;
		Session session = null;
		Destination destination = null;
		
		//连接参数定义： 用户名 密码 url 
		String name = "system";
		String password = "manager";
		String url = "failover://tcp://localhost:61616";
		
		System.out.println("消息发布者开始发布消息了……");
		try{
			
			//创建连接工厂
			//这里的连接参数可以使用常量：Constants.MQ_NAME, Constants.MQ_PASSWORD, Constants.MQ_BROKETURL
			connFactory = new ActiveMQConnectionFactory(name,password,url);
			
			//通过连接工厂创建连接实例
			conn = connFactory.createConnection();
			
			//启动连接
			conn.start();
			
			//4、创建session创建接收或发送的线程实例（创建session的时候定义是否要启用事务，且事务类型是Auto_ACKNOWLEDGE也就是消费者成功在Listern中获得消息返回时，会话自动确定用户收到消息）
			//也可以使用：session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			session = conn.createSession(true, Session.AUTO_ACKNOWLEDGE);
			
			//创建队列，也就是消息发送的目的地
			destination = session.createTopic("FirstTopic");
			
			//创建消息发布者
			MessageProducer  messageProducer = session.createProducer(destination);
			
			//创建需要发送的消息
			TextMessage textMessage = session.createTextMessage();
			textMessage.setText("Hello，broadcast message NO.1!");
			
			//发送消息
			messageProducer.send(textMessage);
			
			//一定要记得这个，提交呀，
			session.commit();
			
			System.out.println("消息发布者："+textMessage.getText());
			
		}catch(JMSException e){
			 e.printStackTrace();
        } finally {
        	
        	//关闭连接
            if (conn != null) {
                try {
                    conn.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }	
            }
		
		}
	
	}
}
