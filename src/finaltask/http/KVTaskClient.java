package finaltask.http;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.net.HttpURLConnection.*;

public class KVTaskClient {
    private String address;
    private int port;
    private HttpClient client;
    private String apiToken;

    KVTaskClient (String address, int port) {
        this.address = address;
        this.port = port;
        client = HttpClient.newHttpClient();
        apiToken = register();
    }

    public void put(String key, String json) {
        try {
            if (apiToken.isEmpty()) {
                System.out.println("Некорректно переданы данные.");
                return;
            } else if (key.isEmpty() || json.isEmpty()) {
                System.out.println("Пустые ключ или тело запроса.");
                return;
            }
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .uri(URI.create("http://" + address + ":" + port + "/save/" + key + "?" + "API_TOKEN=" + apiToken))
                    .version(HttpClient.Version.HTTP_1_1)
                    .header("Accept", "application/json")
                    .build();
            HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
            HttpResponse<String> response = client.send(request, handler);
            System.out.println("Код ответа: " + response.statusCode());
        } catch (Exception exception) {
            System.out.println("Ошибка создания запроса!");
            exception.printStackTrace();
        }
    }

    public String load(String key) {
        try {
            if (apiToken.isEmpty()) {
                return "";
            }
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("http://" + address + ":" + port + "/load/" + key + "?" + "API_TOKEN=" + apiToken))
                    .version(HttpClient.Version.HTTP_1_1)
                    .header("Accept", "application/json")
                    .build();
            HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
            HttpResponse<String> response = client.send(request, handler);
            if (response.statusCode() == HTTP_OK) {
                return response.body();
            } else {
                System.out.println("Ошибка при попытке получить тело ответа. Код ответа: " + response.statusCode());
            }
            return response.body();
        } catch (Exception exception) {
            System.out.println("Ошибка создания запроса!");
            exception.printStackTrace();
        }
        return "";
    }

    private String register() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("http://" + address + ":" + port + "/register"))
                    .version(HttpClient.Version.HTTP_1_1)
                    .header("Accept", "application/json")
                    .build();
            HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
            HttpResponse<String> response = client.send(request, handler);
            if (response.statusCode() == HTTP_OK) {
                return response.body();
            } else {
                System.out.println("Ошибка при попытке получить тело ответа. Код ответа: " + response.statusCode());
            }
        } catch (Exception exception) {
            System.out.println("Ошибка создания запроса!");
            exception.printStackTrace();
        }
        return "";
    }

}
