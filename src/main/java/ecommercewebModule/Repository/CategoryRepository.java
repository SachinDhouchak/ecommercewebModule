package ecommercewebModule.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ecommercewebModule.Entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>  {

	@Procedure(name = "categoryProcedure")
	public String getCategoryNameById(@Param("id")int categoryId);
}
