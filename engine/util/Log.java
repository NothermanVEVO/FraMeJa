package engine.util;

public abstract class Log {

    /*
     * Flags for type of prints.
     */
    public static final int INFO = 1;
    public static final int WARNING = 2;
    public static final int ERROR = 3;
    public static final int DEBUG = 4;
    public static final int SUCCESS = 5;

    /*
     * Flags for color of String.
     */
    private static final String RED_TEXT = "\u001B[31m";
    private static final String GREEN_TEXT = "\u001B[32m";
    private static final String YELLOW_TEXT = "\u001B[33m";
    private static final String BLUE_TEXT = "\u001B[34m";
    private static final String PURPLE_TEXT = "\u001B[35m";

    /*
     * Reset the color of the text.
     */
    private static final String RESET_TEXT = "\u001B[0m";

    /**
     * Print a message based in the given {@code FLAG}.
     * @param FLAG Type of message.
     * @param format A format string as described in Format string syntax.
     * @param objects args Arguments referenced by the format specifiers in the format string. 
     * If there are more arguments than format specifiers, the extra arguments are ignored. 
     * The number of arguments is variable and may be zero. The maximum number of arguments is 
     * limited by the maximum dimension of a Java array as defined by The Java Virtual Machine Specification. 
     * The behaviour on a null argument depends on the conversion.
     * @throws Exception Invalid {@code FLAG}.
     * @throws java.util.IllegalFormatException If a format string contains an illegal syntax, a format specifier 
     * that is incompatible with the given arguments, insufficient arguments given the format string, or other 
     * illegal conditions. For specification of all possible formatting errors, see the Details section of the 
     * formatter class specification.
     * @throws NullPointerException If the {@code format} is {@code null}
     */
    public static void print(final int FLAG, String format, Object... args){
        switch (FLAG) {
            case INFO:
                System.out.printf(YELLOW_TEXT + "[INFO]: " + RESET_TEXT);
                break;
            case WARNING:
                System.out.printf(PURPLE_TEXT + "[WARNING]: " + RESET_TEXT);
                break;
            case ERROR:
                System.out.printf(RED_TEXT + "[ERROR]: " + RESET_TEXT);
                break;
            case DEBUG:
                System.out.printf(BLUE_TEXT + "[DEBUG]: " + RESET_TEXT);
                break;
            case SUCCESS:
                System.out.printf(GREEN_TEXT + "[SUCCESS]: " + RESET_TEXT);
                break;
            default:
                try {
                    throw new Exception("Flag -> \"" + FLAG + "\" isn't a valid flag!");
                } catch (Exception e) {
                    printExceptionError(e);
                }
                return;
        }
        try {
            System.out.printf(format, args);
        } catch (java.util.IllegalFormatException | NullPointerException e) {
            printExceptionError(e);
        }
    }

    /**
     * Print a message based in the given {@code FLAG} with a {@code new line}.
     * @param FLAG Type of message.
     * @param format A format string as described in Format string syntax.
     * @param objects args Arguments referenced by the format specifiers in the format string. 
     * If there are more arguments than format specifiers, the extra arguments are ignored. 
     * The number of arguments is variable and may be zero. The maximum number of arguments is 
     * limited by the maximum dimension of a Java array as defined by The Java Virtual Machine Specification. 
     * The behaviour on a null argument depends on the conversion.
     * @throws Exception Invalid {@code FLAG}.
     * @throws java.util.IllegalFormatException If a format string contains an illegal syntax, a format specifier 
     * that is incompatible with the given arguments, insufficient arguments given the format string, or other 
     * illegal conditions. For specification of all possible formatting errors, see the Details section of the 
     * formatter class specification.
     * @throws NullPointerException If the {@code format} is {@code null}
     */
    public static void println(final int FLAG, String format, Object... args){
        print(FLAG, format, args);
        System.out.printf("\n");
    }

    /**
     * Print an {@code Exception} with a {@code Log.ERROR} flag.
     * @param e The {@link Exception}
     */
    public static void printExceptionError(Exception e){
        Log.println(ERROR, e.getMessage());
        for (StackTraceElement element : e.getStackTrace()) {
            System.out.println("\tat " + element.toString());
        }
        e.getStackTrace();
    }

    /**
     * Print an {@code Exception} with a {@code Log.WARNING} flag.
     * @param e The {@link Exception}
     */
    public static void printExceptionWarning(Exception e){
        Log.println(WARNING, e.getMessage());
        for (StackTraceElement element : e.getStackTrace()) {
            System.out.println("\tat " + element.toString());
        }
        e.getStackTrace();
    }

}
