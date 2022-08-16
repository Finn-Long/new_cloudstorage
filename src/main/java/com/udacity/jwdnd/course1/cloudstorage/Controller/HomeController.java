package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.Model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.Model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.Service.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final CredentialService credentialService;
    private final FileService fileService;
    private final NoteService noteService;
    private final UserService userService;
    private final EncryptionService encryptionService;

    public HomeController(CredentialService credentialService, FileService fileService, NoteService noteService,
                          UserService userService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String getHomeView(Authentication auth, Model model, @ModelAttribute("noteForm")NoteForm noteForm, @ModelAttribute("credentialForm")CredentialForm credentialForm) {
        User cur_User = userService.getCurrentUser(auth);
        Integer userId = cur_User.getUserId();
        model.addAttribute("files", fileService.getAllFiles(userId));
        model.addAttribute("notes", noteService.getAllNote(userId));
        model.addAttribute("credentials", credentialService.getAllEncrypted(userId));
        model.addAttribute("encryptionService", encryptionService);

        return "home";
    }
}
