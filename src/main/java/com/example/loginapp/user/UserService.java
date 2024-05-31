package com.example.loginapp.user;

import com.example.loginapp.core.util.KakaoToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;


@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final KakaoToken kakaoToken;

    @Transactional
    public void 회원가입(String username, String password, String email) {
        User user = User.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();
        userRepository.save(user);
    }

    public User 로그인(String username, String password) {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new RuntimeException("아이디가 없습니다");
        }else{
            if(user.getPassword().equals(password)){
                return user;
            }else{
                throw new RuntimeException("비밀번호가 틀렸습니다");
            }
        }
    }

    public User 카카오로그인(String code) {
        RestTemplate restTemplate = new RestTemplate();

        KakaoResponse.TokenDTO kakaoResponse = kakaoToken.getKakaoToken(code, restTemplate);
        KakaoResponse.KakaoUserDTO kakaoUser = kakaoToken.getKakaoUser(kakaoResponse.getAccessToken(), restTemplate);

        String username = "kakao_" + kakaoUser.getId();
        User userPS = userRepository.findByUsername(username);

        if (userPS != null) {
            System.out.println("어? 유저가 있네? 강제로그인 진행");
            return userPS;
        } else {
            System.out.println("어? 유저가 없네? 강제회원가입 and 강제로그인 진행");
            User user = User.builder()
                    .username(username)
                    .password(UUID.randomUUID().toString())
                    .email(kakaoUser.getProperties().getNickname() + "@nate.com")
                    .provider("kakao")
                    .build();
            return userRepository.save(user);
        }
    }
}
