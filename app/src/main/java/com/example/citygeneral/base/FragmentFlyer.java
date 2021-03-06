package com.example.citygeneral.base;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 张志远 on 2017/5/8.
 */

public class FragmentFlyer {

    private static FragmentFlyer fragmentFlyer;
    private FragmentManager fragmentManager;

    private Fragment lastFragment;
    private FragmentTransaction transaction;
    private String fragmentName;
    private boolean isAddToBackStack=false;

    private int layoutId;

    private FragmentFlyer(AppCompatActivity activity){
        fragmentManager = activity.getSupportFragmentManager();
    }
    private FragmentFlyer(Fragment fragment){
       fragmentManager = fragment.getFragmentManager();
    }

    public static FragmentFlyer getInstance(AppCompatActivity activity){
        if (fragmentFlyer==null){
            fragmentFlyer=new FragmentFlyer(activity);
        }
        return fragmentFlyer;
    }
    public static FragmentFlyer getInstance(Fragment fragment){
        if (fragmentFlyer==null){
            fragmentFlyer=new FragmentFlyer(fragment);
        }
        return fragmentFlyer;
    }

    public FragmentFlyer setLayoutId(int layoutId){
        this.layoutId=layoutId;
        return this;
    }

    public FragmentFlyer isAddToBackStack(boolean isAddToBackStack){
        this.isAddToBackStack=isAddToBackStack;

        return this;
    }

    public FragmentFlyer startFragment(Class<? extends Fragment> fragmentClass){

        fragmentName = fragmentClass.getSimpleName();

        Fragment fragment = fragmentManager.findFragmentByTag(fragmentName);

        transaction = fragmentManager.beginTransaction();
        if (lastFragment!=null){
            transaction.hide(lastFragment);
        }

        if (fragment==null){

            try {
                fragment=fragmentClass.newInstance();

                transaction.add(layoutId,fragment, fragmentName);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        transaction.show(fragment);

        if (isAddToBackStack) {

            transaction.addToBackStack(fragmentName);

            isAddToBackStack=false;
        }
        lastFragment=fragment;

        transaction.commit();
        return this;
    }

    public Fragment build(){
        return lastFragment;
    }


}
