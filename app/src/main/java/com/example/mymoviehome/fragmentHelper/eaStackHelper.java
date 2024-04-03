package com.example.mymoviehome.fragmentHelper;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mymoviehome.R;
import com.example.mymoviehome.activities.tvshow_activity;
import com.example.mymoviehome.contactFragment;
import com.example.mymoviehome.fragments.homePageFragment;
import com.example.mymoviehome.fragments.moviesFragment;

import java.util.Stack;

public class eaStackHelper {
    public static Activity activity;
    private static eaStackHelper instance;
    /**
     * Fragments are init to here as list... must add!!!
     */
    public static final Fragment homepage=new homePageFragment();
    public static final Fragment movies = new moviesFragment();
    public static final Fragment favourites = new tvshow_activity();
    public static final Fragment contact = new contactFragment();


    public static final Stack<eaFragmentModel> eaStack=new Stack<>();
    public static final Stack<eaFragmentModel> volatileStack=new Stack<>();

    //containers must be listed here.
    public enum Containers{
        homepage,movies,favourites,contact            //sample
    }

    public eaStackHelper(Activity activity) {
        this.activity = activity;
    }

    public static eaStackHelper getInstance() {
        return instance;
    }

    public static void init(Activity context) {
        instance=null;
        if (instance == null) instance = new eaStackHelper(context);
    }

    public static void killInstance()
    {
        instance=null;
    }

    /**
     * Init stack and fragments run up.
     */
    public static void clearStacks()
    {
        eaStack.clear();
        volatileStack.clear();
    }

    public void addToStack(eaFragmentModel fragmentModel)
    {
        eaStack.add(fragmentModel);
    }

    public void removeFromStack(eaFragmentModel fragmentModel)
    {
        eaStack.remove(fragmentModel);
    }

    public FragmentManager fragmentManager()
    {
        return ((FragmentActivity)activity).getSupportFragmentManager();
    }

    //replace fragment
    public void replaceFragment(Fragment replacedFragment)
    {
        fragmentManager().beginTransaction().
                replace(R.id.keepFragment,replacedFragment)
                .detach(replacedFragment).attach(replacedFragment).commit();
    }

    //change fragment states based on detail fragment is opened or not.
    public void hideAndShowFragment(Fragment hiding,Fragment showing)
    {
        fragmentManager().beginTransaction().hide(hiding).show(showing).commit();
    }
    //replace fragment
    public void replaceStackFragment(Fragment replacedFragment)
    {
        fragmentManager().beginTransaction()
                .replace(R.id.keepFragment,replacedFragment)
                .addToBackStack(null).commit();
    }

    public  void removeAndShowFragment(Fragment hiding,Fragment showing)
    {
        fragmentManager().beginTransaction().remove(hiding).show(showing).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
    }


    //add and hide fragment
    public void addAndHideFragment(Fragment adding,Fragment hiding)
    {
        fragmentManager().beginTransaction().add(R.id.keepFragment,adding)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).hide(hiding).commit() ;
    }

    public void addFragment(Fragment fragment)
    {
        fragmentManager().beginTransaction().add(R.id.keepFragment,fragment).commit();
    }

    public void addFragmentBackStack(Fragment fragment)
    {
        fragmentManager().beginTransaction().add(R.id.keepFragment,fragment).addToBackStack(fragment.getTag()).commit();
    }


    public void hideFragment(Fragment fragment)
    {
        fragmentManager().beginTransaction().hide(fragment).commit();
    }


    public void showFragment(Fragment fragment)
    {
        fragmentManager().beginTransaction().show(fragment).commit();
    }

    public void removeFragment(Fragment fragment)
    {
        fragmentManager().beginTransaction().remove(fragment).commit();
    }

    public void showFragmentOnly(Fragment showing)
    {
        fragmentManager().beginTransaction().show(showing).commit();
    }

    /**
     * Perfect fragment discriminator for distinct containers.
     * It holds  fragments which is belong in the container. Thus, you can orient and handle whatever you want to them.
     * @param container
     * @return
     */
    public Stack<eaFragmentModel> returnContainerFragments(Containers container)
    {
        volatileStack.clear();
        Stack<eaFragmentModel> activeStackOfThisContainer=new Stack<>();  //holder object.
        for(int index=0; index<eaStack.size(); index++)
        {
            if(eaStack.get(index).getContainerName().equals(container))
            {
                activeStackOfThisContainer.add(eaStack.get(index));  //discriminate fragments.
            }

            if(index==eaStack.size()-1)
            {
                volatileStack.addAll(activeStackOfThisContainer);
                showBase(activeStackOfThisContainer);
            }
        }
        return volatileStack;  //return volatile stack.
    }

    //it shows up the base fragment for the container at first.
    public void showBase(Stack<eaFragmentModel> containerStack)
    {
        for(int index=0; index<containerStack.size(); index++)
        {
            if(containerStack.get(index).getBase())
            {
                addFragment(containerStack.get(index).getFragment());
                break;
            }
        }
    }
}
