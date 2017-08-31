package com.jaoromi.urlshortening.admin.dto;

import lombok.Data;

@Data
public class AdminUserDTO {

    /**
     * Login ID
     */
    private String id;

    /**
     * 로그인 패스워드. 생성 시에만 사용됩니다.
     */
    private String password;

    /**
     * 관리자 이름
     */
    private String name;

    /**
     * 관리자 연락 전화번호
     */
    private String phone;

    /**
     * 관리자 이메일
     */
    private String email;

}
