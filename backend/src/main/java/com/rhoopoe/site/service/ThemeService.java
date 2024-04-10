package com.rhoopoe.site.service;

import com.rhoopoe.site.entity.Theme;
import com.rhoopoe.site.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThemeService {
    private final ThemeRepository themeRepository;

    public Page<Theme> getAllThemes(PageRequest pageRequest) {
        return themeRepository.findAll(pageRequest);
    }
}
