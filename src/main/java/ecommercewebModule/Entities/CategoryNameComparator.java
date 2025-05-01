package ecommercewebModule.Entities;

import java.util.Comparator;

public class CategoryNameComparator implements Comparator<Category> {
    @Override
    public int compare(Category o1, Category o2) {
        return o1.getCategory_name().compareTo(o2.getCategory_name());
    }
}
