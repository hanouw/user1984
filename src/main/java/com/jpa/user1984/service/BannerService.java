package com.jpa.user1984.service;

import com.jpa.user1984.domain.Banner;
import com.jpa.user1984.domain.ProductFile;
import com.jpa.user1984.dto.BannerDTO;
import com.jpa.user1984.dto.BannerForm;
import com.jpa.user1984.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BannerService {
    private final BannerRepository bannerRepository;

    //목록조회
    public List<BannerDTO> findAll(){
        List<Banner> all = bannerRepository.findAll();
        List<BannerDTO> list = all.stream()
                .map(b -> new BannerDTO(b))
                .collect(Collectors.toList());
        return list;
    }

    //조회(1)
    public BannerDTO findOne(Long id){
        Banner banner = bannerRepository.findById(id).orElse(null);
        return new BannerDTO(banner);
    }


}
