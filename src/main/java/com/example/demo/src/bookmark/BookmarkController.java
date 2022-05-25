package com.example.demo.src.bookmark;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.bookmark.model.PostBookmarkRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public BaseResponse<PostBookmarkRes> createBookmark(@PathVariable("employmentIdx") int employmentIdx) {
        try{
            Long userIdx = jwtService.getUserIdx();

            int status = bookmarkProvider.checkStatusUserEmployment(userIdx, employmentIdx);
            if(status == 1){
                return new BaseResponse<>(POST_BOOKMARK_FAIL);
            }
            String result = "북마크에 저장되었습니다.";
            PostBookmarkRes postBookmarkRes = bookmarkService.createBookmark(userIdx, employmentIdx, result);


            return new BaseResponse<>(postBookmarkRes);


        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

//    /**
//     * 북마크 조회 API
//     * [GET]
//     */
//    @ResponseBody
//    @GetMapping("/users/{userIdx}")
//    public BaseResponse<>
}
