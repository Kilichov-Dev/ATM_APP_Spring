package ecma.ai.lesson6_task2.service;

import ecma.ai.lesson6_task2.component.MailSender;
import ecma.ai.lesson6_task2.entity.ATM;
import ecma.ai.lesson6_task2.entity.ATMHistory;
import ecma.ai.lesson6_task2.entity.Card;
import ecma.ai.lesson6_task2.entity.enums.ATMOperationType;
import ecma.ai.lesson6_task2.entity.enums.CardType;
import ecma.ai.lesson6_task2.entity.enums.USDBankNoteType;
import ecma.ai.lesson6_task2.entity.enums.UZSBankNoteType;
import ecma.ai.lesson6_task2.payload.ApiResponse;
import ecma.ai.lesson6_task2.payload.AtmWithDrawDto;
import ecma.ai.lesson6_task2.payload.PulQoyishDto;
import ecma.ai.lesson6_task2.repository.ATMHistoryRepository;
import ecma.ai.lesson6_task2.repository.ATMRepository;
import ecma.ai.lesson6_task2.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class MainService {

    @Autowired
    CardRepository cardRepository;
    @Autowired
    ATMHistoryRepository atmHistoryRepository;
    @Autowired
    MailSender mailSender;
    @Autowired
    ATMRepository atmRepository;

    public ApiResponse withDraw(AtmWithDrawDto drawal) throws MessagingException {
        ATM atm = drawal.getAtm();
        Card card = drawal.getCard();
        ATMHistory atmHistory = new ATMHistory();

        if (card.isActive() && card.isBlocked())
            return new ApiResponse("Card muzlatilgan", false);

        if (!atm.getCardTypes().equals(card.getCardType()))
            return new ApiResponse("Card type mos emas!", false);

        if (atm.getMaxWithdrawal() < drawal.getAmountBalance())
            return new ApiResponse("Ko'p pul yechish mimkin emas!", false);
        else if (card.getBalance() < drawal.getAmountBalance())
            return new ApiResponse("Hisongiznda mablag' yetarli emas!", false);
        else if (atm.getBalance() < drawal.getAmountBalance())
            return new ApiResponse("Atmning hisobida mablag' yetarli emas!", false);

        atmHistory.setCard(card);
        atmHistory.setAtm(atm);
        atmHistory.setDate(new Date(System.currentTimeMillis()));
        atmHistory.setOperationType(ATMOperationType.WITHDRAWAL);
        double amountBalance = drawal.getAmountBalance();

        if (card.getCardType() == CardType.UZCARD) {
            Map<UZSBankNoteType, Integer> map = new HashMap<>();
            for (Map.Entry<UZSBankNoteType, Integer> UZS : atm.getBankNoteCountUZS().entrySet()) {
                int bankNote = 0;
                if (amountBalance >= UZS.getKey().getValue() && UZS.getValue() > 0) {
                    bankNote = (int) amountBalance / UZS.getKey().getValue();
                    if (bankNote >= UZS.getValue()) bankNote = UZS.getValue();
                }
                if (bankNote != 0) {
                    map.put(UZS.getKey(), bankNote);
                    amountBalance -= bankNote * UZS.getValue();
                    UZS.setValue(UZS.getValue() - bankNote);

                }
            }
            if (amountBalance != 0) {
                return new ApiResponse("Bizda bunday kupyura yo'q!", false);
            }
            atmHistory.setBankNoteCountUZS(map);
        }
        if (card.getCardType() == CardType.VISA) {
            Map<USDBankNoteType, Integer> map = new HashMap<>();
            for (Map.Entry<USDBankNoteType, Integer> USD : atm.getBankNoteCountUSD().entrySet()) {
                int bankNote = 0;
                if (amountBalance >= USD.getKey().getValue() && USD.getValue() > 0) {
                    bankNote = (int) amountBalance / USD.getKey().getValue();
                    if (bankNote >= USD.getValue()) bankNote = USD.getValue();
                }
                if (bankNote != 0) {
                    map.put(USD.getKey(), bankNote);
                    amountBalance -= bankNote * USD.getValue();
                    USD.setValue(USD.getValue() - bankNote);

                }
            }
            if (amountBalance != 0) {
                return new ApiResponse("Bizda bunday kupyura yo'q!", false);
            }
            atmHistory.setBankNoteCountUSD(map);
        }
        if (card.getBank().equals(atm.getBank())) {
            card.setBalance(card.getBalance() - drawal.getAmountBalance());
            atmHistory.setOperationAmount(card.getBalance() - drawal.getAmountBalance());
        } else {
            card.setBalance(card.getBalance() - drawal.getAmountBalance() * (1 + atm.getCommission() / 100));
            atmHistory.setOperationAmount(card.getBalance() - drawal.getAmountBalance() * (1 + atm.getCommission() / 100));
        }
        atm.setBalance(atm.getBalance() - drawal.getAmountBalance());
        atmRepository.save(atm);
        cardRepository.save(card);
        atmHistoryRepository.save(atmHistory);
        if (atm.getBalance() < atm.getAlertAmount()) {
            mailSender.mailTextAdd("kilichov190394@gmail.com", atm.getAddress());
        }
        return new ApiResponse("Success pul yechildi!", true);
    }

    public ApiResponse pulQoyish(PulQoyishDto dto) {
        ATM atm = dto.getAtm();
        Card card = dto.getCard();
        ATMHistory atmHistory = new ATMHistory();
        if (card.isActive() && card.isBlocked()) {
            return new ApiResponse("Hisobingiz muzlatilgan", false);
        }
        if (!atm.getCardTypes().equals(card.getCardType()) && !atm.getCardTypes().equals(dto.getCardType())) {
            return new ApiResponse("Card typelari mos emas!", true);
        }

        atmHistory.setCard(card);
        atmHistory.setAtm(atm);
        atmHistory.setDate(new Date(System.currentTimeMillis()));
        atmHistory.setOperationType(ATMOperationType.PULKIRITAWAL);
        double balance = 0;

        if (atm.getCardTypes() == CardType.UZCARD) {
            Map<UZSBankNoteType, Integer> bankNoteCountUZS = dto.getBanknoteCountUZS();
            for (Map.Entry<UZSBankNoteType, Integer> uzsB : bankNoteCountUZS.entrySet()) {
                balance += uzsB.getValue() * uzsB.getKey().getValue();
                for (Map.Entry<UZSBankNoteType, Integer> uzb : atm.getBankNoteCountUZS().entrySet()) {
                    if (uzb.getKey().equals(uzsB.getKey())) {
                        uzb.setValue(uzb.getValue() + uzsB.getValue());
                    }
                }
                boolean bool = true;
                for (Map.Entry<UZSBankNoteType, Integer> uzbBankNote : atm.getBankNoteCountUZS().entrySet()) {
                    if (uzsB.getKey().equals(uzbBankNote.getKey())) {
                        bool = false;
                        break;
                    }
                }
                if (bool) {
                    return new ApiResponse("Bunday bankNote qabul qilmaymiz!", false);
                }
            }
            atmHistory.setBankNoteCountUZS(bankNoteCountUZS);
        } else if (atm.getCardTypes() == CardType.VISA) {
            Map<USDBankNoteType, Integer> banknoteCountUSD = dto.getBanknoteCountUSD();
            for (Map.Entry<USDBankNoteType, Integer> uzsB : banknoteCountUSD.entrySet()) {
                balance += uzsB.getValue() * uzsB.getKey().getValue();
                for (Map.Entry<USDBankNoteType, Integer> usdBankNoteTypeIntegerEntry : atm.getBankNoteCountUSD().entrySet()) {
                    if (usdBankNoteTypeIntegerEntry.getKey().equals(uzsB.getKey())) {
                        usdBankNoteTypeIntegerEntry.setValue(usdBankNoteTypeIntegerEntry.getValue() + uzsB.getValue());
                        break;
                    }
                }

                boolean bool = true;
                for (Map.Entry<USDBankNoteType, Integer> uzsBankNoteATM : atm.getBankNoteCountUSD().entrySet()) {
                    if (uzsB.getKey().equals(uzsBankNoteATM.getKey())) {
                        bool = false;
                        break;
                    }
                }
                if (bool) {
                    return new ApiResponse("Biz bunday kupyura qabul qilmaymiz", false);
                }
            }
            atmHistory.setBankNoteCountUSD(banknoteCountUSD);
        } else return new ApiResponse("Bunday kupyura qabul qilmaymiz!", false);

        atm.setBalance(atm.getBalance() + balance);
        if (card.getBank().equals(atm.getBank())) {
            atmHistory.setOperationAmount(balance);
            card.setBalance(card.getBalance() + balance);
        } else {
            atmHistory.setOperationAmount(balance + (1 + atm.getCommission() / 100));
            card.setBalance(card.getBalance() + balance * (1 - atm.getCommission() / 100));
        }
        atmRepository.save(atm);
        cardRepository.save(card);
        return new ApiResponse("Pul toldirildi!!", true);

    }


}
