package com.madayV3.blog.controller;

import com.madayV3.blog.dto.SubscriptionDto;
import com.madayV3.blog.mapper.MemberMapper;
import com.madayV3.blog.mapper.PlanMapper;
import com.madayV3.blog.mapper.SubscriptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionMapper subscriptionMapper;
    private final MemberMapper memberMapper;
    private final PlanMapper planMapper;

    @GetMapping
    public String list(@RequestParam(defaultValue = "1") int page, Model model) {
        int size = 10, offset = (page - 1) * size;
        int total = subscriptionMapper.count();
        model.addAttribute("subscriptions", subscriptionMapper.findPage(offset, size));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) total / size));
        model.addAttribute("totalCount", total);
        model.addAttribute("members", memberMapper.findAll());
        model.addAttribute("plans", planMapper.findAll());
        return "subscriptions/list";
    }

    @GetMapping("/{id}/data")
    @ResponseBody
    public SubscriptionDto getData(@PathVariable Long id) {
        return subscriptionMapper.findById(id);
    }

    @PostMapping
    @ResponseBody
    public Map<String, Object> add(@RequestBody SubscriptionDto sub) {
        subscriptionMapper.insert(sub);
        return Map.of("success", true, "message", "등록 완료");
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Map<String, Object> edit(@PathVariable Long id, @RequestBody SubscriptionDto sub) {
        sub.setId(id);
        subscriptionMapper.update(sub);
        return Map.of("success", true, "message", "수정 완료");
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public Map<String, Object> delete(@PathVariable Long id) {
        subscriptionMapper.delete(id);
        return Map.of("success", true, "message", "삭제 완료");
    }
}
