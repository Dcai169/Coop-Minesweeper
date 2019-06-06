public class Settings {
    public static final int TOTAL_MINES = 10;
    public static final int WIDTH = 10;
    public static final int HEIGHT = 8;
    public static final int SIZE = 50;
    public static final String ADDRESS = "127.0.0.1";
    public static final int PORT = 5005;
    public static UDPListener LISTENER = new UDPListener(ADDRESS, PORT);
    public static UDPListener VOID_LISTENER = new UDPListener("0.0.0.0", 0);
    public static String NAME = "Foxtrot";
}
