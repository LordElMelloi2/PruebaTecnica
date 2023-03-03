package dominio;

public class Producto {
    private int SKU;
    private String nombre;
    private int unidades;
    private float precio;

    public Producto() {
    }

    public Producto(int SKU, String nombre, int unidades, float precio) {
        this.SKU = SKU;
        this.nombre = nombre;
        this.unidades = unidades;
        this.precio = precio;
    }

    public int getSKU() {
        return SKU;
    }

    public void setSKU(int SKU) {
        this.SKU = SKU;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return SKU + "|" + nombre + "|" + unidades + "|" + precio;
    }
    
    
}
