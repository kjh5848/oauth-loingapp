package com.example.loginapp.core.util;

import com.example.loginapp.user.KakaoResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Controller
public class KakaoToken {


    public KakaoResponse.TokenDTO getKakaoToken(String code, RestTemplate restTemplate) {
        System.out.println("code22222222 = " + code);
        HttpEntity<MultiValueMap<String, String>> request = createTokenRequest(code);

        ResponseEntity<KakaoResponse.TokenDTO> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                request,
                KakaoResponse.TokenDTO.class);

        return response.getBody();
    }

    private HttpEntity<MultiValueMap<String, String>> createTokenRequest(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "0810ee3432a7cecb3d0186c1a3c6d03c");
        body.add("redirect_uri", "http://localhost:8080/oauth/callback");
        body.add("code", code);

        return new HttpEntity<>(body, headers);
    }



    private HttpEntity<MultiValueMap<String, String>> createUserRequest(String accessToken) {
        System.out.println("accessToken222 = " + accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.add("Authorization", "Bearer " + accessToken);

        return new HttpEntity<>(headers);
    }


    public KakaoResponse.KakaoUserDTO getKakaoUser(String accessToken, RestTemplate restTemplate) {

        HttpEntity<MultiValueMap<String, String>> request = createUserRequest(accessToken);

        ResponseEntity<KakaoResponse.KakaoUserDTO> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                request,
                KakaoResponse.KakaoUserDTO.class);

        return response.getBody();
    }





}
