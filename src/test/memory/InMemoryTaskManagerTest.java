package test.memory;

import finaltask.memory.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;
import test.TaskManagerTest;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    public void setUp() {
        taskManager = new InMemoryTaskManager();
    }
}