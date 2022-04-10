public class Board {

    private Pawn[][] board = new Pawn[10][10];
    public int getPawnColor(int i, int j){
        return board[i][j].color;
    }
    public void setPawnTypeDamka(int i, int j){
        board[i][j].type=1;
    }
    public int getPawnType(int i, int j){
        return board[i][j].type;
    }

    public void initiateBoard(){
    for(int i = 0; i<10;i++){
        for(int j = 0; j<10;j++) {
            if((i+j)%2==0||i==0||j==0||i==9||j==9){
                Pawn pawn1 = new Pawn();
                pawn1.setBorderNoPawn();
                board[i][j] = pawn1;
            }else {
                Pawn pawn1 = new Pawn();
                pawn1.setNUllPawn();
                board[i][j] = pawn1;
            }
        }
    }
        for(int i = 1; i<9;i++){
            for(int j = 1; j<9;j++){
                if(i<4){
                    if((i+j)%2==1){
                        Pawn pawn1 = new Pawn();
                        pawn1.setBlackPawn();
                        board[i][j] = pawn1;
                    }
                }else if(i>5){
                    if((i+j)%2==1){
                        Pawn pawn1 = new Pawn();
                        pawn1.setWhitePawn();
                        board[i][j] = pawn1;
                    }
                }
            }
        }
    }

    public int moveNorthWest(int i, int j, int mycolor, int oponentcolor){
        if(board[i][j].color==mycolor){
            if(board[i-1][j-1].color==0){
                board[i-1][j-1].color=mycolor;
                board[i][j].color=0;
                return 0;
            }else if(board[i-1][j-1].color==oponentcolor){
                if(board[i-2][j-2].color==0){
                    board[i-2][j-2].color=mycolor;
                    board[i-1][j-1].color=0;
                    board[i-1][j-1].type=0;
                    board[i][j].color=0;
                    return 1;
                }else{
                    return -1;
                }
            }else{
                return -1;
            }
        }
        return -1;
    }
    public int moveSouthWest(int i, int j, int mycolor, int oponentcolor){
        if(board[i][j].color==mycolor){
            if(board[i+1][j-1].color==0){
                board[i+1][j-1].color=mycolor;
                board[i][j].color=0;
                return 0;
            }else if(board[i+1][j-1].color==oponentcolor){
                if(board[i+2][j-2].color==0){
                    board[i+2][j-2].color=mycolor;
                    board[i+1][j-1].color=0;
                    board[i+1][j-1].type=0;
                    board[i][j].color=0;
                    return 1;
                }else{
                    return -1;
                }
            }else{
                return -1;
            }
        }
        return -1;
    }
    public int moveSouthEast(int i, int j, int mycolor, int oponentcolor){
        if(board[i][j].color==mycolor){
            if(board[i+1][j+1].color==0){
                board[i+1][j+1].color=mycolor;
                board[i][j].color=0;
                return 0;
            }else if(board[i+1][j+1].color==oponentcolor){
                if(board[i+2][j+2].color==0){
                    board[i+2][j+2].color=mycolor;
                    board[i+1][j+1].color=0;
                    board[i+1][j+1].type=0;
                    board[i][j].color=0;
                    return 1;
                }else{
                    return -1;
                }
            }else{
                return -1;
            }
        }
        return -1;
    }
    public int moveNorthEast(int i, int j, int mycolor, int oponentcolor){
        if(board[i][j].color==mycolor){
            if(board[i-1][j+1].color==0){
                board[i-1][j+1].color=mycolor;
                board[i][j].color=0;
                return 0;
            }else if(board[i-1][j+1].color==oponentcolor){
                if(board[i-2][j+2].color==0){
                    board[i-2][j+2].color=mycolor;
                    board[i-1][j+1].color=0;
                    board[i-1][j+1].type=0;
                    board[i][j].color=0;
                    System.out.println( board[i-2][j+2].color);
                    return 1;
                }else{
                    return -1;
                }
            }else{
                return -1;
            }
        }
        return -1;
    }
    public int moveNorthWestQueen(int ifrom, int jfrom, int i, int j, int mycolor, int oponentcolor){ //done?
        System.out.println("moveNorthWestQueen");
        boolean skok = false;
        if(board[ifrom][jfrom].color==mycolor) {
            if (board[i][j].color == oponentcolor && board[i - 1][j - 1].color == 0) {
                skok = true;
            }
            for (int k = ifrom-1, l = jfrom-1; k > i; k--, l--) {
                if (board[k][l].color != 0) {
                    return -1;
                }
            }
            if (skok) {
                board[ifrom][jfrom].color = 0;
                board[ifrom][jfrom].type = 0;
                board[i][j].color = 0;
                board[i][j].type = 0;
                i = i - 1;
                j = j - 1;
                board[i][j].color = mycolor;
                board[i][j].type = 1;
                return 1;
            } else {
                board[ifrom][jfrom].color = 0;
                board[ifrom][jfrom].type = 0;
                board[i][j].color = mycolor;
                board[i][j].type = 1;
                return 0;
            }
        }
        return -1;
    }
    public int moveSouthWestQueen(int ifrom, int jfrom, int i, int j, int mycolor, int oponentcolor){ //done?
        System.out.println("moveSouthWestQueen");
        boolean skok = false;
        if(board[ifrom][jfrom].color==mycolor) {
            if (board[i][j].color == oponentcolor && board[i + 1][j - 1].color == 0) {
                skok = true;
            }
            for (int k = ifrom+1, l = jfrom-1; k < i; k++, l--) {
                if (board[k][l].color != 0) {
                    return -1;
                }
            }
            if (skok) {
                board[ifrom][jfrom].color = 0;
                board[ifrom][jfrom].type = 0;
                board[i][j].color = 0;
                board[i][j].type = 0;
                i = i + 1;
                j = j - 1;
                board[i][j].color = mycolor;
                board[i][j].type = 1;
                return 1;
            } else {
                board[ifrom][jfrom].color = 0;
                board[ifrom][jfrom].type = 0;
                board[i][j].color = mycolor;
                board[i][j].type = 1;
                return 0;
            }
        }
        return -1;
    }
    public int moveSouthEastQueen(int ifrom, int jfrom, int i, int j, int mycolor, int oponentcolor){
        System.out.println("moveSouthEastQueen");
        boolean skok = false;
        if(board[ifrom][jfrom].color==mycolor) {
            if (board[i][j].color == oponentcolor && board[i + 1][j + 1].color == 0) {
                skok = true;
            }
            for (int k = ifrom+1, l = jfrom+1; k < i; k++, l++) {
                if (board[k][l].color != 0) {
                    return -1;
                }
            }
            if (skok) {
                board[ifrom][jfrom].color = 0;
                board[ifrom][jfrom].type = 0;
                board[i][j].color = 0;
                board[i][j].type = 0;
                i = i + 1;
                j = j + 1;
                board[i][j].color = mycolor;
                board[i][j].type = 1;
                return 1;
            } else {
                board[ifrom][jfrom].color = 0;
                board[ifrom][jfrom].type = 0;
                board[i][j].color = mycolor;
                board[i][j].type = 1;
                return 0;
            }
        }
        return -1;
    }
    public int moveNorthEastQueen(int ifrom, int jfrom, int i, int j, int mycolor, int oponentcolor){
        System.out.println("moveNorthEastQueen");
        boolean skok = false;
        if(board[ifrom][jfrom].color==mycolor) {
            if (board[i][j].color == oponentcolor && board[i - 1][j + 1].color == 0) {
                skok = true;
            }
            for (int k = ifrom-1, l = jfrom+1; k > i; k--, l++) {
                if (board[k][l].color != 0) {
                    return -1;
                }
            }
            if (skok) {
                board[ifrom][jfrom].color = 0;
                board[ifrom][jfrom].type = 0;
                board[i][j].color = 0;
                board[i][j].type = 0;
                i = i - 1;
                j = j + 1;
                board[i][j].color = mycolor;
                board[i][j].type = 1;
                return 1;
            } else {
                board[ifrom][jfrom].color = 0;
                board[ifrom][jfrom].type = 0;
                board[i][j].color = mycolor;
                board[i][j].type = 1;
                return 0;
            }
        }
        return -1;
    }
    public int possibleanymoveAFTERJUMP(int i, int j, int mycolor, int oponentcolor){
           if(possibleMoveNorthEast(i, j,  mycolor, oponentcolor)==1){

               return  0;
           }else if(possibleMoveNorthWest(i, j,  mycolor, oponentcolor)==1) {

               return 0;
           }else if(possibleMoveSouthEast(i, j,  mycolor, oponentcolor)==1){

                return  0;
            }else if(possibleMoveSouthWest(i, j,  mycolor, oponentcolor)==1) {

               return 0;
           }else
               return -1;
    }
    public int possibleanymoveBEFOREJUMP(int i, int j, int mycolor, int oponentcolor){
        int result = -1;
        result=possibleanymoveAFTERJUMP(i,j,mycolor,oponentcolor);
        if(result==-1){
            if(board[i+1][j+1].color==0){
                return 0;
            }
            if(board[i+1][j-1].color==0){
                return 0;
            }
            if(board[i-1][j+1].color==0){
                return 0;
            }
            if(board[i-1][j-1].color==0){
                return 0;
            }
        }
        return result;
    }

    public int possibleMoveNorthWest(int i, int j, int mycolor, int oponentcolor){
        if(board[i][j].color==mycolor){
            if(board[i-1][j-1].color==0){
                return 0;
            }else if(board[i-1][j-1].color==oponentcolor){
                if(board[i-2][j-2].color==0){
                    return 1;
                }else{
                    return -2;
                }
            }else{
                return -3;
            }
        }
        return -4;
    }

    public int possibleMoveSouthWest(int i, int j, int mycolor, int oponentcolor){
        if(board[i][j].color==mycolor){
            if(board[i+1][j-1].color==0){
                return 0;
            }else if(board[i+1][j-1].color==oponentcolor){
                if(board[i+2][j-2].color==0){
                    return 1;
                }else{
                    return -2;
                }
            }else{
                return -3;
            }
        }
        return -4;
    }

    public int possibleMoveSouthEast(int i, int j, int mycolor, int oponentcolor){
        if(board[i][j].color==mycolor){
            if(board[i+1][j+1].color==0){
                return 0;
            }else if(board[i+1][j+1].color==oponentcolor){
                if(board[i+2][j+2].color==0){
                    return 1;
                }else{
                    return -2;
                }
            }else{
                return -3;
            }
        }
        return -4;
    }

    public int possibleMoveNorthEast(int i, int j, int mycolor, int oponentcolor){
        if(board[i][j].color==mycolor){
            if(board[i-1][j+1].color==0){
                return 0;
            }else if(board[i-1][j+1].color==oponentcolor){
                if(board[i-2][j+2].color==0){
                    return 1;
                }else{
                    return -2;
                }
            }else{
                return -3;
            }
        }
        return -4;
    }

//    public void printBoard(){
//        System.out.print(" | ");
//        for(int i = 0; i<9;i++){
//            System.out.print(i+"| ");
//        }
//        System.out.println();
//        for(int i = 0; i<10;i++){
//            for(int j = 0; j<10;j++){
//                if(board[i][j].color==8){
//                    if(j==0){System.out.print(i);}
//                    System.out.print("|  ");
//                }else {
//                    System.out.print("|" + board[i][j].color + " ");
//                }
//            }
//            System.out.print("\n");
//        }
//
//    }

//    public static void main(String[] args) {
//        System.out.println("tablica");
//        Board board = new Board();
//        board.initiateBoard();
//        board.printBoard();
//        Scanner scanner = new Scanner(System.in);
//        int i, j, ruch;
//        int czyjRuch=2, oponent = 1;
//        int temp;
//        while(true) {
//            if(czyjRuch==2) {
//                System.out.println("Ruch białych");
//            }else{
//                System.out.println("Ruch czarnych");
//            }
//            System.out.println("Podaj wspolrzednie pionka do ruchu i(liczba), j(od alfabetu ale w liczbie)");
//            i = scanner.nextInt();
//            j = scanner.nextInt();
//            scanner.nextLine();
//            System.out.println("Co zrobic? 1- lewagora, 2-prawagora, 3-lewydol, 4-prawydol)");
//            ruch=scanner.nextInt();
//            switch (ruch){
//                case 1:
//                    board.moveNorthWest(i,j,czyjRuch, oponent);
//                    break;
//                case 2:
//                    board.moveNorthEast(i,j,czyjRuch, oponent);
//                    break;
//
//                case 3:
//                    board.moveSouthWest(i,j,czyjRuch, oponent);
//                    break;
//                case 4:
//                    board.moveSouthEast(i,j,czyjRuch, oponent);
//                    break;
//                default:
//                    System.out.print("Podano zły ruch, strata ruchu!");
//                    break;
//            }
//            temp=czyjRuch;
//            czyjRuch=oponent;
//            oponent=temp;
//            board.printBoard();
//        }
//    }
}
