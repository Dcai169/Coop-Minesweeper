import java.util.ArrayList;

public class EncodedObject {

    private String origin, className, header, body;
    private Object object;

    public EncodedObject(String originName, Object object) {
        this.origin = originName;
        this.className = object.getClass().getSimpleName();
        this.object = object;
        this.body = null;
        this.header = null;
    }

    @Override
    public String toString() {
        return getPacket(origin, object);
    }

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
            } else if ("c".equals(key)) {
                c = Integer.parseInt(value);
            } else if ("type".equals(key)) {
                type = value;
            } else {
                System.out.println("Unrecognized key: "+key);
            }
        }
        //TODO: Add check if r, c is -1
        return new Action(r, c, type);
    }

    public static Mine constructMine(String toString){
        int r = -1;
        int c = -1;
        while (toString.contains("{") && toString.contains("}")){
            String key = toString.substring(0, toString.indexOf("{"));
            String value = toString.substring(toString.indexOf("{")+1, toString.indexOf("}"));
            toString = toString.substring(toString.indexOf("}")+1);
            if ("r".equals(key)) {
                r = Integer.parseInt(value);
            } else if ("c".equals(key)) {
                c = Integer.parseInt(value);
            } else {
                System.out.println("Unrecognized key: "+key);
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
            mines.localAdd(constructMine(encodedMine));
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
            } else if ("height".equals(key)) {
                height = Integer.parseInt(value);
            } else if ("mines".equals(key)){
                mines = constructMineList(value);
            } else {
                System.out.println("====[EncodedObject.constructBoard]====");
                System.out.println("Unrecognized Key: "+key);
                System.out.println("Unrecognized Value: "+value);
                System.out.println();
            }
        }
        return new Board(width, height, mines);
    }

    public static String getHeaderString(String origin, Object obj){
        return "<h(origin:"+origin+";class:"+obj.getClass().getSimpleName()+";)/h>";
    }

    public static String getBodyString(Object obj){
        return "<b("+obj.toString()+")/b>";
    }

    public static String getPacket(String origin, Object object){
        return getHeaderString(origin, object)+getBodyString(object);
    }




    public static String getHeader(String packet){
//        header = packet.substring(packet.indexOf("<h(")+"<h(".length(), packet.indexOf(")/h>"));
        return packet.substring(packet.indexOf("<h(")+"<h(".length(), packet.indexOf(")/h>"));
    }

    public static String getBody(String packet){
//        body = packet.substring(packet.indexOf("<b(")+"<b(".length(), packet.indexOf(")/b>"));
        System.out.println(packet.indexOf("<b(")+"<b(".length());
        return packet.substring(packet.indexOf("<b(")+"<b(".length(), packet.indexOf(")/b>"));
    }
}
