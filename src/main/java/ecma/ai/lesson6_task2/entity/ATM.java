package ecma.ai.lesson6_task2.entity;

import ecma.ai.lesson6_task2.entity.enums.CardType;
import ecma.ai.lesson6_task2.entity.enums.USDBankNoteType;
import ecma.ai.lesson6_task2.entity.enums.UZSBankNoteType;
import ecma.ai.lesson6_task2.entity.type.BanknoteCount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ATM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(value = EnumType.STRING)
    private CardType cardTypes;

    @ManyToOne
    private Bank bank;

    @ElementCollection
    private Map<UZSBankNoteType, Integer> bankNoteCountUZS;

    @ElementCollection
    private Map<USDBankNoteType, Integer> bankNoteCountUSD;

    private String emailATM; //ATMni controll qiladigan bank xodimini email

    private double balance;
    private double commission;
    private double maxWithdrawal; //Maksimum yechish mumkin bo'lgan summa
    private double alertAmount; //minimum - shunga yetib kelganda bank hodimiga message boradi
    private String address;

}

