
package com.MiEquipo.MiEquipo.repository;

import com.MiEquipo.MiEquipo.entity.EntityTeam;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TeamRepository extends CrudRepository<EntityTeam,String>{
 
//    @Query ("SELECT c FROM Equipo c  WHERE c.nombre = :nombre")
//    EntityTeam buscarEquipoPorNombre(@Param("nombre") String nombre);
//    
//    @Query ("SELECT c FROM Equipo c WHERE c.id= :id")
//    EntityTeam buscarEquipoPorId(@Param("id") String id);
//    
    EntityTeam findByName(String name);
}