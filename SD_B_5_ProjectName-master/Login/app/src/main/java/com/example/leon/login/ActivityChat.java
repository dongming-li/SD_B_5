package com.example.leon.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
//import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiahan on 10/1/17.
 */

public class ActivityChat extends AppCompatActivity {


    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    Firebase reference1, reference2;


    ImageView photoButton;
    DatabaseReference mDtabaseReference;

    FirebaseStorage storage = FirebaseStorage.getInstance();

    ImageView fileButton;


//    public static final String URL_STORAGE_REFERENCE = "gs://firebase-collegeproject.appspot.com/";

    public static final String URL_STORAGE_REFERENCE = "gs://logbin-26885.appspot.com/";

    public static final String FOLDER_STORAGE_IMG = "images";

    public static final String FOLDER_STORAGE_FILE = "files";


    boolean isImageFitToScreen;

    ImageView largeImage ;



    // get current date time in long
    public long getDateTimeCurrent(){
        long currenttime = System.currentTimeMillis();
        return currenttime;
//        currentdatetime = currenttime;
    }
    // convert timestamp to string with format
    public String getDateTime( long timestamp ){

        Date date = new Date( timestamp );
        Format format = new SimpleDateFormat( "E hh:mm:ssa M/d/y" );
        return format.format( date );

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        layout = (LinearLayout) findViewById(R.id.layout1);
        layout_2 = (RelativeLayout)findViewById(R.id.layout2);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        messageArea = (EditText)findViewById(R.id.messageArea);
        scrollView = (ScrollView)findViewById(R.id.scrollView);

//        largeImage = (ImageView) findViewById( R.id.largeImage);


        photoButton = (ImageView) findViewById( R.id.imageButton);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, 11);
            }
        });

        fileButton = (ImageView) findViewById( R.id.fileButton);
        fileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                intent.setType("application/pdf");

                intent.setType("*/*");
                String[] mimetypes = { "application/pdf" , "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "application/msword"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);

                startActivityForResult(intent, 111);
            }
        });

//        "application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
//                "application/vnd.ms-powerpoint","application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
//                "application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
//                "text/plain",


        Firebase.setAndroidContext(this);


//        reference1 = new Firebase("https://fir-collegeproject.firebaseio.com/messages/" + UserDetails.username + "_" + UserDetails.chatWith);
//        reference2 = new Firebase("https://fir-collegeproject.firebaseio.com/messages/" + UserDetails.chatWith + "_" + UserDetails.username);

        reference1 = new Firebase("https://logbin-26885.firebaseio.com/messages/" + UserDetails.username + "_" + UserDetails.chatWith);
        reference2 = new Firebase("https://logbin-26885.firebaseio.com/messages/" + UserDetails.chatWith + "_" + UserDetails.username);



        Toast.makeText( getApplicationContext(), "hhh" , Toast.LENGTH_LONG );
        scrollView.fullScroll(View.FOCUS_DOWN);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<>();


                    map.put("timestamp",  String.valueOf((getDateTimeCurrent()))  );

//                    Toast.makeText( getApplicationContext() , (int) getDateTimeCurrent(), Toast.LENGTH_LONG );


                    map.put("message", messageText);
                    map.put("user", UserDetails.username);


                    map.put("link" , "" );

                    map.put("file_link", "");


                    reference1.push().setValue(map);
                    reference2.push().setValue(map);


//                    reference1.push().setValue("");
//                    reference2.push().setValue("");


                    messageArea.setText("");
                }


//                scrollView.smoothScrollTo( 0 , scrollView.getHeight() );

                scrollView.fullScroll(View.FOCUS_DOWN);


            }
        });



//        final TextView eachtextView = new TextView( ActivityChat.this );

//        float sourceTextSize = eachtextView.getTextSize();
//        TextView.setTextSize(sourceTextSize / getResources().getDisplayMetrics().density);

//        float textsize = eachtextView.getTextSize(  getResources().getDisplayMetrics().scaledDensity );

