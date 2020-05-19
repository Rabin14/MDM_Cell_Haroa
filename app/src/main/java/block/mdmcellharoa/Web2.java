package block.mdmcellharoa;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class Web2 extends AppCompatActivity {
    WebView wv;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        // btn = (Button) findViewById(R.id.button);
        // txt = (TextView) findViewById(R.id.editText);
        wv = (WebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        // wv.setWebViewClient(new MyBrowser());


       // wv.setInitialScale(70);
       // wv.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);


        wv.setWebViewClient(new myWebClient());
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.getSettings().setDisplayZoomControls(false);

        wv.loadUrl(getIntent().getExtras().getString("url"));
    }




    @Override
    public void onBackPressed() {
        if(wv.canGoBack()){
            wv.goBack();
        }
        else {
            super.onBackPressed();
        }

    }


    public  class myWebClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            progressBar.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {

            super.onPageFinished(view, url);

            progressBar.setVisibility(View.GONE);

        }

    }

}
