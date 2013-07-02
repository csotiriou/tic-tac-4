/*
 * Copyright (c) 2013, Christos Sotiriou
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted 
 * provided that the following conditions are met:
 * -- Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * -- Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, 
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.sai.exec;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.sai.UI.GameCanvas;
import com.sai.UI.RuleListModel;
import com.sai.exception.InvariantException;
import com.sai.game.BoardGame;
import com.sai.game.BoardGame.PlayerType;
import com.sai.game.GameConnect4;
import com.sai.game.GameTicTacToe;
import com.sai.gamerules.GameRuleFactory;
import com.sai.gamerules.GameRules;
import com.sai.gamerules.RulePlay;

/**
 * Implements all functionality for running a UI for playing
 * all supported games, parametrized with game rules
 * @author soulstorm
 *
 */
public class UIInterfaceRunner implements Observer {

	private JFrame frame;
	GameRules rulesX;
	GameRules rulesO;

	BoardGame currentGame;

	GameCanvas gameCanvas = null;

	JList listRulesO;
	JList listRulesX;



	List<RulePlay> gameRulesPlayerO;
	List<RulePlay> gameRulesPlayerX;


	private JTextField txtInfoTestField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIInterfaceRunner window = new UIInterfaceRunner();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UIInterfaceRunner() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws InvariantException 
	 */
	private void initialize() {
		try {
			defaultGameSetup();
		} catch (InvariantException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println("setting ui...");

		frame = new JFrame();

		frame.setBounds(100, 100, 668, 516);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);

		gameCanvas = new GameCanvas(currentGame);
		gameCanvas.setBounds(0, 0, 422, 364);
		frame.getContentPane().add(gameCanvas);
		gameCanvas.setLayout(new GridLayout(1, 0, 0, 0));


		JButton swapButtonX = new JButton("Swap");
		swapButtonX.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				swapX();
			}
		});
		swapButtonX.setBounds(434, 187, 224, 29);
		frame.getContentPane().add(swapButtonX);

		JButton swapButtonO = new JButton("swap");
		swapButtonO.setBounds(430, 389, 232, 29);
		swapButtonO.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				swapO();
			}
		});
		frame.getContentPane().add(swapButtonO);

		JButton playAIMoveButton = new JButton("Play Next Move as AI");
		playAIMoveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!gameCanvas.gameHasStopped()) {
					try {
						currentGame.makeAIMoveForGameState(currentGame.getCurrentGameState());
					} catch (Exception e) {
						e.printStackTrace();
					}					
				}
			}
		});
		playAIMoveButton.setBounds(30, 417, 230, 29);
		frame.getContentPane().add(playAIMoveButton);

		JButton restartButton = new JButton("Restart Game");
		restartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					resetGame();
				} catch (InvariantException e) {
					e.printStackTrace();
				}
			}
		});
		restartButton.setBounds(20, 376, 117, 29);
		frame.getContentPane().add(restartButton);

		listRulesX = new JList();
		listRulesX.setBounds(434, 44, 224, 131);


		listRulesX.setModel(new RuleListModel(gameRulesPlayerX));
		frame.getContentPane().add(listRulesX);

		listRulesO = new JList();
		listRulesO.setBounds(434, 272, 224, 104);
		listRulesO.setModel(new RuleListModel(gameRulesPlayerO));
		frame.getContentPane().add(listRulesO);

		txtInfoTestField = new JTextField();
		txtInfoTestField.setText("Info text field");
		txtInfoTestField.setBounds(149, 376, 273, 28);
		frame.getContentPane().add(txtInfoTestField);
		txtInfoTestField.setColumns(10);

		JLabel lblRulesForPlayer = new JLabel("Rules for Player O:");
		lblRulesForPlayer.setBounds(434, 244, 153, 16);
		frame.getContentPane().add(lblRulesForPlayer);

		JLabel lblRulesForPlayer_1 = new JLabel("Rules for Player X:");
		lblRulesForPlayer_1.setBounds(433, 16, 154, 16);
		frame.getContentPane().add(lblRulesForPlayer_1);
		
		setTextFieldToCurrentStatusDescription();
	}

	/**
	 * Resets the game, but not the rules.
	 * @throws InvariantException 
	 */
	private void resetGame() throws InvariantException{

		int option = JOptionPane.showOptionDialog(this.gameCanvas, "Choose type of new game", "New Game", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Connect-4", "Tic Tac Toe"}, 0);
		if (option == 0) {
			this.currentGame = new GameConnect4(PlayerType.human, PlayerType.human, gameRulesPlayerX, gameRulesPlayerO, false);	
		}else{
			this.currentGame = new GameTicTacToe(PlayerType.human, PlayerType.human, gameRulesPlayerX, gameRulesPlayerO, false);
		}
		currentGame.addObserver(this);

		gameCanvas.resetWithNewGame(currentGame);
		gameCanvas.repaint();
		setTextFieldToCurrentStatusDescription();
	}

	/**
	 * Set up a new game, with new game rules 
	 * @throws InvariantException 
	 */
	private void defaultGameSetup() throws InvariantException{
		System.out.println("setting up game...");
		rulesX = new GameRules();
		rulesO = new GameRules();


		try {
			rulesX.loadRulesFromLocalFile("config/rules.properties");
			rulesX.loadRulesFromLocalFile("config/rules.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		gameRulesPlayerO = GameRuleFactory.rulePlaysForGameRulesForObject(rulesO);
		gameRulesPlayerX = GameRuleFactory.rulePlaysForGameRulesForObject(rulesX);

		currentGame = new GameConnect4(PlayerType.human, PlayerType.human, gameRulesPlayerX, gameRulesPlayerO, false);
		currentGame.addObserver(this);
	}


	/**
	 * Swaps two selected rules for player X. Will refresh also the view.
	 */
	private void swapX(){
		int [] selections = listRulesX.getSelectedIndices();

		if (selections.length == 2) {
			RuleListModel model = (RuleListModel) listRulesX.getModel();
			model.swap(selections[0], selections[1]);
		}
		System.out.println(gameRulesPlayerX.toString());
	}


	/**
	 * Swaps two selected rules for player O. Will refresh also the view.
	 */
	private void swapO(){
		int [] selections = listRulesO.getSelectedIndices();

		if (selections.length == 2) {
			RuleListModel model = (RuleListModel) listRulesO.getModel();
			model.swap(selections[0], selections[1]);
		}
		System.out.println(gameRulesPlayerO.toString());
	}

	/**
	 * checks the current status of the game
	 * and updates the text field with a descripting text depending on it
	 */
	private void setTextFieldToCurrentStatusDescription(){
		String status = "";

		switch (this.currentGame.getCurrentGameState()) {
		case xWins:
			status = "X wins the game!";
			break;
		case oWins:
			status = "O wins the game!";
			break;
		case xPlays:
			status = "X plays";
			break;
		case oPlays:
			status = "O plays";
			break;
		default:
			break;
		}

		this.txtInfoTestField.setText(status);

	}
	@Override
	public void update(Observable observable, Object arg) {
		setTextFieldToCurrentStatusDescription();
	}
}
