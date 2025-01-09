package ecommercewebModule.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ecommercewebModule.Entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>  {

	
	
}
