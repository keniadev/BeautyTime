package org.esfe.BeautyTimeApp.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // 1. Defina la contraseña que desea para el administrador
        String rawPassword = "kenia515";

        // 2. Generar el hash
        String hashedPassword = encoder.encode(rawPassword);

        System.out.println("Copia este hash para la columna 'contrasena' de la BD:");
        System.out.println(hashedPassword);
    }
}