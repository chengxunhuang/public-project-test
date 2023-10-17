package 算法.树;

/**
 * @Author: wh
 * @Date: 2023/08/01/14:04
 * @Description: 二叉树的 前序 中序 后序 遍历
 */
public class 树 {
    public static void main(String[] args) {
        TreeNode root = new TreeNode("A");
        TreeNode left1 = new TreeNode("B");
        TreeNode right1 = new TreeNode("C");
        TreeNode left21 = new TreeNode("D");
        TreeNode right21 = new TreeNode("E");
        TreeNode left22 = new TreeNode("F");
        TreeNode right22 = new TreeNode("G");
        root.left = left1;
        root.right = right1;
        left1.left = left21;
        left1.right = right21;
        right1.left = left22;
        right1.right = right22;
        preOrder(root);
        System.out.println();
        inOrder(root);
        System.out.println();
        postOrder(root);
    }

    /**
     * 前序遍历
     * 写递归代码的关键，就是看能不能写出递推公式，而写递推公式的关键就是，如果要解决问题 A，就假设子问题 B、C 已经解决，
     * 然后再来看如何利用 B、C 来解决 A。所以，我们可以把前、中、后序遍历的递推公式都写出来。
     * 递推公式： preOrder(r) = print r ->  preOrder(r.left) -> preOrder(r.right)
     * @param node
     */
    public static void preOrder(TreeNode node) {
        if (node == null) return;
        System.out.print(node.data + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    /**
     * 中序遍历
     * @param node
     */
    public static void inOrder(TreeNode node) {
        if (node == null) return;
        inOrder(node.left);
        System.out.print(node.data + " ");
        inOrder(node.right);
    }


    /**
     * 后序遍历
     * @param node
     */
    public static void postOrder(TreeNode node) {
        if (node == null) return;
        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.data + " ");
    }



    public static class TreeNode {
        private String data;
        private TreeNode left;
        private TreeNode right;
        public TreeNode(String data) {
            this.data = data;
        }
    }

}
