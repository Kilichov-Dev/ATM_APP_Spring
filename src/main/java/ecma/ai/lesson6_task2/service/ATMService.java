package ecma.ai.lesson6_task2.service;

import ecma.ai.lesson6_task2.entity.ATM;
import ecma.ai.lesson6_task2.entity.ATMManagerOperationsHistory;
import ecma.ai.lesson6_task2.entity.Bank;
import ecma.ai.lesson6_task2.entity.User;
import ecma.ai.lesson6_task2.entity.enums.ATMOperationType;
import ecma.ai.lesson6_task2.entity.enums.CardType;
import ecma.ai.lesson6_task2.entity.enums.USDBankNoteType;
import ecma.ai.lesson6_task2.entity.enums.UZSBankNoteType;
import ecma.ai.lesson6_task2.payload.ATMDto;
import ecma.ai.lesson6_task2.payload.ApiResponse;
import ecma.ai.lesson6_task2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ATMService {

    @Autowired
    ATMRepository atmRepository;
    @Autowired
    BankRepository bankRepository;
    @Autowired
    BankNoteCountUsdRepository bankNoteCountUsdRepository;
    @Autowired
    BankNoteCountUzsRepository bankNoteCountUzsRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ATMManagerHistoryRepository atmManagerHistoryRepository;

    public ApiResponse getAll() {
        return new ApiResponse("Success", true, atmRepository.findAll());
    }

    public ApiResponse getBankNotes(Integer id) {
        Optional<ATM> byId = atmRepository.findById(id);
        if (!byId.isPresent()) return new ApiResponse("ATM not found!", false);
        ATM atm = byId.get();
        List<Object> bankNotes = new ArrayList<>();
        bankNotes.add(atm.getBankNoteCountUSD());
        bankNotes.add(atm.getBankNoteCountUZS());
        return new ApiResponse("Banknotes", true, bankNotes);
    }

    public ApiResponse addAtm(ATMDto atmDto) {
        Optional<Bank> byId = bankRepository.findById(atmDto.getBankID());
        if (!byId.isPresent()) return new ApiResponse("Bank not found!", false);

        ATM atm = new ATM();
        atm.setBank(byId.get());
        atm.setAddress(atmDto.getAddress());
        atm.setAlertAmount(atmDto.getAlertAmount());
        atm.setMaxWithdrawal(atmDto.getMaxWithdrawal());
        atm.setCardTypes(atmDto.getCardTypes());
        atm.setCommission(atmDto.getComission());

        ATMManagerOperationsHistory operationsHistory = new ATMManagerOperationsHistory();
        operationsHistory.setUser(atmDto.getUser());
        double balance = 0;
        if (atmDto.getCardTypes() == CardType.HUMO) ;
        if (atmDto.getCardTypes() == CardType.UZCARD) ;
        {
            Map<UZSBankNoteType, Integer> banknoteCountUZS = atmDto.getBanknoteCountUZS();
            for (Map.Entry<UZSBankNoteType, Integer> UZS : banknoteCountUZS.entrySet()) {
                balance += UZS.getKey().getValue() * UZS.getValue();
            }
            atmDto.setBanknoteCountUZS(atmDto.getBanknoteCountUZS());
            operationsHistory.setBankNoteCountUZS(atmDto.getBanknoteCountUZS());
        }
        if (atmDto.getCardTypes() == CardType.VISA) {
            Map<USDBankNoteType, Integer> banknoteCountUSD = atmDto.getBanknoteCountUSD();
            for (Map.Entry<USDBankNoteType, Integer> USD : banknoteCountUSD.entrySet()) {
                balance += USD.getKey().getValue() * USD.getValue();
            }
            atm.setBankNoteCountUSD(atmDto.getBanknoteCountUSD());
            operationsHistory.setBankNoteCountUSD(atmDto.getBanknoteCountUSD());
        } else return new ApiResponse("Bunday turdagi bankNotega xizmat ko'rsatmaymiz", false);

        atm.setBalance(balance);
        operationsHistory.setOperationAmount(balance);
        Optional<User> byId1 = userRepository.findById(atmDto.getUser().getId());
        if (!byId1.isPresent()) return new ApiResponse("Siz bu Atmga xizmat ko'rsatolmaysiz!", false);
        operationsHistory.setUser(atmDto.getUser());
        operationsHistory.setDate(new Date(System.currentTimeMillis()));
        operationsHistory.setOperationType(ATMOperationType.MANAGERTOLDIRAWAL);
        ATM save = atmRepository.save(atm);
        operationsHistory.setAtm(save);
        atmManagerHistoryRepository.save(operationsHistory);
        return new ApiResponse("Success added!!!", true);
    }

    public ApiResponse editAtm(ATMDto atmDto, Integer id) {
        Optional<ATM> byId2 = atmRepository.findById(id);
        if (!byId2.isPresent()) return new ApiResponse("Atm not found!", false);

        Optional<Bank> byId = bankRepository.findById(atmDto.getBankID());
        if (!byId.isPresent()) return new ApiResponse("Bank not found!", false);

        ATM atm = byId2.get();
        atm.setBank(byId.get());
        atm.setAddress(atmDto.getAddress());
        atm.setAlertAmount(atmDto.getAlertAmount());
        atm.setMaxWithdrawal(atmDto.getMaxWithdrawal());
        atm.setCardTypes(atmDto.getCardTypes());
        atm.setCommission(atmDto.getComission());

        ATMManagerOperationsHistory operationsHistory = new ATMManagerOperationsHistory();
        operationsHistory.setUser(atmDto.getUser());
        double balance = 0;
        if (atmDto.getCardTypes() == CardType.HUMO) ;
        if (atmDto.getCardTypes() == CardType.UZCARD) ;
        {
            Map<UZSBankNoteType, Integer> banknoteCountUZS = atmDto.getBanknoteCountUZS();
            for (Map.Entry<UZSBankNoteType, Integer> UZS : banknoteCountUZS.entrySet()) {
                balance += UZS.getKey().getValue() * UZS.getValue();
            }
            atmDto.setBanknoteCountUZS(atmDto.getBanknoteCountUZS());
            operationsHistory.setBankNoteCountUZS(atmDto.getBanknoteCountUZS());
        }
        if (atmDto.getCardTypes() == CardType.VISA) {
            Map<USDBankNoteType, Integer> banknoteCountUSD = atmDto.getBanknoteCountUSD();
            for (Map.Entry<USDBankNoteType, Integer> USD : banknoteCountUSD.entrySet()) {
                balance += USD.getKey().getValue() * USD.getValue();
            }
            atm.setBankNoteCountUSD(atmDto.getBanknoteCountUSD());
            operationsHistory.setBankNoteCountUSD(atmDto.getBanknoteCountUSD());
        } else return new ApiResponse("Bunday turdagi bankNotega xizmat ko'rsatmaymiz", false);

        atm.setBalance(balance);
        operationsHistory.setOperationAmount(balance);
        Optional<User> byId1 = userRepository.findById(atmDto.getUser().getId());
        if (!byId1.isPresent()) return new ApiResponse("Siz bu Atmga xizmat ko'rsatolmaysiz!", false);
        operationsHistory.setUser(atmDto.getUser());
        operationsHistory.setDate(new Date(System.currentTimeMillis()));
        operationsHistory.setOperationType(ATMOperationType.MANAGERTOLDIRAWAL);
        ATM save = atmRepository.save(atm);
        operationsHistory.setAtm(save);
        atmManagerHistoryRepository.save(operationsHistory);
        return new ApiResponse("Success edited!!!", true);
    }

    public ApiResponse delete(Integer id) {
        Optional<ATM> byId = atmRepository.findById(id);
        if (!byId.isPresent()) return new ApiResponse("Atm not found", false);
        atmRepository.deleteById(id);
        return new ApiResponse("Success deleting!!!", true);
    }

    public ApiResponse getById(Integer id) {
        Optional<ATM> byId = atmRepository.findById(id);
        return byId.map(atm -> new ApiResponse("Success!!", true, atm)).orElseGet(() -> new ApiResponse("Atm not found!!!", false));
    }

}
