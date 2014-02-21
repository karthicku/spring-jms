package com.test.spring.listener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author perficient
 * MQ message listener class. It receives messages from the mq queue in async fashion.
 *
 */
public class UserMessageListener implements MessageListener{

	 private static final Logger LOG = LoggerFactory.getLogger(UserMessageListener.class);

	    public void onMessage(Message message) {
	        try {
	            LOG.info("Received message: {}", ((TextMessage)message).getText());
	        } catch (JMSException e) {
	            LOG.error(e.getMessage(), e);
	        }
	    }
}
