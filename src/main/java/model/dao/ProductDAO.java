package model.dao;

import java.util.List;
import model.ModelException;
import model.Product;

public interface ProductDAO {
    boolean saveProduct(Product product) throws ModelException;
    boolean updateProduct(Product product) throws ModelException;
    boolean deleteProduct(Product product) throws ModelException;
    List<Product> listAllProducts() throws ModelException;
    Product findProductById(int id) throws ModelException;
}