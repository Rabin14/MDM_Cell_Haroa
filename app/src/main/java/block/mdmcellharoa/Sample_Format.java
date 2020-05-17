package block.mdmcellharoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Sample_Format extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample__format);
    }

    public void annualdata(View view) {
        Intent intent = new Intent(Sample_Format.this, web.class);
        intent.putExtra("url", "https://firebasestorage.googleapis.com/v0/b/mdm-cell-haroa.appspot.com/o/1)%20FINAL%20ANNUAL%20DATA%20FORMAT-%202020.pdf?alt=media&token=33ca6151-2218-4bda-9c3f-39cb77d9c9d6");
        startActivity(intent);

    }

    public void monthly(View view) {
        Intent intent = new Intent(Sample_Format.this, web.class);
        intent.putExtra("url", "https://firebasestorage.googleapis.com/v0/b/mdm-cell-haroa.appspot.com/o/2)%20MONTHLY%20DATA%20ENTRY%20FORM-2020.pdf?alt=media&token=a9c04aa9-2e1e-45b8-a688-6967e15d8f6e");
        startActivity(intent);

    }

    public void sms(View view) {
        Intent intent = new Intent(Sample_Format.this, web.class);
        intent.putExtra("url", "https://firebasestorage.googleapis.com/v0/b/mdm-cell-haroa.appspot.com/o/3)%20FINAL%20SMS%20FORMAT-2020.pdf?alt=media&token=1e3265e2-f278-46d1-9ded-81966d0e265d");
        startActivity(intent);

    }

    public void uc(View view) {
        Intent intent = new Intent(Sample_Format.this, web.class);
        intent.putExtra("url", "https://firebasestorage.googleapis.com/v0/b/mdm-cell-haroa.appspot.com/o/5)%20U.C%20FORMAT%20-2020.pdf?alt=media&token=47f82284-e5ec-43c6-afd1-7453798ea8c5");
        startActivity(intent);

    }

    public void calender(View view) {
        Intent intent = new Intent(Sample_Format.this, web.class);
        intent.putExtra("url", "https://firebasestorage.googleapis.com/v0/b/mdm-cell-haroa.appspot.com/o/MDM%20CALENDER-2019-2020.pdf?alt=media&token=d690e9c5-8e88-4b25-86d4-c81ce7b71295");
        startActivity(intent);
    }

    public void menu(View view) {
        Intent intent = new Intent(Sample_Format.this, web.class);
        intent.putExtra("url", "https://firebasestorage.googleapis.com/v0/b/mdm-cell-haroa.appspot.com/o/MDM%20MENU%20CHART-2019.pdf?alt=media&token=f27938e0-b045-4316-953c-b82434dba1a3");
        startActivity(intent);
    }

    public void format(View view) {
        Intent intent = new Intent(Sample_Format.this, web.class);
        intent.putExtra("url", "https://firebasestorage.googleapis.com/v0/b/mdm-cell-haroa.appspot.com/o/6)%20FORMAT-I%20%26%20II%2C%20FOR%20SCHOOL%20LEVEL-2019.xlsx?alt=media&token=2240372d-2ab9-410f-b352-f3e3019df9d5");
        startActivity(intent);

    }

    public void ppt(View view) {
        Intent intent = new Intent(Sample_Format.this, web.class);
        intent.putExtra("url", "https://firebasestorage.googleapis.com/v0/b/mdm-cell-haroa.appspot.com/o/SMS%20PPT%20%40%20MADE%20BY%20BDO%20HAROA-2017.ppt?alt=media&token=ccbfa873-26e7-4536-aaef-030488765be0");
        startActivity(intent);

    }

}