package ecma.ai.lesson6_task2.payload;

import ecma.ai.lesson6_task2.entity.ATM;
import ecma.ai.lesson6_task2.entity.Card;
import lombok.Data;

@Data
public class WithDrawDto {

    private ATM atm;
    private Card card;
    private Double outcomeMoney;
    private Double incomeMoney;

}
