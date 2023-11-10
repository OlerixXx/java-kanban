package finaltask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import finaltask.file.FileBackedTasksManager;
import finaltask.http.HttpTaskManager;
import finaltask.http.adapters.DurationAdapter;
import finaltask.http.adapters.LocalDateAdapter;
import finaltask.memory.InMemoryHistoryManager;
import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

public class Managers {

    private Managers() {
    }

    public static TaskManager getDefault(String address, int port) {
        HttpTaskManager manager = new HttpTaskManager(address, port);
        manager.load();
        return manager;
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static Gson getDefaultGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .serializeNulls()
                .create();
    }

}
