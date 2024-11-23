package Reportes;

import ModeloDAO.OrdenCompraDAO;

public class GenerarPruebaDAO {
    public static void main(String[] args) {
        OrdenCompraDAO od=new OrdenCompraDAO();
        String ruta = System.getProperty("user.home") + "/Documents/almacenPDF/pruebaDao.pdf";
        od.generarReportePDF(1, ruta);
    }
}
