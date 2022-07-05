package org.carranza.java.msvc.veterinaria.msvcgestionproducto.service.Impl;

import org.carranza.java.msvc.veterinaria.msvcgestionproducto.dto.ProductoDTO;
import org.carranza.java.msvc.veterinaria.msvcgestionproducto.dto.mapper.ProductoMapper;
import org.carranza.java.msvc.veterinaria.msvcgestionproducto.models.CategoriaEntity;
import org.carranza.java.msvc.veterinaria.msvcgestionproducto.models.ProductoEntity;
import org.carranza.java.msvc.veterinaria.msvcgestionproducto.repository.ProductoRepository;
import org.carranza.java.msvc.veterinaria.msvcgestionproducto.service.ProductoService;
import org.carranza.java.msvc.veterinaria.msvcgestionproducto.service.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final EntityManager em;
    private final ProductoRepository productoRepository;
    private static final ProductoMapper productoMapper = ProductoMapper.INSTANCE;

    public ProductoServiceImpl(EntityManager em,
                               ProductoRepository productoRepository) {
        this.em = em;
        this.productoRepository = productoRepository;
    }

    @Override
    public Page<ProductoDTO> search(String producto, Long categoria, Integer stock, Double precio, LocalDateTime fechainicio, LocalDateTime fechafin, Pageable paginador) throws ServiceException {
        try {
            var criteriaBuilder = em.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(ProductoEntity.class);
            var rootAforador = criteriaQuery.from(ProductoEntity.class);
            Join<ProductoEntity, CategoriaEntity> joinCategoria = rootAforador.join("categoria", JoinType.INNER);
            var predicates = new ArrayList<Predicate>();

            if(!(producto == null || producto.isEmpty())){
                String nombreProducto = producto.trim().toLowerCase();
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(rootAforador.get("nombre")), "%" + nombreProducto.replaceAll("\\s+", "") + "%"));
            }
            if(stock != null) {
                predicates.add(criteriaBuilder.equal(rootAforador.get("stock"), stock));
            }
            if(precio != null) {
                predicates.add(criteriaBuilder.equal(rootAforador.get("precio"), precio));
            }
            if(categoria != null) {
                predicates.add(criteriaBuilder.equal(rootAforador.get("categoria"), categoria));
            }
            if (!((fechainicio == null) && (fechafin == null))) {
                predicates.add(criteriaBuilder.between(rootAforador.get("fechaCreacion"), fechainicio, fechafin));
            }
            predicates.add(criteriaBuilder.equal(rootAforador.get("estado"), 1));

            criteriaQuery.where(predicates.toArray(new Predicate[0]));

            criteriaQuery.select(rootAforador).distinct(true);
            criteriaQuery.orderBy(criteriaBuilder.desc(rootAforador.get("fechaCreacion")));

            long offset = paginador.getOffset();
            var result = em.createQuery(criteriaQuery)
                    .setMaxResults(paginador.getPageSize())
                    .setFirstResult((int) offset)
                    .getResultList();

            var listDTO  = result.stream().map(productoMapper::entityToGetDto).collect(Collectors.toList());

            Long result1 = (long) em.createQuery(criteriaQuery).getResultList().size();

            return (Page<ProductoDTO>) paginate(listDTO, paginador, result1);

        }catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductoDTO> export(String producto, Long categoria, Integer stock, Double precio, LocalDateTime fechainicio, LocalDateTime fechafin, String ordencol, String ordendir) throws ServiceException {
        try {
            var criteriaBuilder = em.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(ProductoEntity.class);
            var rootAforador = criteriaQuery.from(ProductoEntity.class);
            Join<ProductoEntity, CategoriaEntity> joinCategoria = rootAforador.join("categoria", JoinType.INNER);
            var predicates = new ArrayList<Predicate>();

            if(!(producto == null || producto.isEmpty())){
                String nombreProducto = producto.trim().toLowerCase();
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(rootAforador.get("nombre")), "%" + nombreProducto.replaceAll("\\s+", "") + "%"));
            }
            if(stock != null) {
                predicates.add(criteriaBuilder.equal(rootAforador.get("stock"), stock));
            }
            if(precio != null) {
                predicates.add(criteriaBuilder.equal(rootAforador.get("precio"), precio));
            }
            if(categoria != null) {
                predicates.add(criteriaBuilder.equal(rootAforador.get("categoria"), categoria));
            }
            if (!((fechainicio == null) && (fechafin == null))) {
                predicates.add(criteriaBuilder.between(rootAforador.get("fechaCreacion"), fechainicio, fechafin));
            }
            predicates.add(criteriaBuilder.equal(rootAforador.get("estado"), 1));

            criteriaQuery.where(predicates.toArray(new Predicate[0]));
            criteriaQuery.select(rootAforador).distinct(true);
            criteriaQuery.orderBy(criteriaBuilder.desc(rootAforador.get("fechaCreacion")));

            var result = em.createQuery(criteriaQuery).getResultList();

            return result.stream().map(productoMapper::entityToGetDto).collect(Collectors.toList());
        }catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductoDTO> saveList(List<ProductoDTO> productoDTOList) throws ServiceException {
        try {
            var productoEntity = productoDTOList.stream().map(productoMapper::postDtoToEntity).collect(Collectors.toList());
            var saveResult = this.productoRepository.saveAll(productoEntity);
            return saveResult.stream().map(productoMapper::entityToGetDto).collect(Collectors.toList());
        }catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductoDTO> findByLike(ProductoDTO productoDTO) throws ServiceException {
        return null;
    }

    @Override
    public Optional<ProductoDTO> findById(Long id) throws ServiceException {
        try {
            var entity = this.productoRepository.findById(id);
            return entity.map(productoEntity -> Optional.of(productoMapper.entityToGetDto(productoEntity))).orElse(null);
        }catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ProductoDTO save(ProductoDTO productoDTO) throws ServiceException {
        try {
            var productoEntity = productoMapper.postDtoToEntity(productoDTO);
            var save = this.productoRepository.save(productoEntity);
            return productoMapper.entityToGetDto(save);

        }catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ProductoDTO update(Long id, ProductoDTO productoDTO) throws ServiceException {
        return null;
    }

    @Override
    public void delete(long id) throws ServiceException {
        try {
            var entity = this.productoRepository.findById(id);
            if (entity.isPresent()){
                entity.get().setEstado(1);
                this.productoRepository.save(entity.get());
            }
        }catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public Page<?> paginate(List<?> l, Pageable pageable, Long t) {
        return PageableExecutionUtils.getPage(l, pageable, () -> t);
    }

}
