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
    @GetMapping
    public String getWypozyczenia() {
        return supabaseService.getWypozyczenia();
    }
    @GetMapping
    public String getKary() {
        return supabaseService.getKary();
    }
    @GetMapping
    public String getKsiazki() {
        return supabaseService.getKsiazki();
    }
    @GetMapping
    public String getUzytkownicy() {
        return supabaseService.getUzytkownicy();
    }
    @GetMapping
    public String getAdmini() {
        return supabaseService.getAdmini();
    }
    @GetMapping
    public String getAutorzy() {
        return supabaseService.getAutorzy();
    }
}
