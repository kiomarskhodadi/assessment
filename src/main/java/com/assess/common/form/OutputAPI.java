package com.assess.common.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @Creator 4/7/2025
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@AllArgsConstructor
@Getter
@Setter
public class OutputAPI <T> extends  ABaseForm {
    private T data;
    public OutputAPI(){
        super();
    }
}
