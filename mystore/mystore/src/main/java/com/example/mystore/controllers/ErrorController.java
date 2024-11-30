
package com.example.mystore.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        // You can log the error here if you want
        return "error";  // This will map to a view called 'error.html' or 'error.jsp'
    }


}
