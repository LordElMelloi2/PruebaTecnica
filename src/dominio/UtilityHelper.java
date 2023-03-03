package dominio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class UtilityHelper {
    
    public static Producto getUltimoElemento(ArrayList<Producto> arrl){
        if(arrl.isEmpty()) return null;
        return arrl.get(arrl.size() - 1);
    }
    
    public static String getFechaDeHoy(String s){
        LocalDate ld = LocalDate.now();
        String fecha = "";
        fecha += ld.getDayOfMonth() < 10 ? "0" + ld.getDayOfMonth() + s : ld.getDayOfMonth() + s;
        fecha += ld.getMonthValue() < 10 ? "0" + ld.getMonthValue() + s : ld.getMonthValue() + s;
        fecha += ld.getYear();
        return fecha;
    }
    
    public static String numeroAleatorio(int limite){
        int numero = (int) (Math.random() * (limite + 1));
        return String.valueOf(numero);
    }
    
    public static String getTiempoAhora(){
        String tiempo = "";
        LocalTime time = LocalTime.now();
        tiempo += time.getHour();
        tiempo += time.getMinute();
        tiempo += time.getSecond();
        return tiempo;
    }
    
    public static Producto getProductoBySKU(ArrayList<Producto> arr ,int sku){
        for(Producto p : arr){
            if(p.getSKU() == sku) return p;
        }
        return null;
    }
    
    public static boolean existProduct(ArrayList<Producto> arr , int sku){
        for(Producto p : arr){
            if(p.getSKU() == sku) return true;
        }
        return false;
    }
    
    public static boolean isan(String s){
        try{
            Double.parseDouble(s);
            return true;
        }catch(NumberFormatException ex){
            return false;
        }
    }
}
