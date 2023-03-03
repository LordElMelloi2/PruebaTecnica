package main;

import dominio.Producto;
import dominio.UtilityHelper;
import fileManager.FileManager;
import fileManager.ticketManager.TicketManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String [] args){
        
        ArrayList<Producto> productos;
        FileManager fm = new FileManager();
        
        productos = fm.getProductos();
        
        String comando = "";
        
        //Para leer los comandos
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println("Bienvenido!");
        while(!comando.toLowerCase().equals("salir")){
            System.out.println("\nPara salir escriba el comando salir");
            System.out.println("Para agregar un producto use el comando: Agregar");
            System.out.println("Para mostrar productos disponibles use el comando: Mostrar");
            System.out.println("Para comprar un producto use el comando: Comprar");
            System.out.println("Para modificar un producto use el comando: Modificar");
            
            try{
                comando = reader.readLine();
                comando = comando.toLowerCase();
                if(comando.equals("salir")) continue;
            } catch(IOException e){
                System.out.println("Hubo un error al leer el comando");
                continue;
            }
            
            String datos = "";
            Producto p_tmp = new Producto();
            if(comando.equals("agregar")){
                int ultimoSKU = UtilityHelper.getUltimoElemento(productos) != null ? UtilityHelper.getUltimoElemento(productos).getSKU() + 1 : 1;
                try {
                    p_tmp.setSKU(ultimoSKU);
                    System.out.println("Por favor introduce el nombre del producto: ");
                    datos = reader.readLine();
                    p_tmp.setNombre(datos);
                    
                    datos = "";
                    while(true){
                        System.out.println("Por favor introduce el numero de existencias: ");
                        datos = reader.readLine();
                        if(!UtilityHelper.isan(datos)) System.out.println("Escribar un numero por favor");
                        else break;
                    }
                    p_tmp.setUnidades(Integer.parseInt(datos));
                    
                    datos = "";
                    while(true){
                        System.out.println("Por favor introduce el precio del producto: ");
                        datos = reader.readLine();
                        if(!UtilityHelper.isan(datos)) System.out.println("Escribar un numero por favor");
                        else break;
                    }
                    p_tmp.setPrecio(Float.parseFloat(datos));             
                    
                    productos.add(p_tmp);
                    fm.writeProducto(p_tmp);
                } catch (IOException ex) {
                    System.out.println("Error al leer el dato");
                    continue;
                }
            }
            else if(comando.equals("mostrar")){
                System.out.println("Productos");
                for(Producto p : productos){
                    if(p.getUnidades() > 0)
                        System.out.println("SKU: " + p.getSKU()+ " | Nombre: " + p.getNombre() + " | Unidades: " + p.getUnidades() + " | Precio: " + p.getPrecio());
                }
            }
            else if(comando.equals("modificar")){
                try {
                    datos = "";
                    while(true){
                        System.out.println("Por favor introduce el SKU del producto a modificar: ");
                        datos = reader.readLine();
                        if(!UtilityHelper.isan(datos)) System.out.println("Escribar un numero por favor");
                        else break;
                    }
                    
                    p_tmp.setSKU(Integer.parseInt(datos));
                    if(!UtilityHelper.existProduct(productos, p_tmp.getSKU())){
                        System.out.println("El producto seleccionado no existe!");
                        continue;
                    }
                    
                    System.out.println("Por favor introduce el nombre del producto: ");
                    datos = reader.readLine();
                    p_tmp.setNombre(datos);
                    
                    datos = "";
                    while(true){
                        System.out.println("Por favor introduce el numero de existencias: ");
                        datos = reader.readLine();
                        if(!UtilityHelper.isan(datos)) System.out.println("Escribar un numero por favor");
                        else break;
                    }
                    p_tmp.setUnidades(Integer.parseInt(datos));
                    
                    datos = "";
                    while(true){
                        System.out.println("Por favor introduce el precio del producto: ");
                        datos = reader.readLine();
                        if(!UtilityHelper.isan(datos)) System.out.println("Escribar un numero por favor");
                        else break;
                    }
                    p_tmp.setPrecio(Float.parseFloat(datos));
                    
                    fm.updateProducto(p_tmp);
                    productos = fm.getProductos();
                } catch (IOException ex) {
                    System.out.println("Error al leer el dato");
                    continue;
                }
            }
            else if(comando.equals("comprar")){
                try {
                    datos = "";
                    while(true){
                        System.out.println("Por favor introduce el SKU del producto a comprar: ");
                        datos = reader.readLine();
                        if(!UtilityHelper.isan(datos)) System.out.println("Escribar un numero por favor");
                        else break;
                    }
                    //Si se mete un valor que deje en negarivo entonces no funcionara el programa    
                    int SKU = Integer.parseInt(datos);
                    Producto p_comprado = UtilityHelper.getProductoBySKU(productos, SKU);
                    if(p_comprado == null){
                        System.out.println("No existe el elemento seleccionado!");
                        continue;
                    }
                    
                    datos = "";
                    while(true){
                        System.out.println("Por favor introduce la cantidad a comprar: ");
                        datos = reader.readLine();
                        if(!UtilityHelper.isan(datos)) System.out.println("Escribar un numero por favor");
                        else break;
                    }        
                    int unidades = Integer.parseInt(datos);
                    
                    if(unidades > p_comprado.getUnidades()) unidades = p_comprado.getUnidades();
                    
                    System.out.println("Total: " + unidades + " " + p_comprado.getNombre() + " x " + p_comprado.getPrecio() + " = " + unidades * p_comprado.getPrecio());
                    TicketManager.crearTicket(p_comprado, unidades);
                    p_comprado.setUnidades(p_comprado.getUnidades() - unidades);
                    fm.updateProducto(p_comprado);
                    productos = fm.getProductos();
                } catch (IOException e) {
                    System.out.println("Ocurrio un error en la compra");
                }
            }
            else if(!comando.equals("salir")){
                System.out.println("No se reconocio el comando!\n");
            }
            
        }
        
    }
}
