package org.dingchuan;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述：演示公平锁，分别展示公平和不公平的情况，非公平锁会让现在持有锁的线程优先再次获取到锁。代码借鉴自Java并发编程实战手册2.7。
 *
 * @author dingchuan
 */
@Slf4j
public class FairAndUnfair {

  public static void main(String[] args) {
    PrintQueue printQueue = new PrintQueue();
    Thread thread[] = new Thread[10];
    for (int i = 0; i < 10; i++) {
      thread[i] = new Thread(new Job(printQueue), "Thread " + i);
    }

    for (int i = 0; i < 10; i++) {
      thread[i].start();
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  static class Job implements Runnable {
    private PrintQueue printQueue;
    public Job(PrintQueue printQueue) {
      this.printQueue = printQueue;
    }

    @Override
    public void run() {
      log.info("{}: Going to print a job\n", Thread.currentThread().getName());
      printQueue.printJob(new Object());
      log.info("{}: The document has been printed\n", Thread.currentThread().getName());
    }
  }


  static class PrintQueue {
    private final Lock queueLock = new ReentrantLock(true);
    public void printJob(Object document) {
      queueLock.lock();
      try {
        Long duration = (long) (Math.random() * 10000);
        log.info("{}: PrintQueue: Printing a Job during {} seconds\n", Thread.currentThread().getName(), (duration / 1000));
        Thread.sleep(duration);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        queueLock.unlock();
      }

      queueLock.lock();
      try {
        Long duration = (long) (Math.random() * 10000);
        log.info("{}: PrintQueue: Printing a Job during {} seconds\n", Thread.currentThread().getName(), (duration / 1000));
        Thread.sleep(duration);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        queueLock.unlock();
      }
    }
  }
}
