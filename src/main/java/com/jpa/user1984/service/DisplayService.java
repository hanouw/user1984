package com.jpa.user1984.service;

import com.jpa.user1984.domain.Display;
import com.jpa.user1984.domain.ProductFile;
import com.jpa.user1984.dto.DisplayDTO;
import com.jpa.user1984.dto.DisplayForm;
import com.jpa.user1984.repository.DisplayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DisplayService {
    private final DisplayRepository displayRepository;
    // 상세
    public DisplayDTO findOne() {
        Display display = displayRepository.findById(1L).orElse(null);
        return new DisplayDTO(display);
    }
}
