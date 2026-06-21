package com.madayV3.blog.controller;

import com.madayV3.blog.service.MariaSqlService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/maria-sql")
@RequiredArgsConstructor
public class MariaSqlController {

    private final MariaSqlService mariaSqlService;

    @PostMapping("/execute")
    public Object execute(@RequestBody Map<String, String> body) {
        try {
            return Map.of("success", true, "result", mariaSqlService.execute(body.get("sql")));
        } catch (Exception e) {
            return Map.of("success", false, "error", e.getMessage());
        }
    }
}
