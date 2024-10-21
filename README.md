<h1>🎞Movie Review</h1>
<h2>📗<a href="https://blog.naver.com/shin_418" style="03C75A">개인 공부/개인 프로젝트 기록 </a></h2>
<h1>프로젝트 소개 및 주요기능</h1>


- 'Movie Review'는 영화에 대한 리뷰를 작성하고, 인기 영화의 굿즈 상품 판매 및 다양한 이벤트 정보를 제공하는 웹사이트입니다.
- PortOne API를 이용한 KG이니시스와 카카오페이 결제 기능 구현
- 권한 제어기능 구현
- Spring Security를 이용한 로그인 기능 구현
- Spring Security를 이용한 회원가입 기능 구현
- NAVER의 SMTP 서버를 이용한 메일로 비밀번호 찾기 기능 구현
- CRUD 게시판,서칭,페이징 기능 구현
- Spring Security와 Thymeleaf 를 사용하여 현재 인증된 사용자의 정보로 글쓰기 구현
- Spring Security와 Thymeleaf를 사용해 글을 작성한 당사자만 수정/삭제가 가능하게 구현 
- 각 영화마다 동적 게시판 기능 구현
- 사용자 인증을 바탕으로 비동기 요청을 통한 댓글 (작성, 수정, 삭제) 기능 구현 
- 댓글 작성자만 수정/삭제를 가능하도록 기능 구현 
- 사용자 인증을 바탕으로 비동기 요청을 통한 대댓글 (작성, 수정, 삭제) 기능 구현
- 대댓글 작성자만 수정/삭제를 가능하도록 기능 구현





<div align="center">
  <h3>🎉Tech Stack🎉</h3>
  <h4>🌈Platforms & Language🌈</h4>
  <div>
    <img src="https://img.shields.io/badge/JAVA-FF7328?style=flat&logo=JAVA&logoColor=white"/>
    <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat&logo=JavaScript&logoColor=white"/>
    <img src="https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=HTML5&logoColor=white"/>
    <img src="https://img.shields.io/badge/CSS3-1572B6?style=flat&logo=CSS3&logoColor=white"/>
  </div>
  <h4>🧰Tools🧰</h4>
  <div>
    <img src="https://img.shields.io/badge/spring-6DB33F?style=flat&logo=spring&logoColor=white"/>
    <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat&logo=Spring Boot&logoColor=white"/>
    <img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=flat&logo=Thymeleaf&logoColor=white"/>
  </div>
  <div>
    <img src="https://img.shields.io/badge/Mybatis-0467DF?style=flat&logo=Mybatis&logoColor=white"/>
    <img src="https://img.shields.io/badge/Gradle-02303A?style=flat&logo=Gradle&logoColor=white"/>
    <img src="https://img.shields.io/badge/bootstrap-%238511FA.svg?style=for-the-badge&logo=bootstrap&logoColor=white"/>
    <img src="https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white"/>
  </div>
</div>





<h1>💻화면구성</h1>
<div align="center">
<img width="30%" src="https://github.com/user-attachments/assets/adcd5582-f1b2-411b-87d4-458c289f1dea">
<img width="30%" src="https://github.com/user-attachments/assets/34dbfd10-9b8d-4479-9f47-b4e959b98142">
<img width="30%" src="https://github.com/user-attachments/assets/718c2f0e-7ff1-48cc-ac9c-99f5fa9a2a9b">
<img width="30%" src="https://github.com/user-attachments/assets/82f119e7-b597-449f-b07c-25df52b3ca3f">
<img width="30%" src="https://github.com/user-attachments/assets/ae9c3f4c-d8bf-4594-b310-b113eb237751">
<img width="30%" src="https://github.com/user-attachments/assets/4b85438e-d0a2-47c0-8d86-4bbe202a0974">
<img width="30%" src="https://github.com/user-attachments/assets/20dbb83b-6d43-48a3-a348-dd251675d68d">
<img width="30%" src="https://github.com/user-attachments/assets/8ce727b5-c563-4fc1-ab28-fbea3dd0230e">
<img width="30%" src="https://github.com/user-attachments/assets/fca3dd0b-b48b-4e10-8a0c-a91edd676f0e">
<img width="30%" src="https://github.com/user-attachments/assets/40f3ddfd-4e12-41e6-a1a6-ca373602bdb8">
<img width="30%" src="https://github.com/user-attachments/assets/bca7e180-8482-4ea2-a4a2-98ca64798e0e">
<img width="30%" src="https://github.com/user-attachments/assets/aa4d7cde-0f53-45ea-b757-cc3cc08ada67">
</div>


# ✏주요기능

### 메인페이지 
- 각 기능(도메인)별 이동, 로그인, 회원가입, 게시글, 굿즈스토어, 이벤트페이지 이동 가능

### 로그인, 회원가입 페이지 
- Spring Security를 이용한 로그인 구현
- Spring Security를 이용한 회원가입 구현
- Naver SMTP서버를 활용한 메일로 비밀번호 찾기 구현

### 결제페이지 
- javascript를 사용하여 수량만큼 총 상품 금액이 변동하는 것을 동적으로 구현
- PortOne API를 이용한 KG이니시스와 카카오페이 결제 기능 구현
- 결제완료 시 결제된 상세주문내역을 Thymeleaf 구현

### 게시판 페이지 
- 각 영화마다 동적게시판 구현
- 삭제 및 수정 기능 구현
- 서칭/필터링/페이징 기능 구현 
- Spring Security와 Thymeleaf 를 사용하여 현재 인증된 사용자의 정보로 글쓰기 구현
- 수정/삭제도 Spring Security와 Thymeleaf를 사용해 글을 작성한 당사자만 수정/삭제가 가능하게 구현

### 댓글 기능 
- 사용자 인증을 바탕으로 비동기 요청을 통한 댓글 수정/삭제 기능 구현
- 댓글 작성자만 수정/삭제를 가능하도록 구현
- 사용자 인증을 바탕으로 비동기 요청을 통한 대댓글 수정/삭제 기능 구현
- 대댓글 작성자만 수정/삭제를 가능하도록 구현
- parenId를 이용해 계층 관계를 유지하여 대댓글 기능 구현 
