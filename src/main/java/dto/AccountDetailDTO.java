package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter @AllArgsConstructor @Builder
public class AccountDetailDTO {
    // 계좌
    private Integer accountNumber;
    private Integer accountBalance;

    // 트랜잭션
    private Integer sender;
    private Integer receiver;
    private Integer amount;
    private Integer balance;
    private Timestamp transferDate;
}