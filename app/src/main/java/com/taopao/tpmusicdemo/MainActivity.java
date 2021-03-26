package com.taopao.tpmusicdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.taopao.music.MusicPlayerManager;
import com.taopao.tpmusicdemo.databinding.ActivityMainBinding;

import org.apache.commons.lang3.time.DurationFormatUtils;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(mBinding.getRoot());
        initView();
    }






    private void initView() {
        mBinding.btnStart.setOnClickListener(v -> {
            MusicPlayerManager.getInstance().init(this);
        });
    }
}