package com.jpa.user1984.controller;


import com.jpa.user1984.dto.PaymentBookForm;
import com.jpa.user1984.security.domain.CustomMember;
import com.jpa.user1984.service.PaymentBookService;
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

    private final PaymentBookService paymentBookService;

    @Value("${iamport.key}")
    private String restApiKey;
    @Value("${iamport.secret}")
    private String restApiSecret;

    private IamportClient iamportClient;

    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(restApiKey, restApiSecret);
    }

    // 책 주문 처리 요청
    @PostMapping("/book")
    public ResponseEntity<String> paymentComplete(@RequestBody PaymentBookForm paymentBookForm,
                                                  @AuthenticationPrincipal CustomMember customMember) throws IOException {
        Long userNo = customMember.getMember().getUserNo();
        paymentBookService.saveOrder(userNo, paymentBookForm);
        log.info("결제 성공 : 주문 번호 {}", paymentBookForm.getOrderBookId());
        return ResponseEntity.ok().build();
    }

    // 결제 시도 시 검증 처리 요청
    @PostMapping("/book/validation/{imp_uid}")
    @ResponseBody
    public IamportResponse<Payment> paymentByImpUid(@PathVariable("imp_uid") String imp_uid) throws IOException {
        return iamportClient.paymentByImpUid(imp_uid);
    }

}
