package com.interest.myapplication.view.save;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by Android on 2016/4/8.
 */
public abstract class SaveBackHandleFragment extends Fragment{
    private SaveBackHanderInterface saveBackHanderInterface;

    public abstract boolean onBackPressed();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity() instanceof SaveBackHanderInterface)) {
          throw new ClassCastException(
                          "Hosting Activity must implement BackHandledInterface");
               } else {
                    this.saveBackHanderInterface= (SaveBackHanderInterface) getActivity();
            }
    }

    @Override
    public void onStart() {
        super.onStart();
        saveBackHanderInterface.setSelectedFragment(this);
    }
}
