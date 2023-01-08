import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Ex2 {
    public class Ex2_1 {
        public static String[] createTextFiles(int n, int seed, int bound) {
            String[] myFiles = new String[n];
            Random rand = new Random(seed);
            for (int i = 0; i < n; i++) {
                int lines = rand.nextInt(bound);
                File txtFile = new File("file_" + (i+1) + ".txt");
                try {
                    if (!(txtFile.createNewFile())) {
                        System.out.println("File already exists");
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                try {
                    FileWriter writeText = new FileWriter(txtFile);
                    for (int j = 0; j < lines; j++) {
                        writeText.write("Hello World Peace And Love Everyone\n");
                    }
                    writeText.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                System.out.println(txtFile.getName() + " created with " + lines + " lines");
                myFiles[i] = txtFile.getName();
            }
            return myFiles;
        }
        public static int getNumOfLines(String[] fileNames) {
            int countLine=0;
            for (String nameFile : fileNames) {
                try {
                    File myFile = new File(nameFile);
                    Scanner fileobj = new Scanner(myFile);
                    while (fileobj.hasNextLine()) {
                        countLine++;
                        fileobj.nextLine();
                    }
                    fileobj.close();
                } catch (FileNotFoundException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
            return countLine;
        }
        public static int getNumOfLinesThreads(String[] fileNames){
            int countLine=0;
            CountLine[] threadFile = new CountLine[fileNames.length];
            for (int i=0;i<fileNames.length;i++) {
                 threadFile[i] = new CountLine(fileNames[i]);
                 threadFile[i].start();
            }
            for (int i=0;i<fileNames.length;i++) {
                try {
                    //The join method is defined in the Thread class
                    //join() method is quite useful for inter-thread synchronization
                    threadFile[i].join();
                    countLine += threadFile[i].getLines();
                } catch (InterruptedException e) {
                    //RuntimeException is the superclass of those exceptions that can be thrown during the normal operation of the Java Virtual Machine.
                    //A method is not required to declare in its throws clause any subclasses of RuntimeException that might be thrown during the execution of the method but not caught.
                    throw new RuntimeException(e);
                }
            }
            return countLine;
        }
       public static  int getNumOfLinesThreadPool(String[] fileNames){
           ExecutorService threadFilePool = Executors.newFixedThreadPool(fileNames.length);
           int countLine = 0;
           List<Future<Integer>> listThreadFile = new ArrayList<Future<Integer>>();
           for (int i=0;i<fileNames.length;i++) {
               Callable<Integer> threadFile = new CountLineCollable(fileNames[i]);
               listThreadFile.add(threadFilePool.submit(threadFile));
           }
           for (int i=0;i<fileNames.length;i++) {
               try{
                   countLine+= listThreadFile.get(i).get();
               } catch (ExecutionException e) {
                   throw new RuntimeException(e);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
           }
           threadFilePool.shutdown();
           return countLine;

       }
        public static void deleteAllFiles(String[] filenames){
            for(int i=0;i<filenames.length;i++) {
                File obj = new File("file_"+ (i+1) +".txt");
                if (obj.delete()) {
                    filenames[i]=null;
                } else {
                    System.out.println("Can't delete file_"+(i+1)+".txt");
                }
            }
            System.out.println("files deleted");
        }
        @Override
        public String toString() {
            return super.toString();
        }
    }
    public static void main(String[] args) {
        String[] filesArr = new String[1000];
        filesArr = Ex2_1.createTextFiles(filesArr.length, 1, 500);
        System.out.println("num of getNumOfLines:"+Ex2_1.getNumOfLines(filesArr));
        System.out.println("num of getNumOfLinesThreads:"+Ex2_1.getNumOfLinesThreads(filesArr));
        System.out.println("num of getNumOfLinesThreadPool:"+Ex2_1.getNumOfLinesThreadPool(filesArr));

        long startTime1 = System.currentTimeMillis();
        int numofLines = Ex2_1.getNumOfLines(filesArr);
        System.out.println("Time of getNumOfLines:"+(System.currentTimeMillis()-startTime1)+" ms");

        long startTime2 = System.currentTimeMillis();
        int numOfLinesThreads = Ex2_1.getNumOfLinesThreads(filesArr);
        System.out.println("Time of getNumOfLinesThreads:"+(System.currentTimeMillis()-startTime2)+" ms");

        long startTime3 = System.currentTimeMillis();
        int numOfLinesThreadPool = Ex2_1.getNumOfLinesThreadPool(filesArr);
        System.out.println("Time of getNumOfLinesThreadPool:"+(System.currentTimeMillis()-startTime3)+" ms");

        Ex2_1.deleteAllFiles(filesArr);





    }
}

