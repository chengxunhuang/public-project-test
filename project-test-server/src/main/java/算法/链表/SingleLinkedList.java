package 算法.链表;

/**
 * @author:wuhao
 * @create: 2023-06-29 15:15
 * @Description:
 */
public class SingleLinkedList {
    private ListNode head = new ListNode(0);

    public ListNode getHead() {
        return head;
    }

    //添加链表(尾插法)
    public void add(ListNode newNode) {
        ListNode temp = head;
        // 找到尾结点
        while (temp.next != null) {
            temp = temp.next;
        }
        // 尾结点的next指向新节点
        temp.next = newNode;
        // head 的val存放的是结点个数
        head.val++;

    }

    //添加链表(头插法)
    public void addByHead(ListNode newNode) {
        newNode.next = head.next;
        head.next = newNode;
        head.val++;

    }


    //遍历节点
    public void list() {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        System.out.println("此链表共有" + head.val + "个结点");
        ListNode temp = head;
        while (temp.next != null) {
            temp = temp.next;
            System.out.println(temp);
        }
    }
}
