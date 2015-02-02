package com.kabuko.tooltipdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.kabuko.tooltip.Tooltip;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends Activity {
    @InjectView(R.id.button_upper_left) Button mButtonUpperLeft;
    @InjectView(R.id.button_upper_center) Button mButtonUpperCenter;
    @InjectView(R.id.button_upper_right) Button mButtonUpperRight;
    @InjectView(R.id.button_lower_left) Button mButtonLowerLeft;
    @InjectView(R.id.button_lower_center) Button mButtonLowerCenter;
    @InjectView(R.id.button_lower_right) Button mButtonLowerRight;
    @InjectView(R.id.button_center_left_top) Button mButtonCenterLeftTop;
    @InjectView(R.id.button_center_left_center) Button mButtonCenterLeftCenter;
    @InjectView(R.id.button_center_left_bottom) Button mButtonCenterLeftBottom;
    @InjectView(R.id.button_center_right_top) Button mButtonCenterRightTop;
    @InjectView(R.id.button_center_right_center) Button mButtonCenterRightCenter;
    @InjectView(R.id.button_center_right_bottom) Button mButtonCenterRightBottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        final String text = "This is a test of a longer tooltip";

        mButtonUpperLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tooltip tooltip = new Tooltip();
                tooltip.setText(text);
                tooltip.setGravity(Gravity.LEFT);
                tooltip.setTooltipDirection(Tooltip.Direction.Below);
                tooltip.show(mButtonUpperLeft);
            }
        });

        mButtonUpperCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tooltip tooltip = new Tooltip();
                tooltip.setText(text);
                tooltip.setGravity(Gravity.CENTER);
                tooltip.setTooltipDirection(Tooltip.Direction.Below);
                tooltip.show(mButtonUpperCenter);
            }
        });

        mButtonUpperRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tooltip tooltip = new Tooltip();
                tooltip.setText(text);
                tooltip.setGravity(Gravity.RIGHT);
                tooltip.setTooltipDirection(Tooltip.Direction.Below);
                tooltip.show(mButtonUpperRight);
            }
        });

        mButtonLowerLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tooltip tooltip = new Tooltip();
                tooltip.setText(text);
                tooltip.setGravity(Gravity.LEFT);
                tooltip.setTooltipDirection(Tooltip.Direction.Above);
                tooltip.show(mButtonLowerLeft);
            }
        });

        mButtonLowerCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tooltip tooltip = new Tooltip();
                tooltip.setText(text);
                tooltip.setGravity(Gravity.CENTER);
                tooltip.setTooltipDirection(Tooltip.Direction.Above);
                tooltip.show(mButtonLowerCenter);
            }
        });

        mButtonLowerRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tooltip tooltip = new Tooltip();
                tooltip.setText(text);
                tooltip.setGravity(Gravity.RIGHT);
                tooltip.setTooltipDirection(Tooltip.Direction.Above);
                tooltip.show(mButtonLowerRight);
            }
        });

        mButtonCenterLeftTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tooltip tooltip = new Tooltip();
                tooltip.setText(text);
                tooltip.setGravity(Gravity.TOP);
                tooltip.setTooltipDirection(Tooltip.Direction.ToRight);
                tooltip.show(v);
            }
        });

        mButtonCenterLeftCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tooltip tooltip = new Tooltip();
                tooltip.setText(text);
                tooltip.setGravity(Gravity.CENTER_VERTICAL);
                tooltip.setTooltipDirection(Tooltip.Direction.ToRight);
                tooltip.show(v);
            }
        });

        mButtonCenterLeftBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tooltip tooltip = new Tooltip();
                tooltip.setText(text);
                tooltip.setGravity(Gravity.BOTTOM);
                tooltip.setTooltipDirection(Tooltip.Direction.ToRight);
                tooltip.show(v);
            }
        });

        mButtonCenterRightTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tooltip tooltip = new Tooltip();
                tooltip.setText(text);
                tooltip.setGravity(Gravity.TOP);
                tooltip.setTooltipDirection(Tooltip.Direction.ToLeft);
                tooltip.show(v);
            }
        });

        mButtonCenterRightCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tooltip tooltip = new Tooltip();
                tooltip.setText(text);
                tooltip.setGravity(Gravity.CENTER_VERTICAL);
                tooltip.setTooltipDirection(Tooltip.Direction.ToLeft);
                tooltip.show(v);
            }
        });

        mButtonCenterRightBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tooltip tooltip = new Tooltip();
                tooltip.setText(text);
                tooltip.setGravity(Gravity.BOTTOM);
                tooltip.setTooltipDirection(Tooltip.Direction.ToLeft);
                tooltip.show(v);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
