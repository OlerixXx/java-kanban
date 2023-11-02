package test.memory;

import finaltask.HistoryManager;
import finaltask.Managers;
import finaltask.memory.Node;
import finaltask.tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    private HistoryManager historyManager;

    private Task task;

    @BeforeEach
    void setUp() {
        historyManager = Managers.getDefaultHistory();
        task = new Task("Title", "Description");
    }

    @Test
    void getEmptyHistoryIfNotCreateTask() {
        List<Task> history = historyManager.getTaskList();
        assertNotNull(history);
        assertTrue(history.isEmpty());
    }

    @Test
    void getHistorySize1IfCreateTask() {
        historyManager.add(task);

        List<Task> history = historyManager.getTaskList();
        assertNotNull(history);
        assertEquals(1, history.size());
    }

    @Test
    void getEmptyMapIfNotCreateTask() {
        Map<Integer, Node> map = historyManager.getMap();
        assertTrue(map.isEmpty());
    }

    @Test
    void getMapSize1IfCreateTask() {
        Map<Integer, Node> map = historyManager.getMap();
        historyManager.add(task);
        assertEquals(1, map.size());
    }

    @Test
    void getEmptyHistoryAfterRemoveNode() {
        task.setId(1);
        historyManager.add(task);
        historyManager.removeNode(task.getId());
        assertTrue(historyManager.getTaskList().isEmpty());
    }

    @Test
    void getHistoryAfterRemoveNodeAndAddTask() {
        task.setId(1);
        historyManager.add(task);
        historyManager.removeNode(task.getId());
        historyManager.add(task);
        assertEquals(1, historyManager.getTaskList().size());
    }

    @Test
    void getHistoryWhenAddingTwoIdenticalObjects() {
        task.setId(1);
        historyManager.add(task);
        historyManager.add(task);
        for (Task task1 : historyManager.getTaskList()) {
            assertEquals(1, task1.getId());
        }
    }

    @Test
    void getHistoryWithTheOrderIdenticalToTheOrderOfAddingTasks() {
        Task task1 = new Task("Title", "Description");
        task1.setId(1);
        historyManager.add(task1);
        Task task2 = new Task("Title", "Description");
        task2.setId(5);
        historyManager.add(task2);
        Task task3 = new Task("Title", "Description");
        task3.setId(3);
        historyManager.add(task3);
        List<Integer> list1 = List.of(1,5,3);
        List<Integer> list = new LinkedList<>();
        for (Task historyTask : historyManager.getTaskList()) {
            list.add(historyTask.getId());
        }
        assertEquals(list1, list);
    }
}