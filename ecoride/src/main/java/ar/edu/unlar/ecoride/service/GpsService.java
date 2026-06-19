package ar.edu.unlar.ecoride.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class GpsService {

    public List<String> deduplicarAlertas(List<String> alertas) {
        if (alertas == null) {
            return new ArrayList<>();
        }
        Set<String> unicos = new LinkedHashSet<>();
        for (int i = 0; i < alertas.size(); i++) {
            unicos.add(alertas.get(i));
        }
        return new ArrayList<>(unicos);
    }
}
