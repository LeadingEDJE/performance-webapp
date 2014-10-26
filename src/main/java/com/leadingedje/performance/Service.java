package com.leadingedje.performance;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import jersey.repackaged.com.google.common.base.Stopwatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/")
@Singleton
@Produces(MediaType.TEXT_PLAIN)
public class Service
{
  private static final Logger logger = LoggerFactory.getLogger(Service.class);

  private ExecutorCompletionService<Integer> executor = new ExecutorCompletionService<>(
                                                                                        Executors
                                                                                            .newFixedThreadPool(10));
  private Synchronized synch = new Synchronized();

  @GET
  @Path("/fast")
  public String fast()
  {
    return Thread.currentThread().getName();
  }

  @GET
  @Path("/sleep/{ms}")
  public String sleep(@PathParam("ms") long ms) throws InterruptedException
  {
    Thread.sleep(ms);
    return Thread.currentThread().getName() + ": " + ms;
  }

  @GET
  @Path("/uuid/{count}")
  public String uuid(@PathParam("count") int count)
  {
    Stopwatch sw = new Stopwatch().start();
    CpuIntensive.uuids(count);
    logger.info("Took {} ms to generate {} UUIDs",sw.elapsed(TimeUnit.MILLISECONDS),count);
    return Thread.currentThread().getName() + ": " + count;
  }

  @GET
  @Path("/fib/{count}")
  public String fib(@PathParam("count") int count)
  {
    Stopwatch sw = new Stopwatch().start();
    int fib = CpuIntensive.fibonacci(count);
    logger.info("Took {} ms to generate {} number in fibonacci sequence: {}",
                sw.elapsed(TimeUnit.MILLISECONDS),
                count,
                fib);
    return Thread.currentThread().getName() + ": " + fib;
  }

  @GET
  @Path("/synch/sleep/{ms}")
  public String synchSleep(@PathParam("ms") long ms) throws InterruptedException
  {
    Stopwatch sw = new Stopwatch().start();
    synch.sleep(ms);
    logger.info("Took {} ms to sleep for {} ms",sw.elapsed(TimeUnit.MILLISECONDS),ms);
    return Thread.currentThread().getName() + ": " + ms;
  }

  @GET
  @Path("/synch/uuid/{count}")
  public String synchUuid(@PathParam("count") int count)
  {
    Stopwatch sw = new Stopwatch().start();
    synch.uuids(count);
    logger.info("Took {} ms to generate {} UUIDs",sw.elapsed(TimeUnit.MILLISECONDS),count);
    return Thread.currentThread().getName() + ": " + count;
  }

  @GET
  @Path("/synch/fib/{count}")
  public String synchFib(@PathParam("count") int count)
  {
    Stopwatch sw = new Stopwatch().start();
    int fib = synch.fib(count);
    logger.info("Took {} ms to generate {} number in fibonacci sequence: {}",
                sw.elapsed(TimeUnit.MILLISECONDS),
                count,
                fib);
    return Thread.currentThread().getName() + ": " + count;
  }

  @GET
  @Path("/executor/sleep/{ms}")
  public String executorSleep(@PathParam("ms") final long ms) throws InterruptedException, ExecutionException
  {
    Stopwatch sw = new Stopwatch().start();
    executor.submit(new Callable<Integer>()
    {
      @Override
      public Integer call() throws Exception
      {
        Thread.sleep(ms);
        return (int) ms;
      }
    }).get();
    logger.info("Took {} ms to sleep for {} ms",sw.elapsed(TimeUnit.MILLISECONDS),ms);
    return Thread.currentThread().getName() + ": " + ms;
  }

  @GET
  @Path("/executor/uuid/{count}")
  public String executorUuid(@PathParam("count") final int count) throws InterruptedException, ExecutionException
  {
    Stopwatch sw = new Stopwatch().start();
    executor.submit(new Callable<Integer>()
    {
      @Override
      public Integer call() throws Exception
      {
        return CpuIntensive.uuids(count).length;
      }
    }).get();
    logger.info("Took {} ms to generate {} UUIDs",sw.elapsed(TimeUnit.MILLISECONDS),count);
    return Thread.currentThread().getName() + ": " + count;
  }

  @GET
  @Path("/executor/fib/{count}")
  public String executorFib(@PathParam("count") final int count) throws InterruptedException, ExecutionException
  {
    Stopwatch sw = new Stopwatch().start();
    int fib = executor.submit(new Callable<Integer>()
    {
      @Override
      public Integer call() throws Exception
      {
        return CpuIntensive.fibonacci(count);
      }
    }).get();
    logger.info("Took {} ms to generate {} number in fibonacci sequence: {}",
                sw.elapsed(TimeUnit.MILLISECONDS),
                count,
                fib);
    return Thread.currentThread().getName() + ": " + count;
  }

  @GET
  @Path("/deadlock/1")
  public String deadlock1()
  {
    synch.oneThenTwo();
    return Thread.currentThread().getName();
  }

  @GET
  @Path("/deadlock/2")
  public String deadlock2()
  {
    synch.twoThenOne();
    return Thread.currentThread().getName();
  }

}
