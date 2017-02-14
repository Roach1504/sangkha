package com.goodsign.sangkghanews.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodsign.sangkghanews.R;
import com.goodsign.sangkghanews.models.ZurkhayModel;

import org.sufficientlysecure.htmltextview.HtmlTextView;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Машка on 22.01.2017.
 */

public class Zurkhay extends Fragment {

    private ZurkhayModel zurkhayModel;
    private TextView title;
    private HtmlTextView description;

    public static Zurkhay newInstance (ZurkhayModel zurkhayModel)
    {
        Zurkhay zurkhay = new Zurkhay();
        Bundle bundle = new Bundle();
        bundle.putSerializable("zurkhayModel", zurkhayModel);
        zurkhay.setArguments(bundle);
        return zurkhay;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zurhai, container, false);
        title = (TextView) view.findViewById(R.id.text_zurkhay_title);
        description = (HtmlTextView) view.findViewById(R.id.text_zurkhay_description);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        zurkhayModel = (ZurkhayModel) getArguments().getSerializable("zurkhayModel");
        if (zurkhayModel.getId() >= 0)
        {
            description.setHtml(zurkhayModel.getText());
        }
        title.setText(zurkhayModel.getTitle());

    }

    public String getDateFromModel()
    {
        if (zurkhayModel != null)
        {
            return zurkhayModel.getFormattedDate();
        }
        else
        {
            return null;
        }
    }
}
