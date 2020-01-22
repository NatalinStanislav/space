package com.space.service;

import com.space.model.Ship;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ShipService {
    @Autowired
    ShipRepository repo;

    public void save(Ship ship) {
        repo.save(ship);

    }

    public List<Ship> listAll(Pageable pageable) {
        return (List<Ship>) repo.findAll(pageable).getContent();
    }

    public Optional<Ship> get(Long id) {
        return repo.findById(id);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Long count() {
        return repo.count();
    }

    public List<Ship> findByName(String name, Pageable page) {
        return repo.findByNameContaining(name, page);
    }

    public Page<Ship> findAll(Specification<Ship> specification, Pageable pageable){
        return repo.findAll(specification, pageable);
    }

    public Long countWithSpec(Specification<Ship> spec){
        return repo.count(spec);
    }

    public Optional<Ship> findByID(Long id){
        return repo.findById(id);
    }


}
