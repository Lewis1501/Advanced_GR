/**
 * Created by Sony VAIO on 26/01/2016.
 */

public class GR_Algorithm {
    private static String[] users;
    private static double[] items;
    private static double[][] ratings;
    private static double[][]Sim = new double[10][10];//Takes two users;
    private static double[][] Groups;
    private static double[][]Relevance = new double[10][20];//Takes two users;
    private static double[][]rel = new double[3][20];
    private static double[][] groupDisagreement = new double[10][20];
    private static double[][] groupRelevance = new double[3][20];
    private static double[][] avgPair = new double[3][20];

    private static double[][] CFunction = new double[3][20];

    static int[] groupSize = new int[3];

    private static final int itemsLength = 20;
    private static final int usersLength = 10;
    private static final int groupLength = 1;



    public static void main(String[] args) {
        setupArrays();
        //getRatings(ratings);
       //emptyRatings(ratings);
       //isElementEmpty(ratings,2,5);
       //getSim(ratings,2);
       //test();
        //getItems(items);
       //testTwo();
        UserSim();
        Relevance();
        LeastMisery();
        //DisagreementVariance();
        //AverageRelevance();
        //AveragePairWise();
        //Consensus(0.5, 0.5);
        }


    public static void setupArrays() {

        /** Array for [users]**/

        users = new String[10];
        /** u.user Data file **/
        users[0] = "196";
        users[1] = "186";
        users[2] = "22";
        users[3] = "244";
        users[4] = "166";
        users[5] = "298";
        users[6] = "115";
        users[7] = "253";
        users[8] = "305";
        users[9] = "6";
        /** End **/


        /** Array of groups [Group][item] **/
        Groups = new double[3][4];
        /** Group 1 **/
        Groups[0][0] = 1;
        Groups[0][1] = 1;
        Groups[0][2] = 1;

        /** Group 2 **/
        Groups[1][0] = 2;
        Groups[1][1] = 2;
        Groups[1][2] = 2;

        /** Group 3 **/
        Groups[2][0] = 3;
        Groups[2][1] = 3;
        Groups[2][2] = 3;
        Groups[2][3] = 3;
        /**End**/





        /** Array for [items]**/
        items = new double[20];

        /** u.item Data folder **/
        items[0] = 242;
        items[10] = 302;
        items[1] = 337;
        items[11] = 51;
        items[2] = 346;
        items[12] = 474;
        items[3] = 265;
        items[13] = 465;
        items[4] = 451;
        items[14] = 86;
        items[5] = 257;
        items[15] = 1014;
        items[6] = 222;
        items[16] = 40;
        items[7] = 29;
        items[17] = 785;
        items[8] = 387;
        items[18] = 274;
        items[9] = 104;
        items[19] = 1184;

        /**End**/

        /** Array for [user][items] Ratings**/
        //User (U) '0' (from array 1) rated item (I) '17' (from array 2) 3, ratings are from 1 - 5..
        ratings = new double[10][20];
      /*  for(int i = 0; i < 10;i++){
            for(int j = 0 ; j < 20;j++){
                ratings[i][j] = 0;
            }
        }*/

        /** get from data for combinations of files **/
        ratings[0][17] = 1;
        ratings[1][5] = 4;
        ratings[2][13] = 4;
        ratings[3][8] = 2;
        ratings[4][11] = 3;
        ratings[5][4] = 3;
        ratings[6][7] = 4;
        ratings[7][10] = 1;
        ratings[8][19] = 2;
        ratings[9][12] = 3;

        /**End**/


    }

/*    public static double ItemSim(double[] itemArray,double itemOne, double itemTwo){
        //   double time = System.nanoTime(); check the speed of the method.
        double i1 = 0,i2 = 0;
        for(int i = 0; i < itemArray.length; i++){
            if(i == itemOne){
                    i1 = itemArray[i];
            }
            if(i == itemTwo){
                   i2 = itemArray[i];
                break;
            }
        }

        *//**/

    /**
         double time1 = System.nanoTime();      Used to check speed.
         System.out.println(time1 - time1);
     **//**//*

        return i1 - i2;
    }*/
    public static void UserSim() {
        for (int ux = 0; ux < usersLength; ux++) {
            double nominator = 0, denominator = 0;
            for (int uy = 0; uy < usersLength; uy++) {
                for (int item_idx = 0; item_idx < itemsLength; item_idx++) {
                    if (ratings[ux][item_idx] != 0 || ratings[uy][item_idx] != 0) {
                        denominator++;
                    }
                    for (int item_idy = 0; item_idy < itemsLength; item_idy++) {
                        if (ratings[ux][item_idx] != 0 && ratings[uy][item_idy] != 0 && (Math.abs(ratings[ux][item_idx] - ratings[uy][item_idy]) <= 2)) {
                            nominator++;
                        }
                    }
                    //System.out.println(nominator + " " + denominator);

                    if (denominator != 0) {
                        Sim[ux][uy] = nominator / denominator;
                        Sim[uy][ux] = Sim[ux][uy];
                    } else {
                        Sim[uy][ux] = 0;
                        Sim[ux][uy] = 0;
                    }
                }
                // System.out.printf("userx: [%s] has a similarity to usery: [%s] of %.2f.\n",Sim[ux], Sim[uy], Sim[ux][uy]);
            }
        }
    }


    public static void Relevance() {

        for (int ux = 0; ux < usersLength; ux++) {
            for (int item_idx = 0; item_idx < itemsLength; item_idx++) {
                double Rel = 0;
                for (int uy = 0; uy < usersLength; uy++) {
                    Rel = Rel + (Sim[ux][uy] * ratings[uy][item_idx]);
                }
                Relevance[ux][item_idx] = Rel;
                //if (Relevance[ux][item_idx] != Relevance[0][0])
                //System.out.printf("user %s for item %s has a relevance of %.2f.\n", users[ux], items[item_idx], Relevance[ux][item_idx]);
                }
            }
        }

