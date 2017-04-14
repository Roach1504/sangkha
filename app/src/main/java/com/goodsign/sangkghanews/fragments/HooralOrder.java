package com.goodsign.sangkghanews.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.goodsign.sangkghanews.R;
import com.yandex.money.api.methods.params.PaymentParams;
import com.yandex.money.api.methods.params.PhoneParams;

import java.math.BigDecimal;

import ru.yandex.money.android.PaymentActivity;

import static android.R.attr.data;
import static android.app.Activity.RESULT_OK;

/**
 * Created by Roman on 19.02.2017.
 */

public class HooralOrder extends Fragment implements BackStackResumedFragment
{
    private static final String CLIENT_ID = "DDA2CC7384C7F658EED735EFAB629B61EBE2B56E21B7A5D8C0A84394E98C2CCF";
    private static final String HOST = "https://money.yandex.ru";
    private static final int REQUEST_CODE = 1;

    private Button button;

    public static HooralOrder newInstance()
    {
        return new HooralOrder();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_hooral, container, false);
        button = (Button) view.findViewById(R.id.button_hooral_payment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                PaymentParams phoneParams = PhoneParams.newInstance("79012345678", new BigDecimal(100.0));
                Intent intent = PaymentActivity.getBuilder(getContext())
                        .setPaymentParams(phoneParams)
                        .setClientId(CLIENT_ID)
                        .setHost(HOST)
                        .build();
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // payment was successful
        }
    }

    @Override
    public void onFragmentResume() {

    }
}
