package ar.edu.unlar.ecoride.service;

public class CriterioTemporal implements CriterioTarifa {
    @Override
    public double calcular(double tarifaBase, int minutos) {
        return (tarifaBase * minutos) + 150.0;
    }
}
