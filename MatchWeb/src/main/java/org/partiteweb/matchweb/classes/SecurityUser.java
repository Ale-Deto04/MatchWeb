package org.partiteweb.matchweb.classes;

import org.partiteweb.matchweb.classes.myUsers.SignupUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

public class SecurityUser implements UserDetails {

    private final SignupUser signupUser;

    public SecurityUser(SignupUser signupUser) {this.signupUser = signupUser;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(signupUser.getRole()));
    }

    @Override
    public String getPassword() {
            return signupUser.getPassword();}


    @Override
    public String getUsername() {
        return signupUser.getUsername();
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

