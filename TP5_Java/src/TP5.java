public class TP5 {
    public static void main(String[] args) {
        int parametersNb = args.length;

        if (parametersNb <= 0 || parametersNb >= 3) {
            System.err.println("Error : bad parameters number, the program should be called with 1 or 2 parameters");
        } else {
            try {
                if(parametersNb == 1) {
                    JavaToCppConverter.convertJavaToCppClass(args[0], args[0]);
                } else {
                    JavaToCppConverter.convertJavaToCppClass(args[0], args[1]);
                }
            } catch (ClassNotFoundException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
