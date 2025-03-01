package com.redhat;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;


public class ArtemisMessageProduceMasterTesting {
    private static final String BROKER_URL = "tcp://amqpetlbla.csi.gnpaws.au.singtelgroup.net:61616"; // Replace with your broker URL
    private static final String QUEUE_NAME = "OMS2SDP-publishDeviceDetails";
    private static final String USERNAME = "svc_sdp_amq_pet";
    private static final String PASSWORD = "SguvJoN3r0uRodCm";

    public static void main(String[] args) {
        ActiveMQConnectionFactory connectionFactory = null;
        Connection connection = null;
        int messageCount = 0;
        // Create a connection factory
        while (true) {
            try {
                // Create a connection factory
                connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);

                // Establish a connection
                connection = connectionFactory.createConnection(USERNAME, PASSWORD);
                connection.start(); // Start the connection

                // Create a session
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                // Create a destination (queue)
                Destination destination = session.createQueue(QUEUE_NAME);

                // Create a message producer
                MessageProducer producer = session.createProducer(destination);

                // Create and send a message
                String messageContent = "Hello, Artemis! "+ ++messageCount;
                TextMessage message = session.createTextMessage(messageContent);

                producer.send(message);
                System.out.println("Message sent: " + messageContent);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

    }
}