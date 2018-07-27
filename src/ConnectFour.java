
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectFour extends JFrame implements ActionListener{

    private JPanel jpMain;
    private JPanel jpBoard;
    private JPanel jpIO;
    private JLabel displayOut;
    private JPanel [][] board;// = new JButton[3][3];
    private String currPlayer = "GREEN";
    private JButton [] label;
    private JPanel jpButton;

    public ConnectFour(){
        setTitle("CONNECT FOUR");

        jpMain = new JPanel();
        jpMain.setLayout(new BorderLayout());

        jpIO = new JPanel();
        jpIO.setLayout(new BorderLayout());
        jpIO.setBackground(Color.BLUE);
        displayOut = new JLabel();
        displayOut.setHorizontalAlignment(JLabel.CENTER);
        updateOut("Let's play ! \'"+currPlayer+"\' goes first");

        jpIO.add(displayOut,BorderLayout.NORTH );

        jpButton = new JPanel();
        jpButton.setLayout(new GridLayout(1,7));
        displayLabelButton();
        jpIO.add(jpButton, BorderLayout.SOUTH);
        jpBoard = new JPanel();
        jpBoard.setLayout(new GridLayout(6,7));
        displayBoard();

        jpMain.add(jpIO, BorderLayout.NORTH);
        jpMain.add(jpBoard, BorderLayout.CENTER);
        add(jpMain);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 600);
        setVisible(true);
    }


    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater( new Runnable(){
            public void run() {
                ConnectFour gui = new ConnectFour();
            }
        });
    }

    public void updateOut(String msg){
        displayOut.setText("<HTML><H1 color=white>"+msg+"</H1></HTML>");
    }
    public void playAnotherGame(){
        int yesNo = JOptionPane.showConfirmDialog(null, "Do you want to play another game?");
        if(yesNo == 0){
            clearBoard();
            updateOut(currPlayer+" goes first!");
        }
        else{
            updateOut("Thanks for playing");
            JOptionPane.showMessageDialog(null, "Thanks for playing!");
            System.exit(EXIT_ON_CLOSE);
        }
        System.out.println(yesNo);
    }

    public void nextDrop(int col){
        for(int row = 5 ; row >=0; row--){
            if((!(board[row][col].getBackground().equals(Color.GREEN)) && !(board[row][col].getBackground().equals(Color.RED)) )){
                Color myColor = Color.GREEN;
                if(currPlayer.equals("RED")){
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

    public void actionPerformed(ActionEvent e) {
        JButton btnClicked = (JButton) e.getSource();

        for(int j=0; j< label.length; j++){
            if(btnClicked.equals(label[j])) {
                nextDrop(j);

            }
        }

        if(isWinner(currPlayer) || isFull()){
            displayWinner();
            playAnotherGame();
        }


        updateCurrPlayer();
        updateOut(currPlayer + "'s TURN");

    }

    public void displayLabelButton(){
        label = new JButton[7];
        for(int i=0; i < label.length; i++){
            label[i]=new JButton();
            label[i].setBackground(Color.BLUE);
            label[i].setFocusable(false);
            label[i].setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            label[i].setFont(new Font(Font.SANS_SERIF,Font.ITALIC,30));

            label[i].setForeground(Color.WHITE);
            label[i].addActionListener(this);
            label[i].setEnabled(true);
            label[i].setText("C"+ (i+1));
            jpButton.add(label[i]);

        }
    }

    public void displayBoard() {
        board = new JPanel[6][7];
        for(int row=0; row<board.length; row++){
            for(int col=0; col<board[row].length; col++){
                board[row][col] = new JPanel();
                board[row][col].setBackground(Color.BLUE);
                board[row][col].setFocusable(false);
                board[row][col].setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
                jpBoard.add(board[row][col]);
            }
        }
    }

    public void clearBoard() {
        for(int row=0; row<board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                board[row][col].setBackground(Color.BLUE);
            }
            for (int i = 0; i < label.length; i++) {
                label[i].setEnabled(true);

            }
        }
    }

    public void displayWinner() {


        if(currPlayer.equalsIgnoreCase("GREEN")){
            updateOut("GREEN is the winner");
        }
        else if (currPlayer.equalsIgnoreCase("RED")){
            updateOut("RED is the winner");
        }
        else
        {
            updateOut("DRAW");
        }


        /*
        if(this.isWinner("GREEN")){
            updateOut("GREEN is the winner");
        }
        else if(this.isWinner("RED")){
            updateOut("RED is the winner");
        }
        else{
            updateOut("DRAW");
        }*/
    }



    public void updateCurrPlayer() {
        if(currPlayer.equalsIgnoreCase("GREEN")){
            currPlayer = "RED";
        }
        else{
            currPlayer = "GREEN";
        }

    }


    public boolean isWinner(String player) {

        Color myColor;
        if(currPlayer.equals("RED")){
            myColor = Color.RED;}
        else {
            myColor = Color.GREEN;}


        //check rows
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
                if( !(board[row][col].getBackground().equals(Color.GREEN)) && !(board[row][col].getBackground().equals(Color.RED)) ){
                    return false;
                }

            }
        }
        return true;
    }

}

