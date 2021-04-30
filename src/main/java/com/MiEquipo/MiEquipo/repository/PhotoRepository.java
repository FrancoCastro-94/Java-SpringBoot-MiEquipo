package com.MiEquipo.MiEquipo.repository;


import com.MiEquipo.MiEquipo.entity.EntityPhoto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends CrudRepository<EntityPhoto, String>{
    
}
