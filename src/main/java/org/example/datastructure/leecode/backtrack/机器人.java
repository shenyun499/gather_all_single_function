package org.example.datastructure.leecode.backtrack;

import util.LinkedList;

/**
 * @Description
 * @Author xuexue
 * @Date 2020/2/1921:27
 */
public class 机器人 {
    public static void main(String[] args) {
        机器人 n = new 机器人();
        int i = n.movingCount(3, 3, 1);
        System.out.println(i);
    }
        public int movingCount(int m, int n, int k) {
            int sum = 0;
            //用队列
            LinkedList<int[]> queue = new LinkedList<>();
            boolean[][] bol = new boolean[m][n];
            queue.add(new int[] {0, 0});
            while (!queue.isEmpty()) {
                for (int i = 0; i < queue.size(); i++) {
                    int[] arr = queue.removeFirst();
                    int x = arr[0];
                    int y = arr[1];
                    if (!bol[x][y]) {
                        bol[x][y] = true;
                        if (num(x, y) <= k) {
                            sum++;
                            if (x < m - 1) {
                                queue.add(new int[] {x + 1, y});
                            }
                            if (y < n - 1) {
                                queue.add(new int[] {x, y + 1});
                            }
                        }
                    }
                }
            }
            return sum;
        }

        //计算和值
        public int num(int x, int y) {
            int temp = 0;
            while (x > 0) {
                temp += x % 10;
                x /= 10;
            }
            while (y > 0) {
                temp += y % 10;
                y /= 10;
            }
            return temp;
        }

    }