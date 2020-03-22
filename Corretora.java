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

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
    String queueName = channel.queueDeclare().getQueue();
    ArrayList livroOfertas = lerLivroOferta("C:/Users/karol/OneDrive/Documentos/DAMD/Ex11_RabbitMQ_PubSub/LivroDeOfertas.csv");
    String saida = "";
    saida = "<html>"
            + "<table border = '1'>"
            + "<tr><th>COD Ação</th><th>Quantidade</th><th>Valor</th><th>Operação</th></tr>";



    for (int i = 0; i < livroOfertas.size(); i++) {
    	String[] livroOfertasSplit = livroOfertas.get(i).toString().split(",");
    	saida = saida + "<tr><td>" + livroOfertasSplit[0]+ "</td><td>" + livroOfertasSplit[1] + "</td><td>" + livroOfertasSplit[2] + "</td><td>"+ livroOfertasSplit[3] + "</td></tr>";
    }

    saida = saida + "</table></html>";
   
    JOptionPane.showMessageDialog(null,saida);
	Object[] possibilities = run("C:/Users/karol/Downloads/1688438_acoes_bovespa.csv").toArray();
	Object[] operacaoList = {"Compra", "Venda"};
	int operacao = JOptionPane.showOptionDialog(null, "O que deseja fazer?", "Bolsa de Valores", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, operacaoList, null);
	String acao = (String)JOptionPane.showInputDialog(
            null,
            "Ações",
            "Bolsa de Valores",
            JOptionPane.PLAIN_MESSAGE,
            null,
            possibilities,
            possibilities[0]);
	int quant = Integer.parseInt((String) JOptionPane.showInputDialog(null,
	        "Digite a quantidade:",
	        "Bolsa de Valores", JOptionPane.INFORMATION_MESSAGE,
	        null,
	        null,
	        ""));
	double val = Double.parseDouble((String) JOptionPane.showInputDialog(null,
	        "Digite o valor:",
	        "Bolsa de Valores", JOptionPane.INFORMATION_MESSAGE,
	        null,
	        null,
	        ""));
	

	//int result = JOptionPane.showOptionDialog(null, panel, "Eleições", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options1, null);
	
    channel.queueBind(queueName, EXCHANGE_NAME, "");
    channel.basicPublish(EXCHANGE_NAME, "", null, (quant + ","+ val + "," + acao + "," + (operacao==1?"Venda":"Compra")).getBytes("UTF-8"));
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope,
                                 AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        String[] message2 = message.split(",");
        System.out.println(message);
        File file = new File("LivroDeOfertas.csv");
        FileWriter writer = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(writer);
        String teste = (message2[2] + "," + message2[0]+ "," + message2[1]+ "," +message2[3]);
        bw.write(teste);
        bw.newLine();
        bw.flush();
        bw.close();
        
     
        System.out.println(message2[3]);
        System.out.println(" [x] Received '" + "A " + message2[2] + " recebeu uma ordem de " + message2[3]+ ": \n Quantidade: " + message2[0] + "\n Valor: " + message2[1] + "'");
        ArrayList livroOfertas = lerLivroOferta("C:/Users/karol/OneDrive/Documentos/DAMD/Ex11_RabbitMQ_PubSub/LivroDeOfertas.csv");
        String saida = "";
        saida = "<html>"
                + "<table border = '1'>"
                + "<tr><th>COD Ação</th><th>Quantidade</th><th>Valor</th><th>Operação</th></tr>";



        for (int i = 0; i < livroOfertas.size(); i++) {
        	String[] livroOfertasSplit = livroOfertas.get(i).toString().split(",");
        	saida = saida + "<tr><td>" + livroOfertasSplit[0]+ "</td><td>" + livroOfertasSplit[1] + "</td><td>" + livroOfertasSplit[2] + "</td><td>"+ livroOfertasSplit[3] + "</td></tr>";
        }

        saida = saida + "</table></html>";
        JOptionPane.showMessageDialog(null,saida);
      }
    };
    channel.basicConsume(queueName, true, consumer);
  }
}

