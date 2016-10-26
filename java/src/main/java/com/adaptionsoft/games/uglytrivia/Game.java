package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private ArrayList<String> players = new ArrayList<>();
    private int[] places = new int[6];
    private int[] purses  = new int[6];
    private boolean[] inPenaltyBox  = new boolean[6];

    private List<String> categories = new ArrayList<>();
    private Map<String, List<String>> questions = new HashMap<>();

    private int currentPlayer = 0;
    private boolean isGettingOutOfPenaltyBox;
    
    public Game() {
        List<String> popQuestions = new ArrayList<>();
        List<String> scienceQuestions = new ArrayList<>();
        List<String> sportsQuestions = new ArrayList<>();
        List<String> rockQuestions = new ArrayList<>();
    	for (int i = 0; i < 50; i++) {
			popQuestions.add("Pop Question " + i);
			scienceQuestions.add("Science Question " + i);
			sportsQuestions.add("Sports Question " + i);
			rockQuestions.add("Rock Question " + i);
    	}
    	addCategory("Pop", popQuestions);
        addCategory("Science", scienceQuestions);
        addCategory("Sports", sportsQuestions);
        addCategory("Rock", rockQuestions);
    }

	public void add(String playerName) {
	    players.add(playerName);
	    places[howManyPlayers()] = 0;
	    purses[howManyPlayers()] = 0;
	    inPenaltyBox[howManyPlayers()] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
	}

	public void addCategory(String name, List<String> questions) {
        categories.add(name);
        this.questions.put(name, new ArrayList<>(questions));
    }
	
	private int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (inPenaltyBox[currentPlayer]) {
			if (isOdd(roll)) {
				isGettingOutOfPenaltyBox = true;
				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
			} else {
				isGettingOutOfPenaltyBox = false;
                System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
            }
		}

        if (!inPenaltyBox[currentPlayer] || isGettingOutOfPenaltyBox) {
            advancePlayerPosition(roll);
			askQuestion();
		}
	}

	private boolean isOdd(int value) {
        return (value % 2) != 0;
    }

    private void advancePlayerPosition(int roll) {
        places[currentPlayer] = places[currentPlayer] + roll;
        if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;
        System.out.println(players.get(currentPlayer)
                + "'s new location is "
                + places[currentPlayer]);
    }

    private void askQuestion() {
        final String category = currentCategory();
        System.out.println("The category is " + category);
        System.out.println(questions.get(category).remove(0));
	}

	private String currentCategory() {
        final int categoryIndex = places[currentPlayer] % categories.size();
        return categories.get(categoryIndex);
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayer] && !isGettingOutOfPenaltyBox) {
            advanceToNextPlayer();
            return true;
		}

        if (inPenaltyBox[currentPlayer]) {
            rewardCorrectAnswer("Answer was correct!!!!");
        } else {
            rewardCorrectAnswer("Answer was corrent!!!!");
        }
        boolean winner = didPlayerWin();
        advanceToNextPlayer();
        return winner;
	}

    private void advanceToNextPlayer() {
        currentPlayer++;
        if (currentPlayer == players.size()) {
            currentPlayer = 0;
        }
    }

    private void rewardCorrectAnswer(String message) {
		System.out.println(message);
		purses[currentPlayer]++;
		System.out.println(players.get(currentPlayer)
				+ " now has "
				+ purses[currentPlayer]
				+ " Gold Coins.");
	}

	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;

        advanceToNextPlayer();
		return true;
	}

	private boolean didPlayerWin() {
		return !(purses[currentPlayer] == 6);
	}
}
