package finaltask.http;

import static java.net.HttpURLConnection.*;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class KVServer {
    public static final int PORT = 8078;
    private final String apiToken;
    private final HttpServer server;
    private final Map<String, String> data = new HashMap<>();

    public KVServer() throws IOException {
        apiToken = generateApiToken();
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/register", this::register);
        server.createContext("/save", this::save);
        server.createContext("/load", this::load);
    }

    /** Тестовый метод main.
     * Запускаем сервер, создаём клиент и выполняем методы от имени клиента.
     * put() - отправляется POST запрос на создание новой записи.
     * load() - получение записи в текстовом виде по ключу.
     * register() - получение API токена от сервера (применяется автоматически)
     */
    public static void main(String[] args) throws IOException {
        new KVServer().start();
        KVTaskClient client = new KVTaskClient("localhost", 8078);
        client.put("testKey", "{\"Hello\":\"Reviewer\"}");
        System.out.println(client.load("testKey"));
    }

    private void load(HttpExchange h) {
        // TODO Добавьте получение значения по ключу
        try {
            System.out.println("\n/load");
            if (!hasAuth(h)) {
                System.out.println("Запрос неавторизован, нужен параметр в query API_TOKEN со значением апи-ключа");
                h.sendResponseHeaders(HTTP_FORBIDDEN, 0);
                return;
            }

            if (h.getRequestMethod().equals("GET")) {
                String key = h.getRequestURI().getPath().substring("/save/".length());
                if (key.isEmpty()) {
                    System.out.println("Key для запроса пустой. key указывается в пути: /load/{key}");
                    h.sendResponseHeaders(HTTP_NOT_FOUND, 0);
                } else if (!data.containsKey(key)) {
                    System.out.println("В базе данных отсутствует значение по переданному ключу");
                    h.sendResponseHeaders(HTTP_NOT_FOUND, 0);
                } else if (data.containsKey(key)) {
                    System.out.println("Значение для ключа " + key + " успешно найдено!");
                    sendText(h, data.get(key));
                }
            } else {
                System.out.println("/load ждёт GET-запрос, а получил: " + h.getRequestMethod());
                h.sendResponseHeaders(HTTP_BAD_METHOD, 0);
            }

        } catch (Exception exception) {
            System.out.println("Неизвестная ошибка!");
            exception.printStackTrace();
        } finally {
            h.close();
        }
    }

    private void save(HttpExchange h) throws IOException {
        try {
            System.out.println("\n/save");
            if (!hasAuth(h)) {
                System.out.println("Запрос неавторизован, нужен параметр в query API_TOKEN со значением апи-ключа");
                h.sendResponseHeaders(HTTP_FORBIDDEN, 0);
                return;
            }
            if ("POST".equals(h.getRequestMethod())) {
                String key = h.getRequestURI().getPath().substring("/save/".length());
                if (key.isEmpty()) {
                    System.out.println("Key для сохранения пустой. key указывается в пути: /save/{key}");
                    h.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
                    return;
                }
                String value = readText(h);
                if (value.isEmpty()) {
                    System.out.println("Value для сохранения пустой. value указывается в теле запроса");
                    h.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
                    return;
                }
                data.put(key, value);
                System.out.println("Значение для ключа " + key + " успешно обновлено!");
                h.sendResponseHeaders(HTTP_OK, 0);
            } else {
                System.out.println("/save ждёт POST-запрос, а получил: " + h.getRequestMethod());
                h.sendResponseHeaders(HTTP_BAD_METHOD, 0);
            }
        } finally {
            h.close();
        }
    }

    private void register(HttpExchange h) throws IOException {
        try {
            System.out.println("\n/register");
            if ("GET".equals(h.getRequestMethod())) {
                sendText(h, apiToken);
            } else {
                System.out.println("/register ждёт GET-запрос, а получил " + h.getRequestMethod());
                h.sendResponseHeaders(HTTP_BAD_METHOD, 0);
            }
        } finally {
            h.close();
        }
    }

    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        System.out.println("Открой в браузере http://localhost:" + PORT + "/");
        System.out.println("API_TOKEN: " + apiToken);
        server.start();
    }

    private String generateApiToken() {
        return "" + System.currentTimeMillis();
    }

    protected boolean hasAuth(HttpExchange h) {
        String rawQuery = h.getRequestURI().getRawQuery();
        return rawQuery != null && (rawQuery.contains("API_TOKEN=" + apiToken) || rawQuery.contains("API_TOKEN=DEBUG"));
    }

    protected String readText(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(), UTF_8);
    }

    protected void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/text");
        h.sendResponseHeaders(HTTP_OK, resp.length);
        h.getResponseBody().write(resp);
    }
}

