package com.example.demo.servicios;

import com.example.demo.dto.CategoriaDTO;
import com.example.demo.entidades.Categoria;
import com.example.demo.entidades.Tematica;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.repositorios.CategoriaRepositorio;
import com.example.demo.utilidades.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoriaServicio {

    private final CategoriaRepositorio categoriaRepositorio;
    private final TematicaServicio tematicaServicio;

    public void crearCategoria(String nombre) {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setNombre(nombre);
        categoriaRepositorio.save(Mapper.categoriaDTOAEntidad(categoriaDTO));
    }

    @Transactional
    public void modificarCategoria(Integer id, String nombre) throws ObjetoNulloExcepcion {
        CategoriaDTO categoriaDTO = obtenerPorId(id) ;
        categoriaDTO.setNombre(nombre);
        categoriaRepositorio.save(Mapper.categoriaDTOAEntidad(categoriaDTO));
    }

    @Transactional
    public List<CategoriaDTO> mostrarCategorias() {
        return Mapper.listaCategoriaEntidadADTO(categoriaRepositorio.findAll());
    }

    @Transactional(readOnly = true)
    public List<CategoriaDTO> mostrarCategoriasPorAlta(Boolean alta) {
        return Mapper.listaCategoriaEntidadADTO(categoriaRepositorio.mostrarPorAlta(alta));
    }

    @Transactional
    public CategoriaDTO obtenerPorId(int id) throws ObjetoNulloExcepcion {
        Categoria categoria = categoriaRepositorio.findById(id).orElse(null);
        if (categoria == null) {
            throw new ObjetoNulloExcepcion("");
        }
        return Mapper.categoriaEntidadADTO(categoria);
    }

    @Transactional
    public void eliminar(int id) throws ObjetoNulloExcepcion {

        CategoriaDTO c = this.obtenerPorId(id);

        for (Tematica tematica: c.getTematicas()) {
            if(tematica.getAlta()){
                tematicaServicio.eliminar(tematica.getId());
            }
        }

        categoriaRepositorio.deleteById(id);


    }

    @Transactional
    public void darAlta(int id) throws ObjetoNulloExcepcion {
        categoriaRepositorio.darAlta(id);
    }
}
