package actividad;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Clase GestorProductos
class GestorProductos {

    private List<Producto> productos;
    private List<Ingredientes> ingredientes;

    public GestorProductos() {
        this.productos = new ArrayList<>();
        this.ingredientes = new ArrayList<>();
    }

    // Método para cargar los productos desde el archivo CSV
    public void cargarProductosDesdeCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            String categoriaActual = null;
            boolean primeraLinea = true;

            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }

                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                if (line.contains(",")) {
                    String[] partes = line.split(",");
                    if (partes.length >= 3) {
                        String nombre = partes[0].trim();
                        double precio = Double.parseDouble(partes[1].trim());
                        List<String> ingredientes = new ArrayList<>();
                        for (int i = 2; i < partes.length; i++) {
                            ingredientes.add(partes[i].trim());
                        }
                        Producto producto = new Producto(nombre, precio, ingredientes);
                        productos.add(producto);
                    }
                } else {
                    // Es una línea de categoría
                    categoriaActual = line.trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarIngredientes(String ingredientesFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(ingredientesFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }

                if (line.contains(",")) {
                    String[] partes = line.split(",");
                    if (partes.length == 2) {
                        String nombre = partes[0].trim();
                        String precio = partes[1].trim();
                        Ingredientes ingrediente = new Ingredientes(nombre, precio);
                        ingredientes.add(ingrediente);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Ingredientes> obtenerIngredientes() {
        return ingredientes;
    }

    public List<Producto> obtenerProductos() {
        return productos;
    }
}
