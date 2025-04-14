package ecommercewebModule.Controller;

import java.util.List;
import java.util.Optional;

import ecommercewebModule.CustomException.ResourceNotFoundException;
import ecommercewebModule.CustomException.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ecommercewebModule.Entities.Category;
import ecommercewebModule.Service.CategoryService;

@RestController
@RequestMapping("/Category")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@GetMapping("/getCategoryName")
	public String getCategoryNameById(@RequestParam("categoryId") int categoryId) {
		return categoryService.getCategoryNameById(categoryId);
	}

	@GetMapping("/getAllCategories")
	public ResponseEntity<?> getAllCategories() {
		try {
			List<Category> categories = categoryService.getAllCategory();
			if (!CollectionUtils.isEmpty(categories)) {
				return ResponseEntity.ok(categories);
			} else {
                return ResponseEntity.ok("Data is empty");
			}
		} catch (Exception e) {
			String errorMsg = "Something went wrong" + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(errorMsg);
		}
	}


	@GetMapping("/getCategoryId")
	public ResponseEntity<?> getSingleCategoryId(@RequestParam("categoryId") Integer categoryId) {
		Category category = categoryService.getSingleCategory(categoryId).orElseThrow(
				()-> new ServerException("Something went wrong while fetching all the data")
		);
		return ResponseEntity.ok(category);
	}


	@GetMapping("/getCategory")
	public ResponseEntity<?> getSingleCategory(@RequestParam("categoryId") Integer categoryId) {
		Category category = categoryService.getSingleCategory(categoryId).orElseThrow(
				()-> new ResourceNotFoundException("Data with id " + categoryId + " is not found")
		);
		return ResponseEntity.ok(category);
	}

	
	// @RequestBody or ModelAttribute
	@PostMapping("/addCategory")
	public ResponseEntity<String> addCategory(@RequestBody Category category) {		
		try {
			Boolean isCategoryAdded = categoryService.addCategory(category);
			    if (isCategoryAdded) {
			    	return ResponseEntity.ok("New Category added");
				} else {
					return ResponseEntity.badRequest().body("Category could not be added");
				}
		} catch (Exception e) {
			String errorMsg = "Something went wrong";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMsg);
		}		
	}
	

	@PostMapping("/updateCategory")
	public ResponseEntity<?> updateCategory(@RequestParam("categoryId") Integer categoryId, @RequestBody Category category  ) {
		if (categoryId == null ) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category is not found");
		}else {
			try {
				Boolean isCategoryUpdated = categoryService.updateCategory(categoryId,category);
				   if (isCategoryUpdated) {
					   return ResponseEntity.ok("Data is updated");
				 }else {
					 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
				}
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
			}
			
		}		
	}
	
	
	// RequestParam or PathVariable
	@DeleteMapping("/deleteCategory/{deleteId}")
	public ResponseEntity<?> deleteCategory(@PathVariable Integer deleteId ) {			
		try {			
			boolean isDeleted = categoryService.deleteCategory(deleteId);			
		      	if (isDeleted) {
			       	return ResponseEntity.ok("Category with id " + deleteId + " deleted Successfully" );
			     } else {
			       	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category with id " +deleteId + "not found" );
		      	}			
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
		}		
	}
	
	
	

}
