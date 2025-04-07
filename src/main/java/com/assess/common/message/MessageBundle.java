package com.assess.common.message;

import com.assess.common.exception.BusinessCodeException;
import com.assess.common.form.OutputAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Locale;
import java.util.ResourceBundle;
import static com.assess.common.ConstraintString.bundleName;
import static com.assess.common.ConstraintString.defaultSuccessfulKey;

/**
 * @Creator 4/7/2025
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Service
@Slf4j
public class MessageBundle implements IMessageBundle{

    public String getMessage(String key, Locale locale){
        String retVal ="";
        try{
            retVal = ResourceBundle.getBundle(bundleName,locale).getString(key);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return retVal;
    }

    public void createMsg(OutputAPI obj, Locale locale){
        try{
            if(obj.isSuccess()){
                obj.setMessage(getMessage(defaultSuccessfulKey,locale));

            }else{
                for(BusinessCodeException exp:obj.getErrors()){
                    obj.setMessage(obj.getMessage() +" "+ getMessage("error."+exp.toString().toLowerCase(),locale));
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    public void createMsg(OutputAPI obj){
        createMsg(obj, new Locale("en"));
    }
}
