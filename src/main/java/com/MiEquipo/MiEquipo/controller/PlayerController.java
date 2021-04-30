
package com.MiEquipo.MiEquipo.controller;

import com.MiEquipo.MiEquipo.entity.EntityTeam;
import com.MiEquipo.MiEquipo.entity.EntityPlayer;
import com.MiEquipo.MiEquipo.error.ErrorService;
import com.MiEquipo.MiEquipo.requestModel.ModelPlayer;
import com.MiEquipo.MiEquipo.requestModel.ModelTeam;
import com.MiEquipo.MiEquipo.service.TeamService;
import com.MiEquipo.MiEquipo.service.PlayerService;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuario")
public class PlayerController {
    
    @Autowired
    private TeamService teamService;
    @Autowired
    private PlayerService playerService;
    
    
    
    @PostMapping("/baja-usuario")
    public String bajaPlayer(@RequestParam String id){
        if (this.playerService.deletePlayer(id)){
            return "index.html";
        }
        return "redirect:/actualizar-perfil";
    }
    
    @PostMapping("/baja-equipo")
    public String bajaTeam(@RequestParam String id){
        if (this.teamService.deleteTeam(id)){
            return "index.html";
        }
        return "redirect:/euqipo";
    }
    
    @GetMapping("/crear-equipo")
    public String showCreateTeam(ModelMap model){
        model.put("team", new ModelTeam());
        return "crear-equipo.html"; 
    }
    
    @PostMapping("/crear-nuevo-equipo")
    public String postCreateTeam(ModelMap model, ModelTeam team) {
        try {
            teamService.createTeam(team);
        } catch (ErrorService ex) {
            model.put("error", ex.getMessage());
            model.addAttribute("team", team);
            return "crear-equipo.html";
        }
        return "usuario.html";
    }
    
    @GetMapping("/actualizar-perfil")
    public String showUpdatePlayer(ModelMap model, HttpSession usuario){
        model.addAttribute(usuario);
        model.addAttribute("player", new ModelPlayer());
        return "actualizar-perfil.html"; 
    }
    
    @PostMapping("/update-user")
    public String postUpdatePlayer(ModelMap model, ModelPlayer player,HttpSession usuario){
        try {
            EntityPlayer updatePlayer = playerService.updatePlayer(player);
            usuario.setAttribute("usuariosession", updatePlayer);
        } catch (Exception ex) {
            model.put("error", ex.getMessage()); 
        }
        return "usuario.html";
    }
    
    @GetMapping("/modificar-equipo/{id}")
    public String showUpdateTeam(ModelMap model, @PathVariable String id){
        model.put("id", id);
        model.addAttribute("team",new ModelTeam());
        return "modificar-equipo.html"; 
    }
    
    @PostMapping("/actualizar-equipo/{id}")
    public String postUpdateTeam(ModelMap model,@ModelAttribute("team") ModelTeam team, @PathVariable String id){
        try {
            this.teamService.updateTeam(team, id);
            return "redirect:/usuario";
        } catch (Exception ex) {
            model.put("error", ex.getMessage()); 
            return "modificar-equipo.html";
        }
    }
    
    
    @GetMapping("/equipo")
    public String showTeams(ModelMap model, @RequestParam (required = false)String id){
        Iterable<EntityTeam> teams = teamService.getTeamsByPlayerId(id);
        if (teams != null){
            model.put("teams", teams);
            return "equipo.html";
        }else{
            model.put("error", "Aun no tiene un equipo, elija unase en uno buscar equipo");
            return "usuario.html";
        }
    }

    @PostMapping("/agregar-jugador")
    public String postAddPlayer(ModelMap model, @RequestParam("idPlayer") String idPlayer,@RequestParam("idTeam") String idTeam){
       model.put("teams", this.teamService.addPlayerToTeam(idPlayer, idTeam));
       return "equipo.html";
    }
    
    @PostMapping("/agregar-equipo")
    public String postAddTeam(ModelMap model, @RequestParam("idPlayer") String idPlayer,@RequestParam("idTeam") String idTeam){
        model.addAttribute("teams", this.playerService.addTeam(idPlayer, idTeam));
        return "equipo.html";
    }
    
    

}

