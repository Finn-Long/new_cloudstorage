package com.udacity.jwdnd.course1.cloudstorage.Model;

public class NoteForm {
    private Integer noteId;
    private String noteTitle;
    private String noteDescription;

    public NoteForm(Integer noteId, String nodeTitle, String nodeDescription) {
        this.noteId = noteId;
        this.noteTitle = nodeTitle;
        this.noteDescription = nodeDescription;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }
}
