package finaltask.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static finaltask.tasks.TaskType.EPIC;

public class Epic extends Task {

    private LocalDateTime endTime;
    private final List<Integer> subtaskIds = new ArrayList<>();

    public Epic(String title, String description, Status status) {
        super(title, description, status);
        this.type = EPIC;
    }

    public Epic(String title, String description) {
        super(title, description);
        this.type = EPIC;
    }

    public Epic(String title, String description, Status status, Integer id, LocalDateTime startTime, Duration duration) {
        super(title, description, status, id, startTime, duration);
        this.type = EPIC;
    }

    public Epic(String title, String description, Status status, Integer id, LocalDateTime startTime, Duration duration, LocalDateTime endTime) {
        super(title, description, status, id, startTime, duration);
        this.type = EPIC;
        this.endTime = endTime;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public List<Integer> getSubtaskIdsList() {
        return subtaskIds;
    }
}
