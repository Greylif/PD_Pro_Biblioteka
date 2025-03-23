package com.example.pd_pro_biblioteka.control;

import com.example.pd_pro_biblioteka.service.SupabaseClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/library")
public class Control {
    private final SupabaseClient supabaseService;

    public Control(SupabaseClient supabaseService) {
        this.supabaseService = supabaseService;
    }

    @GetMapping
    public String getPlacowki() {
        return supabaseService.getPlacowki();
    }
}
