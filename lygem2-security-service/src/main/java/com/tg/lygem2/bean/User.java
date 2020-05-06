package com.tg.lygem2.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.tg.lygem2.vo.BasicUser;
import com.tg.lygem2.vo.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-09-27
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BasicUser implements UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JSONField(serialize=false)
    private String password;

    @JSONField(serialize=false)
    private List<Role> roles;


    /**
     * 当前用户的所有角色拿到
     */
    @Override
    @JSONField(serialize=false)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
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
        return enabled;
    }


}