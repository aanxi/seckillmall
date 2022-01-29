package com.empirefree.gulimall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Title: Catelog2Vo</p>
 * Description：
 * date：2020/6/9 14:41
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Catelog2Vo implements Serializable {

    private static final long serialVersionUID = -3957656981437547787L;
    private String id;

    private String name;

    private String catalog1Id;

    private List<Catalog3Vo> catalog3List;
}
