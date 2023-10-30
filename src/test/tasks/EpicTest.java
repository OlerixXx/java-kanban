package test.tasks;

import finaltask.memory.InMemoryTaskManager;
import finaltask.tasks.Epic;
import finaltask.tasks.Status;
import finaltask.tasks.Subtask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EpicTest {

    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
    Epic epic = new Epic("Title", "Description");
    Subtask subtask1;
    Subtask subtask2;

    @BeforeEach
    public void setUp() {
        inMemoryTaskManager.createEpic(epic);
        subtask1 = new Subtask("Title", "Description");
        subtask2 = new Subtask("Title", "Description");
    }

    @Test
    public void giveNewStatusIfTheListOfSubtasksForAnEpicIsEmpty() {
        assertTrue(inMemoryTaskManager.getEpicById(epic.getId()).getSubtaskIdsList().isEmpty());
        assertEquals(inMemoryTaskManager.getEpicById(epic.getId()).getStatus(), Status.NEW);
    }

    @Test
    public void giveNewStatusIfTheListOfSubtasksForAnEpicHaveStatusNew() {
        subtask1.setStatus(Status.NEW);
        subtask2.setStatus(Status.NEW);
        inMemoryTaskManager.createSubtask(subtask1, epic.getId());
        inMemoryTaskManager.createSubtask(subtask2, epic.getId());
        inMemoryTaskManager.updateEpic(epic);
        assertEquals(inMemoryTaskManager.getEpicById(epic.getId()).getStatus(), Status.NEW);
    }

    @Test
    public void giveDoneStatusIfTheListOfSubtasksForAnEpicHaveStatusDone() {
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);
        inMemoryTaskManager.createSubtask(subtask1, epic.getId());
        inMemoryTaskManager.createSubtask(subtask2, epic.getId());
        inMemoryTaskManager.updateEpic(epic);
        assertEquals(inMemoryTaskManager.getEpicById(epic.getId()).getStatus(), Status.DONE);
    }

    @Test
    public void giveInProgressStatusIfTheListOfSubtasksForAnEpicHaveStatusNewAndDone() {
        subtask1.setStatus(Status.NEW);
        subtask2.setStatus(Status.DONE);
        inMemoryTaskManager.createSubtask(subtask1, epic.getId());
        inMemoryTaskManager.createSubtask(subtask2, epic.getId());
        inMemoryTaskManager.updateEpic(epic);
        assertEquals(inMemoryTaskManager.getEpicById(epic.getId()).getStatus(), Status.IN_PROGRESS);
    }

    @Test
    public void giveInProgressStatusIfTheListOfSubtasksForAnEpicHaveStatusInProgress() {
        subtask1.setStatus(Status.IN_PROGRESS);
        subtask2.setStatus(Status.IN_PROGRESS);
        inMemoryTaskManager.createSubtask(subtask1, epic.getId());
        inMemoryTaskManager.createSubtask(subtask2, epic.getId());
        inMemoryTaskManager.updateEpic(epic);
        assertEquals(inMemoryTaskManager.getEpicById(epic.getId()).getStatus(), Status.IN_PROGRESS);
    }
}