package ecma.ai.lesson6_task2.entity.type;

import ecma.ai.lesson6_task2.entity.enums.USDBankNoteType;
import ecma.ai.lesson6_task2.entity.enums.UZSBankNoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BanknoteCount {
    private USDBankNoteType usdBankNoteType;
    private UZSBankNoteType uzsBankNoteType;
    Integer count;
}
