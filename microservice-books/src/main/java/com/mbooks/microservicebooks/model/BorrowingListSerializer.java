package com.mbooks.microservicebooks.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BorrowingListSerializer extends StdSerializer<List<Borrowing>> {

    public BorrowingListSerializer() {
        this(null);
    }

    public BorrowingListSerializer(Class<List<Borrowing>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<Borrowing> borrowings,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        List<Integer> ids = new ArrayList<>();
        for (Borrowing borrowing : borrowings) {
            ids.add(borrowing.getId());
        }
        generator.writeObject(ids);
    }
}
