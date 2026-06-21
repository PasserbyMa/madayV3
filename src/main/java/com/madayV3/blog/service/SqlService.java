package com.madayV3.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SqlService {

    private final JdbcTemplate jdbcTemplate;

    public Object execute(String sql) {
        String upper = sql.trim().toUpperCase();
        if (upper.startsWith("SELECT") || upper.startsWith("SHOW") || upper.startsWith("DESC") || upper.startsWith("WITH")) {
            return jdbcTemplate.queryForList(sql);
        } else if (upper.startsWith("INSERT") || upper.startsWith("UPDATE") || upper.startsWith("DELETE")) {
            int rows = jdbcTemplate.update(sql);
            return Map.of("message", rows + "건 처리됨");
        } else {
            jdbcTemplate.execute(sql);
            return Map.of("message", "DDL 실행 완료");
        }
    }
}
