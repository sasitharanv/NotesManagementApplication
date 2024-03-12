package com.notesManagement.demo.Controller;
import com.notesManagement.demo.Dto.NoteDto;
import com.notesManagement.demo.Dto.UpdateDto;
import com.notesManagement.demo.Exception.NotFoundException;
import com.notesManagement.demo.Model.Note;
import com.notesManagement.demo.Service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
@CrossOrigin("*")


@AllArgsConstructor
@RestController
@RequestMapping("/api/authenticated")

public class NoteController {

    private NoteService noteService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<String> addNote(@PathVariable long userId, @RequestBody Note note) {
        try {
            noteService.add(userId, note);
            return ResponseEntity.ok("Note Added Successfully");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("/getnotes/{userId}")
    public ResponseEntity<List<NoteDto>> getNotes(@PathVariable long userId) {
        List<NoteDto> notes = noteService.getNotes(userId,false);
        if (notes.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        } else {
            return ResponseEntity.ok(notes);
        }
    }
    @GetMapping("/getnote/{userId}/{id}")
    public ResponseEntity<NoteDto> getNote(@PathVariable Long userId, @PathVariable Long id) {
        NoteDto noteDto = noteService.getnote(userId,id);

        return ResponseEntity.ok(noteDto);


    }


    @PostMapping("/update/{userId}/{id}")
        public ResponseEntity<UpdateDto> updateNote (@PathVariable Long userId, @PathVariable Long id, @RequestBody UpdateDto updateDto){
                  Note updatednote=  noteService.update(userId,id,updateDto);

                    return ResponseEntity.ok(updateDto);


    }

    @DeleteMapping("/delete/{userId}/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable Long userId, @PathVariable Long id) {
        noteService.delete(userId, id);
        return ResponseEntity.ok("Note deleted successfully");
    }

    @PutMapping("/archive/{userId}/{id}")
    public String archiveNote(@PathVariable Long userId, @PathVariable Long id){
        noteService.archive(userId, id);
        return "Note Archived successfully";
    }

    @PutMapping("/unarchive/{userId}/{id}")
    public String unarchiveNote(@PathVariable Long userId, @PathVariable Long id){
        noteService.unarchive(userId, id);
        return "Note unArchived successfully";
    }
    @GetMapping("/getnotesarchived/{userId}")
    public ResponseEntity<List<NoteDto>> getNotesArchieved(@PathVariable long userId) {
        List<NoteDto> notes = noteService.getNotes(userId, true);

        if (notes.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        } else {
            return ResponseEntity.ok(notes);
        }
    }

    @GetMapping("/filterByCategory/{userId}/{categoryId}")
    public ResponseEntity<List<NoteDto>> filterNotesByCategory(@PathVariable Long categoryId, @PathVariable Long userId) {
        List<NoteDto> filteredNotes = noteService.filterByCategory(categoryId, userId);
        return ResponseEntity.ok(filteredNotes);
    }



}
