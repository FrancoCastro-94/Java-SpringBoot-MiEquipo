package com.MiEquipo.MiEquipo.service;

import com.MiEquipo.MiEquipo.error.ErrorService;
import com.MiEquipo.MiEquipo.entity.EntityPhoto;
import java.io.IOException;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.MiEquipo.MiEquipo.repository.PhotoRepository;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository fotoRepositorio;

    @Transactional
    public EntityPhoto savePhoto(MultipartFile imgFile) throws ErrorService {
        if (imgFile != null) {
            try {
                EntityPhoto foto = new EntityPhoto();
                foto.setMime(imgFile.getContentType());
                foto.setName(imgFile.getName());
                foto.setContent(imgFile.getBytes());

                return fotoRepositorio.save(foto);
            } catch (Exception e) {
                System.err.println(e.getMessage());

            }

        }
        return null;
    }

    @Transactional
    public EntityPhoto actualizar(String idFoto, MultipartFile archivo) throws ErrorService {
        try {
            EntityPhoto foto = new EntityPhoto();
            if(idFoto != null){
                Optional<EntityPhoto> respuesta = fotoRepositorio.findById(idFoto);
                if(respuesta.isPresent()){
                    foto = respuesta.get();
                }
            }
            foto.setMime(archivo.getContentType());
            foto.setName(archivo.getName());
            foto.setContent(archivo.getBytes());

            return fotoRepositorio.save(foto);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return null;
        }
        
    }
}

