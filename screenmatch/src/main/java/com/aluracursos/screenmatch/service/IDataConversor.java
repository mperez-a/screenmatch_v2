package com.aluracursos.screenmatch.service;

public interface IDataConversor {
    <T> T getData(String json, Class<T> tClass);
}
