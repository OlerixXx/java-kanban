package test.file;

import finaltask.file.FileBackedTasksManager;
import finaltask.file.ManagerSaveException;
import finaltask.tasks.Epic;
import finaltask.tasks.Subtask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.TaskManagerTest;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    public void getEpicTimeAfterCreatingSubtasks() {
        LocalDateTime startTimeSubtask1 = LocalDateTime.of(2023, 10, 19, 12, 21);
        LocalDateTime startTimeSubtask2 = LocalDateTime.of(2021, 5, 11, 19, 12);
        Duration durationTimeSubtask1 = Duration.ofDays(2);
        Duration durationTimeSubtask2 = Duration.ofDays(10);

        Epic epic = new Epic("Title", "Description");
        Subtask subtask1 = new Subtask("Title", "Description", durationTimeSubtask1, startTimeSubtask1);
        Subtask subtask2 = new Subtask("Title", "Description", durationTimeSubtask2, startTimeSubtask2);
        taskManager.createEpic(epic);
        assertNull(epic.getStartTime());
        assertNull(epic.getEndTime());
        assertNull(epic.getDuration());
        taskManager.createSubtask(subtask1, epic.getId());
        taskManager.createSubtask(subtask2, epic.getId());
        taskManager.updateEpic(epic);
        assertEquals(startTimeSubtask2, epic.getStartTime());
        assertEquals(durationTimeSubtask1.plus(durationTimeSubtask2), epic.getDuration());
        assertEquals(subtask1.getEndTime(), epic.getEndTime());
    }


}