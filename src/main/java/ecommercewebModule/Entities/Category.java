package ecommercewebModule.Entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Category {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="category_id")
	private int category_id;
	
	private String category_name;
	
	@Column(length=20)
	private String description;	
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="combine_col_inCat",nullable=false,referencedColumnName="product_id")
	private List<Product> products;
	
//	private String imageUrl;

	

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	

//	public String getImageUrl() {
//		return imageUrl;
//	}
//
//	public void setImageUrl(String imageUrl) {
//		this.imageUrl = imageUrl;
//	}	
	
	
	
	
}
