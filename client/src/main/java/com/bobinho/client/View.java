package com.bobinho.client;

import com.bobinho.common.interfaces.EColor;
import com.bobinho.common.interfaces.RoomService;
import com.bobinho.common.utils.ConfigUtils;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class View extends JFrame implements MouseListener {

	public static Point GAMEBOARD_TOP_LEFT_CORNER;
	public static int GAMEBOARD_SIZE;
	public static int SQUARE_SIZE;

	private DrawingPanel drawingPanel;
	private Map<String, RoomService> availableRooms;
	private Controller controller;
	private JPanel panneau;
	private JPanel scorePanel;
	List<String> players;

	public View(String title) {
		super(title);
		this.build();
	}

	public void linkController(Controller controller) {
		this.controller = controller;

		while (!this.controller.register(JOptionPane.showInputDialog("Enter your name"))) ;
	}

	public void refreshAvailableRoom(Map<String, RoomService> availableRooms) {
		this.availableRooms = availableRooms;
	}

	public void getAvailableRoom() {
		this.controller.getAvailableRoom();
	}

	private void build() {
		this.drawingPanel = new DrawingPanel();
		this.drawingPanel.addMouseListener(this);
		this.panneau = new JPanel(new GridLayout(2, 2));

		buildContentPane();
	}

	private void buildContentPane() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		getContentPane().add(this.drawingPanel, BorderLayout.CENTER);
		setMinimumSize(new Dimension(1400, 700));
		setVisible(true);

		createScorePanel();

		//Creates the bar menu
		JMenuBar menuBar = new JMenuBar();
		this.drawingPanel.add(menuBar);

		//Creates the Filling board menu.
		JMenuItem menuCreateGame = new JMenuItem("Create a room");
		menuCreateGame.addActionListener(e -> {

			String roomName = JOptionPane.showInputDialog("Enter the room's name");

			if (this.controller.isValidRoomName(roomName)) {
				this.getMenuItem(1).setEnabled(false);
				this.getMenuItem(2).setEnabled(false);

				((JTextField) scorePanel.getComponents()[1]).setText("Waiting for a second player...");

				SwingUtilities.invokeLater(() -> this.controller.createRoom(roomName));
			} else {
				JOptionPane.showMessageDialog(this, "The room name must be at least 3 characters long! (" + roomName + ")", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		menuBar.add(menuCreateGame);

		//Creates the Rules menu
		JMenuItem menuRules = new JMenuItem("Join a room");
		JPopupMenu popupMenu = new JPopupMenu();

		menuRules.addActionListener(event -> {
			this.controller.getAvailableRoom();
			popupMenu.removeAll();

			for (Map.Entry<String, RoomService> room : this.availableRooms.entrySet()) {
				JRadioButtonMenuItem ruleButton = new JRadioButtonMenuItem(room.getKey());
				ruleButton.addActionListener(e -> {
					popupMenu.setVisible(false);
					if (!this.controller.joinRoom(room.getValue())) {
						JOptionPane.showMessageDialog(this, "This room is already full!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				});
				popupMenu.add(ruleButton);
			}

			popupMenu.show(menuRules, 0, menuRules.getHeight());
		});

		menuBar.add(menuRules);

		//Creates the Quit button.
		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(e -> System.exit(EXIT_ON_CLOSE));
		menuBar.add(quit);

		GAMEBOARD_SIZE = (int) (getWidth() / 2.4);
		GAMEBOARD_TOP_LEFT_CORNER = new Point(getWidth() / 2 - GAMEBOARD_SIZE / 2, getHeight() / 2 - GAMEBOARD_SIZE / 2);
		SQUARE_SIZE = (((View.GAMEBOARD_SIZE - 1) / ConfigUtils.BOARD_LENGTH)) - 1;
		GAMEBOARD_SIZE = ConfigUtils.BOARD_LENGTH * (View.SQUARE_SIZE + 1) + 1;
	}

	private void createScorePanel() {
		this.panneau.removeAll();
		scorePanel = new JPanel(new GridLayout(0, 1));
		add(scorePanel, BorderLayout.WEST);
		scorePanel.add(createJTextField(""));
		scorePanel.add(createJTextField("Create or join a room..."));
		this.panneau.add(createJTextField(""));
		this.panneau.add(createJTextField(""));
		this.panneau.add(createJTextField(""));
		this.panneau.add(createJTextField(""));
		scorePanel.add(this.panneau);
		scorePanel.add(createJTextField(""));
	}

	private JTextField createJTextField(String text) {
		JTextField textField = new JTextField(text);
		textField.setEditable(false);
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		return textField;
	}

	private AbstractButton getMenuItem(int i) {
		return (AbstractButton) ((JMenuBar) this.drawingPanel.getComponents()[0]).getComponents()[i - 1];
	}

	private JTextField getPlayerScore(int playerNumber) {
		return (JTextField) this.panneau.getComponents()[2 * playerNumber + 1];
	}

	public void start(List<String> players) {
		((JTextField) this.panneau.getComponents()[0]).setText("   " + players.get(0) + "   ");
		((JTextField) this.panneau.getComponents()[1]).setText("0");
		((JTextField) this.panneau.getComponents()[2]).setText("   " + players.get(1) + "   ");
		((JTextField) this.panneau.getComponents()[3]).setText("0");

		this.players = players;
		for (int i = 1; i < 4; i++) {
			getMenuItem(i).setEnabled(false);
		}
	}

	public void canStart(boolean canStart) {
		getMenuItem(2).setEnabled(canStart);
	}

	public void update(boolean isYourTurn, List<Square> board) {

		((JTextField) scorePanel.getComponents()[1]).setText(isYourTurn ? "It's your turn!" : "Your opponent is playing...");
		((JTextField) this.panneau.getComponents()[1]).setText(String.valueOf(board.stream().filter(square -> square.getColor() == EColor.RED).count()));
		((JTextField) this.panneau.getComponents()[3]).setText(String.valueOf(board.stream().filter(square -> square.getColor() == EColor.BLUE).count()));

		this.drawingPanel.repaint(board);
	}

	@Override
	public void mousePressed(MouseEvent event) {
		try {
			controller.click(event.getPoint(), new Robot().getPixelColor(event.getLocationOnScreen().x, event.getLocationOnScreen().y));
		} catch (AWTException | RemoteException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

}