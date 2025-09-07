package org.java.bin;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;

public class Main {
    protected static final String RESOURCE_FILE_PATH = "logi.bin";
    protected static final String OUTPUT_HTML_FILE = "report.html";


    public static void main(String[] args) {

        List<Message> messageCollection = new ArrayList<>();
        boolean status = readMessage(messageCollection);

        for (Message msg: messageCollection) {
            System.out.println("Payload Size: " + msg.getPayloadSize());
            System.out.println("Sequence Number: " + msg.getSequenceNumber());
            System.out.println("Payload: " + msg.getPayload());
            System.out.println("---------------------------");
        }
        if(status==true){
            boolean reportGenerated = HtmlGenerator.generateHtmlReport(messageCollection, OUTPUT_HTML_FILE);
            if(reportGenerated==true){
                if (reportGenerated && Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                    try {
                        Desktop.getDesktop().open(new File(OUTPUT_HTML_FILE));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Opening the report in your default browser...");
                } else if (reportGenerated) {
                    System.out.println("Desktop is not supported. Please open '" + OUTPUT_HTML_FILE + "' manually.");
                }
            } else {
                System.out.println("Failed to generate HTML report.");
            }
        } else {
            System.out.println("No messages read from the binary file.");

        }

    }

    public static boolean readMessage(List<Message> messageCollection){
        try (InputStream fis = Main.class.getClassLoader().getResourceAsStream(RESOURCE_FILE_PATH)) {
            if (fis == null) {
                System.err.println("Error: Resource '" + RESOURCE_FILE_PATH + "' not found in classpath.");
                return false;
            }
            // A byte buffer to read the 4-byte integers (size and sequence number)
            byte[] intBuffer = new byte[4];

            while (fis.available() > 0) {

                // Read the first 4 bytes for the message size (payload header)
                int bytesReadForSize = fis.read(intBuffer, 0, 4);
                if (bytesReadForSize < 4) {
                    System.err.println("Incomplete header");
                    break;
                }

                int messageSize = ByteBuffer.wrap(intBuffer)
                        .order(ByteOrder.LITTLE_ENDIAN)
                        .getInt();

                // Read the next 4 bytes for the sequence number
                int bytesReadForSeq = fis.read(intBuffer, 0, 4);
                if (bytesReadForSeq < 4) {
                    System.err.println("Incomplete sequence number");
                    break;
                }

                int sequenceNumber = ByteBuffer.wrap(intBuffer)
                        .order(ByteOrder.LITTLE_ENDIAN)
                        .getInt();

                // Read the message string
                byte[] messageBuffer = new byte[messageSize];
                int bytesReadForMessage = fis.read(messageBuffer, 0, messageSize);
                if (bytesReadForMessage < messageSize) {
                    System.err.println("Incomplete message");
                    break;
                }

                String message = new String(messageBuffer, StandardCharsets.UTF_8);
                String[] lines = message.split("\\r?\\n");
                List<String> messageList = Arrays.asList(lines);
                for(String item : messageList){
                    Message msg = new Message(messageSize, sequenceNumber, item);
                    messageCollection.add(msg);
                }


            }




        } catch (IOException e) {
            System.err.println("Error occurred while reading the file: " + e.getMessage());
            e.printStackTrace();
        }
        if(messageCollection.size()>0){
            return true;
        } else {
            return false;
        }
    }
}