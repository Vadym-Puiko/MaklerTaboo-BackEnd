package com.softserve.maklertaboo.repository.search;


import org.hibernate.search.bridge.TwoWayStringBridge;

public class StringToNumberBridge implements TwoWayStringBridge {

    public static String PADDING_PROPERTY = "padding";
    private int padding = 7; //default

    public String objectToString(Object object) {

        try {
            if(object!=null)
            {
                String rawInteger = ((String) object).toString();
                String decimalPoint="";

                if(rawInteger.matches("\\d*\\.\\d+"))
                {
                    decimalPoint=rawInteger.substring(rawInteger.indexOf("."));
                    rawInteger=rawInteger.substring(0,rawInteger.indexOf("."));
                    System.out.println(decimalPoint);
                    System.out.println(rawInteger);
                }
                if (rawInteger.length() > padding)
                    throw new IllegalArgumentException("Try to pad on a number too big");

                StringBuilder paddedInteger = new StringBuilder();
                for (int padIndex = rawInteger.length(); padIndex < padding; padIndex++)
                {
                    paddedInteger.append('0');
                }
                return paddedInteger.append(rawInteger).append(decimalPoint).toString();
            }
            else {
                return "";
            }
            //return object.toString();
        }
        catch (Exception e) {
            return null;
        }
    }

    public Object stringToObject(String stringValue) {
        return Double.valueOf(stringValue);
    }
}
