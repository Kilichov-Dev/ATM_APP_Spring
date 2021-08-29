package ecma.ai.lesson6_task2.entity;

import ecma.ai.lesson6_task2.entity.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Collection;
import java.util.Collections;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Card implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @Size(max = 16, min = 16)
    private String number;

    @Column
    @Size(max = 3, min = 3)
    private String cvv;

    @Column
//    @Size(max = 4, min = 4)
    private String pinCode;

    @ManyToOne
    private Bank bank;

    @ManyToOne
    private User user;

    private String fullName;

    private Date expireDate;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;

    private boolean active = false; // bank xodimi cardni active qiladi
    private boolean blocked = false;
    private double balance;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(user.getRole());
    }

    @Override
    public String getPassword() {
        return this.pinCode;
    }

    @Override
    public String getUsername() {
        return this.number;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    public Card(Integer id, String number, String cvv, Bank bank, String fullName, String pinCode, Date expireDate, CardType cardType, boolean active, boolean blocked, double balance) {
        this.id = id;
        this.number = number;
        this.cvv = cvv;
        this.bank = bank;
        this.fullName = fullName;
        this.pinCode = pinCode;
        this.expireDate = expireDate;
        this.cardType = cardType;
        this.active = active;
        this.blocked = blocked;
        this.balance = balance;
    }
}
