package io.bcyl.douyin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import io.bcyl.douyin.Fragment.Add.AddFragment;
import io.bcyl.douyin.Fragment.Home.HomeFragment;
import io.bcyl.douyin.Fragment.User.UserFragment;

public class HomeActivity extends AppCompatActivity {
    private Fragment mFragmentHome, mFragmentAdd, mFragmentUser;
    final int FRAGMENT_HOME = 0, FRAGMENT_ADD = 1, FRAGMENT_USER = 2;
    public static boolean logged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences preferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        logged = preferences.getBoolean("logged", false);
        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        logged = data.getBooleanExtra("logged", false);
    }

    private void initView() {
        BottomNavigationView mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        showFragment(FRAGMENT_HOME);
                        mFragmentHome.onResume();
                        return true;
                    case R.id.menu_add:
                        showFragment(FRAGMENT_ADD);
                        mFragmentHome.onPause();
                        return true;
                    case R.id.menu_user:
                        showFragment(FRAGMENT_USER);
                        mFragmentHome.onPause();
                        return true;
                }
                return false;
            }
        });
        showFragment(FRAGMENT_HOME);
    }

    private void showFragment(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (index) {
            case FRAGMENT_HOME:
                if (mFragmentHome == null) {
                    mFragmentHome = new HomeFragment();
                    transaction.add(R.id.main_container, mFragmentHome);

                } else {
                    transaction.show(mFragmentHome);
                }
                break;
            case FRAGMENT_ADD:
                if (mFragmentAdd == null) {
                    mFragmentAdd = new AddFragment();
                    transaction.add(R.id.main_container, mFragmentAdd);

                } else {
                    transaction.show(mFragmentAdd);
                }
                break;
            case FRAGMENT_USER:
                if (mFragmentUser == null) {
                    mFragmentUser = new UserFragment();
                    transaction.add(R.id.main_container, mFragmentUser);
                } else {
                    transaction.show(mFragmentUser);
                }
                break;
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //隐藏
    private void hideFragment(FragmentTransaction transaction) {
        if (mFragmentHome != null) {
            transaction.hide(mFragmentHome);
        }
        if (mFragmentAdd != null) {
            transaction.hide(mFragmentAdd);
        }
        if (mFragmentUser != null) {
            transaction.hide(mFragmentUser);
        }
    }
}