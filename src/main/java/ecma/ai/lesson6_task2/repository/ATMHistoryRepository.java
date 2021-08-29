package ecma.ai.lesson6_task2.repository;

import ecma.ai.lesson6_task2.entity.ATM;
import ecma.ai.lesson6_task2.entity.ATMHistory;
import ecma.ai.lesson6_task2.entity.User;
import ecma.ai.lesson6_task2.entity.enums.ATMOperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ATMHistoryRepository extends JpaRepository<ATMHistory, Integer> {

    List<ATMHistory> findAllByAtmId(Integer atm_id);

    List<ATMHistory> findAllByAtmIdAndDateAndOperationType(Integer atm_id, Date date, ATMOperationType operationType);

    List<ATMHistory> findAllByAtmIdAndOperationType(Integer atm_id, ATMOperationType operationType);

}
