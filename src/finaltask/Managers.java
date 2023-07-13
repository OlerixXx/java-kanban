package finaltask;

public class Managers {
    private static final InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
    private static final InMemoryTaskManager getDefault = new InMemoryTaskManager();

    public static TaskManager getDefault() {
        return getDefault;
    }

    public static HistoryManager getDefaultHistory() {
        return historyManager;
    }

}
