package mackerel.dontworry.user.service;

import lombok.RequiredArgsConstructor;
import mackerel.dontworry.user.domain.OAuthAttributes;
import mackerel.dontworry.user.domain.User;
import mackerel.dontworry.user.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    //OAuth2UserRequest OAuth2 로그인 요청을 나타내는 객체
    //OAuth2User OAuth2 로그인에 성공한 사용자 정보를 나타내는 인터페이스
    private final UserRepository userRepository;
    //private final HttpSession httpSession;

    //loadUser는 OAuth2User 반환, 해당 객체는 사용자의 인증 정보와 권한 정보를 포함한다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService(); //DefaultOAuth2User 서비스를 통해 User 정보를 가져와야 하기 때문에 대리자 생성
        OAuth2User oAuth2User = delegate.loadUser(userRequest); // OAuth 서비스(kakao, google, naver)에서 가져온 사용자 정보를 담고있음

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 로그인 중인 OAuth 서비스 구분용 - google
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName(); //OAuth2 로그인 진행시 키가 되는 필드값 (pk), 구글의 기본 코드는 "sub"
        Map<String, Object> attributes = oAuth2User.getAttributes(); // OAuth 서비스의 유저 정보들

        User user = OAuthAttributes.extract(registrationId, attributes); // registrationId에 따라 유저 정보를 통해 공통된 Member 객체로 만들어 줌
        user.updateProvider(registrationId);
        saveOrUpdate(user);
        //httpSession.setAttribute("user", new SessionUser(user));

        Map<String, Object> customAttribute = customAttribute(attributes, userNameAttributeName, user, registrationId);

        // 로그인 유저 리턴
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleValue())),
                customAttribute,
                userNameAttributeName);
    }
    private Map customAttribute(Map attributes, String userNameAttributeName, User user, String registrationId) {
        Map<String, Object> customAttribute = new LinkedHashMap<>();
        customAttribute.put(userNameAttributeName, attributes.get(userNameAttributeName));
        customAttribute.put("name", user.getName());
        customAttribute.put("provider", registrationId);
        customAttribute.put("email", user.getEmail());
        return customAttribute;

    }

    private void saveOrUpdate(User user) {
        User newUser = userRepository.findByEmailAndProvider(user.getEmail(), user.getProvider())
                .map(m -> m.updateEmail(user.getEmail())) // OAuth 서비스 사이트에서의 유저 정보 변경사항 update
                .orElse(User.of(user.getName(), user.getEmail(), user.getProvider(), user.getProviderId()));
        userRepository.save(newUser);
    }
}
