package finaltask;

import finaltask.tasks.Task;

public class Main {

    public static void main(String[] args) {

        /* Всё нижеперечисленное сделано исключительно
           ради тестирования методов менеджера задач. */
        

        Task task1 = new Task("Задача №1", "Описание задачи №1");
        Task task2 = new Task("Задача №2", "Описание задачи №2");
        Task task3 = new Task("Задача №3", "Описание задачи №2");
        Task task4 = new Task("Задача №3", "Описание задачи №2");

        // Создаём 4 задачи
        task1 = Managers.getDefault().createTask(task1);
        task2 = Managers.getDefault().createTask(task2);
        task3 = Managers.getDefault().createTask(task3);
        task4 = Managers.getDefault().createTask(task4);

        // Смотрим 10 задач
        System.out.println("Получение задач по идентификаторам:");
        System.out.println(Managers.getDefault().getTaskById(task1.getId()));
        System.out.println(Managers.getDefault().getTaskById(task2.getId()));
        System.out.println(Managers.getDefault().getTaskById(task4.getId()));
        System.out.println(Managers.getDefault().getTaskById(task2.getId()));
        System.out.println(Managers.getDefault().getTaskById(task2.getId()));
        System.out.println(Managers.getDefault().getTaskById(task1.getId()));
        System.out.println(Managers.getDefault().getTaskById(task3.getId()));
        System.out.println(Managers.getDefault().getTaskById(task3.getId()));
        System.out.println(Managers.getDefault().getTaskById(task3.getId()));
        System.out.println(Managers.getDefault().getTaskById(task2.getId()));

        // Проверяем историю просмотра (Ограничение - 10 задач)
        System.out.println("История просмотра задач:");
        for (Task task : Managers.getDefaultHistory().getHistory()) {
            System.out.println(task.getId());
        }

        // Смотрим ещё 3 задачи
        System.out.println(Managers.getDefault().getTaskById(task1.getId()));
        System.out.println(Managers.getDefault().getTaskById(task2.getId()));
        System.out.println(Managers.getDefault().getTaskById(task3.getId()));

        // Проверяем историю просмотра (В итоге, из за переполениня списка, старые удалились, а новые добавились)
        System.out.println("История просмотра задач:");
        for (Task task : Managers.getDefaultHistory().getHistory()) {
            System.out.println(task.getId());
        }
    }

}
