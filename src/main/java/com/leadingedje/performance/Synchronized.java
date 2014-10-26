package com.leadingedje.performance;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simulate a synchronized bottleneck
 */
public class Synchronized
{
  private static final Logger logger = LoggerFactory.getLogger(Synchronized.class);

  private Object object1 = new Object();
  private Object object2 = new Object();

  public synchronized void sleep(long ms) throws InterruptedException
  {
    Thread.sleep(ms);
  }

  public synchronized UUID[] uuids(int count)
  {
    return CpuIntensive.uuids(count);
  }

  public synchronized int fib(int count)
  {
    return CpuIntensive.fibonacci(count);
  }

  public void oneThenTwo()
  {
    synchronized(object1)
    {
      logger.info("Locked 1");
      synchronized(object2)
      {
        logger.info("Locked 2");
      }
    }
  }

  public void twoThenOne()
  {
    synchronized(object2)
    {
      logger.info("Locked 2");
      synchronized(object1)
      {
        logger.info("Locked 1");
      }
    }
  }
}
