package boost.hackathon.byt;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sparta on 04/03/15.
 */

public abstract class Datos {

    public static final String TAG = "WAXATAG";
    public static boolean wifiConnected = false;
    public static boolean mobileConnected = false;
    public static String user="";
    public static JSONObject USUARIO = null;

    public static ArrayList<JSONObject> ownProjects = null;
    public static ArrayList<JSONObject> waxaOwnProjects = null;

    public static ArrayList<JSONObject> otherProjects = null;
    public static ArrayList<JSONObject> allProjects = null;

    public static Project projectData = null;
    public static String mostrarUserFromProject = "";

}
