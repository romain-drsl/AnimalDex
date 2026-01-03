package ch.heigvd.controllers;

import ch.heigvd.logic.ObservationLogic;
import ch.heigvd.models.Observation;
import io.javalin.http.Context;

import java.time.LocalDate;

public class ObservationController {

    private final ObservationLogic logic;

    public ObservationController(ObservationLogic logic) {
        this.logic = logic;
    }

    public void create(Context ctx) {
        Observation observation = ctx.bodyAsClass(Observation.class);
        Observation created = logic.create(observation);
        ctx.status(201).json(created);
    }

    public void getAll(Context ctx) {
        String animalParam = ctx.queryParam("animalNumber");
        String dateParam = ctx.queryParam("date");
        String region = ctx.queryParam("region");

        Integer animalNumber = animalParam != null ? Integer.parseInt(animalParam) : null;

        LocalDate date = dateParam != null ? LocalDate.parse(dateParam) : null;

        ctx.json(logic.getAll(animalNumber, date, region));
    }

    public void getOne(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        ctx.json(logic.getOne(id));
    }

    public void update(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Observation observation = ctx.bodyAsClass(Observation.class);
        ctx.json(logic.update(id, observation));
    }

    public void delete(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        logic.delete(id);
        ctx.status(204);
    }
}
