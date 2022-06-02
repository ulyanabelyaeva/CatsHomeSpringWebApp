package ru.belyaeva.springapp1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.belyaeva.springapp1.model.Cat;
import ru.belyaeva.springapp1.model.CustomMultipartFile;
import ru.belyaeva.springapp1.repository.CatRepository;

import javax.validation.Valid;
import java.io.*;
@Controller
@RequestMapping("/cats")
public class CatsController {
    @Autowired
    private CatRepository catRepository;

    @GetMapping()
    public String showAllCats(Model model) throws IOException {
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

        cat.setAvatar(cat.getIcon().getBytes());
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
