package com.printfactura.core.controllers;

import com.printfactura.core.domain.appusers.AppUser;
import com.printfactura.core.services.lucene.LuceneServiceAppUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
public class UserController {

    private final LuceneServiceAppUser luceneServiceAppUser;

    public UserController(LuceneServiceAppUser luceneServiceAppUser) {
        this.luceneServiceAppUser = luceneServiceAppUser;
    }

    @GetMapping("/register")
    public String showForm(Model model) {

        AppUser appUser = new AppUser();
        model.addAttribute("AppUser", appUser);

        return "register";
    }

    @PostMapping("/register")
    public String submitForm(@ModelAttribute("AppUser") AppUser appUser) {
        System.out.println(appUser);
        log.info("saving value '{}'", appUser);
        return "register_success";
    }

    @ModelAttribute("register-email")
    public List<AppUser> emails(String email) throws IOException, ParseException {

        luceneServiceAppUser.searchByIdUser(email);
        return null; //messageRepository.findAll();
    }
}
