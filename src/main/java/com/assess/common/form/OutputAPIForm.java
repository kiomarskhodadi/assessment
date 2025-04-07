package com.assess.common.form;

import com.assess.common.exception.BusinessCodeException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * @Creator 4/7/2025
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@AllArgsConstructor
@Getter
@Setter
public class OutputAPIForm<T>  extends ABaseForm{

    private T data;
    public OutputAPIForm(){
        setErrors( new ArrayList<>());
        setMessage("");
        setSuccess(true);
        setNextPage(false);

    }

}
