package ar.edu.unlar.ecoride.service;

import org.springframework.stereotype.Component;

@Component
public class GestorTarifa {
    private CriterioTarifa criterioActivo = new CriterioEstandar();

    public void setCriterio(CriterioTarifa criterioActivo) {
        this.criterioActivo = criterioActivo;
    }

    public CriterioTarifa getCriterioActivo() {
        return this.criterioActivo;
    }

    public double calcular(double tarifaBase, int minutos) {
        if (this.criterioActivo == null) {
            this.criterioActivo = new CriterioEstandar();
        }
        return this.criterioActivo.calcular(tarifaBase, minutos);
    }
}
