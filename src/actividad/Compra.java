package actividad;

import java.util.ArrayList;
import java.util.List;

public class Compra {

    private List<Producto> productos;

    public Compra() {
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public void agregarIngredientes(int indiceProducto, List<Ingredientes> ingredientes) {
        if (indiceProducto >= 0 && indiceProducto < productos.size()) {
            Producto producto = productos.get(indiceProducto);
            producto.agregarIngredientes(ingredientes);
        } else {
            System.out.println("Índice de producto no válido.");
        }
    }

    public double calcularTotal() {
        double total = 0.0;
        for (Producto producto : productos) {
            total += producto.getPrecio();
        }
        return total;
    }

    public List<Producto> getProductos() {
        return productos;
    }
}
