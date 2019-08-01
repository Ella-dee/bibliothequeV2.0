package com.mbooks.microservicebooks.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookListSerializer extends StdSerializer<List<Book>> {

    public BookListSerializer() {
        this(null);
    }

    public BookListSerializer(Class<List<Book>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<Book> books,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        List<Integer> ids = new ArrayList<>();
        for (Book book : books) {
            ids.add(book.getId());
        }
        generator.writeObject(ids);
    }
}
