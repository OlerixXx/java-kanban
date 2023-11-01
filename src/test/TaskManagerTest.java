package test;

import finaltask.TaskManager;
import finaltask.tasks.Epic;
import finaltask.tasks.Subtask;
import finaltask.tasks.Task;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest<T extends TaskManager> {
    
    protected T taskManager;

    @Test
    public void getTaskWhenCreatingTask() {
        Task task = new Task("Title", "Description", Duration.ofDays(10), LocalDateTime.now());
        assertEquals(task, taskManager.createTask(task));
    }

    @Test
    public void getEpicWhenCreatingEpic() {
        Epic epic = new Epic("Title", "Description");
        assertEquals(epic, taskManager.createEpic(epic));
    }

    @Test
    public void getSubtaskWhenCreatingSubtask() {
        Epic epic = new Epic("Title", "Description");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("Title", "Description", Duration.ofDays(10), LocalDateTime.now());
        assertEquals(subtask, taskManager.createSubtask(subtask, epic.getId()));
    }

    @Test
    public void getEpicSubtasksListWhenCallingMethodGetEpicSubtasks() {
        Epic epic = new Epic("Title", "Description");
        taskManager.createEpic(epic);
        Subtask subtask1 = new Subtask("Title", "Description", Duration.ofDays(10), LocalDateTime.now());
        Subtask subtask2 = new Subtask("Title", "Description", Duration.ofDays(10), LocalDateTime.now());
        ArrayList<Subtask> subtaskList = new ArrayList<>();
        subtaskList.add(subtask1);
        subtaskList.add(subtask2);
        for (Subtask subtask : subtaskList) {
            taskManager.createSubtask(subtask, epic.getId());
        }
        assertEquals(subtaskList, taskManager.getEpicSubtasks(epic.getId()));
    }

    @Test
    public void getTaskWhenCallingMethodGetTaskById() {
        Task task = new Task("Title", "Description", Duration.ofDays(10), LocalDateTime.now());
        taskManager.createTask(task);
        assertEquals(task, taskManager.getTaskById(task.getId()));
    }

    @Test
    public void getExceptionWhenCallingMethodGetTaskByIdWithAnEmptyTaskList() {
        Task task = new Task("Title", "Description", Duration.ofDays(10), LocalDateTime.now());
        final RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> taskManager.getTaskById(task.getId())  // Здесь передаётся null
        );

        final RuntimeException exception1 = assertThrows(
                RuntimeException.class,
                () -> taskManager.getTaskById(1)  // Несуществующий идентефикатор
        );
    }

    @Test
    public void getEpicWhenCallingMethodGetEpicById() {
        Epic epic = new Epic("Title", "Description");
        taskManager.createEpic(epic);
        assertEquals(epic, taskManager.getEpicById(epic.getId()));
    }

    @Test
    public void getExceptionWhenCallingMethodGetEpicByIdWithAnEmptyEpicList() {
        Epic epic = new Epic("Title", "Description");
        final RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> taskManager.getEpicById(epic.getId())  // Здесь передаётся null
        );

        final RuntimeException exception1 = assertThrows(
                RuntimeException.class,
                () -> taskManager.getEpicById(1)  // Несуществующий идентефикатор
        );
    }

    @Test
    public void getSubtaskWhenCallingMethodGetSubtaskById() {
        Epic epic = new Epic("Title", "Description");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("Title", "Description", Duration.ofDays(10), LocalDateTime.now());
        taskManager.createSubtask(subtask, epic.getId());
        assertEquals(subtask, taskManager.getSubTaskById(subtask.getId()));
    }

    @Test
    public void getExceptionWhenCallingMethodGetSubtaskByIdWithAnEmptySubtaskList() {
        Subtask subtask = new Subtask("Title", "Description", Duration.ofDays(10), LocalDateTime.now());
        final RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> taskManager.getSubTaskById(subtask.getId())  // Здесь передаётся null
        );

        final RuntimeException exception1 = assertThrows(
                RuntimeException.class,
                () -> taskManager.getSubTaskById(1)  // Несуществующий идентефикатор
        );
    }

    @Test
    public void getTaskListWhenCallingMethodGetAllTask() {
        Task task1 = new Task("Title", "Description", Duration.ofDays(10), LocalDateTime.now());
        Task task2 = new Task("Title", "Description", Duration.ofDays(10), LocalDateTime.now());
        taskManager.createTask(task1);
        taskManager.createTask(task2);

        Task[] taskArray = {task1, task2};

        Collection<Task> taskCollectionFromManager = taskManager.getAllTasks();
        Task[] taskArrayFromManager = taskCollectionFromManager.toArray(new Task[taskCollectionFromManager.size()]);

        assertArrayEquals(taskArray, taskArrayFromManager);
    }

    @Test
    public void getEmptyTaskListWhenCallingMethodGetAllTasksWithAnEmptyTaskList() {
        Task[] taskArray = {};

        Collection<Task> taskCollectionFromManager = taskManager.getAllTasks();
        Task[] taskArrayFromManager = taskCollectionFromManager.toArray(new Task[taskCollectionFromManager.size()]);

        assertArrayEquals(taskArray, taskArrayFromManager);
    }

    @Test
    public void getEpicListWhenCallingMethodGetAllEpic() {
        Epic epic1 = new Epic("Title", "Description");
        Epic epic2 = new Epic("Title", "Description");
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);

        Epic[] epicArray = {epic1, epic2};

        Collection<Epic> epicCollectionFromManager = taskManager.getAllEpics();
        Epic[] subtaskArrayFromManager = epicCollectionFromManager.toArray(new Epic[epicCollectionFromManager.size()]);

        assertArrayEquals(epicArray, subtaskArrayFromManager);
    }

    @Test
    public void getEmptyEpicListWhenCallingMethodGetAllEpicWithAnEmptyEpicList() {
        Epic[] epicArray = {};

        Collection<Epic> epicCollectionFromManager = taskManager.getAllEpics();
        Epic[] epicArrayFromManager = epicCollectionFromManager.toArray(new Epic[epicCollectionFromManager.size()]);

        assertArrayEquals(epicArray, epicArrayFromManager);
    }

    @Test
    public void getSubtaskListWhenCallingMethodGetAllSubtasks() {
        Epic epic = new Epic("Title", "Description");
        Subtask subtask1 = new Subtask("Title", "Description", Duration.ofDays(10), LocalDateTime.now());
        Subtask subtask2 = new Subtask("Title", "Description", Duration.ofDays(10), LocalDateTime.now());
        taskManager.createEpic(epic);
        taskManager.createSubtask(subtask1, epic.getId());
        taskManager.createSubtask(subtask2, epic.getId());

        Subtask[] subtaskArray = {subtask1, subtask2};

        Collection<Subtask> subtaskCollectionFromManager = taskManager.getAllSubtasks();
        Subtask[] subtaskArrayFromManager = subtaskCollectionFromManager.toArray(new Subtask[subtaskCollectionFromManager.size()]);

        assertArrayEquals(subtaskArray, subtaskArrayFromManager);
    }

    @Test
    public void getEmptySubtaskListWhenCallingMethodGetAllSubtasksWithAnEmptySubtaskList() {
        Subtask[] subtaskArray = {};

        Collection<Subtask> subtaskCollectionFromManager = taskManager.getAllSubtasks();
        Subtask[] subtaskArrayFromManager = subtaskCollectionFromManager.toArray(new Subtask[subtaskCollectionFromManager.size()]);

        assertArrayEquals(subtaskArray, subtaskArrayFromManager);
    }

    @Test
    public void getHistoryArrayWith312WhenCallingTheseTasks() {
        Task task1 = new Task("Title", "Description", Duration.ofDays(10), LocalDateTime.now());
        Task task2 = new Task("Title", "Description", Duration.ofDays(10), LocalDateTime.now());
        Task task3 = new Task("Title", "Description", Duration.ofDays(10), LocalDateTime.now());
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);
        taskManager.getTaskById(task3.getId());
        taskManager.getTaskById(task1.getId());
        taskManager.getTaskById(task2.getId());
        Integer[] array = {3, 1, 2};
        List<Integer> historyList = new ArrayList<>();

        taskManager.getHistory()
                .stream()
                .map(task -> historyList.add(task.getId()))
                .collect(Collectors.toList());

        assertArrayEquals(array, historyList.toArray());
    }

    @Test
    public void getAnEmptyHistoryArrayIfYouDontCallTasks() {
        Task task1 = new Task("Title", "Description", Duration.ofDays(10), LocalDateTime.now());
        Task task2 = new Task("Title", "Description", Duration.ofDays(10), LocalDateTime.now());
        Task task3 = new Task("Title", "Description", Duration.ofDays(10), LocalDateTime.now());
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);
        Integer[] array = {};
        List<Integer> historyList = new ArrayList<>();

        taskManager.getHistory()
                .stream()
                .map(task -> historyList.add(task.getId()))
                .collect(Collectors.toList());

        assertArrayEquals(array, historyList.toArray());
    }

    @Test
    public void getNewTitleAfterUpdatingTheTask() {
        Task task = new Task("Title", "Description", Duration.ofDays(10), LocalDateTime.now());
        taskManager.createTask(task);
        task.setTitle("NewTitle");
        taskManager.updateTask(task);
        assertEquals("NewTitle", taskManager.getTaskById(task.getId()).getTitle());
    }

    @Test
    public void doNothingWhenUpdatingTaskIfItIsNotInManager() {
        Task task = new Task("Title", "Description", Duration.ofDays(10), LocalDateTime.now());
        taskManager.updateTask(task);
        assertTrue(taskManager.getAllTasks().isEmpty());
    }

    @Test
    public void getNewTitleAfterUpdatingTheEpic() {
        Epic epic = new Epic("Title", "Description");
        taskManager.createEpic(epic);
        epic.setTitle("NewTitle");
        taskManager.updateEpic(epic);
        assertEquals("NewTitle", taskManager.getEpicById(epic.getId()).getTitle());
    }

    @Test
    public void doNothingWhenUpdatingEpicIfItIsNotInManager() {
        Epic epic = new Epic("Title", "Description");
        taskManager.updateEpic(epic);
        assertTrue(taskManager.getAllEpics().isEmpty());
    }

    @Test
    public void getNewTitleAfterUpdatingTheSubtask() {
        Epic epic = new Epic("Title", "Description");
        Subtask subtask = new Subtask("Title", "Description", Duration.ofDays(10), LocalDateTime.now());
        taskManager.createEpic(epic);
        taskManager.createSubtask(subtask, epic.getId());
        subtask.setTitle("NewTitle");
        taskManager.updateSubtask(subtask);
        assertEquals("NewTitle", taskManager.getSubTaskById(subtask.getId()).getTitle());
    }

    @Test
    public void doNothingWhenUpdatingSubtaskIfItIsNotInManager() {
        Subtask subtask = new Subtask("Title", "Description", Duration.ofDays(10), LocalDateTime.now());
        taskManager.updateSubtask(subtask);
        assertTrue(taskManager.getAllSubtasks().isEmpty());
    }

    @Test
    public void getAnEmptyTaskListWhenDeletingTasks() {
        Task task = new Task("Title", "Description", Duration.ofDays(10), LocalDateTime.now());
        taskManager.createTask(task);
        taskManager.getTaskById(task.getId());

        Task[] taskArray = {task};
        Collection<Task> taskCollectionFromManager = taskManager.getAllTasks();
        Task[] taskArrayFromManager = taskCollectionFromManager.toArray(new Task[taskCollectionFromManager.size()]);
        assertArrayEquals(taskArray, taskArrayFromManager);

        taskManager.removeTasks();

        Task[] taskArrayEmpty = {};
        taskCollectionFromManager = taskManager.getAllTasks();
        taskArrayFromManager = taskCollectionFromManager.toArray(new Task[taskCollectionFromManager.size()]);
        assertArrayEquals(taskArrayEmpty, taskArrayFromManager);
    }

    @Test
    public void getAnEmptyEpicListWhenDeletingEpics() {
        Epic epic = new Epic("Title", "Description");
        taskManager.createEpic(epic);
        taskManager.getEpicById(epic.getId());

        Epic[] epicArray = {epic};
        Collection<Epic> epicCollectionFromManager = taskManager.getAllEpics();
        Epic[] epicArrayFromManager = epicCollectionFromManager.toArray(new Epic[epicCollectionFromManager.size()]);
        assertArrayEquals(epicArray, epicArrayFromManager);

        taskManager.removeEpics();

        Epic[] epicArrayEmpty = {};
        epicCollectionFromManager = taskManager.getAllEpics();
        epicArrayFromManager = epicCollectionFromManager.toArray(new Epic[epicCollectionFromManager.size()]);
        assertArrayEquals(epicArrayEmpty, epicArrayFromManager);
    }

    @Test
    public void getAnEmptySubtaskListWhenDeletingSubtasks() {
        Epic epic = new Epic("Title", "Description");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("Title", "Description", Duration.ofDays(10), LocalDateTime.now());
        taskManager.createSubtask(subtask, epic.getId());
        taskManager.getSubTaskById(subtask.getId());

        Subtask[] subtaskArray = {subtask};
        Collection<Subtask> subtaskCollectionFromManager = taskManager.getAllSubtasks();
        Subtask[] subtaskArrayFromManager = subtaskCollectionFromManager.toArray(new Subtask[subtaskCollectionFromManager.size()]);
        assertArrayEquals(subtaskArray, subtaskArrayFromManager);

        taskManager.removeSubtasks();

        Subtask[] subtaskArrayEmpty = {};
        subtaskCollectionFromManager = taskManager.getAllSubtasks();
        subtaskArrayFromManager = subtaskCollectionFromManager.toArray(new Subtask[subtaskCollectionFromManager.size()]);
        assertArrayEquals(subtaskArrayEmpty, subtaskArrayFromManager);
    }

    @Test
    public void getAnEmptyTaskListWhenDeletingTaskById() {
        Task task = new Task("Title", "Description", Duration.ofDays(10), LocalDateTime.now());
        taskManager.createTask(task);
        taskManager.getTaskById(task.getId());

        taskManager.removeTaskById(task.getId());

        Task[] taskArrayEmpty = {};
        Collection<Task> taskCollectionFromManager = taskManager.getAllTasks();
        Task[] taskArrayFromManager = taskCollectionFromManager.toArray(new Task[taskCollectionFromManager.size()]);
        assertArrayEquals(taskArrayEmpty, taskArrayFromManager);
    }

    @Test
    public void getTaskListWhenDeletingTaskWithInvalidId() {
        Task task = new Task("Title", "Description", Duration.ofDays(10), LocalDateTime.now());
        taskManager.createTask(task);
        taskManager.getTaskById(task.getId());

        taskManager.removeTaskById(null);

        Task[] taskArray = {task};
        Collection<Task> taskCollectionFromManager = taskManager.getAllTasks();
        Task[] taskArrayFromManager = taskCollectionFromManager.toArray(new Task[taskCollectionFromManager.size()]);
        assertArrayEquals(taskArray, taskArrayFromManager);
    }

    @Test
    public void getAnEmptyEpicListWhenDeletingEpicById() {
        Epic epic = new Epic("Title", "Description");
        taskManager.createEpic(epic);
        taskManager.getEpicById(epic.getId());

        taskManager.removeEpicById(epic.getId());

        Epic[] epicArray = {};
        Collection<Epic> epicCollectionFromManager = taskManager.getAllEpics();
        Epic[] epicArrayFromManager = epicCollectionFromManager.toArray(new Epic[epicCollectionFromManager.size()]);
        assertArrayEquals(epicArray, epicArrayFromManager);
    }

    @Test
    public void getTaskListWhenDeletingEpicWithInvalidId() {
        Epic epic = new Epic("Title", "Description");
        taskManager.createEpic(epic);
        taskManager.getEpicById(epic.getId());

        taskManager.removeEpicById(null);

        Epic[] epicArray = {epic};
        Collection<Epic> epicCollectionFromManager = taskManager.getAllEpics();
        Epic[] epicArrayFromManager = epicCollectionFromManager.toArray(new Epic[epicCollectionFromManager.size()]);
        assertArrayEquals(epicArray, epicArrayFromManager);
    }

    @Test
    public void getAnEmptySubtaskListWhenDeletingSubtaskById() {
        Epic epic = new Epic("Title", "Description");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("Title", "Description", Duration.ofDays(10), LocalDateTime.now());
        taskManager.createSubtask(subtask, epic.getId());
        taskManager.getSubTaskById(subtask.getId());

        taskManager.removeSubtaskById(subtask.getId());

        Subtask[] subtaskArray = {};
        Collection<Subtask> subtaskCollectionFromManager = taskManager.getAllSubtasks();
        Subtask[] subtaskArrayFromManager = subtaskCollectionFromManager.toArray(new Subtask[subtaskCollectionFromManager.size()]);
        assertArrayEquals(subtaskArray, subtaskArrayFromManager);
    }

    @Test
    public void getSubtaskListWhenDeletingSubtaskWithInvalidId() {
        Epic epic = new Epic("Title", "Description");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("Title", "Description", Duration.ofDays(10), LocalDateTime.now());
        taskManager.createSubtask(subtask, epic.getId());
        taskManager.getSubTaskById(subtask.getId());

        taskManager.removeSubtaskById(null);

        Subtask[] subtaskArray = {subtask};
        Collection<Subtask> subtaskCollectionFromManager = taskManager.getAllSubtasks();
        Subtask[] subtaskArrayFromManager = subtaskCollectionFromManager.toArray(new Subtask[subtaskCollectionFromManager.size()]);
        assertArrayEquals(subtaskArray, subtaskArrayFromManager);
    }

    @Test
    public void getEndTime() {
        Task task = new Task("Title", "Description", Duration.ofDays(10), LocalDateTime.of(2023, 10, 10, 0, 0));
        taskManager.createTask(task);
        assertEquals(LocalDateTime.of(2023, 10, 20, 0, 0), task.getEndTime());
    }
}