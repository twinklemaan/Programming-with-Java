import library.Book;

public class LibraryProgram {
    public static void main(String[] args) {
        Book b = new Book("B1023", "Effective Java", "Joshua Bloch", 799.00);
        b.showDetails();
    }
}