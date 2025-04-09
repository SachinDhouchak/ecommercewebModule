package ecommercewebModule.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

@Entity
@NamedStoredProcedureQueries(
	@NamedStoredProcedureQuery(
		name = "categoryProcedure",
		procedureName = "firstProcedure",
		parameters = {
			@StoredProcedureParameter(mode = ParameterMode.IN,type = Integer.class,name = "id"),
			@StoredProcedureParameter(mode = ParameterMode.OUT,type = String.class, name="fetchedCategoryName")
		}
	)
)
public class Category {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="category_id")
	private int category_id;
	
	private String category_name;
	
	@Column(length=20)
	private String description;	
	
	
	public String getCategory_name() {
		return category_name;
	}


	public int getCategory_id() {
		return category_id;
	}


	public void setCategory_id(int category_id) {
		this.category_id = category_id;
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

	
	
	
	
}
