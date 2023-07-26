import db.DBConnection;
import model.account.Account;
import model.account.AccountDAO;
import model.transaction.Transaction;
import model.transaction.TransactionDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BankApp {
    public static void main(String[] args) {
        //소켓 받아오기
        Connection connection = DBConnection.getInstance();
        // db 접근하기 = DI
        AccountDAO accountDAO = new AccountDAO(connection);
        TransactionDAO transactionDAO = new TransactionDAO(connection);

        // 계좌 이체

            // 1. 이체 요청 정보
            String wAccountPassword = "1234";
            int wAccountNumber = 4444;
            int dAccountNumber = 5555;
            int amount = 100;

            // 2. 0원 이체 확인
            if(amount <= 0){
                System.out.println("[유효성 검사] 0원을 이체할 수 없습니다.");
                return;
            }

            // 3. 동일 계좌 확인
            if(wAccountNumber == dAccountNumber){
                System.out.println("[유효성 검사] 입출금 계좌가 동일할 수 없습니다.");
                return;
            }
        try {
            // ============================================= 트랜잭션 시작
            connection.setAutoCommit(false); // false로 만들어서 coommit이 안되게 만든다

            // 4. 계좌 존재 확인 (출금 계좌 존재여부)
            Account wAccount =  accountDAO.getAccountByNumber(wAccountNumber);
            if(wAccount == null){
                // 강제로 터트리면 아래 catch에서 받는다
                throw new RuntimeException("출금계좌가 존재하지 않습니다 : " + wAccountNumber);
            }

            // 5. 계좌 존재 확인 (입금 계좌 존재여부)
            Account dAccount =  accountDAO.getAccountByNumber(dAccountNumber);
            if(dAccount == null){
                // 강제로 터트리면 아래 catch에서 받는다
                throw new RuntimeException("입금계좌가 존재하지 않습니다 : " + dAccountNumber);
            }

            // 6. 계좌 비밀번호 확인 (출금 계좌 비번)
            if(!wAccount.getAccountPassword().equals(wAccountPassword)){
                throw new RuntimeException("출금 비밀번호가 일치하지 않습니다 : " + wAccountPassword);
            }

            // 7. 계좌 잔액 확인 (출금 계좌 잔액)
            if(wAccount.getAccountBalance() < amount){
                throw new RuntimeException("출금 계좌의 잔액이 부족합니다 : " + wAccount.getAccountBalance());
            }

            // 8. 출금 계좌 잔액 - update
            int wBalance = wAccount.getAccountBalance() - amount;
            accountDAO.updateAccount(wBalance, wAccountNumber);

            // 9. 입금 계좌 잔액 + update
            int dBalance = dAccount.getAccountBalance() + amount;
            accountDAO.updateAccount(dBalance, dAccountNumber);

            // 10. 계좌 트랜잭션 남기기
            Transaction transaction = Transaction.builder()
                    .transactionAmount(amount)
                    .transactionWAccountNumber(wAccountNumber)
                    .transactionDAccountNumber(dAccountNumber)
                    .transactionWBalance(wBalance)
                    .transactionDBalance(dBalance)
                    .build();
            transactionDAO.transfer(transaction);

            connection.commit();

            // =================================== 트랜잭션 종료
        }catch (Exception e){
            try {
                connection.rollback();
                System.out.println("[catch]" + e.getMessage());
            } catch (SQLException ex) {
                e.printStackTrace();
            }
        }finally {
            try {
                // 중간에 예외가 터졌을때는 catch로 빠지기 때문에
                // connection.setAutoCommit(true)가 반드시 실행되어야 해서 finally안에 넣어야한다
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        try {
             // 1. 계좌생성
             accountDAO.createAccount(1111, "1234", 1000);
             accountDAO.createAccount(4444, "1234", 1000);
             accountDAO.createAccount(5555, "1234", 1000);

             //2. 계좌목록보기
            List<Account> accounts = accountDAO.getAllAccounts();
            accounts.forEach(account -> {
                System.out.println("계좌번호 : "+account.getAccountNumber());
                System.out.println("계좌비번 : "+account.getAccountPassword());
                System.out.println("계좌잔액 : "+account.getAccountBalance());
                System.out.println("===================================");
            });

             //3. 계좌한건보기
            Account account = accountDAO.getAccountByNumber(3333);
            if(account == null){
                System.out.println("해당 계좌번호를 찾을 수 없습니다");
            }else{
                System.out.println("계좌번호 : "+account.getAccountNumber());
                System.out.println("계좌비번 : " + account.getAccountPassword());
                System.out.println("계좌잔액 : " + account.getAccountBalance());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}