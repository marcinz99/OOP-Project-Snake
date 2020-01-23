package snake;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
    public static ArrayList<Integer> readHighScore() throws FileNotFoundException {
        try {
            File myObj = new File("src\\save\\save.txt");
            Scanner myReader = new Scanner(myObj);
            ArrayList<Integer> data = new ArrayList<>();
            int max = 9;
            while (myReader.hasNextLine() && max-->0) {
                String line = myReader.nextLine();
                data.add(Integer.parseInt(line));
            }
            myReader.close();
            if(data.size() < 9){
                throw new IllegalArgumentException("Corrupted save file.");
            }
            return data;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Couldn't find save file.");
        }
    }

    public static void writeHighScore(ArrayList<Integer> scores) {
        try {
            if(scores.size() != 9){
                throw new IllegalArgumentException("Corrupted save file.");
            }
            StringBuilder str = new StringBuilder();
            String ls = System.getProperty("line.separator");
            for(int a : scores){
                str.append(a);
                str.append(ls);
            }
            FileWriter myWriter = new FileWriter("src\\save\\save.txt");
            myWriter.write(str.toString());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("IOException occurred while saving scores");
        }
    }

    public static ArrayList<String> readMap(int mapId) {
        try {
            File myObj = new File(String.format("src\\save\\map%d.txt", mapId));
            Scanner myReader = new Scanner(myObj);
            ArrayList<String> data = new ArrayList<>();
            int max = 30;
            while (myReader.hasNextLine() && max-->0) {
                String line = myReader.nextLine();
                data.add(line);
            }
            myReader.close();
            if(data.size() < 30){
                throw new IllegalArgumentException("Corrupted save file.");
            }
            return data;
        } catch (FileNotFoundException e) {
            System.out.println("IOException occurred while reading map");
            return null;
        }
    }
}
