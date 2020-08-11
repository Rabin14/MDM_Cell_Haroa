package block.mdmcellharoa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ContactUs extends AppCompatActivity {
TextView mobile1,mobile2,mobile3,mobile4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        mobile1 = findViewById(R.id.mobile1);
        mobile2 = findViewById(R.id.mobile2);
        mobile3 = findViewById(R.id.mobile3);
        mobile4 = findViewById(R.id.mobile4);
    }

    public void callme1(View view) {
        if (Build.VERSION.SDK_INT > 22) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(ContactUs.this, new String[]{Manifest.permission.CALL_PHONE}, 101);

                return;
            }
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + mobile1.getText().toString().trim()));
            startActivity(callIntent);
        } else {

            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + mobile1.getText().toString().trim()));
            startActivity(callIntent);
        }
    }

    public void msgme1(View view) {
        String phoneNumber = mobile1.getText().toString().trim();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + Uri.encode(phoneNumber)));
        startActivity(intent);
    }


    public void callme2(View view) {
        if (Build.VERSION.SDK_INT > 22) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(ContactUs.this, new String[]{Manifest.permission.CALL_PHONE}, 101);

                return;
            }
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + mobile2.getText().toString().trim()));
            startActivity(callIntent);
        } else {

            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + mobile2.getText().toString().trim()));
            startActivity(callIntent);
        }
    }

    public void msgme2(View view) {
        String phoneNumber = mobile2.getText().toString().trim();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + Uri.encode(phoneNumber)));
        startActivity(intent);
    }


    public void callme3(View view) {
        if (Build.VERSION.SDK_INT > 22) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(ContactUs.this, new String[]{Manifest.permission.CALL_PHONE}, 101);

                return;
            }
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + mobile3.getText().toString().trim()));
            startActivity(callIntent);
        } else {

            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + mobile3.getText().toString().trim()));
            startActivity(callIntent);
        }
    }

    public void msgme3(View view) {
        String phoneNumber = mobile3.getText().toString().trim();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + Uri.encode(phoneNumber)));
        startActivity(intent);
    }

    public void callme4(View view) {
        if (Build.VERSION.SDK_INT > 22) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(ContactUs.this, new String[]{Manifest.permission.CALL_PHONE}, 101);

                return;
            }
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + mobile4.getText().toString().trim()));
            startActivity(callIntent);
        } else {

            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + mobile4.getText().toString().trim()));
            startActivity(callIntent);
        }
    }

    public void msgme4(View view) {
        String phoneNumber = mobile4.getText().toString().trim();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + Uri.encode(phoneNumber)));
        startActivity(intent);

    }
}