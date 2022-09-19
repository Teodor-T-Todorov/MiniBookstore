package bookstore.products;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract public class Product implements Comparable<Product>{
    private String name;
    private double price;
    private int copies;
    private static final Logger log = LoggerFactory.getLogger(Product.class);

    protected Product(String name, double price, int copies) throws IllegalArgumentException {
        if (price < 0 || copies < 0) {
            final String exceptionMessage = "Price or copies can't be negative";
            throw new IllegalArgumentException(exceptionMessage);
        }
        this.name = name;
        this.price = price;
        this.copies = copies;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) throws IllegalArgumentException{
        if (price < 0) {
            final String exceptionMessage = "Price can't be negative";
            throw new IllegalArgumentException(exceptionMessage);
        }
        this.price = price;
    }

    public int getCopies() {
        return this.copies;
    }

    public void setCopies(int copies) {
        if (copies < 0) {
            final String exceptionMessage = "Copies can't be negative";
            throw new IllegalArgumentException(exceptionMessage);
        }
        this.copies = copies;
    }

    abstract public String getProductInfo();

    public void removeCopies(int copiesToRemove) throws IllegalArgumentException{
        if (copiesToRemove <= 0) {
            final String exceptionMessage = "Can't remove negative or zero amount of copies";
            throw new IllegalArgumentException(exceptionMessage);
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

    public void getMoreCopies(int copiesToOrder) throws IllegalArgumentException{
        if (copiesToOrder <= 0) {
            final String exceptionMessage = "Can't get negative or zero amount of copies";
            throw new IllegalArgumentException(exceptionMessage);
        }
        copies += copiesToOrder;
        log.info("{} copies has been added for a total of {} for product: {}",
                copiesToOrder, copies, getName());
    }

    @Override
    public int compareTo(Product product) {
        if (getPrice() > product.getPrice()) {
            return 1;
        } else if (getPrice() < product.getPrice()){
            return -1;
        } else {
            return 0;
        }
    }
}
