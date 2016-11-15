package com.example.anuj.assignment5;

import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainActivity extends AppCompatActivity  {

    private TextView mShowAboutTextView;
    private  String mUrl = " https://www.iiitd.ac.in/about" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void show_about_pressed(View view) {
        new DownloadTask().execute(mUrl) ;
        mShowAboutTextView = (TextView)findViewById ( R.id.showAboutTextview ) ;


    }



    private class DownloadTask extends AsyncTask<String , Void, String> {


        private static final String TAG = "Download Task";
        private   String mAbout ="";
        private   String mTotal = "" ;



        @Override
        protected String doInBackground(String ... urls) {


            try {
                mAbout = "" ;
                mTotal = "" ;
                Document doc = Jsoup.connect("https://www.iiitd.ac.in/about").get();
                mTotal += doc ;
                System.out.print(mTotal);
                Elements paragraphs = doc.select("p");
                for( int i = 0 ; i < paragraphs.size() ; i++) {
                    Element p = paragraphs.get(i) ;

                    if( i == 6|| i==7) {
                        mAbout+= p.text() ;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return mAbout;
        }

        @Override
        protected void onPostExecute(String about) {

            Log.d(TAG , "Download Complete") ;
            Toast.makeText( getApplicationContext() , "Download Complete" , Toast.LENGTH_SHORT).show();
            mShowAboutTextView.setText( mAbout );
            mShowAboutTextView.setVisibility(View.VISIBLE);
        }


    }


}
