package com.example.leon.login;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
/**
 * Created by jiahan on 10/30/17.
 */
public class ActivityUsers extends OperationActivity {
    ListView usersList;
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    ProgressDialog pd;
    private List<String> arrayList;
    private List<Double> arrayListLa;
    private List<Double> arrayListLo;
    private List<String> arrayListNa;
    private List<String> arrayListDe;
    private List<String> arrayListAd;
    private List<String> arrayListFN;
    private List<String> arrayListLN;
    private List<String> arrayListEM;
    public static final String DATA_URL = "http://proj-309-sd-b-5.cs.iastate.edu/list_store_directory.php?company_id=5&location_id=1";
    //    public static final String KEY_LA = "LATITUDE";
//    public static final String KEY_LO = "LONGITUDE";
//    public static final String KEY_NAME = "NAME";
//    public static final String KEY_DESCRIPTION = "DESCRIPTION";
    public static final String EMAIL = "email";
    public static final String FIRSTNAME = "first_name";
    public static final String LASTNAME = "last_name";
    public static final String JSON_ARRAY = "result";
    //    public static final String KEY_ADDRESS = "ADDRESS";
    public int rownum;
    //    ActivityUsersUsers activityUsersUsers;
//    private final String DATA_URLNAME = "http://proj-309-sd-b-5.cs.iastate.edu/database/list_store_directory.php?company_id=5&location_id=1";
    private final String DATA_URLNAME = "http://proj-309-sd-b-5.cs.iastate.edu/database/list_all_users_new.php?company_id=" + LoginActivity.companyID
//        + "&location_id=" + OperationActivity.locationID
            ;
    //    private final String DATA_URLNAME = "http://proj-309-sd-b-5.cs.iastate.edu/database/list_store_directory.php?company_id=" + "" + "&location_id=" + "";
    static String parsedResult = "";
    private ArrayList<ActivityChatInformation> productList;
    //
    public String DATA_EMAIL = "abc@gmail.com"; // user's email
    //    public String DATA_EMAIL = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        usersList = (ListView)findViewById(R.id.usersList);
//        noUsersText = (TextView)findViewById(R.id.noUsersText);
        pd = new ProgressDialog(ActivityUsers.this);
        pd.setMessage("Loading...");
        pd.show();
        arrayListFN = new ArrayList<>();
        arrayListLN = new ArrayList<>();
        arrayListEM = new ArrayList<>();
        navDrawer();
        setImage();
        //
        UserDetails.username =
//                "abc@gmail.com"
//                .split("\\.")[0]
                FirebaseAuth.getInstance().getCurrentUser().getEmail()
                        .split("\\.")[0]
        ;
        Log.e("company id, location id", LoginActivity.companyID + " " + LoginActivity.locationID + " " +
                FirebaseAuth.getInstance().getCurrentUser().getEmail().split("\\.")[0]
        );
//        String url = "https://fir-collegeproject.firebaseio.com/users.json";
//        String url = "https://fir-collegeproject.firebaseio.com/userss.json";
        String url = "https://logbin-26885.firebaseio.com/users.json";
//        String url = "https://logbin-26885.firebaseio.com/userss.json";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(ActivityUsers.this);
        rQueue.add(request);
        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                UserDetails.chatWith = al.get(position)
//                        .split("\\." )[0];
                String allstring = al.get(position);
                String chatwithwith = allstring.substring( allstring.indexOf("(") + 1 );
                chatwithwith = chatwithwith.substring( 0, chatwithwith.indexOf(".") );
                UserDetails.chatWith = chatwithwith;
                Log.e( "chatwith" , "(" + UserDetails.chatWith + ")" );
//                 =  UserDetails.chatWith;
                // between characters
//                line = line.substring(line.indexOf(",") + 1);
//                line = line.substring(0, line.indexOf(":"));
//                String line = UserDetails.chatWith.substring(UserDetails.chatWith.indexOf("(") + 1);
//                line = line.substring(0, line.indexOf("."));
//                UserDetails.chatWith = line;
                startActivity(new Intent(ActivityUsers.this, ActivityChat.class));
            }
        });
        //
