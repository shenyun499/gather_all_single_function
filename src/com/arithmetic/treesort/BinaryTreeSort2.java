package com.arithmetic.treesort;


import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

/**
 * @Description
 * 非递归实现二叉树广度遍历排序
 * @Author xuexue
 * @Date 2019/11/16 11:27
 */
public class BinaryTreeSort2 {

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
    public static void preOrderTraveral(TreeNode root) {
        Stack<TreeNode> treeNodeStack = new Stack<>();
        TreeNode treeNode = root;
        while (treeNode != null || !treeNodeStack.isEmpty()) {
            //左子树遍历，并入栈
            while (treeNode != null) {
                System.out.print(treeNode.getDate() + " ");
                treeNodeStack.push(treeNode);
                treeNode = treeNode.leftTree;
            }

            //如果节点没有左孩子，则弹出栈顶节点，访问节点右孩子
            if (!treeNodeStack.isEmpty()) {
                TreeNode popTreeNode = treeNodeStack.pop();//栈的最重要，回溯性
                treeNode = popTreeNode.rightTree;
            }
        }
    }

    //中序遍历
    public static void inOrderTraveral(TreeNode root) {
        Stack<TreeNode> treeNodeStack = new Stack<>();
        TreeNode treeNode = root;

        while (treeNode != null || !treeNodeStack.isEmpty()) {
            //遍历左子树，并入栈
            while (treeNode != null) {
                treeNodeStack.push(treeNode);
                treeNode = treeNode.leftTree;
            }

            //当节点的左子树为null时，弹出节点并输出数据
            if (!treeNodeStack.isEmpty()) {
                TreeNode popTreeNode = treeNodeStack.pop();
                System.out.print(popTreeNode.getDate() + " ");
                treeNode = popTreeNode.rightTree;
            }

        }
    }

    //后序遍历
    public static void postOrderTraveral(TreeNode treeNode) {
    }

    @Test
    public void t() {
        Scanner sc = new Scanner(System.in);
        sc.hasNext();
        String[] a = {"a/b", "a/c", "c/d", "b/a"};
        Arrays.sort(a);
        for (String s :a) {
            System.out.println(s);
        }
        String b = "aaa";
        System.out.println(b.startsWith("a"));
    }
}
