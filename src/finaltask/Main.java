package finaltask;

import finaltask.tasks.Epic;
import finaltask.tasks.Subtask;
import finaltask.tasks.Task;

import java.io.File;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {

        File file = new File("src\\finaltask\\resources",
                "file.csv");
        TaskManager taskManager = Managers.getDefault(file);
        TaskManager taskManager1 = Managers.getDefault(file);

        /* Всё нижеперечисленное сделано исключительно
           ради тестирования методов менеджера задач. */

        Task task1 = new Task("Задача №1", "Описание задачи №1");
        Task task2 = new Task("Задача №2", "Описание задачи №2");
        Epic epic1 = new Epic("Эпик №1", "Описание эпика №1");
        Subtask epic1subtask1 = new Subtask("Сабтаск №1", "Описание сабтаска №1");
        Subtask epic1subtask2 = new Subtask("Сабтаск №2", "Описание сабтаска №2");
        Subtask epic1subtask3 = new Subtask("Сабтаск №3", "Описание сабтаска №3");
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
        System.out.println(taskManager.getTaskById(task2.getId()));
        checkHistory(taskManager);
        System.out.println(taskManager.getTaskById(task2.getId()));
        checkHistory(taskManager);
        System.out.println(taskManager.getTaskById(task1.getId()));
        checkHistory(taskManager);
        System.out.println(taskManager.getEpicById(epic2.getId()));
        checkHistory(taskManager);
        System.out.println(taskManager.getEpicById(epic1.getId()));
        checkHistory(taskManager);
        System.out.println(taskManager.getEpicById(epic1.getId()));
        checkHistory(taskManager);
        System.out.println(taskManager.getSubTaskById(epic1subtask1.getId()));
        checkHistory(taskManager);
        System.out.println(taskManager.getSubTaskById(epic1subtask2.getId()));
        checkHistory(taskManager);
        System.out.println(taskManager.getSubTaskById(epic1subtask3.getId()));
        checkHistory(taskManager);
        System.out.println(taskManager.getEpicById(epic2.getId()));
        checkHistory(taskManager);
        System.out.println(taskManager.getTaskById(task2.getId()));
        checkHistory(taskManager);

        // Удаляем задачи
        taskManager.removeTaskById(task1.getId());
        checkHistory(taskManager1);
    }

    public static void checkHistory(TaskManager taskManager) {
        System.out.println("\nИстория задач:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task);
        }
        System.out.println("\n");
    }

}
