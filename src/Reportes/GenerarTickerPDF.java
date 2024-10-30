import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GenerarTickerPDF {

    public static void main(String[] args) {
        // Tamaño de página personalizado (7.5 cm x 20 cm)
        Rectangle smallPage = new Rectangle(216, 600); // aproximadamente 7.5 cm x 20 cm
        Document document = new Document(smallPage, 10, 10, 10, 10); // Márgenes pequeños

        try {
            // Ruta para guardar el PDF
            String ruta = System.getProperty("user.home") + "/Documents/almacenPDF/ticket.pdf";
            PdfWriter.getInstance(document, new FileOutputStream(ruta));
            document.open();

            // Fuente para el encabezado y el contenido
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

            // Encabezado del recibo
            Paragraph header = new Paragraph("Farmacia Salud y Vida", headerFont);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);

            Paragraph address = new Paragraph("Av. Ejemplo 123, Ciudad", contentFont);
            address.setAlignment(Element.ALIGN_CENTER);
            document.add(address);

            Paragraph phone = new Paragraph("Tel: (555) 123-4567", contentFont);
            phone.setAlignment(Element.ALIGN_CENTER);
            document.add(phone);

            document.add(new Paragraph(" ")); // Espacio en blanco

            // Información de la transacción
            document.add(new Paragraph("Fecha: 2024-10-29", contentFont));
            document.add(new Paragraph("Número de Ticket: 00012345", contentFont));
            document.add(new Paragraph("Atendido por: Juan Pérez", contentFont));

            document.add(new Paragraph(" ")); // Espacio en blanco

            // Tabla para los productos
            PdfPTable table = new PdfPTable(3); // 3 columnas: Producto, Cantidad, Precio
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2, 1, 1}); // Anchos relativos de las columnas
            table.addCell(new PdfPCell(new Phrase("Producto", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Cant", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Precio", headerFont)));

            // Agregamos filas de ejemplo a la tabla
            String[][] productos = {
                    {"Paracetamol", "2", "10.00"},
                    {"Ibuprofeno", "1", "15.00"},
                    {"Vitaminas", "1", "25.00"}
            };
            //de la caja grande recorro cada subcajita
            for (String[] producto : productos) { //cada subcajita contiene un array de tipo cadena
                table.addCell(new PdfPCell(new Phrase(producto[0], contentFont))); //se agrega en la celda 1
                table.addCell(new PdfPCell(new Phrase(producto[1], contentFont)));//celda 2 de la actual fila
                table.addCell(new PdfPCell(new Phrase(producto[2], contentFont)));//celda 3 de la actual fila
                //como la tabla solo tiene 3 columnas se situa en la proxima FILA y así sucesivamente
            }

            document.add(table); //Se guarda la tabla en el documento

            // Total
            document.add(new Paragraph(" "));
            Paragraph total = new Paragraph("Total: $60.00", headerFont);
            total.setAlignment(Element.ALIGN_RIGHT);
            document.add(total);

            document.add(new Paragraph(" ")); // Espacio en blanco

            // Mensaje de agradecimiento
            Paragraph gracias = new Paragraph("¡Gracias por su compra!", contentFont);
            gracias.setAlignment(Element.ALIGN_CENTER);
            document.add(gracias);
            //Mostrar o abrrur en pdf
            File pdfFile=new File(ruta); //Objeto tipo archivo que contiene su ruta
            if (pdfFile.exists()) {
                Desktop.getDesktop().open(pdfFile);
                System.out.println("El archivo se abrio automaticamente");
            }else{
                System.out.println("No se encontro el archivo para abrir");
            }
            
            System.out.println("Ticket PDF generado exitosamente en: " + ruta);

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}

