package ch.heigvd;

import ch.heigvd.controllers.AnimalController;
import ch.heigvd.exceptions.ConflictException;
import ch.heigvd.exceptions.NotFoundException;
import ch.heigvd.logic.AnimalLogic;
import io.javalin.Javalin;

public class Main {
    public static final int PORT = 8080;

    public static void main(String[] args) {

        AnimalLogic animalLogic = new AnimalLogic();
        AnimalController animalController = new AnimalController(animalLogic);

        Javalin app = Javalin.create();

        // Routes Animals
        app.post("/animals", animalController::create);
        app.get("/animals", animalController::getAll);
        app.get("/animals/{number}", animalController::getOne);
        app.put("/animals/{number}", animalController::update);
        app.delete("/animals/{number}", animalController::delete);

        // Exception handlers
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
