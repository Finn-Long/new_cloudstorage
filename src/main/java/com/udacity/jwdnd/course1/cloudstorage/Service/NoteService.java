package com.udacity.jwdnd.course1.cloudstorage.Service;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getAllNote(Integer userId) {
        return noteMapper.getAllNotes(userId);
    }

    public Note getNote(Integer noteId) {
        return noteMapper.getNote(noteId);
    }

    public int createNote(Note note) {
        return noteMapper.insert(note);
    }

    public int editNote(Integer noteId, String noteTitle, String noteDescription) {
        return noteMapper.update(noteId, noteTitle, noteDescription);
    }

    public int deleteNote(Integer noteId) {
        return noteMapper.delete(noteId);
    }
}
