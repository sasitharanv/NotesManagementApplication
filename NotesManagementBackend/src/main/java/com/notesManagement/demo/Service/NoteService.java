package com.notesManagement.demo.Service;
import com.notesManagement.demo.Dto.NoteDto;
import com.notesManagement.demo.Dto.UpdateDto;
import com.notesManagement.demo.Model.Note;
import java.util.List;



public interface NoteService {

    String add(Long userId, Note note);

    List<NoteDto> getNotes(Long userId, boolean isArchived);

    String archive(Long userId, Long id);

    String unarchive(Long userId, Long id);

    NoteDto getnote(Long userId, Long id);

    Note update(Long userId, Long id, UpdateDto updateDto);

    String delete(Long userId, Long id);

    List<NoteDto> filterByCategory(Long userId,Long CategoryId);


}