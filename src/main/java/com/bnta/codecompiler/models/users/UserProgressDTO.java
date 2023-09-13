package com.bnta.codecompiler.models.users;

public class UserProgressDTO {
    private String username;
    private long score;
    private long problemsSolved;
    private long totalProblems;

    public UserProgressDTO(String username, long score, long problemsSolved, long totalProblems) {
        this.username = username;
        this.score = score;
        this.problemsSolved = problemsSolved;
        this.totalProblems = totalProblems;
    }

    public UserProgressDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public long getProblemsSolved() {
        return problemsSolved;
    }

    public void setProblemsSolved(long problemsSolved) {
        this.problemsSolved = problemsSolved;
    }

    public long getTotalProblems() {
        return totalProblems;
    }

    public void setTotalProblems(long totalProblems) {
        this.totalProblems = totalProblems;
    }

}
