package com.empirefree.sso.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @program: renren-fast
 * @description:
 * @author: huyuqiao
 * @create: 2022/02/05 11:28
 */

@ToString
@Data
public class User {

    private String user;

    private String username;

    private String password;

    private String url;
}
