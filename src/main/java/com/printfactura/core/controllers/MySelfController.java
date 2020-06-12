package com.printfactura.core.controllers;

import com.printfactura.core.domain.MySelf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
public class MySelfController {

    @GetMapping("/register")
    public String showForm(Model model) {

        MySelf mySelf = new MySelf();
        model.addAttribute("mySelf", mySelf);

        List<String> listProfession = Arrays.asList("Developer", "Tester", "Architect");
        model.addAttribute("listProfession", listProfession);

        return "register_form";
    }

    @PostMapping("/register")
    public String submitForm(@ModelAttribute("mySelf") MySelf mySelf) {
        System.out.println(mySelf);
        log.info("saving value '{}'", mySelf);
        return "register_success";
    }

    @GetMapping("/login")
    public String showFormLogin(Model model) {

        /*MySelf mySelf = new MySelf();
        model.addAttribute("mySelf", mySelf);

        List<String> listProfession = Arrays.asList("Developer", "Tester", "Architect");
        model.addAttribute("listProfession", listProfession);*/

        return "login";
    }
}
