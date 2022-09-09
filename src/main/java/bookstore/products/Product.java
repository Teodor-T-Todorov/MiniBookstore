package bookstore.products;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract public class Product implements Comparable<Product>{
    private String name;
    private double price;
    private int copies;
    private static final Logger log = LoggerFactory.getLogger(Product.class);

    public Product(String name, double price, int copies) {
        if (price < 0 || copies < 0) {
            throw new IllegalArgumentException("Price or copies can't be negative");
        }
        this.name = name;
        this.price = price;
        this.copies = copies;
    }

    abstract public String getProductInfo();

    public void removeCopies(int copiesToRemove) {
        if (copiesToRemove <= 0) {
            throw new IllegalArgumentException("Can't remove negative or zero amount of copies");
        }

        if (copies >= copiesToRemove) {
            copies -= copiesToRemove;
            log.info("Sold {} copies of {}", copiesToRemove, getName());
        } else {
            log.info("""
                    Not enough copies in stock.\s
                    Current copies {}\s
                    Copies wanted to be sold {}""", copies,copiesToRemove);
        }

    }

    public void getMoreCopies(int copiesToOrder) {
        if (copiesToOrder <= 0) {
            throw new IllegalArgumentException("Can't get negative or zero amount of copies");
        }
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
        if (price < 0) {
            throw new IllegalArgumentException("Price can't be negative");
        }
        this.price = price;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        if (copies < 0) {
            throw new IllegalArgumentException("Price can't be negative");
        }
        this.copies = copies;
    }

    @Override
    public int compareTo(Product product) {
        if (getPrice() > product.getPrice()) {
            return 1;
        } else {
            return -1;
        }
    }
}
