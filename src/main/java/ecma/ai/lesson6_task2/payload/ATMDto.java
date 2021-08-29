package ecma.ai.lesson6_task2.payload;

import ecma.ai.lesson6_task2.entity.Bank;
import ecma.ai.lesson6_task2.entity.BanknoteCountUSD;
import ecma.ai.lesson6_task2.entity.BanknoteCountUZS;
import ecma.ai.lesson6_task2.entity.User;
import ecma.ai.lesson6_task2.entity.enums.CardType;
import ecma.ai.lesson6_task2.entity.enums.USDBankNoteType;
import ecma.ai.lesson6_task2.entity.enums.UZSBankNoteType;
import lombok.Data;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Map;

@Data
public class ATMDto {

    private CardType cardTypes;

    private Map<UZSBankNoteType, Integer> banknoteCountUZS; //null

    private Map<USDBankNoteType, Integer> banknoteCountUSD;

    private double maxWithdrawal;

    private double alertAmount;

    private String address;

    private Integer bankID;
    private int comission;
    private User user;

}
