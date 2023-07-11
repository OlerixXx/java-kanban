package finaltask;

public class Managers {
    private static final InMemoryTaskManager getDefault = new InMemoryTaskManager();
    private static final InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

    public static TaskManager getDefault() {
        return getDefault;
    }

    public static HistoryManager getDefaultHistory() {
        return historyManager;
    }

}
