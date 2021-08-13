package com.redbox.medicare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCCustomerInfoInitializer;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCProductInitializer;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCommerzInitialization;
import com.sslwireless.sslcommerzlibrary.model.response.SSLCTransactionInfoModel;
import com.sslwireless.sslcommerzlibrary.model.util.SSLCCurrencyType;
import com.sslwireless.sslcommerzlibrary.model.util.SSLCSdkType;
import com.sslwireless.sslcommerzlibrary.view.singleton.IntegrateSSLCommerz;
import com.sslwireless.sslcommerzlibrary.viewmodel.listener.SSLCTransactionResponseListener;

public class SslCommerzPaymentGateway extends AppCompatActivity implements SSLCTransactionResponseListener {
    private TextView successs,failed,cancel;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssl_commerz_payment_gateway);
        successs = findViewById(R.id.success);
        failed = findViewById(R.id.failed);
        cancel = findViewById(R.id.cancel);

        final SSLCommerzInitialization sslCommerzInitialization = new SSLCommerzInitialization
                ("virtu606bf351a146c","virtu606bf351a146c@ssl", 800,
                        SSLCCurrencyType.BDT,"123456789098765", "yourProductType", SSLCSdkType.TESTBOX);


        final SSLCCustomerInfoInitializer customerInfoInitializer = new SSLCCustomerInfoInitializer
                ("customer name", "customer email", "address", "dhaka",
                        "1214", "Bangladesh", "018102545654");

        final SSLCProductInitializer productInitializer = new SSLCProductInitializer ("food", "food",
                new SSLCProductInitializer.ProductProfile.TravelVertical("Travel", "10", "A",
                        "12", "Dhaka"));


        IntegrateSSLCommerz
                .getInstance(SslCommerzPaymentGateway.this)
                .addSSLCommerzInitialization(sslCommerzInitialization)
                .addCustomerInfoInitializer(customerInfoInitializer)
                .addProductInitializer(productInitializer)
                .buildApiCall(this);

    }

    @Override
    public void transactionSuccess(SSLCTransactionInfoModel sslcTransactionInfoModel) {

        successs.setText(sslcTransactionInfoModel.getAPIConnect() + "-" + sslcTransactionInfoModel.getStatus());
    }


    @Override
    public void transactionFail(String s) {
        failed.setText(s);


    }

    @Override
    public void merchantValidationError(String s) {
        cancel.setText(s);

    }

}