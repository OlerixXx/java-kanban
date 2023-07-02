package finaltask;

import finaltask.tasks.Epic;
import finaltask.tasks.Subtask;
import finaltask.tasks.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Manager {
    public int generatedId = 0;
    public HashMap<Integer, Task> taskStorage = new HashMap<>();
    public HashMap<Integer, Epic> epicStorage = new HashMap<>();
    public HashMap<Integer, Subtask> subtaskStorage = new HashMap<>();

    public Task createTask(Task task) {
        int id = generateId();
        task.setId(id);
        taskStorage.put(id, task);
        return task;
    }

    public Epic createEpic(Epic epic) {
        int id = generateId();
        epic.setId(id);
        epicStorage.put(id, epic);
        return epic;
    }

    public Subtask createSubtask(Subtask subtask, int epicId) {
        int id = generateId();
        subtask.setId(id);
        subtask.setEpicId(epicId);
        subtaskStorage.put(id, subtask);
        return subtask;
    }

    public ArrayList<Subtask> getEpicSubtasks(int id) {
        ArrayList<Subtask> subtasks = new ArrayList<>();
        for (Subtask value : subtaskStorage.values()) {
            if (value.getEpicId() == id) {
                subtasks.add(value);
            }
        }
        return subtasks;
    }

    public Task getTaskById(int id) {
        return taskStorage.get(id);
    }

    public Epic getEpicById(int id) {
        return epicStorage.get(id);
    }

    public Subtask getSubTaskById(int id) {
        return subtaskStorage.get(id);
    }

    public Collection<Task> getAllTasks() {
        return taskStorage.values();
    }

    public Collection<Epic> getAllEpics() {
        return epicStorage.values();
    }

    public Collection<Subtask> getAllSubtasks() {
        return subtaskStorage.values();
    }

    public void updateTask(Task task) {
        Task saved = taskStorage.get(task.getId());
        if (saved == null) {
            return;
        }
        taskStorage.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        Task saved = epicStorage.get(epic.getId());
        if (saved == null) {
            return;
        }
        epicStorage.put(epic.getId(), epic);
    }

    public void updateSubtask(Subtask subtask) {
        Task saved = subtaskStorage.get(subtask.getId());
        if (saved == null) {
            return;
        }
        subtaskStorage.put(subtask.getId(), subtask);
    }

    public int generateId() {
        return ++generatedId;
    }

    public void removeTasks() {
        taskStorage.clear();
    }

    public void removeEpics() {
        epicStorage.clear();
        subtaskStorage.clear();
    }

    public void removeSubtasks() {
        subtaskStorage.clear();
    }

    public void removeTaskById(int id) {
        taskStorage.remove(id);
    }

    public void removeEpicById(int id) {
        Epic epic = epicStorage.get(id);
        for (Subtask value : subtaskStorage.values()) {
            if (value.getEpicId() == id) {
                subtaskStorage.remove(id);
            }
        }
        epicStorage.remove(id);
    }

    public void removeSubtaskById(int id) {
        subtaskStorage.remove(id);
    }
}
