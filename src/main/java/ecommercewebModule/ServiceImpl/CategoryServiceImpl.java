package ecommercewebModule.ServiceImpl;

import java.util.List;
import java.util.Optional;

import ch.qos.logback.core.net.SyslogOutputStream;
import ecommercewebModule.CustomException.ServerException;
import ecommercewebModule.Repository.ProductRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommercewebModule.Entities.Category;
import ecommercewebModule.Repository.CategoryRepository;
import ecommercewebModule.Service.CategoryService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ProductRepository productRepository;


	@Override
	@Transactional
	public String addAndDeleteCategory() throws ServerException {
		try {
			categoryRepository.save(new Category(0,"flyingVehicles","Air vehcile"));
			deleteProduct();
			return "transaction is done";
		} catch (Exception ex) {
           throw new ServerException("Transaction failed " + ex.getMessage());
		}
	}

	public void deleteProduct() {
		productRepository.deleteById(36);
	}

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
