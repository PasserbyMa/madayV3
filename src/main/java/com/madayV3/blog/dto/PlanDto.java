package com.madayV3.blog.dto;

import lombok.Data;

@Data
public class PlanDto {
    private Long id;
    private String planName;
    private Integer monthlyFee;
    private Integer dataGb;
    private Integer callMin;
    private String description;
}
