
import java.io.*;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        String operation = null;
        String messageRead = null;
        String fileNameRead = null;
        String fileNameWrite = null;
        boolean dataPresent = false;
        boolean outPresent = false;
        String algorithm = "shift";
        int key = 0;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-mode")) {
                operation = args[i + 1];
            }
            if (args[i].equals("-key")) {
                key = Integer.parseInt(args[i + 1]);
            }
            if (args[i].equals("-data")) {
                messageRead = args[i + 1];
                dataPresent = true;
            }
            if (args[i].equals("-alg")){
                algorithm = args [i + 1];
            }
            if (args[i].equals("-in")) {
                fileNameRead = args[i + 1];
            }
            if (args[i].equals("-out")) {
                fileNameWrite = args[i + 1];
                outPresent = true;
            }
        }
        if (operation == null) {
            operation = "enc";
        }
        // reading message from a file input stream. ("-in") mode;
        FileReader file = null;
        try {
            file = new FileReader(fileNameRead);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (!dataPresent) {

            try (Scanner input = new Scanner(file)) {

                while (input.hasNext()) {
                    messageRead = input.nextLine();
                }

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        if (messageRead == null) {
            messageRead = "";
        }
        String codedMessage = "";

        if(algorithm.equals("shift")){

            if (operation.equals("enc")) {
                for (int i = 0; i < messageRead.length(); i++) {
                    int localKey = key;
                    int unicodeCharacters = (char) messageRead.charAt(i);
                    if((unicodeCharacters >= 65 && unicodeCharacters  <= 90)||
                            (unicodeCharacters >= 97 && unicodeCharacters <= 122)){
                        while (localKey != 0){
                            if(unicodeCharacters == 90){
                                unicodeCharacters = 65;
                                localKey--;
                            }
                            else if(unicodeCharacters == 122){
                                unicodeCharacters = 97;
                                localKey--;
                            }
                            else {
                                unicodeCharacters++;
                                localKey--;
                            }
                        }
                    }
                    codedMessage += (char) unicodeCharacters;
                }
            }
            if (operation.equals("dec")) {
                for (int i = 0; i < messageRead.length(); i++) {
                    int localKey = key;
                    int unicodeCharacters = messageRead.charAt(i);

                    if((unicodeCharacters >= 65 && unicodeCharacters  <= 90)||
                            (unicodeCharacters >= 97 && unicodeCharacters <= 122)){
                        while (localKey != 0){
                            if(unicodeCharacters == 65){
                                unicodeCharacters = 90;
                                localKey--;
                            }
                            else if(unicodeCharacters == 97){
                                unicodeCharacters = 122;
                                localKey--;
                            }
                            else {
                                unicodeCharacters--;
                                localKey--;
                            }
                        }
                    }
                    codedMessage += (char) unicodeCharacters;
                }
            }

        }else{
            if (operation.equals("enc")) {
                for (int i = 0; i < messageRead.length(); i++) {
                    char x = messageRead.charAt(i);
                    int unicodeCharacters = (int) x;
                    unicodeCharacters += key;
                    x = (char) unicodeCharacters;
                    codedMessage += x;
                }
            }
            if (operation.equals("dec")) {
                for (int i = 0; i < messageRead.length(); i++) {
                    char x = messageRead.charAt(i);
                    int unicodeCharacters = (int) x;
                    unicodeCharacters -= key;
                    x = (char) unicodeCharacters;
                    codedMessage += x;
                }
            }

        }
        // write the message to the file or output to console
        try {
            FileWriter output = new FileWriter(fileNameWrite);
            output.write(codedMessage);
            output.close();
        } catch (IOException ex) {
            ex.getStackTrace();
        }

        if (!outPresent) {
            System.out.println(codedMessage);
        }
    }
}
