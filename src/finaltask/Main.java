package finaltask;

import finaltask.tasks.Epic;
import finaltask.tasks.Subtask;
import finaltask.tasks.Task;

public class Main {
    public static void main(String[] args) {

        /* Всё нижеперечисленное сделано исключительно
           ради тестирования методов менеджера задач. */

        Manager manager = new Manager();

        Task task1 = new Task("Задача №1", "Описание задачи №1", "NEW");
        Task task2 = new Task("Задача №2", "Описание задачи №2", "IN_PROGRESS");

        Epic epic1 = new Epic("Эпик №1", "Описание эпика №1");
        Subtask subtask1epic1 = new Subtask("Подзадача №1", "Описание подзадачи №1");
        Subtask subtask2epic1 = new Subtask("Подзадача №2", "Описание подзадачи №2");

        Epic epic2 = new Epic("Эпик №2", "Описание эпика №2");
        Subtask subtask1epic2 = new Subtask("Подзадача №1", "Описание подзадачи №1");

        // Пункт ТЗ 2.4: Создание - создаём 2 задачи
        task1 = manager.createTask(task1);
        task2 = manager.createTask(task2);

        // Пункт ТЗ 2.4: Создание - создаём эпик 1 и для него же 2 сабстатика
        epic1 = manager.createEpic(epic1);
        subtask1epic1 = manager.createSubtask(subtask1epic1, epic1.getId());
        subtask2epic1 = manager.createSubtask(subtask2epic1, epic1.getId());

        // Пункт ТЗ 2.4: Создание - создаём эпик 2 и для него же 1 сабстатик
        epic2 = manager.createEpic(epic2);
        subtask1epic2 = manager.createSubtask(subtask1epic2, epic2.getId());

        // Пункт ТЗ 2.5: Обновление - объявляем статус IN_PROGRESS для 1 задачи
        Task modTask1 = manager.getTaskById(task1.getId());
        modTask1.setStatus("IN_PROGRESS");
        manager.updateTask(modTask1);

        // Пункт ТЗ 2.5: Обновление - объявляем статус DONE для 1 эпика, его сабстатика 1
        Subtask modSubtask1epic1 = manager.getSubTaskById(subtask1epic1.getId());
        modSubtask1epic1.setStatus("DONE");
        manager.updateSubtask(modSubtask1epic1);

        // Пункт ТЗ 2.5: Обновление - объявляем статус DONE для 1 эпика, его сабстатика 2
        Subtask modSubtask2epic1 = manager.getSubTaskById(subtask2epic1.getId());
        modSubtask2epic1.setStatus("DONE");
        manager.updateSubtask(modSubtask2epic1);

        // Пункт ТЗ 2.3: Получение по идентификатору, используем метод getId()
        System.out.println("Получение задач по идентификаторам:");
        System.out.println(manager.getTaskById(task1.getId()));
        System.out.println(manager.getTaskById(task2.getId()));

        System.out.println(manager.getEpicById(epic1.getId()));
        System.out.println(manager.getSubTaskById(subtask1epic1.getId()));
        System.out.println(manager.getSubTaskById(subtask2epic1.getId()));

        System.out.println(manager.getEpicById(epic2.getId()));
        System.out.println(manager.getSubTaskById(subtask1epic2.getId()));

        // Пункт ТЗ 2.1: Получение списка всех задач, метод getAll...()
        System.out.println("\nВывод списка всех задач:");
        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubtasks());

        // Пункт ТЗ 3.1: Получение списка всех задач опр. эпика с пом. getEpicSubtasks()
        System.out.println("\nПолучение списка всех подзадач определённого эпика:");
        System.out.println(manager.getEpicSubtasks(epic1.getId()));

        // Пункт ТЗ 4.2: Обновление, используем метод update...()
        System.out.println("\nОбновление статуса эпика 1 (Обе подзадачи имеют статус 'DONE')");
        manager.updateEpic(epic1);
        System.out.println(epic1);

        System.out.println("\nОбновление статуса эпика 2 (Единственная подзадача в эпике имеет статус 'NEW')");
        manager.updateEpic(epic2);
        System.out.println(epic2);

        System.out.println("\nОбновление статуса эпика 1 (Одна из подзадач в эпике имеет статус 'IN_PROGRESS')");
        manager.getSubTaskById(subtask2epic1.getId()).setStatus("IN_PROGRESS");
        manager.updateEpic(epic1);
        System.out.println(epic1);

        // Пункт ТЗ 2.6: Удаление по идентификатору
        System.out.println("\nУдаление 1 задачи, вывод всех задач, её нет в списке:");
        manager.removeTaskById(task1.getId());
        System.out.println(manager.getAllTasks());

        // Пункт ТЗ 2.2: Удаление всех задач
        System.out.println("\nУдаление всех задач, их вывод:");
        manager.removeTasks();
    }

}
