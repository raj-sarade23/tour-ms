package com.example.tour_ms;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class visa {

    @GetMapping("/flight")

    public String getData() {
        return "Please book visa in 20% discount.VISA-----..";
    }

}