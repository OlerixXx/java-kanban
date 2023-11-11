package finaltask.http.endpoints;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import finaltask.TaskManager;
import finaltask.tasks.Subtask;

import static java.net.HttpURLConnection.*;

public class SubtaskEndpoint extends Endpoint implements HttpHandler {

    public SubtaskEndpoint(Gson gson, TaskManager manager) {
        super(gson, manager);
    }

    @Override
    public void handle(HttpExchange exchange) {

        try {
            String query = exchange.getRequestURI().getQuery();
            String method = exchange.getRequestMethod();
            String body = convertRequestBody(exchange);
            System.out.println("Обработка эндпоинта " + exchange.getRequestURI().getPath());

            switch (method) {
                case "GET": {
                    if (query == null) {
                        System.out.println("Обработка метода GET, пустого эндпоинта");
                        String response = gson.toJson(manager.getAllSubtasks());
                        sendText(exchange, response);
                    } else if (query != null) {
                        // не понимаю, почему тут показывает, что query всегда не равен null
                        System.out.println("Обработка метода GET, запроса " + query);
                        int id = Integer.parseInt(query.replaceFirst("id=", ""));
                        String response = gson.toJson(manager.getSubtaskById(id));
                        sendText(exchange, response);
                    } else {
                        System.out.println("Неверно указан эндпоинт для GET: */subtask/?");
                        exchange.sendResponseHeaders(HTTP_NOT_FOUND, 0);
                    }
                    break;
                }
                case "POST": {
                    if (body.isEmpty()) {
                        System.out.println("Тело запроса пустое!");
                        exchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
                    } else if (query == null) {
                        System.out.println("Обработка метода POST, пустого эндпоинта");
                        Subtask subtask = gson.fromJson(body, Subtask.class);
                        manager.createSubtask(subtask, subtask.getEpicId());
                        exchange.sendResponseHeaders(HTTP_OK, 0);
                    } else {
                        System.out.println("Неверно указан эндпоинт для POST: */subtask/?");
                        exchange.sendResponseHeaders(HTTP_NOT_FOUND, 0);
                    }
                    break;
                }
                case "DELETE": {
                    if (query == null) {
                        System.out.println("Обработка метода DELETE, пустого эндпоинта");
                        manager.removeSubtasks();
                        exchange.sendResponseHeaders(HTTP_OK, 0);
                    } else if (query != null) {
                        System.out.println("Обработка метода DELETE, запроса " + query);
                        int id = Integer.parseInt(query.replaceFirst("id=", ""));
                        manager.removeSubtaskById(id);
                        exchange.sendResponseHeaders(HTTP_OK, 0);
                    } else {
                        System.out.println("Неверно указан эндпоинт для DELETE: */subtask/?");
                        exchange.sendResponseHeaders(HTTP_OK, 0);
                    }
                    break;
                }
                default: {
                    System.out.println("Неизвестный метод! " + method);
                    exchange.sendResponseHeaders(HTTP_BAD_METHOD, 0);
                    break;
                }
            }
        } catch (Exception exception) {
            System.out.println("Неизвестная ошибка!");
            exception.printStackTrace();
        } finally {
            exchange.close();
        }
    }
}
