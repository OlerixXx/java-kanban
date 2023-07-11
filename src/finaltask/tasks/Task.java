package finaltask.tasks;

import java.util.Objects;

public class Task {
    protected Integer id;
    protected String title;
    protected String description;
    protected Status status;

    public Task(String title, String description, Status status) { // 1 конструктор необходим, если статус будет задан пользователем.
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public Task(String title, String description) { // 2 конструктор, если статус не задали изначально (Пункт ТЗ 4.1)
        this.title = title;
        this.description = description;
        this.status = Status.NEW;
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

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id)
                && Objects.equals(title, task.title)
                && Objects.equals(description, task.description)
                && Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, status);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
