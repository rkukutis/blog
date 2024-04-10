package com.rhoopoe.site.controller;

import com.rhoopoe.site.entity.Post;
import com.rhoopoe.site.entity.Theme;
import com.rhoopoe.site.service.ThemeService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("themes")
@RequiredArgsConstructor
public class ThemeController {
    private final ThemeService themeService;

    @GetMapping
    public ResponseEntity<Page<Theme>> getAllPosts(@RequestParam(defaultValue = "1") @Min(0) Integer page,
                                                  @RequestParam(defaultValue = "10") @Min(0) Integer limit,
                                                  @RequestParam(defaultValue = "createdAt") String sortBy,
                                                  @RequestParam(defaultValue = "false") boolean sortDesc) {
        Sort.Direction direction = sortDesc ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        PageRequest pageRequest = PageRequest.of(page, limit, sort);
        Page<Theme> returnedThemes = themeService.getAllThemes(pageRequest);
        return ResponseEntity.ok().body(returnedThemes);
    }
}
