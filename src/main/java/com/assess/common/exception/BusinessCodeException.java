package com.assess.common.exception;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @Creator 4/6/2025
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/


public enum BusinessCodeException {

    SYSTEM_EXCEPTION(1),
    DATABASE_EXCEPTION(2),
    BAD_PARAMETER(3),
    UNDEFINED(2000);

    private int businessCodeException;
    BusinessCodeException(int code) {
        this.businessCodeException = code;
    }

    @JsonValue
    public int getBusinessCodeException() {
        return businessCodeException;
    }

    public void setBusinessCodeException(int businessCodeException) {
        if(businessCodeException > 0 && businessCodeException < 200){
            this.businessCodeException = businessCodeException;
        }else{
            this.businessCodeException = 2000;
        }
    }

}
