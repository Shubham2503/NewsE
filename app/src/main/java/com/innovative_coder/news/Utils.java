package com.innovative_coder.news;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public class Utils {
    public static ColorDrawable[] vibrantLightColorList =
            {
                    new ColorDrawable(Color.parseColor("#ffeead")),
                    new ColorDrawable(Color.parseColor("#93cfb3")),
                    new ColorDrawable(Color.parseColor("#fd7a7a")),
                    new ColorDrawable(Color.parseColor("#faca5f")),
                    new ColorDrawable(Color.parseColor("#1ba798")),
                    new ColorDrawable(Color.parseColor("#6aa9ae")),
                    new ColorDrawable(Color.parseColor("#ffbf27")),
                    new ColorDrawable(Color.parseColor("#d93947"))
            };

    public static ColorDrawable getRandomDrawbleColor() {
        int idx = new Random().nextInt(vibrantLightColorList.length);
        return vibrantLightColorList[idx];
    }

    public static String DateToTimeFormat(String oldstringDate) throws ParseException {
//        PrettyTime p = new PrettyTime(new Locale(getCountry()));
//        String isTime = null;
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",
//                    Locale.ENGLISH);
//            Date date = sdf.parse(oldstringDate);
//            isTime = p.format(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");

            Date date = parser.parse(oldstringDate);
            TimeZone utc = TimeZone.getTimeZone("UTC");
        SimpleDateFormat s2 = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
        s2.setTimeZone(TimeZone.getDefault());

        return s2.format(date);
    }

    public static String DateFormat(String oldstringDate) throws ParseException {
//        String newDate;
//        SimpleDateFormat dateFormat = new SimpleDateFormat("E, d MMM yyyy", new Locale(getCountry()));
//        try {
//            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(oldstringDate);
//            newDate = dateFormat.format(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            newDate = oldstringDate;
//        }
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");

        Date date = parser.parse(oldstringDate);
        TimeZone utc = TimeZone.getTimeZone("UTC");
        SimpleDateFormat s1 = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH);
        s1.setTimeZone(TimeZone.getDefault());

        return s1.format(date);
    }

    public static String getCountry(){
        Locale locale = Locale.getDefault();
        String country = String.valueOf(locale.getCountry());
        return country.toLowerCase();
    }

    public static String getLanguage(){
        Locale locale = Locale.getDefault();
        String country = String.valueOf(locale.getLanguage());
        return country.toLowerCase();
    }
}
