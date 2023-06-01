package it.unipd.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.unipd.models.Block;
import it.unipd.models.Board;
import it.unipd.models.Move;

import java.io.IOException;

public class MoveJsonAdapter extends TypeAdapter<Move> {

    private Board board;

    public MoveJsonAdapter(Board board) {
        this.board = board;
    }

    @Override
    public void write(JsonWriter jsonWriter, Move move) throws IOException {
    }

    @Override
    public Move read(JsonReader jsonReader) throws IOException {
        int blockIdx = -1;
        Block.Direction dir = null;
        try {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String name = jsonReader.nextName();
                switch (name) {
                    case "step":
                        jsonReader.nextInt();
                        break;
                    case "blockIdx": {
                        blockIdx = jsonReader.nextInt();
                        break;
                    }
                    case "dirIdx": {
                        switch (jsonReader.nextInt()) {
                            case 0:
                                dir = Block.Direction.D;
                                break;
                            case 1:
                                dir = Block.Direction.R;
                                break;
                            case 2:
                                dir = Block.Direction.U;
                                break;
                            case 3:
                                dir = Block.Direction.L;
                                break;
                        }
                        break;
                    }
                }
            }
            jsonReader.endObject();
        }
        catch (IOException e) {}
        if (blockIdx == -1 || dir == null) throw new IOException("Could not parse JSON for Move");
        return new Move(board.getBlocks().get(blockIdx), dir);
    }
}
