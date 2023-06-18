package com.example.notificationbot.controller;

import com.example.notificationbot.modell.Task;
import com.example.notificationbot.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/tasks";
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("task") Task task) {
        return "tasks/new";
    }

    @PostMapping()
    public String update(@ModelAttribute("task") Task task) {

        taskService.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        try {
            model.addAttribute("task", taskService.findById(id).orElseThrow(EntityNotFoundException::new)); // change for something custom
        } catch (EntityNotFoundException e) {
            return "redirect:/tasks";
        }
        return "tasks/edit";
    }

    @PatchMapping("{id}")
    public String update(@ModelAttribute("task") Task task,
                         @PathVariable("id") long id) {

        taskService.update(id, task);
        return "redirect:/tasks";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") long id) {
        taskService.delete(id);
        return "redirect:/tasks";
    }


}




























