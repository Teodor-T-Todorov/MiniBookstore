package bookstore;

import bookstore.products.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Bookstore {
    private String name;
    private List<Product> productList;
    private final Logger log = LoggerFactory.getLogger(Bookstore.class);

    public Bookstore(String name, List<Product> productList) {
        this.name = name;
        this.productList = productList;
    }

    public void addProductToStore(Product product) {
        if (!productList.contains(product)) {
            productList.add(product);
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
        if (productList.contains(product)) {
            productList.remove(product);
            log.info("[{}, {}] got removed to the bookstore",
                    product.getName(), product.getClass().getSimpleName());
        } else {
            log.info("Can't remove, [{}, {}] is not included in the store",
                    product.getName(), product.getClass().getSimpleName());
        }
    }

    public void sellProduct(Product product) {
        if (productList.contains(product)) {
            product.sellCopies(1);
        } else {
            log.info("[{}, {}] is not currently in stock",
                    product.getName(), product.getClass().getSimpleName());
        }
    }

    public void sellProduct(Product product, int copies) {
        if (productList.contains(product)) {
            product.sellCopies(copies);
        } else {
            log.info("[{}, {}] is not currently in stock",
                    product.getName(), product.getClass().getSimpleName());
        }
    }

    public void resupplyProduct(Product product, int copies) {
        if (productList.contains(product)) {
            product.orderCopies(copies);
        } else {
            log.info("[{}, {}] is not currently in stock",
                    product.getName(), product.getClass().getSimpleName());
        }
    }

    public void makeDiscount(Product product, double discount) {
        if (discount > 0 && discount < 100) {
            if (productList.contains(product)) {
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
}
