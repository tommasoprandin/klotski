package it.unipd.models;

import java.util.Arrays;

public class Configuration {
    public final static int BLOCKS_NUM = 10;
    private final Block[] blocks = new Block[BLOCKS_NUM];

    public Configuration(int type) {
        switch (type) {
            case 0: {
                blocks[0] = new Block(1, 0, 2, 2);
                blocks[1] = new Block(0, 0, 1, 2);
                blocks[2] = new Block(3, 0, 1, 2);
                blocks[3] = new Block(0, 2, 1, 2);
                blocks[4] = new Block(1, 2, 1, 1);
                blocks[5] = new Block(2, 2, 1, 1);
                blocks[6] = new Block(3, 2, 1, 2);
                blocks[7] = new Block(1, 3, 1, 1);
                blocks[8] = new Block(2, 3, 1, 1);
                blocks[9] = new Block(1, 4, 2, 1);
                break;
            }
            case 1:
                blocks[0] = new Block(1, 0, 2, 2);
                blocks[1] = new Block(0, 0, 1, 1);
                blocks[2] = new Block(3, 0, 1, 1);
                blocks[3] = new Block(0, 1, 1, 2);
                blocks[4] = new Block(3, 1, 1, 2);
                blocks[5] = new Block(1, 2, 1, 2);
                blocks[6] = new Block(0, 3, 1, 1);
                blocks[7] = new Block(3, 3, 1, 1);
                blocks[8] = new Block(0, 4, 2, 1);
                blocks[9] = new Block(2, 4, 2, 1);
                break;
            case 2:
                blocks[0] = new Block(2, 1, 2, 2);
                blocks[1] = new Block(0, 0, 1, 2);
                blocks[2] = new Block(1, 0, 1, 1);
                blocks[3] = new Block(2, 0, 1, 1);
                blocks[4] = new Block(3, 0, 1, 1);
                blocks[5] = new Block(1, 1, 1, 2);
                blocks[6] = new Block(0, 2, 1, 2);
                blocks[7] = new Block(1, 3, 2, 1);
                blocks[8] = new Block(3, 3, 1, 1);
                blocks[9] = new Block(2, 4, 2, 1);
                break;
            case 3:
                blocks[0] = new Block(1, 0, 2, 2);
                blocks[1] = new Block(0, 0, 1, 2);
                blocks[2] = new Block(3, 0, 1, 2);
                blocks[3] = new Block(0, 2, 1, 2);
                blocks[4] = new Block(1, 2, 2, 1);
                blocks[5] = new Block(3, 2, 1, 2);
                blocks[6] = new Block(1, 3, 1, 1);
                blocks[7] = new Block(2, 3, 1, 1);
                blocks[8] = new Block(0, 4, 1, 1);
                blocks[9] = new Block(3, 4, 1, 1);
                break;
            default:
                break;
        }
    }

    public Block[] getBlocks() {
        Block[] copy = new Block[blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            copy[i] = new Block(blocks[i]);
        }
        return copy;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "blocks=" + Arrays.toString(blocks) +
                '}';
    }

    public static int getAvailableConfigs() {
        return 4;
    }
}
