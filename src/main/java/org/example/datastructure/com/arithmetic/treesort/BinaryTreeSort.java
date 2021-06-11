package org.example.datastructure.com.arithmetic.treesort;


import util.Arrays;
import util.LinkedList;

/**
 * @Description
 * 递归实现二叉树广度遍历排序
 * @Author xuexue
 * @Date 2019/11/16 11:27
 */
public class BinaryTreeSort {

    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<Integer>(
                Arrays.asList(new Integer[]{3,2,9,null,null,10,null, null,8,null,4}));
        //创建二叉树
        TreeNode treeNode = createBinaryTree(linkedList);

        //3 2 9 null null 10 null null 8 null 4
        System.out.print("前序遍历:");
        preOrderTraveral(treeNode);//前序遍历:3 2 9 10 8 4
        System.out.print("\r\n中序遍历:");
        inOrderTraveral(treeNode);//中序遍历:9 2 10 3 8 4
        System.out.print("\r\n后序遍历:");
        postOrderTraveral(treeNode);//后序遍历:9 10 2 4 8 3
    }

    //创建二叉树
    public static TreeNode createBinaryTree(LinkedList<Integer> linkedList) {
        if (linkedList == null || linkedList.isEmpty()) {
            return null;
        }

        //拿到数据
        Integer data = linkedList.removeFirst();
        TreeNode treeNode = null;

        //递归结束条件
        if (data != null) {
            //二叉树的链表节点初始化
            treeNode = new TreeNode(data);

            //左子树赋值
            treeNode.leftTree = createBinaryTree(linkedList);

            //右子树赋值
            treeNode.rightTree = createBinaryTree(linkedList);
        }
        return treeNode;
    }

    //前序遍历
    public static void preOrderTraveral(TreeNode treeNode) {
        if (treeNode == null) {
            return;
        }
        //根左右
        System.out.print(treeNode.getDate() + " ");
        preOrderTraveral(treeNode.leftTree);
        preOrderTraveral(treeNode.rightTree);
    }

    //中序遍历
    public static void inOrderTraveral(TreeNode treeNode) {
        if (treeNode == null) {
            return;
        }
        //左根右
        inOrderTraveral(treeNode.leftTree);
        System.out.print(treeNode.getDate() + " ");
        inOrderTraveral(treeNode.rightTree);
    }

    //后序遍历
    public static void postOrderTraveral(TreeNode treeNode) {
        if (treeNode == null) {
            return;
        }
        //左右根
        postOrderTraveral(treeNode.leftTree);
        postOrderTraveral(treeNode.rightTree);
        System.out.print(treeNode.getDate() + " ");
    }
}


/**
 * 二叉树链表节点
 * 包含数据 左子树和右子树指针
 */
class TreeNode {
    //数据
    private int date;

    //左子树指针
    TreeNode leftTree;

    //右子树指
    TreeNode rightTree;

    public TreeNode(int date) {
        this.date = date;
    }

    public int getDate() {
        return date;
    }
}
//isEmpty()方法判断Map是否有内容（即new分配空间后是否put键值对），若没有内容则true，否则false
//== null是判断map是否为null（即是否new分配空间，和其中的键值对没关系），若没有内容则true，否则false