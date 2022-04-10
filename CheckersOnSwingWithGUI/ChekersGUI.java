import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.lang.*;
import java.time.LocalTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.Random;
import java.io.IOException;
import static java.time.temporal.ChronoUnit.SECONDS;

public class ChekersGUI implements ActionListener {
    private boolean boardInitialised = false;
    private boolean isAiActive=false;
    private int blackTime=0;
    private int whiteTime=0;
    private int blackPawnCounter=12,whitePawnCounter=12;
    private static final String COLS = "ABCDEFGH";
    private final Board mechanicsBoard = new Board();
    private final JPanel gui = new JPanel(new BorderLayout());
    private JPanel panelBoard;
    private final JToolBar options = new JToolBar();
    private final JToolBar options2 = new JToolBar();
    private final JButton[][] BoardSquares = new JButton[10][10];
    private final JButton moveIcon = new JButton("<- turn");
    private final JButton time = new JButton("Black:" + blackTime+"s White:" + whiteTime+"s");
    private final JButton ai2 = new JButton("Communication Board");
    private final JButton pvp = new JButton(new AbstractAction("Play with another player"){
        @Override
        public void actionPerformed( ActionEvent e ) {
            isAiActive=false;
        }
    });
    private final JButton ai = new JButton(new AbstractAction(" Play with a computer") {
        @Override
        public void actionPerformed( ActionEvent e ) {
            isAiActive=true;
        }
    });


