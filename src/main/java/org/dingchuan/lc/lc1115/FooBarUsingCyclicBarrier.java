package org.dingchuan.lc.lc1115;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class FooBarUsingCyclicBarrier {

  private int n;
  private CountDownLatch a;
  private CyclicBarrier barrier;// 使用CyclicBarrier保证任务按组执行

  public FooBarUsingCyclicBarrier(int n) {
    this.n = n;
    a = new CountDownLatch(1);
    barrier = new CyclicBarrier(2);// 保证每组内有两个任务
  }

  public void foo(Runnable printFoo) throws InterruptedException {

    try {
      for (int i = 0; i < n; i++) {
        printFoo.run();
        a.countDown();// printFoo方法完成调用countDown
        barrier.await();// 等待printBar方法执行完成
      }
    } catch (Exception e) {
    }
  }

  public void bar(Runnable printBar) throws InterruptedException {

    try {
      for (int i = 0; i < n; i++) {
        a.await();// 等待printFoo方法先执行
        printBar.run();
        a = new CountDownLatch(1); // 保证下一次依旧等待printFoo方法先执行
        barrier.await();// 等待printFoo方法执行完成
      }
    } catch (Exception e) {
    }
  }
}
