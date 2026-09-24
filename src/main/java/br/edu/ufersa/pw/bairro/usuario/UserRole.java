package br.edu.ufersa.pw.bairro.usuario;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String roleName;

    UserRole(String roleName){
        this.roleName = roleName;
    }

    public String getRoleName(){
        return roleName;
    }


}
