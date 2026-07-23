package modelo.services;

import modelo.dao.PagoDAO;
import modelo.entity.Cita;
import modelo.entity.Pago;

public class PagoService {

    private PagoDAO pagoDAO;

    public PagoService() {
        this.pagoDAO = new PagoDAO();
    }

    public void guardarPago(Cita cita, Double monto) {
        Pago pago = new Pago(monto, cita);
        pagoDAO.guardarPago(pago);
    }
}
