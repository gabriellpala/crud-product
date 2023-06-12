package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelException;
import model.Product;
import model.dao.DAOFactory;
import model.dao.ProductDAO;

import java.sql.SQLIntegrityConstraintViolationException;

@WebServlet(urlPatterns = {"/products", "/product/form", "/product/delete", "/product/insert", "/product/update"})
public class ProductController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getRequestURI();

        switch (action) {
            case "/crud-manager/product/form": {
                listProducts(req);
                req.setAttribute("action", "insert");
                ControllerUtil.forward(req, resp, "/form-product.jsp");
                break;
            }
            case "/crud-manager/product/update": {
                listProducts(req);
                Product product = loadProduct(req);
                req.setAttribute("product", product);
                req.setAttribute("action", "update");
                ControllerUtil.forward(req, resp, "/form-product.jsp");
                break;
            }
            default:
                listProducts(req);

                ControllerUtil.transferSessionMessagesToRequest(req);

                ControllerUtil.forward(req, resp, "/products.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getRequestURI();

        if (action == null || action.equals("")) {
            ControllerUtil.forward(req, resp, "/index.jsp");
            return;
        }

        switch (action) {
            case "/crud-manager/product/delete":
                deleteProduct(req, resp);
                break;
            case "/crud-manager/product/insert": {
                insertProduct(req, resp);
                break;
            }
            case "/crud-manager/product/update": {
                updateProduct(req, resp);
                break;
            }
            default:
                System.out.println("URL inválida " + action);
                break;
        }

        ControllerUtil.redirect(resp, req.getContextPath() + "/products");
    }

    private Product loadProduct(HttpServletRequest req) {
        String productIdParameter = req.getParameter("productId");

        int productId = Integer.parseInt(productIdParameter);

        ProductDAO dao = DAOFactory.createDAO(ProductDAO.class);

        try {
            Product product = dao.findById(productId);

            if (product == null)
                throw new ModelException("Produto não encontrado para alteração");

            return product;
        } catch (ModelException e) {
            // log no servidor
            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
        }

        return null;
    }

    private void listProducts(HttpServletRequest req) {
        ProductDAO dao = DAOFactory.createDAO(ProductDAO.class);

        List<Product> products = null;
        try {
            products = dao.listAll();
        } catch (ModelException e) {
            // Log no servidor
            e.printStackTrace();
        }

        if (products != null)
            req.setAttribute("products", products);
    }

    private void insertProduct(HttpServletRequest req, HttpServletResponse resp) {
        String productName = req.getParameter("name");
        String productDescription = req.getParameter("description");
        double productPrice = Double.parseDouble(req.getParameter("price"));

        Product product = new Product();
        product.setName(productName);
        product.setDescription(productDescription);
        product.setPrice(productPrice);

        ProductDAO dao = DAOFactory.createDAO(ProductDAO.class);

        try {
            if (dao.save(product)) {
                ControllerUtil.successMessage(req, "Produto '" + product.getName() + "' salvo com sucesso.");
            } else {
                ControllerUtil.errorMessage(req, "Produto '" + product.getName() + "' não pode ser salvo.");
            }

        } catch (ModelException e) {
            // log no servidor
            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
        }
    }

    private void updateProduct(HttpServletRequest req, HttpServletResponse resp) {
        String productName = req.getParameter("name");
        String productDescription = req.getParameter("description");
        double productPrice = Double.parseDouble(req.getParameter("price"));

        Product product = loadProduct(req);
        product.setName(productName);
        product.setDescription(productDescription);
        product.setPrice(productPrice);

        ProductDAO dao = DAOFactory.createDAO(ProductDAO.class);

        try {
            if (dao.update(product)) {
                ControllerUtil.successMessage(req, "Produto '" + product.getName() + "' atualizado com sucesso.");
            } else {
                ControllerUtil.errorMessage(req, "Produto '" + product.getName() + "' não pode ser atualizado.");
            }

        } catch (ModelException e) {
            // log no servidor
            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
        }
    }

    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp) {
        String productIdParameter = req.getParameter("id");

        int productId = Integer.parseInt(productIdParameter);

        ProductDAO dao = DAOFactory.createDAO(ProductDAO.class);

        try {
            Product product = dao.findById(productId);

            if (product == null)
                throw new ModelException("Produto não encontrado para deleção.");

            if (dao.delete(product)) {
                ControllerUtil.successMessage(req, "Produto '" + product.getName() + "' deletado com sucesso.");
            } else {
                ControllerUtil.errorMessage(req, "Produto '" + product.getName() + "' não pode ser deletado. Existem dados relacionados ao produto.");
            }
        } catch (ModelException e) {
            // log no servidor
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                ControllerUtil.errorMessage(req, e.getMessage());
            }
            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
        }
    }
}
