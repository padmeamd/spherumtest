package com.example.demo.util;

import com.example.demo.entity.dao.DataDAO;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class InputDeserializer implements Deserializer<DataDAO> {

    private final Gson gson;

    @Autowired
    public InputDeserializer(Gson gson) {
        this.gson = gson;
    }

    @Override
    public DataDAO deserialize(String json) {
        try {
            return gson.fromJson(json, DataDAO.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
