package ecma.ai.lesson6_task2.entity;

import ecma.ai.lesson6_task2.entity.enums.USDBankNoteType;
import ecma.ai.lesson6_task2.entity.enums.UZSBankNoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BanknoteCountUSD {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(value = EnumType.STRING)
    private USDBankNoteType usdBankNoteType;

    private Integer count;

}
