package org.example.datastructure.com.Test;

/**
 * @Description
 * @Author xuexue
 * @Date 2019/9/1911:46
 */
public class Interrupt {

    public static void main(String[] args) {

        myRunable mye = new myRunable();

        Thread t1 = new Thread(mye);

        t1.start();



        for (int i = 0; i <20 ; i++) {

            System.err.println("A="+i);
            if (Thread.interrupted()){
                break;

            }

            try {

                Thread.sleep(200);

            } catch (InterruptedException e) {

                e.printStackTrace();

            }

            if (i==10){

                mye.fin=false;

            }

        }

    }

}

class myRunable implements Runnable{

    public boolean fin=true;

    public myRunable(){

        fin=true;

    }

    @Override

    public void run() {

        int i=0;

        while (fin){

            //
            // System.out.println("B=="+(i++));

        }

        try {

            Thread.sleep(200);

        } catch (InterruptedException e) {

            e.printStackTrace();

        }

    }

}

