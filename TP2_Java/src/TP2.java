import java.util.*;

public class TP2 {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        ArrayList<Book> shelf = new ArrayList<Book>();

        while(true) {
            displayMenu();
            manageMenu(keyboard, shelf);
        }
    }

    public static void displayMenu() {
        System.out.println("Menu");
        System.out.println("1. Afficher toute la bibliothèque");
        System.out.println("2. Afficher seulement les livres de la bibliothèque");
        System.out.println("3. Afficher seulement les magazines de la bibliothèque");
        System.out.println("4. Ajouter un livre dans la bibliothèque");
        System.out.println("5. Ajouter un magazine dans la bibliothèque");
        System.out.println("6. Trier la bibliothèque par ordre alphabétique");
        System.out.println("7. Trier la bibliothèque par date de publication");
        System.out.println("8. Quitter");
        System.out.println();
        System.out.println("Quel est votre choix ?");
    }

    public static void manageMenu(Scanner keyboard, ArrayList<Book> shelf) {
        int choice = keyboard.nextInt();
        keyboard.nextLine(); // drain the buffer
        switch(choice) {
            case 1:
                displayShelf(shelf);
                break;
            case 2:
                displayBooks(shelf);
                break;
            case 3:
                displayMagazines(shelf);
                break;
            case 4:
                addBook(shelf, keyboard);
                break;
            case 5:
                addMagazine(shelf, keyboard);
                break;
            case 6:
                Collections.sort(shelf, Book.TitleComparator);
                break;
            case 7:
                Collections.sort(shelf, Book.DateComparator);
                break;
            case 8:
                keyboard.close();
                System.exit(0);
        }
    }

    public static void addBook(List<Book> shelf, Scanner keyboard)
    {
        System.out.println("Veuillez rentrer les informations du livre à ajouter dans la bibliothèque.");
        System.out.print("Titre : ");
        String title = keyboard.nextLine();
        System.out.print("Année de publication : ");
        int publicationYear = keyboard.nextInt();
        System.out.print("Prix : ");
        float price = keyboard.nextFloat();
        System.out.print("Catégorie : ");
        keyboard.nextLine(); // drain the buffer
        String category = keyboard.nextLine();
        System.out.print("Note des lecteurs : ");
        float readersMark = keyboard.nextFloat();

        shelf.add(new Book(title, publicationYear, price, category, readersMark));

        System.out.printf("Le livre %s a été ajouté avec succès !\n", title);
    }

    public static void addMagazine(List<Book> shelf, Scanner keyboard)
    {
        System.out.println("Veuillez rentrer les informations du magazine à ajouter dans la bibliothèque.");
        System.out.print("Titre : ");
        String title = keyboard.nextLine();
        System.out.print("Année de publication : ");
        int publicationYear = keyboard.nextInt();
        keyboard.nextLine(); // drain the buffer
        System.out.println("Périodicité : ");
        String periodicity = keyboard.nextLine();
        System.out.print("Prix : ");
        float price = keyboard.nextFloat();
        System.out.print("Catégorie : ");
        keyboard.nextLine(); // drain the buffer
        String category = keyboard.nextLine();
        System.out.print("Note des lecteurs : ");
        float readersMark = keyboard.nextFloat();

        shelf.add(new Magazine(title, publicationYear, price, category, readersMark, periodicity));

        System.out.printf("Le livre %s a été ajouté avec succès !\n", title);
    }

    public static void displayShelf(List<Book> shelf) {
        boolean isElementPresent = false;
        System.out.println("Liste des ouvrages dans la bibliothèque :");
        for (Book b : shelf) {
            b.Display();
            isElementPresent = true;
        }
        if(!isElementPresent) {
            System.out.println("Aucun ouvrage dans la bibliotheque");
        }
    }

    public static void displayBooks(List<Book> shelf) {
        boolean isBookPresent = false;
        System.out.println("Liste des livres dans la bibliothèque :");
        for (Book b : shelf) {
            if (b.getClass() == Book.class) {
                b.Display();
                isBookPresent = true;
            }
        }
        if(!isBookPresent) {
            System.out.println("Aucun livre dans la bibliotheque");
        }
    }

    public static void displayMagazines(List<Book> shelf) {
        boolean isMagazinePresent = false;
        System.out.println("Liste des magazines dans la bibliothèque :");
        for (Book b : shelf) {
            if (b.getClass() == Magazine.class) {
                b.Display();
                isMagazinePresent = true;
            }
        }
        if(!isMagazinePresent) {
            System.out.println("Aucun magazine dans la bibliotheque");
        }
    }
}