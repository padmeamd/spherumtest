package com.example.demo.util;

public interface Deserializer<T> {
    T deserialize(String json);
}
