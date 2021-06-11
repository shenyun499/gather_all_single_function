package org.example.datastructure.offer;


import util.HashMap;
import util.Map;

/**
 * 剑指 Offer 07. 重建二叉树
 * 输入某二叉树的前序遍历和中序遍历的结果，请重建该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
 *
 * 例如，给出
 * 前序遍历 preorder = [3,9,20,15,7]    [1,0,3,2,4]
 * 中序遍历 inorder = [9,3,15,20,7]
 * 返回如下的二叉树：
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 * 限制：
 * 0 <= 节点个数 <= 5000
 *
 * 思路：前序遍历是 “中左右”，可以快速确定中间元素。中序遍历是 “左中右”，可以确定中间的元素是在左边还是右边。
 * 找到前序遍历后的第一个元素，在中序遍历那确定两边位置。采用递归方式
 * 1、递归结束条件：左边索引大于右边索引
 * 2、递归函数确定：buildTree(前序数组1，中序数组2，前序左边索引start，前序右边索引end，中序左边索引start,中序右边索引end)
 * 递归左边链接时，需要确定左边的右端值前序右边索引值。递归右边链接时，需要确定左端值前序左端索引值
 * 定义一个map，可以通过前序遍历值，快速找到中序遍历中的索引位置
 *
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2021-01-07
 */
public class Offer07 {
    Map<Integer, Integer> map = new HashMap<>();

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // 将中序遍历的值作为key，将索引作为value，方便快速定位索引
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);

        }
        // 调用递归函数
        return buildTree(preorder, inorder, 0, preorder.length - 1, 0, inorder.length - 1);
    }

    public TreeNode buildTree(int[] preorder, int[] inorder, int preStart, int preEnd, int inStart, int inEnd) {
        // 递归结束条件
        if (preStart > preEnd || inStart > inEnd) {
            return null;
        }
        // 构建节点
        int value = preorder[preStart];
        TreeNode head = new TreeNode(value);
        int pivot = map.get(value);
        // 递归关键点，确定preEnd的位置 pivot - inStart就是位移差值，用原始位置加上位移差值，就能确定preEnd的位置
        head.left = buildTree(preorder, inorder, preStart + 1, preStart + pivot - inStart, inStart, pivot -1);
        // 递归关键点，确定preStart的位置 这个位置就是上面preEnd位置+1个位置
        head.right = buildTree(preorder, inorder, preStart + pivot - inStart + 1, preEnd, pivot + 1, inEnd);
        return head;
    }

}
