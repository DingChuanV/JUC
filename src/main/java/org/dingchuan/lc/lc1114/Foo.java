package org.dingchuan.lc.lc1114;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dingchuan
 */

// 给你一个类：
//
//public class Foo {
//  public void first() { print("first"); }
//  public void second() { print("second"); }
//  public void third() { print("third"); }
//}
//三个不同的线程 A、B、C 将会共用一个 Foo 实例。
//
//线程 A 将会调用 first() 方法
//线程 B 将会调用 second() 方法
//线程 C 将会调用 third() 方法
//请设计修改程序，以确保 second() 方法在 first() 方法之后被执行，third() 方法在 second() 方法之后被执行。
//
//提示：
//
//尽管输入中的数字似乎暗示了顺序，但是我们并不保证线程在操作系统中的调度顺序。
//你看到的输入格式主要是为了确保测试的全面性。
//
//
//示例 1：
//
//输入：nums = [1,2,3]
//输出："firstsecondthird"
//解释：
//有三个线程会被异步启动。输入 [1,2,3] 表示线程 A 将会调用 first() 方法，线程 B 将会调用 second() 方法，线程 C 将会调用 third() 方法。正确的输出是 "firstsecondthird"。
public class Foo {

  private AtomicInteger firstJobDone = new AtomicInteger(0);
  private AtomicInteger secnodJobDone = new AtomicInteger(0);

  public Foo() {

  }

  public void first(Runnable printFirst) throws InterruptedException {
    // printFirst.run() outputs "first". Do not change or remove this line.
    printFirst.run();
    firstJobDone.incrementAndGet();
  }

  public void second(Runnable printSecond) throws InterruptedException {
    while (firstJobDone.get()!=1){
      // wait
    }
    // printSecond.run() outputs "second". Do not change or remove this line.
    printSecond.run();
    secnodJobDone.incrementAndGet();
  }

  public void third(Runnable printThird) throws InterruptedException {
    while (secnodJobDone.get()!=1){
      // wa it
    }
    // printThird.run() outputs "third". Do not change or remove this line.
    printThird.run();
  }

  public static void main(String[] args) {
    Foo foo = new Foo();
    new Thread(() -> {
      try {
        foo.first(() -> System.out.println("first"));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    },"thread1").start();
    new Thread(() -> {
      try {
        foo.second(() -> System.out.println("second"));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    },"thread2").start();
    new Thread(() -> {
      try {
        foo.third(() -> System.out.println("third"));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    },"thread3").start();
  }
}
