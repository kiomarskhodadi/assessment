package com.assess.common.message;

import com.assess.common.exception.BusinessCodeException;
import com.assess.common.form.OutputAPIForm;
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
//@Slf4j
public class MessageBundle implements IMessageBundle{

    public String getMessage(String key, Locale locale){
        String retVal ="";
        try{
            retVal = ResourceBundle.getBundle(bundleName,locale).getString(key);
        }catch (Exception e){
//            log.error(e.getMessage());
        }
        return retVal;
    }

    public void createMsg(OutputAPIForm retVal, Locale locale){
        try{
            if(retVal.isSuccess()){
                retVal.setMessage(getMessage(defaultSuccessfulKey,locale));

            }else{
                for(Object exp:retVal.getErrors()){
                    retVal.setMessage(retVal.getMessage() +" "+ getMessage("error."+exp.toString().toLowerCase(),locale));
                }
            }
        }catch (Exception e){
//            log.error(e.getMessage());
        }
    }

    public void createMsg(OutputAPIForm obj){
        createMsg(obj, new Locale("en"));
    }
}
