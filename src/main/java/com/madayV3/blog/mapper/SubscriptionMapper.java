package com.madayV3.blog.mapper;

import com.madayV3.blog.dto.SubscriptionDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface SubscriptionMapper {
    List<SubscriptionDto> findPage(@Param("offset") int offset, @Param("limit") int limit);
    int count();
    SubscriptionDto findById(Long id);
    void insert(SubscriptionDto sub);
    void update(SubscriptionDto sub);
    void delete(Long id);
    int countByStatus(@Param("status") String status);
}
