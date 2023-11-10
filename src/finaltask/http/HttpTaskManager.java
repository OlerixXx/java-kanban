package finaltask.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import finaltask.Managers;
import finaltask.TaskManager;
import finaltask.file.FileBackedTasksManager;
import finaltask.tasks.Epic;
import finaltask.tasks.Subtask;
import finaltask.tasks.Task;

import java.lang.reflect.Type;
import java.util.List;

public class HttpTaskManager extends FileBackedTasksManager implements TaskManager {

    public static String TASK_KEY = "tasks";
    public static String EPIC_KEY = "epics";
    public static String SUBTASK_KEY = "subtasks";
    public static String HISTORY_KEY = "history";

    private final KVTaskClient client;
    private static final Gson gson = Managers.getDefaultGson();

    public HttpTaskManager(String address, int port) {
        TASK_KEY = "tasks";
        EPIC_KEY = "epics";
        SUBTASK_KEY = "subtasks";
        HISTORY_KEY = "history";
        client = new KVTaskClient(address, port);
    }

    @Override
    public void save() {
        String jsonTask = gson.toJson(getAllTasks());
        client.put(TASK_KEY, jsonTask);

        String jsonEpic = gson.toJson(getAllEpics());
        client.put(EPIC_KEY, jsonEpic);

        String jsonSubtask = gson.toJson(getAllSubtasks());
        client.put(SUBTASK_KEY, jsonSubtask);

        String jsonHistory = gson.toJson(getHistory());
        client.put(HISTORY_KEY, jsonHistory);
    }

    public void load() {
        String jsonTask = client.load(TASK_KEY);
        if (!jsonTask.isEmpty()) {
            Type taskListType = new TypeToken<List<Task>>() {}.getType();
            List<Task> taskList = gson.fromJson(jsonTask, taskListType);
            if (!taskList.isEmpty()) {
                for (Task task : taskList) {
                    this.taskStorage.put(task.getId(), task);
                }
            }
        }


        String jsonEpic = client.load(EPIC_KEY);
        if (!jsonEpic.isEmpty()) {
            Type epicListType = new TypeToken<List<Epic>>() {}.getType();
            List<Epic> epicList = gson.fromJson(jsonEpic, epicListType);
            if (!epicList.isEmpty()) {
                for (Epic epic : epicList) {
                    this.epicStorage.put(epic.getId(), epic);
                }
            }
        }


        String jsonSubtask = client.load(SUBTASK_KEY);
        if (!jsonSubtask.isEmpty()) {
            Type subtaskListType = new TypeToken<List<Subtask>>() {}.getType();
            List<Subtask> subtaskList = gson.fromJson(jsonSubtask, subtaskListType);
            if (!subtaskList.isEmpty()) {
                for (Subtask subtask : subtaskList) {
                    this.subtaskStorage.put(subtask.getId(), subtask);
                }
            }
        }

        String jsonHistory = client.load(HISTORY_KEY);
        if (!jsonHistory.isEmpty()) {
            Type historyListType = new TypeToken<List<Task>>() {}.getType();
            List<Task> historyList = gson.fromJson(jsonHistory, historyListType);
            if (!historyList.isEmpty()) {
                for (Task task : historyList) {
                    this.historyManager.add(task);
                }
            }
        }
    }

}
