package fileManager;

import dominio.Producto;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileManager {
    
    private static final String ABSOLUTE_PATH = Paths.get("").toAbsolutePath().toString();
    private String relativePath = "\\res";
    private String databaseName = "\\db.txt";
    private File database;
    
    public FileManager(){
        database = new File(FileManager.ABSOLUTE_PATH + this.relativePath);
        if(!database.exists()){
            if(database.mkdir()){
                System.out.println("Creando carpeta para el txt");
            }else{
                System.out.println("Falo al crear carpeta");
            }
        }
        
        database = new File(FileManager.ABSOLUTE_PATH + this.relativePath + this.databaseName);
        if(!database.exists()){
            try {
                if(database.createNewFile()){
                    System.out.println("txt creado con exito");
                }else{
                    System.out.println("Ya existe el archivo");
                }
            } catch (IOException ex) {
                System.out.println("Ocurrio un error");
                database = null;
            }
        }
    }
    
    public static boolean makeFile(String dir, String nombreArchivo){
        
        if(dir == null || dir.strip().equals("")) dir = "";
        dir = !dir.equals("") ? "\\" + dir : "";
        
        if(nombreArchivo == null || nombreArchivo.strip().equals("")) return false;
        nombreArchivo = '\\' + nombreArchivo;
        
        File file = new File(FileManager.ABSOLUTE_PATH + dir);
         if(!file.exists()){
            if(!file.mkdir()){
                return false;
            }
        }
        
         file = new File(FileManager.ABSOLUTE_PATH + dir + nombreArchivo);
         if(!file.exists()){
            try {
                if(!file.createNewFile()){
                    return false;
                }
            } catch (IOException ex) {
                return false;
            }
        }
        
        return true;
    }
    
    public void writeProducto(Producto prod){
        FileWriter fw;
        PrintWriter pw;
        try {
            fw = new FileWriter(database, true);
            pw = new PrintWriter(fw);
            pw.println(prod.toString());
            pw.close();
            fw.close();
        } catch (IOException ex) {
            System.out.println("Error al escribir el archivo");
        }
    }
    
    public ArrayList<Producto> getProductos(){
        ArrayList<Producto> prods = new ArrayList<>();
        Producto prod;
        
        String currentLine;
        
        FileReader fr;
        BufferedReader br;
        
        try {
            fr = new FileReader(database);
            br = new BufferedReader(fr);
            
            
            while((currentLine = br.readLine()) != null){
                prod = new Producto();
                
                String[] prodAttr = currentLine.split("\\|");        
                
                prod.setSKU(Integer.parseInt(prodAttr[0]));
                prod.setNombre(prodAttr[1]);
                prod.setUnidades(Integer.parseInt(prodAttr[2]));
                prod.setPrecio(Float.parseFloat(prodAttr[3]));
                
                prods.add(prod);
            }
            
            br.close();
            fr.close();
            
        } catch (FileNotFoundException ex) {
            System.out.println("Ocurrio un erro al leer el archivo");
        } catch (IOException ex) {
            System.out.println("Ocurrio un error al leer el archivo");
        }
        
        return prods;
    }
    
    public boolean updateProducto(Producto prod){
        String filasIniciales = "";
        String filasFinales = "";
        boolean primeraSeccion = true;
        String textoFinal = "";
        
        String currentLine = "";
        
        FileReader fr;
        BufferedReader br;
        
        try{
            fr = new FileReader(database);
            br = new BufferedReader(fr);
            
            while((currentLine = br.readLine()) != null){
                String[] prodAttr = currentLine.split("\\|");
                if(prod.getSKU() != Integer.parseInt(prodAttr[0]) & primeraSeccion)
                    filasIniciales += filasIniciales.equals("") ? currentLine : "\n" + currentLine;
                else if(prod.getSKU() == Integer.parseInt(prodAttr[0]))
                    primeraSeccion = false;
                else
                    filasFinales += filasFinales.equals("") ? currentLine : "\n" + currentLine;
            }
            
            br.close();
            fr.close();
            
        } catch (FileNotFoundException ex) {
            return false;
        } catch (IOException ex) {
            return false;
        }
        
        textoFinal = filasIniciales.equals("") ? "" : filasIniciales + "\n";
        textoFinal += prod.toString();
        textoFinal += filasFinales.equals("") ? "" : "\n" + filasFinales + "\n";
        
        //Seccion de escritura
        FileWriter fw;
        PrintWriter pw;
        try {
            fw = new FileWriter(database, false);
            pw = new PrintWriter(fw);
            pw.print(textoFinal);
            pw.close();
            fw.close();
        } catch (IOException ex) {
            return false;
        }
        
        return true;
    }
}
