package finaltask.file;

import finaltask.HistoryManager;
import finaltask.tasks.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

public class CSVFormatHandler {
    Map<Integer, Task> taskList = new HashMap<>();
    private static final String DELIMITER = ",";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    String toString(Task task) {

        String result = String.join(DELIMITER,
                String.valueOf(task.getId()),
                String.valueOf(task.getType()),
                task.getTitle(),
                String.valueOf(task.getStatus()),
                task.getDescription());

        if (task.getStartTime() == null) {
            result = String.join(DELIMITER, result, "NONE");
        } else {
            result = String.join(DELIMITER, result, task.getStartTime().format(FORMATTER));
        }

        if (task.getDuration() == null) {
            result = String.join(DELIMITER, result, "NONE");
        } else {
            result = String.join(DELIMITER, result, task.getDuration().toString());
        }

        if (task.getEndTime() == null) {
            result = String.join(DELIMITER, result, "NONE");
        } else {
            result = String.join(DELIMITER, result, task.getEndTime().format(FORMATTER));
        }

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

        LocalDateTime startTime;
        Duration duration;
        LocalDateTime endTime;

        if (split[5].equals("NONE")) {
            startTime = null;
        } else {
            startTime = LocalDateTime.parse(split[5], FORMATTER);
        }

        if (split[6].equals("NONE")) {
            duration = null;
        } else {
            duration = Duration.parse(split[6]);
        }

        if (split[7].equals("NONE")) {
            endTime = null;
        } else {
            endTime = LocalDateTime.parse(split[7], FORMATTER);
        }

        switch (TaskType.valueOf(split[1].toUpperCase())) {
            case TASK:
                Task task = new Task(
                        split[2],
                        split[4],
                        Status.valueOf(split[3].toUpperCase()),
                        Integer.valueOf(split[0]),
                        startTime,
                        duration
                );
                taskList.put(Integer.parseInt(split[0]), task);
                return task;

            case EPIC:
                Epic epic = new Epic(
                        split[2],
                        split[4],
                        Status.valueOf(split[3].toUpperCase()),
                        Integer.valueOf(split[0]),
                        startTime,
                        duration,
                        endTime
                );
                taskList.put(Integer.parseInt(split[0]), epic);
                return epic;

            case SUBTASK:
                Subtask subtask = new Subtask(
                        split[2],
                        split[4],
                        Status.valueOf(split[3].toUpperCase()),
                        Integer.valueOf(split[0]),
                        startTime,
                        duration,
                        Integer.parseInt(split[8])
                );
                taskList.put(Integer.parseInt(split[0]), subtask);
                return subtask;

            default:
                return null;
        }
    }

    String getHeader() {
        return "id,type,name,status,description,startTime,duration,endTime,epic";
    }


}