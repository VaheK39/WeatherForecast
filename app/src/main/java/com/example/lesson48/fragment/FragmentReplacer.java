package com.example.lesson48.fragment;

import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public class FragmentReplacer {
    public static final int NO_TRANSITION = 0;

    public static void replace(FragmentActivity activity, int containerId, Fragment fragment, String tag,
                               boolean addToBackStack, String backStackTag,
                               int enterTransition, int exitTransition, int popEnterTransition, int popExitTransition) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction()
                .replace(containerId, fragment, tag)
                .setCustomAnimations(enterTransition, exitTransition, popEnterTransition, popExitTransition);
        if (addToBackStack) {
            transaction.addToBackStack(backStackTag);
        }
        transaction.commit();
    }

    public static void replace(FragmentActivity activity, int containerId, Fragment fragment, String tag,
                               boolean addToBackStack, String backStackTag) {
        replace(activity, containerId, fragment, tag, addToBackStack, backStackTag,
                NO_TRANSITION, NO_TRANSITION, NO_TRANSITION, NO_TRANSITION);
    }

    public static void replace(FragmentActivity activity, int containerId, Fragment fragment, String tag) {
        replace(activity, containerId, fragment, tag, false, null);
    }
}
