package info.lofei.app.tuchong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import info.lofei.app.tuchong.R;

/**
 * Created by lofei on 6/18/15.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private Context mContext;

    public MainAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_main, viewGroup);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.iv_photo)
        ImageView image;

        @InjectView(R.id.tv_title)
        TextView title;

        @InjectView(R.id.tv_like_count)
        TextView like_count;

        @InjectView(R.id.tv_comment_count)
        TextView comment_count;

        @InjectView(R.id.tv_author_name)
        TextView author_name;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
