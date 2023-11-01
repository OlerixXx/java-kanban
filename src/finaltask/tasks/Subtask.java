package finaltask.tasks;

import java.time.Duration;
import java.time.LocalDateTime;

import static finaltask.tasks.TaskType.SUBTASK;

public class Subtask extends Epic {
    private int epicId;

    public Subtask(String title, String description) {
        super(title, description);
        this.type = SUBTASK;
    }

    public Subtask(String title, String description, Status status) {
        super(title, description, status);
        this.type = SUBTASK;
    }

    public Subtask(String title, String description, Status status, Integer id, LocalDateTime startTime, Duration duration, int epicId) {
        super(title, description, status, id, startTime, duration);
        this.type = SUBTASK;
        this.epicId = epicId;
    }

    public Subtask(String title, String description, Duration duration) {
        super(title, description);
        this.type = SUBTASK;
        this.duration = duration;
    }

    public Subtask(String title, String description, Duration duration, LocalDateTime startTime) {
        super(title, description);
        this.type = SUBTASK;
        this.duration = duration;
        this.startTime = startTime;
    }

    @Override
    public LocalDateTime getEndTime() {
        LocalDateTime finalTime = startTime.plus(duration);
        if (startTime.equals(finalTime)) {
            return null;
        } else {
            return finalTime;
        }
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}
