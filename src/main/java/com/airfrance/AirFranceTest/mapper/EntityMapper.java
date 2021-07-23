package com.airfrance.AirFranceTest.mapper;

import java.util.List;
/**
 *
 * @param <D> Dto
 *  @param <E> Entity
 */
public interface EntityMapper<D, E> {

    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntityList(List<D> dtoList);

    List<D> toDtoList(List<E> entityList);
}
