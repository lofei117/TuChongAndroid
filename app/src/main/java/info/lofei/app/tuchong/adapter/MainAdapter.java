package info.lofei.app.tuchong.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.data.RequestManager;
import info.lofei.app.tuchong.model.TCActivity;
import info.lofei.app.tuchong.vendor.TuChongApi;

/**
 * Created by lofei on 6/18/15.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private Context mContext;

    private Drawable mFailedDrawable;

    private List<TCActivity> mTCActivitiesList;

    public MainAdapter(Context context) {
        mContext = context;
        mFailedDrawable = context.getResources().getDrawable(R.drawable.ic_dashboard);
    }

    public void fillData(List<TCActivity> list) {
        mTCActivitiesList = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_main, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TCActivity activity = mTCActivitiesList.get(position);
        if (activity != null) {
            // TODO get the correct author name
            holder.author_name.setText(String.valueOf(activity.getPost().getAuthor_id()));
            holder.title.setText(activity.getPost().getTitle());
            holder.like_count.setText(String.valueOf(activity.getPost().getFavorites()));
            holder.comment_count.setText(String.valueOf(activity.getPost().getComments()));
            String url = String.format(TuChongApi.PHOTO_URL_LARGE, activity.getPost().getAuthor_id(), activity.getPost().getImages().get(0).getImg_id());
            RequestManager.loadImage(url, RequestManager.getImageListener(holder.image, null, mFailedDrawable));
        }
    }

    @Override
    public int getItemCount() {
        return mTCActivitiesList == null ? 0 : mTCActivitiesList.size();
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
