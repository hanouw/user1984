package com.jpa.user1984.service;

import com.jpa.user1984.domain.PaymentBookHistory;
import com.jpa.user1984.domain.Store;
import com.jpa.user1984.dto.*;
import com.jpa.user1984.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CmsService {

    private final CmsRepository cmsRepository;
    private final CmsCustomRepositoryImpl cmsCustomRepository;
    private final PaymentBookHistoryRepository paymentBookHistoryRepository;
    private final PasswordEncoder storePasswordEncoder;
    private final PaymentCustomRepository paymentCustomRepository;
    private final PaymentBookHistoryCustomRepositoryImpl paymentBookHistoryCustomRepository;

    // 서점관리 - 서점 정보 등록
    public String save(StoreForm storeForm){
        storeForm.setStorePassword(storePasswordEncoder.encode(storeForm.getStorePassword()));
        cmsRepository.save(storeForm.toSignupEntity());
        return null;
    }

    // 서점 아이디로 전체 찾아오기
    public StoreDTO findStoreById(String storeLoginId){
        try {
            Store store = cmsCustomRepository.findByStoreLoginId(storeLoginId);
            return(new StoreDTO(store));
        } catch (Exception e){
            return null;
        }
    }

    // 판매자 로그인
//    public StoreLoginForm login(StoreLoginForm storeloginForm){
//        Store store = cmsCustomRepository.findByStoreLoginId(storeloginForm.getStoreLoginId());
//        log.info("******* CmsService = {}", store);
//        if(store != null){ // Optional 객체가 값을 가지고 있다면 true, 값이 없다면 false 리턴
//            if(storeloginForm.getStorePassword().equals(store.getStorePassword())){
//                return storeloginForm;
//            }
//        }
//        return null;
//    }

    // 주문관리 - 주문 목록 조회 판매자 ver
    public List<PaymentBookResponseDTO> findHistoryList(Long storeId, PageRequestDTO pageRequestDTO) {
        List<PaymentBookHistory> historyEntityList = paymentBookHistoryCustomRepository.findListByStoreId(storeId, pageRequestDTO);
//        List<PaymentBookHistoryDTO> list = new ArrayList<>();
//        for (PaymentBookHistory p : historyEntityList) {
//            list.add(new PaymentBookHistoryDTO(p));
//        }
        List<PaymentBookResponseDTO> list = new ArrayList<>();
        for (PaymentBookHistory orderList : historyEntityList) {
            PaymentBookResponseDTO paymentBookResponseDTO = new PaymentBookResponseDTO();
            paymentBookResponseDTO.setOrderBookNo(orderList.getPaymentBook().getOrderBookNo());
            paymentBookResponseDTO.setOrderBookId(orderList.getPaymentBook().getOrderBookId());
            paymentBookResponseDTO.setUserId(orderList.getPaymentBook().getMember().getUserId());
            paymentBookResponseDTO.setUserName(orderList.getPaymentBook().getMember().getUserName());
            paymentBookResponseDTO.setIsbn(orderList.getBook().getBookId());
            paymentBookResponseDTO.setBookTitle(orderList.getBook().getBookTitle());
            //paymentResponseDTO.setStoreTitle(orderList.getBook().getS);
            paymentBookResponseDTO.setPaymentBookStatus(orderList.getPaymentBook().getPaymentBookStatus());
            paymentBookResponseDTO.setOrderBookMethod(orderList.getPaymentBook().getOrderBookMethod());
            paymentBookResponseDTO.setCreateDate(orderList.getPaymentBook().getCreateDate());
            paymentBookResponseDTO.setBookPub(orderList.getBook().getBookPub());
            paymentBookResponseDTO.setBookEbookPrice(orderList.getBook().getBookEbookPrice());
            paymentBookResponseDTO.setCreateDate(orderList.getCreateDate());
            list.add(paymentBookResponseDTO);
        }
        return list;
    }

    // 주문관리 - 검색된 주문 개수 조회 판매자 ver
    public Long countHistoryList(Long storeId, PageRequestDTO pageRequestDTO) {
        return paymentBookHistoryCustomRepository.countHistoryListByStoreId(storeId, pageRequestDTO);
    }

}
