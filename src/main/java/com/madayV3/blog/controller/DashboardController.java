package com.madayV3.blog.controller;

import com.madayV3.blog.mapper.MemberMapper;
import com.madayV3.blog.mapper.PlanMapper;
import com.madayV3.blog.mapper.SubscriptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final MemberMapper memberMapper;
    private final SubscriptionMapper subscriptionMapper;
    private final PlanMapper planMapper;

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("totalMembers", memberMapper.count(null));
        model.addAttribute("activeSubscriptions", subscriptionMapper.countByStatus("ACTIVE"));
        model.addAttribute("totalPlans", planMapper.findAll().size());
        model.addAttribute("recentMembers", memberMapper.findPage(0, 5, null));
        return "dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
