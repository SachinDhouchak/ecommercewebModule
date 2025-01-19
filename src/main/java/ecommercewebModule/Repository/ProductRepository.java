package ecommercewebModule.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ecommercewebModule.Entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
