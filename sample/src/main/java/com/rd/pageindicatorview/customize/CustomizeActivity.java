package com.rd.pageindicatorview.customize;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.*;
import com.rd.pageindicatorview.base.BaseActivity;
import com.rd.pageindicatorview.data.Customization;
import com.rd.pageindicatorview.data.CustomizationConverter;
import com.rd.pageindicatorview.sample.R;

public class CustomizeActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    public static final String EXTRAS_CUSTOMIZATION = "EXTRAS_CUSTOMIZATION";
    public static final int EXTRAS_CUSTOMIZATION_REQUEST_CODE = 1000;

    private Customization customization;

    public static void start(@NonNull Activity activity, @NonNull Customization customization) {
        Intent intent = new Intent(activity, CustomizeActivity.class);
        intent.putExtra(EXTRAS_CUSTOMIZATION, customization);
        activity.startActivityForResult(intent, EXTRAS_CUSTOMIZATION_REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_customize);

        initToolbar();
        displayBackButton(true);
        initData();
        initViews();
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra(EXTRAS_CUSTOMIZATION, customization);
        setResult(Activity.RESULT_OK, intent);

        super.finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinnerAnimationType) {
            customization.setAnimationType(CustomizationConverter.getAnimationType(position));
        } else if (parent.getId() == R.id.spinnerOrientation) {
            customization.setOrientation(CustomizationConverter.getOrientation(position));
        } else if (parent.getId() == R.id.spinnerRtl) {
            customization.setRtlMode(CustomizationConverter.getRtlMode(position));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {/*empty*/}

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        if (id == R.id.switchInteractiveAnimation) {
            customization.setInteractiveAnimation(isChecked);
        } else if (id == R.id.switchAutoVisibility) {
            customization.setAutoVisibility(isChecked);
        } else if (id == R.id.switchFadeOnIdle) {
            customization.setFadeOnIdle(isChecked);
        }
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            customization = intent.getParcelableExtra(EXTRAS_CUSTOMIZATION);
        } else {
            customization = new Customization();
        }
    }

    private void initViews() {
        Spinner spinnerAnimationType = findViewById(R.id.spinnerAnimationType);
        setSpinnerAdapter(spinnerAnimationType, R.array.animation_type);
        spinnerAnimationType.setOnItemSelectedListener(this);
        spinnerAnimationType.setSelection(customization.getAnimationType().ordinal());

        Spinner spinnerOrientation = findViewById(R.id.spinnerOrientation);
        setSpinnerAdapter(spinnerOrientation, R.array.orientation);
        spinnerOrientation.setOnItemSelectedListener(this);
        spinnerOrientation.setSelection(customization.getOrientation().ordinal());

        Spinner spinnerRtl = findViewById(R.id.spinnerRtl);
        setSpinnerAdapter(spinnerRtl, R.array.rtl);
        spinnerRtl.setOnItemSelectedListener(this);
        spinnerRtl.setSelection(customization.getRtlMode().ordinal());

        Switch switchInteractiveAnimation = findViewById(R.id.switchInteractiveAnimation);
        switchInteractiveAnimation.setOnCheckedChangeListener(this);
        switchInteractiveAnimation.setChecked(customization.isInteractiveAnimation());

        Switch switchAutoVisibility = findViewById(R.id.switchAutoVisibility);
        switchAutoVisibility.setOnCheckedChangeListener(this);
        switchAutoVisibility.setChecked(customization.isAutoVisibility());

        Switch switchFadeOnIdle = findViewById(R.id.switchFadeOnIdle);
        switchFadeOnIdle.setOnCheckedChangeListener(this);
        switchFadeOnIdle.setChecked(customization.isFadeOnIdle());

    }

    private void setSpinnerAdapter(@Nullable Spinner spinner, int textArrayId) {
        if (spinner != null) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, textArrayId, R.layout.item_spinner_selected);
            adapter.setDropDownViewResource(R.layout.item_spinner_drop_down);
            spinner.setAdapter(adapter);
            spinner.getBackground().setColorFilter(getResources().getColor(R.color.gray_500), PorterDuff.Mode.SRC_ATOP);
        }
    }
}
