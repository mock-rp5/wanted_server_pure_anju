package com.example.demo.src.event;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.event.model.GetArticleMainRes;
import com.example.demo.src.event.model.GetEventMainRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/app")
public class EventController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final EventProvider eventProvider;
    @Autowired
    private final EventService eventService;
    @Autowired
    private final JwtService jwtService;

    public EventController(EventProvider eventProvider, EventService eventService, JwtService jwtService) {
        this.eventProvider = eventProvider;
        this.eventService = eventService;
        this.jwtService = jwtService;
    }

    /**
     * 메인화면 이벤트 조회 API
     */
    @ResponseBody
    @GetMapping("/main/events")
    public BaseResponse<List<GetEventMainRes>> getEventMain(){
        try{
            List<GetEventMainRes> getEventMainRes = eventProvider.getEventMain();
            return new BaseResponse<>(getEventMainRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 메인화면 아티클 조회 API
     */
    @ResponseBody
    @GetMapping("/main/articles")
    public BaseResponse<List<GetArticleMainRes>> getArticleMain(){
        try{
            List<GetArticleMainRes> getArticleMainRes = eventProvider.getArticleMain();
            return new BaseResponse<>(getArticleMainRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}



