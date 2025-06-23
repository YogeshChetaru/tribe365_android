package com.chetaru.tribe365_new.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;

import java.io.ByteArrayOutputStream;
import java.util.regex.Pattern;

/**
 * Created by Abhisheik on 04-09-2015.
 */
public class Constant {
    public static ProgressDialog progressDialog = null;
    public static AlertDialog.Builder alertbox;
    public static AlertDialog alertDialog;
    public static LayoutInflater _inflater;
    public static View progressdialogview;
    public static Dialog progress_dialog;
    public static RotateAnimation rAnim;

    public static String year = "year";
    public static String month = "month";
    public static String week = "week";
    public static String day = "day";


    public static String _clientName = "";
    public static String _clientMessageCount = "";
    public static String _clientDisc = "";
    public static String _time = "";
    public static int _clientId = 0;
    public static int _clientFollowStatus = -1;
    public static String _clientImage = "";
    public static String _user_image_base64 = "";
    public Context activity;

    //    //change status bar code on lollipop
//    public static void changeStatusBarColor(Activity con)
//
//    {
//        Window window = con.getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        window.setStatusBarColor(con.getResources().getColor(R.color.blue));
//    }
//    public static void cancelDialog()
//    {
//        if(progress_dialog!=null)
//        {
//            //			System.out.println("Dialog is Canceling");
//            progress_dialog.cancel();
////            progress_dialog = null;
//        }
//    }
    //    // ///////////////////show Progress dialog
//    public static void showProgressDialog(final Context context)
// {
//        progressDialog = new ProgressDialog(context,
//                android.R.style.Theme_Panel);
//        progressDialog.setMessage("Please wait...");
//        progressDialog.show();
//        progressDialog.setCancelable(false);
//        //		System.out.println("Dialog is Showing");
//    }
// ///////////////////show Progress dialog
//    public static void showProgressDialog(final Context context)
//    {
//        _inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        progressdialogview = _inflater.inflate(R.layout.progress_bar_layout,null);
//
//        progress_dialog = new Dialog(context,android.R.style.Theme_Panel);
//        progress_dialog.setContentView(progressdialogview);
//        progress_dialog.setCancelable(false);
//        progress_dialog.show();

//        progressDialog = new ProgressDialog(context,R.layout.progress_bar_layout);
//        View v = View.inflate(context,R.layout.progress_bar_layout, null);
//        ImageView rotate = (ImageView) progressdialogview.findViewById(R.id.rotateImage);
//    progressDialog.setMessage("Please wait...");
//        rAnim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        rAnim.setRepeatCount(Animation.INFINITE);
//        rAnim.setInterpolator(new LinearInterpolator());
//        rAnim.setDuration(700);
    /* refreshIcon is object of an imageView */
//        rotate.startAnimation(rAnim);
    //		System.out.println("Dialog is Showing");
//    }

    public static boolean checkEmail(String email) {
        String expression = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*"
                + "+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
        Pattern emailPattern = Pattern.compile(expression);
        return emailPattern.matcher(email).matches();
    }

//    //hide key board
//    public static void hideKeyBoard(View view, Activity context)
//    {
//        View focusedView = context.getCurrentFocus();
//        if (focusedView != null) {
//            ((InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE)).
//                    hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }
//    }


    public static void showAlertDialog(final String title, String message, final Context context, final boolean redirectToPreviousScreen) {
        if (alertDialog != null && alertDialog.isShowing()) {
        } else {
            alertbox = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
            alertbox.setMessage(message);
            alertbox.setTitle(title);
            /*alertbox.setTitle(Gravity.CENTER);*/
            alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    alertDialog.dismiss();
                }
            });
            alertDialog = alertbox.create();
            alertDialog.show();
        }
    }

    ///////// key board hide
    public static void hideKeyBoard(Activity context) {
        View focusedView = context.getCurrentFocus();
        //	 Toast.makeText(context,"not hide", 1).show();
        if (focusedView != null) {
            //       Toast.makeText(context,"hide", 1).show();
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(context.getWindow().getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static String convertBitmapToBase64(Bitmap image) {
        String imageEncoded = "";
        try {
            Bitmap immagex = image;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            immagex.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
            //						Log.e("LOOK", imageEncoded);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            System.out.println("Error 1 : " + e);
        } catch (Exception e1) {
            e1.printStackTrace();
            System.out.println("Error 2 : " + e1);
        }

        return imageEncoded;
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        String base64 = null;
        try {
            if (bitmap != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
                byte[] _byteArray = baos.toByteArray();
                base64 = Base64.encodeToString(_byteArray, Base64.DEFAULT);
//				base64 = Base64.encodeToString(_byteArray,Base64.NO_WRAP);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return base64;
    }

}