package finaltask;

import finaltask.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> taskViewHistory = new ArrayList<>(10);

    public void add(Task task) {
        if (taskViewHistory.size() < 10 ) {
            taskViewHistory.add(task);
        } else {
            taskViewHistory.remove(0);
            taskViewHistory.add(task);
        }
    }

    public List<Task> getHistory() {
        return taskViewHistory;
    }
}
