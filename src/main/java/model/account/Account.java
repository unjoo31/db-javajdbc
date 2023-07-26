package model.account;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

@ToString
@Getter
public class Account {
    private int accountNumber; // PK
    private String accountPassword;
    private int accountBalance;
    private Timestamp accountCreatedAt;

    @Builder
    public Account(int accountNumber, String accountPassword, int accountBalance, Timestamp accountCreatedAt) {
        this.accountNumber = accountNumber;
        this.accountPassword = accountPassword;
        this.accountBalance = accountBalance;
        this.accountCreatedAt = accountCreatedAt;
    }
}