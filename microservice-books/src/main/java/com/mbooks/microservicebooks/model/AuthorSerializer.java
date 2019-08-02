package com.mbooks.microservicebooks.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.List;

public class AuthorSerializer extends StdSerializer<Author> {
    public AuthorSerializer() {
        this(null);
    }

    public AuthorSerializer(Class<Author> t) {
        super(t);
    }

    @Override
    public void serialize(
            Author value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeStringField("firstName", value.getFirstName());
        jgen.writeStringField("lastName", value.getLastName());
        jgen.writeStringField("nationality", value.getNationality());
        jgen.writeStringField("birthDate", value.getBirthDate());
        //Iterate List
        jgen.writeArrayFieldStart("books");
        for(Book book: value.getBooks()) {
            jgen.writeStartObject();
            jgen.writeNumberField("id", book.getId());
            jgen.writeStringField("title", book.getTitle());
            jgen.writeStringField("bookCover", book.getBookCover());
            jgen.writeEndObject();
        }
        jgen.writeEndArray();

        jgen.writeEndObject();
    }

}
