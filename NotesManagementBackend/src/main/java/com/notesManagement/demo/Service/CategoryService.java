package com.notesManagement.demo.Service;
import com.notesManagement.demo.Model.Category;

import java.util.List;


public interface CategoryService {

    String addCategory(Category category, Long id);

    String removeCategory(Long userId,Long id);

    List<Category>getDistinctCategoryNamesByUserId(Long userId);
}
