package org.esfe.BeautyTimeApp.repositorios;

import org.esfe.BeautyTimeApp.modelos.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriaRepository extends JpaRepository<Categoria, Integer>{
}
