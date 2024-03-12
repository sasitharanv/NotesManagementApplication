package com.notesManagement.demo.Repository;
import com.notesManagement.demo.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {



    Optional<Category> findByCategoryName(String categoryName);

}
