package com.mbooks.microservicebooks.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class BorrowingTypeSerializer extends StdSerializer<BorrowingType> {
    public BorrowingTypeSerializer() {
        this(null);
    }

    public BorrowingTypeSerializer(Class<BorrowingType> t) {
        super(t);
    }

    @Override
    public void serialize(
            BorrowingType value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeStringField("type", value.getType());
        //Iterate List
        jgen.writeArrayFieldStart("borrowingList");

        for(Borrowing borrowing: value.getBorrowingList()) {
            jgen.writeStartObject();
            jgen.writeNumberField("id", borrowing.getId());
            jgen.writeStringField("borrowed", borrowing.getBorrowed());
            if(borrowing.getReturned() != null) {
                jgen.writeStringField("returned", borrowing.getReturned());
            }
            jgen.writeBooleanField("reminderMail", borrowing.getReminderMail());
            jgen.writeEndObject();
        }
        jgen.writeEndArray();

        jgen.writeEndObject();
    }

}
