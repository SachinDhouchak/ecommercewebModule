package ecommercewebModule.Controller;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ecommercewebModule.CustomException.ResourceNotFoundException;
import ecommercewebModule.CustomException.ServerException;
import ecommercewebModule.Entities.CategoryBulk;
import ecommercewebModule.Entities.CategoryNameComparator;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/Category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;

	@PostMapping("/addCategoryBatch")
	public ResponseEntity<?> insertDataThroughBatch() {
		JobParameters jobParameters = new JobParametersBuilder()
				.addLong("startAt",System.currentTimeMillis()).toJobParameters();

        try {
            jobLauncher.run(job, jobParameters);
			return ResponseEntity.ok("Batch job started successfully.");
        }  catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
				  JobParametersInvalidException e) {
			e.printStackTrace(); // Optional: log this in a logger
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Batch job failed: " + e.getMessage());
		}
	}

	@PostMapping("/addBulkCategory")
	public ResponseEntity<String> addBulkData(@RequestBody @Valid CategoryBulk categoryBulk, BindingResult bindingResult) {
		StringBuilder errors = new StringBuilder();
		if (bindingResult.hasErrors()) {
			FieldError fieldError = bindingResult.getFieldError();
			errors.append(fieldError.getField()).append(" : ").append(fieldError.getDefaultMessage()).append(" , ");
			return ResponseEntity.badRequest().body(errors.toString());
		}
		return ResponseEntity.badRequest().body(categoryService.addBulkData(categoryBulk));
	}

	@GetMapping("addAndDelete")
	public ResponseEntity<String> addAndDeleteCategory() {
		return ResponseEntity.ok().body(categoryService.addAndDeleteCategory());
	}

	@GetMapping("/getCategoryName")
	public String getCategoryNameById(@RequestParam("categoryId") int categoryId) {
		return categoryService.getCategoryNameById(categoryId);
	}


	@GetMapping("/getAllCategories")
	public ResponseEntity<?> getAllCategories() {
		try {
			List<Category> categories = categoryService.getAllCategory();
			if (!CollectionUtils.isEmpty(categories)) {
				return ResponseEntity.ok().body(categories.stream()
						.collect(Collectors.toMap(
								Category::getCategory_name,
								category -> category,
								(existing,recent) -> existing
								)));
			} else {
                return ResponseEntity.ok("Data is empty");
			}
		} catch (Exception e) {
			String errorMsg = "Something went wrong" + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(errorMsg);
		}
	}


	@GetMapping("/getAllCategoriesByComparator")
	public ResponseEntity<?> getAllCategoriesByComparator() {
		try {
			List<Category> categories = categoryService.getAllCategory();
			Collections.sort(categories, new CategoryNameComparator());
			if (!CollectionUtils.isEmpty(categories)) {
				return ResponseEntity.ok().
						body(categories.stream().collect(
								Collectors.toMap(
										Category::getCategory_id,
										category -> category,
										(existingCategory, updatedCategory) -> existingCategory,
										LinkedHashMap::new
								)
						));
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
		System.out.println("First line is printed");
		System.out.println("Second line is printed");
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

	@GetMapping("/getCategory2")
	public ResponseEntity<?> getSingleCategory2(@RequestParam("categoryId") Integer categoryId) {
		Category category = categoryService.getSingleCategory(categoryId).orElseThrow(
				()-> new ResourceNotFoundException("Data with id " + categoryId + " is not found")
		);
		return ResponseEntity.ok(category);
	}

	
	// @RequestBody or ModelAttribute
	@PostMapping("/addCategory")
	public ResponseEntity<String> addCategory(@Valid @RequestBody Category category, BindingResult bindingResult) {

		System.out.println("First line of add handler is done");

		if (bindingResult.hasErrors()) {
			StringBuilder validaionErrors = new StringBuilder();
			 for (FieldError fieldError : bindingResult.getFieldErrors()) {
				 validaionErrors.append(fieldError.getField()).append(" : ").append(fieldError.getDefaultMessage()).append(" , ");
			 }
			 return ResponseEntity.badRequest().body(validaionErrors.toString());
		}

		try {
			Boolean isCategoryAdded = categoryService.addCategory(category);
			System.out.println("after adding category class data");
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
