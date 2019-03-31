package ro.pub.cs.systems.eim.practicaltest01;

public class Constants {

    final public static String TAG = "[Started Service]";

    final public static String[] actionTypes = new String[] {"1", "2", "3"};


    final public static int MESSAGE_STRING = 1;
    final public static int MESSAGE_INTEGER = 2;
    final public static int MESSAGE_ARRAY_LIST = 3;

    final public static String DATA = "ro.pub.cs.systems.eim.lab05.startedservice.data";

    final public static String STRING_DATA = "EIM";
//    final public static int INTEGER_DATA = Integer.parseInt(new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime()));


    final public static long SLEEP_TIME = 10000;

    public static final int NUMBER_OF_CLICKS_THRESHOLD = 3;
    public static final int SERVICE_STOPPED = 0;
    public static final int  SERVICE_STARTED = 1;
}
