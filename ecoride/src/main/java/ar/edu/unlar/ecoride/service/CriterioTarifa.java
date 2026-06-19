package ar.edu.unlar.ecoride.service;

public interface CriterioTarifa {
    double calcular(double tarifaBase, int minutos);
}
