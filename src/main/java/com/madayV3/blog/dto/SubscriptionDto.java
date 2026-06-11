package com.madayV3.blog.dto;

import lombok.Data;

@Data
public class SubscriptionDto {
    private Long id;
    private Long memberId;
    private Long planId;
    private String memberName;
    private String planName;
    private Integer monthlyFee;
    private String startDate;
    private String endDate;
    private String status;
    private String createdAt;
}
