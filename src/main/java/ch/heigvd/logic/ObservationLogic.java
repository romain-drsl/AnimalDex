package ch.heigvd.logic;

import ch.heigvd.exceptions.NotFoundException;
import ch.heigvd.models.Observation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObservationLogic {

    private final Map<Integer, Observation> observations = new HashMap<>();
    private int counter = 1;

    // Dépendance vers AnimalLogic
    private final AnimalLogic animalLogic;

    public ObservationLogic(AnimalLogic animalLogic) {
        this.animalLogic = animalLogic;
    }

    public Observation create(Observation observation) {
        // Vérifier que l'animal existe
        animalLogic.getOne(observation.getAnimalNumber());

        if (observation.getDate() == null) {
            throw new IllegalArgumentException("Invalid date");
        }

        observation.setId(counter++);
        observations.put(observation.getId(), observation); // Stockage de la nouvelle observation dans la map
        return observation;
    }

    public Observation getOne(int id) {
        Observation obs = observations.get(id);
        if (obs == null) {
            throw new NotFoundException("Observation not found");
        }
        return obs;
    }

    public List<Observation> getAll(Integer animalNumber, LocalDate date, String region) {
        List<Observation> result = new ArrayList<>(); // Liste pour stocker les observations filtrées

        for (Observation obs : observations.values()) {

            if (animalNumber != null && obs.getAnimalNumber() != animalNumber) {
                continue;
            }

            if (date != null && !obs.getDate().equals(date)) {
                continue;
            }

            if (region != null && (obs.getRegion() == null || !obs.getRegion().toLowerCase().contains(region.toLowerCase()))) {
                continue;
            }

            result.add(obs);
        }

        return result;
    }

    public Observation update(int id, Observation observation) {
        if (!observations.containsKey(id)) {
            throw new NotFoundException("Observation not found");
        }

        // Vérifier que l'animal existe
        animalLogic.getOne(observation.getAnimalNumber());

        observation.setId(id);
        observations.put(id, observation); // Mise à jour de l'observation dans la map
        return observation;
    }

    public void delete(int id) {
        if (observations.remove(id) == null) {
            throw new NotFoundException("Observation not found");
        }
    }
}



