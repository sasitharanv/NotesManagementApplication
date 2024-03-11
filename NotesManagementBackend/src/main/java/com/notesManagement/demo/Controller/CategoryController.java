package com.notesManagement.demo.Controller;
import com.notesManagement.demo.Model.Category;
import com.notesManagement.demo.Repository.CategoryRepository;
import com.notesManagement.demo.Repository.NoteRepository;
import com.notesManagement.demo.Service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")

@AllArgsConstructor
@RestController
@RequestMapping("/api/authenticated")

public class CategoryController {

    private CategoryService categoryService;
    private NoteRepository noteRepository;

    private CategoryRepository categoryRepository;

    @PostMapping("/addcategory")
    public ResponseEntity<String> addCategory(@RequestBody Category category, @RequestParam Long noteId) {
        if (noteRepository.existsById(noteId)) {
            String result = categoryService.addCategory(category, noteId);
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note not found");
        }
    }

    @GetMapping("/distinctcategory/{userId}")
    public ResponseEntity<List<Category>> getDistinctCategoriesByUserId(@PathVariable Long userId) {
        List<Category> distinctCategories = categoryService.getDistinctCategoryNamesByUserId(userId);
        return ResponseEntity.ok(distinctCategories);
    }

    @PutMapping("/removecategory/{userId}/{noteId}")
    public ResponseEntity<String> removeCategory(@PathVariable Long userId, @PathVariable Long noteId) {
        String result = categoryService.removeCategory(userId,noteId);

        if ("Category removed successfully".equals(result)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }



}



