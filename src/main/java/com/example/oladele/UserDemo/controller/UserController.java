package com.example.oladele.UserDemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController{

    private static final String FILE_PATH = "src/main/java/com/example/oladele/UserDemo/file/messages.txt";
    private  static final String LOG_PATH ="src/main/java/com/example/oladele/UserDemo/file/log.txt";

    @GetMapping("message")
    public List<String> getMessages() throws IOException {
        List<String> message = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))){
           String line = reader.readLine();

           while(line != null ){
               System.out.println(line);
               message.add(line);
               line = reader.readLine();
           }

        } catch (IOException e){
            System.out.println(e.getMessage());
        }

        return message;
    }

    @GetMapping("/messageCount")
    public int getMessageCount() {
        int messageCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            while (reader.readLine() != null) {
                messageCount++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return messageCount;
    }

    @PostMapping("/postMessage")
    public String postMessage(@RequestBody String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(message);
            writer.newLine();
            logActivity("new message created");

            return "Message posted successfully";
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "Failed to post message";
        }
    }

    private void logActivity(String activity) {
        try (BufferedWriter logWriter = new BufferedWriter(new FileWriter(LOG_PATH, true))) {
            LocalDateTime now = LocalDateTime.now();
            String formattedDateTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            logWriter.write(formattedDateTime + " - " + activity);
            logWriter.newLine();
        } catch (IOException e) {
            System.out.println("Failed to log activity: " + e.getMessage());
        }
    }
}
