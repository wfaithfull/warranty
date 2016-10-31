package me.faithfull.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

/**
 * @author Will Faithfull
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Principal {

    @NotEmpty
    @Column(unique = true)
    @Pattern(regexp = "^[a-z0-9_-]{3,15}$")
    @Id
    String username;

    @NotEmpty
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    boolean isAccountNonLocked          = true;
    boolean isAccountNonExpired         = true;
    boolean isCredentialsNonExpired     = true;
    boolean isEnabled                   = true;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    Set<String> authorities;

}
