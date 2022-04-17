package com.example.leon.login;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.os.Bundle;
import android.os.Handler;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.ExpandableBadgeDrawerItem;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Typefaceable;

import junit.framework.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OperationActivity extends AppCompatActivity{

    FirebaseAuth firebaseAuth;
    Drawer result = null;
    boolean doubleBackToExitPressedOnce = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);
        navDrawer();
        setImage();

        MainActivity.company_id = LoginActivity.companyID;
        MainActivity.location_id = LoginActivity.locationID;
        MainActivity.userType = LoginActivity.usertype;

    }


    protected void helpEmail() {
        Log.i("Send email", "");
        String[] TO = {"help&feedback@logbin.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "For help, put help in the subject and" +
                " please provide us details regarding any problems that you are facing. " +
                "Otherwise put Feedback in the subject and share any valuable feedback " +
                "that we can utilize to improve your LogBin experience even further.");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email.", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    protected void navDrawer() {
        //Initialize the firebase auth object required
        //for user authentication.
        firebaseAuth = FirebaseAuth.getInstance();
        //Check if the user is already logged in or not.
        // If not null then (i.e., logged in) then
        if (firebaseAuth.getCurrentUser() == null) {
            //that means user is already logged in
            //so close this activity
            finish();
            //do some activity in the Operation Activity class.
            startActivity(new Intent(this, LoginActivity.class));
        }
        //Get the current user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        //Nav Drawer
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(new ProfileDrawerItem().withName(LoginActivity.Name).withEmail(user.getEmail()).withIcon(R.drawable.profile))
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Home")
                .withTextColor(Color.parseColor("#EAA600"))
                .withIconColor(Color.parseColor("#EAA600"))
                .withIcon(GoogleMaterial.Icon.gmd_home);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("Scan Item")
                .withTextColor(Color.parseColor("#EAA600"))
                .withIconColor(Color.parseColor("#EAA600"))
                .withIcon(FontAwesome.Icon.faw_barcode);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName("Search Item")
                .withTextColor(Color.parseColor("#EAA600"))
                .withIconColor(Color.parseColor("#EAA600"))
                .withIcon(FontAwesome.Icon.faw_search);

        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withName("Chat")
                .withTextColor(Color.parseColor("#EAA600"))
                .withIconColor(Color.parseColor("#EAA600"))
                .withIcon(GoogleMaterial.Icon.gmd_chat);

        PrimaryDrawerItem item5 = new PrimaryDrawerItem().withIdentifier(5).withName("Submit Order")
                .withTextColor(Color.parseColor("#EAA600"))
                .withIconColor(Color.parseColor("#EAA600"))
                .withIcon(GoogleMaterial.Icon.gmd_send);

        PrimaryDrawerItem item7 = new PrimaryDrawerItem().withIdentifier(7).withName("Search Directory")
                .withTextColor(Color.parseColor("#EAA600"))
                .withIconColor(Color.parseColor("#EAA600"))
                .withIcon(FontAwesome.Icon.faw_users);

        PrimaryDrawerItem item8 = new PrimaryDrawerItem().withIdentifier(8).withName("Update Directory")
                .withTextColor(Color.parseColor("#EAA600"))
                .withIconColor(Color.parseColor("#EAA600"))
                .withIcon(FontAwesome.Icon.faw_database);

        PrimaryDrawerItem item12 = new PrimaryDrawerItem().withIdentifier(12).withName("Data Analysis")
                .withTextColor(Color.parseColor("#EAA600"))
                .withIconColor(Color.parseColor("#EAA600"))
                .withIcon(FontAwesome.Icon.faw_bar_chart);

        PrimaryDrawerItem item13 = new PrimaryDrawerItem().withIdentifier(13).withName("Help & Feedback")
                .withTextColor(Color.parseColor("#EAA600"))
                .withIconColor(Color.parseColor("#EAA600"))
                .withIcon(GoogleMaterial.Icon.gmd_feedback);

        PrimaryDrawerItem item14 = new PrimaryDrawerItem().withIdentifier(14).withName("Logout")
                .withTextColor(Color.parseColor("#EAA600"))
                .withIconColor(Color.parseColor("#EAA600"))
                .withIcon(GoogleMaterial.Icon.gmd_account_box);

        if (LoginActivity.usertype.equals("5")) {

            result = new DrawerBuilder().withActivity(this)
                    .withToolbar(toolbar)
                    .withSliderBackgroundColor(Color.parseColor("#1F2D44"))
                    .withAccountHeader(headerResult)
                    .addDrawerItems(
                            item1, item4, new DividerDrawerItem(),
                            item7, item8, new DividerDrawerItem(),
                            new ExpandableDrawerItem().withName("Company Management")
                                    .withTextColor(Color.parseColor("#EAA600"))
                                    .withIconColor(Color.parseColor("#EAA600"))
                                    .withIcon(GoogleMaterial.Icon.gmd_settings)
                                    .withSubItems(
                                            new SecondaryDrawerItem()
                                                    .withTextColor(Color.parseColor("#EAA600"))
                                                    .withIconColor(Color.parseColor("#EAA600"))
                                                    .withIcon(FontAwesome.Icon.faw_user_plus)
                                                    .withLevel(2)
                                                    .withIdentifier(91)
                                                    .withName("Create User"))
                            ,item13, item14)
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            long id = drawerItem.getIdentifier();
                            if (id == 1) {
                                startActivity(new Intent(view.getContext(), OperationActivity.class));
                            } else if (id == 2) {
                                startActivity(new Intent(view.getContext(), ScanActivity.class));
                            } else if (id == 3) {
                                startActivity(new Intent(view.getContext(), SearchProductActivity.class));
                            } else if (id == 4) {
                                startActivity(new Intent(view.getContext(), ActivityUsers.class));
                            } else if (id == 5) {
                                startActivity(new Intent(view.getContext(), OrderRequestAdd.class));
                            } else if (id == 61) {
                                startActivity(new Intent(view.getContext(), SearchOrderActivity.class));
                            } else if (id == 62) {
                                startActivity(new Intent(view.getContext(), CompletedOrder.class));
                            } else if (id == 7) {
                                startActivity(new Intent(view.getContext(), SearchDirectoryActivity.class));
                            } else if (id == 8) {
                                startActivity(new Intent(view.getContext(), DirectoryUpdate.class));
                            } else if (id == 91) {
                                startActivity(new Intent(view.getContext(), UserType.class));
                            } else if (id == 101) {
                                startActivity(new Intent(view.getContext(), StoreAdd.class));
                            } else if (id == 102) {
                                startActivity(new Intent(view.getContext(), StoreUpdate.class));
                            } else if (id == 103) {
                                startActivity(new Intent(view.getContext(), StoreRemove.class));
                            } else if (id == 111) {
                                startActivity(new Intent(view.getContext(), WarehouseAdd.class));
                            } else if (id == 112) {
                                startActivity(new Intent(view.getContext(), WarehouseUpdate.class));
                            }else if (id == 113) {
                                startActivity(new Intent(view.getContext(), WarehouseRemove.class));
                            }
                            else if (id == 12) {
                                //startActivity(new Intent(view.getContext(), TestAddressConvert.class));
                            } else if (id == 13) {
                                helpEmail();
                            } else if (id == 14) {
                                //Log out the user
                                firebaseAuth.signOut();
                                //Close this activity.
                                finish();
                                //Start the login activity
                                startActivity(new Intent(view.getContext(), LoginActivity.class));
                            }
                            return false;
                        }
                    })
                    .build();
            result.getActionBarDrawerToggle().getDrawerArrowDrawable().setColor(Color.parseColor("#EAA600"));

        } else if (LoginActivity.usertype.equals("4")) {
            result = new DrawerBuilder().withActivity(this)
                    .withToolbar(toolbar)
                    .withSliderBackgroundColor(Color.parseColor("#1F2D44"))
                    .withAccountHeader(headerResult)
                    .addDrawerItems(
                            item1, new DividerDrawerItem(), item2, item3, new DividerDrawerItem(), item4, new DividerDrawerItem(), item5,
                            new ExpandableDrawerItem().withName("View orders")
                                    .withTextColor(Color.parseColor("#EAA600"))
                                    .withIconColor(Color.parseColor("#EAA600"))
                                    .withIcon(GoogleMaterial.Icon.gmd_local_grocery_store)
                                    .withSubItems(
                                            new SecondaryDrawerItem()
                                                    .withTextColor(Color.parseColor("#EAA600"))
                                                    .withIconColor(Color.parseColor("#EAA600"))
                                                    .withIcon(FontAwesome.Icon.faw_align_left)
                                                    .withLevel(2)
                                                    .withIdentifier(61)
                                                    .withName("Pending orders"),
                                            new SecondaryDrawerItem()
                                                    .withTextColor(Color.parseColor("#EAA600"))
                                                    .withIconColor(Color.parseColor("#EAA600"))
                                                    .withIcon(FontAwesome.Icon.faw_align_justify)
                                                    .withLevel(2)
                                                    .withIdentifier(62)
                                                    .withName("Completed orders")),

                            new DividerDrawerItem(),
                            item7, item8, new DividerDrawerItem(),
                            new ExpandableDrawerItem().withName("Company Management")
                                    .withTextColor(Color.parseColor("#EAA600"))
                                    .withIconColor(Color.parseColor("#EAA600"))
                                    .withIcon(GoogleMaterial.Icon.gmd_settings)
                                    .withSubItems(
                                            new SecondaryDrawerItem()
                                                    .withTextColor(Color.parseColor("#EAA600"))
                                                    .withIconColor(Color.parseColor("#EAA600"))
                                                    .withIcon(FontAwesome.Icon.faw_user_plus)
                                                    .withLevel(2)
                                                    .withIdentifier(91)
                                                    .withName("Create User")),
                            new DividerDrawerItem(),
                            new ExpandableDrawerItem().withName("Store")
                                    .withTextColor(Color.parseColor("#EAA600"))
                                    .withIconColor(Color.parseColor("#EAA600"))
                                    .withIcon(FontAwesome.Icon.faw_bank)
                                    .withSubItems(
                                            new SecondaryDrawerItem()
                                                    .withTextColor(Color.parseColor("#EAA600"))
                                                    .withIconColor(Color.parseColor("#EAA600"))
                                                    .withIcon(GoogleMaterial.Icon.gmd_add)
                                                    .withLevel(2)
                                                    .withIdentifier(101)
                                                    .withName("Create Store"),
                                            new SecondaryDrawerItem()
                                                    .withTextColor(Color.parseColor("#EAA600"))
                                                    .withIconColor(Color.parseColor("#EAA600"))
                                                    .withIcon(FontAwesome.Icon.faw_refresh)
                                                    .withLevel(2)
                                                    .withIdentifier(102)
                                                    .withName("Update Store"),
                                            new SecondaryDrawerItem()
                                                    .withTextColor(Color.parseColor("#EAA600"))
                                                    .withIconColor(Color.parseColor("#EAA600"))
                                                    .withIcon(GoogleMaterial.Icon.gmd_remove_circle_outline)
                                                    .withLevel(2)
                                                    .withIdentifier(103)
                                                    .withName("Remove Store")),

                            new ExpandableDrawerItem().withName("Warehouse")
                                    .withTextColor(Color.parseColor("#EAA600"))
                                    .withIconColor(Color.parseColor("#EAA600"))
                                    .withIcon(GoogleMaterial.Icon.gmd_store)
                                    .withSubItems(
                                            new SecondaryDrawerItem()
                                                    .withTextColor(Color.parseColor("#EAA600"))
                                                    .withIconColor(Color.parseColor("#EAA600"))
                                                    .withIcon(GoogleMaterial.Icon.gmd_add)
                                                    .withLevel(2)
                                                    .withIdentifier(111)
                                                    .withName("Create Warehouse"),
                                            new SecondaryDrawerItem()
                                                    .withTextColor(Color.parseColor("#EAA600"))
                                                    .withIconColor(Color.parseColor("#EAA600"))
                                                    .withIcon(FontAwesome.Icon.faw_refresh)
                                                    .withLevel(2)
                                                    .withIdentifier(112)
                                                    .withName("Update Warehouse"),
                                            new SecondaryDrawerItem()
                                                    .withTextColor(Color.parseColor("#EAA600"))
                                                    .withIconColor(Color.parseColor("#EAA600"))
                                                    .withIcon(GoogleMaterial.Icon.gmd_remove_circle_outline)
                                                    .withLevel(2)
                                                    .withIdentifier(113)
                                                    .withName("Remove Warehouse"))
                            , item12, new DividerDrawerItem(), item13, item14)
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            long id = drawerItem.getIdentifier();
                            if (id == 1) {
                                startActivity(new Intent(view.getContext(), OperationActivity.class));
                            } else if (id == 2) {
                                startActivity(new Intent(view.getContext(), ScanActivity.class));
                            } else if (id == 3) {
                                startActivity(new Intent(view.getContext(), SearchProductActivity.class));
                            } else if (id == 4) {

                            } else if (id == 5) {
                                startActivity(new Intent(view.getContext(), OrderRequestAdd.class));
                            } else if (id == 61) {
                                startActivity(new Intent(view.getContext(), SearchOrderActivity.class));
                            } else if (id == 62) {
                                startActivity(new Intent(view.getContext(), CompletedOrder.class));
                            } else if (id == 7) {
                                startActivity(new Intent(view.getContext(), SearchDirectoryActivity.class));
                            } else if (id == 8) {
                                startActivity(new Intent(view.getContext(), DirectoryUpdate.class));
                            } else if (id == 91) {
                                startActivity(new Intent(view.getContext(), UserType.class));
                            } else if (id == 101) {
                                startActivity(new Intent(view.getContext(), StoreAdd.class));
                            } else if (id == 102) {
                                startActivity(new Intent(view.getContext(), StoreUpdate.class));
                            } else if (id == 103) {
                                startActivity(new Intent(view.getContext(), StoreRemove.class));
                            } else if (id == 111) {
                                startActivity(new Intent(view.getContext(), WarehouseAdd.class));
                            } else if (id == 112) {
                                startActivity(new Intent(view.getContext(), WarehouseUpdate.class));
                            } else if (id == 113) {
                                startActivity(new Intent(view.getContext(), WarehouseRemove.class));
                            } else if (id == 12) {
                                //startActivity(new Intent(view.getContext(), TestAddressConvert.class));
                            } else if (id == 13) {
                                helpEmail();
                            } else if (id == 14) {
                                //Log out the user
                                firebaseAuth.signOut();
                                //Close this activity.
                                finish();
                                //Start the login activity
                                startActivity(new Intent(view.getContext(), LoginActivity.class));
                            }
                            return false;
                        }
                    })
                    .build();
            result.getActionBarDrawerToggle().getDrawerArrowDrawable().setColor(Color.parseColor("#EAA600"));

        } else if (LoginActivity.usertype.equals("3")) {
            result = new DrawerBuilder().withActivity(this)
                    .withToolbar(toolbar)
                    .withSliderBackgroundColor(Color.parseColor("#1F2D44"))
                    .withAccountHeader(headerResult)
                    .addDrawerItems(
                            item1, new DividerDrawerItem(), item2, item3, item4, new DividerDrawerItem(),
                            new ExpandableDrawerItem().withName("View orders")
                                    .withTextColor(Color.parseColor("#EAA600"))
                                    .withIconColor(Color.parseColor("#EAA600"))
                                    .withIcon(GoogleMaterial.Icon.gmd_local_grocery_store)
                                    .withSubItems(
                                            new SecondaryDrawerItem()
                                                    .withTextColor(Color.parseColor("#EAA600"))
                                                    .withIconColor(Color.parseColor("#EAA600"))
                                                    .withIcon(FontAwesome.Icon.faw_align_left)
                                                    .withLevel(2)
                                                    .withIdentifier(61)
                                                    .withName("Pending orders"),
                                            new SecondaryDrawerItem()
                                                    .withTextColor(Color.parseColor("#EAA600"))
                                                    .withIconColor(Color.parseColor("#EAA600"))
                                                    .withIcon(FontAwesome.Icon.faw_align_justify)
                                                    .withLevel(2)
                                                    .withIdentifier(62)
                                                    .withName("Completed orders")),

                            new DividerDrawerItem(),
                            item7, item8,
                            new DividerDrawerItem(),
                            new ExpandableDrawerItem().withName("Warehouse")
                                    .withTextColor(Color.parseColor("#EAA600"))
                                    .withIconColor(Color.parseColor("#EAA600"))
                                    .withIcon(GoogleMaterial.Icon.gmd_store)
                                    .withSubItems(
                                            new SecondaryDrawerItem()
                                                    .withTextColor(Color.parseColor("#EAA600"))
                                                    .withIconColor(Color.parseColor("#EAA600"))
                                                    .withIcon(GoogleMaterial.Icon.gmd_add)
                                                    .withLevel(2)
                                                    .withIdentifier(111)
                                                    .withName("Create Warehouse"),
                                            new SecondaryDrawerItem()
                                                    .withTextColor(Color.parseColor("#EAA600"))
                                                    .withIconColor(Color.parseColor("#EAA600"))
                                                    .withIcon(FontAwesome.Icon.faw_refresh)
                                                    .withLevel(2)
                                                    .withIdentifier(112)
                                                    .withName("Update Warehouse"),
                                            new SecondaryDrawerItem()
                                                    .withTextColor(Color.parseColor("#EAA600"))
                                                    .withIconColor(Color.parseColor("#EAA600"))
                                                    .withIcon(GoogleMaterial.Icon.gmd_remove_circle_outline)
                                                    .withLevel(2)
                                                    .withIdentifier(113)
                                                    .withName("Remove Warehouse"))
                            , item12, new DividerDrawerItem(), item13, item14)
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            long id = drawerItem.getIdentifier();
                            if (id == 1) {
                                startActivity(new Intent(view.getContext(), OperationActivity.class));
                            } else if (id == 2) {
                                startActivity(new Intent(view.getContext(), ScanActivity.class));
                            } else if (id == 3) {
                                startActivity(new Intent(view.getContext(), SearchProductActivity.class));
                            } else if (id == 4) {

                            } else if (id == 5) {
                                startActivity(new Intent(view.getContext(), OrderRequestAdd.class));
                            } else if (id == 61) {
                                startActivity(new Intent(view.getContext(), SearchOrderActivity.class));
                            } else if (id == 62) {
                                startActivity(new Intent(view.getContext(), CompletedOrder.class));
                            } else if (id == 7) {
                                startActivity(new Intent(view.getContext(), SearchDirectoryActivity.class));
                            } else if (id == 8) {
                                startActivity(new Intent(view.getContext(), DirectoryUpdate.class));
                            } else if (id == 91) {
                                startActivity(new Intent(view.getContext(), UserType.class));
                            } else if (id == 101) {
                                startActivity(new Intent(view.getContext(), StoreAdd.class));
                            } else if (id == 102) {
                                startActivity(new Intent(view.getContext(), StoreUpdate.class));
                            } else if (id == 103) {
                                startActivity(new Intent(view.getContext(), StoreRemove.class));
                            } else if (id == 111) {
                                startActivity(new Intent(view.getContext(), WarehouseAdd.class));
                            } else if (id == 112) {
                                startActivity(new Intent(view.getContext(), WarehouseUpdate.class));
                            } else if (id == 113) {
                                startActivity(new Intent(view.getContext(), WarehouseRemove.class));
                            } else if (id == 12) {
                                //startActivity(new Intent(view.getContext(), TestAddressConvert.class));
                            } else if (id == 13) {
                                helpEmail();
                            } else if (id == 14) {
                                //Log out the user
                                firebaseAuth.signOut();
                                //Close this activity.
                                finish();
                                //Start the login activity
                                startActivity(new Intent(view.getContext(), LoginActivity.class));
                            }
                            return false;
                        }
                    })
                    .build();
            result.getActionBarDrawerToggle().getDrawerArrowDrawable().setColor(Color.parseColor("#EAA600"));

        } else if (LoginActivity.usertype.equals("2")) {

            result = new DrawerBuilder().withActivity(this)
                    .withToolbar(toolbar)
                    .withSliderBackgroundColor(Color.parseColor("#1F2D44"))
                    .withAccountHeader(headerResult)
                    .addDrawerItems(
                            item1, new DividerDrawerItem(), item2, item3, new DividerDrawerItem(), item4, item5,
                            new DividerDrawerItem(), item7, item8, new DividerDrawerItem(),
                            new ExpandableDrawerItem().withName("Store")
                                    .withTextColor(Color.parseColor("#EAA600"))
                                    .withIconColor(Color.parseColor("#EAA600"))
                                    .withIcon(FontAwesome.Icon.faw_bank)
                                    .withSubItems(
                                            new SecondaryDrawerItem()
                                                    .withTextColor(Color.parseColor("#EAA600"))
                                                    .withIconColor(Color.parseColor("#EAA600"))
                                                    .withIcon(GoogleMaterial.Icon.gmd_add)
                                                    .withLevel(2)
                                                    .withIdentifier(101)
                                                    .withName("Create Store"),
                                            new SecondaryDrawerItem()
                                                    .withTextColor(Color.parseColor("#EAA600"))
                                                    .withIconColor(Color.parseColor("#EAA600"))
                                                    .withIcon(FontAwesome.Icon.faw_refresh)
                                                    .withLevel(2)
                                                    .withIdentifier(102)
                                                    .withName("Update Store"),
                                            new SecondaryDrawerItem()
                                                    .withTextColor(Color.parseColor("#EAA600"))
                                                    .withIconColor(Color.parseColor("#EAA600"))
                                                    .withIcon(GoogleMaterial.Icon.gmd_remove_circle_outline)
                                                    .withLevel(2)
                                                    .withIdentifier(103)
                                                    .withName("Remove Store"))
                            , item12, new DividerDrawerItem(), item13, item14)
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            long id = drawerItem.getIdentifier();
                            if (id == 1) {
                                startActivity(new Intent(view.getContext(), OperationActivity.class));
                            } else if (id == 2) {
                                startActivity(new Intent(view.getContext(), ScanActivity.class));
                            } else if (id == 3) {
                                startActivity(new Intent(view.getContext(), SearchProductActivity.class));
                            } else if (id == 4) {

                            } else if (id == 5) {
                                startActivity(new Intent(view.getContext(), OrderRequestAdd.class));
                            } else if (id == 61) {
                                startActivity(new Intent(view.getContext(), SearchOrderActivity.class));
                            } else if (id == 62) {
                                startActivity(new Intent(view.getContext(), CompletedOrder.class));
                            } else if (id == 7) {
                                startActivity(new Intent(view.getContext(), SearchDirectoryActivity.class));
                            } else if (id == 8) {
                                startActivity(new Intent(view.getContext(), DirectoryUpdate.class));
                            } else if (id == 91) {
                                startActivity(new Intent(view.getContext(), UserType.class));
                            } else if (id == 101) {
                                startActivity(new Intent(view.getContext(), StoreAdd.class));
                            } else if (id == 102) {
                                startActivity(new Intent(view.getContext(), StoreUpdate.class));
                            } else if (id == 103) {
                                startActivity(new Intent(view.getContext(), StoreRemove.class));
                            } else if (id == 111) {
                                startActivity(new Intent(view.getContext(), WarehouseAdd.class));
                            } else if (id == 112) {
                                startActivity(new Intent(view.getContext(), WarehouseUpdate.class));
                            } else if (id == 113) {
                                startActivity(new Intent(view.getContext(), WarehouseRemove.class));
                            } else if (id == 12) {
                                //startActivity(new Intent(view.getContext(), TestAddressConvert.class));
                            } else if (id == 13) {
                                helpEmail();
                            } else if (id == 14) {
                                //Log out the user
                                firebaseAuth.signOut();
                                //Close this activity.
                                finish();
                                //Start the login activity
                                startActivity(new Intent(view.getContext(), LoginActivity.class));
                            }
                            return false;
                        }
                    })
                    .build();
            result.getActionBarDrawerToggle().getDrawerArrowDrawable().setColor(Color.parseColor("#EAA600"));

        } else if (LoginActivity.usertype.equals("1")) {
            result = new DrawerBuilder().withActivity(this)
                    .withToolbar(toolbar)
                    .withSliderBackgroundColor(Color.parseColor("#1F2D44"))
                    .withAccountHeader(headerResult)
                    .addDrawerItems(
                            item1, new DividerDrawerItem(), item2, item3, new DividerDrawerItem(), item4,
                            item7, new DividerDrawerItem(), item13, item14)
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            long id = drawerItem.getIdentifier();
                            if (id == 1) {
                                startActivity(new Intent(view.getContext(), OperationActivity.class));
                            } else if (id == 2) {
                                startActivity(new Intent(view.getContext(), ScanActivity.class));
                            } else if (id == 3) {
                                startActivity(new Intent(view.getContext(), SearchProductActivity.class));
                            } else if (id == 4) {

                            } else if (id == 5) {
                                startActivity(new Intent(view.getContext(), OrderRequestAdd.class));
                            } else if (id == 61) {
                                startActivity(new Intent(view.getContext(), SearchOrderActivity.class));
                            } else if (id == 62) {
                                startActivity(new Intent(view.getContext(), CompletedOrder.class));
                            } else if (id == 7) {
                                startActivity(new Intent(view.getContext(), SearchDirectoryActivity.class));
                            } else if (id == 8) {
                                startActivity(new Intent(view.getContext(), DirectoryUpdate.class));
                            } else if (id == 91) {
                                startActivity(new Intent(view.getContext(), UserType.class));
                            } else if (id == 101) {
                                startActivity(new Intent(view.getContext(), StoreAdd.class));
                            } else if (id == 102) {
                                startActivity(new Intent(view.getContext(), StoreUpdate.class));
                            } else if (id == 103) {
                                startActivity(new Intent(view.getContext(), StoreRemove.class));
                            } else if (id == 111) {
                                startActivity(new Intent(view.getContext(), WarehouseAdd.class));
                            } else if (id == 112) {
                                startActivity(new Intent(view.getContext(), WarehouseUpdate.class));
                            } else if (id == 113) {
                                startActivity(new Intent(view.getContext(), WarehouseRemove.class));
                            } else if (id == 12) {
                                //startActivity(new Intent(view.getContext(), TestAddressConvert.class));
                            } else if (id == 13) {
                                helpEmail();
                            } else if (id == 14) {
                                //Log out the user
                                firebaseAuth.signOut();
                                //Close this activity.
                                finish();
                                //Start the login activity
                                startActivity(new Intent(view.getContext(), LoginActivity.class));
                            }
                            return false;
                        }
                    })
                    .build();
            result.getActionBarDrawerToggle().getDrawerArrowDrawable().setColor(Color.parseColor("#EAA600"));
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        //Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
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
        AlertDialog.Builder adb = new AlertDialog.Builder(OperationActivity.this, R.style.MyAlertDialogStyle);
        adb.setTitle("Home Information");
        adb.setMessage("This is your home page. Use the nav drawer to navigate to other pages to perform necessary assigned operations.");
        adb.setNegativeButton("OK", null);
        adb.show();
    }

}