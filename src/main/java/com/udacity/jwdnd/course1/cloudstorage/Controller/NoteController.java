package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import com.udacity.jwdnd.course1.cloudstorage.Model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.Service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.Service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/note")
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping("/delete_note/{noteId}")
    public String delete_note(Authentication auth, @PathVariable Integer noteId, Model model) {
        try {
            noteService.deleteNote(noteId);
            model.addAttribute("isSuccess", true);
            model.addAttribute("successMsg", "The note has been successfully deleted!");
        }catch(Exception e) {
            model.addAttribute("isError", true);
            model.addAttribute("errorMsg", "An error occurred when deleting the note, please try again");
        }

        return "result";
    }

    @PostMapping("/add_note")
    public String add_or_update_note(Authentication auth, @ModelAttribute("noteForm") NoteForm noteForm, Model model) {
        String username = (String) auth.getPrincipal();
        User user = userService.getUser(username);
        String error_Msg = null;

        if (noteForm.getNoteId() != null) {
            noteService.editNote(noteForm.getNoteId(), noteForm.getNoteTitle(), noteForm.getNoteDescription());
            model.addAttribute("isSuccess", true);
            model.addAttribute("successMsg", "Note has been successfully updated");
        }else {
            try {
                noteService.createNote(new Note(null, noteForm.getNoteTitle(), noteForm.getNoteDescription(), user.getUserId()));
                model.addAttribute("isSuccess", true);
                model.addAttribute("successMsg", "Note has been successfully created");
            }catch(Exception e) {
                model.addAttribute("isError", true);
                model.addAttribute("errorMsg", "Something went wrong when creating the note, please try again");
            }
        }
        model.addAttribute("notes", noteService.getAllNote(user.getUserId()));

        return "result";
    }
}
