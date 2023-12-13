package Ejercicio1;

import redis.clients.jedis.Jedis;

public class ServidorURL {
    private static final String URLS_TO_SHORT_KEY = "DAVID:URLS_TO_SHORT";

    public void shortenURL(String s, Jedis jedis) {
        String[] parts = s.split(" ");
        if (parts.length != 2) {
            System.out.println("Formato incorrecto. Uso: shorten URL");
        }else {
            String url = parts[1];
            System.out.println("Agrega url: " + url);
            jedis.rpush(URLS_TO_SHORT_KEY, url); // Crea la clave como una lista y agrega la URL
        }
    }
}
