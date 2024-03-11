package com.notesManagement.demo.Service.Serviceimpl;
import com.notesManagement.demo.Dto.NoteDto;
import com.notesManagement.demo.Dto.UpdateDto;
import com.notesManagement.demo.Exception.NotFoundException;
import com.notesManagement.demo.Model.Note;
import com.notesManagement.demo.Model.User;
import com.notesManagement.demo.Repository.NoteRepository;
import com.notesManagement.demo.Repository.UserRepository;
import com.notesManagement.demo.Service.NoteService;
import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@AllArgsConstructor
@Service
public class NoteServiceimpl implements NoteService {
    private NoteRepository noteRepository;
    private EntityManagerFactory entityManagerFactory;
    private UserRepository userRepository;
    @Override
    public String add(Long userId, Note note) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));

        note.setUser(user);

        note.setCreationDate(LocalDateTime.now());
        noteRepository.save(note);

        return "Note Added Successfully";
    }

    @Override

    public List<NoteDto> getNotes(Long userId, boolean isArchived) {
        List<Note> notes;
        if (isArchived) {
            notes = noteRepository.findByIsArchivedTrueAndUserUserId(userId);
        } else {
            notes = noteRepository.findByIsArchivedFalseAndUserUserId(userId);
        }

        List<NoteDto> noteDtos = notes.stream()
                .map(note -> mapNoteToDto(note))
                .collect(Collectors.toList());

        return noteDtos;
    }

    @Override
    public NoteDto getnote(Long userId, Long id) {
        Note note=noteRepository.findNoteByUserUserIdAndId(userId,id);
        if (note == null) {
            throw new NotFoundException("Note not found with userId: " + userId + " and id: " + id);
        }

        return mapNoteToDto(note);
    }

    private NoteDto mapNoteToDto(Note note) {
        NoteDto noteDto = new NoteDto();
        noteDto.setId(note.getId());
        noteDto.setTitle(note.getTitle());
        noteDto.setContent(note.getContent());
        noteDto.setCreatedDate(note.getCreationDate());
        noteDto.setLastEditedDate(note.getLastEditedDate());
        noteDto.setUserId(note.getUser().getUserId());
        noteDto.setCategory(note.getCategory());

        return noteDto;
    }

    @Override
    public Note update(Long userId, Long id, UpdateDto updateDto) {
        Note note1=noteRepository.findNoteByUserUserIdAndId(userId,id);

        note1.setTitle(updateDto.getTitle());
        note1.setContent(updateDto.getContent());
        note1.setLastEditedDate(updateDto.getLastEditedDate());
        noteRepository.save(note1);

        return note1;

    }

    @Override
    public String delete(Long userId, Long id) {
        Note note=noteRepository.findNoteByUserUserIdAndId(userId,id);
        noteRepository.delete(note);
        return  "Note deleted successfully";
    }

    @Override
    public List<NoteDto> filterByCategory(Long categoryId, Long userId) {
        List<Note> notes = noteRepository.findNotesByCategory_IdAndUserUserId(categoryId, userId);

        if (notes != null) {
            return notes.stream()
                    .map(this::mapNoteToDto)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }



    @Override
    public String archive(Long userId, Long id) {
        Note note=noteRepository.findNoteByUserUserIdAndId(userId,id);

        note.setArchived(true);
        noteRepository.save(note);
        return "Note Archived successfully";
    }

    @Override
    public String unarchive(Long userId, Long id) {
        Note note=noteRepository.findNoteByUserUserIdAndId(userId,id);

        note.setArchived(false);
        noteRepository.save(note);
        return "Note Archived successfully";
    }

}
