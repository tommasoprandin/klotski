package it.unipd.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.unipd.models.Block;
import it.unipd.models.Board;

import java.io.IOException;

public class BoardJsonAdapter extends TypeAdapter<Board> {
    @Override
    public void write(JsonWriter jsonWriter, Board board) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("blocks");
        jsonWriter.beginArray();
        for (Block b : board.getBlocks()) {
            jsonWriter.beginObject();
            jsonWriter.name("shape");
            jsonWriter.beginArray();
            jsonWriter.value(b.getH());
            jsonWriter.value(b.getW());
            jsonWriter.endArray();
            jsonWriter.name("position");
            jsonWriter.beginArray();
            jsonWriter.value(b.getY());
            jsonWriter.value(b.getX());
            jsonWriter.endArray();
            jsonWriter.endObject();
        }
        jsonWriter.endArray();
        jsonWriter.name("boardSize");
        jsonWriter.beginArray();
        jsonWriter.value(board.getRows());
        jsonWriter.value(board.getCols());
        jsonWriter.endArray();
        jsonWriter.name("escapePoint");
        jsonWriter.beginArray();
        jsonWriter.value(board.getyGoal());
        jsonWriter.value(board.getxGoal());
        jsonWriter.endArray();
        jsonWriter.endObject();
    }

    @Override
    public Board read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
