package ecommercewebModule.Service;

import java.util.List;
import java.util.Optional;

import ecommercewebModule.Entities.Category;


public interface CategoryService {
	
	public List<Category> getAllCategory();

	public Optional<Category> getSingleCategory(Integer categoryId);
	
}
