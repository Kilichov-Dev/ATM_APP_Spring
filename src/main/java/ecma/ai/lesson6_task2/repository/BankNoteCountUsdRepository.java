package ecma.ai.lesson6_task2.repository;

import ecma.ai.lesson6_task2.entity.BanknoteCountUSD;
import ecma.ai.lesson6_task2.entity.User;
import ecma.ai.lesson6_task2.entity.enums.USDBankNoteType;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankNoteCountUsdRepository extends JpaRepository<BanknoteCountUSD, Integer> {
    Optional<BanknoteCountUSD> findByUsdBankNoteType(String usdBankNoteType);

}
