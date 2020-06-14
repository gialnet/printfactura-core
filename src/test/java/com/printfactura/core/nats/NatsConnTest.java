package com.printfactura.core.nats;

import io.nats.client.Connection;
import io.nats.client.Message;
import io.nats.client.Nats;
import io.nats.client.Subscription;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

public class NatsConnTest {



    @Test
    public void ConnTest() throws IOException, InterruptedException, TimeoutException {

        Connection nc = Nats.connect();

        nc.publish("updates", "All is Well1".getBytes(StandardCharsets.UTF_8));
        nc.publish("updates", "All is Well2".getBytes(StandardCharsets.UTF_8));
        nc.publish("updates", "All is Well3".getBytes(StandardCharsets.UTF_8));
        nc.publish("updates", "All is Well4".getBytes(StandardCharsets.UTF_8));

        // Make sure the message goes through before we close
        nc.flush(Duration.ZERO);
        nc.close();
    }

    @Test
    public void ReadTest() throws IOException, InterruptedException {

        Connection nc = Nats.connect();
        // Subscribe
        Subscription sub = nc.subscribe("updates");
        System.out.println(sub.getQueueName());
        System.out.println(nc.getStatus().toString());

        // Read a message
        Message msg = sub.nextMessage(Duration.ZERO);

        String str = new String(msg.getData(), StandardCharsets.UTF_8);
        System.out.println(str);

        // Close the connection
        nc.close();
    }
}
