package com.example.angularschool.controller;

import com.example.angularschool.ds.Category;
import com.example.angularschool.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class CategoryController {

    private  final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all-categories")
    public String  showAll(Model model){
        model.addAttribute("categories",categoryService.findAllCategory());
        return "admin/all-categories";

    }
    @GetMapping("/create-category")
    public String create(Model model){
        model.addAttribute("category",new Category());
        return "admin/category-form";
    }

    @PostMapping("/create-category")
    public String save(@Valid Category category, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "admin/category-form";
        }
        else {
            categoryService.saveCategory(category);
        }
        return "redirect:/all-categories";
    }



}
