package com.jpa.user1984.repository;

import com.jpa.user1984.domain.PaymentBookHistory;
import com.jpa.user1984.dto.PageRequestDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
@Primary
public class PaymentBookHistoryCustomRepositoryImpl implements PaymentBookHistoryCustomRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public PageRequestDTO bindingMethod(PageRequestDTO pageRequestDTO) {

        PageRequestDTO newDTO = new PageRequestDTO();

        log.info("************bindingMethod pageRequestDTO:{}", pageRequestDTO);

        if (pageRequestDTO.getDateOrder() == null || pageRequestDTO.getDateOrder().equals("desc")) {
            newDTO.setDateOrder("desc");
            pageRequestDTO.setDateOrder("desc");
        } else if (pageRequestDTO.getDateOrder() != null || !pageRequestDTO.getDateOrder().equals("")) {
            newDTO.setDateOrder(pageRequestDTO.getDateOrder());
        }
        if (pageRequestDTO.getKeyword() == null || pageRequestDTO.getKeyword().equals("")) {
            newDTO.setKeyword(null);
        } else {
            newDTO.setKeyword(pageRequestDTO.getKeyword());
        }
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (pageRequestDTO.getDatePeriod() == null || pageRequestDTO.getDatePeriod().equals("")) {
            newDTO.setStartDate(null);
            newDTO.setEndDate(null);
        } else {
            if (pageRequestDTO.getDatePeriod().equals("today")) {
                startDate = LocalDate.now().atStartOfDay();
                endDate = LocalDate.now().atTime(LocalTime.MAX);
                newDTO.setStartDate(startDate.format(formatter));
                newDTO.setEndDate(endDate.format(formatter));
                pageRequestDTO.setStartDate(startDate.format(formatter));
                pageRequestDTO.setEndDate(endDate.format(formatter));
            } else if (pageRequestDTO.getDatePeriod().equals("oneWeek")) {
                startDate = LocalDate.now().minusDays(7).atStartOfDay();
                endDate = LocalDateTime.now();
                pageRequestDTO.setStartDate(startDate.format(formatter));
                pageRequestDTO.setEndDate(endDate.format(formatter));
                newDTO.setStartDate(startDate.format(formatter));
                newDTO.setEndDate(endDate.format(formatter));
            } else if (pageRequestDTO.getDatePeriod().equals("oneMonth")) {
                startDate = LocalDate.now().minusDays(30).atStartOfDay();
                endDate = LocalDateTime.now();
                pageRequestDTO.setStartDate(startDate.format(formatter));
                pageRequestDTO.setEndDate(endDate.format(formatter));
                newDTO.setStartDate(startDate.format(formatter));
                newDTO.setEndDate(endDate.format(formatter));
            }
        }
        if (pageRequestDTO.getStartDate() == null || pageRequestDTO.getStartDate().equals("")) {
            newDTO.setStartDate(null);
            newDTO.setEndDate(null);
        }
        if (pageRequestDTO.getStartDate() != null && !pageRequestDTO.getStartDate().equals("")) {
            startDate = LocalDate.parse(pageRequestDTO.getStartDate(), formatter).atStartOfDay();
            endDate = LocalDate.parse(pageRequestDTO.getEndDate(), formatter).atTime(LocalTime.MAX);
            newDTO.setStartDate(startDate.format(formatter));
            newDTO.setEndDate(endDate.format(formatter));
        }
        return newDTO;
    }

    @Override
    public List<PaymentBookHistory> findByOrderBookId(Long orderBookId) {
        List<PaymentBookHistory> resultList = em.createQuery("select p from PaymentBookHistory p " +
                        "where p.paymentBook.orderBookId = :orderBookId ",  PaymentBookHistory.class)
                .setParameter("orderBookId", orderBookId)
                .getResultList();
        return resultList;
    }

    @Override
    public List<PaymentBookHistory> findBookListByUserNo(Long userNo, PageRequestDTO pageRequestDTO) {
        int offset = (pageRequestDTO.getPage() - 1) * pageRequestDTO.getSize();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        PageRequestDTO dto = bindingMethod(pageRequestDTO);
        String order = dto.getDateOrder();
        String keyword = dto.getKeyword();
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;
        if (dto.getStartDate() != null) {
            startDate = LocalDate.parse(dto.getStartDate(), formatter).atStartOfDay();
            endDate = LocalDate.parse(dto.getEndDate(), formatter).atTime(LocalTime.MAX);
        }

        log.info("-------------PaymentBookHistoryRepo order:{}, keyword:{}, startDate:{}, endDate:{}", order, keyword, startDate, endDate);

        if (startDate == null && keyword == null ) { // 키워드 없음 + 기간 없음
            log.info("**************************키워드 없음 + 기간 없음");
            List<PaymentBookHistory> historyList = em.createQuery("select p from PaymentBookHistory p " +
                            "where p.paymentBook.member.userNo = :userNo  And p.paymentBook.paymentBookStatus = 'COMPLETE' " +
                            "order by p.createDate "+ order +" ", PaymentBookHistory.class)
                    .setParameter("userNo", userNo)
                    .setFirstResult(offset)
                    .setMaxResults(pageRequestDTO.getSize())
                    .getResultList();
            return historyList;
        }
        else if (keyword == null && startDate != null) { // 키워드 없음 + 기간 있음
            log.info("**************************키워드 없음 + 기간 있음");
            List<PaymentBookHistory> historyList = em.createQuery("select p from PaymentBookHistory p " +
                            "where p.paymentBook.member.userNo = :userNo and p.createDate between :startDate And :endDate  " +
                            "And p.paymentBook.paymentBookStatus = 'COMPLETE' " +
                            "order by p.createDate "+ order +" ", PaymentBookHistory.class)
                    .setParameter("userNo", userNo)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .setFirstResult(offset)
                    .setMaxResults(pageRequestDTO.getSize())
                    .getResultList();
            return historyList;
        }
        else if (startDate == null && keyword != null) { // 키워드 있음 + 기간 없음
            log.info("**************************키워드 있음 + 기간 없음");
            String s = searchTypeMethod(pageRequestDTO);
            keyword = pageRequestDTO.getKeyword();
            List<PaymentBookHistory> historyList = em.createQuery("select p from PaymentBookHistory p " +
                            "where p.paymentBook.member.userNo = :userNo and " + s + " like concat('%', :keyword, '%') " +
                            "And p.paymentBook.paymentBookStatus = 'COMPLETE' " +
                            "order by p.createDate "+ order +" ", PaymentBookHistory.class)
                    .setParameter("userNo", userNo)
                    .setParameter("keyword", keyword)
                    .setFirstResult(offset)
                    .setMaxResults(pageRequestDTO.getSize())
                    .getResultList();
            return historyList;
        }
        else { // 키워드 있음+ 기간 있음
            log.info("**************************키워드 있음 + 기간 있음");
            String s = searchTypeMethod(pageRequestDTO);
            keyword = pageRequestDTO.getKeyword();
            List<PaymentBookHistory> historySerachList = em.createQuery("select p from PaymentBookHistory p " +
                            "where p.paymentBook.member.userNo = :userNo and " + s + " like concat('%', :keyword, '%') " +
                            "And p.paymentBook.paymentBookStatus = 'COMPLETE' " +
                            "and p.createDate between :startDate And :endDate  " +
                            "order by p.createDate " + order + " ", PaymentBookHistory.class)
                    .setParameter("userNo", userNo)
                    .setParameter("keyword", keyword)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .setFirstResult(offset)
                    .setMaxResults(pageRequestDTO.getSize())
                    .getResultList();

            return historySerachList;
        }
    }

    @Override
    public Long countBookListByUserNo(Long userNo, PageRequestDTO pageRequestDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        PageRequestDTO dto = bindingMethod(pageRequestDTO);
        String order = dto.getDateOrder();
        String keyword = dto.getKeyword();
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;
        if (dto.getStartDate() != null) {
            startDate = LocalDate.parse(dto.getStartDate(), formatter).atStartOfDay();
            endDate = LocalDate.parse(dto.getEndDate(), formatter).atTime(LocalTime.MAX);
        }

        log.info("************PaymentBookHistoryRepo count page:{},order:{},keyword:{},startDate:{},endDate:{}",pageRequestDTO.getPage(),order,keyword,startDate,endDate);

        if (startDate == null && keyword == null) {
            log.info("**************************키워드 없음 + 기간 없음");
            Long searchResult = (Long) em.createQuery("select count(p) from PaymentBookHistory p " +
                            "where p.paymentBook.member.userNo = :userNo And p.paymentBook.paymentBookStatus = 'COMPLETE' " +
                            "order by p.createDate "+ order +" ")
                    .setParameter("userNo", userNo)
                    .getSingleResult();
            return searchResult;
        }
        else if (keyword == null && startDate != null) {
            log.info("**************************키워드 없음 + 기간 있음");
            Long searchResult = (Long) em.createQuery("select count(p) from PaymentBookHistory p " +
                            "where p.paymentBook.member.userNo = :userNo and p.createDate between :startDate " +
                            "And :endDate And p.paymentBook.paymentBookStatus = 'COMPLETE' " +
                            "order by p.createDate "+ order +" ")
                    .setParameter("userNo", userNo)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .getSingleResult();
            return searchResult;
        }
        else if (startDate == null && keyword != null) {
        log.info("**************************키워드 없음 + 기간 있음");
            String s = searchTypeMethod(pageRequestDTO);
            Long searchResult = (Long) em.createQuery("select count(p) from PaymentBookHistory p " +
                            "where p.paymentBook.member.userNo = :userNo and " + s + " like concat('%', :keyword, '%') " +
                            "And p.paymentBook.paymentBookStatus = 'COMPLETE' " +
                            "order by p.createDate "+ order +" ")
                    .setParameter("userNo", userNo)
                    .getSingleResult();
            log.info("--PaymentRepo result : {}", searchResult);
            return searchResult;
        }
        else {
            log.info("**************************키워드 있음 + 기간 있음");
            String s = searchTypeMethod(pageRequestDTO);
            Long searchResult = (Long) em.createQuery("select count(p) from PaymentBookHistory p " +
                            "where p.paymentBook.member.userNo = :userNo and " + s + " like concat('%', :keyword, '%') " +
                            "And p.paymentBook.paymentBookStatus = 'COMPLETE' " +
                            "and p.createDate between :startDate And :endDate  " +
                            "order by p.createDate " + order + " ")
                    .setParameter("userNo", userNo)
                    .setParameter("keyword", keyword)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .getSingleResult();
            return searchResult;
        }
    }


    @Override
    public String searchTypeMethod(PageRequestDTO pageRequestDTO) {
        String searchType = pageRequestDTO.getSearchType();
        String s = "p";
        switch (searchType) {
            case "orderNo" :
                s += ".paymentBook.orderBookNo";
                break;
            case "userId" :
                s += ".paymentBook.member.userId";
                break;
            case "userName" :
                s += ".paymentBook.member.userName";
                break;
            case "bookId" :
                s += ".book.bookId";
                break;
            case "bookTitle" :
                s += ".book.bookTitle";
                break;
            case "storeTitle" :
                s += ".book.storeTitle";
                break;
            case "status" :
                s += ".paymentBook.paymentBookStatus";
                break;
            case "method" :
                s += ".paymentBook.orderBookMethod";
                break;
        }
        return s;
    }


}
