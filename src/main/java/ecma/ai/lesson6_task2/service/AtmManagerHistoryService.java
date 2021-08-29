package ecma.ai.lesson6_task2.service;

import ecma.ai.lesson6_task2.entity.ATM;
import ecma.ai.lesson6_task2.entity.ATMHistory;
import ecma.ai.lesson6_task2.entity.ATMManagerOperationsHistory;
import ecma.ai.lesson6_task2.entity.enums.ATMOperationType;
import ecma.ai.lesson6_task2.payload.ApiResponse;
import ecma.ai.lesson6_task2.repository.ATMManagerHistoryRepository;
import ecma.ai.lesson6_task2.repository.ATMRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtmManagerHistoryService {
    @Autowired
    ATMManagerHistoryRepository atmManagerHistoryRepository;
    @Autowired
    ATMRepository atmRepository;

    public ApiResponse getManagerPullMoney(Integer id) {
        Optional<ATM> optionalATM = atmRepository.findById(id);
        if (!optionalATM.isPresent()) {
            return new ApiResponse("ATM not found!", false);
        }
        ATM atm = optionalATM.get();
        List<ATMManagerOperationsHistory> allByAtmId = atmManagerHistoryRepository.findAllByAtmId(atm.getId());

        return new ApiResponse("ATM ni pul bilan manager to'ldirganligi haqi list!", true, allByAtmId);
    }
}
