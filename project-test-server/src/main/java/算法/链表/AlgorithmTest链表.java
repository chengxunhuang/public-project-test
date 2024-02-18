package 算法.链表;

import 算法.链表.ListNode;
import 算法.链表.SingleLinkedList;

import java.util.HashSet;

/**
 * @author:wuhao
 * @create: 2023-06-28 09:31
 * @Description:
 */
public class AlgorithmTest链表 {
    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder("121");
        if (stringBuilder.reverse().toString().equals("121")) System.out.println("true");
        System.out.println("回文数字" + isMoslemsNumber(123));
        System.out.println("回文字符串" + isMoslemsString("369"));

        // 也可以这样创建链表
        SingleLinkedList list = new SingleLinkedList();
        list.add(new ListNode(1));
        list.add(new ListNode(4));
        list.add(new ListNode(6));
        list.add(new ListNode(9));
        list.list();

        // 创建链表 1 -> 2 -> 3 -> 4 -> 5
        ListNode head = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        head.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;

        //System.out.println("双指针迭代原始链表：");
        //ListNode.printList(head);
        //
        //ListNode listNode = reverseList(head);
        //
        //System.out.println("双指针迭代反转链表：");
        //ListNode.printList(listNode);

        //System.out.println("递归原始链表：");
        //ListNode.printList(head);
        //
        //ListNode listDGNode = reverseDGList(head);
        //
        //System.out.println("递归反转链表：");
        //ListNode.printList(listDGNode);


        ListNode head2 = new ListNode(0);

        System.out.println("合并有序链表：");
        ListNode.printList(mergeTwoLists1(head, head2));


    }

    /**
     * 回文数字
     */
    public static boolean isMoslemsNumber(int x) {
        if (x < 0 || (x % 10 == 0) && x != 0) return false;
        int number = 0;
        while (x > number) {
            number = number * 10 + x % 10;
            x = x / 10;
        }
        return x == number || x == number / 10;
    }

    /**
     * 回文字符串
     */
    public static boolean isMoslemsString(String x) {
        char[] chars = x.toCharArray();
        for (int i = 0; i < chars.length / 2; i++) {
            if (chars[i] != chars[chars.length - 1 - i]) return false;
        }
        return true;
    }


    /**
     * 单链表反转 - 双指针迭代
     */
    public static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head; //当前节点
        while (curr != null) {
            ListNode next = curr.next; // 暂存当前节点的下一个节点
            curr.next = prev; // 将当前节点的下一个指针指向前一个节点，实现反转
            prev = curr; // 更新前一个节点为当前节点
            curr = next;  // 更新当前节点为原先的下一个节点
        }
        return prev; // 反转后的头节点即为原链表的尾节点
    }

    /**
     * 单链表反转 - 递归
     * 1->2->3 反转 3->2->1
     *
     */
    public static ListNode reverseDGList(ListNode head) {
        //递归终止条件是当前为空，或者下一个节点为空
        if (head == null || head.next == null) {
            // 如果链表是 1->2->3->4->5，当前节点5 就会进来 ，然后执行 reverseDGList head =4 ，因为栈是先进后出的
            return head;
        }
        //这里的cur就是最后一个节点
        ListNode cur = reverseDGList(head.next);
        //这里请配合动画演示理解
        //如果链表是 1->2->3->4->5，那么此时的cur就是5
        //而head是4，head的下一个是5，下下一个是空
        //所以head.next.next 就是5->4
        head.next.next = head;
        //防止链表循环，需要将head.next设置为空
        head.next = null;
        //每层递归函数都返回cur，也就是最后一个节点
        return cur;
    }

    /**
     * 链表中环的检测 - 哈希表 ， 访问的节点加入哈希表中 ，如果已经访问了 再访问就是环形链表
     */
    public boolean hasCycle1(ListNode head) {
        HashSet<ListNode> listNodes = new HashSet<>();
        while (head != null) {
            if (!listNodes.add(head)) {
                return true;
            }
            head = head.next;
        }
        return false;
    }

    /**
     * 环形链表-快慢指针，只要快指针 追上 慢指针 就是环形
     *
     * @param head
     * @return
     */
    public boolean hasCycle2(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }

    /**
     * 合并两个有序链表 , 迭代   leetcode 21
     *
     * @param list1
     * @param list2
     * @return
     */
    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // 增加一个哨兵节点，便于合并链表，返回的时候直接 preHead.next 返回
        ListNode preHead = new ListNode(-1);
        ListNode prev = preHead;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                prev.next = list1;
                list1 = list1.next;
            } else {
                prev.next = list2;
                list2 = list2.next;
            }
            prev = prev.next;
        }
        // 还有一种情况就是，这两个链表的长度不一样，其中一个遍历完成 ，另一个还没有遍历完
        prev.next = list1 == null ? list2 : list1;
        return preHead.next;
    }

    /**
     * 合并两个有序链表 , 递归
     * list1[0]+merge(list1[1],list2)  list1[0]<list2[0]
     * list2[0]+merge(list1,list2[1])  otherwise
     * <p>
     * 也就是说，两个链表头部值较小的一个节点与剩下元素的 merge 操作结果合并。
     *
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode mergeTwoLists1(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        } else if (l1.val < l2.val) {
            l1.next = mergeTwoLists1(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists1(l1, l2.next);
            return l2;
        }
    }

    /**
     * 删除链表的倒数第 N 个结点 ,计算链表的长度
     *
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode preHead = new ListNode(0, head);
        int i = 0;
        ListNode curr = preHead;
        while (head != null) {
            i++;
            head = head.next;
        }
        for (int j = 1; j < i - n + 1; j++) {
            curr = curr.next;
        }
        curr.next = curr.next.next;
        return preHead.next;
    }

    /**
     * 删除链表的倒数第 N 个结点，双指针
     *
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd1(ListNode head, int n) {
        ListNode preHead = new ListNode(0, head);
        ListNode first = head;
        ListNode second = preHead;
        for (int i = 0; i < n; i++) {
            first = first.next;
        }
        while (first != null) {
            first = first.next;
            second = second.next;
        }
        second.next = second.next.next;
        return preHead.next;
    }

    /**
     * 链表的中间结点 ,单指针迭代
     */
    public static ListNode middleNode(ListNode head) {
        ListNode curr = head;
        int count = 0;
        while (head != null) {
            head = head.next;
            count++;
        }
        for (int i = 0; i < count / 2; i++) {
            curr = curr.next;
        }
        return curr;
    }

    /**
     * 链表的中间结点 ,快慢指针
     * 当快指针指向null时 ， 慢指针就是中间节点
     * 慢指针几乎就是答案的位置
     */
    public static ListNode middleNode1(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

}
