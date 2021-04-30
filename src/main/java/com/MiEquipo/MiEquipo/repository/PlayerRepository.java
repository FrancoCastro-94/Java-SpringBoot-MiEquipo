
package com.MiEquipo.MiEquipo.repository;

import com.MiEquipo.MiEquipo.entity.EntityPlayer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface PlayerRepository extends CrudRepository<EntityPlayer,String> {
    
    @Query ("SELECT c FROM EntityPlayer c WHERE c.email= :email AND c.baja IS NULL")
    public EntityPlayer playerByEmail(@Param("email") String email);
    
//    @Query ("SELECT c FROM Jugador c WHERE c.id= :id AND c.baja IS NULL")
//    public EntityPlayer playerById(@Param("id") String id);
    
//    EntityPlayer findByEmail(String email);
    
    Boolean existsByEmail(String email);
            
}
