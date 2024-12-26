import java.util.ArrayList;
import java.util.Scanner;

public class ChuckNorrisCipherEncoder {
    public static void main(String[] args) {
        EncoderDecoder encoderDecoder = new EncoderDecoder();
        encoderDecoder.run();
    }
}

class EncoderDecoder {

    public void run() {
        Scanner scanner = new Scanner(System.in);
        String action = "";
        String input;
        String output;
        while(!action.equals("exit")) {
            System.out.println("Please input operation (encode/decode/exit):");
            action = scanner.nextLine();
            switch(action) {
                case "encode" :
                    System.out.println("Input string:");
                    input = scanner.nextLine();
                    output = encode(input);
                    System.out.println("\nEncoded string:");
                    System.out.println(output+"\n");
                    break;
                case "decode" :
                    System.out.println("Input encoded string:");
                    input = scanner.nextLine();
                    if(!isValidEncodedMessage(input)) {
                        System.out.println("Encoded string is not valid.");
                    }
                    else {
                        output = decode(input);
                        System.out.println("\nDecoded string:");
                        System.out.println(output+"\n");
                    }
                    break;
                case "exit" :
                    System.out.println("Bye!");
                    break;
                default :
                    System.out.println("There is no '" + action + "' operation\n");
            }
        }
        scanner.close();
    }

    public boolean isValidEncodedMessage(String s) {
        boolean valid  = true;
        char[] charArray = s.toCharArray();
        for(int i = 0; i < s.length(); i++) {
            if (charArray[i] != '0' && charArray[i] != ' ') {
                valid = false;
                break;
            }
        }
        String[] stringArray = s.split(" ");
        for(int i = 0; i < stringArray.length; i++) {
            if (i % 2 == 0) {
                if (!stringArray[i].equals("0") && !stringArray[i].equals("00")) {
                    valid = false;
                }
            }
        }
        if(stringArray.length % 2 != 0 ) {
            valid = false;
        }
        String binary = getCodeToBinary(stringArray);
        if(binary.length() % 7 != 0) {
            valid = false;
        }
        return valid;
    }

    public String decode(String s) {
        String[] stringArray = s.split(" ");
        String binary = getCodeToBinary(stringArray);
        return getBinaryToCharacters(binary);
    }

    private String getBinaryToCharacters(String s) {
        ArrayList<Character> cList = new ArrayList<>();
        ArrayList<String> sList = new ArrayList<>();
        for(int i = 0; i < s.length(); i+=7) {
            sList.add(s.substring(i, i+7));
        }
        for(String e : sList) {
            cList.add((char)Integer.parseInt(e, 2));
        }
        return getCharArrayListToString(cList);
    }

    private String getCharArrayListToString(ArrayList<Character> cList) {
        StringBuilder builder = new StringBuilder();
        for(char c : cList) {
            builder.append(c);
        }
        return builder.toString();
    }

    private String getCodeToBinary(String[] array) {
        char bit = ' ';
        StringBuilder binaryBlock = new StringBuilder();
        for(int i = 0; i < array.length; i++) {
            if( i % 2 == 0){
                if(array[i].equals("0")) {
                    bit = '1';
                }
                else {
                    bit = '0';
                }
            }
            else {
                for(int j = 0; j < array[i].length(); j++) {
                    binaryBlock.append(bit);
                }
            }
        }
        return binaryBlock.toString();
    }

    public String encode(String s) {
        String binaryString = getStringToBinary(s);
        char[] charArray = binaryString.toCharArray();
        StringBuilder code = new StringBuilder();
        for(int i = 0; i < charArray.length; i++) {
            if(charArray[i] == '1') {
                code.append("0 ");
            }
            else if(charArray[i] == '0') {
                code.append("00 ");
            }

            for(int j = i; j < charArray.length-1; j++) {
                if(charArray[j] != charArray[j+1]) {
                    code.append("0 ");
                    break;
                }
                else if(charArray[j] == charArray[j+1]) {
                    code.append("0");
                    i++;
                }
            }
        }
        code.append("0");
        return code.toString();
    }

    private String getStringToBinary(String s) {
        StringBuilder binary = new StringBuilder();
        char[] charArray = s.toCharArray();
        for(char c : charArray) {
            binary.append(getCharToBinary(c));
        }
        return binary.toString();
    }

    private String getCharToBinary(char c) {
        return String.format("%7s", Integer.toBinaryString(c)).replace(" ", "0");
    }
}
