package server.tigglemate.domain.account.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.tigglemate.domain.account.domain.entity.Account;

import java.time.LocalDate;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    // 금일 지출 내역 리스트 조회
    @Query("SELECT a FROM Account a WHERE DATE(a.createDate) = :date AND a.type = 'EXPENSE'")
    List<Account> findAllByCreateDate(LocalDate date);

    // 금일 지출 금액 합계 조회
    @Query("SELECT SUM(a.amount) FROM Account a WHERE a.createDate= :date")
    Integer sumTodayExpenses(LocalDate date);

    // 월별 지출 합계 조회
    @Query("SELECT SUM(a.amount) FROM Account a WHERE FUNCTION('YEAR', a.createDate) = :year AND FUNCTION('MONTH', a.createDate) = :month AND a.type = 'EXPENSE'")
    Integer sumExpensesByMonth(int year, int month);

    // 월별 카테고리 지출 항목 수
    @Query("SELECT a.category, COUNT(a) FROM Account a WHERE YEAR(a.createDate) = :year AND MONTH(a.createDate) = :month AND a.type = 'EXPENSE' GROUP BY a.category")
    List<Object[]> countExpensesByCategoryForMonth(int year, int month);

    // 월별 카테고리별 지출 금액 합계
    @Query("SELECT a.category, SUM(a.amount) FROM Account a WHERE YEAR(a.createDate) = :year AND MONTH(a.createDate) = :month AND a.type = 'EXPENSE' GROUP BY a.category")
    List<Object[]> getExpensesByCategory(int year, int month);

    // 월별 만족도별 지출 금액 합계
    @Query("SELECT a.satisfaction, SUM(a.amount) FROM Account a WHERE YEAR(a.createDate) = :year AND MONTH(a.createDate) = :month AND a.type = 'EXPENSE' GROUP BY a.satisfaction")
    List<Object[]> getExpensesBySatisfaction(int year, int month);

    // 한 달 일별 지출 금액 합계
    @Query("SELECT a.createDate, SUM(a.amount) FROM Account a WHERE YEAR(a.createDate) = :year AND MONTH(a.createDate) = :month AND a.type = 'EXPENSE' GROUP BY a.createDate ORDER BY a.createDate")
    List<Object[]> sumExpensesByDayForMonth(int year, int month);

}
