package com.MiEquipo.MiEquipo.controller;

import com.MiEquipo.MiEquipo.entity.EntityTeam;
import com.MiEquipo.MiEquipo.error.ErrorService;
import com.MiEquipo.MiEquipo.service.PlayerService;
import com.MiEquipo.MiEquipo.entity.EntityPhoto;
import com.MiEquipo.MiEquipo.requestModel.ModelPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.MiEquipo.MiEquipo.repository.TeamRepository;
import com.MiEquipo.MiEquipo.repository.PhotoRepository;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private PlayerService playerService;
    
    @Autowired
    private PhotoRepository photoRepository;
   
    @Autowired
    private TeamRepository teamRepository;
    
    

    @PreAuthorize("hasAnyRole('ROLE_PLAYER')")
    @GetMapping("/usuario")
    public String showPlayer() {
        return "usuario.html";
    }
    

    @GetMapping("/registro")
    public String showRegister(ModelMap model) {
        model.addAttribute("player", new ModelPlayer());
        return "registro.html";
    }

    @PostMapping("/registrar")
    public String registerPlayer(ModelMap model, @ModelAttribute ModelPlayer player ) {
        try {
            playerService.registerUser(player);
        } catch (ErrorService ex) {
            model.put("error", ex.getMessage());
            model.addAttribute("player", player);
            return "registro.html";
        }
        return "registro-exitoso.html";
    }
    
    
    @GetMapping("/")
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap model) {
        if (error != null) {
            model.put("error", "Usuario o clave incorrectos");
        }
        if (logout != null) {
            model.put("logout", "Ha salido correctamente");
        }
        return "index.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_PLAYER')")
    @GetMapping("/buscador")
    public String showAllTeams(ModelMap model){
        Iterable<EntityTeam> allTeams = teamRepository.findAll();
        model.addAttribute("teams", allTeams);
        return "buscador.html";
    }
    
    @GetMapping("/cargar/{id}")
    public ResponseEntity<byte[]> showPicture(@PathVariable String id) {
        EntityPhoto foto = photoRepository.findById(id).get();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(foto.getContent(), headers, HttpStatus.OK);
    }
    
    
}
