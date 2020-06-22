package application;

/**
 * This class represents the Launcher of the system, to bypass JAVA 11 modules constraints.
 */
public final class Launcher {

    private Launcher() { }

    /**
     * 
     * @param args unused
     */
    public static void main(final String[] args) {
    	System.out.println(Integer.toString(11/2));
        Main.main(args);
    }
}
