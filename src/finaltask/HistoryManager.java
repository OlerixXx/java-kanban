package finaltask;

import finaltask.tasks.Task;

import java.util.HashMap;
import java.util.List;

public interface HistoryManager {

    void add(Task task);

    void removeNode(Node node);

    List<Task> getTasks();

    /* Метод getMap() необходим для работы удаления задач в InMemoryTaskManager.
       В метод removeNode() надо передавать ноду, доступа к которой в классе
       InMemoryTaskManager нет. В итоге, взяв HashMap со всеми нодами мы
       можем реализовать метод removeNodeById(). */
    HashMap<Integer, Node> getMap();
}
