import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private String[] options;
    public Menu(String[] options) {
        this.options = options;
    }

    public int getChoiceFromMenu(boolean showHeader) {
        do {
            printMenu(showHeader);

            Scanner sc = new Scanner(System.in);
            System.out.print("Wybor: ");
            String line = sc.nextLine();
            System.out.println();
            
            try {
                int choice = Integer.parseInt(line);

                if(choice <= options.length && choice > 0) {
                    return choice;
                }
            } catch (Exception e) {}
        } while(true);
    }

    private void printMenu(boolean showHeader) {
        if(showHeader) System.out.println("-=-=-=-=-=- Menu -=-=-=-=-=-");
        for (String string : options) {
            System.out.println(string);
        }
    } 
}
