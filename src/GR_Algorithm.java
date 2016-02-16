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
    private static double[][] CFunction = new double[3][20];

    static int[] groupSize = new int[3];


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
        LeastMis();
        DisagreementVariance();
        AverageRelevance();
        AveragePairWise();
        Consensus(0.5, 0.5);
        }


    public static void setupArrays() {

        groupSize[0] = 3;
        groupSize[1] = 4;
        groupSize[2] = 4;

        /** Array for [users]**/

        users = new String[10];
        /** u.user Data file **/
        users[0] = "Lewis";
        users[1] = "John";
        users[2] = "Luke";
        users[3] = "Al";
        users[4] = "Mike";
        users[5] = "Robert";
        users[6] = "Harry";
        users[7] = "Ghost";
        users[8] = "Reus";
        users[9] = "Marco";
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
        items[0] = 101;
        items[10] = 56;
        items[1] = 56;
        items[11] = 22;
        items[2] = 5;
        items[12] = 18;
        items[3] = 23;
        items[13] = 14;
        items[4] = 11;
        items[14] = 64;
        items[5] = 58;
        items[15] = 34;
        items[6] = 12;
        items[16] = 24;
        items[7] = 31;
        items[17] = 81;
        items[8] = 26;
        items[18] = 95;
        items[9] = 20;
        items[19] = 56;

        /**End**/

        /** Array for [user][items] Ratings**/
        //User (U) '0' (from array 1) rated item (I) '17' (from array 2) 3, ratings are from 1 - 5..
        ratings = new double[10][20];
        for(int i = 0; i < 10;i++){
            for(int j = 0 ; j < 20;j++){
                ratings[i][j] = 0;
            }
        }

        /** get from data for combinations of files **/
        ratings[0][17] = 3;
        ratings[1][5] = 2;
        ratings[2][13] = 5;
        ratings[3][8] = 4;
        ratings[4][11] = 5;
        ratings[5][4] = 3;
        ratings[6][7] = 1;
        ratings[7][10] = 3;
        ratings[8][19] = 4;
        ratings[9][12] = 3;

        /**End**/


    }

    public static double ItemSim(double[] itemArray,double itemOne, double itemTwo){
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

        /**
         double time1 = System.nanoTime();      Used to check speed.
         System.out.println(time1 - time1);
         **/

        return i1 - i2;
    }

    public static void UserSim(){

        for(int ux = 0; ux < users.length; ux++){
            double nominator = 0, denominator = 0;
            for(int uy = 0; uy < users.length; uy++) {
                for (int item_idx = 0; item_idx < items.length; item_idx++) {
                    if (ratings[ux][item_idx] > 0 || ratings[uy][item_idx] > 0) {
                        denominator++;
                    }
                    for (int item_idy = 0; item_idy < items.length; item_idy++) {
                        if (ratings[ux][item_idx] > 0 && ratings[uy][item_idy] < 0 && (Math.abs(ratings[ux][item_idx] - ratings[uy][item_idy]) <= 2)) {
                            nominator++;
                        }
                    }

               // System.out.println(nominator + " " + denominator);
                if (denominator != 0) {
                    Sim[ux][uy] = nominator / denominator;
                      Sim[uy][ux] = Sim[ux][uy];
                } else {
                    Sim[uy][ux] = 0;
                    Sim[ux][uy] = 0;
                }
            }
                Sim[ux][ux] = 0;

            }
        }

        }


    public static void Relevance() {
        int ux, uy, item_idx;
        for (ux = 0; ux < users.length; ux++) {
            for (item_idx = 0; item_idx < items.length; item_idx++) {
                double Rel = 0;
                for (uy = 0; uy < users.length; uy++) {
                    Rel = Rel + (Sim[ux][uy] * ratings[uy][item_idx]);
                }
                Relevance[ux][item_idx] = Rel;
            }
        }
    }

    public static double LeastMis() {
        double min = 1000;
        for (int g = 0; g < 1; g++) {
            for (int itemindx = 0; itemindx < 20; itemindx++) {
                for (int u = 0; u < 10; u++) {
                    if (Relevance[u][itemindx] < min) {
                        min = Relevance[u][itemindx];
                    }
                    rel[g][itemindx] = min;
                }

            }

        }
        return min;

    }

    public static void AverageRelevance() {
        double avg = 0;
        for (int g = 0; g < 3; g++) {
            for (int i = 0; i < 20; i++) {
                for (int u = 0; u < 10; u++) {
                    avg = avg + (Relevance[u][i]);

                }

                avg = avg / groupSize[g];

                groupRelevance[g][i] = avg;
            }
            }

        }

    public static void DisagreementVariance(){
        for(int g = 0; g < 3; g++){
            double dis = 0;
            for (int itemindx = 0; itemindx < 20; itemindx++) {
                for (int u = 0; u < 10; u++) {

                    dis = dis + Math.pow(groupDisagreement[u][itemindx] - groupDisagreement[0][itemindx], 2);
                }

                dis = dis * 1 / groupSize[g];

            }
        }

    }

    public static void AveragePairWise() {
        for (int g = 0; g < 3; g++) {
            for (int i = 0; i < 20; i++) {
                double dis = 0;
                for (int u = 0; u < 10; u++) {
                    for (int v = 0; v < 5; v++) {

                        dis = dis + Math.abs(Relevance[u][i] - Relevance[v][i]);
                    }
                    dis = dis * 2 / (groupSize[g] * (groupSize[g] - 1));

                }

            }
        }
    }


    public static void Consensus(double w1, double w2) {
        double con = 0;
        for (int g = 0; g < 3; g++) {
            for (int itemsI = 0; itemsI < 20; itemsI++) {
                con = con + w1 * Relevance[g][itemsI] + w2 * (1 - groupDisagreement[g][itemsI]);


                CFunction[g][itemsI] = con;
            }
        }
    }

    public static void testTwo(){
        System.out.println(ItemSim(items,10,0));
    }

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





