package Reportes;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream; //crea nuevo o reemplaza el archivo si ya existe con ese nombre
import java.io.IOException;
public class practicando {
    public static void main(String[] args) {
        Document document=new Document();
        try {
            //ruta donde se guardara el archivo
            String ruta=System.getProperty("user.home")+"\\Documents\\almacenPDF\\ReporteBasicoPrueba.pdf";
            PdfWriter.getInstance(document, new FileOutputStream(ruta));
            document.open(); //se abre el documento ahora si puedo escribir cosas
            
            //1. Agregar un titulo al documento
            Font titleFont=FontFactory.getFont(FontFactory.HELVETICA_BOLD,18); //estilo del titulo
            Paragraph tittle=new Paragraph("Reporte de venta",titleFont); //parrafo 
            tittle.setAlignment(Element.ALIGN_CENTER);//agrega un centrado al texto
            document.add(tittle); //añade un elemento al documento
            
            //Espacio en blanco
            document.add(new Paragraph(" "));
            
            //2. Agregar una tabla con una sola fila y algunas celdas de ejemplo
            PdfPTable table= new PdfPTable(3); //crea una tabla con 3 columnas
            //Agrega encabezados a la tabla
            table.addCell("Producto"); //supongo que va en la celda 1 correspondiente a la fila 1
            table.addCell("Cantidad");//celda 2
            table.addCell("Precio");//celda 3 y como en una fila maximo hay 3 entonces ahora se situa en la next fila
            
            //Agregar una fila de ejemplo
            table.addCell("Laptop"); //celda 1 de la fila actual
            table.addCell("2"); //celda 2 de la fila actual
            table.addCell("S/1200.00"); //celda 3 de la fila actual y ahora se situa en la next fila...
            
            //Agregar la tabla al documento
            document.add(table);
            document.add(new Paragraph(" "));
            
            // 3. Agregar una descripcion o parrafo adicional
            Font conFont= FontFactory.getFont(FontFactory.HELVETICA,12);
            Paragraph paragraph=new Paragraph("Este reporte contiene el detalle de la venta");
            paragraph.setAlignment(Element.ALIGN_JUSTIFIED);//Alinea el texto a justificado
            document.add(paragraph);
            
            System.out.println("Reporte PDF generado exitosamente en :"+ruta);
            
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
        } finally{
            document.close(); //cierra el documento
        }
        
    }
    
}
