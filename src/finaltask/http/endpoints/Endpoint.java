package finaltask.http.endpoints;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import finaltask.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static java.net.HttpURLConnection.*;

public abstract class Endpoint {
    public Gson gson;
    public TaskManager manager;

    Endpoint(Gson gson, TaskManager manager) {
        this.gson = gson;
        this.manager = manager;
    }

    protected void sendText(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.getResponseHeaders().add("Content-Type", "application/json");
        byte[] bytes = response.getBytes(Charset.defaultCharset());
        httpExchange.sendResponseHeaders(HTTP_OK, bytes.length);
        httpExchange.getResponseBody().write(bytes);
    }

    public String convertRequestBody(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        return new String(inputStream.readAllBytes(), Charset.defaultCharset());
    }

}
