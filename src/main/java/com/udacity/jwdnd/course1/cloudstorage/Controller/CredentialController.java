package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.Model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.Service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.Service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credential")
public class CredentialController {
    private final UserService userService;
    private final CredentialService credentialService;

    public CredentialController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @GetMapping("/delete_credential/{credential_Id}")
    public String delete_credential(Authentication auth, @PathVariable Integer credential_Id, Model model) {
        try {
            credentialService.deleteCredential(credential_Id);
            model.addAttribute("isSuccess", true);
            model.addAttribute("successMsg", "Successfully deleted the note");
        }catch(Exception e){
            model.addAttribute("isError", true);
            model.addAttribute("errorMsg", "Something went wrong when deleting the note, please try again");
        }

        return "result";
    }

    @PostMapping("/create_credential")
    public String create_or_edit_credential(Authentication auth, @ModelAttribute("credentialForm")CredentialForm credentialForm, Model model) {
        String username = (String) auth.getPrincipal();
        Integer userId = userService.getUser(username).getUserId();

        if (credentialForm.getCredentialId() == null) {
            try{
                credentialService.createCredential(new Credential(null, credentialForm.getUrl(), credentialForm.getUsername(),null, credentialForm.getPassword(), userId));
                model.addAttribute("isSuccess", true);
                model.addAttribute("successMsg", "Successfully created the credential");
            }catch(Exception e){
                model.addAttribute("isError", true);
                model.addAttribute("errorMsg", "An error occurred when creating the credential, please try again");
            }
        }else {
            try {
                String key = credentialService.getCredential(credentialForm.getCredentialId()).getKey();
                credentialService.editCredential(new Credential(credentialForm.getCredentialId(), credentialForm.getUrl(), credentialForm.getUsername(), key, credentialForm.getPassword(), userId));
                model.addAttribute("isSuccess", true);
                model.addAttribute("successMsg", "Successfully updated the credential");
            }catch(Exception e) {
                model.addAttribute("isError", true);
                model.addAttribute("errorMsg", "Something went wrong when updating the credential, please try again");
            }
        }

        return "result";
    }
}
