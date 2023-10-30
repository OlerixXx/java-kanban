package finaltask;

import finaltask.file.FileBackedTasksManager;
import finaltask.memory.InMemoryHistoryManager;
import java.io.File;

public class Managers {

    private Managers() {
    }

    public static TaskManager getDefault(File file) {
        return FileBackedTasksManager.loadFromFile(file);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

}
