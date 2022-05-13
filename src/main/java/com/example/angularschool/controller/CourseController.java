package com.example.angularschool.controller;

import com.example.angularschool.ds.Course;
import com.example.angularschool.service.CategoryService;
import com.example.angularschool.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class CourseController {
    private final CourseService courseService;
    private final CategoryService categoryService;

    public CourseController(CourseService courseService,CategoryService categoryService) {
        this.courseService = courseService;
        this.categoryService=categoryService;
    }

    @GetMapping("/create-course")
    public String create(Model model){
        model.addAttribute("course",new Course());
        model.addAttribute("categories",categoryService.findAllCategory());
        return "admin/course-form";
    }

    @PostMapping("/create-course")
    public String save(@Valid Course course, @RequestParam("catId") int categoryId , BindingResult result, RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            return "admin/course-form";
        }
        else {
            courseService.saveCourse(categoryId,course);
            redirectAttributes.addFlashAttribute("success",true);
        }
        return "redirect:/all-courses";
    }

    @GetMapping("/all-courses")
    public String allCourses(Model model){
        model.addAttribute("courses",courseService.findAll());
        model.addAttribute("success",model.containsAttribute("success"));
        model.addAttribute("delete",model.containsAttribute("delete"));
        return "admin/all-courses";
    }

    @GetMapping("/all-courses/delete/{id}")
    public String removeCourse(@PathVariable ("id")int courseId,RedirectAttributes redirectAttributes){
        courseService.removeCourse(courseId);
        redirectAttributes.addFlashAttribute("delete",true);
        return "redirect:/all-courses";
    }

    @GetMapping("/all-courses/update")
    public String update(@RequestParam("cid")int courseId,Model model){
        model.addAttribute("categories",categoryService.findAllCategory());
        model.addAttribute("course",courseService.findCourse(courseId));
        this.courseId=courseId;
        return "admin/course-update";

    }
    @PostMapping("/all-courses/process")
    public String processUpdate(@RequestParam("catId")int catId, Course course,BindingResult result){
        if (result.hasErrors()){
            return "admin/course-update";
        }
        else {
            courseService.updateCourse(courseId,course,catId);
        }

        return "redirect:/all-courses";
    }

    private int courseId;

}

