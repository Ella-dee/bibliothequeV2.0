package com.mmailing.microservicemailing.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MailSentListSerializer extends StdSerializer<List<MailSent>> {
    public MailSentListSerializer() {
        this(null);
    }

    public MailSentListSerializer(Class<List<MailSent>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<MailSent> mailSentList,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        List<Integer> ids = new ArrayList<>();
        for (MailSent mailSent : mailSentList) {
            ids.add(mailSent.getId());
        }
        generator.writeObject(ids);
    }

}
