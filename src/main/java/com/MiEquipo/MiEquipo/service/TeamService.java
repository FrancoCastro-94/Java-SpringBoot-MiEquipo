package com.MiEquipo.MiEquipo.service;
 
import com.MiEquipo.MiEquipo.entity.EntityPhoto;
import com.MiEquipo.MiEquipo.entity.EntityTeam;
import com.MiEquipo.MiEquipo.entity.EntityPlayer;
import com.MiEquipo.MiEquipo.error.ErrorService;
import com.MiEquipo.MiEquipo.requestModel.ModelTeam;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.MiEquipo.MiEquipo.repository.TeamRepository;
import com.MiEquipo.MiEquipo.repository.PlayerRepository;
import java.util.Date;


@Service
public class TeamService {
    
    @Autowired
    private TeamRepository teamRepository;
    
    @Autowired
    private PhotoService photoService;
    
    @Autowired
    private PlayerRepository playerRepository;
    
    @Transactional
    public void createTeam(ModelTeam team) throws ErrorService {
        try {
            validate(team.getName());
            EntityTeam newTeam = new EntityTeam();
            newTeam.setAlta(new Date());
            newTeam.setCity(team.getCity());
            newTeam.setName(team.getName());
            newTeam.setSizeTeam(team.getSizeTeam());
            newTeam.setGender(team.getGender());
            newTeam.setProvincia(team.getProvincia());

            if(!team.getImgFile().isEmpty()){
                EntityPhoto photo = this.photoService.savePhoto(team.getImgFile());
                newTeam.setPhoto(photo);
            }
            this.teamRepository.save(newTeam);
        } catch (Exception e) {
            throw new ErrorService("Por Favor intenta de nuevo" + e.toString());
        }
    }
    
    @Transactional
    public void updateTeam(ModelTeam team,String id) throws ErrorService {
        validate(team.getName());
        EntityTeam newTeam = this.teamRepository.findById(id).get();
        newTeam.setCity(team.getCity());
        newTeam.setName(team.getName());
        newTeam.setSizeTeam(team.getSizeTeam());
        newTeam.setGender(team.getGender());
        newTeam.setProvincia(team.getProvincia());
        try {
            this.teamRepository.save(newTeam);
        } catch (Exception e) {
            throw new ErrorService("Por Favor intenta de nuevo");
        }
    }
 
    @Transactional
    public void addTeam(EntityTeam team, EntityPlayer player){
        player.getTeams().add(team);
        this.playerRepository.save(player);
    }
    
    @Transactional
    public Iterable<EntityTeam> getTeamsByPlayerId(String id){
        try{
            EntityPlayer player = this.playerRepository.findById(id).get();
            return player.getTeams();
        } catch(Exception e){
            return null;
        }
    }
    
    @Transactional
    public EntityTeam addPlayerToTeam(String idPlayer, String idTeam){
        EntityPlayer player = playerRepository.findById(idPlayer).get();
        EntityTeam team = teamRepository.findById(idTeam).get();
        team.getPlayers().add(player);
        this.teamRepository.save(team);
        return team;
    }
    
    @Transactional
    public boolean deleteTeam(String id){
        try {
            EntityTeam team = this.teamRepository.findById(id).get();
            team.setBaja(new Date());
            this.teamRepository.save(team);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private void validate(String name) throws ErrorService {
        EntityTeam respuesta = teamRepository.findByName(name);
        if (respuesta != null){
            throw new ErrorService("Ya exixte un equipo con ese nombre");
        }
    }
    
}
