import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class BolsaValores {

  private static final String EXCHANGE_NAME = "BOLSADEeVALORES";

  public static void main(String[] argv) throws Exception {
   // ConnectionFactory factory = new ConnectionFactory();
   // factory.setHost("localhost");
   // Connection connection = factory.newConnection();
	Connection connection = RabbitMQConnection.getConnection();
    Channel channel = connection.createChannel();

    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

    String message = new String(compra(argv));
    String routing = new String(getRouting(argv));
    String queueName = channel.queueDeclare().getQueue();
    //channel.basicPublish(TopicExchange.EXCHANGE_NAME, TopicExchange.ROUTING_KEY_1, null,  (quant + ","+ val + "," + acao).getBytes("UTF-8"));
    channel.queueBind(queueName, EXCHANGE_NAME, routing);
    channel.basicPublish(EXCHANGE_NAME, routing, null, message.getBytes("UTF-8"));
    
    System.out.println(" [x] Sent '" + message + "'" + routing);

    channel.close();
    connection.close();
  }
  private static String getRouting(String[] strings) {
      if (strings.length < 1)
          return "anonymous.info";
      return strings[0];
  }
  private static String getMessage(String[] strings){
    if (strings.length < 1)
    	    return "info: Hello World!";
    return joinStrings(strings, " ");
  }
  
  private static String compra(String[] compra) {
	  return "Recebeu uma ordem de compra"; 
  }

  private static String joinStrings(String[] strings, String delimiter) {
    int length = strings.length;
    if (length == 0) return "";
    StringBuilder words = new StringBuilder(strings[0]);
    for (int i = 1; i < length; i++) {
        words.append(delimiter).append(strings[i]);
    }
    return words.toString();
  }
  
 
}

