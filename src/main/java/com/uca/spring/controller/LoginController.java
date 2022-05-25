package com.uca.spring.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.uca.spring.model.Usuario;
import com.uca.spring.util.UsuarioService;

@Controller
@RequestMapping("/")
public class LoginController {	
	@Autowired
	UsuarioService userService;

    @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();          
        model.setViewName("index.jsp");
        return model;

    }

   @RequestMapping("/Access")
    public ModelAndView login(@ModelAttribute("usuario") Usuario usuario) {
	   Usuario oauthUsuario = userService.login(usuario.getUsername(), usuario.getPassword());
        ModelAndView model = new ModelAndView();
        if(Objects.nonNull(oauthUsuario))
        {
        model.setViewName("index.jsp");
        } else {
        	model.setViewName("login.jsp");
        }
        return model;

    }
   
    @RequestMapping("/")
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {
        ModelAndView model = new ModelAndView();
        model.setViewName("login.jsp");
        return model;

    }
    
    
}
