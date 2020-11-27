package com.beebeom.a09_sharedpreference_2;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AppListActivity extends AppCompatActivity {

    private AppListAdapter mAppListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);

        //폰에 설치된 어플리케이션을 가져오기 위해
        //패키지 매니져객체를 생성해줌.
        PackageManager packageManager = getPackageManager();

        //이 코드로 infos에 설치된 모든 애플리케이션 정보를 담을 수 있음.
        List<ApplicationInfo> infos = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        //어댑터 준비, 데이터 넣고, 연걸
        //아이템 클릭 리스너에서 데이터 뽑아야해서 필드화
        mAppListAdapter = new AppListAdapter();
        mAppListAdapter.setItems(infos);
        ListView listView = findViewById(R.id.app_listView);
        listView.setAdapter(mAppListAdapter);
        //리스트 아이템 클릭 시
        listView.setOnItemClickListener((parent, view, position, id) -> {
            //클릭된 아이템 정보 얻어오기
            ApplicationInfo info = (ApplicationInfo) mAppListAdapter.getItem(position);
            Intent intent = new Intent();
            //ctrl+p 눌러보면 parcelable 데이터로 나옴 걍 담아서 보내도 됨.
            intent.putExtra("info", info);
            setResult(RESULT_OK, intent);
            //이 액티비티 종료
            finish();
        });
    }

}