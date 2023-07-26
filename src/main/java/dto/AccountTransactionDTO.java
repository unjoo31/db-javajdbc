package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import model.account.Account;
import model.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @AllArgsConstructor
public class AccountTransactionDTO {
    private Account account;
    private List<Transaction> transactions = new ArrayList<>();
}