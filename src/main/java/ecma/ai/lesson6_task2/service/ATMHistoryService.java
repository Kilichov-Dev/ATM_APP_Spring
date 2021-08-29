package ecma.ai.lesson6_task2.service;

import ecma.ai.lesson6_task2.entity.*;
import ecma.ai.lesson6_task2.entity.enums.ATMOperationType;
import ecma.ai.lesson6_task2.entity.enums.RoleName;
import ecma.ai.lesson6_task2.payload.ATMDto;
import ecma.ai.lesson6_task2.payload.ApiResponse;
import ecma.ai.lesson6_task2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ATMHistoryService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ATMRepository atmRepository;
    @Autowired
    ATMHistoryRepository atmHistoryRepository;
    @Autowired
    BankRepository bankRepository;
    @Autowired
    BankNoteCountUsdRepository bankNoteCountUsdRepository;
    @Autowired
    BankNoteCountUzsRepository bankNoteCountUzsRepository;

    public ApiResponse getATMAllHistory(Integer id) {
        Optional<ATM> optionalATM = atmRepository.findById(id);
        if (!optionalATM.isPresent()) {
            return new ApiResponse("ATM not found!", false);
        }
        ATM atm = optionalATM.get();
        List<ATMHistory> allByAtmId = atmHistoryRepository.findAllByAtmId(atm.getId());
        return new ApiResponse("ATM ning kirim va chiqimi", true, allByAtmId);
    }

    public ApiResponse getByDayOutcome(Integer id) {
        Optional<ATM> optionalATM = atmRepository.findById(id);
        if (!optionalATM.isPresent()) {
            return new ApiResponse("ATM not found!", false);
        }
        Date date = new Date();
        ATM atm = optionalATM.get();
        List<ATMHistory> withDraw = atmHistoryRepository.findAllByAtmIdAndDateAndOperationType(atm.getId(), date, ATMOperationType.WITHDRAWAL);
        return new ApiResponse("ATM ning kunlik chiqim", true, withDraw);
    }

    public ApiResponse getByDayIncome(Integer id) {
        Optional<ATM> optionalATM = atmRepository.findById(id);
        if (!optionalATM.isPresent()) {
            return new ApiResponse("ATM not found!", false);
        }
        ATM atm = optionalATM.get();
        Date date = new Date();
        List<ATMHistory> kirim = atmHistoryRepository.findAllByAtmIdAndDateAndOperationType(atm.getId(), date, ATMOperationType.PULKIRITAWAL);
        return new ApiResponse("ATM ning kunlik kirimi", true, kirim);
    }




}