//        Log.e("each text view" , String.valueOf( eachtextView.getTextSize()));
//        if( eachtextView.getTextSize() == 6)
//        if( eachtextView.getText().toString().startsWith( "https" ) ) {
//
//            Log.e("clicked file", String.valueOf(eachtextView.getTextSize()));
//
//            eachtextView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
////                String url = "http://www.axmag.com/download/pdfurl-guide.pdf";
//
//                    String url = eachtextView.getText().toString();
//
//                    Log.e("file url", url);
//
////                String url = "http://www.axmag.com/download/pdfurl-guide.pdf";
//
//                    WebView webView = new WebView(ActivityChat.this);
////                WebView webView = (WebView) findViewById(R.id.webView);
//                    webView.getSettings().setJavaScriptEnabled(true);
//                    webView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + url);
//
//
//                }
//            });
//        }


        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);


                String datetime;
//                String datetime = map.get("timestamp").toString() ;
                if( map.get("timestamp") != null) {
                    datetime = map.get("timestamp").toString();
//                    datetime = getDateTime(  map.get("timestamp").toString()  );
                }
                else {
                    datetime = "1";
                }

                String finaldt = getDateTime(  Long.parseLong( datetime )  );


                String messageimage = "";
                if( map.get("link")!=null ) {
                    messageimage = map.get("link").toString();
                }

                String message = "";
                if( map.get("message") != null) {
                    message = map.get("message").toString();
                }
//                        + "\n"
//                        + getDateTime(  Long.parseLong( datetime )  )
                        ;

                String userName = "";
                if( map.get("user") != null) {
                    userName = map.get("user").toString();
                }
//                String userName = map.get("user").toString();

                String messagefile = "";
                if( map.get("file_link") != null) {
                    messagefile = map.get("file_link").toString();
                }


                if(userName.equals(UserDetails.username)){
                    addMessageBox(
//                            "You: " +

                            finaldt ,
                            messageimage ,
                            messagefile ,

                            message, 1);
                }
                else{
                    addMessageBox(
//                            UserDetails.chatWith + ": " +

                            finaldt ,
                            messageimage ,
                            messagefile ,

                            message, 2);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



    }


    public void addMessageBox( String finaldt , String messageimage, String messagefile , String message, int type){

//        TextView title = (TextView) findViewById(R.id.textView3);
//        title.setText("Conversation with " + UserDetails.chatWith );


        TextView dt = new TextView(ActivityChat.this);
        dt.setText(finaldt);


        TextView textView = new TextView(ActivityChat.this);
        textView.setText(message);


        TextView textView2 = new TextView( ActivityChat.this);
        textView2.setText(messageimage);

        final TextView textView3 = new TextView( ActivityChat.this);
        textView3.setText(
//                "A file: " +
                messagefile
//                        + " from " + UserDetails.chatWith
        );


        ImageView imageView = new ImageView( ActivityChat.this);
//        imageView.setBackgroundColor( Color.rgb(246, 111, 113) );


//        ImageView imageView = (ImageView) findViewById(R.id.my_image_view);
//        Glide.with(this)
//                .load( messageimage )
//                .apply(
//                        new RequestOptions()
//                                .circleCrop()
//                                .fitCenter()
//                                .centerCrop()
//                )
//                .into(imageView);


        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if(type == 1) {
            lp2.gravity = Gravity.RIGHT;

            textView.setTextColor( Color.rgb(246, 111, 113) );


            textView.setTextSize(getResources().getDimension(R.dimen.textsize1));

            textView.setPadding( 0 , 19, 0, 3);
//            textView.setBackgroundColor( Color.rgb(129, 136, 166) );


//            textView.setBackgroundResource(R.drawable.bubble_in);



            dt.setTextColor( Color.rgb(167, 235, 173) );
            dt.setTextSize(getResources().getDimension(R.dimen.textsizedt));
            dt.setPadding( 0 , 0, 0, 19);


            textView2.setTextColor( Color.rgb(246, 111, 113) );
            textView2.setTextSize(getResources().getDimension(R.dimen.textsize1));
            textView2.setPadding( 0 , 19, 0, 3);


            imageView.setPadding( 0 , 39, 0, 3);


            textView3.setTextColor( Color.rgb(246, 111, 113) );
            textView3.setTextSize(getResources().getDimension(R.dimen.textsizefile));
            textView3.setPadding( 0 , 19, 0, 3);


        }
        else{
            lp2.gravity = Gravity.LEFT;


            textView.setTextColor( Color.rgb(112, 170, 243) );

//            textView.setTextColor( Color.rgb(77, 94, 228) );


            textView.setTextSize(getResources().getDimension(R.dimen.textsize2));

            textView.setPadding( 0 , 19, 0, 3);



            dt.setTextColor( Color.rgb(167, 235, 173) );
            dt.setTextSize(getResources().getDimension(R.dimen.textsizedt));
            dt.setPadding( 0 , 0, 0, 19);



            textView2.setTextColor( Color.rgb(246, 111, 113) );
            textView2.setTextSize(getResources().getDimension(R.dimen.textsize2));
            textView2.setPadding( 0 , 19, 0, 3);



            imageView.setPadding( 0 , 39, 0, 3);

            textView3.setTextColor( Color.rgb(246, 111, 113) );
            textView3.setTextSize(getResources().getDimension(R.dimen.textsizefile));
            textView3.setPadding( 0 , 19, 0, 3);

//            textView.setBackgroundResource(R.drawable.bubble_out);
        }






        Log.e("textview", "[" + textView.getText() + "]");

        if( textView.getText().equals( "" ) == false ) {
            textView.setLayoutParams(lp2);
            layout.addView(textView);
        }

        if( textView2.getText().equals( "" ) == false ) {
//            textView2.setLayoutParams(lp2);
//            layout.addView(textView2);

            Glide.with(getApplicationContext())
                    .load( messageimage )
                    .apply(
                            new RequestOptions()
//                                    .circleCrop()
                                    .fitCenter()
//                                    .centerCrop()
//                            .override( 2000 , 600)
                                    .override( 2100 , 800)

                    )
                    .into(imageView);

            imageView.setLayoutParams(lp2);
            layout.addView(imageView);


//            imageView.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    if (isImageFitToScreen) {
//                        isImageFitToScreen = false;
//                        largeImage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//                        largeImage.setAdjustViewBounds(true);
//                    } else {
//                        isImageFitToScreen = true;
//                        largeImage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//                        largeImage.setScaleType(ImageView.ScaleType.FIT_XY);
//                    }
//                }
//            });


        }


        if( textView3.getText().equals( "" ) == false ) {
            textView3.setLayoutParams(lp2);
            layout.addView(textView3);

            textView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                String url = "http://www.axmag.com/download/pdfurl-guide.pdf";

//                    String beforeurl = "File: ";

                    String url = textView3.getText().toString();

                    Log.e("file url", url);

//                String url = "http://www.axmag.com/download/pdfurl-guide.pdf";

//                    WebView webView = new WebView(ActivityChat.this);
////                WebView webView = (WebView) findViewById(R.id.webView);
//                    webView.getSettings().setJavaScriptEnabled(true);
//                    webView.loadUrl(  url );

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse( url ));
                    startActivity(browserIntent);

                }
            });
        }

