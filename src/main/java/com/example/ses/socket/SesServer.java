package com.example.ses.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
@Component
public class SesServer {

    private static ServerSocket server;
    private static Socket socket;
    private static ObjectInputStream inputStream;
    private static ObjectOutputStream outputStream;

    @Value("${app.port}")
    private int port;

    @EventListener(ApplicationReadyEvent.class)
    public void startServer() throws Exception {
        log.info("port {}", port);
        server = new ServerSocket(port);
        connectClient();
    }

    private void connectClient() throws Exception {
        log.info("Waiting for the client");
        socket = server.accept();
        inputStream = new ObjectInputStream(socket.getInputStream());
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        log.info("Connected");
        // read login message
//        String message = (String) inputStream.readObject();
//        log.info("Message Received: " + message);
    }

    public static void sendRequest(int messageNumberPerSecond, int times) throws Exception {
        log.info("Start send messageNumberPerSecond {} times {}", messageNumberPerSecond, times);
        for (int i = 0; i < times; i++) {
            long start = System.currentTimeMillis();
            for (int j = 0; j < messageNumberPerSecond; j++) {
                outputStream.writeObject(generateMessage(j));
            }
            long delay = 1000 - (System.currentTimeMillis() - start);
            if (delay > 0) {
                Thread.sleep(delay);
            }
        }
        log.info("Send done");
    }

    private static String generateMessage(int i) {
        return "hello " + i;
    }
}
