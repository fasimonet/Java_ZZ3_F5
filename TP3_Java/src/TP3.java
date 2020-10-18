import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TP3 {
    public static void main(String[] args)
    {
        Scanner keyboard = new Scanner(System.in);
        Parser parser = new Parser();
        ArrayList<MetroStop> stops = new ArrayList<>();

        while(true) {
            displayMenu();
            stops = manageMenu(keyboard, parser, stops);
        }
    }

    public static void displayMenu() {
        System.out.println("Menu");
        System.out.println("1. Afficher toutes les stations");
        System.out.println("2. Parser un fichier de station de transport en commun");
        System.out.println("3. Trier la liste de stations par id");
        System.out.println("4. Trier la liste de stations par arrondissement puis par nom de station");
        System.out.println("5. Quitter");
        System.out.println();
        System.out.println("Quel est votre choix ?");
    }

    public static ArrayList<MetroStop> manageMenu(Scanner keyboard, Parser parser, ArrayList<MetroStop> stops) {

        try {
            int choice = keyboard.nextInt();
            keyboard.nextLine(); // drain the buffer

            switch(choice) {
                case 1:
                    displayStops(stops);
                    break;
                case 2:
                    try {
                        stops = parser.parse("ratp_arret.csv", "#");
                    } catch(Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    Collections.sort(stops, MetroStop.IdComparator);
                    System.out.println("Liste d'arrets de metro triee avec succes");
                    break;
                case 4:
                    Collections.sort(stops, MetroStop.DistrictComparator);
                    System.out.println("Liste d'arrets de metro triee avec succes");
                    break;
                default:
                    keyboard.close();
                    System.exit(0);
            }

            return stops;
        } catch (Exception e) {
            System.out.println("Vous devez rentrer un chiffre entre 1 et 5");
            keyboard.nextLine(); // drain the buffer
            manageMenu(keyboard, parser, stops);
        }

        return null;
    }

    public static void displayStops(List<MetroStop> stops) {
        boolean isElementPresent = false;
        System.out.println("Liste des stations repertoriees :");
        for (MetroStop ms : stops) {
            ms.Display();
            isElementPresent = true;
        }
        if(!isElementPresent) {
            System.out.println("Aucune station repertoriee");
        }
    }
}
