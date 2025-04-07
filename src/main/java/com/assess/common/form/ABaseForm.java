package com.assess.common.form;

import com.assess.common.exception.BusinessCodeException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.apache.bcel.classfile.CodeException;

import java.util.ArrayList;

/**
 * @Creator 4/7/2025
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@AllArgsConstructor
@Getter
@Setter
public abstract class ABaseForm {
    private ArrayList<BusinessCodeException> errors;
    private String message  = "";
    private boolean success;
    private boolean nextPage;

    ABaseForm(){
        this(new ArrayList<>(),"",true,false);
    }
}
