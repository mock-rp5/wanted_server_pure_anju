package com.example.demo.src.bookmark;

import com.example.demo.config.BaseException;

import com.example.demo.config.BaseResponse;

import com.example.demo.src.bookmark.model.PostBookmarkRes;
import com.example.demo.src.company.model.PostCompanyReq;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class BookmarkService {

    private final BookmarkProvider bookmarkProvider;
    private final BookmarkDao bookmarkDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public BookmarkService(BookmarkProvider bookmarkProvider, BookmarkDao bookmarkDao, JwtService jwtService) {
        this.bookmarkProvider = bookmarkProvider;
        this.bookmarkDao = bookmarkDao;
        this.jwtService = jwtService;
    }

    //북마크 생성
    public void createBookmark(Long userIdx, int employmentIdx) throws BaseException {
        try {

            bookmarkDao.createBookmark(userIdx, employmentIdx);

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }




    //북마크 취소
    public void cancelBookmark(Long userIdx, int employmentIdx) throws BaseException{
        try{
            int result = bookmarkDao.cancelBookmark(userIdx, employmentIdx);
            if(result == 0)
                throw new BaseException(FAIL_CANCEL_BOOKMARK);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }



}
