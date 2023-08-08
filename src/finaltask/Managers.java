package finaltask;

public class Managers {

    // Мне нужно, чтобы эти переменные были созданы, т.к класс InMemoryTaskManager работает с InMemoryHistoryManager'ом.
    // Методы TaskManager'а попросту не смогут найти historyManager.
    private static final InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
    private static final InMemoryTaskManager getDefault = new InMemoryTaskManager();

    private Managers() {
    }

    public static HistoryManager getDefaultHistory() {
        return historyManager;
    }

    public static TaskManager getDefault() {
        return getDefault;
    }

}
