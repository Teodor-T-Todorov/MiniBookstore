package bookstore.products;

public class Book extends  Product{
    private String author;
    private int pages;

    public Book(String name, double price, int amountOfCopies, String author, int pages) {
        super(name, price, amountOfCopies);
        this.author = author;
        this.pages = pages;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return this.pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    @Override
    public String getProductInfo() {
        return "Book: " + getName() + "\n" +
                "Written by: " + getAuthor() + "\n" +
                "Pages: " + getPages() + "\n" +
                "Price: " + getPrice() + "\n" +
                "Available copies: " + getCopies() + "\n";
    }
}
