import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.nio.charset.StandardCharsets;

public class Recv {

	//Configura la classe 
	


    public static void main(String[] argv) throws Exception {
    	
    	/*
    	 * creiamo una connessione al server
    	 * cosi facendo ci colleghiamo a un nodo RabbitMQ sulla macchina locale, cioè localhost.
    	 *  Se volessimo connetterci a un nodo su una macchina diversa, 
    	 *  dovremmo semplicemente specificare il suo nome host o indirizzo IP
        */
    	
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Nomi.Localhost);
        Connection connection = factory.newConnection();
        
        //creiamo un canale, che è dove risiede la maggior parte dell'API per fare le cose.
        
        Channel channel = connection.createChannel();

        //Per inviare, dobbiamo dichiarare una coda a cui inviare; 
    	//una volta fatto ciò potremo pubblicare un messaggio nella coda. 
    	/**
    	 *  queue name = QUEUE_NAME
			durability = false
			autodelete = false
			exclusive =false
			type = null
    	 */
        channel.queueDeclare(Nomi.CODA_BASE, false, false, false, null);
        
        
        
        
        System.out.println("attendo i messaggi");

      
        //deliverCallback permette di essere avvisati quando viene consegnato un messaggio
        
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
        };
        /**
         * nomecoda
         * autoack = true
         * deliverCallback
         * consumerTag
         */
        channel.basicConsume(Nomi.CODA_BASE, true, deliverCallback, consumerTag -> { });
        
        /*
         * Perché non utilizziamo un'istruzione try-with-resource per chiudere automaticamente il canale e la connessione? 
         * In questo modo faremmo semplicemente andare avanti il ​​programma, chiudere tutto ed uscire! 
         *  il nostro obbiettivo è che il processo rimanga attivo 
         *  mentre il consumatore ascolta in modo asincrono l'arrivo dei messaggi.
         */
    }
}