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
        intent.putExtra("url", "https://karmodishari.co.in/wp-content/uploads/2020/05/1-FINAL-ANNUAL-DATA-FORMAT-2020.pdf");
        startActivity(intent);

    }

    public void monthly(View view) {
        Intent intent = new Intent(Sample_Format.this, web.class);
        intent.putExtra("url", "https://karmodishari.co.in/wp-content/uploads/2020/05/2-MONTHLY-DATA-ENTRY-FORM-2020.pdf");
        startActivity(intent);

    }

    public void sms(View view) {
        Intent intent = new Intent(Sample_Format.this, web.class);
        intent.putExtra("url", "https://karmodishari.co.in/wp-content/uploads/2020/05/3-FINAL-SMS-FORMAT-2020.pdf");
        startActivity(intent);

    }

    public void uc(View view) {
        Intent intent = new Intent(Sample_Format.this, web.class);
        intent.putExtra("url", "https://karmodishari.co.in/wp-content/uploads/2020/05/5-U.C-FORMAT-2020.pdf");
        startActivity(intent);

    }

    public void calender(View view) {
        Intent intent = new Intent(Sample_Format.this, web.class);
        intent.putExtra("url", "https://karmodishari.co.in/wp-content/uploads/2020/05/MDM-CALENDER-2019-2020.pdf");
        startActivity(intent);
    }

    public void menu(View view) {
        Intent intent = new Intent(Sample_Format.this, web.class);
        intent.putExtra("url", "https://karmodishari.co.in/wp-content/uploads/2020/05/MDM-MENU-CHART-2019.pdf");
        startActivity(intent);
    }

    public void format(View view) {
        Intent intent = new Intent(Sample_Format.this, web.class);
        intent.putExtra("url", "https://karmodishari.co.in/wp-content/uploads/2020/05/6-FORMAT-I-II-FOR-SCHOOL-LEVEL-2019.xlsx");
        startActivity(intent);

    }

    public void ppt(View view) {
        Intent intent = new Intent(Sample_Format.this, web.class);
        intent.putExtra("url", "https://karmodishari.co.in/wp-content/uploads/2020/05/SMS-PPT-@-MADE-BY-BDO-HAROA-2017.ppt");
        startActivity(intent);

    }

}