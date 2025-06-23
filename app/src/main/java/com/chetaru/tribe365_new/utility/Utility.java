package com.chetaru.tribe365_new.utility;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.core.graphics.drawable.DrawableCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Pattern;

import id.zelory.compressor.Compressor;

//import id.zelory.compressor.Compressor;


/**
 * Created by prakhar on 8/3/18.
 */

public class Utility {
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    public static boolean isValidEmail(CharSequence target) {
        if (target == null || target.toString().isEmpty()) {
            return false;
        } else {
            return EMAIL_ADDRESS_PATTERN.matcher(target.toString()).matches();

        }

    }
    String result = "";
    StringBuilder sb;


    //in this class we will writing code which we need to use more often
    //for eg: fetching current date or showing toast
    String startdate = "";

    public static boolean isAppRunning(final Context context, final String packageName) {
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        if (procInfos != null) {
            for (final ActivityManager.RunningAppProcessInfo processInfo : procInfos) {
                if (processInfo.processName.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Drawable tintMyDrawable(Drawable drawable, int color) {
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, color);
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
        return drawable;
    }

    public static String image_to_Base64(Context context, String filePath) {
        Bitmap bmp = null;
        ByteArrayOutputStream bos = null;
        byte[] bt = null;
        String encodeString = null;
        File sourceFile;
        Compressor compressedImageBitmap;

        try {
            sourceFile = new File(filePath);

            bmp = BitmapFactory.decodeFile(filePath);
            bmp = new Compressor(context).compressToBitmap(sourceFile);
            bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bt = bos.toByteArray();
            encodeString = Base64.encodeToString(bt, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError ome) {

        }
        return encodeString;
    }

    public static String format(Number n) {
        NumberFormat format = DecimalFormat.getInstance();
        format.setRoundingMode(RoundingMode.FLOOR);
        format.setMinimumFractionDigits(0);
        format.setMaximumFractionDigits(2);
        return format.format(n);
    }

    public static Bitmap decodeSampledBitmapFromResource(String path, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }


    /*private <T> Set<T> findDuplicates(Collection<T> collection) {

        Set<T> duplicates = new LinkedHashSet<>();
        Set<T> uniques = new HashSet<>();

        for(T t : collection) {
            if(!uniques.add(t)) {
                duplicates.add(t);
            }
        }

        return duplicates;
    }*/
    public <T> Set<T> findDuplicates(Collection<T> collection){
        Set<T> duplicates= new LinkedHashSet<>();
        Set<T> uniques = new HashSet<>();
        for (T t: collection){
            if (!uniques.add(t)){
                duplicates.add(t);
            }
        }
        return duplicates;
    }

   /* public String getAddressFromLatlong(Context context,double lat, double longi) {

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        if (InternetDetect.isConnected(getApplicationContext())) {
            //locationPresenter.runGeocodeAPI(latLng.latitude + "," + latLng.longitude, AppConstants.GEOCODE_API_KEY);
            List<Address> addresses = new ArrayList<>();
            try {
                addresses = geocoder.getFromLocation(lat, longi, 1);

                //latitude= latLng.latitude;
                //longitude = latLng.longitude;
            } catch (IOException e) {
                e.printStackTrace();
            }

                Address address = addresses.get(0);


            if (address != null) {
                sb = new StringBuilder();
                if(address.getMaxAddressLineIndex()>0){
                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                        sb.append(address.getAddressLine(i) + "\n");
                    }
                }else {
                    try {
                        sb.append(address.getAddressLine(0));
                    } catch (Exception ignored) {}
                }
            }
        }
        return sb.toString();

    }*/

    public void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public String getAndroidID(Context context) {
        String android_id = Settings.System.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return android_id;
    }

    public Bitmap getBitmap(String path) {
        Bitmap bitmap = null;
        try {
            File f = new File(path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
            //image.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public boolean showDialog(final Context context, String heading, String message) {


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle(heading);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //return true;
                result = "yes";

                // Write your code here to invoke YES event
                //Toast.makeText(context, "You clicked on YES", Toast.LENGTH_SHORT).show();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                result = "no";
                // Write your code here to invoke NO event
                //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
        return result.equals("yes");

    }

    //public TextView showCalender(Context context,TextView tv) {
    public String showCalender(Context context) {
        final String[] startDate = {""};
        //here we are calling calendar
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        //tv.setText("");


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        //tv_startdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        startDate[0] = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
        //tv.setText(startdate);
        //return tv;
        return startDate[0];
    }

    public String meterToKM(float meters) {
        float km;
        km = meters / 1000;
        return format(km);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getPath(Uri uri, Context context) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();
        return s;
    }

    public String changeYMDtoDate(String dateStr){
        //String dateStr = "2014-01-09T09:33:20+0530";
        String newDateStr="";
        try {

            SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
            Date dateObj = curFormater.parse(dateStr);

             newDateStr= curFormater.format(dateObj);
        }catch (Exception e){
            e.printStackTrace();
        }
        return newDateStr;
    }
    public String changeDateYMDtoDMY(String date) {
        Date myDate;
        String outputDateStr = "";
        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
            myDate = inputFormat.parse(date);
            outputDateStr = outputFormat.format(myDate);
            System.out.println(outputDateStr);
        } catch (Exception e) {
            //java.text.ParseException: Unparseable date: Geting error
            System.out.println("Excep" + e);
        }
        return outputDateStr;
    }
    public String changeDateDMYtoYMD(String date) {
        Date myDate;
        String outputDateStr = "";
        try {
            DateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy");
            DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            myDate = inputFormat.parse(date);
            outputDateStr = outputFormat.format(myDate);
            //System.out.println(outputDateStr);
        } catch (Exception e) {
            //java.text.ParseException: Unparseable date: Geting error
           // System.out.println("Excep" + e);
        }
        return outputDateStr;
    }

    public String changeDatemonthName(String date) {
        Date myDate;
        String outputDateStr = "";
        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
            myDate = inputFormat.parse(date);
            outputDateStr = outputFormat.format(myDate);
            System.out.println(outputDateStr);
        } catch (Exception e) {
            //java.text.ParseException: Unparseable date: Geting error
            System.out.println("Excep" + e);
        }
        return outputDateStr;
    }

    public String parseDateToddMMyyyy(String newTime) {
        //String inputDateStr="2013-06-24";
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd MMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(newTime);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
    public String changeDate(String newDate){
        String outputDateStr = "";
        try {
            String dt = "2008-01-01";  // Start date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(dt));
            c.add(Calendar.DATE, 1);  // number of days to add
            dt = sdf.format(c.getTime());
            //System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH).format(date));
        }catch (Exception e){
            e.printStackTrace();
        }
        return outputDateStr;
    }
    public String changeDateName(String dateStr) {
        Date myDate;
        String outputDateStr = "";
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd MMM, yyyy  hh:mm aa", Locale.ENGLISH);
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            myDate = df.parse(dateStr);
            df.setTimeZone(TimeZone.getDefault());
            outputDateStr = df.format(myDate);
            Log.d("dateChange", outputDateStr);
        } catch (Exception e) {
            //java.text.ParseException: Unparseable date: Geting error
            System.out.println("Excep" + e);
        }
        return outputDateStr;
    }

    public String getCurrentDate() {
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        //String currentDateandTime = sdf.format(new Date());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        // df.setTimeZone(TimeZone.getDefault());
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }

    public String getTodayDate() {
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        //String currentDateandTime = sdf.format(new Date());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        //df.setTimeZone(TimeZone.getTimeZone("UTC"));
        df.setTimeZone(TimeZone.getDefault());
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }
    public String getLastDate(String ourDate){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(ourDate);
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM, yyyy  hh:mm aa"); //this format changeable
            dateFormatter.setTimeZone(TimeZone.getDefault());
            ourDate = dateFormatter.format(value);

            //Log.d("ourDate", ourDate);
        } catch (Exception e) {
            ourDate = "00-00-0000 00:00";
        }
        return ourDate;
    }

    public String getDate(String ourDate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(ourDate);
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy, hh:mm aa"); //this format changeable
            dateFormatter.setTimeZone(TimeZone.getDefault());
            ourDate = dateFormatter.format(value);

            //Log.d("ourDate", ourDate);
        } catch (Exception e) {
            ourDate = "00-00-0000 00:00";
        }
        return ourDate;
    }
    public String getFeedbackDate(String ourDate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(ourDate);
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy, hh:mm aa"); //this format changeable
            //dateFormatter.setTimeZone(TimeZone.getDefault());
            ourDate = dateFormatter.format(value);

            //Log.d("ourDate", ourDate);
        } catch (Exception e) {
            ourDate = "00-00-0000 00:00";
        }
        return ourDate;
    }
    public String getDateWOTime(String ourDate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(ourDate);
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM, yyyy "); //this format changeable
            dateFormatter.setTimeZone(TimeZone.getDefault());
            ourDate = dateFormatter.format(value);

            //Log.d("ourDate", ourDate);
        } catch (Exception e) {
            ourDate = "00-00-0000 00:00";
        }
        return ourDate;
    }


