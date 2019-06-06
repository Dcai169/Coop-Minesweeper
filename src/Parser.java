import java.util.ArrayList;

public class Parser {
    public static Action constructAction(String toString){
        int r = -1;
        int c = -1;
        String type = "";
        while (toString.contains(":") && toString.contains(";")){
            String key = toString.substring(0, toString.indexOf(":"));
            String value = toString.substring(toString.indexOf(":")+1, toString.indexOf(";"));
            toString = toString.substring(toString.indexOf(";")+1);
            if ("r".equals(key)) {
                r = Integer.parseInt(value);
                break;
            } else if ("c".equals(key)) {
                c = Integer.parseInt(value);
                break;
            } else if ("type".equals(key)) {
                type = value;
                break;
            } else {
                break;
            }
        }
        //TODO: Add check if r, c is -1
        return new Action(r, c, type);
    }

    public static Mine constructMine(String toString){
        int r = -1;
        int c = -1;
        while (toString.contains(":") && toString.contains(";")){
            String key = toString.substring(0, toString.indexOf(":"));
            String value = toString.substring(toString.indexOf(":")+1, toString.indexOf(";"));
            toString = toString.substring(toString.indexOf(";")+1);
            if ("r".equals(key)) {
                r = Integer.parseInt(value);
                break;
            } else if ("c".equals(key)) {
                c = Integer.parseInt(value);
                break;
            } else {
                break;
            }
        }
        //TODO: Add check if r, c is -1
        return new Mine(r, c);
    }

    // This is just a JSON parser :\
    public static ArrayList<String> contructList(String toString){
        ArrayList<String> parsedList = new ArrayList<String>();
        boolean first = true;
        while(toString.contains("[") || toString.contains("]")) {
            if (first){
                parsedList.add(toString.substring(toString.indexOf("[") + 1, toString.indexOf(",")));
                first = false;
                toString = toString.substring(toString.indexOf(",") + 1);
            } else {
                if (toString.contains(",")){
                    parsedList.add(toString.substring(toString.indexOf(" ") + 1, toString.indexOf(",")));
                    toString = toString.substring(toString.indexOf(",") + 1);
                } else {
                    parsedList.add(toString.substring(toString.indexOf(" ") + 1, toString.indexOf("]")));
                    break;
                }
            }
        }
        return parsedList;
    }

    public static UDPArrayList<Mine> constructMineList(String toString){
        ArrayList<String> encodedMines = contructList(toString);
        UDPArrayList<Mine> mines = new UDPArrayList<Mine>(Settings.VOID_LISTENER);
        for (String encodedMine : encodedMines) {
            mines.add(constructMine(encodedMine));
        }
        return mines;
    }

    public static Board constructBoard(String toString){
        int width = -1;
        int height = -1;
        UDPArrayList<Mine> mines = null;
        while (toString.contains(":") && toString.contains(";")){
            String key = toString.substring(0, toString.indexOf(":"));
            String value = toString.substring(toString.indexOf(":")+1, toString.indexOf(";"));
            toString = toString.substring(toString.indexOf(";")+1);
            if ("width".equals(key)) {
                width = Integer.parseInt(value);
                break;
            } else if ("height".equals(key)) {
                height = Integer.parseInt(value);
                break;
            } else if ("mine".equals(key)){
                mines = constructMineList(value);
                break;
            } else {
                break;
            }
        }
        return new Board(width, height, mines);
    }
}
