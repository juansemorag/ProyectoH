package actividad;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Clase Producto
class Producto {

    private String nombre;
    private double precio;
    private List<Ingredientes> ingredientesAdicionales;
    private List<String> ingredientesNormales;

    public Producto(String nombre, double precio, List<String> ingredientes) {
        this.nombre = nombre;
        this.precio = precio;
        this.ingredientesNormales = ingredientes;
    }

    // Getters y setters (opcional)
    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public List<String> getIngredientes() {
        return ingredientesNormales;
    }

    public void agregarIngredientes(List<Ingredientes> ingredientes) {
        if (ingredientesAdicionales == null) {
            ingredientesAdicionales = new ArrayList<>();
        }
        ingredientesAdicionales.addAll(ingredientes);
    }

    public List<Ingredientes> getIngredientesAdicionales() {
        return ingredientesAdicionales;
    }

}
