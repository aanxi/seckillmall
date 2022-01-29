package com.empirefree.gulimall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>Title: Catalog3Vo</p>
 * Description：
 * date：2020/6/9 14:42
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Catalog3Vo implements Serializable {

    private static final long serialVersionUID = 1035987076736866455L;
    private String id;

    private String name;

    private String catalog2Id;
}
