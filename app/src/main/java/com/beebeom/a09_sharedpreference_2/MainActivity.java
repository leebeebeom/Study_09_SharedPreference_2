package com.beebeom.a09_sharedpreference_2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1000;
    private static final String TAG = "asd";
    private SharedPreferences mPreferences;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.shortcut_image);

        //저장된 값 불러오기
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (mPreferences != null) {
            String packageName = mPreferences.getString("name", null);
            PackageManager packageManager = getPackageManager();
            try {
                //아이콘 불러오기
                Drawable icon = packageManager.getApplicationIcon(packageName);
                mImageView.setImageDrawable(icon);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        //바로가기 추가버튼 클릭시 앱리스트로 이동
        findViewById(R.id.btn_add_shortcut).setOnClickListener(v -> {
            //앱 데이터를 다시 받아올거기 때문에 forResult 로 해줌
            startActivityForResult(new Intent(this, AppListActivity.class), REQUEST_CODE);
        });
        //아이콘 이미지 클릭시 실행
        mImageView.setOnClickListener(v -> {
            //패키지 네임 받아오기
            mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String packageName = mPreferences.getString("name", null);
            PackageManager packageManager = getPackageManager();
            Intent intent = packageManager.getLaunchIntentForPackage(packageName);
            startActivity(intent);
        });
    }

    //결과값 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            //보낼때 parcelable 데이터였기 때문에 parcelable 로 받아줌
            ApplicationInfo info = data.getParcelableExtra("info");
            //앱 실행시 패키지 네임이 필요하기 떄문에
            //loadLabel 이 아닌 packageName 으로 받아옴.
//            String name1 = info.packageName;
            //->com.albamon.app
//            String name2 = (String) info.loadLabel(getPackageManager());
            //->알바몬
            Drawable icon = info.loadIcon(getPackageManager());
            mImageView.setImageDrawable(icon);
            //프리퍼런스에 저장
            SharedPreferences.Editor editor = mPreferences.edit();
            //아이콘은 패키지 네임에서 다시 불러올 수 있어서 저장 안해도 됨.
            editor.putString("name", info.packageName);
            editor.apply();
        }
    }
}
