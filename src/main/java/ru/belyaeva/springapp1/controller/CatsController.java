package ru.belyaeva.springapp1.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.belyaeva.springapp1.model.Cat;
import ru.belyaeva.springapp1.model.CustomMultipartFile;
import ru.belyaeva.springapp1.repository.CatRepository;

import javax.servlet.*;
import javax.servlet.descriptor.JspConfigDescriptor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Controller
@RequestMapping("/cats")
public class CatsController {
    @Autowired
    private CatRepository catRepository;

    @GetMapping()
    public String showAllCats(Model model, HttpServletResponse response) throws IOException {
        model.addAttribute("cats", catRepository.findAll());
        return "index";
    }

    @GetMapping("/{id}")
    public String showCatById(@PathVariable("id") Long id, Model model) throws IOException {
        model.addAttribute("cat", catRepository.findById(id).get());
        return "showCat";
    }

    @GetMapping("/newCat")
    public String showForm(Model model){
        model.addAttribute("cat", new Cat());
        return "form";
    }

    @PostMapping()
    public String createNewCat(  @ModelAttribute("cat") @Valid Cat cat,
                               BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()){
            System.out.println("Вызвана ошибка");
            System.out.println(bindingResult);
            return "form";
        }

        cat.setAvatar(cat.getIcon().getBytes()); //картинка в бинарном виде
        cat.setImage("/images/" + cat.getIcon().getOriginalFilename()); //путь к картинке

        //создание картинки в проекте
        CustomMultipartFile customMultipartFile = new CustomMultipartFile(cat.getAvatar(), cat.getIcon().getOriginalFilename());
        customMultipartFile.transferTo(customMultipartFile.getFile());

        catRepository.save(cat);
        return "redirect:/cats";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) throws IOException {
        model.addAttribute("cat", catRepository.findById(id).get());
        return "edit";
    }

    @PatchMapping("/{id}")
    public String update( @Valid @ModelAttribute("cat") Cat cat,
                         BindingResult bindingResult,
                         @PathVariable("id") Long id) throws IOException {

        if (bindingResult.hasErrors())
            return "edit";

        if (!cat.getIcon().getOriginalFilename().equals("")){
            cat.setAvatar(cat.getIcon().getBytes());
            cat.setImage("/images/" + cat.getIcon().getOriginalFilename());

            CustomMultipartFile customMultipartFile = new CustomMultipartFile(cat.getAvatar(), cat.getIcon().getOriginalFilename());
            customMultipartFile.transferTo(customMultipartFile.getFile());
        } else {
            cat.setAvatar(catRepository.findById(id).get().getAvatar());
            cat.setImage(catRepository.findById(id).get().getImage());
        }
        catRepository.save(cat);
        return "redirect:/cats";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id){
        catRepository.deleteById(id);
        return "redirect:/cats";
    }

    @GetMapping("/cats")
    public String main_page(){
        return "redirect:/cats";
    }
}
