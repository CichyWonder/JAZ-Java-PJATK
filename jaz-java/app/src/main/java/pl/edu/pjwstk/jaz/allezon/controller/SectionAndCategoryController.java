package pl.edu.pjwstk.jaz.allezon.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.jaz.allezon.request.CategoryRequest;
import pl.edu.pjwstk.jaz.allezon.request.SectionRequest;
import pl.edu.pjwstk.jaz.allezon.service.AllezonAdminService;

@RestController
public class SectionAndCategoryController {
    private final AllezonAdminService allezonAdminService;

    public SectionAndCategoryController(AllezonAdminService allezonAdminServicec) {
        this.allezonAdminService = allezonAdminServicec;
    }


    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/addSection")
    public void addSection(@RequestBody @Validated SectionRequest section){
        allezonAdminService.createSection(section.getName());
    }

    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("/updateSection/{sectionName}")
    public void updateSection(@RequestBody @Validated SectionRequest section, @PathVariable("sectionName") String sectionName){
        allezonAdminService.updateSection(sectionName,section.getName());
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/addCategory")
    public void createCategory(@RequestBody @Validated CategoryRequest category){
        allezonAdminService.createCategory(category);
    }

    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("/updateCategory/{categoryName}")
    public void updateCategory(@RequestBody @Validated CategoryRequest category, @PathVariable("categoryName") String categoryName){
        allezonAdminService.updateCategory(category, categoryName);
    }

}

