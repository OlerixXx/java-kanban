package finaltask.tasks;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    private final List<Integer> subtaskIds = new ArrayList<>();

    public Epic(String title, String description, Status status) {
        super(title, description, status);
        this.type = TaskType.EPIC;
    }

    public Epic(String title, String description) {
        super(title, description);
        this.type = TaskType.EPIC;
    }

    public Epic(String title, String description, Status status, Integer id) {
        super(title, description, status, id);
        this.type = TaskType.EPIC;
    }

    public List<Integer> getSubtaskIdsList() {
        return subtaskIds;
    }
}
