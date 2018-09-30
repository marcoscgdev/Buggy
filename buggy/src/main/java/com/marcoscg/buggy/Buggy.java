package com.marcoscg.buggy;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.Locale;

/**
 * Created by @MarcosCGdev on 12/12/2017.
 */

public class Buggy implements Thread.UncaughtExceptionHandler {

    private static Buggy instance;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private StringBuilder report;
    private Application application;
    private String email = "", subject = "";

    private String INTENT_EXTRA_SUBJECT = "CRASH_ERROR_SUBJECT";
    private String INTENT_EXTRA_EMAIL = "CRASH_ERROR_EMAIL";
    private String INTENT_EXTRA_INFO = "CRASH_ERROR_INFO";

    private Buggy(Application application){
        this.application = application;
    }

    public static Buggy init(Application application) {
        if (instance == null)
            instance = new Buggy(application);
        return instance;
    }

    public Buggy withEmail(String email) {
        this.email = email;
        return this;
    }

    public Buggy withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public void start() {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        report = new StringBuilder();
        Date curDate = new Date();
        report.append("-------START-BUG-REPORT-------\n\n\n");
        report.append("Report collected on: ").append(curDate.toString()).append('\n');
        report.append("\n\nDevice information:\n\n");
        addInformation(report);
        report.append("\n\nError information:\n\n");
        collectStackInfo(ex, report);

        Intent intent = new Intent(application.getApplicationContext(), BuggyActivity.class);
        intent.putExtra(INTENT_EXTRA_EMAIL, email);
        intent.putExtra(INTENT_EXTRA_SUBJECT, subject);
        intent.putExtra(INTENT_EXTRA_INFO, report.toString());
        // here I do logging of exception to a db
        PendingIntent myActivity = PendingIntent.getActivity(application.getApplicationContext(),
                192837, intent,
                PendingIntent.FLAG_ONE_SHOT);

        AlarmManager alarmManager;
        alarmManager = (AlarmManager) application.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager!=null)
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    0, myActivity);

        System.exit(2);

        mDefaultHandler.uncaughtException(thread, ex);
    }

    private void collectStackInfo(Throwable exception, StringBuilder report) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        exception.printStackTrace(printWriter);
        report.append(result.toString());
        report.append("\n\n\n--------END-BUG-REPORT--------\n\n");
        printWriter.close();
    }

    private void addInformation(StringBuilder message) {
        message.append("Locale: ").append(Locale.getDefault()).append("\n");
        PackageManager pm = application.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(application.getPackageName(), 0);
            message.append("Version: ").append(pi.versionName).append("\n");
            message.append("Package: ").append(pi.packageName).append("\n");
        } catch (PackageManager.NameNotFoundException e) {
            message.append("Could not get version infomation for ").append(application.getPackageName());
        }
        message.append("Phone Model: ").append(android.os.Build.MODEL).append("\n");
        message.append("Android Version: ").append(android.os.Build.VERSION.RELEASE).append("\n");
        message.append("Board: ").append(android.os.Build.BOARD).append("\n");
        message.append("Brand: ").append(android.os.Build.BRAND).append("\n");
        message.append("Device: ").append(android.os.Build.DEVICE).append("\n");
        message.append("Host: ").append(android.os.Build.HOST).append("\n");
        message.append("ID: ").append(android.os.Build.ID).append("\n");
        message.append("Model: ").append(android.os.Build.MODEL).append("\n");
        message.append("Product: ").append(android.os.Build.PRODUCT).append("\n");
        message.append("Type: ").append(android.os.Build.TYPE).append("\n");
    }
}