package ecommercewebModule.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

}
