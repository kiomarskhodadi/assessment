package com.assess.common.form;

import com.assess.common.exception.BusinessCodeException;
import lombok.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @Creator 4/7/2025
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@AllArgsConstructor
@Getter
@Setter
public abstract class ABaseForm implements Serializable {

    private ArrayList<BusinessCodeException> errors = new ArrayList<>();
    private String message = "" ;
    private boolean success = true;
    private boolean nextPage = false;

    public ABaseForm(){
        setErrors( new ArrayList<>());
        setMessage("");
        setSuccess(true);
        setNextPage(false);
    }

}
