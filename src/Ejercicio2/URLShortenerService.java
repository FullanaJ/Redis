package Ejercicio2;

import redis.clients.jedis.Jedis;
import java.security.SecureRandom;
import java.util.Base64;

public class URLShortenerService {
    private static final String URLS_TO_SHORT_KEY = "DAVID:URLS_TO_SHORT";
    private static final String SHORTED_URLS_KEY = "DAVID:SHORTED_URLS";
    private static final int SHORT_KEY_LENGTH = 8;

    public static void main(String[] args) {
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            SecureRandom random = new SecureRandom();

            while (true) {
                // Verificar si hay URLs pendientes por acortar
                String url = jedis.lpop(URLS_TO_SHORT_KEY);

                if (url != null) {
                    // Generar una cadena aleatoria para la clave corta
                    byte[] randomBytes = new byte[SHORT_KEY_LENGTH];
                    random.nextBytes(randomBytes);
                    String shortKey = Base64.getUrlEncoder().encodeToString(randomBytes)
                            .substring(0, SHORT_KEY_LENGTH)
                            .replace("_", "A")
                            .replace("-", "B"); // Reemplazar caracteres no válidos

                    // Almacenar la URL original en la tabla HASH
                    jedis.hset(SHORTED_URLS_KEY, shortKey, url);
                    System.out.println("URL original: " + url);
                }

                // Agregar un pequeño retraso para no sobrecargar Redis
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

