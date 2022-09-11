import com.rabbitmq.client.Channel;
	import com.rabbitmq.client.Connection;
	import com.rabbitmq.client.ConnectionFactory;
	
public class ClientA {
	
	    public static void main(String[] argv) throws Exception {
	        ConnectionFactory factory = new ConnectionFactory();
	        
	        // dichiariamo il nostro localhost
	        factory.setHost("localhost");
	       
	        //instauriamo una connessione e creiamo il canale da utilizzare per la comunicazione
	        try (Connection connection = factory.newConnection();
	             Channel channel = connection.createChannel()) {
	        	//dichiariamo il nostro Topic Exchange
	            channel.exchangeDeclare(Nomitopic.NomeExchange, "topic");
	           //dichiariamo le nostre code 
	            channel.queueDeclare("Giochi", true, false, false, null);
	            channel.queueDeclare("Sports", true, false, false, null);
	            channel.queueDeclare("Storia", true, false, false, null);
	           
	            //Creiamo le associazioni  tramite (queue, exchange, routingKey) con routingKey diverso da null
	            channel.queueBind("Giochi", Nomitopic.NomeExchange, "Giochi.*");
	            channel.queueBind("Sports", Nomitopic.NomeExchange, "#.Sports.*");
	            channel.queueBind("Storia", Nomitopic.NomeExchange, "#.Storia");
	           //andiamo a pubblicare i messaggi indirizzandoli verso le code desiderate
	            String message = "Gioca piu che puoi!";
	            channel.basicPublish(Nomitopic.NomeExchange, "Giochi.Storia", null, message.getBytes());
	            System.out.println(" Spedito " + "'" + message + "'");  
	            
	            message = "Napoleone era nano";
	            channel.basicPublish(Nomitopic.NomeExchange, "Storia", null, message.getBytes());
	            System.out.println(" Spedito " + "'" + message + "'");  
	            
	            message = "Allenati!!";
	            channel.basicPublish(Nomitopic.NomeExchange, "Storia.Sports.Giochi", null, message.getBytes());
	            System.out.println(" Spedito " + "'" + message + "'");  
	      
	        }
	    }
}
	  
