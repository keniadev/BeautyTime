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
        if (usuario.getId() == null) {
            // 1. CREACIÓN: Encriptar siempre la nueva contraseña
            usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        } else {
            // 2. EDICIÓN
            Usuario existente = iUsuarioRepository.findById(usuario.getId()).orElseThrow(
                    () -> new RuntimeException("Usuario no encontrado para edición.")
            );

            // Solo encriptar si la nueva contraseña no es nula/vacía
            if (usuario.getContrasena() != null && !usuario.getContrasena().isEmpty()) {
                usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
            } else {
                // Si la nueva contraseña está vacía, mantener la existente hasheada
                usuario.setContrasena(existente.getContrasena());
            }
        }
        return iUsuarioRepository.save(usuario);
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