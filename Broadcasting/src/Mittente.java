 import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Mittente {

	   public static void main(String[] argv) throws Exception {
	    		  
		  ConnectionFactory factory = new ConnectionFactory();
		    factory.setHost(Nomibroadcasting.Localhost);
		    try (Connection connection = factory.newConnection();
		      Channel channel = connection.createChannel()) {
		      	// Andiamo ad usare un Fanout Exchange in modo che i messaggi 
		       	//vengano mandati a tutte le code 
		        	
		       	channel.exchangeDeclare(Nomibroadcasting.Exchangename, "fanout", true);
		        
		      	//una volta creato un Fanout Exchange che sia durevole, pubblichiamo una serie di messaggi
		          
		         for (int i = 0; i < 15; i++) {
		           String message = "Secondo esempio Messaggio " + i;
		            //nel basicpublish andiamo a definire (exchangename, routingKey, proprieta, messaggio)
		            channel.basicPublish(Nomibroadcasting.Exchangename, "", null, message.getBytes("UTF-8"));
		           System.out.println(" Spedito " + "'" + message + "'");     
		              }  	
		        }
	   }
}
