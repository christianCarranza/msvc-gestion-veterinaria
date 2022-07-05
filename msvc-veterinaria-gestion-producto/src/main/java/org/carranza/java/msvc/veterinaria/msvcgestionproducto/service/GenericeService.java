package org.carranza.java.msvc.veterinaria.msvcgestionproducto.service;


import org.carranza.java.msvc.veterinaria.msvcgestionproducto.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface GenericeService<T> {

    List<T> findByLike(T t) throws ServiceException;

    Optional<T> findById(Long id) throws ServiceException;

    T save(T t) throws ServiceException;

    T update(Long id, T t) throws ServiceException;

    void delete (long id) throws ServiceException;
}