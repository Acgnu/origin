package com.acgnu.origin.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 登陆凭证, 储存登陆后的基本信息
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginCredential {
    private int id;
    private String uname;
    private String nick;
    private String accessToken;
}
