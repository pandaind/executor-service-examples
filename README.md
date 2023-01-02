# executor-service-examples

#### 1. What is an Executor and how does it differ from a traditional java.lang.Thread?
An Executor is an object that is responsible for executing Runnable tasks. It differs from a Thread in that it is not a direct subclass of Thread, and it is designed to decouple task submission from task execution. This allows you to reuse threads and to more easily configure and manage the thread pool.

#### How can you use an Executor to execute a Runnable or Callable task?
To execute a Runnable or Callable task using an Executor, you can call the execute or submit method, respectively. For example:
~~~ java
  Executor executor = getExecutor(); // obtain an Executor instance

  Runnable task = () -> System.out.println("Running a task");
  executor.execute(task);

  Callable<Integer> task = () -> 42;
  Future<Integer> future = executor.submit(task);
~~~

#### 2. What is an ExecutorService and what are some common methods that are available on it?
An ExecutorService is a subinterface of Executor that adds support for managing the lifecycle of the tasks it executes, as well as the ability to submit tasks that return results. Some common methods that are available on ExecutorService include:
* submit: submits a task for execution and returns a Future representing the result of the task
* invokeAll: executes a collection of tasks and returns a list of Future objects representing the results of the tasks
* invokeAny: executes a collection of tasks and returns the result of the first completed task
* shutdown: initiates the process of shutting down the executor, allowing it to complete all currently running tasks before terminating
* shutdownNow: attempts to stop all actively executing tasks and returns a list of tasks that were in progress

#### 3. What is a ScheduledExecutorService and how can you use it to schedule tasks for future execution?
A ScheduledExecutorService is a subinterface of ExecutorService that adds support for scheduling tasks to be executed at some point in the future. You can use the schedule method to schedule a task to be executed once, or you can use the scheduleAtFixedRate or scheduleWithFixedDelay methods to schedule a task to be executed repeatedly with a fixed delay or period, respectively.


#### 4. How can you shut down an ExecutorService gracefully?
To shut down an ExecutorService gracefully, you can call the shutdown method. This will allow the executor to complete all currently running tasks before terminating. If you need to stop all tasks immediately, you can call the shutdownNow method, which will attempt to stop all actively executing tasks and return a list of tasks that were in progress.

#### 5. How can you create a fixed-size thread pool using Executors.newFixedThreadPool? How about a single-threaded executor using Executors.newSingleThreadExecutor?
To create a fixed-size thread pool using Executors.newFixedThreadPool, you can call the method and pass it the number of threads you want in the pool:
~~~java
ExecutorService executor = Executors.newFixedThreadPool(4); // create a thread pool with 4 threads
~~~
To create a single-threaded executor using Executors.newSingleThreadExecutor, you can simply call the method without any arguments:

~~~java
ExecutorService executor = Executors.newSingleThreadExecutor(); // create a single-threaded executor
~~~

#### 6. How can you use an ExecutorCompletionService to manage the results of multiple asynchronous tasks?
An ExecutorCompletionService is a utility class that manages the results of asynchronous tasks that are executed using an Executor. You can use it to submit tasks for execution and retrieve their results as they become available, rather than waiting for all tasks to complete.

#### 7. What is a ThreadFactory and how can you use it to customize the threads that are created by an Executor?
A ThreadFactory is a factory that creates threads. It is a useful way to customize the threads that are created by an Executor, which is an object that executes submitted Runnable tasks.

To use a ThreadFactory to customize the threads that are created by an Executor, you can create a class that implements the ThreadFactory interface and override its newThread method. This method takes a Runnable task as a parameter and returns a new Thread object that will execute that task. You can then use your custom ThreadFactory to create a new Executor by passing it to the Executor's constructor.

For example:
~~~java
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class CustomThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        // Create a new thread and customize it here
        Thread t = new Thread(r);
        t.setName("MyCustomThread");
        t.setPriority(Thread.MAX_PRIORITY);
        return t;
    }
}

Executor executor = Executors.newFixedThreadPool(10, new CustomThreadFactory());

~~~

This creates a new Executor with a fixed thread pool of size 10, and uses the custom ThreadFactory to create the threads for the pool. All threads created by this Executor will have the name "MyCustomThread" and the maximum priority.