package it.unipd.utils;

import com.google.gson.GsonBuilder;
import it.unipd.adapters.MoveJsonAdapter;
import it.unipd.models.Block;
import it.unipd.models.Board;
import it.unipd.models.Move;
import it.unipd.adapters.BoardJsonAdapter;

//import javax.json.Json;
//import javax.json.JsonArrayBuilder;
//import javax.json.JsonObject;
//import javax.json.JsonObjectBuilder;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class BestMoveFetcher {

    private final URI solverURI;
    private final HttpClient client;

    public BestMoveFetcher(URI solverURI) {
        this.solverURI = solverURI;
        this.client = HttpClient.newBuilder().build();
    }

    public Move getNextBestMove(Board board) throws IOException, InterruptedException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Board.class, new BoardJsonAdapter())
                .registerTypeAdapter(Move.class, new MoveJsonAdapter(board))
                .create();
        HttpRequest request = HttpRequest.newBuilder(solverURI)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(board)))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Move bestMove = gson.fromJson(response.body(), Move.class);
        return bestMove;
    }
}
