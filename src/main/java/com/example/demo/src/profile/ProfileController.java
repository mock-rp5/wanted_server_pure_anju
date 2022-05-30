package com.example.demo.src.profile;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.profile.model.GetProfileRes;
import com.example.demo.src.profile.model.PatchSpecializedFieldReq;
import com.example.demo.src.resume.model.GetResumeRes;
import com.example.demo.src.user.model.PostUserReq;
import com.example.demo.src.user.model.UserDto;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/profiles")
public class ProfileController {

    private final ProfileProvider profileProvider;
    private final ProfileService profileService;
    private final JwtService jwtService;

    @Autowired
    public ProfileController(ProfileProvider profileProvider, ProfileService profileService, JwtService jwtService) {
        this.profileProvider = profileProvider;
        this.profileService = profileService;
        this.jwtService = jwtService;
    }

    /**
     * 전문 분야 수정 API
     * [post] /app/profiles/specialized-field
     */
    @PatchMapping("/specialized-field")
    public BaseResponse<BaseResponseStatus> modifyProfiles(@Valid @RequestBody PatchSpecializedFieldReq patchSpecializedFieldReq) {
        try {
            Long userIdx = jwtService.getUserIdx();
            profileService.modifyProfileSpecializedField(userIdx, patchSpecializedFieldReq);
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
