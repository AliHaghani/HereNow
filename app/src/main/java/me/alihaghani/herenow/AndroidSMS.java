package me.alihaghani.herenow;

import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by shivashisht on 2016-01-23.
 */

public class AndroidSMS {

    SmsManager shortMessageManager;

    // Get array
    HashSet<String> phoneNumberArray;

    String[] SMSNumbers;
    public AndroidSMS()
    {
       this.shortMessageManager = SmsManager.getDefault();
        phoneNumberArray = Contacts.getNumbers();
        this.SMSNumbers = phoneNumberArray.toArray(new String[phoneNumberArray.size()]);
    }





    String message = "Hi! This is a text from HereNow to let you know your friend/family member has reached their destination.";

    public void sendSMS() {
        for (int i = 0; i < SMSNumbers.length; i++) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            shortMessageManager.sendTextMessage(SMSNumbers[i],
                    null, message, null, null);
            //Log.d("PhoneNo", SMSNumbers[i]);
        }
    }
}


//    SmsManager smsManager = SmsManager.getDefault();
//    HashSet<String> contactsList = Contacts.getNumbers();
//
//    // SMS sent pending intent
//
//
//    public void sendSMS(){
//
//        for(String num : contactsList) {
//            smsManager.sendTextMessage(num, "Hi! You're friend has arrived!", );
//
//        }
//    }
//}
