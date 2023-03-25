# stagram
+ #### 소개
> 인스타그램 클론 코딩.

+ #### 개발 환경
  + Window 11 
  + IntelliJ

+ #### 사용 스택
  + Java(Spring)
  + JPA
  + MySQL(AWS RDS)
  + Thymeleaf
  + HTML
  + CSS
  + JS, JQuery

+ #### 구현한 기능
  + 회원가입
  + 로그인
  + 회원 정보 수정
  + 회원 탈퇴
  + 프로필 사진 업로드/수정/삭제
  + 게시글(사진) 업로드/수정/삭제
  + 게시글 좋아요
  + 게시글 댓글
  + 팔로우/팔로워
  + 인피니티 스크롤(무한 스크롤)

+ #### 디렉토리 구조
  + DTO
  + Entity
  + Controller
  + Service
  + Repository
  
+ #### 화면 구성
   + 로그인
  ![로그인 화면](https://user-images.githubusercontent.com/91775452/227707909-3d5b53b3-c351-4349-b629-d9e5d6b4d317.png)
  
  + 타임라인
  ![타임라인 화면](https://user-images.githubusercontent.com/91775452/227707974-d48d9a6d-703e-475d-879c-95ab44db873b.png)
  
  + 프로필 페이지
  ![개인페이지화면](https://user-images.githubusercontent.com/91775452/227708008-a0121f0c-c973-4c76-8a6b-b921885647aa.png)
  
  + 회원수정 페이지
  ![회원정보 수정 화면](https://user-images.githubusercontent.com/91775452/227708105-92794ac8-cd82-4a59-9a7a-85acdee76f2b.png)

  + 회원 탈퇴 페이지
 ![회원탈퇴페이지](https://user-images.githubusercontent.com/91775452/227708108-aa02602e-6bd6-4cf9-8044-ef10697dfa11.png)
 
  + 게시글 업로드
  ![게시글 업로드 화면](https://user-images.githubusercontent.com/91775452/227708358-2f30c69c-6c0d-4876-a7ae-ff6835c47a55.png)


+ ### 문제
  + 타임라인 페이지의 게시글에 좋아요 하나도 없는 상태에서 좋아요를 누르면 리액션, 댓글 섹션이 사라짐.
  + 게시글에 업로드되는 사진은 한 장씩만 가능.
  + 무한 스크롤 페이징 로직의 비효율성.
