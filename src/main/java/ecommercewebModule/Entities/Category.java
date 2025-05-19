package ecommercewebModule.Entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Comparator;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
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
public class Category  {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="category_id")
	private int category_id;

	@NotBlank(message = "category should not be blank")
	private String category_name;
	
	@Column(length=30)
	@Size(max = 30,message = "description length shouldn't be more than 20 characters")
	private String description;


}
