package com.example.demo.src.event;

import com.example.demo.config.BaseException;
import com.example.demo.src.event.model.GetArticleMainRes;
import com.example.demo.src.event.model.GetEventMainRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class EventProvider {
    private final EventDao eventDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public EventProvider(EventDao eventDao, JwtService jwtService) {

        this.eventDao = eventDao;
        this.jwtService = jwtService;
    }

    public List<GetEventMainRes> getEventMain() throws BaseException{
        try{
            List<GetEventMainRes> getEventMainRes = eventDao.getEventMain();
            return getEventMainRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetArticleMainRes> getArticleMain() throws BaseException{
        try{
            List<GetArticleMainRes> getArticleMainRes = eventDao.getArticleMain();
            return getArticleMainRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
