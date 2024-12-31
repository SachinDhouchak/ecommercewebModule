package ecommercewebModule.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommercewebModule.Entities.Category;
import ecommercewebModule.Repository.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;

	public List<Category> getAllCategory() {
		return categoryRepository.findAll();
	}
	
	
	
}
