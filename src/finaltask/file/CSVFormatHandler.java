package finaltask.file;

import finaltask.HistoryManager;
import finaltask.tasks.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVFormatHandler {
    Map<Integer, Task> taskList = new HashMap<>();
    private static final String DELIMITER = ",";

    String toString(Task task) {
        String result = String.join(DELIMITER,
                String.valueOf(task.getId()),
                String.valueOf(task.getType()),
                task.getTitle(),
                String.valueOf(task.getStatus()),
                task.getDescription());

        if (task.getType() == TaskType.SUBTASK) {
            result = String.join(DELIMITER, result, String.valueOf(((Subtask) task).getEpicId()));
        }

        return result;
    }

    String historyToString(HistoryManager manager) {
        List<String> result = new ArrayList<>();

        for (Task task : manager.getTaskList()) {
            result.add(String.valueOf(task.getId()));
        }

        return String.join(DELIMITER, result);
    }

    List<Task> historyFromString(String value) {
        List<Task> historyList = new ArrayList<>();
        String[] split = value.trim().split(DELIMITER);
        for (String id : split) {
            Task task = taskList.get(Integer.valueOf(id));
            historyList.add(task);
        }
        return historyList;
    }

    Task fromString(String value) {
        String[] split = value.split(DELIMITER);
        switch (TaskType.valueOf(split[1].toUpperCase())) {
            case TASK:
                Task task = new Task(split[2], split[4], Status.valueOf(split[3].toUpperCase()), Integer.valueOf(split[0]));
                taskList.put(Integer.parseInt(split[0]), task);
                return task;
            case EPIC:
                Epic epic = new Epic(split[2], split[4], Status.valueOf(split[3].toUpperCase()), Integer.valueOf(split[0]));
                taskList.put(Integer.parseInt(split[0]), epic);
                return epic;
            case SUBTASK:
                Subtask subtask = new Subtask(split[2], split[4], Status.valueOf(split[3].toUpperCase()),
                        Integer.valueOf(split[0]), Integer.parseInt(split[5]));
                taskList.put(Integer.parseInt(split[0]), subtask);
                return subtask;
            default:
                return null;
        }
    }

    String getHeader() {
        return "id,type,name,status,description,epic";
    }


}