package org.uin.deadlock.avoiddeadlock;

/**
 * 解决死锁问题-避免死锁（调整锁的获取顺序）
 *
 * @author wanglufei
 */
public class TransferMoney implements Runnable {

  int flag;
  static Account a = new Account(500);
  static Account b = new Account(500);

  static class Account {

    public Account(int balance) {
      this.balance = balance;
    }

    int balance;
  }

  @Override
  public void run() {
    if (flag == 1) {
      transferMoney(a, b, 200);
    }
    if (flag == 0) {
      transferMoney(b, a, 200);
    }
  }

  /**
   * @param from   转账的用户
   * @param to     被转账的用户
   * @param amount 转账的金额
   * @author wanglufei
   * @date 2022/7/31 12:32 AM
   */
  public static void transferMoney(Account from, Account to, int amount) {
    // 空引用的哈希码为零
    int fromHash = System.identityHashCode(from);
    int toHash = System.identityHashCode(to);
    if (fromHash < toHash) {
      //先获取两把锁，然后开始转账
      synchronized (to) {
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
        synchronized (from) {
          if (from.balance - amount < 0) {
            System.out.printf("余额不足，转账失败！");
            return;
          }
          from.balance -= amount;
          to.balance += amount;
          System.out.println("成功转账，" + amount + "元");
        }
      }
    } else if (fromHash > toHash) {
      synchronized (to) {
        synchronized (from) {
          if (from.balance - amount < 0) {
            System.out.println("余额不足，转账失败。");
            return;
          }
          from.balance -= amount;
          to.balance += amount;
          System.out.println("成功转账" + amount + "元");
        }
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    TransferMoney r1 = new TransferMoney();
    TransferMoney r2 = new TransferMoney();
    r1.flag = 1;
    r2.flag = 0;
    Thread t1 = new Thread(r1);
    Thread t2 = new Thread(r2);
    t1.start();
    t2.start();
    //等待t1线程死掉
    t1.join();
    //等待t2线程死掉
    t2.join();
    System.out.println("a的余额" + a.balance);
    System.out.println("b的余额" + b.balance);
  }
}
