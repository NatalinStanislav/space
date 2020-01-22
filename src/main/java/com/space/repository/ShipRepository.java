package com.space.repository;

import com.space.model.Ship;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ShipRepository extends PagingAndSortingRepository<Ship, Long>, JpaSpecificationExecutor<Ship>  {
    List<Ship> findByName(String name, Pageable page);

    List<Ship> findByNameContaining(String name, Pageable page);
}
