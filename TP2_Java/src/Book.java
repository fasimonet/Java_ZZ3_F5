import java.util.Comparator;

public class Book {
    public String title;
    public int publicationYear;
    public float price;
    public String category;
    public float readersMark;

    public Book(String title, int publicationYear, float price, String category, float readersMark) {
        this.title = title;
        this.publicationYear = publicationYear;
        this.price = price;
        this.category = category;
        this.readersMark = readersMark;
    }

    public void Display() {
        System.out.printf("Livre '%s' sorti en %d au prix de %.2f€ dans la categorie %s que les lecteurs ont noté %.2f/20\n", title, publicationYear, price, category, readersMark);
    }

    public static Comparator<Book> TitleComparator = new Comparator<Book>() {
        @Override
        public int compare(Book b1, Book b2) {
            String title1 = b1.title.toUpperCase();
            String title2 = b2.title.toUpperCase();

            return title1.compareTo(title2);
        }
    };

    public static Comparator<Book> DateComparator = new Comparator<Book>() {
        @Override
        public int compare(Book b1, Book b2) {
            return b1.publicationYear - b2.publicationYear;
        }
    };
}