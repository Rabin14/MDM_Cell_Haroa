package block.mdmcellharoa;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>  {

    Sample_Format mainActivity;
    ArrayList<DownModel> downModels;

    public MyAdapter(Sample_Format mainActivity, ArrayList<DownModel> downModels) {
        this.mainActivity = mainActivity;
        this.downModels = downModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(mainActivity.getBaseContext());
        View view = layoutInflater.inflate(R.layout.elements, null, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        myViewHolder.mName.setText(downModels.get(i).getName());
        myViewHolder.mLink.setText(downModels.get(i).getLink());

        myViewHolder.mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile(myViewHolder.mName.getContext(),downModels.get(i).getName(),"",DIRECTORY_DOWNLOADS,downModels.get(i).getLink());


            }

        });


    }
    //Runtime External storage permission for saving download files


    public void downloadFile(Context context, String fileName, String fileExtension, String destinationDirectory, String url) {

        DownloadManager downloadmanager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
      // Execute DownloadImage AsyncTask
        new DownloadImage().execute(url);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);

        downloadmanager.enqueue(request);




    }


    @Override
    public int getItemCount() {
        return downModels.size();
    }
    ProgressDialog mProgressDialog;
    // DownloadImage AsyncTask



    private class DownloadImage extends AsyncTask<String, Integer, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
// Create a progressdialog
            mProgressDialog = new ProgressDialog(mainActivity);
// Set progressdialog title
            mProgressDialog.setTitle("Downloading File");
// Set progressdialog message
            mProgressDialog.setMessage("Please Wait....");
            mProgressDialog.setIndeterminate(false);
// Show progressdialog

           // mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(0);
            mProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {

            // Get the number of task
            int count = URL.length;
            // Initialize a new list
            List<String> taskList= new ArrayList<>(count);

            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // We were cancelled; stop sleeping!
                }
            }
            return null;
        }

        // While Downloading Music File


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            ObjectAnimator animation = ObjectAnimator.ofInt(mProgressDialog, "progress", 100 * values[0]);
            animation.setDuration(1000);
            animation.setInterpolator(new LinearInterpolator());
            animation.start();
        }

        @Override
        protected void onPostExecute(Bitmap result) {

// Close progressdialog
            mProgressDialog.dismiss();
            Toast.makeText(mainActivity.getApplicationContext(), "Download Completed", Toast.LENGTH_LONG).show();

        }
    }

}
