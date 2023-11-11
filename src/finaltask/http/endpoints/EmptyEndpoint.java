package finaltask.http.endpoints;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import finaltask.TaskManager;

import static java.net.HttpURLConnection.*;

public class EmptyEndpoint extends Endpoint implements HttpHandler {
    public EmptyEndpoint(Gson gson, TaskManager manager) {
        super(gson, manager);
    }

    @Override
    public void handle(HttpExchange exchange) {
        try {
            String query = exchange.getRequestURI().getQuery();
            String method = exchange.getRequestMethod();
            System.out.println("Обработка эндпоинта " + exchange.getRequestURI().getPath());

            if (method.equals("GET")) {
                if (query == null) {
                    System.out.println("Обработка метода GET, пустого эндпоинта");
                    String response = gson.toJson(manager.getPrioritizedTasks());
                    sendText(exchange, response);
                } else {
                    System.out.println("Неверно указан эндпоинт для GET: */tasks");
                    exchange.sendResponseHeaders(HTTP_NOT_FOUND, 0);
                }
            } else {
                System.out.println("Неизвестный метод! " + method);
                exchange.sendResponseHeaders(HTTP_BAD_METHOD, 0);
            }
        } catch (Exception exception) {
            System.out.println("Неизвестная ошибка!");
            exception.printStackTrace();
        } finally {
            exchange.close();
        }
    }
}
