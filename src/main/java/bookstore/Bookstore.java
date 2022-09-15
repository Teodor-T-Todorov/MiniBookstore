package bookstore;

import bookstore.products.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Bookstore {
    private String name;
    private List<Product> products;
    private final Logger log = LoggerFactory.getLogger(Bookstore.class);

    public Bookstore(String name, List<Product> productList) {
        this.name = name;
        this.products = productList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> productList) {
        this.products = productList;
    }

    public boolean contains(Product product) {
        return products.contains(product);
    }

    public void addProductToStore(Product product) {
        if (!products.contains(product)) {
            products.add(product);
            log.info("{} copies of [{}, {}] got added to the bookstore",
                    product.getCopies(),
                    product.getName(),
                    product.getClass().getSimpleName());
        } else {
            log.info("[{}, {}] is already available in the store",
                    product.getName(), product.getClass().getSimpleName());
        }
    }

    public void removeProductFromStore(Product product) {
        if (products.contains(product)) {
            products.remove(product);
            log.info("[{}, {}] got removed to the bookstore",
                    product.getName(), product.getClass().getSimpleName());
        } else {
            log.info("Can't remove, [{}, {}] is not included in the store",
                    product.getName(), product.getClass().getSimpleName());
        }
    }

    public void sellProduct(Product product) {
        if (products.contains(product)) {
            product.removeCopies(1);
        } else {
            log.info("[{}, {}] is not currently in stock",
                    product.getName(), product.getClass().getSimpleName());
        }
    }

    public void sellProduct(Product product, int copies) {
        if (copies <= 0) {
            throw new IllegalArgumentException("Can't sell negative or zero amount of copies");
        }

        if (products.contains(product)) {
            product.removeCopies(copies);
        } else {
            log.info("[{}, {}] is not currently in stock",
                    product.getName(), product.getClass().getSimpleName());
        }
    }

    public void resupplyProduct(Product product, int copies) {
        if (copies <= 0) {
            throw new IllegalArgumentException("Can't resupply negative or zero amount of copies");
        }

        if (products.contains(product)) {
            product.getMoreCopies(copies);
        } else {
            log.info("[{}, {}] is not currently in stock",
                    product.getName(), product.getClass().getSimpleName());
        }
    }

    public void makeDiscount(Product product, double discount) {
        if (discount > 0 && discount < 100) {
            if (products.contains(product)) {
                product.setPrice(product.getPrice() - product.getPrice()*(discount/100));
                log.info("Discount price for [{}, {}] is {}$",
                        product.getName(), product.getClass().getSimpleName(), product.getPrice());
            } else {
                log.info("[{}, {}] is not currently in stock",
                        product.getName(), product.getClass().getSimpleName());
            }
        } else {
            log.warn("Invalid discount number");
        }
    }

    public void increasePrice(Product product, double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Can't increase price with negative or zero value");
        }

        if (products.contains(product)) {
            product.setPrice(product.getPrice() + price);
            log.info("Price for [{}, {}] got increased to {}",
                    product.getName(), product.getClass().getSimpleName(), product.getPrice());
        } else {
            log.info("Can't increase price for [{}, {}] since it's not included in the store",
                    product.getName(), product.getClass().getSimpleName());
        }
    }
}
