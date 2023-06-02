package com.officelunch.security;

import com.officelunch.model.Role;
import com.officelunch.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserSpringDetails implements UserDetails {

    private String username;
    private String password;

    private List<SimpleGrantedAuthority> authorities;


//    private List<GrantedAuthority> authorities;

    public UserSpringDetails(User user) {
        username = user.getUsername();
        password = user.getPassword();
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        for (Role r : user.getRoles()) {
            System.out.println("----------------> "+r.getRoleName());
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(r.getRoleName());
            simpleGrantedAuthorities.add(simpleGrantedAuthority);
        }
        this.authorities = simpleGrantedAuthorities;

//        authorities= Arrays.stream(user.getRole().split(","))
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
