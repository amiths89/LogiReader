package org.java.bin;

public class Message {

    private final int payloadSize;
    private final int sequenceNumber;
    private final String payload;

    public Message(int messageSize, int sequenceNumber, String payload) {
        this.payloadSize = messageSize;
        this.sequenceNumber = sequenceNumber;
        this.payload = payload;
    }

    public int getPayloadSize() {
        return payloadSize;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public String getPayload() {
        return payload;
    }
}
