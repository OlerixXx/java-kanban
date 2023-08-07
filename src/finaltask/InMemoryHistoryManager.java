package finaltask;

import finaltask.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    HashMap<Integer, Node> map = new HashMap<>();
    public Node head;
    public Node tail;
    private int size = 0;

    @Override
    public void add(Task task) {
        if (map.containsKey(task.getId())) {
            linkLast(task);
            removeNode(map.get(task.getId()));
            map.remove(task.getId());
            map.put(task.getId(), head);
        } else {
            linkLast(task);
            map.put(task.getId(), head);
        }
    }

    @Override
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

    @Override
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

    public HashMap<Integer, Node> getMap() {
        return map;
    }
}

