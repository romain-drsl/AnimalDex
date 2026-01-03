package ch.heigvd.controllers;

import ch.heigvd.logic.AnimalLogic;
import ch.heigvd.models.Animal;
import ch.heigvd.models.AnimalGroup;
import io.javalin.http.Context;

public class AnimalController {

    private final AnimalLogic logic; // Délégation de la logique métier à la couche logic

    public AnimalController(AnimalLogic logic) {
        this.logic = logic;
    }

    public void create(Context ctx) {
        Animal animal = ctx.bodyAsClass(Animal.class); // Désérialisation du corps de la requête en un objet Animal
        Animal created = logic.create(animal);
        ctx.status(201).json(created);
    }

    public void getAll(Context ctx) {
        String frenchName = ctx.queryParam("frenchName");
        String latinName = ctx.queryParam("latinName");
        String groupParam = ctx.queryParam("group");

        AnimalGroup group = null;
        if (groupParam != null) {
            group = AnimalGroup.valueOf(groupParam);
        }

        ctx.json(logic.getAll(frenchName, latinName, group));
    }

    public void getOne(Context ctx) {
        int number = Integer.parseInt(ctx.pathParam("number"));
        ctx.json(logic.getOne(number));
    }

    public void update(Context ctx) {
        int number = Integer.parseInt(ctx.pathParam("number"));
        Animal animal = ctx.bodyAsClass(Animal.class);
        ctx.json(logic.update(number, animal));
    }

    public void delete(Context ctx) {
        int number = Integer.parseInt(ctx.pathParam("number"));
        logic.delete(number);
        ctx.status(204);
    }
}
