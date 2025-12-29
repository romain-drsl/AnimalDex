package ch.heigvd;

import io.javalin.Javalin;

public class Main {
    public static final int PORT = 8080;

    public static void main(String[] args) {
        Javalin app = Javalin.create();

        app.get("/", ctx -> ctx.result("Hello, world from a GET request method!"));
        app.post("/", ctx -> ctx.result("Hello, world from a POST request method!"));
        app.patch("/", ctx -> ctx.result("Hello, world from a PATCH request method!"));
        app.delete("/", ctx -> ctx.result("Hello, world from a DELETE request method!"));

        app.start(PORT);
    }
}