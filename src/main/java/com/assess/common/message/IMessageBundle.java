package com.assess.common.message;

import com.assess.common.form.OutputAPIForm;

import java.util.Locale;

/**
 * @Creator 4/7/2025
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/


public interface IMessageBundle {
    void createMsg(OutputAPIForm obj, Locale locale);
    void createMsg(OutputAPIForm obj);
    String getMessage(String key, Locale locale);

}
