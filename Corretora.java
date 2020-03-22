import com.rabbitmq.client.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Corretora extends Planilha{
  private static final String EXCHANGE_NAME = "BROKER";

  public static String Corretora(String acao, int quant, double val, String operacao) throws IOException {
    Connection connection = RabbitMQConnection.getConnection();
    Channel channel = connection.createChannel();
    String queueName = channel.queueDeclare().getQueue();
    channel.queueBind(queueName, EXCHANGE_NAME, "");
    channel.basicPublish(EXCHANGE_NAME, "", null, (quant + ","+ val + "," + acao + "," + operacao).getBytes("UTF-8"));
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
    String message = null;
    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope,
                                 AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        String[] messageSplit = message.split(",");
        
        System.out.println(" [x] Received '" + "A " + messageSplit[2] + " recebeu uma ordem de " + messageSplit[3]+ ": \n Quantidade: " + messageSplit[0] + "\n Valor: " + messageSplit[1] + "'");
        
      }
    };
    channel.basicConsume(queueName, true, consumer);
    return message;
  }
     
}

