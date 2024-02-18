package 算法.链表;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wh
 * @Date: 2024/02/18/16:43
 * @Description: leetcode 150题， 146
 */
public class LRUCache {
    class Node{
        int key;
        int value;
        Node prev;
        Node next;
        Node(int key, int value){
            this.key = key;
            this.value = value;
        }
    }
    private Map<Integer, Node> map = new HashMap<>();
    private Node head;
    private Node tail;
    private int capacity;
    private int size;
    public LRUCache(int capacity){
        this.capacity = capacity;
        head = new Node(0,0);
        tail = new Node(0,0);
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if(map.containsKey(key)){
            Node node = map.get(key);
            removeToHead(node);
            return node.value;
        }else{
            return -1;
        }
    }

    private void removeToHead(Node node) {
        removeNode(node);
        addToHead(node);
    }
    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    private void addToHead(Node node) {
       node.prev = head;
       node.next = head.next;
       head.next.prev = node;
       head.next = node;
    }

    public void put(int key, int value) {
        Node node = map.get(key);
        if(node != null){
            node.value = value;
            removeToHead(node);
        }else{
            if(size == capacity){
                Node tailNode = tail.prev;
                map.remove(tailNode.key);
                removeNode(tailNode);
                size--;
            }
            Node newNode = new Node(key, value);
            addToHead(newNode);
            map.put(key, newNode);
            size++;
        }
    }


}
