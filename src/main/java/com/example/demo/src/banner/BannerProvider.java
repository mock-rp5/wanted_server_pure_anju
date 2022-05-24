package com.example.demo.src.banner;

import com.example.demo.config.BaseException;
import com.example.demo.src.banner.BannerDao;
import com.example.demo.src.banner.model.BannerDto;
import com.example.demo.src.user.model.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Slf4j
@Service
@Transactional(readOnly = true)
public class BannerProvider {

    private final BannerDao bannerDao;

    @Autowired
    public BannerProvider(BannerDao bannerDao) {
        this.bannerDao = bannerDao;
    }

    // 배너 목록 조회
    public List<BannerDto> getBanner(String location)  throws BaseException {
        try {
            return bannerDao.getBanner(location);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
