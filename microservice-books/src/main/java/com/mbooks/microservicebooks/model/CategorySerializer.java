package com.mbooks.microservicebooks.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CategorySerializer extends StdSerializer<Category> {
    public CategorySerializer() {
        this(null);
    }

    public CategorySerializer(Class<Category> t) {
        super(t);
    }

    @Override
    public void serialize(
            Category value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeStringField("name", value.getName());
        //Iterate List
        jgen.writeArrayFieldStart("books");
        for(Book book: value.getBooks()) {
            jgen.writeStartObject();
            jgen.writeNumberField("id", book.getId());
            jgen.writeStringField("title", book.getTitle());
            jgen.writeStringField("ref", book.getRef());
            jgen.writeStringField("bookCover", book.getBookCover());
            jgen.writeEndObject();
        }


        jgen.writeEndArray();

        jgen.writeEndObject();
    }

}
