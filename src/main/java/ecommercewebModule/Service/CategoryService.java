package ecommercewebModule.Service;

import java.util.List;
import java.util.Optional;

import ecommercewebModule.Entities.Category;

import javax.transaction.Transactional;


public interface CategoryService {

	@Transactional
	public  String addAndDeleteCategory();

	public String getCategoryNameById(int categoryId);
	
	public List<Category> getAllCategory();

	public Optional<Category> getSingleCategory(Integer categoryId);

	public Boolean addCategory(Category category);

	public Boolean updateCategory(Integer categoryId,Category category);

	public boolean deleteCategory(Integer deleteId);
	
}
