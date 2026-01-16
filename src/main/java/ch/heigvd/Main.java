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

    // ObservationLogic dépend de AnimalLogic
    ObservationLogic observationLogic = new ObservationLogic(animalLogic);

    // Contrôleurs
    AnimalController animalController = new AnimalController(animalLogic);
    ObservationController observationController = new ObservationController(observationLogic);

    Javalin app = Javalin.create();

    // Routes Animals
    app.post("/api/animals", animalController::create);
    app.get("/api/animals", animalController::getAll);
    app.get("/api/animals/{number}", animalController::getOne);
    app.put("/api/animals/{number}", animalController::update);
    app.delete("/api/animals/{number}", animalController::delete);

    // Routes Observations
    app.post("/api/observations", observationController::create);
    app.get("/api/observations", observationController::getAll);
    app.get("/api/observations/{id}", observationController::getOne);
    app.put("/api/observations/{id}", observationController::update);
    app.delete("/api/observations/{id}", observationController::delete);

    // Gestion des exceptions
    app.exception(
        NotFoundException.class,
        (e, ctx) -> {
          ctx.status(404).json(e.getMessage());
        });

    app.exception(
        ConflictException.class,
        (e, ctx) -> {
          ctx.status(409).json(e.getMessage());
        });

    app.exception(
        IllegalArgumentException.class,
        (e, ctx) -> {
          ctx.status(400).json(e.getMessage());
        });

    app.start(PORT);
  }
}
