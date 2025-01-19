package ecommercewebModule.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ecommercewebModule.Entities.Product;
import ecommercewebModule.Service.ProductService;

@RestController
@RequestMapping("/Product")
public class ProductController {
   
	@Autowired
	ProductService productService;
	
	@GetMapping("/getAllProducts")
	public ResponseEntity<?> getAllProducts(@RequestParam("productId") Integer productId){
		try {
			List<Product> product = productService.getAllProducts(productId);			
				if ( CollectionUtils.isEmpty(product)  ) {
					return ResponseEntity.ok("Data is empty");
				}else {
					return ResponseEntity.ok(product);
				}			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
		}
	}
	
	
	@PostMapping("/addProduct")
	public ResponseEntity<String> addProduct(@ModelAttribute("newProduct") Product product) {		
			Boolean isProductSaved = productService.addProduct(product);
			    if (isProductSaved) {
			    	return ResponseEntity.ok("New Product is added successfully");
				} else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
				}			
	}
	
	
	@GetMapping("/getProduct")
	public ResponseEntity<?> getSingleProduct(@RequestParam("productId") Integer productId ) {		
		   try {
				Optional<Product> product = productService.getSingleProduct(productId);				
					if (product.isPresent()  ) {
						return ResponseEntity.ok(product);
					}else {
						return ResponseEntity.ok("Data not found");
					}
		   } catch (Exception e) {
			   return ResponseEntity.internalServerError().body("Something went wrong, Try again");
		   }		
	}
	
	
	
	@PostMapping("/updateProduct")
	public ResponseEntity<String> updateProduct(
			@RequestParam("productId") Integer productId ,
			@ModelAttribute("updatedProduct") Product updatedProduct )   {
			try {
				Boolean isUpdated =  productService.updateProduct(productId,updatedProduct);			
					if (isUpdated) {
						return ResponseEntity.ok("Data is updated successfully");
					}else {
						return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Record is not found");
					}
				
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Try again");
			}
	}
	
	
	@DeleteMapping("/deleteProduct")
	public ResponseEntity<String> deleteProduct(@RequestParam("productId") Integer productId) {
		try {
			 Boolean isProductDelete =  productService.deleteProduct(productId);
			    if (isProductDelete) {
					return ResponseEntity.ok("Product is deleted successfully");
				} else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product is not found");
				}
			 
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
		}
	}
	
	
  	
}
