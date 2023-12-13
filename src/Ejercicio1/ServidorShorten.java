package Ejercicio1;

import redis.clients.jedis.Jedis;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ServidorShorten {

    private static final String SHORTED_URLS_KEY = "DAVID:SHORTED_URLS";
    private static final String URLS_TO_SHORT_KEY = "DAVID:URLS_TO_SHORT";

    public void shortURL(String s, Jedis jedis) {
        String[] parts = s.split(" ");
        if (parts.length != 2) {
            System.out.println("Formato incorrecto. Uso: url SHORTEDURL");
        }
        List<String> valores = jedis.lrange(URLS_TO_SHORT_KEY, 0, -1);
        for (String valor : valores) {
            try {
                URL url = new URL(valor);
                if (url.toString().equals(parts[1]))
                    System.out.println("URL acortada: " + url.getAuthority());
                jedis.hset(SHORTED_URLS_KEY, url.getAuthority(), url.toString()); // Agregar un campo-valor a un hash
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        jedis.ltrim("miLista", valores.size(), -1);
    }
}
