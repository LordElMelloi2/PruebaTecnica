package fileManager.ticketManager;

import dominio.Producto;
import dominio.UtilityHelper;
import fileManager.FileManager;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

public class TicketManager {
    
    private static final String TEXTO_INICIAL = ""
            + "Ticket\n"
            + "Este ticket desglosa la compra del siguiente producto\n";
    private static final String ABSOLUTE_PATH = Paths.get("").toAbsolutePath().toString();
    
    private static final String dir = "tickets";
    private static final String nombreGeneral = "ticket.txt";
    
    public static boolean crearTicket(Producto prod, int cantidad){
        String nombreTickActual = UtilityHelper.getFechaDeHoy("") + "_" + UtilityHelper.getTiempoAhora() + "_" + nombreGeneral;
        boolean exito = FileManager.makeFile(dir, nombreTickActual);
        if(exito){
            File file = new File(ABSOLUTE_PATH + "\\" + dir + "\\" + nombreTickActual);
            FileWriter fw;
            PrintWriter pw;
            try {
                fw = new FileWriter(file, true);
                pw = new PrintWriter(fw);
                
                String tmp_texto = "\nProducto: " + prod.getNombre() + " | Cantidad: " + cantidad + " x " + prod.getPrecio() + " = " + cantidad * prod.getPrecio();
                tmp_texto += "\nFecha:" + UtilityHelper.getFechaDeHoy("-");
                pw.println(TEXTO_INICIAL + tmp_texto);        
                
                pw.close();
                fw.close();
            } catch (IOException ex) {
                return false;
            }
        }
        
        return exito;
    }
}
