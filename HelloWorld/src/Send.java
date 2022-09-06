import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;


//Configura la classe 
public class Send {

    

    public static void main(String[] argv) throws Exception {
    	
    	/*
    	 * creiamo una connessione al server
    	 * cosi facendo ci colleghiamo a un nodo RabbitMQ sulla macchina locale, cioè localhost.
    	 *  Se volessimo connetterci a un nodo su una macchina diversa, 
    	 *  dovremmo semplicemente specificare il suo nome host o indirizzo IP
        */
    	ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Nomi.Localhost);
        
        /*
         * possiamo usare un'istruzione try-with-resources perché sia ​​Connection che Channel
         *  implementano java.io.Closeable . 
         * In questo modo non abbiamo bisogno di chiuderli esplicitamente nel nostro codice.
         */
        
        try (Connection connection = factory.newConnection();
        		
        //creiamo un canale, che è dove risiede la maggior parte dell'API per fare le cose.
        	 Channel channel = connection.createChannel()) {
        	
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
            String message = "Hello World!";
            
            /**
             * exchange name = "" exchange di default 
             * routing key = Nomi.coda_base
             * message properties = null
             * message body bytes è il vettore di byter che rappresenta il messaggio inviato
             */
            channel.basicPublish("", Nomi.CODA_BASE, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
            
            //N.B. Dichiarare una coda è idempotente: verrà creata solo se non esiste già. 
            
        }
    }
}

