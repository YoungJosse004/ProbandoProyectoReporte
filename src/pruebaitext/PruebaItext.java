package pruebaitext;

import Mode.OrdenCompra;
import ModeloDAO.OrdenCompraDAO;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;

public class PruebaItext {
    public static void main(String[] args) {
        // Obtén el ID de la orden de compra
        int idOc = 1;
        // Define la ruta donde se guardará el PDF
        String userHome = System.getProperty("user.home");
        String folderPath = userHome + "/Documents/almacenPDF"; // Cambia "almacenPDF" si deseas otro nombre
        File directory = new File(folderPath);
        
        // Crea la carpeta si no existe
        if (!directory.exists()) {
            directory.mkdirs(); // Crea todos los directorios intermedios necesarios
        }
        // Define el nombre y la ruta del archivo PDF
        String filePath = folderPath + "/OrdenCompra_" + idOc + ".pdf";
        // Llama al método para generar el PDF
        OrdenCompraDAO ocDAO = new OrdenCompraDAO();
        ocDAO.generarReporteDetalladoPDF(idOc, filePath);
        // Muestra un mensaje de éxito y abre el PDF automáticamente
        JOptionPane.showMessageDialog(null, "PDF generado exitosamente: " + filePath);
        
        // Intenta abrir el PDF automáticamente en el visor predeterminado
        try {
            File pdfFile = new File(filePath);
            if (pdfFile.exists()) {
                Desktop.getDesktop().open(pdfFile);
                System.out.println("Se abrio correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "El archivo PDF no fue encontrado.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al abrir el archivo PDF: " + e.getMessage());
        }
    }
    
}
