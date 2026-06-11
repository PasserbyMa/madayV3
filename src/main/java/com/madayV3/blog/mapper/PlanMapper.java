package com.madayV3.blog.mapper;

import com.madayV3.blog.dto.PlanDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PlanMapper {
    List<PlanDto> findAll();
    PlanDto findById(Long id);
}