   /* public String changeDateYMDtoDMY(String date) {

        Date myDate;
        String outputDateStr = "";
        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");

            myDate = inputFormat.parse(date);
            outputDateStr = outputFormat.format(myDate);
            System.out.println(outputDateStr);

        } catch (Exception e) {
            //java.text.ParseException: Unparseable date: Geting error
            System.out.println("Excep" + e);
        }
        return outputDateStr;
    }*/

    public static String getEmojiFromString(String emojiString) {

        if (!emojiString.contains("\\u")) {

            return emojiString;
        }
        String emojiEncodedString = "";

        int position = emojiString.indexOf("\\u");

        while (position != -1) {

            if (position != 0) {
                emojiEncodedString += emojiString.substring(0, position);
            }

            String token = emojiString.substring(position + 2, position + 6);
            emojiString = emojiString.substring(position + 6);
            emojiEncodedString += (char) Integer.parseInt(token, 16);
            position = emojiString.indexOf("\\u");
        }
        emojiEncodedString += emojiString;

        return emojiEncodedString;
    }
    /*method to check the time span between 4 pm to 11 pm
     */
    public boolean checkTime() {
        try {
            String string1 = "16:00:00";
            Date time1 = new SimpleDateFormat("HH:mm:ss").parse(string1);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);
            //calendar1.add(Calendar.DATE, 1);
            String string2 = "23:59:00";
            Date time2 = new SimpleDateFormat("HH:mm:ss").parse(string2);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);
            //calendar2.add(Calendar.DATE, 1);
            String someRandomTime = "01:00:00";
            Date d = new SimpleDateFormat("HH:mm:ss").parse(someRandomTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(d);
            //calendar3.add(Calendar.DATE, 1);
            //Date x = calendar3.getTime();
            Calendar currTime = Calendar.getInstance();
            int hour = currTime.get(Calendar.HOUR_OF_DAY);
            int minut = currTime.get(Calendar.MINUTE);
            Float time = Float.parseFloat(hour + "." + minut);
            // Float time = Float.parseFloat(currTime.get(Calendar.HOUR_OF_DAY)+"."+currTime.get(Calendar.MINUTE));

            Date x = Calendar.getInstance().getTime();
//            int starttime= hour-1;
//            int endtime=hour+1;
            // Toast.makeText(activity, "true in time", Toast.LENGTH_SHORT).show();
            //Toast.makeText(activity, "not in time \nstarttime "+time +"endtime "+time, Toast.LENGTH_SHORT).show();
            return (time >= 16) && (time < 23.59);
//            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
//                //checkes whether the current time is between 14:49:00 and 20:11:13.
//                //Toast.makeText(activity, "true in time", Toast.LENGTH_SHORT).show();
//                return true;
//            }else{
//                //Toast.makeText(activity, "not in time", Toast.LENGTH_SHORT).show();
//                return false;
//            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()){
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }

    public void addAutoStartup(Context context) {

        try {
            Intent intent = new Intent();
            String manufacturer = android.os.Build.MANUFACTURER;
            if ("xiaomi".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
            } else if ("oppo".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity"));
            } else if ("vivo".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));
            } else if ("Letv".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AutobootManageActivity"));
            } else if ("Honor".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity"));
            }

            List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if  (list.size() > 0) {
                context.startActivity(intent);
            }



        } catch (Exception e) {
            Log.e("exc" , String.valueOf(e));
        }
    }


}

