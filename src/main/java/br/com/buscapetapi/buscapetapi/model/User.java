package br.com.buscapetapi.buscapetapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Size(max = 50)
@NotNull
@Column(name = "name", length = 50)
private String name;

@Size(max = 150)
@NotNull
@Column(name = "email", length = 150)
private String email;

@Size(max = 15)
@Column(name = "phone", length = 15)
private String phone;

@Size(max = 150)
@NotNull
@Column(name = "password", length = 150)
private String password;

@Size(max = 150)
@Column(name = "reset_token", length = 150)
private String resetToken;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id")
//    private int address;

@Column(name = "address")
private Integer address;

@Column(name = "created_at")
private LocalDate createdAt;


@Column(name = "updated_at")
private LocalDate updatedAt;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}