package finaltask.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

import static finaltask.tasks.Status.NEW;
import static finaltask.tasks.TaskType.TASK;

public class Task {
    protected Integer id;
    protected String title;
    protected String description;
    protected Status status;
    protected TaskType type;

    protected Duration duration;
    protected LocalDateTime startTime;

    public Task(String title, String description, Status status) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.type = TASK;
    }

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = NEW;
        this.type = TASK;
    }

    public Task(String title, String description, Duration duration) {
        this.title = title;
        this.description = description;
        this.status = NEW;
        this.type = TASK;
        this.duration = duration;
    }

    public Task(String title, String description, Duration duration, LocalDateTime startTime) {
        this.title = title;
        this.description = description;
        this.status = NEW;
        this.type = TASK;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(String title, String description, Status status, Integer id, LocalDateTime startTime, Duration duration) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.id = id;
        this.type = TASK;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public TaskType getType() {
        return type;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        LocalDateTime finalTime = startTime.plus(duration);
        if (startTime.equals(finalTime)) {
            return null;
        } else {
            return finalTime;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(title, task.title) && Objects.equals(description, task.description) && status == task.status && type == task.type && Objects.equals(duration, task.duration) && Objects.equals(startTime, task.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, status, type, duration, startTime);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", duration=" + duration +
                ", startTime=" + startTime +
                '}';
    }
}
