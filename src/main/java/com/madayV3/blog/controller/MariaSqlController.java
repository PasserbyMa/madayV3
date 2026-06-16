package com.madayV3.blog.controller;

import com.madayV3.blog.service.MariaSqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/maria-sql")
public class MariaSqlController {

    @Autowired
    private MariaSqlService mariaSqlService;

    @PostMapping("/execute")
    public Object execute(@RequestBody Map<String, String> body) {
        try {
            return Map.of("success", true, "result", mariaSqlService.execute(body.get("sql")));
        } catch (Exception e) {
            return Map.of("success", false, "error", e.getMessage());
        }
    }
}
