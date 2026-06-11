package com.madayV3.blog.mapper;

import com.madayV3.blog.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface MemberMapper {
    List<MemberDto> findAll();
    List<MemberDto> findPage(@Param("offset") int offset, @Param("limit") int limit, @Param("keyword") String keyword);
    int count(@Param("keyword") String keyword);
    MemberDto findById(Long id);
    void insert(MemberDto member);
    void update(MemberDto member);
    void delete(Long id);
}
