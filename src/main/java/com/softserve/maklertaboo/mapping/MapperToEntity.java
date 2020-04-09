package com.softserve.maklertaboo.mapping;

/**
 * The interface provides method declaration for mapping entity classes to dto classes and vise
 * versa.
 *
 * @param <D> dto class type
 * @param <E> entity class type
 */
public interface MapperToEntity<D, E> {

    /**
     * Maps a dto object to an entity object.
     *
     * @param dto to map from
     * @return a mapped object with type {@code E}
     */
    E convertToEntity(D dto);
}
