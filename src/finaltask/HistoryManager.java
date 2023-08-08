package finaltask;

import finaltask.tasks.Task;

import java.util.List;
import java.util.Map;

public interface HistoryManager {

    void add(Task task);

    void removeNode(int taskId);

    List<Task> getTaskList();

    /* Метод getMap() необходим для работы удаления задач в InMemoryTaskManager.
       В метод removeNode() надо передавать ноду, доступа к которой в классе
       InMemoryTaskManager нет. В итоге, взяв HashMap со всеми нодами мы
       можем реализовать метод removeNodeById(). */
    Map<Integer, Node> getMap();
}
