package com.example.sulistyarif.serverpulsapkm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by sulistyarif on 06/07/17.
 */

public class TangkapSMS extends BroadcastReceiver {

    DatabaseReference baseUsers, baseRelat;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String msg_from;

            System.out.println("Ada sms masuk..");

            if (bundle != null) {
                try {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];

                    for (int i = 0; i < msgs.length; i++) {
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        String isiSms = msgs[i].getMessageBody();

                        String parseSMS[] = isiSms.split(" ");
                        String nomorDiTopup = parseSMS[0];
                        String besarPulsa = parseSMS[4];

                        System.out.println("Pesan dari : " + msg_from + " berisi " + isiSms);

                        String kataPanjang[] = besarPulsa.split("");

                        String parsedPulsa = "";

                        for (int j = 3 ; j < kataPanjang.length ; j++){
                            parsedPulsa += kataPanjang[j];
                        }

                        System.out.println("Pesan telah diterima dari " + msg_from + " dengan isi " + isiSms);

                        System.out.println("Nomor : " + nomorDiTopup + " akan diisi " + besarPulsa + " atau sebesar " + parsedPulsa);

                        topUp(nomorDiTopup, parsedPulsa);
                    }

                } catch (Exception e) {
                    Log.d("Exeption caught ", e.getMessage());
                }
            }

        }
    }

    private void topUp(String nomorDiTopup, final String parsedPulsa) {

        System.out.println("Masuk ke method topUp");

        // pertama mencari user dengan nama, nomor telepon pengirim
//        FirebaseApp.initializeApp();

        baseUsers = FirebaseDatabase.getInstance().getReference("users");
        baseRelat = FirebaseDatabase.getInstance().getReference("relation");

        baseUsers.child(nomorDiTopup).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final User dataUser = dataSnapshot.getValue(User.class);
                baseRelat.child(dataUser.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Relation dataRelat = dataSnapshot.getValue(Relation.class);
                        int besarTransfer = Integer.parseInt(parsedPulsa);
                        int saldo = dataRelat.getSaldo();
                        int saldoAkhir = saldo + besarTransfer;

                        baseRelat.child(dataUser.getId()).child("saldo").setValue(saldoAkhir);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
