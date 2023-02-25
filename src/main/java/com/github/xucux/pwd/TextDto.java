package com.github.xucux.pwd;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * @descriptions:
 * @author: xu-ux
 * @version: 1.0
 * <pre> </pre>
 */
@Getter
@Setter
public class TextDto {

    private String icon;

    private Integer t;

    private String id;

    @JSONField(name = "name_all")
    private String nameAll;

    private String size;

    private String time;

    private String duan;

    @JSONField(name = "p_ico")
    private Integer pIco;





}
