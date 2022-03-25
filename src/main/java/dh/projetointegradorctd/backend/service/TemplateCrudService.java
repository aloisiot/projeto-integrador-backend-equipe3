package dh.projetointegradorctd.backend.service;

import dh.projetointegradorctd.backend.exception.global.UnprocessableEntityException;
import dh.projetointegradorctd.backend.exception.global.EmpityRepositoryException;
import dh.projetointegradorctd.backend.exception.global.ResorceNotFoundException;
import dh.projetointegradorctd.backend.model.dataStorage.DataBaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class TemplateCrudService<T extends  DataBaseEntity> {

    protected JpaRepository<T, Long> repository;

    public TemplateCrudService(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    public T save(T entity) throws UnprocessableEntityException {
        if(entity.getId() != null && repository.existsById(entity.getId())){
            throw new UnprocessableEntityException("o parametro id nao deve ser especificado");
        }
        return repository.save(entity);
    }

    public T update(T entity) throws ResorceNotFoundException {
        if(entity.getId() != null && repository.existsById(entity.getId())){
            return repository.save(entity);
        }
        throw new ResorceNotFoundException();
    }

    public T findById(Long id) throws ResorceNotFoundException {
        Optional<T> categoria = repository.findById(id);
        if(categoria.isEmpty()){
            throw new ResorceNotFoundException();
        }
        return categoria.get();
    }

    public List<T> findAll() throws EmpityRepositoryException {
        if(repository.count() == 0) {
            throw new EmpityRepositoryException();
        }
        return repository.findAll();
    }

    public void deleteById(Long id) throws ResorceNotFoundException {
        if(! repository.existsById(id)){
            throw new ResorceNotFoundException();
        }
        repository.deleteById(id);
    }
}
