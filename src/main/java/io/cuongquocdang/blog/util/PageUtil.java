package io.cuongquocdang.blog.util;

import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

public class PageUtil {

    public static <T> Map<String, Object> getPagingResponse(Page<T> page) {

        Map<String, Object> response = new HashMap<>();

        response.put("products", page.getContent());
        response.put("currentPage", page.getNumber());
        response.put("totalItems", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());

        return response;
    }
}
