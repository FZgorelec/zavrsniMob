package com.zgorelec.filip.zavrsni;

public class AlgorithmResult {
    private String[] moves;
    private double fitness;
    private double time;

    public AlgorithmResult(String[] moves, double fitness, double time) {
        this.moves = moves;
        this.fitness = fitness;
        this.time = time;
    }

    public String[] getMoves() {
        return moves;
    }

    public double getFitness() {
        return fitness;
    }

    public double getTime() {
        return time;
    }
}
