package ecommercewebModule.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ecommercewebModule.Entities.Category;
import ecommercewebModule.Service.CategoryService;

@RestController
public class AdminController {

	@Autowired
	CategoryService categoryService;

	@GetMapping("/allCategory")
	public ResponseEntity<?> getAllCategories() {
		try {
			List<Category> categories = categoryService.getAllCategory();
			return ResponseEntity.ok(categories);
		} catch (Exception e) {
			String errorMsg = "Something went wrong" + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMsg);
		}
	}
	
	
	@GetMapping("/getCategory")
	public ResponseEntity<?> getSingleCategory(@RequestParam("categoryid") Integer categoryId) {
		Optional<Category> category = categoryService.getSingleCategory(categoryId);
		if (category == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found in single category");
		}
		return ResponseEntity.ok(category);
	}
	
	
	@PostMapping("/addCategory")
	public ResponseEntity<String> addCategory(@ModelAttribute("newCategory") Category category) {
		
		try {
			categoryService.addCategory(category);
			return ResponseEntity.ok("New Category added");
		} catch (Exception e) {
			String errorMsg = "Something went wrong";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMsg);
		}		
	}
	
	
	@PostMapping("/updateCategory")
	public ResponseEntity<?> updateCategory(@RequestParam("id") Integer id, @ModelAttribute("updatedCategoryData") Category category  ) {
		if (id == null ) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data is not found");
		}else {
			Category categoryResult = categoryService.updateCategory(category);
			return ResponseEntity.ok(categoryResult);
		}		
	}
	
	
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
