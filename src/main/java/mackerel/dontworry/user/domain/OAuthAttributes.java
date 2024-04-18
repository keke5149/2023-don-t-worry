package mackerel.dontworry.user.domain;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

//OAuth2UserService를 통해 가져온 OAuth2User의 속성을 담는 클래스
public enum OAuthAttributes {

    GOOGLE("google", (attributes) -> {
        return User.of(
                (String) attributes.get("name"),
                (String) attributes.get("email"),
                "google",
                "google_"+ attributes.get("sub")
        );
    });

    private final String registrationId;
    private final Function<Map<String, Object>, User> of;

    OAuthAttributes(String registrationId, Function<Map<String, Object>, User> of) {
        this.registrationId = registrationId;
        this.of = of;
    }

    // provider가 일치하는 경우에만 apply를 호출하여 (google) user를 반환
    public static User extract(String registrationId, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> registrationId.equals(provider.registrationId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .of.apply(attributes);
    }
}
