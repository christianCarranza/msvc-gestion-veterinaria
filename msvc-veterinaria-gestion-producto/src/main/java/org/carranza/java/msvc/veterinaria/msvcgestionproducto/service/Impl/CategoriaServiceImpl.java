package org.carranza.java.msvc.veterinaria.msvcgestionproducto.service.Impl;


import org.carranza.java.msvc.veterinaria.msvcgestionproducto.dto.CategoriaDTO;
import org.carranza.java.msvc.veterinaria.msvcgestionproducto.dto.mapper.CategoriaMapper;
import org.carranza.java.msvc.veterinaria.msvcgestionproducto.repository.CategoriaRepository;
import org.carranza.java.msvc.veterinaria.msvcgestionproducto.service.CategoriaService;
import org.carranza.java.msvc.veterinaria.msvcgestionproducto.service.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final EntityManager em;
    private final CategoriaRepository categoriaRepository;
    private static final CategoriaMapper categoriaMapper = CategoriaMapper.INSTANCE;

    public CategoriaServiceImpl(EntityManager em,
                                CategoriaRepository categoriaRepository) {
        this.em = em;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public Page<CategoriaDTO> search(Pageable paginador) throws ServiceException {
        try {
            var categoriaPage = this.categoriaRepository.findAll(paginador);
            var listDTO  = categoriaPage.stream().map(categoriaMapper::entityToGetDto).collect(Collectors.toList());
            return (Page<CategoriaDTO>) paginate(listDTO, paginador, categoriaPage.getTotalElements());
        }catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CategoriaDTO> export() throws ServiceException {
        try {
            var categoriaList = this.categoriaRepository.findAll();
            return categoriaList.stream().map(categoriaMapper::entityToGetDto).collect(Collectors.toList());
        }catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CategoriaDTO> saveList(List<CategoriaDTO> categoriaDTOList) throws ServiceException {
        try {
            var categoriaEntity = categoriaDTOList.stream().map(categoriaMapper::postDtoToEntity).collect(Collectors.toList());
            var saveResult = this.categoriaRepository.saveAll(categoriaEntity);
            return saveResult.stream().map(categoriaMapper::entityToGetDto).collect(Collectors.toList());
        }catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CategoriaDTO> findByLike(CategoriaDTO categoriaDTO) throws ServiceException {
        return null;
    }

    @Override
    public Optional<CategoriaDTO> findById(Long id) throws ServiceException {
        try {
            var entity = this.categoriaRepository.findById(id);
            return entity.map(categoriaEntity -> Optional.of(categoriaMapper.entityToGetDto(categoriaEntity))).orElse(null);
        }catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public CategoriaDTO save(CategoriaDTO categoriaDTO) throws ServiceException {
        try {
            var categoriaEntity = categoriaMapper.postDtoToEntity(categoriaDTO);
            var save = this.categoriaRepository.save(categoriaEntity);
            return categoriaMapper.entityToGetDto(save);

        }catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public CategoriaDTO update(Long id, CategoriaDTO categoriaDTO) throws ServiceException {
        try {
            var entity = this.categoriaRepository.findById(id);
            if (entity.isPresent()){
                entity.get().setDescripcion(categoriaDTO.getDescripcion().isEmpty()?entity.get().getDescripcion():categoriaDTO.getDescripcion());
                var save = this.categoriaRepository.save(entity.get());
                return  categoriaMapper.entityToGetDto(save);
            }
            return null;
        }catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(long id) throws ServiceException {
        try {
            var entity = this.categoriaRepository.findById(id);
            if (entity.isPresent()){
                entity.get().setEstado(1);
                this.categoriaRepository.save(entity.get());
            }
        }catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public Page<?> paginate(List<?> l, Pageable pageable, Long t) {
        return PageableExecutionUtils.getPage(l, pageable, () -> t);
    }

}
