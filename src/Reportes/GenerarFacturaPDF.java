import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GenerarFacturaPDF {

    public static void main(String[] args) {
        Document document = new Document();

        try {
            // Ruta del archivo PDF en la carpeta Documentos
            String ruta = System.getProperty("user.home") + "\\Documents\\almacenPDF\\ReporteFactura.pdf";
            PdfWriter.getInstance(document, new FileOutputStream(ruta));
            document.open();

            // Fuente para los títulos y el contenido
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            // Datos de ejemplo para las facturas
            String[][] ventas = {
                    {"1", "2024-10-01", "Juan Pérez", "Carlos López", "150.00"},
                    {"2", "2024-10-02", "María González", "Ana García", "270.00"},
                    {"3", "2024-10-03", "Juan Pérez", "Luis Torres", "80.00"}
            };
            //caja grande 3 tiene subcajitas y cada subcajita contiene productos
            String[][][] detallesVentas = {
                    {{"Laptop", "1", "100.00", "100.00"}, {"Mouse", "2", "25.00", "50.00"}},
                    {{"Monitor", "3", "60.00", "180.00"}, {"Teclado", "1", "90.00", "90.00"}},
                    {{"Mouse", "2", "40.00", "80.00"}}
            };

            for (int i = 0; i < ventas.length; i++) {
                // Título de la factura
                Paragraph title = new Paragraph("Factura #" + ventas[i][0], titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);

                // Información de la venta
                document.add(new Paragraph("Fecha: " + ventas[i][1], contentFont));
                document.add(new Paragraph("Empleado: " + ventas[i][2], contentFont));
                document.add(new Paragraph("Cliente: " + ventas[i][3], contentFont));
                document.add(new Paragraph(" ")); // Espacio en blanco

                // Tabla de detalles
                PdfPTable table = new PdfPTable(4); // 4 columnas: Producto, Cantidad, Precio Unitario, Subtotal
                table.setWidthPercentage(100); 
                table.addCell(new PdfPCell(new Phrase("Producto", titleFont))); //se agrega de esta forma
                table.addCell(new PdfPCell(new Phrase("Cantidad", titleFont))); //para que contenga sus estilos
                table.addCell(new PdfPCell(new Phrase("Precio Unitario", titleFont)));
                table.addCell(new PdfPCell(new Phrase("Subtotal", titleFont)));
                
                //Recordar que la cajota no se cuenta pues se ignora ya q contiene a las subcajitas
                // Agregar los detalles de cada producto en la venta
                //cada subcajita(detalle) es un array y en ese array accedo ahora a sus datos
                for (String[] detalle : detallesVentas[i]) { //en cada iteracion principal cambio de array detalle
                    table.addCell(new PdfPCell(new Phrase(detalle[0], contentFont))); // Producto
                    table.addCell(new PdfPCell(new Phrase(detalle[1], contentFont))); // Cantidad
                    table.addCell(new PdfPCell(new Phrase(detalle[2], contentFont))); // Precio Unitario
                    table.addCell(new PdfPCell(new Phrase(detalle[3], contentFont))); // Subtotal
                }

                document.add(table); //se agrega la tabla de detalle al documento

                // Total de la venta
                Paragraph totalVenta = new Paragraph("Total de la Venta: $" + ventas[i][4], titleFont);
                totalVenta.setAlignment(Element.ALIGN_RIGHT);
                document.add(totalVenta);

                // Separador entre facturas
                if (i < ventas.length - 1) {
                    document.newPage(); // Salto de página
                }
            }

            System.out.println("Archivo PDF creado exitosamente en: " + ruta);
            
            //ABRIR EL pdf automatico : usamos un objeto de tipo archivo
            File pdfFile=new File(ruta); //contiene ese archivo  su ruta
            if (pdfFile.exists()) {
                Desktop.getDesktop().open(pdfFile);
                System.out.println("El archivo se abrio automaticamente");
            }else{
                System.out.println("No se encontro el archivo para abrir");
            }
            
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}

