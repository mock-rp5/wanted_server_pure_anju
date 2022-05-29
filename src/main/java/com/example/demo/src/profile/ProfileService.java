package com.example.demo.src.profile;

import com.example.demo.config.BaseException;
import com.example.demo.src.profile.model.PatchSpecializedFieldReq;
import com.example.demo.src.profile.model.PutSpecializedFieldReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Transactional(readOnly = false)
public class ProfileService {

    private ProfileDao profileDao;

    @Autowired
    public ProfileService(ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    public void modifyProfileSpecializedField(Long userIdx, PatchSpecializedFieldReq patchSpecializedFieldReq) throws BaseException{
        try {
           profileDao.modifyProfileSpecializedField(userIdx, patchSpecializedFieldReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }




}
