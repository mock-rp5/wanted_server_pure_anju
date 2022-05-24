package com.example.demo.src.banner;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.banner.model.BannerDto;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app/banners")
public class BannerController {

    private final BannerProvider bannerProvider;
    private final JwtService jwtService;

    @Autowired
    public BannerController(BannerProvider bannerProvider, JwtService jwtService) {
        this.bannerProvider = bannerProvider;
        this.jwtService = jwtService;
    }

    /**
     * 베너 조회 API
     * [get] /app/banners/:location
     */
    @GetMapping("/{location}")
    public BaseResponse<List<BannerDto>> getBanners(@PathVariable String location) {
        try {
            return new BaseResponse<>(bannerProvider.getBanner(location));
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
