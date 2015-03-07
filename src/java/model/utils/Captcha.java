
package model.utils;

import java.util.ArrayList;
import java.util.Calendar;


public class Captcha {
    private final Integer[] arrayEnteros=new Integer[]{1,2,3,4,5,6,7,8,9};
    
    public static ArrayList<Object> generarCaptchaString(){
        ArrayList<Object> lista=new ArrayList<Object>();
        Calendar c=Calendar.getInstance();
        Integer i=c.get(Calendar.MILLISECOND)%10;
        Integer j=Calendar.getInstance().get(Calendar.SECOND)%10;
        
        lista.add(i);
        lista.add(j);
        
        switch (i){
        case 0: lista.add("c3ro");   
        return lista;
        case 1:lista.add("uno");
            return lista;
        case 2:lista.add("dos");
        return lista;
        case 3:lista.add("tr3s");
            return lista;
        case 4:lista.add("cuatr0");
            return lista;
        case 5:lista.add("c1nco");
            return lista;
        case 6:lista.add("s31s");
            return lista;
        case 7:lista.add("s13t3");
            return lista;
        case 8:lista.add("Och0");
            return lista;
        case 9:lista.add("Nu3ve");
            return lista;    
    }
        
        
        
        return null;
        
        
        
    }
    
    
    
    public static Integer convertirLetraANumero(String palabra){
    
    switch (palabra){
        
        case("c3ro"):return(0);
        case("uno"):return (1);
        case("dos"):return (1);    
        case("tr3s"):return (1);
        case("cuatr0"):return (1);
        case("c1nco"):return (1);
        case("s31s"):return (1);
        case("s13t3"):return (1);
        case("0ch0"):return (1);
        case("Nu3ve"):return (1);    
            
    }
    
       return null;
    
}
    
    
    
    
    
}