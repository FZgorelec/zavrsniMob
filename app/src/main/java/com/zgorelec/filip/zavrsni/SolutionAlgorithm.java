package com.zgorelec.filip.zavrsni;

import algorithm.GenerationalGeneticAlgorithm;
import algorithmImpl.GeneticProgramming.*;
import util.GeneticAlgorithmParameters;

import java.util.List;

public class SolutionAlgorithm {
    public AlgorithmResult run(String[][] boardState) {
        String[][] preparedBoardState = prepareBoard(boardState);
        MovementTreeFactory fact = new MovementTreeFactory(7, 200);
        MovementTreeEvaluator evaluator = new MovementTreeEvaluator(preparedBoardState, 150);
        GenerationalGeneticAlgorithm<ITree> ga = new GenerationalGeneticAlgorithm<>(fact,
                (ITree genome) -> {
                    return evaluator.evaluate(genome);
                }, new GeneticAlgorithmParameters(24, 30000, 14.1));
        GeneticProgrammingAlgorithm gpa = new GeneticProgrammingAlgorithm(ga, fact, 7, 200);
        long startTime = System.currentTimeMillis();
        ITree tree = gpa.run(false);
        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;

        MovementDecoder decoder = new MovementDecoder(preparedBoardState, 150);
        List<String> movements = decoder.treeToMovementList(tree, 150);
        String[] moves = new String[movements.size()];
        for (int i = 0; i < moves.length; i++) {
            switch (movements.get(i)) {
                case "MOVE":
                    moves[i] = "moveForward";
                    break;
                case "ROTATE_RIGHT":
                    moves[i] = "rotateRight";
                    break;
                case "ROTATE_LEFT":
                    moves[i] = "rotateLeft";
                    break;
            }
        }
        return new AlgorithmResult(moves, tree.getFitness(),timeElapsed);
    }

    public String[][] prepareBoard(String[][] boardState) {
        String[][] preparedBoardState = new String[boardState.length][boardState[0].length];
        for (int i = 0; i < boardState.length; i++) {
            for (int j = 0; j < boardState[0].length; j++) {
                switch (boardState[i][j]) {
                    case "openField":
                        preparedBoardState[i][j] = MapValues.OPENFIELD.toString();
                        break;
                    case "bomb":
                        preparedBoardState[i][j] = MapValues.BOMB.toString();
                        break;
                    case "wall":
                        preparedBoardState[i][j] = MapValues.WALL.toString();
                        break;
                    case "food":
                        preparedBoardState[i][j] = MapValues.FOOD.toString();
                        break;
                    case "antRight":
                        preparedBoardState[i][j] = MapValues.OPENFIELD.toString();
                        break;
                }
            }
        }
        return preparedBoardState;
    }
}
