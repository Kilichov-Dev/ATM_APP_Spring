package ecma.ai.lesson6_task2.entity;

import ecma.ai.lesson6_task2.entity.enums.ATMOperationType;
import ecma.ai.lesson6_task2.entity.enums.USDBankNoteType;
import ecma.ai.lesson6_task2.entity.enums.UZSBankNoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ATMHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Date date;

    @ManyToOne
    private User user;

    @ManyToOne
    private Card card;

    @ManyToOne
    private ATM atm;

    @Column
    private double operationAmount;

    @ElementCollection
    private Map<UZSBankNoteType, Integer> bankNoteCountUZS;

    @ElementCollection
    private Map<USDBankNoteType, Integer> bankNoteCountUSD;

    @Enumerated(value = EnumType.STRING)
    private ATMOperationType operationType;

}
