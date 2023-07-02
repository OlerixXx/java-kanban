package finaltask.tasks;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> subtaskIds;

    public Epic(String title, String description, String status) {
        super(title, description, status);
    }

    public Epic(String title, String description) {
        super(title, description);
    }

}
