package com.qadr.gateway.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class ClientDetails implements UserDetails {
    private Client client;

    public ClientDetails(Client client){
        this.client = client;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return client.getRoles()
                .stream()
                .map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public String getPassword() {
        return client.getClientSecret();
    }

    @Override
    public String getUsername() {
        return client.getClientName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
