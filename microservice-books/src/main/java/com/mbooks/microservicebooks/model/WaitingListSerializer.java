package com.mbooks.microservicebooks.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WaitingListSerializer extends StdSerializer<List<WaitingList>> {
    public WaitingListSerializer() {
        this(null);
    }

    public WaitingListSerializer(Class<List<WaitingList>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<WaitingList> waitingList,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        List<Integer> ids = new ArrayList<>();
        for (WaitingList list : waitingList) {
            ids.add(list.getId());
        }
        generator.writeObject(ids);
    }

}
