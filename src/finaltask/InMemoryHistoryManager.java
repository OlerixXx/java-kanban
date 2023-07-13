package finaltask;

import finaltask.tasks.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final LinkedList<Task> taskViewHistory = new LinkedList<>();

    public void add(Task task) {
        if (taskViewHistory.size() < 10 ) {
            taskViewHistory.add(task);
        } else {
            taskViewHistory.removeFirst();
            taskViewHistory.add(task);
        }
    }

    public List<Task> getHistory() {
        return taskViewHistory;
    }
}
