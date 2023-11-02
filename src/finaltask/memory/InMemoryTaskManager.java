package finaltask.memory;

import finaltask.HistoryManager;
import finaltask.Managers;
import finaltask.TaskManager;
import finaltask.file.IntersectionException;
import finaltask.tasks.Epic;
import finaltask.tasks.Status;
import finaltask.tasks.Subtask;
import finaltask.tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    Set<Task> prioritizedTaskList = new TreeSet<>((o1, o2) -> {
        if (o1.getStartTime() == null) {
            return -1;
        } else if (o2.getStartTime() == null) {
            return 1;
        }
        return o1.getStartTime().compareTo(o2.getStartTime());
    });

    protected final Map<Integer, Task> taskStorage;
    protected final Map<Integer, Epic> epicStorage;
    protected final Map<Integer, Subtask> subtaskStorage;
    protected final HistoryManager historyManager;
    private int generatedId = 0;

    public InMemoryTaskManager() {
        taskStorage = new HashMap<>();
        epicStorage = new HashMap<>();
        subtaskStorage = new HashMap<>();
        historyManager = Managers.getDefaultHistory();
    }

    public Task createTask(Task task) {
        try {
            int id = generateId();
            task.setId(id);
            taskStorage.put(id, task);
            checkTheTaskForRepetition(task);
            return task;
        } catch (IntersectionException exception) {
            System.out.println("Пересечение по времени при создании задачи с ID:" + task.getId());
            return task;
        }
    }

    public Epic createEpic(Epic epic) {
        int id = generateId();
        epic.setId(id);
        epicStorage.put(id, epic);
        return epic;
    }

    public Subtask createSubtask(Subtask subtask, int epicId) {
        try {
            int id = generateId();
            subtask.setId(id);
            subtask.setEpicId(epicId);
            subtaskStorage.put(id, subtask);
            Epic epic = epicStorage.get(epicId);
            List<Integer> list = epic.getSubtaskIdsList();
            list.add(id);
            updateEpic(getEpicById(epicId));
            checkTheTaskForRepetition(subtask);
            return subtask;
        } catch (IntersectionException exception) {
            System.out.println("Пересечение по времени при создании подзадачи с ID:" + subtask.getId());
            return subtask;
        }
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

    public Set<Task> getPrioritizedTasks() {  // Когда пользлователю необходимо, он может запросить список, который был уже сформирован и отсортирован.
        return prioritizedTaskList;
    }

    public List<Task> getHistory() {
        return historyManager.getTaskList();
    }

    public void updatePrioritizedTasks() {  // При создании задачи и приоритизированный список обновляется
        prioritizedTaskList.addAll(getAllTasks());
        prioritizedTaskList.addAll(getAllSubtasks());
    }

    public void updateTask(Task task) {
        try {
            Task saved = taskStorage.get(task.getId());
            if (saved == null) {
                return;
            }
            taskStorage.put(task.getId(), task);
        } catch (IntersectionException exception) {
            System.out.println("Пересечение по времени при обновлении задачи с ID:" + task.getId());
        }
    }

    public void updateEpic(Epic epic) {
        Epic saved = epicStorage.get(epic.getId());
        if (saved == null) {
            return;
        }
        epic.setStatus(updateEpicStatus(epic));
        epic.setStartTime(updateEpicStartTime(epic));
        epic.setEndTime(updateEpicEndTime(epic));
        epic.setDuration(updateEpicDurationTime(epic));
        epicStorage.put(epic.getId(), epic);
    }

    public void updateSubtask(Subtask subtask) {
        try {
            Subtask saved = subtaskStorage.get(subtask.getId());
            if (saved == null) {
                return;
            }
            subtaskStorage.put(subtask.getId(), subtask);

            Epic epic = getEpicById(subtask.getEpicId());

            epic.setStatus(updateEpicStatus(epic));
            epic.setStartTime(updateEpicStartTime(epic));
            epic.setEndTime(updateEpicEndTime(epic));
            epic.setDuration(updateEpicDurationTime(epic));
            epicStorage.put(epic.getId(), epic);
        } catch (IntersectionException exception) {
            System.out.println("Пересечение по времени при обновлении подзадачи с ID:" + subtask.getId());
        }

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

    public void removeTaskById(Integer id) {
        if (id == null) {
            return;
        }
        historyManager.removeNode(id);
        taskStorage.remove(id);
    }

    public void removeEpicById(Integer id) {
        if (id == null) {
            return;
        }
        List<Integer> list = getEpicById(id).getSubtaskIdsList();
        for (Integer subtaskId : list) {
            removeSubtaskById(subtaskId);
        }
        historyManager.removeNode(id);
        epicStorage.remove(id);
    }

    public void removeSubtaskById(Integer id) {
        if (id == null) {
            return;
        }
        historyManager.removeNode(id);
        subtaskStorage.remove(id);
    }

    public void checkTheTaskForRepetition(Task task) throws IntersectionException {
        String errorMessage = "Пересечение по времени при создании или обновлении задачи.";
        updatePrioritizedTasks();
        if (getPrioritizedTasks().isEmpty()) {
            return;
        }
        updatePrioritizedTasks();
        for (Task prioritizedTask : getPrioritizedTasks()) {
            if (prioritizedTask.getStartTime() == null ||
                    prioritizedTask.getEndTime() == null ||
                    task.getStartTime() == null ||
                    task.getEndTime() == null) {
                return;
            } else if (task.equals(prioritizedTask)) {
                return;
            } else if (task.getStartTime().isAfter(prioritizedTask.getStartTime()) &&
                    task.getStartTime().isBefore(prioritizedTask.getEndTime())) {
                throw new IntersectionException(errorMessage);
            }
        }
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

    private LocalDateTime updateEpicStartTime(Epic epic) {
        Optional<LocalDateTime> optionalTime = getEpicSubtasks(epic.getId())
                .stream()
                .map(Task::getStartTime)
                .filter(Objects::nonNull)
                .min(LocalDateTime::compareTo);
        return optionalTime.orElse(LocalDateTime.MIN);
    }

    private Duration updateEpicDurationTime(Epic epic) {
        Optional<Duration> optionalTime = getEpicSubtasks(epic.getId())
                .stream()
                .map(Task::getDuration)
                .filter(Objects::nonNull)
                .reduce(Duration::plus);
        return optionalTime.orElse(Duration.ZERO);
    }

    private LocalDateTime updateEpicEndTime(Epic epic) {
        Optional<LocalDateTime> optionalTime = getEpicSubtasks(epic.getId())
                .stream()
                .map(Task::getEndTime)
                .filter(Objects::nonNull)
                .max(LocalDateTime::compareTo);
        return optionalTime.orElse(LocalDateTime.MAX);
    }

    private int generateId() {
        return ++generatedId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InMemoryTaskManager that = (InMemoryTaskManager) o;
        return generatedId == that.generatedId && Objects.equals(taskStorage, that.taskStorage) && Objects.equals(epicStorage, that.epicStorage) && Objects.equals(subtaskStorage, that.subtaskStorage) && Objects.equals(historyManager, that.historyManager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskStorage, epicStorage, subtaskStorage, historyManager, generatedId);
    }
}
