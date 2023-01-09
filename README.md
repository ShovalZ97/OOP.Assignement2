# OOP.Assignement2

## Part one  

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

<img src="https://user-images.githubusercontent.com/118892976/211375872-ad68ea9e-dffb-4ada-b337-bb94cf0512b0.png" alt="drawing" width="500"/>

### Running times of methods 2,3,4

Time for 500 files:
Without Threads : 110 ms
Using Threads: 69 ms
Using ThreadPool: 59 ms
 
Time for 1000 files:
Without Threads : 167 ms
Using Threads: 109 ms
Using ThreadPool: 79 ms
 
Time for 5000 files:
Without Threads : 793 ms
Using Threads: 457 ms
Using ThreadPool: 454 ms

We can notice that the calculation with threadPool is almost always faster than using threads. When we look at the rest of the calculations with 500 files, 1000 files and 5000 files, we can see that the calculations with threadPool are faster than using only one thread.
There are two reasons why using a threadPool can be faster than using threads:
The first one, is that we can use the same thread for multiple tasks instead of creating a completely new thread.
And the second one, is that we use submit() method that accepts a Callable instead of Runnable, so we can get the return value faster with get() 
 





 
