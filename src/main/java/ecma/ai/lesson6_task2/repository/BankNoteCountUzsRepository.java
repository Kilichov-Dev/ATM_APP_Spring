package ecma.ai.lesson6_task2.repository;

import ecma.ai.lesson6_task2.entity.BanknoteCountUSD;
import ecma.ai.lesson6_task2.entity.BanknoteCountUZS;
import ecma.ai.lesson6_task2.entity.enums.UZSBankNoteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankNoteCountUzsRepository extends JpaRepository<BanknoteCountUZS, Integer> {
    Optional<BanknoteCountUZS> findByUzsBankNoteType(String uzsBankNoteType);
}
