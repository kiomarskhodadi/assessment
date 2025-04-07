package com.assess.common.message;

import com.assess.common.form.OutputAPI;

import java.util.Locale;

/**
 * @Creator 4/7/2025
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/


public interface IMessageBundle {
    void createMsg(OutputAPI obj, Locale locale);
    void createMsg(OutputAPI obj);
    String getMessage(String key, Locale locale);

}
