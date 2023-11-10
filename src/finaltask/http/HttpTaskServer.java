package finaltask.http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import finaltask.Managers;
import finaltask.TaskManager;
import finaltask.http.endpoints.*;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private Gson gson;
    private HttpServer server;
    private TaskManager manager;

    public HttpTaskServer() throws IOException {
        this.server = HttpServer.create();
        this.server.bind(new InetSocketAddress(PORT), 0);
        this.manager = Managers.getDefault("localhost", 8080);
        this.gson = Managers.getDefaultGson();
        this.server.createContext("/tasks", new EmptyEndpoint(gson, manager));
        this.server.createContext("/tasks/task", new TaskEndpoint(gson, manager));
        this.server.createContext("/tasks/epic", new EpicEndpoint(gson, manager));
        this.server.createContext("/tasks/subtask", new SubtaskEndpoint(gson, manager));
        this.server.createContext("/tasks/history", new HistoryEndpoint(gson, manager));
        this.server.createContext("/tasks/subtask/epic", new EpicSubtasksEndpoint(gson, manager));

    }

    /** Тестовый метод main.
     * После запуска, сервер будет доступен по адресу localhost:8080
     */
    public static void main(String[] args) throws IOException {
        HttpTaskServer server = new HttpTaskServer();
        server.start();
    }

    public void start() {
        System.out.println("Started HTTP Task Server in port: " + PORT);
        server.start();
    }

    public void stop() {
        server.stop(0);
        System.out.println("HTTP Task Server has stopped");
    }
}
