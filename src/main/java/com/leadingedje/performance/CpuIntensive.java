package com.leadingedje.performance;

import java.util.UUID;

/**
 * Simulate doing something CPU intensive.
 */
public class CpuIntensive
{
  public static UUID[] uuids(int count)
  {
    UUID[] uuids = new UUID[count];
    for(int i = 0; i < count; i++)
    {
      uuids[i] = UUID.randomUUID();
    }
    return uuids;
  }

  public static int fibonacci(int n)
  {
    if(n == 0)
      return 0;
    else if(n == 1)
      return 1;
    else
      return fibonacci(n - 1) + fibonacci(n - 2);
  }
}
