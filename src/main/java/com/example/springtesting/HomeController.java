package com.example.springtesting;

import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {
    @RequestMapping("/")
    public @ResponseBody String greeting() {
        return "Hello, World";
    }
    @GetMapping("/add")
    public Object add(
            @RequestParam(value = "a", defaultValue = "0") Float a,
            @RequestParam(value = "b", defaultValue = "0") Float b){

        return a+b;
    }
    @GetMapping("/subtract")
    public Object subtract(
            @RequestParam(value = "a", defaultValue = "0") Float a,
            @RequestParam(value = "b", defaultValue = "0") Float b){

        return a-b;
    }
    @GetMapping("/multiply")
    public Object multiply(
            @RequestParam(value = "a", defaultValue = "0") Float a,
            @RequestParam(value = "b", defaultValue = "0") Float b){

        return a*b;
    }
    @GetMapping("/divide")
    public Object divide(
            @RequestParam(value = "a", defaultValue = "0") Float a,
            @RequestParam(value = "b", defaultValue = "0") Float b){

        return a/b;
    }
}