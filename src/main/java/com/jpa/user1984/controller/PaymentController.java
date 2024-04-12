package com.jpa.user1984.controller;


import com.jpa.user1984.dto.PaymentBookForm;
import com.jpa.user1984.dto.PaymentMemForm;
import com.jpa.user1984.security.domain.CustomMember;
import com.jpa.user1984.service.PaymentService;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
// 주문시에 사용하는 컨트롤러
@RestController
@Slf4j
@RequestMapping("/order")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @Value("${iamport.key}")
    private String restApiKey;
    @Value("${iamport.secret}")
    private String restApiSecret;

    private IamportClient iamportClient;

    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(restApiKey, restApiSecret);
    }

    // 결제 시도 시 검증 처리 요청
    @PostMapping("/validation/{imp_uid}")
    @ResponseBody
    public IamportResponse<Payment> paymentByImpUid(@PathVariable("imp_uid") String imp_uid) throws IOException {
        return iamportClient.paymentByImpUid(imp_uid);
    }

    // 책 주문 처리 요청
    @PostMapping("/book")
    public ResponseEntity<String> orderBook(@RequestBody PaymentBookForm paymentBookForm,
                                                  @AuthenticationPrincipal CustomMember customMember) throws IOException {
        log.info("*******PaymentController orderBook 실행");
        Long garaId = 1L;
        Long saved = paymentService.saveBookOrder(garaId, paymentBookForm);
        log.info("결제 성공 : 주문 번호 {}", saved);
        return ResponseEntity.ok().build();
    }

    // 구독권 주문 처리 요청
    @PostMapping("/membership")
    public ResponseEntity<String> membershipOrder(@RequestBody PaymentMemForm paymentMemForm,
                                                  @AuthenticationPrincipal CustomMember customMember) throws IOException {
        log.info("*******PaymentController membershipOrder 실행");
        Long garaId = 1L;
        Long saved = paymentService.saveMembershipOrder(garaId, paymentMemForm);
        log.info("결제 성공 : 주문 번호 {}", saved);
        return ResponseEntity.ok().build();
    }

}
