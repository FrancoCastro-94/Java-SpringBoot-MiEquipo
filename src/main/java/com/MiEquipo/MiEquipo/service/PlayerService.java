package com.MiEquipo.MiEquipo.service;

import com.MiEquipo.MiEquipo.entity.EntityPhoto;
import com.MiEquipo.MiEquipo.error.ErrorService;
import com.MiEquipo.MiEquipo.entity.EntityPlayer;
import com.MiEquipo.MiEquipo.entity.EntityTeam;
import com.MiEquipo.MiEquipo.requestModel.ModelPlayer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.MiEquipo.MiEquipo.repository.PlayerRepository;
import com.MiEquipo.MiEquipo.repository.TeamRepository;


@Service
public class PlayerService implements UserDetailsService{
    
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PhotoService photoService;

    
    //Services
    
    @Transactional
    public void registerUser(ModelPlayer player) throws ErrorService {
        validate(player);
        
        EntityPlayer newPlayer = new EntityPlayer();
        
        newPlayer.setName(player.getName());
        newPlayer.setEmail(player.getEmail());
        newPlayer.setNumber(player.getNumber());
        String encrypted = new BCryptPasswordEncoder().encode(player.getPasswordOne());
        newPlayer.setPassword(encrypted);
        
        newPlayer.setAlta(new Date());
        
        if(!player.getImgFile().isEmpty()){
            EntityPhoto photo = photoService.savePhoto(player.getImgFile());
            newPlayer.setPhoto(photo);
        }
//        String subject = "Inscripcion de tu equipo";
//
//        String content = "Gracias por registrarse " + nombre + "!";        
//        sendEmail(email, subject, content);


        playerRepository.save(newPlayer);
    }
    
    @Transactional
    public EntityPlayer getById(String id){
        return playerRepository.findById(id).get();
    }
    
    @Transactional
    public Iterable<EntityTeam> addTeam(String idPlayer, String idTeam){
        EntityTeam team = this.teamRepository.findById(idTeam).get();
        if(team != null){
            EntityPlayer player = this.playerRepository.findById(idPlayer).get();
            player.getTeams().add(team);
            team.getPlayers().add(player);
            this.teamRepository.save(team);
            this.playerRepository.save(player);
            return player.getTeams();
        }
        return null;
    }
    
    
    private void validate(ModelPlayer player) throws ErrorService { 
        
        if (this.playerRepository.existsByEmail(player.getEmail())){
            throw new ErrorService("Ya exixte una cuenta con este correo");
        }
        if(player.getNumber().length() >= 3){
            throw new ErrorService("El numero de camiseta debe ser menor a 100");
        }
        if (player.getPasswordOne().length() < 6) {
            throw new ErrorService("La clave debe tener 6 caracteres o mas");
        }
        if (!player.getPasswordOne().equals(player.getPasswordTwo())) {
            throw new ErrorService("Las claves deben coincidir");
        }
        
    }
    
    
    @Transactional
    public EntityPlayer updatePlayer(ModelPlayer player)throws ErrorService{
        try {
            Optional<EntityPlayer> respuesta = playerRepository.findById(player.getId());
            EntityPlayer newPlayer = respuesta.get();
            newPlayer.setName(player.getName());
            newPlayer.setNumber(player.getNumber());
            if(!player.getImgFile().isEmpty()){
                EntityPhoto foto = photoService.savePhoto(player.getImgFile());
                newPlayer.setPhoto(foto);
            }      
            return this.playerRepository.save(newPlayer);
        } catch (Exception e) {
            throw new ErrorService("Por Favor intenta de nuevo");
        }
    }
    
    @Transactional
    public boolean deletePlayer(String id){
        try{
            EntityPlayer player = this.playerRepository.findById(id).get();
            player.setBaja(new Date());
            this.playerRepository.save(player);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        EntityPlayer player = playerRepository.playerByEmail(email);
        if (player != null) {

            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_PLAYER");
            permisos.add(p1);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", player);

            User user = new User(player.getEmail(), player.getPassword(), permisos);
            return user;
            
        } else {
            return null;
        }
    }
}
