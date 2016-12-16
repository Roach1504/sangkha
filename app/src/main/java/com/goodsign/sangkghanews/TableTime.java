package com.goodsign.sangkghanews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Машка on 14.12.2016.
 */

public class TableTime extends Fragment {

    final static String KEY_MSG = "FRAGMENT1_MSG";
    TextView textMsg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_tabletime, container,false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String msg = bundle.getString(KEY_MSG);
            if (msg != null) {
                textMsg.setText(msg);
            }
        }
        return view;
    }
    public void setMsg(String msg) {
        textMsg.setText(msg);
    }
}
