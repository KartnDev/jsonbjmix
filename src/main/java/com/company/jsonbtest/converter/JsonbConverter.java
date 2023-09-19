package com.company.jsonbtest.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.postgresql.util.PGobject;

@Converter(autoApply = true)
public class JsonbConverter implements AttributeConverter<JsonB, PGobject> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public PGobject convertToDatabaseColumn(JsonB attribute) {
        PGobject result = new PGobject();
        String jsonbResult;
        result.setType("jsonb");
        try {
            jsonbResult = objectMapper.writeValueAsString(attribute);
            result.setValue(jsonbResult);
        } catch (Exception e) {
        }
        return result;
    }

    @Override
    public JsonB convertToEntityAttribute(PGobject dbData) {
        if(dbData == null || !dbData.getType().equals("jsonb") || dbData.getValue() == null){
            return new JsonB();
        }
        try {
            return objectMapper.readValue(dbData.getValue(), JsonB.class);
        } catch (JsonProcessingException e) {
            return new JsonB();
        }
    }

//    @Override
//    public String convertToDatabaseColumn(JsonB object) {
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        try {
//            objectMapper.writeValue(out, object);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return out.toByteArray();
//    }
//
//    @Override
//    public JsonB convertToEntityAttribute(byte[] data) {
//        if(data == null) {
//            return new JsonB();
//        }
//        try {
//            return objectMapper.readValue(data, JsonB.class);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
