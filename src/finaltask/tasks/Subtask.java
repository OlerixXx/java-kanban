package finaltask.tasks;

public class Subtask extends Epic {
    private int epicId;
    public Subtask(String title, String description) {
        super(title, description);
        this.type = TaskType.SUBTASK;
    }
    public Subtask(String title, String description, Status status) {
        super(title, description, status);
        this.type = TaskType.SUBTASK;
    }

    public Subtask(String title, String description, Status status, Integer id, int epicId) {
        super(title, description, status, id);
        this.type = TaskType.SUBTASK;
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}
