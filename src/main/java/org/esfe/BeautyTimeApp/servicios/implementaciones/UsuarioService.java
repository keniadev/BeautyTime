package org.esfe.BeautyTimeApp.servicios.implementaciones;

import org.esfe.BeautyTimeApp.modelos.Usuario;
import org.esfe.BeautyTimeApp.repositorios.IUsuarioRepository;
import org.esfe.BeautyTimeApp.servicios.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUsuarioRepository iUsuarioRepository;

    @Override
    public Page<Usuario> buscarTodosPaginados(Pageable pageable) {
        return iUsuarioRepository.findAll(pageable);
    }

    @Override
    public List<Usuario> ObtenerTodos() {
        return iUsuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> BuscarPorId(Integer id) {
        return iUsuarioRepository.findById(id);
    }

    @Override
    public Usuario crearOEditar(Usuario usuario) {
        logger.info("Guardando usuario con correo: {}", usuario.getCorreo());
        logger.info("Contraseña antes de encriptar: {}", usuario.getContrasena());
        if (usuario.getId() == null) {
            usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
            logger.info("Contraseña encriptada: {}", usuario.getContrasena());
        } else {
            Usuario existente = iUsuarioRepository.findById(usuario.getId()).orElse(null);
            if (existente != null && !usuario.getContrasena().equals(existente.getContrasena())) {
                usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
                logger.info("Contraseña actualizada y encriptada: {}", usuario.getContrasena());
            }
        }
        Usuario savedUsuario = iUsuarioRepository.save(usuario);
        logger.info("Usuario guardado: correo={}, rol_id={}", savedUsuario.getCorreo(), savedUsuario.getRol().getId());
        return savedUsuario;
    }

    @Override
    public void eliminarPorId(Integer id) {
        iUsuarioRepository.deleteById(id);
    }

    @Override
    public Optional<Usuario> BuscarPorCorreo(String correo) {
        return iUsuarioRepository.findByCorreo(correo);
    }

}