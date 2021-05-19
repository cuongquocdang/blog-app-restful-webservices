package io.cuongquocdang.blog.util;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

public class ConvertUtil {

    private static ModelMapper modelMapper;

    public static <REQ, E> E convertRequestToEntity(REQ request, Class<E> clazz) {
        return getMapper().map(request, clazz);
    }

    public static <E, RESP> RESP convertEntityToResponse(E entity, Class<RESP> clazz) {
        return getMapper().map(entity, clazz);
    }

    public static <RESP, E> Page<RESP> convertPageEntityToPageResponse(Page<E> pageEntity, Class<RESP> clazz) {
        return pageEntity.map(entity -> convertEntityToResponse(entity, clazz));
    }

    private static ModelMapper getMapper() {
        if (modelMapper == null) {
            modelMapper = new ModelMapper();
        }
        return modelMapper;
    }
}
