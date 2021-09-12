package com.empirefree.gulimall.search.dao;

import lombok.Data;

/**
 * @program: gulimall-search
 * @description:
 * @author: huyuqiao
 * @create: 2021/08/01 15:15
 */
@Data
public class Account {

    private int accountNumber;

    private int balance;

    private String firstname;

    private String lastname;

    private int age;

    private String gender;

    private String address;

    private String employer;

    private String email;

    private String city;

    private String state;
}
