package ar.edu.unlar.ecoride.service;

public class CriterioHoraPico implements CriterioTarifa {
    @Override
    public double calcular(double tarifaBase, int minutos) {
        return (tarifaBase * minutos) * 1.40;
    }
}
