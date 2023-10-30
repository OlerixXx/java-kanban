package test.file;

import finaltask.file.FileBackedTasksManager;
import finaltask.file.ManagerSaveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.TaskManagerTest;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    File file;

    @BeforeEach
    public void setUp() {
        file = new File("src\\test\\resources", "file.csv");
        taskManager = new FileBackedTasksManager(file);
    }

    @Test
    public void getExceptionIfAnInvalidPathWasReceivedWhenSaving() {
        file = new File("invalidPath", "file.csv");
        taskManager = new FileBackedTasksManager(file);
        final ManagerSaveException exception = assertThrows(
                ManagerSaveException.class,
                () -> taskManager.save()
        );
    }

    @Test
    public void getExceptionIfAnInvalidPathWasReceivedWhenLoad() {
        file = new File("invalidPath", "file.csv");
        final IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> FileBackedTasksManager.loadFromFile(file)
        );
    }



}