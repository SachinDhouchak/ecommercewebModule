package ecommercewebModule.Service;

import java.util.List;
import java.util.Optional;

import ecommercewebModule.Entities.Category;


public interface CategoryService {

	public String getCategoryNameById(int categoryId);
	
	public List<Category> getAllCategory();

	public Optional<Category> getSingleCategory(Integer categoryId);

	public Boolean addCategory(Category category);

	public Boolean updateCategory(Integer categoryId,Category category);

	public boolean deleteCategory(Integer deleteId);
	
}
