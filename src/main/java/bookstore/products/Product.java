package bookstore.products;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract public class Product {
    private String name;
    private double price;
    private int copies;
    private final Logger log = LoggerFactory.getLogger(Product.class);

    public Product(String name, double price, int copies) {
        this.name = name;
        this.price = price;
        this.copies = copies;
    }

    abstract public String getProductInfo();

    public void sellCopies(int copiesToSell) {
        if (copies >= copiesToSell) {
            copies -= copiesToSell;
            log.info("Sold {} copies of {}", copiesToSell, name);
        } else {
            log.info("""
                    Not enough copies in stock.\s
                    Current copies {}\s
                    Copies wanted to be sold {}""", copies,copiesToSell);
        }
    }

    public void orderCopies(int copiesToOrder) {
        copies += copiesToOrder;
        log.info("{} copies has been added for a total of {} for product: {}",
                copiesToOrder, copies, getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }
}
