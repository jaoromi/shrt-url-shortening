# SHRT URL Shortening service
이 서비스는 아래 논문을 참고하여 만들어 졌습니다.

윤수진, 박정은, 최창국, 김승주, "_**SHRT : 유사 단어를 활용한 URL 단축 기법**_" , 한국통신학회 논문지<br>

http://dx.doi.org/10.7840/kics.2013.38B.6.473

# Summary
가장 큰 장점은 URL 접속의 시도하는 사람이 직관적으로 원본 URL 을 유추할 수 있다는 점입니다. 위의 연구에 따르면 대략 평균 10자 정도의 길이가 도출 됩니다(물론 특성상 67바이트까지 허용하고 있습니다).<br>
이 방식은 원본 URL 의 출처를 인식할 수 있는 2,3차 도메인의 자음을 추출해 1차 분류로 사용하는 방식입니다. Short URL 을 확인하는 사람이 어디로 접속되는지를 추측 가능하도록 하여 안정성을 높이기 위함입니다.<br>
URL을 8자 이하로 조정하기 위해 인식성을 떨어지더라도 원본에서 추출된 연관키워드를 4자로 제한하였습니다.<br>  

구현된 서비스의 Shorting URL 의 구조는<br>
**1-4(가변):** 연관키워드. 도메인 자음이 4자 이상일 경우 앞의 4글자로 사용, 2자 이하일 경우 자모 포함 앞에서 4자.<br>
**5:** 구분기호. / 를 사용하여 연관키워드와의 가독성 향상<br>
**6-7:** 해쉬 인덱스<br>
**8:** 해쉬 내의 카운드 인덱스<br>

### API Document
https://jaoromi.github.io/shrt-url-shortening/shortening-url.html

### 유사 단어의 개선
유사단어의 길이를 4글자로 줄이는 대신, 가독성을 높이기 위하여, 사전기반 유사 단어를 함께 사용하도록 하였습니다.<br>

### URL 등록 Test file HTML
src/main/resources/static/shrot-url-post-form.html 을 브라우저에서 실행시켜서 확인할 수 있습니다.<br>

### 해결안된 이슈
1. embedded mongodb 의 Graceful shutdown<br>

# 관리자 계정 관리 API 문서
https://jaoromi.github.io/shrt-url-shortening/admin-user.html