package com.leadingedje.performance;

import java.util.UUID;

/**
 * Simulate a synchronized bottleneck
 */
public class Synchronized
{
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
}