    protected static void LeastMisery() {
        double min = 50;
        for (int g = 0; g < groupLength; g++) {
            for (int itemindx = 0; itemindx < itemsLength; itemindx++) {
                for (int u = 0; u < usersLength; u++) {
                    if (Relevance[u][itemindx] != Relevance[0][0] && Relevance[u][itemindx] < min) {
                        min = Relevance[u][itemindx];
                    }
                    // System.out.println(min);
                    rel[g][itemindx] = min;
                }
                //if (rel[g][itemindx] != rel[0][0])
                //System.out.printf("The least rated item for group %s was item %s with a rating of: %.2f\n",groupSize[g],items[itemindx],rel[g][itemindx]);

            }
        }
    }

    protected static void AverageRelevance() {
        for (int g = 0; g < groupLength; g++) {
            for (int i = 0; i < itemsLength; i++) {
                double avg = 0;
                for (int u = 0; u < usersLength; u++) {
                    //loop and adding all rel[u][i] together
                    avg = avg + (Relevance[u][i]);

                }

                //dividing by the size of the group.
                avg = avg / groupSize[g];

                groupRelevance[g][i] = avg;
                /** Not working **/
                //  System.out.println(groupRelevance[g][i]);
            }
            }
    }

    public static void DisagreementVariance(){
        for (int g = 0; g < groupLength; g++) {
            double dis = 0;
            for (int itemindx = 0; itemindx < itemsLength; itemindx++) {
                for (int u = 0; u < usersLength; u++) {
                    dis = dis + Math.pow(groupDisagreement[u][itemindx] - groupDisagreement[0][itemindx], 2);


                    dis = dis * 1 / groupSize[g];
                }

                System.out.printf("The disargeement in group %s with item %s is: ", groupSize[g], items[itemindx], dis);
            }
        }

    }

    public static void AveragePairWise() {
        System.out.printf("%s", "Average Pair-Wise");
        int v = 0;
        int u;
        for (int g = 0; g < groupLength; g++) {
            for (int i = 0; i < itemsLength; i++) {
                double dis = 0;
                for (u = 0; u < usersLength; u++) {
                    for (v = 0; v < 5; v++) {
                        if (u != v)
                            dis = dis + Math.abs(Relevance[u][i] - Relevance[v][i]);
                    }

                    dis = dis * 2 / (groupSize[g] * (groupSize[g] - 1));

                }
                avgPair[g][i] = dis;

                System.out.printf("The average disagreement between user %s and user %s is %.2f.\n", users[u], users[v], avgPair[g][i]);

            }


        }

    }


    public static void Consensus(double w1, double w2) {
        for (int g = 0; g < groupLength; g++) {
            double con = 0;
            for (int items = 0; items < itemsLength; items++) {
                con = con + w1 * Relevance[g][items] + w2 * (1 - groupDisagreement[g][items]);

                CFunction[g][items] = con;


                System.out.println(CFunction[g][items]);

            }


        }
    }

/*
    public static void testTwo(){
        System.out.println(ItemSim(items,10,0));
    }
*/

    public static void getItems(double[] items) {
        System.out.println("Items:");
        for (int i = 0; i < items.length; i++) {
            System.out.println("item " +i + ": " +items[i]);

        }

    }

    public static void getItem(double[] items, double item) {
        System.out.println("Items:");
        int i;
        for ( i = 0; i < items.length; i++) {
            if (i == item) {
                break;
            }
        }
        System.out.println(item + " at index: " + i);
    }

    public static void getUsers(double[] users){
        System.out.println("Users:");
        for (double user : users) {
            System.out.println(user);

        }
    }

    public static void getRatings(double[][] ratings) {
        /**Possibly add a user Preference into the array. **/
        System.out.println("Known Ratings:");
        for (int i = 0; i < ratings.length; i++) {
            for (int j = 0; j < ratings[i].length; j++) {
                if(ratings[i][j] != ratings[0][0])
                {
                    System.out.println("User '" + i +  "' "  + "gave item '" + j  + "'" + " a rating of " +  ratings[i][j]);
                }
            }
        }
    }

    public static void emptyRatings(double[][] ratings) {
        int counter = 0;
        System.out.println("Unknown Ratings:");
        for (double[] rating : ratings) {
            for (int j = 0; j < rating.length; j++) {
                if (rating[j] == ratings[0][0]) {
                    counter++;
                }
            }
        }
        System.out.println("There are " + counter  + " empty values.");
    }

    public static int isElementEmpty(int a, int b) {
    double[][] arr = new double[a][b];
        for (double[] anArr : arr) {
            for (int j = 0; j < anArr.length; j++) {
                if (anArr[j] == arr[a][b] && anArr[j] != arr[0][0]) {
                    System.out.println("Returned 0");
                    return 0;
                } else {

                }
            }
        }
        System.out.println("Returned 1");
        return 1;
        //need fixing


    }

    /**
     * @param ratings
     * @param rate
     */
    public static void getSim(double[][] ratings, int rate) {
        /**Possibly add a user Preference into the array. **/
        System.out.println("Known Ratings:");
        for (int i = 0; i < ratings.length; i++) {
            for (int j = 0; j < ratings[i].length; j++) {
                if(ratings[i][j] != ratings[0][0]  && ratings[i][j] + rate < 6)
                {
                    System.out.println("User '" + i +  "' "  + "gave a similar rating to item '" + j  + "'" + " with a rating of " +  ratings[i][j]);
                }
            }
        }
    }

}