//        if(imageView != null) {
//            imageView.setLayoutParams(lp2);
//            layout.addView(imageView);
//        }

        dt.setLayoutParams(lp2);
        layout.addView( dt );



        scrollView.fullScroll(View.FOCUS_DOWN);



    }

    public static void clickImage( ImageView imageView) {

//        boolean isImageFitToScreen;
//
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isImageFitToScreen) {
//                    isImageFitToScreen = false;
//                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//                    imageView.setAdjustViewBounds(true);
//                } else {
//                    isImageFitToScreen = true;
//                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                }
//            }
//        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Log.e( "inside onactivtyresult" , "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        final StorageReference storageRef = storage.getReferenceFromUrl( URL_STORAGE_REFERENCE).child( FOLDER_STORAGE_IMG);
        final StorageReference storageRef2 = storage.getReferenceFromUrl( URL_STORAGE_REFERENCE).child( FOLDER_STORAGE_FILE);

        if (requestCode == 11) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    final Uri uri = data.getData();
                    Log.e( "" , "Uri: " + uri.toString());


                    Map<String, String> map = new HashMap<>();
//                    map.put("timestamp",  String.valueOf((getDateTimeCurrent()))  );
//
////                    Toast.makeText( getApplicationContext() , (int) getDateTimeCurrent(), Toast.LENGTH_LONG );
//
//                    map.put("message", "" );
//                    map.put("user", UserDetails.username);


                    StorageReference storageReference =
//                                        storageRef.child( uri.getLastPathSegment() );

                                        storageRef
//                                                .child(key)
                                                .child( uri.getLastPathSegment() );


//                    reference1.push().setValue(map);
                    putImageInStorage( storageReference , uri , "" );
//                    reference1.push()
//                            .setValue(map
//                            , new DatabaseReference.CompletionListener() {
//                        @Override
//                        public void onComplete(DatabaseError databaseError,
//                                               DatabaseReference databaseReference) {
//                            if (databaseError == null) {
//
//                                String key = databaseReference.getKey();
//
//                                Log.e( "inside completion" , key );
//
//                                StorageReference storageReference =
////                                        storageRef.child( uri.getLastPathSegment() );
//
//                                        FirebaseStorage.getInstance()
//
////                                                .getReferenceFromUrl()
//
//                                                .getReference( "abc"  )
//                                                .child(key)
//                                                .child(uri.getLastPathSegment())
//
//                                        ;
//
//                                putImageInStorage(storageReference, uri, key);
//
//                            } else {
//                                Log.e( "" , "Unable to write message to database.",
//                                        databaseError.toException());
//                            }
//                        }
//                    });
//                    reference2.push().setValue(map );



//                    ActivityChat tempMessage = new ActivityChat(null, mUsername, mPhotoUrl,
//                            LOADING_IMAGE_URL);
//                    mFirebaseDatabaseReference.child(MESSAGES_CHILD).push()
//                            .setValue(tempMessage, new DatabaseReference.CompletionListener() {
//                                @Override
//                                public void onComplete(DatabaseError databaseError,
//                                                       DatabaseReference databaseReference) {
//                                    if (databaseError == null) {
//                                        String key = databaseReference.getKey();
//                                        StorageReference storageReference =
//                                                FirebaseStorage.getInstance()
//                                                        .getReference(mFirebaseUser.getUid())
//                                                        .child(key)
//                                                        .child(uri.getLastPathSegment());
//
//                                        putImageInStorage(storageReference, uri, key);
//                                    } else {
//                                        Log.w(TAG, "Unable to write message to database.",
//                                                databaseError.toException());
//                                    }
//                                }
//                            });


                }
            }
        }

        if (requestCode == 111) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    final Uri uri = data.getData();
                    Log.e("", "Uri: " + uri.toString());

                    StorageReference storageReference =
