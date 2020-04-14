import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
 
public class RabbitMQConnection {
  
  public static Connection getConnection(){
    Connection conn = null;
    try{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("localhost");
        factory.setPassword("123456");
        //factory.setVirtualHost("");
        factory.setHost("localhost");
        //factory.setPort(5672);
        conn = factory.newConnection();
    }catch(Exception e){
        e.printStackTrace();
    }
    return conn;
  }
 
}
