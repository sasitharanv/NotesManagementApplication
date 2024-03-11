package com.notesManagement.demo.Repository;
import com.notesManagement.demo.Model.Category;
import com.notesManagement.demo.Model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
public interface NoteRepository extends JpaRepository<Note,Long> {


    @Query("SELECT DISTINCT n.category FROM Note n WHERE n.user.userId = :userId")
    List<Category> findDistinctCategoriesByUserId(@Param("userId") Long userId);

    Optional<Note>  findById(Long noteId);
    Optional<Note>  findByUserUsername(String username);
   List<Note>  findByIsArchivedTrueAndUserUserId(long userId);
   List<Note> findByIsArchivedFalseAndUserUserId(long userId);

   List<Note> findNotesByCategory_IdAndUserUserId(Long categoryId,Long UserId);


    Note findNoteByUserUserIdAndId(Long userId,Long id);
    void deleteById(Long noteId);
}
