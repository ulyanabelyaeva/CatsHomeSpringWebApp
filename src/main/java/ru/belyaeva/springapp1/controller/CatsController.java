package ru.belyaeva.springapp1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.belyaeva.springapp1.model.Cat;
import ru.belyaeva.springapp1.repository.CatRepository;

import javax.validation.Valid;

@Controller
@RequestMapping("/cats")
public class CatsController {

    @Autowired
    private CatRepository catRepository;

    //в аннотации то, что получаем на вход
    @GetMapping()
    public String showAllCats(Model model){
        //получаем котов из бд
        Iterable<Cat> cats = catRepository.findAll();
        //под ключом cats будет лежать список котов
        model.addAttribute("cats", cats);
        //название страницы
        return "cats/index";
    }

    @GetMapping("/{id}")
    public String showCatById(@PathVariable("id") Long id, Model model){
        Cat cat = catRepository.findById(id).get();
        model.addAttribute("cat", cat);
        return "cats/showCat";
    }

    @GetMapping("/newCat")
    public String showForm(Model model){
        model.addAttribute("cat", new Cat());
        return "cats/form";
    }

    @PostMapping()
    public String createNewCat(  @ModelAttribute("cat") @Valid Cat cat,
                               BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            System.out.println("Вызвана ошибка");
            return "cats/form";
        }

        catRepository.save(cat);
        return "redirect:/cats";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id){
        Cat cat = catRepository.findById(id).get();
        model.addAttribute("cat", cat);
        return "cats/edit";
    }

    @PatchMapping("/{id}")
    public String update( @Valid @ModelAttribute("cat") Cat cat,
                         BindingResult bindingResult,
                         @PathVariable("id") Long id){

        if (bindingResult.hasErrors())
            return "cats/edit";

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
