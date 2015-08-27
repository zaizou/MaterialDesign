package jiltarjih.materialdesign;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavDrawerFrag extends android.support.v4.app.Fragment {


    public static final String PREF_FILE_NAME="drawerPref";

    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private boolean mUserLearnedDrawer;
    private String KeymUserLearnedDrawer="userLD";
    private boolean mFromSaveInstanceState;
    private View FragDrawer;

    public NavDrawerFrag() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mUserLearnedDrawer=Boolean.valueOf(readFromPreferences(this.getActivity(),this.KeymUserLearnedDrawer,"false"));
        if(savedInstanceState!=null)
            this.mFromSaveInstanceState=true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nav_drawer, container, false);
    }


    public void setUp(int navFragId,DrawerLayout drawerLayout,Toolbar toolbar) {
        this.FragDrawer=this.getActivity().findViewById(navFragId);
        this.drawerLayout=drawerLayout;
        this.drawerToggle=new ActionBarDrawerToggle(this.getActivity(),this.drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(),KeymUserLearnedDrawer,  mUserLearnedDrawer+"");
                }
                getActivity().invalidateOptionsMenu();

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };

        if(!mUserLearnedDrawer && !mFromSaveInstanceState)
            this.drawerLayout.openDrawer(this.FragDrawer);

        this.drawerLayout.setDrawerListener(this.drawerToggle);
        this.drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                drawerToggle.syncState();
            }
        });


    }


    public static void saveToPreferences(Context contex,String prefName,String PrefValue){
        SharedPreferences sharedPreferences=contex.getSharedPreferences(PREF_FILE_NAME,contex.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(prefName,PrefValue);
        editor.apply();


    }public static String readFromPreferences(Context contex,String prefName,String defaulValue){
        SharedPreferences sharedPreferences=contex.getSharedPreferences(PREF_FILE_NAME,contex.MODE_PRIVATE);
        return sharedPreferences.getString(prefName,defaulValue);
    }



}
