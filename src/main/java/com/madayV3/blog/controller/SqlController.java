package com.madayV3.blog.controller;

import com.madayV3.blog.service.SqlService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/sql")
@RequiredArgsConstructor
public class SqlController {

    private final SqlService sqlService;

    @PostMapping("/execute")
    public Object execute(@RequestBody Map<String, String> body) {
        try {
            return Map.of("success", true, "result", sqlService.execute(body.get("sql")));
        } catch (Exception e) {
            return Map.of("success", false, "error", e.getMessage());
        }
    }
}
