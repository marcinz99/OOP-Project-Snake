package snake;

public class Main {
    public static void main(String args[]){
        try{
            SettingsWindow.showSettingsWindow();
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            ex.getStackTrace();
        }
    }
}
