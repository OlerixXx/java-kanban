package test.memory;

import finaltask.HistoryManager;
import finaltask.Managers;
import finaltask.memory.Node;
import finaltask.tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void getEmptyMapIfNotCreateTask(){
        Map<Integer, Node> map = historyManager.getMap();
        assertTrue(map.isEmpty());
    }

    @Test
    void getMapSize1IfCreateTask(){
        Map<Integer, Node> map = historyManager.getMap();
        historyManager.add(task);
        assertEquals(1, map.size());
    }

    @Test
    void getEmptyHistoryAfterRemoveNode(){
        task.setId(1);
        historyManager.add(task);
        historyManager.removeNode(task.getId());
        assertTrue(historyManager.getTaskList().isEmpty());
    }

    @Test
    void getHistoryAfterRemoveNodeAndAddTask(){
        task.setId(1);
        historyManager.add(task);
        historyManager.removeNode(task.getId());
        historyManager.add(task);
        assertEquals(1, historyManager.getTaskList().size());
    }
}