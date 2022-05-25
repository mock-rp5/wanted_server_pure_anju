package com.example.demo.src.bookmark;


import com.example.demo.config.BaseException;
import com.example.demo.src.bookmark.model.GetBookmarkRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class BookmarkProvider {
    private final BookmarkDao bookmarkDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public BookmarkProvider(BookmarkDao bookmarkDao, JwtService jwtService) {

        this.bookmarkDao = bookmarkDao;
        this.jwtService = jwtService;
    }

    //해당 userIdx와 employmentIdx가 이미 Bookmark 테이블에 존재하는지 확인
    public int checkStatusUserEmployment(Long userIdx, int employmentIdx) throws BaseException {
        try {
            return bookmarkDao.checkStatusUserEmployment(userIdx, employmentIdx);
        } catch (Exception exception) {

            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkUserEmployment(Long userIdx, int employmentIdx) throws BaseException {
        try {
            return bookmarkDao.checkUserEmployment(userIdx, employmentIdx);

        } catch (Exception exception) {

            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetBookmarkRes> getBookmarks(Long userIdx)throws BaseException{
        try {
            List<GetBookmarkRes> getBookmarkRes = bookmarkDao.getBookmarks(userIdx);
            return getBookmarkRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
