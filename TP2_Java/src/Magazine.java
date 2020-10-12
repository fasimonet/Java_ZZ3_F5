public class Magazine extends Book {
    public String periodicity;

    public Magazine(String title, int publicationyear, float price, String category, float readersMark, String periodicity) {
        super(title, publicationyear, price, category, readersMark);
        this.periodicity = periodicity;
    }

    @Override
    public void Display() {
        System.out.printf("Magazine '%s' qui est un %s sorti en %d au prix de %.2f€ dans la categorie %s que les lecteurs ont noté %.2f/20\n", title, periodicity, publicationYear, price, category, readersMark);
    }
}