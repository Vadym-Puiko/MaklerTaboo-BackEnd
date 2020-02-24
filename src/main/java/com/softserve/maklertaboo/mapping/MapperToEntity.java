package com.softserve.maklertaboo.mapping;

public interface MapperToEntity<D, E> {

    E convertToEntity(D dto);
}
