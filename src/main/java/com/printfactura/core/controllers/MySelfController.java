package com.printfactura.core.controllers;

import com.printfactura.core.domain.MySelf;
import com.printfactura.core.services.rocksdb.ServicesMySelf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class MySelfController {

    private final ServicesMySelf servicesMySelf;
    private final ObjectFactory<HttpSession> httpSessionFactory;
    private MySelf mySelfController;

    public MySelfController(ServicesMySelf servicesMySelf, ObjectFactory<HttpSession> httpSessionFactory) {
        this.servicesMySelf = servicesMySelf;
        this.httpSessionFactory = httpSessionFactory;
    }


    @GetMapping("/")
    public String root(HttpSession session) {

        log.info("index uuid {}", session.getAttribute("uuid"));
        return "index";
    }

    @GetMapping("/menu")
    public String MenuPage(HttpSession session, Authentication a) {

        if (a!=null){
            log.info("authenticate user '{}'",a.getName());
            log.info("menu -> session uuid user '{}'",session.getAttribute("uuid"));
        }


        return "menu";
    }

    /**
     *
     * @param model
     * @param a
     * @return
     */
    @GetMapping("/myselfform")
    public String showFormMySelf(Model model, Authentication a) {

        if (a!=null) {
            log.info("authenticate user '{}'", a.getName());
            mySelfController = servicesMySelf.FindMySelf(a.getName());
            log.info("mySelft GET object user '{}'", mySelfController);

            model.addAttribute("MySelf2", mySelfController);
        }
        else return "/login";

        return "myselfform";
    }


    @PostMapping("/myselfform")
    public String SaveMySelfForm(@ModelAttribute("MySelf2") MySelf myform, Authentication a){

        log.info("Post mySelf");
        log.info("Authentication user '{}'",a.getName());
        log.info("myform object user '{}'", myform);
        //log.info("mySelfController object user '{}'", mySelfController);



        if (myform==null)
            return "myselfform";
        else
            myform.setIdUser(a.getName());

        if (servicesMySelf.SaveMySelf(myform) )
        {
            log.info("Post mySelf update data '{}'", myform);
        }
        else {
            log.info("Post error doesn't was possible to save data in RocksDB");
            return "AlertMessage";
        }
        return "myself_success";
    }

}
