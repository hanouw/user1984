package com.jpa.user1984.repository;

import com.jpa.user1984.domain.PaymentMem;
import com.jpa.user1984.domain.PaymentMemStatus;
import com.jpa.user1984.dto.PageRequestDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class PaymentMemCustomRepositoryImpl implements PaymentMemCustomRepository{

    @PersistenceContext
    private EntityManager em;

    @Qualifier("paymentBookHistoryCustomRepositoryImpl")
    private final PaymentBookHistoryCustomRepository paymentBookHistoryCustomRepository;

    @Override
    public PaymentMem findMembershipByOrderMembershipId(Long orderMembershipId) {
        PaymentMem result = em.createQuery("select p from PaymentMem p " +
                        "where p.orderMembershipId = :orderMembershipId And p.paymentMemStatus = 'COMPLETE' ", PaymentMem.class)
                .setParameter("orderMembershipId", orderMembershipId)
                .getSingleResult();
        return result;
    }

    @Override
    public List<PaymentMem> findMembershipListByUserNo(Long userNo, PageRequestDTO pageRequestDTO) {
        int offset = (pageRequestDTO.getPage() - 1) * pageRequestDTO.getSize();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        PageRequestDTO dto = paymentBookHistoryCustomRepository.bindingMethod(pageRequestDTO);
        String order = dto.getDateOrder();
        String keyword = dto.getKeyword();
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;
        if (dto.getStartDate() != null) {
            startDate = LocalDate.parse(dto.getStartDate(), formatter).atStartOfDay();
            endDate = LocalDate.parse(dto.getEndDate(), formatter).atTime(LocalTime.MAX);
        }

        log.info("-------------PaymentMemCustomRepositoryImpl order:{}, keyword:{}, startDate:{}, endDate:{}", order, keyword, startDate, endDate);

        if (startDate == null && keyword == null ) { // 키워드 없음 + 기간 없음
            log.info("**************************키워드 없음 + 기간 없음");
            List<PaymentMem> historyList = em.createQuery("select p from PaymentMem p " +
                            "where p.member.userNo = :userNo And p.paymentMemStatus = 'COMPLETE' " +
                            "order by p.membershipStartDate "+ order +" ", PaymentMem.class)
                    .setParameter("userNo", userNo)
                    .setFirstResult(offset)
                    .setMaxResults(pageRequestDTO.getSize())
                    .getResultList();
            return historyList;
        }
        else if (keyword == null && startDate != null) { // 키워드 없음 + 기간 있음
            log.info("**************************키워드 없음 + 기간 있음");
            List<PaymentMem> historyList = em.createQuery("select p from PaymentMem p " +
                            "where p.member.userNo = :userNo and p.membershipStartDate between :startDate And :endDate " +
                            "And p.paymentMemStatus = 'COMPLETE' order by p.membershipStartDate "+ order +" ", PaymentMem.class)
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
            List<PaymentMem> historyList = em.createQuery("select p from PaymentMem p " +
                            "where p.member.userNo = :userNo and " + s + " like concat('%', :keyword, '%') " +
                            "And p.paymentMemStatus = 'COMPLETE' order by p.membershipStartDate "+ order +" ", PaymentMem.class)
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
            List<PaymentMem> historySerachList = em.createQuery("select p from PaymentMem p " +
                            "where p.member.userNo = :userNo and " + s + " like concat('%', :keyword, '%') " +
                            "and p.membershipStartDate between :startDate And :endDate And p.paymentMemStatus = 'COMPLETE' " +
                            "order by p.membershipStartDate " + order + " ", PaymentMem.class)
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
    public Long countMembershipListByUserNo(Long userNo, PageRequestDTO pageRequestDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        PageRequestDTO dto = paymentBookHistoryCustomRepository.bindingMethod(pageRequestDTO);
        String order = dto.getDateOrder();
        String keyword = dto.getKeyword();
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;
        if (dto.getStartDate() != null) {
            startDate = LocalDate.parse(dto.getStartDate(), formatter).atStartOfDay();
            endDate = LocalDate.parse(dto.getEndDate(), formatter).atTime(LocalTime.MAX);
        }
        log.info("************PaymentMemCustomRepositoryImpl count page:{},order:{},keyword:{},startDate:{},endDate:{}",pageRequestDTO.getPage(),order,keyword,startDate,endDate);

        if (startDate == null && keyword == null) {
            log.info("**************************키워드 없음 + 기간 없음");
            Long result = (Long) em.createQuery("select count(p) from PaymentMem p " +
                            "where p.member.userNo = :userNo And p.paymentMemStatus = 'COMPLETE' " +
                            "order by p.membershipStartDate "+ order +" ")
                    .setParameter("userNo", userNo)
                    .getSingleResult();
            return result;
        }
        else if(keyword == null && startDate != null){
            log.info("**************************키워드 없음 + 기간 있음");
            Long searchResult = (Long) em.createQuery("select count(p) from PaymentMem p " +
                            "where p.member.userNo = :userNo and p.membershipStartDate between :startDate And :endDate " +
                            "And p.paymentMemStatus = 'COMPLETE' " +
                            "order by p.membershipStartDate "+ order +" ")
                    .setParameter("userNo", userNo)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .getSingleResult();
            return searchResult;
        }
        else if (startDate == null && keyword != null) {
            log.info("**************************키워드 있음 + 기간 없음");
            String s = searchTypeMethod(pageRequestDTO);
            Long searchResult = (Long) em.createQuery("select count(p) from PaymentMem p " +
                            "where p.member.userNo = :userNo and " + s + " like concat('%', :keyword, '%') " +
                            "And p.paymentMemStatus = 'COMPLETE' order by p.membershipStartDate " + order + " ")
                    .setParameter("userNo", userNo)
                    .setParameter("keyword", keyword)
                    .getSingleResult();
            return searchResult;
        }
        else {
            log.info("**************************키워드 있음 + 기간 있음");
            String s = searchTypeMethod(pageRequestDTO);
            Long result = (Long) em.createQuery("select count(p) from PaymentMem p " +
                            "where p.member.userNo = :userNo and " + s + " like concat('%', :keyword, '%') " +
                            "and p.membershipStartDate between :startDate And :endDate And p.paymentMemStatus = 'COMPLETE' " +
                            "order by p.membershipStartDate " + order + " ")
                    .setParameter("userNo", userNo)
                    .setParameter("keyword", keyword)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .getSingleResult();
            return result;
        }
    }


    @Override
    public String searchTypeMethod(PageRequestDTO pageRequestDTO) {
        String searchType = pageRequestDTO.getSearchType();
        String s = "p";
        switch (searchType) {
            case "userId" :
                s += ".member.userId";
                break;
            case "userName" :
                s += ".member.userName";
                break;
            case "userPhoneNum" :
                s += ".member.userPhoneNum";
                break;
            case "userEmail" :
                s += ".member.userEmail";
                break;
            case "orderMembershipMethod" :
                s += ".orderMembershipMethod";
                break;
            case "storeTitle":
                s += ".store.storeTitle";
                break;
        }
        return s;
    }


}
