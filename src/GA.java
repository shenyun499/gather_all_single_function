/**
 * 遗传算法
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2020-10-24
 */

import java.util.ArrayList;
import java.util.Random;
public class GA {
/*
在这里采用无符号二进制整数来表示。x1,x2的取值范围为0到10,，
则可用两个4位二进制数来分别表示它们。而一个基因就是一个
8位二进制串。例如，基因型00001010所对应的表现型是：x1=0,x2=10
 */

    public static final int GENE_LENGTH = 4;//基因型長度

    public static final int MAX_X = 10;//x1最大取值

    public static final int MAX_Y = 10;//x2最大取值

    private int x, y;//变量

    private String gene;//基因组,由x,y组成。

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getGene() {
        return gene;
    }

    //写一个主类的构造函数,给定表现型构造染色体，x,y就是作为表现型的形参
    public GA(int x, int y) {

        if (x > MAX_X || y > MAX_Y || x < 0 || y < 0)//判断x,y是否超过最大值或者是否为负值
            return;

        this.x = x;
        this.y = y;

        String item = Integer.toBinaryString(x);//用字符串表示的二进制无符号整数。

        for (int i = item.length(); i < GENE_LENGTH / 2; i++) {//确保表现型是四位正数,
            item = "0" + item;
        }
        gene = item;//实现字符串赋值。

        item = Integer.toBinaryString(y);
        for (int i = item.length(); i < GENE_LENGTH / 2; i++) {
            item = "0" + item;
        }
        gene = gene + item;//成功将x,y拼接，构造成两个四位二进制数字的染色体

    }

    public GA(String gene) {
        if (gene.length() != GA.GENE_LENGTH)
            return;
        this.gene = gene;
        String xStr = gene.substring(0, GA.GENE_LENGTH / 2);
        String yStr = gene.substring(GA.GENE_LENGTH / 2);
        this.x = Integer.parseInt(xStr, 2);
        this.y = Integer.parseInt(yStr, 2);

    }

    public String toString() {
        return "x:" + x + "\ty:" + y + "\tgene:" + gene;
    }

    public void selfMutation(String newGene) {
        if (newGene.length() != GA.GENE_LENGTH)
            return;
        this.gene = newGene;
        String xStr = newGene.substring(0, GA.GENE_LENGTH / 2);
        String yStr = newGene.substring(GA.GENE_LENGTH / 2);
        this.x = Integer.parseInt(xStr, 2);
        this.y = Integer.parseInt(yStr, 2);
    }

    /*
      随机产生初始群体
      构造函数，首先随机产生两个-10到10之间的数作为X,Y，
      再调用构造函数生成新染色体并添加到初始种群中去。如此重复，直到种群规模达到要求。
     */
    public static ArrayList<GA> initGroup(int size) {

        ArrayList<GA> list = new ArrayList<GA>();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            //产生两个-10到10的随机数
            int x = random.nextInt() % 11;
            int y = random.nextInt() % 11;
            //x,y取绝对值，便于计算
            x = x < 0 ? (-x) : x;
            y = y < 0 ? (-y) : y;
            list.add(new GA(x, y));//将新的染色体添加到初始种群中。
        }
        return list;
    }

    //适应度计算
    public int calFitness() {//求最大值
        return x * x + y * y;
    }

    //选择运算
    public static ArrayList<GA> selector(ArrayList<GA> fatherGroup, int sonGroupSize) {

        ArrayList<GA> sonGroup = new ArrayList<GA>();
        int totalFitness = 0;//适应度初始化
        double[] fitness = new double[fatherGroup.size()];
        for (GA ga : fatherGroup) {
            totalFitness += ga.calFitness();//计算种群中每个个体的适应度,保存到数组中，然后求累加和。
        }
        int index = 0;
        //计算适应度
        for (GA ga : fatherGroup) {
            fitness[index] = ga.calFitness() / ((double) totalFitness);//数组中每个元素除以总的适应度。
            index++;
        }
        //计算累加适应度
        for (int i = 1; i < fitness.length; i++) {
            fitness[i] = fitness[i - 1] + fitness[i];
        }
        //轮盘赌选择
        for (int i = 0; i < sonGroupSize; i++) {
            Random random = new Random();
            double probability = random.nextDouble();
            int choose;
            for (choose = 1; choose < fitness.length - 1; choose++) {
                if (probability < fitness[choose])
                    break;
                sonGroup.add(new GA(fatherGroup.get(choose).getGene()));
            }

        }
        return sonGroup;
    }

    //交叉运算--one point
    public static ArrayList<GA> corssover(ArrayList<GA> fatherGroup, double probability) {

        ArrayList<GA> sonGroup = new ArrayList<GA>();
        sonGroup.addAll(fatherGroup);
        Random random = new Random();
        for (int k = 0; k < fatherGroup.size() / 2; k++) {
            if (probability > random.nextDouble()) {
                int i = 0, j = 0;
                do {
                    i = random.nextInt(fatherGroup.size());
                    j = random.nextInt(fatherGroup.size());
                } while (i == j);
                int position = random.nextInt(GA.GENE_LENGTH);
                String parent1 = fatherGroup.get(i).getGene();
                String parent2 = fatherGroup.get(j).getGene();
                String son1 = parent1.substring(0, position) + parent2.substring(position);
                String son2 = parent2.substring(0, position) + parent1.substring(position);
                sonGroup.add(new GA(son1));
                sonGroup.add(new GA(son2));
            }
        }
        return sonGroup;
    }

    //变异
    public static void mutation(ArrayList<GA> fatherGroup, double probability) {
        Random random = new Random();
        GA bestOne = GA.best(fatherGroup);
        fatherGroup.add(new GA(bestOne.getGene()));
        for (GA c : fatherGroup) {
            String newGene = c.getGene();
            for (int i = 0; i < newGene.length(); i++) {
                if (probability > random.nextDouble()) {
                    String newChar = newGene.charAt(i) == '0' ? "1" : "0";
                    newGene = newGene.substring(0, i) + newChar + newGene.substring(i + 1);
                }
            }
            c.selfMutation(newGene);
        }
    }

    public static GA best(ArrayList<GA> group) {
        GA bestOne = group.get(0);
        for (GA c : group) {
            if (c.calFitness() > bestOne.calFitness())
                bestOne = c;
        }
        return bestOne;
    }

    //测试
    public static void main(String[] args) {
        final int GROUP_SIZE = 4;//种群规模
        final double CORSSOVER_P = 0.6;//交叉概率
        final double MUTATION_P = 0.01;//变异概率
        ArrayList<GA> group = GA.initGroup(GROUP_SIZE);
        GA theBest;
        do {
            group = GA.corssover(group, CORSSOVER_P);
            GA.mutation(group, MUTATION_P);
            group = GA.selector(group, GROUP_SIZE);
            theBest = GA.best(group);
            System.out.println(theBest.calFitness());
        } while (theBest.calFitness() < 200);
    }
}