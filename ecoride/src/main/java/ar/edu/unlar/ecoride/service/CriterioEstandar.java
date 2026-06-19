package ar.edu.unlar.ecoride.service;

public class CriterioEstandar implements CriterioTarifa {
    @Override
    public double calcular(double tarifaBase, int minutos) {
        return tarifaBase * minutos;
    }
}
