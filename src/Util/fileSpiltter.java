package Util;

import java.io.*;

/**
 * Created by Sony VAIO on 16/02/2016.
 */
public class fileSpiltter {

    fileSpiltter() {

    }

    public static void Spilt(String fileFrom, String fileTo) throws IOException {
        File files = new File(fileFrom);
        BufferedReader br = new BufferedReader(new FileReader("Data/" + files));
        BufferedWriter bw = new BufferedWriter(new FileWriter("Data/" + fileTo + ".csv"));

        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split("\\t", -1);
            bw.write(values[0] + ", \n" + values[1] + "," + values[2] + "\n");
        }


        br.close();
        bw.close();

    }

    public static void main(String[] args) throws IOException {
        fileSpiltter.Spilt("u.data", "newnew");
    }


}
