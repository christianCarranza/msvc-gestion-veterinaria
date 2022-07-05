package org.carranza.java.msvc.veterinaria.msvcgestioncliente.service.Impl;

import org.carranza.java.msvc.veterinaria.msvcgestioncliente.dto.ClienteDTO;
import org.carranza.java.msvc.veterinaria.msvcgestioncliente.dto.mapper.ClienteMapper;
import org.carranza.java.msvc.veterinaria.msvcgestioncliente.models.ClienteEntity;
import org.carranza.java.msvc.veterinaria.msvcgestioncliente.models.TipoDocumentoEntity;
import org.carranza.java.msvc.veterinaria.msvcgestioncliente.repository.ClienteRepository;
import org.carranza.java.msvc.veterinaria.msvcgestioncliente.service.ClienteService;
import org.carranza.java.msvc.veterinaria.msvcgestioncliente.service.exception.ServiceException;
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
public class ClienteServiceImpl implements ClienteService {

    private final EntityManager em;
    private final ClienteRepository clienteRepository;
    private static final ClienteMapper clienteMapper = ClienteMapper.INSTANCE;

    public ClienteServiceImpl(EntityManager em, ClienteRepository clienteRepository) {
        this.em = em;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Page<ClienteDTO> search(String cliente, Integer edad, String sexo, Long tipoDocumento, String documento, String telefono, String celular, String direccion, String correo, LocalDateTime fechainicio, LocalDateTime fechafin, Pageable paginador) throws ServiceException {
        try {
            var criteriaBuilder = em.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(ClienteEntity.class);
            var rootAforador = criteriaQuery.from(ClienteEntity.class);
            Join<ClienteEntity, TipoDocumentoEntity> joinTipoDocumento = rootAforador.join("idTipoDoc", JoinType.INNER);
            var predicates = new ArrayList<Predicate>();

            if(!(cliente == null || cliente.isEmpty())){
                var nombre = criteriaBuilder.concat(rootAforador.get("nombre"), rootAforador.get("apellidoPaterno"));
                var concatenado =  criteriaBuilder.concat(nombre, rootAforador.get("apellidoMaterno"));

                var nombre2 = criteriaBuilder.concat(rootAforador.get("apellidoPaterno"), rootAforador.get("apellidoMaterno"));
                var concatenado2 =  criteriaBuilder.concat(nombre2, rootAforador.get("nombre"));

                var byLikeNombre = criteriaBuilder.like(criteriaBuilder.lower(concatenado), "%"+cliente.toLowerCase().replaceAll("\\s+","")+"%");
                var byLikeApellido = criteriaBuilder.like(criteriaBuilder.lower(concatenado2), "%"+cliente.toLowerCase().replaceAll("\\s+","")+"%");

                predicates.add(criteriaBuilder.or(byLikeNombre, byLikeApellido));

            }
            if(edad != null) {
                predicates.add(criteriaBuilder.equal(rootAforador.get("edad"), edad));
            }
            if (!(sexo == null || sexo.isEmpty())) {
                predicates.add(criteriaBuilder.like(rootAforador.get("sexo"), "%" + sexo.replaceAll("\\s+", "") + "%"));
            }
            if(tipoDocumento != null) {
                predicates.add(criteriaBuilder.equal(joinTipoDocumento.get("id"), tipoDocumento));
            }
            if (!(documento == null || documento.isEmpty())) {
                predicates.add(criteriaBuilder.like(rootAforador.get("documento"), "%" + documento.replaceAll("\\s+", "") + "%"));
            }
            if (!(telefono == null || telefono.isEmpty())) {
                predicates.add(criteriaBuilder.like(rootAforador.get("telefono"), "%" + telefono.replaceAll("\\s+", "") + "%"));
            }
            if (!(celular == null || celular.isEmpty())) {
                predicates.add(criteriaBuilder.like(rootAforador.get("celular"), "%" + celular.replaceAll("\\s+", "") + "%"));
            }
            if (!(direccion == null || direccion.isEmpty())) {
                predicates.add(criteriaBuilder.like(rootAforador.get("direccion"), "%" + direccion.replaceAll("\\s+", "") + "%"));
            }
            if (!(correo == null || correo.isEmpty())) {
                predicates.add(criteriaBuilder.like(rootAforador.get("correo"), "%" + correo.replaceAll("\\s+", "") + "%"));
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

            var listDTO  = result.stream().map(clienteMapper::entityToGetDto).collect(Collectors.toList());

            Long result1 = (long) em.createQuery(criteriaQuery).getResultList().size();

            return (Page<ClienteDTO>) paginate(listDTO, paginador, result1);

        }catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ClienteDTO> export(String cliente, Integer edad, String sexo, Long tipoDocumento, String documento, String telefono, String celular, String direccion, String correo, LocalDateTime fechainicio, LocalDateTime fechafin, String ordencol, String ordendir) throws ServiceException {
        try {
            var criteriaBuilder = em.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(ClienteEntity.class);
            var rootAforador = criteriaQuery.from(ClienteEntity.class);
            Join<ClienteEntity, TipoDocumentoEntity> joinTipoDocumento = rootAforador.join("idTipoDoc", JoinType.INNER);
            var predicates = new ArrayList<Predicate>();

            if(!(cliente == null || cliente.isEmpty())){
                var nombre = criteriaBuilder.concat(rootAforador.get("nombre"), rootAforador.get("apellidoPaterno"));
                var concatenado =  criteriaBuilder.concat(nombre, rootAforador.get("apellidoMaterno"));

                var nombre2 = criteriaBuilder.concat(rootAforador.get("apellidoPaterno"), rootAforador.get("apellidoMaterno"));
                var concatenado2 =  criteriaBuilder.concat(nombre2, rootAforador.get("nombre"));

                var byLikeNombre = criteriaBuilder.like(criteriaBuilder.lower(concatenado), "%"+cliente.toLowerCase().replaceAll("\\s+","")+"%");
                var byLikeApellido = criteriaBuilder.like(criteriaBuilder.lower(concatenado2), "%"+cliente.toLowerCase().replaceAll("\\s+","")+"%");

                predicates.add(criteriaBuilder.or(byLikeNombre, byLikeApellido));

            }
            if(edad != null) {
                predicates.add(criteriaBuilder.equal(rootAforador.get("edad"), edad));
            }
            if (!(sexo == null || sexo.isEmpty())) {
                predicates.add(criteriaBuilder.like(rootAforador.get("sexo"), "%" + sexo.replaceAll("\\s+", "") + "%"));
            }
            if(tipoDocumento != null) {
                predicates.add(criteriaBuilder.equal(joinTipoDocumento.get("id"), tipoDocumento));
            }
            if (!(documento == null || documento.isEmpty())) {
                predicates.add(criteriaBuilder.like(rootAforador.get("documento"), "%" + documento.replaceAll("\\s+", "") + "%"));
            }
            if (!(telefono == null || telefono.isEmpty())) {
                predicates.add(criteriaBuilder.like(rootAforador.get("telefono"), "%" + telefono.replaceAll("\\s+", "") + "%"));
            }
            if (!(celular == null || celular.isEmpty())) {
                predicates.add(criteriaBuilder.like(rootAforador.get("celular"), "%" + celular.replaceAll("\\s+", "") + "%"));
            }
            if (!(direccion == null || direccion.isEmpty())) {
                predicates.add(criteriaBuilder.like(rootAforador.get("direccion"), "%" + direccion.replaceAll("\\s+", "") + "%"));
            }
            if (!(correo == null || correo.isEmpty())) {
                predicates.add(criteriaBuilder.like(rootAforador.get("correo"), "%" + correo.replaceAll("\\s+", "") + "%"));
            }
            if (!((fechainicio == null) && (fechafin == null))) {
                predicates.add(criteriaBuilder.between(rootAforador.get("fechaCreacion"), fechainicio, fechafin));
            }
            predicates.add(criteriaBuilder.equal(rootAforador.get("estado"), 1));
            criteriaQuery.where(predicates.toArray(new Predicate[0]));

            criteriaQuery.select(rootAforador).distinct(true);
            criteriaQuery.orderBy(criteriaBuilder.desc(rootAforador.get("fechaCreacion")));
            var result = em.createQuery(criteriaQuery).getResultList();

            return result.stream().map(clienteMapper::entityToGetDto).collect(Collectors.toList());

        }catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ClienteDTO> saveList(List<ClienteDTO> clienteDTOList) throws ServiceException {
        try {
            var clienteEntity = clienteDTOList.stream().map(clienteMapper::postDtoToEntity).collect(Collectors.toList());
            var saveResult = this.clienteRepository.saveAll(clienteEntity);
            return saveResult.stream().map(clienteMapper::entityToGetDto).collect(Collectors.toList());
        }catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ClienteDTO> findByLike(ClienteDTO clienteDTO) throws ServiceException {
        return null;
    }

    @Override
    public Optional<ClienteDTO> findById(Long id) throws ServiceException {
        try {
            var entity = this.clienteRepository.findById(id);
            return entity.map(clienteEntity -> Optional.of(clienteMapper.entityToGetDto(clienteEntity))).orElse(null);
        }catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ClienteDTO save(ClienteDTO clienteDTO) throws ServiceException {
        try {
            var clienteEntity = clienteMapper.postDtoToEntity(clienteDTO);
            var save = this.clienteRepository.save(clienteEntity);
            return clienteMapper.entityToGetDto(save);

        }catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ClienteDTO update(Long id, ClienteDTO clienteDTO) throws ServiceException {
        return null;
    }

    @Override
    public void delete(long id) throws ServiceException {
        try {
            var entity = this.clienteRepository.findById(id);
            if (entity.isPresent()){
                entity.get().setEstado(1);
                this.clienteRepository.save(entity.get());
            }
        }catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public Page<?> paginate(List<?> l, Pageable pageable, Long t) {
        return PageableExecutionUtils.getPage(l, pageable, () -> t);
    }

}
