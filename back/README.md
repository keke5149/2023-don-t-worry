# 돈워리(Be Happy) -backend <br><br>
"일정 및 소비 데이터를 활용한 예산 사용량 예측 AI 기반 모바일 웹 서비스 "<br><br><br>

## :mag:목차<br><br>
1.설치 및 실행<br><br>
2.기술 스택<br><br>
3.디렉토리 구조<br><br>
4.주요 모듈<br>
&nbsp;&nbsp;&nbsp;&nbsp;-user<br>
&nbsp;&nbsp;&nbsp;&nbsp;-account<br>
&nbsp;&nbsp;&nbsp;&nbsp;-budgetguid<br>
&nbsp;&nbsp;&nbsp;&nbsp;-challenge<br>
&nbsp;&nbsp;&nbsp;&nbsp;-global<br>
&nbsp;&nbsp;&nbsp;&nbsp;-home<br>
&nbsp;&nbsp;&nbsp;&nbsp;-schedule<br>
&nbsp;&nbsp;&nbsp;&nbsp;-statics<br><br><br>


## 1.설치 및 실행<br><br>
&nbsp;&nbsp;-프로젝트 clone<br><br>
```
git clone http://github.com/2023_DontWorry
cd 2023_DontWorry/src
```
<br><br>
&nbsp;&nbsp;-프로젝트 빌드<br><br>
```
./gradlew build
```
<br><br>
&nbsp;&nbsp;-실행<br><br>
```
./gradlew bootRun
```
<br><br><br>
## 2.기술 스택<br><br>
-개발 도구<br><br>
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"><img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"><br><br><br>
-배포<br><br>
<img src="https://img.shields.io/badge/Amazon AWS-232F3E?style=flat-square&logo=amazonaws&logoColor=white"/><img src="https://img.shields.io/badge/Amazon%20EC2-FF9900?style=for-the-badge&logo=Amazon%20EC2&logoColor=white"><br>
-AI<br><br>

|머신러닝|easyOCR|
|------|------|
|![사이킷런](https://github.com/keke5149/2023_DontWorry/assets/110442829/6548e478-007d-40ac-9e73-0a6478d66cd0)|![easyocr](https://github.com/keke5149/2023_DontWorry/assets/110442829/d999b2a5-2aa5-470b-b698-369d7365733f)|
|사용자의 데이터를 사용해 Scikit Learn 머신러닝 라이브러리로 다중선형회귀모델을 훈련시킨다.|easyocr를 사용해 사용자의 영수증에서 결제 장소, 금액을 추출한다.|<br><br><br>

## 3.디렉토리 구조<br><br>
```
DontWorry
├─ users                   
|  ├─ domain
|  ├─ dto
|  ├─ exception
|  ├─ repository
|  └─ service
├─ accountbook                   
│  ├─ controller
│  ├─ domain
│  ├─ dto
|  ├─ repository
|  └─ service
├─ budgetguid
│  ├─ controller
│  ├─ domain
│  ├─ dto
|  ├─ repository
|  └─ service
├─ challenge
│  ├─ controller
│  ├─ domain
│  ├─ dto
|  ├─ repository
|  └─ service
├─ home
│  ├─ controller
│  ├─ dto
|  └─ service
├─ schedule
│  ├─ controller
│  ├─ domain
│  ├─ dto
|  ├─ repository
|  └─ service
├─ statistic
│  ├─ controller
│  ├─ domain
│  ├─ dto
|  ├─ repository
|  └─ service
└─ DonTWorryApplication.java
```
<br><br>
## 4.주요 모듈<br><br><br>
 * ```AccountBook```:가계부 등록<br>
 * ```Budget```:예산 등록<br>
 * ```CategoryGoalTracker```:카테고리 횟수 지정<br>
 * ```DailySpending```:하루 지출 제한<br>
 * ```MoneyCollector```:목돈 모으기<br>
 * ```WithFriends```:사용량 공유<br>
 * ```Statistics```:통계<br>



















