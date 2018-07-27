/*
* ConnectX - CMP 326
* Author: Jeffrey Mathew
* Objective: To connect x amount of dots horizontally, vertically or diagonally before your opponent does.
 */



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectX extends JFrame{

    private static ConnectX game = new ConnectX();
    private static JPanel jMain, jBoard, jIO, jButton, jStats;
    private static JLabel jDisplay, jWinner;
    private static JPanel[][] board;
    private static String playerOne, playerTwo, numOfRowsEntry, currPlayer;
    private static int numofRows, p1count, p2count;
    private static JButton[] label, jlabel;
    private static Color backgroundColor, borderColor;
    private static Color yellowIMG, blueIMG, darkBlueIMG;


    static{
       playerOne = JOptionPane.showInputDialog(game, "Enter the name of the First Player", "First Player", JOptionPane.PLAIN_MESSAGE);
       playerTwo = JOptionPane.showInputDialog(game, "Enter the name of the Second Player", "Second Player", JOptionPane.PLAIN_MESSAGE);
      // numOfRowsEntry = JOptionPane.showInputDialog(game, "Enter the number of Rows", "Number of Rows", JOptionPane.PLAIN_MESSAGE);
       //numofRows = Integer.parseInt(numOfRowsEntry);

    }

    private static void CreateGUI(){
        jMain = new JPanel();
        jBoard = new JPanel();
        jButton = new JPanel();
        jIO = new JPanel();
        jDisplay = new JLabel();
        jStats = new JPanel();
        //jMain Settings & Configurations
        jMain.setLayout(new BorderLayout());

        //jIO Settings
        jIO.setLayout(new BorderLayout());
        backgroundColor = new Color(12, 51, 148);
        borderColor = new Color(8, 40, 121);
        yellowIMG = new Color(255, 221, 4);
        blueIMG = new Color(52, 81, 130);
        darkBlueIMG = new Color(40, 63, 102);
        jIO.setBackground(blueIMG);
        jIO.setBorder(BorderFactory.createLineBorder(Color.white, 4));

        jIO.add(jDisplay,BorderLayout.CENTER);
        jIO.add(jStats, BorderLayout.NORTH);
        jIO.add(jButton, BorderLayout.SOUTH);



        //jDisplay Settings
        jDisplay.setHorizontalAlignment(JLabel.CENTER);
        game.updateText("Hey there, "+ playerOne +" goes first!");
        jDisplay.setBorder(BorderFactory.createLineBorder(Color.white, 0));
        currPlayer = playerOne;
        p1count = 0;
        p2count = 0;

        //jButtons Setting
        jButton.setLayout(new GridLayout(1,7));
        game.displayLabelButton();

        //jStats
        jStats.setLayout(new GridLayout(1,4));
        game.setStatsButtons();
        Color
                jlabel0 = new Color(75, 119, 190),
                jlabel1 = new Color(75, 119, 190),
                jlabel2 = new Color(75, 119, 190),
                jlabel3 = new Color(75, 119, 190);
        jlabel[0].setBackground(jlabel0);
        jlabel[1].setBackground(jlabel1);
        jlabel[2].setBackground(jlabel2);
        jlabel[3].setBackground(jlabel3);
        jlabel[0].setBorder(BorderFactory.createLineBorder(jlabel0, 4));
        jlabel[1].setBorder(BorderFactory.createLineBorder(jlabel1, 4));
        jlabel[2].setBorder(BorderFactory.createLineBorder(jlabel2, 4));
        jlabel[3].setBorder(BorderFactory.createLineBorder(jlabel3, 4));
        jlabel[0].setText("<html><h4 color=black>Last Winner: N/A</h4></html>");
        jlabel[1].setText("<html><h4 color=black>Champion: N/A</h4></html>");
        jlabel[2].setText("<html><h4 color=black>"+ playerOne + "'s wins: " + p1count + "</h4></html>");
        jlabel[3].setText("<html><h4 color=black>"+ playerTwo + "'s wins: " + p2count + "</h4></html>");

        //jBoard Settings
        jBoard = new JPanel();
        jBoard.setLayout(new GridLayout(6,7));
        game.displayBoard();

        //Global Settings
        jMain.add(jIO, BorderLayout.NORTH);
        jMain.add(jBoard, BorderLayout.CENTER);
        game.add(jMain);
        game.setTitle("Connect4 - Players (" + playerOne + " - "+p1count+" Wins, " + playerTwo + " - "+p2count+" Wins)");
        game.setDefaultCloseOperation(EXIT_ON_CLOSE);
        game.setSize(900, 600);
        game.setVisible(true);

    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater( new Runnable(){
            public void run() {
                CreateGUI();
            }
        });
    }
    public void updateText(String msg){
        jDisplay.setText("<HTML><H1 color=white>"+msg+"</H1></HTML>");
    }



    public void displayLabelButton(){
        label = new JButton[7];
        for(int i=0; i < label.length; i++){
            label[i]=new JButton();
            label[i].setBackground(darkBlueIMG);
            label[i].setFocusable(false);
            label[i].setBorder(BorderFactory.createLineBorder(darkBlueIMG, 2));
            label[i].setFont(new Font(Font.SANS_SERIF, Font.PLAIN,30));

            label[i].setForeground(darkBlueIMG);
            label[i].addActionListener(this::actionPerformed);
            label[i].setEnabled(true);
            label[i].setText("<html><h4 color=white>Drop Here</h4></html>");
            jButton.add(label[i]);

        }
    }

    public void setStatsButtons(){
        jlabel = new JButton[4];
        for(int i=0; i < jlabel.length; i++){
            jlabel[i]=new JButton();
            jlabel[i].setFocusable(false);
            jlabel[i].setFont(new Font(Font.SANS_SERIF,Font.ITALIC,30));

            jlabel[i].setForeground(Color.black);
            jlabel[i].setEnabled(true);
            jStats.add(jlabel[i]);

        }
    }

    public void displayBoard() {
        board = new JPanel[6][7];
        for(int row=0; row<board.length; row++){
            for(int col=0; col<board[row].length; col++){
                board[row][col] = new JPanel();
                board[row][col].setBackground(backgroundColor);
                board[row][col].setFocusable(false);
                board[row][col].setBorder(BorderFactory.createLineBorder(borderColor, 5));
                jBoard.add(board[row][col]);
            }
        }
    }
    public void actionPerformed(ActionEvent e) {
        JButton btnClicked = (JButton) e.getSource();

        for (int j = 0; j < label.length; j++) {
            if (btnClicked.equals(label[j])) {
                nextDrop(j);

            }
        }
        if(game.isWinner(currPlayer) || game.isFull()){
            game.displayWinner();
            game.playAnotherGame();
        }

        game.updateCurrPlayer();
        game.updateText(currPlayer + "'s Turn");
    }

    public void playAnotherGame(){
        int yesNo = JOptionPane.showConfirmDialog(null, "Do you want to play another game?");
        if(yesNo == 0){
            clearBoard();
            currPlayer = playerOne;
            updateText(currPlayer+" goes first!");
        }
        else{
            updateText("Thanks for playing");
            JOptionPane.showMessageDialog(null, "Thanks for playing!");
            System.exit(EXIT_ON_CLOSE);
        }
        System.out.println(yesNo);
    }

    public void nextDrop(int col){
        for(int row = 5 ; row >=0; row--){
            if((!(board[row][col].getBackground().equals(yellowIMG)) && !(board[row][col].getBackground().equals(Color.RED)) )){
                Color myColor = yellowIMG;
                if(currPlayer.equals(playerTwo)){
                    myColor = Color.RED;
                }
                board[row][col].setBackground(myColor);

                if (row==0){
                    label[col].setEnabled(false);
                }
                break;
            }
        }


    }


    public void updateCurrPlayer() {
        if(currPlayer.equalsIgnoreCase(playerOne)){
            currPlayer = playerTwo;
        }
        else{
            currPlayer = playerOne;
        }

    }

    public boolean isWinner(String player) {


        //Player's Color
        Color myColor;
        //If the current player
        if(currPlayer.equals(playerTwo)){
            myColor = Color.RED;}
        else {
            myColor = yellowIMG;}


        //Checks for all rows
        for(int row=0; row<board.length; row++){
            int rowCount=0;//row match counter, resets on next row
            for(int col=0; col<board[row].length; col++){
                if(board[row][col].getBackground().equals(myColor)){

                    rowCount++;//increment counter
                } else {
                    rowCount=0;
                }
                if(rowCount == 4){
                    return true;//found 3 in same row

                }
            }
        }
        //check columns
        for(int col=0; col<7; col++){
            int colCount=0;
            for(int row=0; row<6; row++){
                if(board[row][col].getBackground().equals(myColor)){
                    colCount++;
                } else {colCount=0;	}
                if(colCount ==4){
                    return true;//found 3 in same column
                }
            }
        }



        //check main diagonal [0][0],[1][1],[2][2]

        int colInc=0;

        for(int i = 0; i < 4 ;i++){
            int col = colInc;
            int  row=0;


            int count=0;

            while (( col <= board.length) && (row < 6)){

                if(board[row][col].getBackground().equals(myColor)){
                    count++;
                } else {
                    count=0;
                }
                if (count == 4){
                    return true;}
                row++;
                col++; }
            colInc++; }


        //check main diagonal lower
        int rowInc=1;

        for(int i = 0; i < 2 ;i++){
            int col =0;
            int  row =rowInc;
            int count =0;


            while (( col <= board.length) && (row < 6)){
                if(board[row][col].getBackground().equals(myColor)){
                    count++;
                } else {
                    count=0;			}
                if (count == 4){
                    return true;

                }
                row++;
                col++;
            }

            System.out.println();

            rowInc++;

        }

        int colInc2 =0;
        for(int i =0; i < 4; i++){
            int rowCount = 5;
            int colCount = colInc2;
            int  countMatch=0;

            while ((colCount <= board.length)&& (rowCount >=0)){
                if(board[rowCount][colCount].getBackground().equals(myColor)){
                    countMatch++;
                } else {
                    countMatch=0;
                }
                if (countMatch == 4){
                    return true;

                }
                colCount++;
                rowCount--;
            }
            colInc2++;
        }

        int rowDec =4;

        for(int i =0; i < 2; i++){
            int rowCount = rowDec;// numrows -2
            int colCount = 0;
            int countMatch=0;

            while ((colCount <= board.length)&& (rowCount >=0)){
                if(board[rowCount][colCount].getBackground().equals(myColor)){
                    countMatch++;}
                else {rowCount=0;	}
                if (countMatch == 4){
                    return true;        	}

                colCount++;
                rowCount--;		}

            rowDec--;	}
        return false;
    }

    public boolean isFull() {
        for(int row=0; row<board.length; row++){
            for(int col=0; col<board[row].length; col++){
                if( !(board[row][col].getBackground().equals(yellowIMG)) && !(board[row][col].getBackground().equals(Color.RED)) ){
                    return false;
                }

            }
        }
        return true;
    }
    public void clearBoard() {
        for(int row=0; row<board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                board[row][col].setBackground(backgroundColor);
            }
            for (int i = 0; i < label.length; i++) {
                label[i].setEnabled(true);

            }
        }
        currPlayer = playerOne;
    }

    public void displayWinner() {


        if(currPlayer.equalsIgnoreCase(playerOne)){
            updateText(playerOne + " is the winner");
            p1count++;
            game.setTitle("Connect4 - Players (" + playerOne + " - "+p1count+" Wins, " + playerTwo + " - "+p2count+" Wins) Last Winner: " + playerOne);
            jlabel[0].setText("<html><h4 color=white>Last Winner: " + playerOne + "</h4></html>");
            jlabel[2].setText("<html><h4 color=white>"+ playerOne + "'s wins: " + p1count + "</h4></html>");

            if(p1count > p2count)
            {
                jlabel[1].setText("<html><h4 color=black>Champion: " + playerOne + "</h4></html>");

            }
            else if(p2count > p1count){
                jlabel[1].setText("<html><h4 color=black>Champion: "+ playerTwo +"</h4></html>");
            }

        }
        else if (currPlayer.equalsIgnoreCase(playerTwo)){
            updateText(playerTwo + " is the winner");
            p2count++;
            game.setTitle("Connect4 - Players (" + playerOne + " - "+p1count+" Wins, " + playerTwo + " - "+p2count+" Wins) Last Winner: " + playerTwo);
            jlabel[0].setText("<html><h4 color=white>Last Winner: "+ playerTwo +"</h4></html>");
            jlabel[3].setText("<html><h4 color=white>"+ playerTwo + "'s wins: " + p2count + "</h4></html>");

            if(p1count > p2count)
            {
                jlabel[1].setText("<html><h4 color=black>Champion: " + playerOne + "</h4></html>");

            }
            else if(p2count > p1count){
                jlabel[1].setText("<html><h4 color=black>Champion: "+ playerTwo +"</h4></html>");
            }

        }
        else
        {
            updateText("DRAW");
        }

    }






}











