package github.Zcy19980412.core;

import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import github.Zcy19980412.Constant.Constant;
import github.Zcy19980412.domain.entity.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class WebService {


    public boolean checkToken(String token){
        //解析token
        if (StringUtils.isBlank(token)) {
            throw new RuntimeException("token为空");
        }
        JWT jwt = JWTUtil.parseToken(token);
        JWTPayload payload = jwt.getPayload();
        JSONObject claimsJson = payload.getClaimsJson();
        String username = claimsJson.getStr(Constant.LOGIN.USERNAME);
        String password = claimsJson.getStr(Constant.LOGIN.PASSWORD);
        Long userId = claimsJson.getLong(Constant.LOGIN.USER_ID);
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || userId == null) {
            return false;
        }
        RequestThreadContext.setCurrentUser(
                User
                        .builder()
                        .id(userId)
                        .username(username)
                        .password(password)
                        .build()
        );
        return true;
    }


}
