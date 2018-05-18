package cn.dfx.activeMQ_demo.topic;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 消息订阅 消息接收方的订阅者01的监听器
 * @author Administrator
 *
 */
public class Listener01 implements MessageListener {

	@Override
	public void onMessage(Message message) {
		
		try{
			System.out.println("订阅者01接收到的消息为："+((TextMessage) message).getText());
		}catch(JMSException e){
			e.printStackTrace();
		}
		
	}

}
