package org.esfe.BeautyTimeApp.repositorios;

import org.esfe.BeautyTimeApp.modelos.Cupo;
import org.esfe.BeautyTimeApp.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICupoRepository extends JpaRepository<Cupo, Integer> {

}
