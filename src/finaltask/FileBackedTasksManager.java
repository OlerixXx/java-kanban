package finaltask;

import finaltask.tasks.*;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {
    File file;
    private static final CSVFormatHandler handler = new CSVFormatHandler();
    public FileBackedTasksManager(File file) {
        this.file = file;
    }
    public FileBackedTasksManager() {}

    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            // Заголовок

            writer.write(handler.getHeader());
            writer.newLine();

            // Сохраняем список задач
            for (Task task : taskStorage.values()) {
                writer.write(handler.toString(task));
                writer.newLine();
            }

            // Сохраняем список эпиков
            for (Epic epic : epicStorage.values()) {
                writer.write(handler.toString(epic));
                writer.newLine();
            }

            // Сохраняем список подзадач
            for (Subtask subtask : subtaskStorage.values()) {
                writer.write(handler.toString(subtask));
                writer.newLine();
            }

            // Сохраняем историю просмотров
            writer.newLine();
            writer.write(handler.historyToString(historyManager));



        } catch (IOException exception) {
            throw new IllegalArgumentException("Невозможно сохранить файл!");
        }
    }

    static FileBackedTasksManager loadFromFile (File file) {
        FileBackedTasksManager manager = new FileBackedTasksManager(file);

        try (BufferedReader reader = new BufferedReader(new FileReader(manager.file))) {
            List<String> lineArray = new ArrayList<>();

            while (reader.ready()) {
                String line = reader.readLine();
                lineArray.add(line);
            }

            /* Проверяем наличие заголовка, после пропускаем заголовок (проверка, что файл не пустой).
               Если строка не пустая, то записываем в manager задачи. Если же строка
               пустая, то следующая строка - либо история (преобразовываем историю), либо файл совсем пустой. */

            if (lineArray.isEmpty()) {
                throw new IOException("Файл пустой!");
            } else if (lineArray.get(0).trim().isEmpty()) {
                throw new IOException("Файл имеет неверный формат записи!");
            }

            for (int i = 1; i <= lineArray.size(); i++) {
                if (lineArray.size() == 1) {break;}

                String line = lineArray.get(i);

                if (!line.trim().isEmpty()) {
                    Task task = handler.fromString(line);
                    switch (task.getType()) {
                        case TASK:
                            manager.taskStorage.put(task.getId(), task);
                            break;
                        case EPIC:
                            manager.epicStorage.put(task.getId(), (Epic) task);
                            break;
                        case SUBTASK:
                            manager.subtaskStorage.put(task.getId(), (Subtask) task);
                            Epic epic = manager.epicStorage.get(((Subtask) task).getEpicId());
                            List<Integer> list = epic.getSubtaskIdsList();
                            list.add(task.getId());
                            break;
                    }
                } else {
                    String nextLine = lineArray.get(i + 1);
                    if (!nextLine.trim().isEmpty()) {
                        List<Task> historyList = handler.historyFromString(nextLine);
                        for (Task task1 : historyList) {
                            manager.historyManager.add(task1);
                        }
                    }
                    break;
                }
            }
        } catch (IOException exception) {
            throw new IllegalArgumentException("Невозможно прочитать файл!");
        }
        return manager;
    }

    @Override
    public Task createTask(Task task) {
        Task newTask = super.createTask(task);
        save();
        return newTask;
    }

    @Override
    public Epic createEpic(Epic epic) {
        Epic newEpic = super.createEpic(epic);
        save();
        return newEpic;
    }

    @Override
    public Subtask createSubtask(Subtask subtask, int epicId) {
        Subtask newSubtask = super.createSubtask(subtask, epicId);
        save();
        return newSubtask;
    }

    @Override
    public ArrayList<Subtask> getEpicSubtasks(int id) {
        ArrayList<Subtask> list = super.getEpicSubtasks(id);
        save();
        return list;
    }

    @Override
    public Task getTaskById(int id) {
        Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = super.getEpicById(id);
        save();
        return epic;
    }

    @Override
    public Subtask getSubTaskById(int id) {
        Subtask subtask = super.getSubTaskById(id);
        save();
        return subtask;
    }

    @Override
    public Collection<Task> getAllTasks() {
        Collection<Task> collection = super.getAllTasks();
        save();
        return collection;
    }

    @Override
    public Collection<Epic> getAllEpics() {
        Collection<Epic> collection = super.getAllEpics();
        save();
        return collection;
    }

    @Override
    public Collection<Subtask> getAllSubtasks() {
        Collection<Subtask> collection = super.getAllSubtasks();
        save();
        return collection;
    }

    @Override
    public List<Task> getHistory() {
        List<Task> list = super.getHistory();
        save();
        return list;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void removeTasks() {
        super.removeTasks();
        save();
    }

    @Override
    public void removeEpics() {
        super.removeEpics();
        save();
    }

    @Override
    public void removeSubtasks() {
        super.removeSubtasks();
        save();
    }

    @Override
    public void removeTaskById(int id) {
        super.removeTaskById(id);
        save();
    }

    @Override
    public void removeEpicById(int id) {
        super.removeEpicById(id);
        save();
    }

    @Override
    public void removeSubtaskById(int id) {
        super.removeSubtaskById(id);
        save();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


}

class CSVFormatHandler {

    private final Map<Integer, Task> temporaryTasks = new HashMap<>();
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
            Task task = temporaryTasks.get(Integer.valueOf(id));
            historyList.add(task);
        }
        return historyList;
    }

    Task fromString(String value) {
        String[] split = value.split(DELIMITER);
        switch (TaskType.valueOf(split[1].toUpperCase())) {
            case TASK:
                Task task = new Task(split[2], split[4], Status.valueOf(split[3].toUpperCase()), Integer.valueOf(split[0]));
                temporaryTasks.put(Integer.valueOf(split[0]), task);
                return task;
            case EPIC:
                Epic epic = new Epic(split[2], split[4], Status.valueOf(split[3].toUpperCase()), Integer.valueOf(split[0]));
                temporaryTasks.put(Integer.valueOf(split[0]), epic);
                return epic;
            case SUBTASK:
                Subtask subtask = new Subtask(split[2], split[4], Status.valueOf(split[3].toUpperCase()),
                        Integer.valueOf(split[0]), Integer.parseInt(split[5]));
                temporaryTasks.put(Integer.valueOf(split[0]), subtask);
                return subtask;
            default:
                return null;
        }
    }

    String getHeader() {
        return "id,type,name,status,description,epic";
    }


}

