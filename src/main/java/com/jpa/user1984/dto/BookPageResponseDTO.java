package com.jpa.user1984.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.List;
@Getter
@ToString
public class BookPageResponseDTO extends PageResponseDTO{

    protected List<PaymentBookHistoryDTO> orderList;

    public BookPageResponseDTO(PageRequestDTO pageRequestDTO, Long totalCount) {
        super(pageRequestDTO, totalCount);
    }

    public BookPageResponseDTO(PageRequestDTO pageRequestDTO, Long totalCount, List<PaymentBookHistoryDTO> orderList) {
        super(pageRequestDTO, totalCount);
        this.orderList = orderList;
    }
}