    ChekersGUI() {
        try {
            initializeGui();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void decrementPawnNumber(int opColor){
        if(opColor==2){
            whitePawnCounter=whitePawnCounter-1;
        }else {
            blackPawnCounter=blackPawnCounter-1;
        }
    }
    public int victoryAchieved(int myColor){
        if(myColor==2){
            if(blackPawnCounter==0){
                ai2.setText("---Hooray, White victory!---");
                return 0;
            }
        }else{
            if(whitePawnCounter==0) {
                ai2.setText("---Hooray, Black victory!---");
                return 0;
            }
        }
        return 1;
    }

    public final void initializeGui() throws IOException {
        Color grey = new Color(112, 128, 144);
        Color grey2 = new Color(176, 196, 222);
        Border emptyBorder = BorderFactory.createLineBorder(grey, 2);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  //take the screen dimension to make nice size for the board
        int height = (int) screenSize.getHeight();

        options.setBackground(grey);
        gui.add(options, BorderLayout.NORTH);
        options.setLayout(new GridLayout(1, 0));

        ai.setBackground(grey2);
        ai.setBorder(emptyBorder);
        ai.setFont(new Font("Manjari", Font.BOLD, height / 50));


        pvp.setBackground(grey2);
        pvp.setBorder(emptyBorder);
        pvp.setFont(new Font("Manjari", Font.BOLD, height / 50));

        time.setBackground(grey2);
        time.setBorder(emptyBorder);
        time.setFont(new Font("Manjari", Font.BOLD, height / 50));

        moveIcon.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("Grey_pawn_smol.png"))));
        moveIcon.setBackground(grey2);
        moveIcon.setBorder(emptyBorder);
        moveIcon.setFont(new Font("Manjari", Font.BOLD, height / 50));
        if (!boardInitialised) {
        options.add(pvp);
        options.add(ai);
        options.setBorder(emptyBorder);
        options.add(time);
        options.add(moveIcon);

        panelBoard = new JPanel(new GridLayout(10, 10));  //setting up the board and its borders
        panelBoard.setBackground(grey);
        gui.add(panelBoard, BorderLayout.CENTER);

        //options2.setBackground(grey);
        options.setLayout(new GridLayout(1, 0));
        gui.add(options2, BorderLayout.SOUTH);

        ai2.setBackground(grey2);
        ai2.setBorder(emptyBorder);
        ai2.setFont(new Font("Manjari", Font.BOLD, height / 40));
        options2.add(ai2);
        options2.setBorder(emptyBorder);
        options2.setLayout(new GridLayout(1, 0));
        }
        mechanicsBoard.initiateBoard();
        int pawncolor;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i == 0 && j == 0 || i == 0 && j == 9 || i == 9 && j == 0 || i == 9 && j == 9) {  //all those 3 ifs are for the borders with info, not the board
                    JLabel templabel = new JLabel();
                    templabel.setPreferredSize(new Dimension(height / 48, height / 48));
                    panelBoard.add(templabel);
                    continue;
                }
                if (i == 0 || i == 9) {
                    JLabel templabel = new JLabel(String.valueOf(COLS.charAt(j - 1)), SwingConstants.CENTER);
                    templabel.setFont(new Font("Manjari", Font.PLAIN, height / 36));
                    templabel.setPreferredSize(new Dimension(height / 48, height / 48));
                    panelBoard.add(templabel);
                    continue;
                }
                if (j == 0 || j == 9) {
                        String temp = String.valueOf( 9-i);  //laurix pamietaj to jest odwrocone 9-i zmien potem
                    JLabel templabel = new JLabel(temp, SwingConstants.CENTER);
                    templabel.setFont(new Font("Manjari", Font.PLAIN, height / 36));
                    templabel.setPreferredSize(new Dimension(height / 48, height / 48));
                    panelBoard.add(templabel);
                    continue;
                }
                Icon icon = null;
                pawncolor = mechanicsBoard.getPawnColor(i, j);
                if (pawncolor == 2) {
                    icon = new ImageIcon(getClass().getResource("Grey_pawn_smol.png"));
                } else if (pawncolor == 1) {
                    icon = new ImageIcon(getClass().getResource("Gold_pawn_smol.png"));
                }
                JButton button = new JButton(icon);
                button.setName(String.valueOf(i)+String.valueOf(j));
                button.setPreferredSize(new Dimension(height / 12, height / 12));
                if (pawncolor != 8) {
                    button.setBackground(Color.BLACK);
                    button.addActionListener(this);
                }
                BoardSquares[i][j] = button;
                panelBoard.add(BoardSquares[i][j]);
            }
        }
    boardInitialised=true;
    }

    public JComponent getGui() {
        return gui;
    }

    public int change(int ifrom, int jfrom, int i, int j, int mycolor, int oponentcolor) {
        System.out.println(" ");
        int nextmoveexist;
        if(ruchpozbiciu){
            if(Math.abs(ifrom-i)>2||Math.abs(jfrom-j)>2){
                return -1;
            }
            if(Math.abs(ifrom-i)==1&&Math.abs(jfrom-j)==1){
                if(mechanicsBoard.getPawnColor(i,j)!=oponentcolor){
                    return -1;
                }
            }
            if(Math.abs(ifrom-i)==2&&Math.abs(jfrom-j)==2){
                if(mechanicsBoard.getPawnColor((i+ifrom)/2,(j+jfrom)/2)!=oponentcolor){
                    System.out.println(i+ifrom/2 + "  "+ j+jfrom/2);
                    return -1;
                }
            }
        }
            if(Math.abs(ifrom-i)!=Math.abs(jfrom-j)){
                return -1;
            }
        if (mechanicsBoard.getPawnType(ifrom, jfrom) == 0) {
                if(mycolor==2){
                    if(ifrom-i==-1){
                        if(mechanicsBoard.getPawnColor(i,j)!=oponentcolor){
                            return -1;
                        }
                    }
                }
            if(mycolor==1){
                if(ifrom-i==1){
                    if(mechanicsBoard.getPawnColor(i,j)!=oponentcolor){
                        return -1;
                    }
                }
            }
                if (i - ifrom == 2) {
                    i = i - 1;
                } else if (i - ifrom == -2) {
                    i = i + 1;
                }
                if (j - jfrom == 2) {
                    j = j - 1;
                } else if (j - jfrom == -2) {
                    j = j + 1;
                }
            if (ifrom > i) {
                if (jfrom > j) {
                    nextmoveexist = mechanicsBoard.moveNorthWest(ifrom, jfrom, mycolor, oponentcolor);
                    if (nextmoveexist == -2) {
                        return -1;
                    } else if (nextmoveexist == -1) {
                        return -1;
                    } else if (nextmoveexist == 1) {
                        BoardSquares[ifrom][jfrom].setIcon(null);
                        BoardSquares[i][j].setIcon(null);
                        decrementPawnNumber(oponentcolor);
                        if (mycolor == 2) {
                            BoardSquares[i - 1][j - 1].setIcon(new ImageIcon(getClass().getResource("Grey_pawn_smol.png")));
                        } else {
                            BoardSquares[i - 1][j - 1].setIcon(new ImageIcon(getClass().getResource("Gold_pawn_smol.png")));
                        }
                        iFrom = i - 1;
                        jFrom = j - 1;
                        if (mycolor == 2 && iFrom == 1) {
                            BoardSquares[iFrom][jFrom].setIcon(new ImageIcon(getClass().getResource("Grey_pawn_smol_damka.png")));
                            mechanicsBoard.setPawnTypeDamka(iFrom, jFrom);
                        }
                        return 1;
                    } else {
                        BoardSquares[ifrom][jfrom].setIcon(null);
                        if (mycolor == 2) {
                            BoardSquares[i][j].setIcon(new ImageIcon(getClass().getResource("Grey_pawn_smol.png")));
                        } else {
                            BoardSquares[i][j].setIcon(new ImageIcon(getClass().getResource("Gold_pawn_smol.png")));
                        }
                        if (mycolor == 2 && i == 1) {
                            BoardSquares[i][j].setIcon(new ImageIcon(getClass().getResource("Grey_pawn_smol_damka.png")));
                            mechanicsBoard.setPawnTypeDamka(i, j);
                        }
                        return 0;
                    }
                } else {
                    nextmoveexist = mechanicsBoard.moveNorthEast(ifrom, jfrom, mycolor, oponentcolor);
                    if (nextmoveexist == -2) {
                        return -1;
                    } else if (nextmoveexist == -1) {
                        return -1;
                    } else if (nextmoveexist == 1) {
                        BoardSquares[ifrom][jfrom].setIcon(null);
                        BoardSquares[i][j].setIcon(null);
                        decrementPawnNumber(oponentcolor);
                        if (mycolor == 2) {
                            BoardSquares[i - 1][j + 1].setIcon(new ImageIcon(getClass().getResource("Grey_pawn_smol.png")));
                        } else {
                            BoardSquares[i - 1][j + 1].setIcon(new ImageIcon(getClass().getResource("Gold_pawn_smol.png")));
                        }
                        iFrom = i - 1;
                        jFrom = j + 1;
                        if (mycolor == 2 && iFrom == 1) {
                            BoardSquares[iFrom][jFrom].setIcon(new ImageIcon(getClass().getResource("Grey_pawn_smol_damka.png")));
                            mechanicsBoard.setPawnTypeDamka(iFrom, jFrom);
                        }
                        return 1;
                    } else {
                        BoardSquares[ifrom][jfrom].setIcon(null);
                        if (mycolor == 2) {
                            BoardSquares[i][j].setIcon(new ImageIcon(getClass().getResource("Grey_pawn_smol.png")));
                        } else {
                            BoardSquares[i][j].setIcon(new ImageIcon(getClass().getResource("Gold_pawn_smol.png")));
                        }
                        if (mycolor == 2 && i == 1) {
                            BoardSquares[i][j].setIcon(new ImageIcon(getClass().getResource("Grey_pawn_smol_damka.png")));
                            mechanicsBoard.setPawnTypeDamka(i, j);
                        }
                    }
                }
            } else {
                if (jfrom > j) {
                    nextmoveexist = mechanicsBoard.moveSouthWest(ifrom, jfrom, mycolor, oponentcolor);
                    if (nextmoveexist == -2) {
                        return -1;
                    } else if (nextmoveexist == -1) {
                        return -1;
                    } else if (nextmoveexist == 1) {
                        BoardSquares[ifrom][jfrom].setIcon(null);
                        BoardSquares[i][j].setIcon(null);
                        decrementPawnNumber(oponentcolor);
                        if (mycolor == 2) {
                            BoardSquares[i + 1][j - 1].setIcon(new ImageIcon(getClass().getResource("Grey_pawn_smol.png")));
                        } else {
                            BoardSquares[i + 1][j - 1].setIcon(new ImageIcon(getClass().getResource("Gold_pawn_smol.png")));
                        }
                        iFrom = i + 1;
                        jFrom = j - 1;
                        if (mycolor == 1 && iFrom == 8) {
                            BoardSquares[iFrom][jFrom].setIcon(new ImageIcon(getClass().getResource("Gold_pawn_smol_damka.png")));
                            mechanicsBoard.setPawnTypeDamka(iFrom, jFrom);
                        }
                        return 1;
                    } else {
                        BoardSquares[ifrom][jfrom].setIcon(null);
                        if (mycolor == 2) {
                            BoardSquares[i][j].setIcon(new ImageIcon(getClass().getResource("Grey_pawn_smol.png")));
                        } else {
                            BoardSquares[i][j].setIcon(new ImageIcon(getClass().getResource("Gold_pawn_smol.png")));
                        }
                        if (mycolor == 1 && iFrom == 8) {
                            BoardSquares[i][j].setIcon(new ImageIcon(getClass().getResource("Gold_pawn_smol_damka.png")));
                            mechanicsBoard.setPawnTypeDamka(i, j);
                        }
                    }
                } else {
                    nextmoveexist = mechanicsBoard.moveSouthEast(ifrom, jfrom, mycolor, oponentcolor);
                    if (nextmoveexist == -2) {
                        return -1;
                    } else if (nextmoveexist == -1) {
                        return -1;
                    } else if (nextmoveexist == 1) {
                        BoardSquares[ifrom][jfrom].setIcon(null);
                        BoardSquares[i][j].setIcon(null);
                        decrementPawnNumber(oponentcolor);
                        if (mycolor == 2) {
                            BoardSquares[i + 1][j + 1].setIcon(new ImageIcon(getClass().getResource("Grey_pawn_smol.png")));
                        } else {
                            BoardSquares[i + 1][j + 1].setIcon(new ImageIcon(getClass().getResource("Gold_pawn_smol.png")));
                        }
                        iFrom = i + 1;
                        jFrom = j + 1;
                        if (mycolor == 1 && iFrom == 8) {
                            BoardSquares[iFrom][jFrom].setIcon(new ImageIcon(getClass().getResource("Gold_pawn_smol_damka.png")));
                            mechanicsBoard.setPawnTypeDamka(iFrom, jFrom);
                        }
                        return 1;
                    } else {
                        BoardSquares[ifrom][jfrom].setIcon(null);
                        if (mycolor == 2) {
                            BoardSquares[i][j].setIcon(new ImageIcon(getClass().getResource("Grey_pawn_smol.png")));
                        } else {
                            BoardSquares[i][j].setIcon(new ImageIcon(getClass().getResource("Gold_pawn_smol.png")));
                        }
                        if (mycolor == 1 && iFrom == 8) {
                            BoardSquares[i][j].setIcon(new ImageIcon(getClass().getResource("Gold_pawn_smol_damka.png")));
                            mechanicsBoard.setPawnTypeDamka(i, j);
                        }
                    }
                }
            }
        } else {
            if(ifrom>i){
                if(jfrom>j){
                    if(mechanicsBoard.getPawnColor(i,j)==0&&mechanicsBoard.getPawnColor(i+1,j+1)==oponentcolor){ //jezeli kliknie wczesniej
                        i+=1;
                        j+=1;
                    }
                    nextmoveexist=mechanicsBoard.moveNorthWestQueen(ifrom,jfrom,i,j,mycolor,oponentcolor);
                    if (nextmoveexist == -1) {
                        return -1;
                    } else if (nextmoveexist == 1) { //if jump
                        BoardSquares[ifrom][jfrom].setIcon(null);
                        BoardSquares[i][j].setIcon(null);
                        i-=1;
                        j-=1;
                        decrementPawnNumber(oponentcolor);
                        if (mycolor == 2) {
                            BoardSquares[i][j].setIcon(new ImageIcon(getClass().getResource("Grey_pawn_smol_damka.png")));
                        } else {
                            BoardSquares[i][j].setIcon(new ImageIcon(getClass().getResource("Gold_pawn_smol_damka.png")));
                        }
                        iFrom = i;
                        jFrom = j;
                        return 1;
                    } else {
                        BoardSquares[ifrom][jfrom].setIcon(null);
                        if (mycolor == 2) {
                            BoardSquares[i][j].setIcon(new ImageIcon(getClass().getResource("Grey_pawn_smol_damka.png")));
                        } else {
                            BoardSquares[i][j].setIcon(new ImageIcon(getClass().getResource("Gold_pawn_smol_damka.png")));
                        }
                        return 0;
                    }
                }else{
                    if(mechanicsBoard.getPawnColor(i,j)==0&&mechanicsBoard.getPawnColor(i+1,j-1)==oponentcolor){
                        i+=1;
                        j-=1;
                    }
                    nextmoveexist=mechanicsBoard.moveNorthEastQueen(ifrom,jfrom,i,j,mycolor,oponentcolor);
                    if (nextmoveexist == -1) {
                        return -1;
                    } else if (nextmoveexist == 1) { //if jump
                        BoardSquares[ifrom][jfrom].setIcon(null);
                        BoardSquares[i][j].setIcon(null);
                        i-=1;
                        j+=1;
                        decrementPawnNumber(oponentcolor);
                        if (mycolor == 2) {
                            BoardSquares[i][j].setIcon(new ImageIcon(getClass().getResource("Grey_pawn_smol_damka.png")));
                        } else {
                            BoardSquares[i][j].setIcon(new ImageIcon(getClass().getResource("Gold_pawn_smol_damka.png")));
                        }
                        iFrom = i;
                        jFrom = j;
                        return 1;
                    } else {
                        BoardSquares[ifrom][jfrom].setIcon(null);
                        if (mycolor == 2) {
                            BoardSquares[i][j].setIcon(new ImageIcon(getClass().getResource("Grey_pawn_smol_damka.png")));
                        } else {
                            BoardSquares[i][j].setIcon(new ImageIcon(getClass().getResource("Gold_pawn_smol_damka.png")));
                        }
                        return 0;
                    }
                }
            }else{
                if(jfrom>j){
                    if(mechanicsBoard.getPawnColor(i,j)==0&&mechanicsBoard.getPawnColor(i-1,j+1)==oponentcolor){
                        i-=1;
                        j+=1;
                    }
                    nextmoveexist=mechanicsBoard.moveSouthWestQueen(ifrom,jfrom,i,j,mycolor,oponentcolor);
                    if (nextmoveexist == -1) {
                        return -1;
                    } else if (nextmoveexist == 1) { //if jump
                        BoardSquares[ifrom][jfrom].setIcon(null);
                        BoardSquares[i][j].setIcon(null);
                        i+=1;
                        j-=1;
                        decrementPawnNumber(oponentcolor);
                        if (mycolor == 2) {
                            BoardSquares[i][j].setIcon(new ImageIcon(getClass().getResource("Grey_pawn_smol_damka.png")));
                        } else {
                            BoardSquares[i][j].setIcon(new ImageIcon(getClass().getResource("Gold_pawn_smol_damka.png")));
                        }
                        iFrom = i;
                        jFrom = j;
                        return 1;
                    } else {
                        BoardSquares[ifrom][jfrom].setIcon(null);
                        if (mycolor == 2) {
                            BoardSquares[i][j].setIcon(new ImageIcon(getClass().getResource("Grey_pawn_smol_damka.png")));
                        } else {
                            BoardSquares[i][j].setIcon(new ImageIcon(getClass().getResource("Gold_pawn_smol_damka.png")));
                        }
                        return 0;
                    }
                }else{
                    if(mechanicsBoard.getPawnColor(i,j)==0&&mechanicsBoard.getPawnColor(i-1,j-1)==oponentcolor){
                        i-=1;
                        j-=1;
                    }
                    nextmoveexist=mechanicsBoard.moveSouthEastQueen(ifrom,jfrom,i,j,mycolor,oponentcolor);
                    if (nextmoveexist == -1) {
                        return -1;
                    } else if (nextmoveexist == 1) { //if jump
                        BoardSquares[ifrom][jfrom].setIcon(null);
                        BoardSquares[i][j].setIcon(null);
                        i+=1;
                        j+=1;
                        decrementPawnNumber(oponentcolor);
                        if (mycolor == 2) {
                            BoardSquares[i][j].setIcon(new ImageIcon(getClass().getResource("Grey_pawn_smol_damka.png")));
                        } else {
                            BoardSquares[i][j].setIcon(new ImageIcon(getClass().getResource("Gold_pawn_smol_damka.png")));
                        }
                        iFrom = i;  //this may be bad
                        jFrom = j;
                        return 1;
                    } else {
                        BoardSquares[ifrom][jfrom].setIcon(null);
                        if (mycolor == 2) {
                            BoardSquares[i][j].setIcon(new ImageIcon(getClass().getResource("Grey_pawn_smol_damka.png")));
                        } else {
                            BoardSquares[i][j].setIcon(new ImageIcon(getClass().getResource("Gold_pawn_smol_damka.png")));
                        }
                        return 0;
                    }
                }
            }
        }
        return -2;
    }
    int czyjruch = 2; //2biale, 1czarne
    int iFrom=0, jFrom=0;
    boolean ruchpozbiciu = false; boolean timerStarted=false;
    LocalTime start, stop;
    @Override
    public void actionPerformed(ActionEvent e) {
        if(!timerStarted) {
            start = LocalTime.now();
            timerStarted=true;
        }
        ai2.setText(" ");
        JButton temp = (JButton) e.getSource();
        if(iFrom==0&&jFrom==0) {
            iFrom = temp.getName().charAt(0)-48;
            jFrom = temp.getName().charAt(1)-48;
            if(mechanicsBoard.getPawnColor(iFrom,jFrom)==czyjruch%2+1){
                ai2.setText("Not your move!");
                iFrom =0;
                jFrom=0;
                return;
            }else if(mechanicsBoard.getPawnColor(iFrom,jFrom)==0){
                ai2.setText("No pawn on this button!");
                iFrom =0;
                jFrom=0;
                return;
            }
            if(mechanicsBoard.possibleanymoveBEFOREJUMP(iFrom, jFrom, czyjruch, czyjruch%2+1)==-1){
                iFrom =0;
                jFrom=0;
                ai2.setText("This pawn cannot move, choose another one!");
                return;
            }
            return;
        }else {
            int i, j;
            i = temp.getName().charAt(0) - 48;
            j = temp.getName().charAt(1) - 48;
            if(iFrom==i&&jFrom==j){
                return;
            }
            if ((Math.abs(i - iFrom) > 2 || Math.abs(j - jFrom) > 2)&&mechanicsBoard.getPawnType(iFrom,jFrom)==0) {
                ai2.setText("Wrong move, ya lost your turn");
            } else {
                int changechecker;
                    changechecker = change(iFrom, jFrom, i, j, czyjruch, czyjruch % 2 + 1);
                if(victoryAchieved(czyjruch)==0){
                    return; ///nwm moze cos lepszego dodaj na koniec, reinicjalizacje np
                }
                if (changechecker == -1) {
                    ai2.setText("Wrong move, ya lost your turn");
                }
                if (changechecker == 1) {
                    if (mechanicsBoard.possibleanymoveAFTERJUMP(iFrom, jFrom, czyjruch, czyjruch % 2 + 1) == -1) {
                        ai2.setText("Nice one, unfortunately there's no next move possible!");
                    } else {
                        ai2.setText("Nice one, next move with this pawn exist, ya can do it!");
                        ruchpozbiciu=true;
                        return;
                    }
                }
            }
        }
        ruchpozbiciu=false;
        stop=LocalTime.now();
        czyjruch=czyjruch%2+1;
        if(czyjruch==1){
            moveIcon.setIcon(new ImageIcon(getClass().getResource("Gold_pawn_smol.png")));
            whiteTime+=SECONDS.between(start,stop)+1;
        }else{
            moveIcon.setIcon(new ImageIcon(getClass().getResource("Grey_pawn_smol.png")));
            blackTime+=SECONDS.between(start,stop)+1;
        }
        start=LocalTime.now();
        time.setText("Black:" + blackTime +"s White:" + whiteTime+"s");
        if(isAiActive) {
            iFrom = 0;
            jFrom = 0;
            ai(czyjruch, czyjruch % 2 + 1);
        }
        iFrom =0;
        jFrom=0;
    }

    public void ai(int aicolor, int playercolor){
        Random rand = new Random();
        int timeDelay = rand.nextInt(3);
        try {
            TimeUnit.SECONDS.sleep(timeDelay);
        } catch (InterruptedException e) {
            System.out.println("");
        }
        for(int i = 1; i<9;i++) {
            for (int j = 1; j < 9; j++) {
                if (mechanicsBoard.possibleMoveNorthWest(i, j, aicolor, playercolor) == 1) {
                    iFrom = i;
                    jFrom = j;
                    break;
                }
                if (mechanicsBoard.possibleMoveNorthEast(i, j, aicolor, playercolor) == 1) {
                    iFrom = i;
                    jFrom = j;
                    break;
                }
                if (mechanicsBoard.possibleMoveSouthWest(i, j, aicolor, playercolor) == 1) {
                    iFrom = i;
                    jFrom = j;
                    break;
                }
                if (mechanicsBoard.possibleMoveSouthEast(i, j, aicolor, playercolor) == 1) {
                    iFrom = i;
                    jFrom = j;
                    break;
                }
            }
        }
        if(iFrom==0){
            for(int i = 1; i<9;i++) {
                for (int j = 1; j < 9; j++) {
                    if (mechanicsBoard.possibleMoveSouthWest(i, j, aicolor, playercolor) == 0) {
                        iFrom = i;
                        jFrom = j;
                        break;
                    }
                    if (mechanicsBoard.possibleMoveSouthEast(i, j, aicolor, playercolor) == 0) {
                        iFrom = i;
                        jFrom = j;
                        break;
                    }
                }
            }
        }
        int posibleMOV;
        while(true) {
            posibleMOV = mechanicsBoard.possibleMoveNorthWest(iFrom, jFrom, aicolor, playercolor);
            if (posibleMOV > 0) {
                change(iFrom, jFrom, iFrom - 1, jFrom - 1, aicolor, playercolor);
                break;
            }
            posibleMOV = mechanicsBoard.possibleMoveNorthEast(iFrom, jFrom, aicolor, playercolor);
            if (posibleMOV > 0) {
                change(iFrom, jFrom, iFrom - 1, jFrom + 1, aicolor, playercolor);
                break;
            }
            posibleMOV = mechanicsBoard.possibleMoveSouthWest(iFrom, jFrom, aicolor, playercolor);
            if (posibleMOV >= 0) {
                change(iFrom, jFrom, iFrom + 1, jFrom - 1, aicolor, playercolor);
                break;
            }
            posibleMOV = mechanicsBoard.possibleMoveSouthEast(iFrom, jFrom, aicolor, playercolor);
            if (posibleMOV >= 0) {
                change(iFrom, jFrom, iFrom + 1, jFrom + 1, aicolor, playercolor);
                break;
            }
        }
            stop=LocalTime.now();
            blackTime+=SECONDS.between(start,stop)+1;
            time.setText("Black:" + blackTime +"s White:" + whiteTime+"s");
            czyjruch=czyjruch%2+1;
            moveIcon.setIcon(new ImageIcon(getClass().getResource("Grey_pawn_smol.png")));
            start=LocalTime.now();
    }

    public static void main(String[] args)  {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) screenSize.getHeight();
        Runnable myrun = () -> {
            ChekersGUI cg = new ChekersGUI();
            JFrame frame = new JFrame("Lauretanian Cheeckers");
            frame.setLocationByPlatform(true);
            frame.setMinimumSize(new Dimension((int) (height * 0.5-height/70), (int) (height * 0.5 )));
            frame.add(cg.getGui());
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
        };
        SwingUtilities.invokeLater(myrun);
    }
}
