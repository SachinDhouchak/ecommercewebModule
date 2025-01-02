package ecommercewebModule.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommercewebModule.Entities.Category;
import ecommercewebModule.Repository.CategoryRepository;
import ecommercewebModule.Service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepository;
	
	@Override
	public List<Category> getAllCategory() {		
			return categoryRepository.findAll();
		}

	@Override
	public Optional<Category> getSingleCategory(Integer categoryId) {
		return categoryRepository.findById(categoryId);
	}

	
		
	

}
