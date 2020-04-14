import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Corretora {
	  private static final String broker = "BROKER";
	  private static final String bolsadevalores = "BOLSADEVALORES";

	  public static String publish(String acao,int quant, double val,String operacao) {
		  	String message = null;
	        ConnectionFactory factory = new ConnectionFactory();
	        factory.setHost("localhost");
	        //factory.setUsername("localhost");
	        //factory.setPassword("123456");
	        factory.setPort(5672);
	        try (Connection connection = factory.newConnection();
	             Channel channel = connection.createChannel()) {

	            channel.exchangeDeclare(broker, "topic");

	            String routingKey = operacao + "." + acao;
	            message = "' Quantidade: " + quant + ", Valor: " + val + "'";

	            channel.basicPublish(broker, routingKey, null, message.getBytes("UTF-8"));
	            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
	        } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return message;
	  }
	    public static void subscribe(String[] argv) throws Exception {
	        ConnectionFactory factory = new ConnectionFactory();
	        factory.setHost("localhost");
	        //factory.setUsername("localhost");
	        //factory.setPassword("123456");
	        factory.setPort(5672);
	        Connection connection = factory.newConnection();
	        Channel channel = connection.createChannel();

	        channel.exchangeDeclare(bolsadevalores, "topic");
	        String queueName = channel.queueDeclare().getQueue();
	        if (argv.length < 1) {
	            System.err.println("Usage: ReceiveLogsTopic [binding_key]...");
	            System.exit(1);
	        }

	        for (String bindingKey : argv) {
	            channel.queueBind(queueName, bolsadevalores, bindingKey);
	        }

	        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
	        Consumer consumer = new DefaultConsumer(channel) {
	            @Override
	            public void handleDelivery(String consumerTag, Envelope envelope,
	                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
	              String message = new String(body, "UTF-8");
	              
	              System.out.println(" [x] Received '"+ envelope.getRoutingKey() + ":" + message + "'");
	              
	            }
	          };
	          channel.basicConsume(queueName, true, consumer);
	    }
}
