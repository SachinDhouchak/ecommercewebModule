package ecommercewebModule.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ecommercewebModule.Entities.Product;
import ecommercewebModule.Repository.ProductRepository;
import ecommercewebModule.Service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;
	
	@Override
	public List<Product> getAllProducts(Integer productId) {
		 List<Product> productList =  productRepository.findAll();
		 if(!CollectionUtils.isEmpty(productList)){
			 return productList;
		 }
		 return null;
	}

	@Override
	public Boolean addProduct(Product product) {		
		try {
			 productRepository.save(product);
			 return true;
		} catch (Exception e) {
			System.out.println("Something went wrong");
			return false;
		}		
	}

	@Override
	public Optional<Product> getSingleProduct(Integer productId) {
		Optional<Product> product = productRepository.findById(productId);
		return product;
	}

	@Override
	public Boolean updateProduct(Integer productId,Product updatedProduct) {
		
			Optional<Product> isProductExist = productRepository.findById(productId);
			    if (isProductExist.isPresent()) {
					Product existingProduct = isProductExist.get();
					existingProduct.setProduct_id(updatedProduct.getProduct_id());
					existingProduct.setName(updatedProduct.getName());
					existingProduct.setPrice(updatedProduct.getPrice());
					existingProduct.setDescription(updatedProduct.getDescription());
					
					
						try {
							productRepository.save(existingProduct);
							return true;
						} catch (Exception e) {
							System.out.println("Something went wrong while updating the product");
							return false;
						}				
					
				} else {
					System.out.println("Data not found");
	                return false;
				}
		
	}

	@Override
	public Boolean deleteProduct(Integer productId) {
		try {
			productRepository.deleteById(productId);
			return true;
		} catch (Exception e) {
			return false;
		}

	}
	
	
	

	
	
	
}
