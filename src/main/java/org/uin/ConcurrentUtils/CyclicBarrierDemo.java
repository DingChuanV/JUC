package org.uin.ConcurrentUtils;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author wanglufei
 * @description: TODO
 * @date 2022/3/21/5:31 PM
 */
public class CyclicBarrierDemo {

  public static void main(String[] args) {
    CyclicBarrier barrier = new CyclicBarrier(10,
        () -> System.out.println("飞机马上就要起飞了，各位特种兵请准备！"));
    for (int i = 0; i < 10; i++) {
      int finalI = i;
      new Thread(() -> {
        try {
          Thread.sleep((long) (2000 * new Random().nextDouble()));
          System.out.println(
              "玩家 " + finalI + " 进入房间进行等待... (" + barrier.getNumberWaiting() + "/10)");

          barrier.await();    //调用await方法进行等待，直到等待的线程足够多为止

          //开始游戏，所有玩家一起进入游戏
          System.out.println("玩家 " + finalI + " 进入游戏！");
        } catch (InterruptedException | BrokenBarrierException e) {
          e.printStackTrace();
        }
      }).start();
    }
  }
}
