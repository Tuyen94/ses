package com.example.ses.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SesServer {

    private static ServerSocket server;
    private static Socket socket;
    private static ObjectInputStream inputStream;
    private static ObjectOutputStream outputStream;
    private static List<String> messageList;

    @Value("${app.port}")
    private int port;

    @EventListener(ApplicationReadyEvent.class)
    public void startServer() throws Exception {
        log.info("port {}", port);
        readTestData();
        connectClient();
    }

    private void readTestData() throws IOException {
        Resource resource = new ClassPathResource("test.data");
        InputStream inputStream = resource.getInputStream();
        try ( BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)) ) {
            messageList = reader.lines().collect(Collectors.toList());
            System.out.println(messageList.size());
            System.out.println(messageList);
        }
    }

    private void connectClient() throws Exception {
        log.info("Waiting for the client");
        server = new ServerSocket(port);
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

    private static String generateMessage(int index) {
        return messageList.get(index % messageList.size());
    }
}
