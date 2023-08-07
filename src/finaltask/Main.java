package finaltask;

import finaltask.tasks.Epic;
import finaltask.tasks.Subtask;
import finaltask.tasks.Task;

public class Main {

    public static void main(String[] args) {

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
        task1 = Managers.getDefault().createTask(task1);
        task2 = Managers.getDefault().createTask(task2);
        epic1 = Managers.getDefault().createEpic(epic1);
        epic2 = Managers.getDefault().createEpic(epic2);
        epic1subtask1 = Managers.getDefault().createSubtask(epic1subtask1, epic1.getId());
        epic1subtask2 = Managers.getDefault().createSubtask(epic1subtask2, epic1.getId());
        epic1subtask3 = Managers.getDefault().createSubtask(epic1subtask3, epic1.getId());

        // Смотрим задачи
        System.out.println("Получение задач по идентификаторам:");
        System.out.println(Managers.getDefault().getTaskById(task1.getId()));
        checkTaskHistory();
        System.out.println(Managers.getDefault().getTaskById(task2.getId()));
        checkTaskHistory();
        System.out.println(Managers.getDefault().getTaskById(task2.getId()));
        checkTaskHistory();
        System.out.println(Managers.getDefault().getTaskById(task1.getId()));
        checkTaskHistory();
        System.out.println(Managers.getDefault().getEpicById(epic2.getId()));
        checkTaskHistory();
        System.out.println(Managers.getDefault().getEpicById(epic1.getId()));
        checkTaskHistory();
        System.out.println(Managers.getDefault().getEpicById(epic1.getId()));
        checkTaskHistory();
        System.out.println(Managers.getDefault().getSubTaskById(epic1subtask1.getId()));
        checkTaskHistory();
        System.out.println(Managers.getDefault().getSubTaskById(epic1subtask2.getId()));
        checkTaskHistory();
        System.out.println(Managers.getDefault().getSubTaskById(epic1subtask3.getId()));
        checkTaskHistory();
        System.out.println(Managers.getDefault().getEpicById(epic2.getId()));
        checkTaskHistory();
        System.out.println(Managers.getDefault().getTaskById(task2.getId()));
        checkTaskHistory();

        // Удаляем задачи
        Managers.getDefault().removeTaskById(task1.getId());
        checkTaskHistory();
        Managers.getDefault().removeEpicById(epic2.getId());
        checkTaskHistory();
        Managers.getDefault().removeSubtaskById(epic1subtask1.getId());
        checkTaskHistory();
        Managers.getDefault().removeEpicById(epic1.getId());
        checkTaskHistory();
    }

    public static void checkTaskHistory() {
        System.out.println("История просмотра задач:");
        for (Task task : Managers.getDefaultHistory().getTasks()) {
            System.out.println(task.getId());
        }
    }

}
