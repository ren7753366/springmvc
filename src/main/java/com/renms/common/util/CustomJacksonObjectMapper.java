package com.renms.common.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;

public class CustomJacksonObjectMapper extends ObjectMapper {

    private static final long serialVersionUID = -8261548697068855204L;

    public CustomJacksonObjectMapper() {
        super();                
        DefaultSerializerProvider.Impl sp = new DefaultSerializerProvider.Impl();
        sp.setNullValueSerializer(new JacksonNullValueSerializer());
        this.setSerializerProvider(sp);
    }
    
}

class JacksonNullValueSerializer extends JsonSerializer<Object>{

    @Override
    public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeString("");
    }
    
}