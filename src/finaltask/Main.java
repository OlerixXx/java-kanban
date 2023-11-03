package finaltask;

import finaltask.tasks.Epic;
import finaltask.tasks.Status;
import finaltask.tasks.Subtask;
import finaltask.tasks.Task;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {

        File file = new File("src\\finaltask\\resources",
                "file.csv");
        TaskManager taskManager = Managers.getDefault(file);
        TaskManager taskManager1 = Managers.getDefault(file);

        /* Всё нижеперечисленное сделано исключительно
           ради тестирования методов менеджера задач. */

        Task task1 = new Task("Задача №1", "Описание задачи №1", Duration.ofDays(5), LocalDateTime.of(2023, 3, 28, 19, 0));
        Task task2 = new Task("Задача №2", "Описание задачи №2", Duration.ofDays(2), LocalDateTime.of(2023, 3, 29, 19, 0)); // Тут будет пересечение по времени, об этом сообщется в начале консоли
        Epic epic1 = new Epic("Эпик №1", "Описание эпика №1");
        Subtask epic1subtask1 = new Subtask("Сабтаск №1", "Описание сабтаска №1", Duration.ofDays(1), LocalDateTime.of(2023, 9, 15, 10, 0));
        Subtask epic1subtask2 = new Subtask("Сабтаск №2", "Описание сабтаска №2", Duration.ofDays(4), LocalDateTime.of(2023, 10, 5, 12, 0));
        Subtask epic1subtask3 = new Subtask("Сабтаск №3", "Описание сабтаска №3", Duration.ofDays(5), LocalDateTime.of(2023, 5, 11, 19, 0));
        Epic epic2 = new Epic("Эпик №2", "Описание эпика №2");

        // Создаём задачи
        task1 = taskManager.createTask(task1);
        task2 = taskManager.createTask(task2);
        epic1 = taskManager.createEpic(epic1);
        epic2 = taskManager.createEpic(epic2);
        epic1subtask1 = taskManager.createSubtask(epic1subtask1, epic1.getId());
        epic1subtask2 = taskManager.createSubtask(epic1subtask2, epic1.getId());
        epic1subtask3 = taskManager.createSubtask(epic1subtask3, epic1.getId());

        // Смотрим задачи
        System.out.println("Получение задач по идентификаторам:");
        System.out.println(taskManager.getTaskById(task1.getId()));
        checkHistory(taskManager);
        epic1subtask1.setStatus(Status.IN_PROGRESS);
        taskManager.updateEpic(epic1);
        taskManager.getTaskById(task2.getId());
        checkHistory(taskManager);
        taskManager.getTaskById(task2.getId());
        checkHistory(taskManager);
        taskManager.getTaskById(task1.getId());
        checkHistory(taskManager);
        taskManager.getEpicById(epic2.getId());
        checkHistory(taskManager);
        taskManager.getEpicById(epic1.getId());
        checkHistory(taskManager);
        taskManager.getEpicById(epic1.getId());
        checkHistory(taskManager);
        taskManager.getSubTaskById(epic1subtask1.getId());
        checkHistory(taskManager);
        taskManager.getSubTaskById(epic1subtask2.getId());
        checkHistory(taskManager);
        taskManager.getSubTaskById(epic1subtask3.getId());
        checkHistory(taskManager);
        taskManager.getEpicById(epic2.getId());
        checkHistory(taskManager);
        taskManager.getTaskById(task2.getId());
        checkHistory(taskManager);

        System.out.println("\nСписок задач в порядке приоритета:");
        for (Task task : taskManager.getPrioritizedTasks()) {
            System.out.println(task);
        }
    }

    public static void checkHistory(TaskManager taskManager) {
        System.out.println("\nИстория задач:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task);
        }
    }

}
