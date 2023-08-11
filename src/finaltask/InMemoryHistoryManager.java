package finaltask;

import finaltask.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private final CustomLinkedList customLinkedList = new CustomLinkedList();

    HashMap<Integer, Node> map = new HashMap<>();

    public void add(Task task) {
        customLinkedList.addNode(task, map);
    }

    @Override
    public void removeNode(int taskId) {
        customLinkedList.removeNode(map.get(taskId));
    }

    @Override
    public List<Task> getTaskList() {
        return customLinkedList.getTasks();
    }

    public Map<Integer, Node> getMap() {
        return map;
    }

}

class CustomLinkedList {

    public Node head;
    public Node tail;
    private int size = 0;

    public void addNode(Task task, Map<Integer, Node> map) {
        if (map.containsKey(task.getId())) {
            removeNode(map.get(task.getId()));
            map.remove(task.getId());
            linkLast(task);
            map.put(task.getId(), head);
        } else {
            linkLast(task);
            map.put(task.getId(), head);
        }
    }

    public List<Task> getTasks() {
        List<Task> list = new ArrayList<>();
        if (tail == null) {
            return list;
        }
        Node current = tail;

        while (current != null) {
            list.add(current.data);
            current = current.next;
        }
        return list;
    }

    public void linkLast (Task task) {
        final Node l = head;
        final Node newNode = new Node(head, task, null);
        head = newNode;
        if (l == null)
            tail = newNode;
        else
            l.next = newNode;
        size++;
    }

    public void removeNode(Node node) {
        Node prev = node.prev;
        Node next = node.next;

        if (prev == null) {
            tail = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            head = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        node.data = null;
        size--;
    }
}