//                                        storageRef.child( uri.getLastPathSegment() );

                            storageRef2
//                                                .child(key)
                                    .child(uri.getLastPathSegment());


//                    reference1.push().setValue(map);
                    putFileInStorage(storageReference, uri, "");

                }
            }
        }


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

//        if (mReturningWithResult) {
//            // Commit your transactions here.
//        }
//        // Reset the boolean flag back to false for next time.
//        mReturningWithResult = false;


    }


    private void putImageInStorage(StorageReference storageReference, Uri uri, final String key) {
        storageReference.putFile(uri).addOnCompleteListener( ActivityChat .this,
                new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {

                            String imageurl = task.getResult().getMetadata().getDownloadUrl().toString();

//                            FriendlyMessage friendlyMessage = new FriendlyMessage(null, mUsername, mPhotoUrl, imageurl );

//                            mFirebaseDatabaseReference.child(MESSAGES_CHILD).child(key).setValue(friendlyMessage);


                            Map<String, String> map = new HashMap<>();
                            map.put("timestamp",  String.valueOf((getDateTimeCurrent()))  );
                            map.put("message", "" );
                            map.put("user", UserDetails.username);
                            map.put( "link" , imageurl );

                            reference1.push().setValue(map);
                            reference2.push().setValue(map);


                        } else {
                            Log.e( "" , "Image upload task was not successful.",
                                    task.getException());
                        }
                    }
                });
    }

    private void putFileInStorage(StorageReference storageReference, Uri uri, final String key) {
        storageReference.putFile(uri).addOnCompleteListener( ActivityChat .this,
                new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {

                            String imageurl = task.getResult().getMetadata().getDownloadUrl().toString();

//                            FriendlyMessage friendlyMessage = new FriendlyMessage(null, mUsername, mPhotoUrl, imageurl );

//                            mFirebaseDatabaseReference.child(MESSAGES_CHILD).child(key).setValue(friendlyMessage);


                            Map<String, String> map = new HashMap<>();
                            map.put("timestamp",  String.valueOf((getDateTimeCurrent()))  );
                            map.put("message", "" );
                            map.put("user", UserDetails.username);
                            map.put( "file_link" , imageurl );

                            reference1.push().setValue(map);
                            reference2.push().setValue(map);


                        } else {
                            Log.e( "" , "Image upload task was not successful.",
                                    task.getException());
                        }
                    }
                });
    }




}
