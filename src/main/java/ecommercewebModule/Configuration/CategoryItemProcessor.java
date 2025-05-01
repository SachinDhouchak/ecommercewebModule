package ecommercewebModule.Configuration;

import ecommercewebModule.Entities.Category;
import org.springframework.batch.item.ItemProcessor;

public class CategoryItemProcessor implements ItemProcessor<Category, Category> {
    @Override
    public Category process(Category category) throws Exception {
        return category;
    }
}
