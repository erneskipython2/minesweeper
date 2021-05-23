package com.deviget.minesweeper.domain;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model class for the user model authentication service
 *
 */
@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode
@Document(collection = "accounts")
public class UserDao implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5121876050097325276L;

	@Id
	private String id;
	
	@NotEmpty
    private String username;
    @NotEmpty
    private String password;
    private List<String> roles;
    
	public UserDao(String id, @NotEmpty String username, @NotEmpty String password, List<String> roles) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}


	
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(toList());
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


	@Override
	public String toString() {
		return "UserDao [_id=" + id + ", username=" + username + ", roles=" + roles + "]";
	}	

}
