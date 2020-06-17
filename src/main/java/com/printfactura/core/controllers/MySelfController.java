package com.printfactura.core.controllers;

import com.printfactura.core.domain.MySelf;
import com.printfactura.core.domain.appusers.AppUser;
import com.printfactura.core.services.rocksdb.ServicesUsers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
public class MySelfController {

    private final ServicesUsers servicesUsers;
    private final ObjectFactory<HttpSession> httpSessionFactory;

    public MySelfController(ServicesUsers servicesUsers, ObjectFactory<HttpSession> httpSessionFactory) {
        this.servicesUsers = servicesUsers;
        this.httpSessionFactory = httpSessionFactory;
    }

    @GetMapping("/")
    public String root(HttpSession session) {

        log.info("index '{}'", session.getAttribute("id"));
        return "index";
    }

    @GetMapping("/menu")
    public String MenuPage(HttpSession session, Authentication a) {

        if (a!=null)
            log.info("authenticate user '{}'",a.getName());

        return "menu";
    }



    @GetMapping("/myself")
    public String showFormMySelf(Model model) {

        MySelf mySelf = new MySelf();
        model.addAttribute("MySelf", mySelf);

        return "myself";
    }



}
