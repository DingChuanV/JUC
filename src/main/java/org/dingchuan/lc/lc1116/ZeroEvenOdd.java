package org.dingchuan.lc.lc1116;

import java.util.function.IntConsumer;


/**
 * @author dingchuan
 */
// 现有函数 printNumber 可以用一个整数参数调用，并输出该整数到控制台。
//
//例如，调用 printNumber(7) 将会输出 7 到控制台。
//给你类 ZeroEvenOdd 的一个实例，该类中有三个函数：zero、even 和 odd 。ZeroEvenOdd 的相同实例将会传递给三个不同线程：
//
//线程 A：调用 zero() ，只输出 0
//线程 B：调用 even() ，只输出偶数
//线程 C：调用 odd() ，只输出奇数
//修改给出的类，以输出序列 "010203040506..." ，其中序列的长度必须为 2n 。
//
//实现 ZeroEvenOdd 类：
//
//ZeroEvenOdd(int n) 用数字 n 初始化对象，表示需要输出的数。
//void zero(printNumber) 调用 printNumber 以输出一个 0 。
//void even(printNumber) 调用printNumber 以输出偶数。
//void odd(printNumber) 调用 printNumber 以输出奇数。
// 输入：n = 2
//输出："0102"
//解释：三条线程异步执行，其中一个调用 zero()，另一个线程调用 even()，最后一个线程调用odd()。正确的输出为 "0102"。
public class ZeroEvenOdd {

  private int n;

  private volatile int flag = 1;

  public ZeroEvenOdd(int n) {
    this.n = n;
  }

  // printNumber.accept(x) outputs "x", where x is an integer.
  public void zero(IntConsumer printNumber) throws InterruptedException {
    for (int i = 1; i <= n; i++) {
      synchronized (this) {
        while (flag % 2 == 0) {
          wait();
        }
        printNumber.accept(0);
        notifyAll();
      }
    }
  }

  public void even(IntConsumer printNumber) throws InterruptedException {
    for (int i = 2; i <= n; i += 2) {
      synchronized (this) {
        while (flag % 4 != 0) {
          wait();
        }
        printNumber.accept(i);
        notifyAll();
      }
    }
  }

  public void odd(IntConsumer printNumber) throws InterruptedException {
    for (int i = 1; i <= n; i += 2) {
      synchronized (this) {
        while (flag % 4 != 2) {
          wait();
        }
        printNumber.accept(i);
        flag++;
        notifyAll();
      }
    }
  }
}
