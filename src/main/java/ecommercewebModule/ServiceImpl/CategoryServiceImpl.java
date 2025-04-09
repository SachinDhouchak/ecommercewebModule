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
    public String getCategoryNameById(int categoryId) {
       return categoryRepository.getCategoryNameById(categoryId);
	}
	
	@Override
	public List<Category> getAllCategory() {return categoryRepository.findAll(); }

	@Override
	public Optional<Category> getSingleCategory(Integer categoryId) {
		return categoryRepository.findById(categoryId);
	}

	@Override
	public Boolean addCategory(Category category) {
		 try {
			 categoryRepository.save(category);
			 return true;
		} catch (Exception e) {
			return false;
		}		
	}

	@Override
	public Boolean updateCategory(Integer categoryId, Category category) {		
		Optional<Category> isCategoryExist = categoryRepository.findById(categoryId);
		   if (isCategoryExist.isPresent()) {
			    Category existedData = isCategoryExist.get();
			    existedData.setCategory_id(category.getCategory_id());
			    existedData.setCategory_name(category.getCategory_name());
			    existedData.setDescription(category.getDescription());
			    
			  try {
				  categoryRepository.save(existedData);
				  return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
		
		 
	}

	@Override
	public boolean deleteCategory(Integer deleteId) {
		Optional<Category> category =  categoryRepository.findById(deleteId);
		if (category.isPresent()) {
			categoryRepository.deleteById(deleteId);
			return true;
		} else {
            return false;
		}		
	}

	
		
	

}
