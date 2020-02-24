package com.softserve.maklertaboo.mapping;

public interface MapperToDto<E, D> {

    D convertToDto(E entity);
}
