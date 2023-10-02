package actividad;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        GestorProductos gestorProductos = new GestorProductos();
        gestorProductos.cargarProductosDesdeCSV("productos.csv");
        gestorProductos.cargarIngredientes("ingredientes.csv");

        Compra compra = new Compra();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menú:");
            System.out.println("1. Comprar un producto");
            System.out.println("2. Agregar ingredientes a un producto");
            System.out.println("3. Ver Carrito Actual");
            System.out.println("4. Generar factura");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    mostrarProductos(gestorProductos);
                    System.out.print("Seleccione el número de producto a comprar: ");
                    int numeroProducto = scanner.nextInt();
                    Producto producto = gestorProductos.obtenerProductos().get(numeroProducto - 1);
                    compra.agregarProducto(producto);
                    System.out.println("Producto agregado a la compra.");
                    break;
                case 2:
                    mostrarProductosEnCompra(compra);
                    System.out.print("Seleccione el número de producto al que desea agregar ingredientes: ");
                    int numeroProductoEnCompra = scanner.nextInt();
                    mostrarIngredientes(gestorProductos);
                    System.out.print("Seleccione el número de ingredientes a agregar (separados por comas): ");
                    scanner.nextLine(); // Consumir la nueva línea
                    String ingredientesSeleccionados = scanner.nextLine();
                    String[] ingredientesArray = ingredientesSeleccionados.split(",");
                    List<Ingredientes> ingredientesLista = obtenerIngredientesSeleccionados(gestorProductos, ingredientesArray);
                    compra.agregarIngredientes(numeroProductoEnCompra - 1, ingredientesLista);
                    System.out.println("Ingredientes agregados al producto en la compra.");
                    break;
                case 3:
                    verCarritoActual(compra);
                    break;
                case 4:
                    generarFactura(compra);
                    break;
                case 5:
                    System.out.println("Saliendo del programa.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    private static void mostrarProductos(GestorProductos gestorProductos) {
        List<Producto> productos = gestorProductos.obtenerProductos();
        System.out.println("Productos Disponibles:");
        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            System.out.println((i + 1) + ". " + producto.getNombre() + " - Precio: " + producto.getPrecio());
        }
    }

    private static void mostrarProductosEnCompra(Compra compra) {
        List<Producto> productos = compra.getProductos();
        System.out.println("Productos en la Compra:");
        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            System.out.println((i + 1) + ". " + producto.getNombre());
        }
    }

    // Método para mostrar la lista de ingredientes disponibles
    private static void mostrarIngredientes(GestorProductos gestorProductos) {
        List<Ingredientes> ingredientes = gestorProductos.obtenerIngredientes();
        System.out.println("Ingredientes Disponibles:");
        for (int i = 0; i < ingredientes.size(); i++) {
            Ingredientes ingrediente = ingredientes.get(i);
            System.out.println((i + 1) + ". " + ingrediente.getNombre() + " - Precio: " + ingrediente.getPrecio());
        }
    }

    // Método para obtener los objetos Ingredientes seleccionados por número
    private static List<Ingredientes> obtenerIngredientesSeleccionados(GestorProductos gestorProductos, String[] numerosIngredientes) {
        List<Ingredientes> ingredientesSeleccionados = new ArrayList<>();
        List<Ingredientes> ingredientes = gestorProductos.obtenerIngredientes();
        for (String numero : numerosIngredientes) {
            int indice = Integer.parseInt(numero.trim()) - 1;
            if (indice >= 0 && indice < ingredientes.size()) {
                ingredientesSeleccionados.add(ingredientes.get(indice));
            }
        }
        return ingredientesSeleccionados;
    }

    private static void generarFactura(Compra compra) {
        List<Producto> productos = compra.getProductos();
        double total = 0.0;

        FileWriter archivo = null;
        PrintWriter escritor = null;

        try {
            archivo = new FileWriter("factura.txt");
            escritor = new PrintWriter(archivo);

            escritor.println("Factura:");
            escritor.println("Productos en la Compra:");

            for (int i = 0; i < productos.size(); i++) {
                Producto producto = productos.get(i);
                escritor.println((i + 1) + ". " + producto.getNombre() + " - Precio: " + producto.getPrecio());

                // Agregar precios de ingredientes adicionales si los hay
                List<Ingredientes> ingredientesAdicionales = producto.getIngredientesAdicionales();
                if (ingredientesAdicionales != null && !ingredientesAdicionales.isEmpty()) {
                    escritor.println("   Ingredientes Adicionales:");
                    for (Ingredientes ingrediente : ingredientesAdicionales) {
                        escritor.println("   - " + ingrediente.getNombre() + " - Precio: " + ingrediente.getPrecio());
                    }
                }

                total += producto.getPrecio();

                // Sumar el precio de los ingredientes adicionales al total
                if (ingredientesAdicionales != null && !ingredientesAdicionales.isEmpty()) {
                    for (Ingredientes ingrediente : ingredientesAdicionales) {
                        total += Double.parseDouble(ingrediente.getPrecio());
                    }
                }
            }

            escritor.println("Total: " + total);
            System.out.println("Factura generada con éxito (ver archivo factura.txt).");

        } catch (IOException e) {
            System.out.println("Error al generar la factura.");
            e.printStackTrace();
        } finally {
            try {
                if (escritor != null) {
                    escritor.close();
                }
                if (archivo != null) {
                    archivo.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void verCarritoActual(Compra compra) {
        List<Producto> productos = compra.getProductos();
        double total = 0.0;

        if (productos.isEmpty()) {
            System.out.println("El carrito está vacío.");
        } else {
            System.out.println("Carrito Actual:");
            for (int i = 0; i < productos.size(); i++) {
                Producto producto = productos.get(i);
                System.out.println((i + 1) + ". " + producto.getNombre() + " - Precio: " + producto.getPrecio());

                List<Ingredientes> ingredientesAdicionales = producto.getIngredientesAdicionales();
                if (ingredientesAdicionales != null && !ingredientesAdicionales.isEmpty()) {
                    System.out.println("   Ingredientes Adicionales:");
                    for (Ingredientes ingrediente : ingredientesAdicionales) {
                        System.out.println("   - " + ingrediente.getNombre() + " - Precio: " + ingrediente.getPrecio());
                    }
                }

                total += producto.getPrecio();

                // Sumar el precio de los ingredientes adicionales al total
                if (ingredientesAdicionales != null && !ingredientesAdicionales.isEmpty()) {
                    for (Ingredientes ingrediente : ingredientesAdicionales) {
                        total += Double.parseDouble(ingrediente.getPrecio());
                    }
                }
            }
            System.out.println("Costo total: " + total);
        }
    }

}
