package bookstore.products;

public class Album extends Product{
    private String artist;

    public Album(String name, double price, int amountOfCopies, String artist) {
        super(name, price, amountOfCopies);
        this.artist = artist;
    }

    public String getArtist() {
        return this.artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String getProductInfo() {
        return "Album: " + getName() + "\n" +
                "Written by: " + getArtist() + "\n" +
                "Price: " + getPrice() + "\n" +
                "Available copies: " + getCopies() + "\n";
    }
}
