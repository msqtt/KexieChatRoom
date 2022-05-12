package com.example.websocket.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONChange {

    public static Object jsonToObj (String json, Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return obj = mapper.readValue(json, obj.getClass());
    }

   public static String objToJson (Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
   }
}
