class Book {
    private final String isbn; // cannot be changed once assigned
    private String title;
    private String author;
    private double price;

    Book(String isbn, String title, String author, double price) {
        this.isbn = isbn; // allowed: initialization in constructor
        this.title = title;
        this.author = author;
        this.price = price;
    }

    void showDetails() {
        System.out.println("ISBN   : " + isbn);
        System.out.println("Title  : " + title);
        System.out.println("Author : " + author);
        System.out.println("Price  : Rs. " + price);
    }

    // Uncommenting this would cause a compile-time error:
    // void updateIsbn(String newIsbn) {
    //     isbn = newIsbn;   // ERROR: cannot assign a value to final variable
    // }
}

public class LibraryBookProgram {
    public static void main(String[] args) {
        Book b = new Book("978-0134685991", "Effective Java", "Joshua Bloch", 799.00);
        b.showDetails();
    }
}