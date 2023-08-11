package finaltask.tasks;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> subtaskIds = new ArrayList<>();
    public Epic(String title, String description, Status status) {
        super(title, description, status);
    }

    public Epic(String title, String description) {
        super(title, description);
    }
    public List<Integer> getSubtaskIdsList() {
        return subtaskIds;
    }
}
