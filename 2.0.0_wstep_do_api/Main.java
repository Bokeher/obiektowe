
import java.util.ArrayList;
import java.util.Scanner;

class Main{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        GetData gd = new GetData();

        ArrayList<Person> persons = new ArrayList<>();

        int state = 1;
        do {
            System.out.print("Podaj imie: ");
            String imie = sc.nextLine();

            persons.add(gd.getData(imie));

            System.out.print("Chcesz podac jeszcze jedno imie [y/n]: ");
            String answer = sc.nextLine();

            if(answer.equals("n")) state = 0;
        } while (state == 1);
        
        for (Person person : persons) {
            System.out.println(person.toString());
        }

        // for(int i = 0; i < persons.size(); i++) {
        //     for(int j = 0; j < persons.size(); j++) {
        //         if(persons.get(i))
        //     }
        // }
    }
}