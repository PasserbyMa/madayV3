package com.madayV3.blog.dto;

import lombok.Data;

@Data
public class MemberDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String birthDate;
    private String address;
    private String status;
    private String createdAt;
    private String updatedAt;
}
