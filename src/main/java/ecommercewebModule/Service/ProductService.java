package ecommercewebModule.Service;

import java.util.List;
import java.util.Optional;

import ecommercewebModule.Entities.Product;

public interface ProductService {

	List<Product> getAllProducts(Integer productId);

	Boolean addProduct(Product product);

	Optional<Product> getSingleProduct(Integer productId);

	Boolean updateProduct(Integer productId, Product updatedProduct);

	Boolean deleteProduct(Integer productId);

}
