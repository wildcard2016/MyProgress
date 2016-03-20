package wildcard.android.com.myprogress;

import android.content.SharedPreferences;
import android.graphics.Color;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by Shiken Zijian Xu on 2016/03/20.
 */
public class MyUtils {
    public static ArrayList<String> StrToArr(String str) {
        String[] items = str.split(",");
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < items.length; i++) {
            arrayList.add(items[i]);
        }
        return arrayList;
    }
    public static String ArrToStr(ArrayList<String> arr) {
        String str = "";
        for (int i = 0; i < arr.size(); i++) {
            str += arr.get(i) + ",";
        }
        return str;
    }
    public static void AddToPref(SharedPreferences sharedPreferences, String key, String newValue) {
        String item = sharedPreferences.getString(key, "fake");
        item += "," + newValue;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, item);
        editor.apply();
    }
    public static ArrayList<String> getArr(SharedPreferences sharedPreferences, String key) {
        String items = sharedPreferences.getString(key, "fake");
        return StrToArr(items);
    }

    public static int getColor(String tag) {
        switch (tag) {
            case "work":
                return Color.parseColor("#5c6bc0");
            case "study":
                return Color.parseColor("#66bb6a");
            case "social":
                return Color.parseColor("#ffa726");
            case "read":
                return Color.parseColor("#26a69a");
            case "travel":
                return Color.parseColor("#ef5350");
            case "old":
                return Color.parseColor("#aca6a6");
            default:
                return 0;
        }
    }
    public static int getLightColor(String tag) {
        switch (tag) {
            case "work":
                return Color.parseColor("#c5cae9");
            case "study":
                return Color.parseColor("#c8e6c9");
            case "social":
                return Color.parseColor("#ffe082");
            case "read":
                return Color.parseColor("#b2dfbb");
            case "travel":
                return Color.parseColor("#ffcdd2");
            case "old":
                return Color.parseColor("#f5f5f5");
            default:
                return 0;
        }
    }
    public static int getLColor(String tag) {
        switch (tag) {
            case "work":
                return Color.parseColor("#7986cb");
            case "study":
                return Color.parseColor("#aed581");
            case "social":
                return Color.parseColor("#ffb74d");
            case "read":
                return Color.parseColor("#4db6ac");
            case "travel":
                return Color.parseColor("#e57373");
            case "old":
                return Color.parseColor("#e0e0e0");
            default:
                return 0;
        }
    }

    public boolean searchToday(String period) {
        Date date = new Date(System.currentTimeMillis());
        String today = new SimpleDateFormat("dd MMM yyyy", Locale.US).format(date);
        String[] dates = period.split(" - ");
        Scanner scan = new Scanner(today);
        int thisDay = scan.nextInt();
        String thisMonth = scan.next();
        scan = new Scanner(dates[0]);
        int startDay = scan.nextInt();
        String startMonth = scan.next();
        scan = new Scanner(dates[1]);
        int endDay = scan.nextInt();
        String endMonth = scan.next();
        if (thisMonth.equals(startMonth) && startDay <= thisDay) {
            if (! thisMonth.equals(endMonth)) return true;
            else if (thisDay <= endDay) return  true;
            else return false;
        }else if (startMonth.equals("Feb") && thisMonth.equals(endMonth) && thisDay <= endDay) {
            return true;
        }else {
            return false;
        }

        // not correct; just a temporary method
    }
}
