package ch.heigvd;

import ch.heigvd.controllers.AnimalController;
import ch.heigvd.controllers.ObservationController;
import ch.heigvd.exceptions.ConflictException;
import ch.heigvd.exceptions.NotFoundException;
import ch.heigvd.logic.AnimalLogic;
import ch.heigvd.logic.ObservationLogic;
import io.javalin.Javalin;

public class Main {
    public static final int PORT = 8080;

    public static void main(String[] args) {

        // Logique métier
        AnimalLogic animalLogic = new AnimalLogic();
        ObservationLogic observationLogic = new ObservationLogic(animalLogic); // ObservationLogic dépend de AnimalLogic

        // Contrôleurs
        AnimalController animalController = new AnimalController(animalLogic);
        ObservationController observationController = new ObservationController(observationLogic);

        Javalin app = Javalin.create();

        // Routes Animals
        app.post("/animals", animalController::create);
        app.get("/animals", animalController::getAll);
        app.get("/animals/{number}", animalController::getOne);
        app.put("/animals/{number}", animalController::update);
        app.delete("/animals/{number}", animalController::delete);

        // Routes Observations
        app.post("/observations", observationController::create);
        app.get("/observations", observationController::getAll);
        app.get("/observations/{id}", observationController::getOne);
        app.put("/observations/{id}", observationController::update);
        app.delete("/observations/{id}", observationController::delete);

        // Gestion des exceptions
        app.exception(NotFoundException.class, (e, ctx) -> {
            ctx.status(404).json(e.getMessage());
        });

        app.exception(ConflictException.class, (e, ctx) -> {
            ctx.status(409).json(e.getMessage());
        });

        app.exception(IllegalArgumentException.class, (e, ctx) -> {
            ctx.status(400).json(e.getMessage());
        });

        app.start(PORT);
    }
}

