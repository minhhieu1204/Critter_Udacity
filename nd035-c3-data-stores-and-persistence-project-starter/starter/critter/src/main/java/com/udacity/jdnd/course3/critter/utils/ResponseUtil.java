package com.udacity.jdnd.course3.critter.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseUtil {

    public <T> ResponseEntity buildResult(T object) {
        String json = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            json = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
        return ResponseEntity.ok().body(json);
    }

    public <T> ResponseEntity buildError(String message) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("message",message);
            jsonObject.put("status", HttpStatus.BAD_REQUEST);
        } catch (JSONException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
        return ResponseEntity.badRequest().body(jsonObject.toString());
    }
}
