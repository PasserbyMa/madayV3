package com.madayV3.blog.controller;

import com.madayV3.blog.dto.MemberDto;
import com.madayV3.blog.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "") String keyword,
                       Model model) {
        int size = 10;
        String kw = keyword.isBlank() ? null : keyword;
        model.addAttribute("members", memberService.getPage(page, size, kw));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", memberService.getTotalPages(size, kw));
        model.addAttribute("totalCount", memberService.getTotalCount(kw));
        model.addAttribute("keyword", keyword);
        return "members/list";
    }

    @GetMapping("/{id}/data")
    @ResponseBody
    public MemberDto getData(@PathVariable Long id) {
        return memberService.getById(id);
    }

    @PostMapping
    @ResponseBody
    public Map<String, Object> add(@RequestBody MemberDto member) {
        memberService.create(member);
        return Map.of("success", true, "message", "등록 완료");
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Map<String, Object> edit(@PathVariable Long id, @RequestBody MemberDto member) {
        member.setId(id);
        memberService.modify(member);
        return Map.of("success", true, "message", "수정 완료");
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public Map<String, Object> delete(@PathVariable Long id) {
        memberService.remove(id);
        return Map.of("success", true, "message", "삭제 완료");
    }
}
