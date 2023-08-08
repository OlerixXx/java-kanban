package finaltask;

import finaltask.tasks.Epic;
import finaltask.tasks.Status;
import finaltask.tasks.Subtask;
import finaltask.tasks.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> taskStorage;
    private final Map<Integer, Epic> epicStorage;
    private final Map<Integer, Subtask> subtaskStorage;
    private final HistoryManager historyManager;
    private int generatedId = 0;

    public InMemoryTaskManager() {
        taskStorage = new HashMap<>();
        epicStorage = new HashMap<>();
        subtaskStorage = new HashMap<>();
        historyManager = Managers.getDefaultHistory();
    }

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
        epicStorage.get(epicId).getSubtaskIdsList().add(id);
        return subtask;
    }

    public ArrayList<Subtask> getEpicSubtasks(int id) {
        ArrayList<Subtask> subtasks = new ArrayList<>();
        for (int SubtaskId : epicStorage.get(id).getSubtaskIdsList()) {
            subtasks.add(subtaskStorage.get(SubtaskId));
        }
        return subtasks;
    }

    public Task getTaskById(int id) {
        Task task = taskStorage.get(id);
        if (task == null) {
            throw new RuntimeException();
        } else {
            historyManager.add(task);
            return task;
        }
    }

    public Epic getEpicById(int id) {
        Epic epic = epicStorage.get(id);
        if (epic == null) {
            throw new RuntimeException();
        } else {
            historyManager.add(epic);
            return epic;
        }
    }

    public Subtask getSubTaskById(int id) {
        Subtask subtask = subtaskStorage.get(id);
        if (subtask == null) {
            throw new RuntimeException();
        } else {
            historyManager.add(subtask);
            return subtask;
        }
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

    public List<Task> getHistory() {
        return historyManager.getTaskList();
    }

    public void updateTask(Task task) {
        Task saved = taskStorage.get(task.getId());
        if (saved == null) {
            return;
        }
        taskStorage.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        Epic saved = epicStorage.get(epic.getId());
        if (saved == null) {
            return;
        }
        epic.setStatus(updateEpicStatus(epic));
        epicStorage.put(epic.getId(), epic);
    }

    public void updateSubtask(Subtask subtask) {
        Subtask saved = subtaskStorage.get(subtask.getId());
        Epic epic = epicStorage.get(subtask.getEpicId());
        if (saved == null) {
            return;
        }
        subtaskStorage.put(subtask.getId(), subtask);
        epic.setStatus(updateEpicStatus(epic));
    }

    public void removeTasks() {
        for (Integer id : taskStorage.keySet()) {
            historyManager.removeNode(id);
        }
        taskStorage.clear();
    }

    public void removeEpics() {
        for (Map.Entry<Integer, Epic> entry : epicStorage.entrySet()) {
            historyManager.removeNode(entry.getKey());
            for (Integer id : entry.getValue().getSubtaskIdsList()) {
                historyManager.removeNode(id);
            }
        }
        epicStorage.clear();
        subtaskStorage.clear();
    }

    public void removeSubtasks() {
        for (Integer id : subtaskStorage.keySet()) {
            historyManager.removeNode(id);
        }
        subtaskStorage.clear();
    }

    public void removeTaskById(int id) {
        historyManager.removeNode(id);
        taskStorage.remove(id);
    }

    public void removeEpicById(int id) {
        List<Integer> list = getEpicById(id).getSubtaskIdsList();
        for (Integer subtaskId : list) {
            removeSubtaskById(subtaskId);
        }
        historyManager.removeNode(id);
        epicStorage.remove(id);
    }

    public void removeSubtaskById(int id) {
        historyManager.removeNode(id);
        subtaskStorage.remove(id);
    }

    private Status updateEpicStatus(Epic epic) {
        boolean statusNEW = true;
        boolean statusDONE = true;

        for (Subtask subtask : getEpicSubtasks(epic.getId())) {
            statusNEW &= subtask.getStatus() == Status.NEW;
            statusDONE &= subtask.getStatus() == Status.DONE;
        }

        if (epic.getSubtaskIdsList().isEmpty()) {
            return Status.NEW;
        } else if (statusNEW) {
            return Status.NEW;
        } else if (statusDONE) {
            return Status.DONE;
        } else {
            return Status.IN_PROGRESS;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InMemoryTaskManager inMemoryTaskManager = (InMemoryTaskManager) o;
        return generatedId == inMemoryTaskManager.generatedId
                && taskStorage.equals(inMemoryTaskManager.taskStorage)
                && epicStorage.equals(inMemoryTaskManager.epicStorage)
                && subtaskStorage.equals(inMemoryTaskManager.subtaskStorage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(generatedId, taskStorage, epicStorage, subtaskStorage);
    }

    private int generateId() {
        return ++generatedId;
    }

}
