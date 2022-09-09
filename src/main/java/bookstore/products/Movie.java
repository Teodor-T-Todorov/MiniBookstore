package bookstore.products;

public class Movie extends Product{
    private String director;

    public Movie(String name, double price, int amountOfCopies, String director) {
        super(name, price, amountOfCopies);
        this.director = director;
    }

    @Override
    public String getProductInfo() {
        return "Movie: " + getName() + "\n" +
                "Directed by: " + getDirector() + "\n" +
                "Price: " + getPrice() + "\n" +
                "Available copies: " + getCopies() + "\n";
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDirector() {
        return director;
    }
}
