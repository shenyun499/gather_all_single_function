package org.example.datastructure.leecode.treesort;

import org.junit.Test;
import sun.reflect.generics.tree.Tree;

import util.Arrays;
import util.LinkedList;

/**
 * @Description
 * 1、二叉树的前中后序深度优先遍历
 * 2、二叉树的层次广度优先遍历
 * 3、递归实现
 * @Author xuexue
 * @Date 2020/2/820:32
 */
public class BinaryTreeSortDG {
    @Test
    public void binaryTreeOrderTest() {
        BinaryTreeSortDG binaryTreeSortDG = new BinaryTreeSortDG();
        //数据 根据前序遍历来制造的数据
        LinkedList<Integer> linkedList = new LinkedList<>(
                Arrays.asList(new Integer[]{3,2,9,null,null,10,null, null,8,null,4}));

        //创建二叉树
        TreeNode binaryTree = binaryTreeSortDG.createBinaryTree(linkedList);
        System.out.print("前序遍历：");
        binaryTreeSortDG.preOrderTraveral(binaryTree);
        System.out.println("\n------------------------------------");

        System.out.print("中序遍历：");
        binaryTreeSortDG.btOrderTraveral(binaryTree);
        System.out.println("\n------------------------------------");

        System.out.print("后序遍历：");
        binaryTreeSortDG.ltOrderTraveral(binaryTree);
    }

    //前序遍历创建二叉树
    public TreeNode createBinaryTree(LinkedList<Integer> linkedList) {
        if (linkedList.isEmpty()) {
            return null;
        }
        //拿到数据并清除第一个数据
        Integer value = linkedList.removeFirst();
        TreeNode treeNode = null;
        //递归结束条件
        if (value != null) {
            treeNode = new TreeNode(value);
            treeNode.l = createBinaryTree(linkedList);
            treeNode.r = createBinaryTree(linkedList);
        }
        return treeNode;
    }

    //前序遍历
    public void preOrderTraveral(TreeNode treeNode) {
        if (treeNode == null) {
            return;
        }
        System.out.print(treeNode.value);
        preOrderTraveral(treeNode.l);
        preOrderTraveral(treeNode.r);
    }

    //中序遍历
    public void btOrderTraveral(TreeNode treeNode) {
        if (treeNode == null) {
            return;
        }
        btOrderTraveral(treeNode.l);
        System.out.print(treeNode.value);
        btOrderTraveral(treeNode.r);
    }
    //后序遍历
    public void ltOrderTraveral(TreeNode treeNode) {
        if (treeNode == null) {
            return;
        }
        ltOrderTraveral(treeNode.l);
        ltOrderTraveral(treeNode.r);
        System.out.print(treeNode.value);
    }


}


/**
 * 二叉树的节点
 */
class TreeNode {
    TreeNode l;
    TreeNode r;
    int value;
    TreeNode (int x) {
        this.value = x;
    }
}