//        activityUsersUsers = new ActivityUsersUsers();
//        activityUsersUsers.getDataName();
        getDataName();
    }
    public void doOnSuccess(String s){
        try {
            JSONObject obj = new JSONObject(s);
            Iterator i = obj.keys();
            String key = "";
            while(i.hasNext()){
                key = i.next().toString();
                if(!key.equals(UserDetails.username)) {
//                    al.add(key);
                }
                totalUsers++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(totalUsers <=1){
//            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        }
        else{
//            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
//            usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));
        }
        pd.dismiss();
    }
    //
//
//    private void showJSON(String response){
//
////        makeText( "begin json "  + response );
//
//        double la = 0.0;
//        double lo = 0.0;
//        String name = "";
//        String desc = "";
//        String addr = "";
//
//
//        String email = "";
//        String firstname = "";
//        String lastname = "";
//
//        try {
//
////            makeText( "begin try "  + response );
//
//            JSONObject jsonObject = new JSONObject(response);
//            JSONArray result = jsonObject.getJSONArray(     JSON_ARRAY);
//
////            Toast.makeText( MapsActivity.this, collegeData.toString() , Toast.LENGTH_LONG).show();
//
////            textView.setText( collegeData.toString() );
//
////            arrayList.add( "begin" ) ;
//
////            makeText( "begin json "  + response + arrayList.toString() );
//
//            rownum =  result.length();
//
//            for(int x=0;x<result.length();x++) {
//
//                JSONObject collegeData = result.getJSONObject(x);
//
//                makeText( collegeData.toString() );
//
////                la = collegeData.getDouble(KEY_LA);
////                lo = collegeData.getDouble(KEY_LO);
////                name = collegeData.getString(KEY_NAME);
////                desc = collegeData.getString(KEY_DESCRIPTION);
//
////                addr = collegeData.getString(KEY_ADDRESS);
//
//                firstname = collegeData.getString(FIRSTNAME);
//                lastname = collegeData.getString(LASTNAME);
//                email = collegeData.getString(EMAIL);
//
//
//
//
////                arrayList.add( "la: " + la + ", lo: " + lo + ", name: " + name + ", desc: " + desc + ", addr: " + addr ) ;
//
//                arrayList.add( "fn: " + firstname + ", ln: " + lastname + ", email: " + email ) ;
//
//
////                hashmap = new HashMap( result.length() );
////                for(int i=1; i<=result.length(); ++i){
//
////                    hashmap.put( "LA" ,  la );
////                    hashmap.put( "LO" , lo );
////                    hashmap.put( "NA" ,  name );
////                    hashmap.put( "DE" ,  desc );
//
////                arrayListLa.add( la );
////                arrayListLo.add( lo );
////                arrayListNa.add( name );
////                arrayListDe.add( desc );
////                arrayListAd.add( addr );
//
//                arrayListFN.add( firstname );
//                arrayListLN.add( lastname );
//                arrayListEM.add( email );
//
////                }
////                arrayList.add(row);
////                Log.e( "hashmap", hashmap.toString()  );
//
//            }
//
////            arrayList.add( "end" ) ;
//            Log.e( "arraylist", arrayList.toString()  );
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
////        textView.setText( "everything->" +  arrayList.toString() );
////        textView.setText( "everything->" +  "la: " + la + ", lo: " + lo + ", name: " + name + ", desc: " + desc );
//
////        Toast.makeText( this,  "" + la + "" + lo + "" + name + "" + desc , Toast.LENGTH_LONG ).show();
//
////        textViewResult.setText("Name:\t"+name+"\nAddress:\t" +address+ "\nVice Chancellor:\t"+ vc);
//
//
//
////        HashMap row = new HashMap( rownum );
//        for(int i=0; i<rownum; i++) {
//
//            LatLng there = new LatLng(  arrayListLa.get(i) ,  arrayListLo.get(i) );
////            mMap.addMarker(new MarkerOptions().position( there )
////                    .title(String.valueOf((arrayListNa.get(i))))
////                    .snippet(String.valueOf(arrayListDe.get(i))) );
//
////            Log.e( "hashmap",  hashmap.get("LA") + ", " +hashmap.get("LO").toString() + ", " + hashmap.get("NA"));
//            Log.e( "arraylist",   arrayListLa.get(i) + ", " + arrayListLo.get(i) + ", " +  arrayListNa.get(i));
//        }
//
//
//
//
//
////        listView.setTextFilterEnabled( true );
//
////        suggestion =  arrayListNa.toArray( new String[arrayListNa.size()]);
////        makeText( suggestion[0] );
////        Log.e("", suggestion.toString() );
//
////        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1
////                , suggestion
////        ) ;
////        listView.setAdapter( adapter );
//
////        listView.setVisibility( View.GONE );
//
//
//
//    }
//
//    public void readMaps(){
//
//        double la = 0.0;
//        double lo = 0.0;
//
////        String url = "http://proj-309-sd-b-5.cs.iastate.edu/database/ReadMaps.php?latitude=?" ;
//
//
//        String all = DATA_URL ;
////                + "\"" ;
//
//
//        //StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>()
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, all , new Response.Listener<String>()
//        {
//            @Override
//            public void onResponse(String response)
//            {
////                textViewResult.setText(response);
////                makeText( "before json" );
//                showJSON( response );
////                textView.setText( "everything->"  +  response );
//
//            }
//        },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error)
//                    {
//                        Toast.makeText(ActivityUsers.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
//                    }
//                });
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//
//
//
//
////        Toast.makeText( "x: " + la + ", y: " + lo);
////        Toast.makeText( stringRequest.toString() );
//
//    }
    public void makeText( String message ){
        Toast.makeText( ActivityUsers.this ,  message  , Toast.LENGTH_LONG).show();
    }
    protected void getDataName()
    {
//        String upcName = editTextUPCName.getText().toString().trim();
//        if (upcName.equals(""))
//        {
//            Toast.makeText(this, "Please enter a Product Name", Toast.LENGTH_LONG).show();
//            return;
//        }
//        String url = DATA_URLNAME + editTextUPCName.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DATA_URLNAME, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.e("aads", response );
                parseText(response);
//                adapter = new RecyclerViewAdapter(productList);
//                recyclerView.setAdapter(adapter);
            }
        },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(ActivityUsers.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private String parseText(String result) {
        Scanner scanner = new Scanner(result);
        productList = new ArrayList<ActivityChatInformation>();
//        String upc = "";
//        String name = "";
//        String quantity = "";
//        String description = "";
//        String binID = "";
//        String manufacturerPrice = "";
//        String msrp = "";
//        String locationID = "";
        String firstname = "";
        String lastname = "";
        String email = "";
        parsedResult = "";
        if (result.charAt(0) == '0') {
            return result;
        } else {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] information;
//                information = line.split("-");
                information = line.split(":");
                // between characters
                line = line.substring(line.indexOf(",") + 1);
                line = line.substring(0, line.indexOf(":"));
                //
//                int iend = line.indexOf(","); //this finds the first occurrence of "."
//                int iend2 = line.indexOf(":"); //this finds the first occurrence of "."
//                String subString1 = line.substring(0, iend); //this will give abc
//                String subString2 = line.substring(0, iend2); //this will give abc
//                String subString3 = line.substring(1, iend2); //this will give abc
                lastname = information[0];
//                Log.e("lastname" , lastname );
                firstname = information[1];
                email = information[2];
//                lastname = subString1;
//                firstname = subString2;
//                email = subString3;
//                upc = information[0];
//                name = information[1];
//                quantity = information[2];
//                description = information[3];
//                binID = information[4];
//                manufacturerPrice = information[5];
//                msrp = information[6];
//                locationID = information[7];
//                Information info = new Information(upc, name, quantity, description, binID, manufacturerPrice, msrp, locationID);
                ActivityChatInformation info = new ActivityChatInformation( firstname, lastname, email);
                productList.add(info);
            }
            for (ActivityChatInformation q : productList) {
                parsedResult += "\n" + q.getLastname() + "\n" + q.getFirstname() + "\n" + q.getEmail() + "\n";
//                parsedResult += "\n" + q.getUpc() + "\n" + q.getName() + "\n" + q.getQuantity() + "\n" + q.getDescription()
//                        + "\n" + q.getBinID() + "\n" + q.getManufacturerPrice()
//                        + "\n" + q.getMsrp() + "\n" + q.getLocationID() + "\n";
                al.add(
//                        "email:" +
                        q.getFirstname() + " " + q.getLastname() + " (" +
                                q.getEmail() + ")"
//                                " (" + q.getLastname() + ")"
//                        + "~lastname: " + q.getLastname()
                );
            }
            //
            usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));
//            Toast.makeText( this, parsedResult , Toast.LENGTH_LONG ).show();
//            return parsedResult;
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
//        al.add(result);
            return result;
        }
    }
    private void setImage() {
        ImageView imgFavorite = findViewById(R.id.info);
        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayInfo();
            }
        });
    }

    private void displayInfo() {
        AlertDialog.Builder adb = new AlertDialog.Builder(ActivityUsers.this, R.style.MyAlertDialogStyle);
        adb.setTitle("User Chat Information");
        adb.setMessage("This is where user can chat with other employees of the company.");
        adb.setNegativeButton("OK", null);
        adb.show();
    }

}
class UserDetails {
    static String username = "";
    static String password = "";
    static String chatWith = "";
}