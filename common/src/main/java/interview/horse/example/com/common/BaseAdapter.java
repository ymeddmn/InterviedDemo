package interview.horse.example.com.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

/**
 * Created by mayong on 2018/10/6.
 */

public class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.BaseHolder> {
    private Context context;
    private List<InfoBean> datas;

    public BaseAdapter(Context context, List<InfoBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View content = View.inflate(context, R.layout.item_btn, null);
        BaseHolder baseHolder = new BaseHolder(content);
        return baseHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder holder, int position) {
        InfoBean infoBean = datas.get(position);
        holder.button.setText(infoBean.isClass ? infoBean.splitPackageName : infoBean.splitPackageName);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (infoBean.isClass) {
                    Intent intent = new Intent();
                    intent.setClassName(context.getPackageName(), infoBean.completeClassName);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent();
                    intent.setAction(BaseActivity.ACTION_ACTIVITY);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.putExtra(BaseActivity.PACKAGENAME, infoBean.splitPackageName);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class BaseHolder extends RecyclerView.ViewHolder {
        public Button button;

        public BaseHolder(View itemView) {
            super(itemView);
            this.button = itemView.findViewById(R.id.base_btn);
        }
    }
}
