# OOP.Assignement2

## Part one Ex2_1

In this assignment, you create several text files calculate the number of lines in these files. We will use three methods:
• Normal method without the use of threads,
• use of threads,
• Using ThreadPool 

Within each call of the methods over our file, we are measuring the elapsed time for each action.
In this part there is a class called 1_Ex2 and in it are written 4 functions:

### Function 1 - Create Text Files
Input:
n - a natural number representing the number of text files.
The file names will read like this:
.file_1, file_2 , …, file_n.
The number of lines in each file is a random number, obtained with the help of a usage class
In this class and in the seed and bound parameters.
Output: The function creates n text files on disk and returns an array
of the file names. Each line in the file contains at least 10 characters. 

### Function2 - getNumOfLines
Input: an array that contains the file names.
Output: total number of lines of the files.
In this function we used in Scanner class in nextLine() method, which is used to read Strings.

### Function 3 - getNumOfLinesThreads
Input: an array that contains the file names.
Output: total number of lines of the files.
In this function we used the CountLine class that we wrote. This class extends from the thread class.
In class that we wrote that call CountLine we have the run method that calculating total number of lines of the file. 
The function creates a new CountLine, with each of the file name ,activating it's run method by start(), and join each of the threads to get total line count sum of all files.

### Function 4 - getNumOfLinesThreadPool
Input: an array that contains the file names.
Output: total number of lines of the files.
In this function we used the CountLineCollable class that we wrote. This class implements from the Callable interface that similar to Runnable but a Callable interface that basically throws a checked exception and returns some results. This is one of the major differences between the upcoming Runnable interface where no value is being returned. In this interface, it simply computes a result else throws an exception if unable to do so..
The Callable interface is a generic interface containing a single call() method that returns a generic value.
The call() method method that calculating total number of lines of the file.
In our function getNumOfLinesThreadPool we are create thread pool with a number of threads equal to the number of files and Array list from Future objects,that represents the result of an asynchronous computation. When the asynchronous task is created, a Java Future object is returned. This Future object functions as a handle to the result of the asynchronous task. Once the asynchronous task completes, the result can be accessed via the Future object returned when the task was started. returned by the thread pool. It iterates over the list of file names and creates a CountLineCollable Callable object for each file name. Later on submits the Callable object to the thread pool and add the returned Future object in the list.
After we are iterates over the list of Future objects and retrieves the result of each Future object by call the get() method ,each result we add to the counter and
to finish the thread pool is shut down. 

### Function 5 - deleteAllFiles
In this function ew are deleting all the files that we are creating after we are running all the functions.

### Class diagram

<img src="https://user-images.githubusercontent.com/118892976/211491934-637d03b5-c3cd-4207-8d95-bf84b977128e.png" alt="drawing" width="500"/>


### Running times of methods 2,3,4

#### Time for 500 files:
##### Without Threads : 110 ms,
##### Using Threads: 69 ms,
##### Using ThreadPool: 59 ms.
 
#### Time for 1000 files:
##### Without Threads : 167 ms,
##### Using Threads: 109 ms,
##### Using ThreadPool: 79 ms.
 
#### Time for 5000 files:
##### Without Threads : 793 ms,
##### Using Threads: 457 ms,
##### Using ThreadPool: 454 ms.

We can notice that the calculation with threadPool is almost always faster than using threads. When we look at the rest of the calculations with 500 files, 1000 files and 5000 files, we can see that the calculations with threadPool are faster than using only one thread.
There are two reasons why using a threadPool can be faster than using threads:
The first one, is that we can use the same thread for multiple tasks instead of creating a completely new thread.
And the second one, is that we use submit() method that accepts a Callable instead of Runnable, so we can get the return value faster with get() 

## Part two Ex2_2 
In Java, there is no built-in option to give priority to an asynchronous task (a task that will be executed in a separate thread).
The language does allow you to set a priority for the Thread that runs the task, but not for the task itself.
Therefore, we are in a problem when we want to prioritize an asynchronous task using:
1. Interface <V> can be called in an interface that represents a task with a generic return value.
A task that is of type <V> Callable cannot be executed by a normal thread, cannot be assigned to it
indirectly preferred.
2. B-ThreadPool which is placed according to its tasks can be activated or <V<Callable, since it is not possible to determine
Priority to a specific Thread in the Executor's Threads collection.

In this part we created a new type that represents an asynchronous task with priority and a new ThreadPool type that supports tasks with
priority.
 
### We using two classes to do this: 

### Task Class
Represents an operation that can be run asynchronously and can return a value of some type (that is, will be defined). as a type
return generic (. It is not necessary for the operation to succeed and in case of failure, an exception will be thrown (E)

Extends FutureTask<E> because ecause in the CustomExecutor class
We make an exeute that receives a variable of type Runnable Future.
Implenents Callable<E> - A task that returns a result and may throw an exception.
And Comparable<Task<E>> - To compare between the priority task(The priorityblockingquque class
make the comparison according to the compareTo function that found in the Task class.
We do overrider to compareTo method because we changed the method to compare the priority values and not to compare the addresses in memory.

- There are 2 constructors, the first get 1 parameter of Callable,the second get 2 parametrs of Callable and TaskType.
 We use the superclass constructor that can be called from the first line of a subclass constructor by using the special keyword super() and passing appropriate parameters.
 
 - We have the function call because we implements from Callable class.
  The call() method is called in order to execute the asynchronous task,the method can return a result.
  If the task is executed asynchronously,the result is typically propagated back to the creator of the task via a Java Future.

 - We have 2 factorial methods that creates a Task with a default priority,the first method get 1 parameter of Callable,the second get 2 parametrs of Callable and    TaskType. 

  - The hashCode() method in Java is used to compute hash values of Java objects and to do run time of O(1).
 
### CustomExecutor Class
_CustomExecutor_ is a type of _ThreadPool_ that executes instances of _Task_ according to it's priority preferences. 
Concise explanation of some of class methods/fields. 
* _ThreadPoolExecutor_- An executor mechanism that uses a pool of threads to execute calls asynchronicously . 
* _PriorityBlockingQueue_ - An unbounded blocking queue that uses the same ordering rules as class _PriorityQueue_ and supplies blocking retrieval operations[^4]. 
It's _Comparator_ will be used to order our _Tasks_ by priority. 
* _submit()_- Submits either an asynchronic task or a _TaskType_ with an asynchronic task or a built-in _Task_ to the _PrioityBlockingQueue_ and returns a _Future_ representing the pending result[^3] after it's execution. 
* _maxPrio_ - Holds the value of the highest rated _Task_ that has been submitted to the queue. It is being updated in each call for _submit()_. 
* _gracefullyTerminate()_ - Activation will block insertion of new _Task_s to the queue, executing the remaining _Task_s and finishing all the _Task_s that are currently in the threads of the class. 

### Class diagram
 
<img src="https://user-images.githubusercontent.com/118892976/211532284-710cc6fa-77c2-4fea-99f0-3b9835ed1b8f.png" alt="drawing" width="500"/>

[^1]: Assignment 2 Part 2/Built-in-limitations 
[^2]: Priority range is 1-10, therefore 1 is the highest priority, and respectively 10 is the lowest one.
[^3]: JavaDoc/ConcurrentAbstractExecutorService/submit() 
[^4]: Oracle site 
 





 
