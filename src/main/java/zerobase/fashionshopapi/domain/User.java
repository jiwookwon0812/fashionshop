package zerobase.fashionshopapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import zerobase.fashionshopapi.domain.constant.Authority;

import java.util.Collection;
import java.util.List;

@Entity(name = "User")
@Getter
@ToString
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email; // 회원 이메일 (고유값)

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String phonenumber;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Authority authority;

    public User(String email, String username, String phonenumber, String password, Authority authority) {
        this.email = email;
        this.username = username;
        this.phonenumber = phonenumber;
        this.password = password;
        this.authority = authority;
    }


    @Override // 권한 반환 : 사용자가 가진 권한
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(authority.name()));
    }

    @Override // 사용자의 고유한 값 반환 -> 여기선 email 이 고유값이므로 email 반환
    public String getUsername() {
        return email;
    }

    @Override // 사용자의 비밀번호 반환 -> 암호화 되어야 함
    public String getPassword() {
        return password;
    }

    @Override // 계정 만료 여부 반환 : 만료시 false
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override // 계정 잠금 여부 반환 : 잠금시 false
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override // 패스워드 만료 여부 반환 : 만료시 false
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override // 계정 사용 가능 여부 반환 : 사용불가능시 false
    public boolean isEnabled() {
        return true;
    }
}
