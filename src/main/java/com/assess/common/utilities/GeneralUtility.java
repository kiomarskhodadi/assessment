package com.assess.common.utilities;

public class GeneralUtility {
    public static Integer convertToInteger(String str){
        Integer retVal = null;
        try{
            retVal = Integer.parseInt(str);
        }catch (Exception e){
            retVal = -1;
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
    public static String convertLength(String str){
        String retVal="";
        try{
            retVal = str.substring(0,Math.min(str.length(),254));
        }catch(Exception e){
            retVal = "";
        }
        return retVal;
    }

}
