import java.util.ArrayList;
import java.util.Arrays;

public class TestMain {
    public static void main(String[] args) {
//        int r = 0;
//        int c = 0;
//        String type = "";
//        String toString = "r:5;c:4;type:lClick;".trim();
//        while(toString.contains(":") && toString.contains(";")){
//            String key = toString.substring(0, toString.indexOf(":"));
//            String value = toString.substring(toString.indexOf(":")+1, toString.indexOf(";"));
//            toString = toString.substring(toString.indexOf(";")+1);
////            System.out.println(key);
////            System.out.println(value);
//            switch (key){
//                case "r": r = Integer.parseInt(value);
//                break;
//                case "c": c = Integer.parseInt(value);
//                break;
//                case "type": type = value;
//                break;
//                default: break;
//            }
//        }

//        ArrayList<Integer> arrayList = new ArrayList<>();
//        arrayList.add(1);
//        arrayList.add(2);
//        arrayList.add(3);
//        String toString = arrayList.toString();
//        System.out.println(toString);
//
//        ArrayList<String> parsedList = new ArrayList<>();
//        boolean first = true;
//        while(toString.contains("[") || toString.contains("]")) {
//            if (first){
//                parsedList.add(toString.substring(toString.indexOf("[") + 1, toString.indexOf(",")));
//                first = false;
//                toString = toString.substring(toString.indexOf(",") + 1);
//            } else {
//                if (toString.contains(",")){
//                    parsedList.add(toString.substring(toString.indexOf(" ") + 1, toString.indexOf(",")));
//                    toString = toString.substring(toString.indexOf(",") + 1);
//                } else {
//                    parsedList.add(toString.substring(toString.indexOf(" ") + 1, toString.indexOf("]")));
//                    break;
//                }
//            }
//        }
//        System.out.println(parsedList);

//        System.out.println(EncodedObject.getHeaderString("Foxtrot", new Mine(5, 3)));
//        System.out.println(EncodedObject.getHeaderString("Foxtrot", ""));
//        String data = EncodedObject.getPacket("Foxtrot", new Mine(5, 3));
        String string = "width:500;height:400;mines:[r$7#c$6#, r$1#c$5#, r$1#c$3#, r$3#c$0#, r$6#c$6#, r$7#c$5#, r$4#c$2#, r$4#c$2#, r$2#c$1#, r$5#c$0#];";
        Board b = EncodedObject.constructBoard(string);
        System.out.println(b.getMines());
    }
}
