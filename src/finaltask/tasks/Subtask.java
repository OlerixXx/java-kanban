package finaltask.tasks;

public class Subtask extends Epic {
    private int epicId;

    public Subtask(String title, String description) {
        super(title, description);
    }

    public Subtask(String title, String description, String status) {
        super(title, description, status);
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}
