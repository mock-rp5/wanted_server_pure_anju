package com.example.demo.src.bookmark;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;

import com.example.demo.src.bookmark.model.GetBookmarkRes;

import com.example.demo.src.bookmark.model.PostBookmarkRes;
import com.example.demo.src.company.model.PostCompanyReq;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;


import static com.example.demo.config.BaseResponseStatus.POST_BOOKMARK_FAIL;


@RestController
@RequestMapping("/app/bookmarks")
public class BookmarkController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final BookmarkProvider bookmarkProvider;
    @Autowired
    private final BookmarkService bookmarkService;
    @Autowired
    private final JwtService jwtService;

    public BookmarkController(BookmarkProvider bookmarkProvider, BookmarkService bookmarkService, JwtService jwtService) {
        this.bookmarkProvider = bookmarkProvider;
        this.bookmarkService = bookmarkService;
        this.jwtService = jwtService;
    }

    /**
     * 채용공고 북마크 생성 API
     * [POST]
     */
    @ResponseBody
    @PostMapping("/employments/{employmentIdx}")
    public BaseResponse<String> createBookmark(@PathVariable("employmentIdx") int employmentIdx) {
        try{
            Long userIdx = jwtService.getUserIdx();

            int status = bookmarkProvider.checkStatusUserEmployment(userIdx, employmentIdx);
            if(status == 1){
                return new BaseResponse<>(POST_BOOKMARK_FAIL);
            }

            String result = "북마크에 저장되었습니다.";
            bookmarkService.createBookmark(userIdx, employmentIdx);


            return new BaseResponse<>(result);


        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 채용공고 북마크 취소 API
     * [PATCH]
     */
    @ResponseBody
    @PatchMapping("/employments/{employmentIdx}")
    public BaseResponse<String> cancelBookmark(@PathVariable("employmentIdx") int employmentIdx){
        try{
            Long userIdx = jwtService.getUserIdx();
            bookmarkService.cancelBookmark(userIdx, employmentIdx);
            String result = "북마크가 해제되었습니다.";

            return new BaseResponse<>(result);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 북마크 조회 API
     * [GET]
     */
    @ResponseBody
    @GetMapping("/users")
    public BaseResponse<List<GetBookmarkRes>> findBookmarks() {
        try{
            Long userIdx = jwtService.getUserIdx();
            List<GetBookmarkRes> getBookmarkRes = bookmarkProvider.getBookmarks(userIdx);
            return new BaseResponse<>(getBookmarkRes);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
