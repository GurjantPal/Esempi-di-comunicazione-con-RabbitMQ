import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Destinatario {
	
    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Nomibroadcasting.Localhost);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //dichiariamo l'exchange 
        channel.exchangeDeclare(Nomibroadcasting.Exchangename, "fanout", true);
        //creiamo una coda temporanea 
        String queueName = channel.queueDeclare().getQueue();
        // per creare il binding mettiamo la routing key a "" cioè null
        channel.queueBind(queueName, Nomibroadcasting.Exchangename, "");

        System.out.println(" in attesa di messaggi ");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" Consumato '" +delivery.getEnvelope()+ "':'" + message + "'");
        };
        
        // nel caso il consumatore venga cancellato questo callback ce lo farà sapere
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println(consumerTag);
          };
        channel.basicConsume(queueName, true, deliverCallback,cancelCallback);
    }
}
