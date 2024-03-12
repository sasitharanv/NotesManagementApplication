package com.notesManagement.demo.Service.Serviceimpl;
import com.notesManagement.demo.Model.Category;
import com.notesManagement.demo.Model.Note;
import com.notesManagement.demo.Repository.CategoryRepository;
import com.notesManagement.demo.Repository.NoteRepository;
import com.notesManagement.demo.Service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Setter
@Getter
@AllArgsConstructor
@Service
public class CategoryServiceimpl implements CategoryService {

    private NoteRepository noteRepository;
    private CategoryRepository categoryRepository;

    @Override
    public String addCategory(Category category, Long noteId) {
        Optional<Note> optionalNote = noteRepository.findById(noteId);

        if (optionalNote.isPresent()) {
            Note note = optionalNote.get();
            Optional<Category> existingCategory = categoryRepository.findByCategoryName(category.getCategoryName());

            if (existingCategory.isPresent()) {
                note.setCategory(existingCategory.get());
                noteRepository.save(note);
            } else {
                Category newCategory = new Category();
                newCategory.setCategoryName(category.getCategoryName());
                categoryRepository.save(newCategory);
                note.setCategory(newCategory);
                noteRepository.save(note);
            }
            return "Category added successfully";
        } else {
            return "Note not found";
        }
    }



    @Override
    public String removeCategory(Long userId, Long id) {
        Optional<Note> noteOptional = noteRepository.findById(id);

        if (noteOptional.isPresent()) {
            Note note = noteOptional.get();
            note.setCategory(null);
            noteRepository.save(note);

            return "Category removed successfully";
        }

        return "Note not found";
    }

    @Override
    public List<Category> getDistinctCategoryNamesByUserId(Long userId) {
        List<Category> categories=noteRepository.findDistinctCategoriesByUserId(userId);

        return categories;
    }

}
