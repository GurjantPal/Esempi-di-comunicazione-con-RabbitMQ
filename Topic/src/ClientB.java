import com.rabbitmq.client.Channel;
	import com.rabbitmq.client.Connection;
	import com.rabbitmq.client.ConnectionFactory;
	import com.rabbitmq.client.DeliverCallback;
	
public class ClientB {
	
    //instauriamo una connessione e creiamo il canale da utilizzare per la comunicazione
	    public static void main(String[] argv) throws Exception {
	        ConnectionFactory factory = new ConnectionFactory();
	        // dichiariamo il nostro localhost
	        factory.setHost("localhost");
	        Connection connection = factory.newConnection();
	        Channel channel = connection.createChannel();

	        //Avendo gia fatto l'associazione tra Exchange e code nella parte del Client A
	        //possiamo proseguire direttamente al consumo dei messaggi
	            
	        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
	            String message = new String(delivery.getBody(), "UTF-8");
	            
	            System.out.println("sono il consumatore "+ consumerTag+" e ho consumato: '" + "':'" + message + "'");
	        };
	        channel.basicConsume("Giochi", true, deliverCallback, consumerTag -> { });
	               
	        channel.basicConsume("Sports", true, deliverCallback, consumerTag -> { });
	        	       
	        channel.basicConsume("Storia", true, deliverCallback, consumerTag -> { });
	    }
	
}
