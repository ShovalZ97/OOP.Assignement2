import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class CountLineCollable implements Callable<Integer> {
    String nameFile = "";
    public CountLineCollable(String nameFile) {
        this.nameFile = nameFile;
    }
    @Override
    public Integer call() throws Exception {
        int countLine=0;
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
        return countLine;
    }
}