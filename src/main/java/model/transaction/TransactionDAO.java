package model.transaction;

import db.DBConnection;
import dto.AccountDetailDTO;
import lombok.Getter;
import model.account.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TransactionDAO {
    private Connection connection;

    public TransactionDAO(Connection connection) {
        this.connection = connection;
    }


    public List<AccountDetailDTO> details(int accountNumber) throws SQLException {
        List<AccountDetailDTO> dtos = new ArrayList<>();
        String sql = "select \n" +
                "ac.account_number, \n" +
                "ac.account_balance, \n" +
                "ts.transaction_amount amount, \n" +
                "ts.transaction_w_account_number sender, \n" +
                "ts.transaction_d_account_number receiver,\n" +
                "if(ts.transaction_w_account_number= ?,ts.transaction_w_balance, ts.transaction_d_balance) balance,\n" +
                "ts.transaction_created_at transfer_date\n" +
                "from account_tb ac \n" +
                "inner join transaction_tb ts on ac.account_number = ts.transaction_w_account_number \n" +
                "OR ac.account_number = ts.transaction_d_account_number\n" +
                "where ac.account_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, accountNumber);
            statement.setInt(2, accountNumber);
            try(ResultSet rs = statement.executeQuery()){
                while (rs.next()) {
                    AccountDetailDTO dto = AccountDetailDTO.builder()
                            .accountNumber(rs.getInt("account_number"))
                            .accountBalance(rs.getInt("account_balance"))
                            .sender(rs.getInt("sender"))
                            .receiver(rs.getInt("receiver"))
                            .amount(rs.getInt("amount"))
                            .balance(rs.getInt("balance"))
                            .transferDate(rs.getTimestamp("transfer_date"))
                            .build();
                    dtos.add(dto);
                }
            }
        }
        return dtos;
    }

    public void transfer(Transaction transaction) throws SQLException{
        String query = "INSERT INTO transaction_tb (transaction_amount, transaction_w_balance, transaction_d_balance, transaction_w_account_number, transaction_d_account_number, transaction_created_at) VALUES (?, ?, ?, ?, ?, now())";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, transaction.getTransactionAmount());
            statement.setInt(2, transaction.getTransactionWBalance());
            statement.setInt(3, transaction.getTransactionDBalance());
            statement.setInt(4, transaction.getTransactionWAccountNumber());
            statement.setInt(5, transaction.getTransactionDAccountNumber());
            statement.executeUpdate();
        }
    }


}