package com.madayV3.blog.service;

import com.madayV3.blog.dto.MemberDto;
import com.madayV3.blog.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberMapper memberMapper;

    public List<MemberDto> getPage(int page, int size, String keyword) {
        int offset = (page - 1) * size;
        return memberMapper.findPage(offset, size, keyword);
    }

    public int getTotalPages(int size, String keyword) {
        int total = memberMapper.count(keyword);
        return (int) Math.ceil((double) total / size);
    }

    public int getTotalCount(String keyword) {
        return memberMapper.count(keyword);
    }

    public MemberDto getById(Long id) {
        return memberMapper.findById(id);
    }

    public void create(MemberDto member) {
        memberMapper.insert(member);
    }

    public void modify(MemberDto member) {
        memberMapper.update(member);
    }

    public void remove(Long id) {
        memberMapper.delete(id);
    }
}
