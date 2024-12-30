package com.literatura.desafioliteratura.proyecto_literatura_alura.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
