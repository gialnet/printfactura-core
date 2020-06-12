package com.printfactura.core.controllers;

import com.printfactura.core.domain.MySelf;
import com.printfactura.core.domain.appusers.AppUser;
import com.printfactura.core.services.rocksdb.ServicesUsers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class MySelfController {

    private final ServicesUsers servicesUsers;

    public MySelfController(ServicesUsers servicesUsers) {
        this.servicesUsers = servicesUsers;
    }

    @GetMapping("/register")
    public String showForm(Model model) {

        MySelf mySelf = new MySelf();
        model.addAttribute("mySelf", mySelf);

        List<String> listProfession = Arrays.asList("Developer", "Tester", "Architect");
        model.addAttribute("listProfession", listProfession);

        return "register_form";
    }

    @GetMapping("/myself")
    public String showFormMySelf(Model model) {

        MySelf mySelf = new MySelf();
        model.addAttribute("MySelf", mySelf);

        return "myself";
    }

    @PostMapping("/register")
    public String submitForm(@ModelAttribute("mySelf") MySelf mySelf) {
        System.out.println(mySelf);
        log.info("saving value '{}'", mySelf);
        return "register_success";
    }

    @GetMapping("/login")
    public String showFormLogin(Model model) {

        var appUser = AppUser.builder().build();
        model.addAttribute("AppUser",appUser);

        return "login";
    }

    @PostMapping("/login")
    public String submitFormUser(@ModelAttribute("AppUser") AppUser appUser) {
        System.out.println(appUser);
        log.info("saving value '{}'", appUser);

        Optional result = servicesUsers.FindUser(appUser.getIdUser());
        if (result.isEmpty())
            return "AlertMessage";

        return "menu";
    }
}
