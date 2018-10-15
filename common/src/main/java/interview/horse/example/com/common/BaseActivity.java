package interview.horse.example.com.common;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class BaseActivity extends AppCompatActivity {

    private RecyclerView recycle;
    private List<InfoBean> datas;
    private BaseAdapter adapter;
    private String currentPackageName;
    public static final String PACKAGENAME = "packageName";
    private String splitDot = "\\.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        recycle = findViewById(R.id.recycle);
        initData();
        initAdapter();
    }

    private void initData() {
        Intent intent = getIntent();
        System.out.println(intent.getCategories());
        currentPackageName = intent.getStringExtra(PACKAGENAME);
        datas = new ArrayList<>();
        getPackageData();
    }

    private void initAdapter() {
        recycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new BaseAdapter(this, datas);
        recycle.setAdapter(adapter);
    }

    public void getPackageData() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
            ActivityInfo[] activities = packageInfo.activities;
            if (activities != null) {
                Set<String> set = new TreeSet<>();
                for (ActivityInfo info : activities) {
                    addItem(datas, set, info);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void addItem(List<InfoBean> datas, Set<String> set, ActivityInfo info) {
        String packageName = getPackageName();
        if (!info.name.contains(packageName)) {//如果类名中不包含包名就不是我们要显示的条目
            return;
        }
        InfoBean infoBean = new InfoBean();
        if (TextUtils.isEmpty(currentPackageName)) {
            String newPackageName = info.name.replace(packageName + ".", "");
            String[] split = newPackageName.split(splitDot);
            infoBean.completeClassName = info.name;
            infoBean.isClass = !newPackageName.contains(splitDot);
            infoBean.splitPackageName = split.length > 0 ? split[0] : newPackageName;
        } else {
            String newPackageName = info.name.replace(packageName + ".", "");
            String[] split = newPackageName.split(splitDot);
            infoBean.completeClassName = info.name;
            infoBean.isClass = !newPackageName.contains(splitDot);
            infoBean.splitPackageName = split.length > 0 ? split[1] : newPackageName;
        }
        String[] split = infoBean.splitPackageName.split(splitDot);
        if (!infoBean.isClass && !set.contains(infoBean.splitPackageName)) {
            datas.add(infoBean);
        }
        if (infoBean.isClass) {
            set.add(infoBean.splitPackageName);
            datas.add(infoBean);
        } else {
            if (split.length > 0) {
                set.add(split[0]);
            }
        }
    }

}
