package com.assess.common.utilities;

public class GeneralUtility {
    public static Integer convertToInteger(String str){
        Integer retVal = null;
        try{
            retVal = Integer.parseInt(str);
        }catch (Exception e){
            retVal = null;
        }
        return retVal;
    }

    public static Float convertToFloat(String str){
        Float retVal = null;
        try{
            retVal = Float.parseFloat(str);
        }catch (Exception e){
            retVal = null;
        }
        return retVal;
    }

}
