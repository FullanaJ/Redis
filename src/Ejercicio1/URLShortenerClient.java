package Ejercicio1;

import redis.clients.jedis.Jedis;

import java.util.Scanner;

public class URLShortenerClient {

    public static void main(String[] args) {
        try (Jedis jedis = new Jedis("localhost",6379)) { // Conexión a Redis
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Ingrese uno de los siguientes comandos: 'shorten URL', 'url SHORTEDURL', 'exit'");
                String input = scanner.nextLine();

                if (input.startsWith("shorten")) {
                    new ServidorURL().shortenURL(input, jedis);
                } else if (input.startsWith("url")) {
                    new ServidorShorten().shortURL(input, jedis);
                } else if (input.equals("exit")) {
                    break;
                } else {
                    System.out.println("Comando no reconocido.");
                }
            }
        }
    }
}
