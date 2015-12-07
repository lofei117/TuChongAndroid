package info.lofei.app.tuchong.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.activity.MainActivity;
import info.lofei.app.tuchong.data.RequestManager;
import info.lofei.app.tuchong.data.request.LoginRequest;
import info.lofei.app.tuchong.model.TCActivity;
import info.lofei.app.tuchong.model.TCImage;
import info.lofei.app.tuchong.model.TCPost;
import info.lofei.app.tuchong.model.TCSite;
import info.lofei.app.tuchong.utils.NumberUtil;
import info.lofei.app.tuchong.utils.PreferenceUtil;
import info.lofei.app.tuchong.utils.SitesMapCache;
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_main, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TCActivity activity = mTCActivitiesList.get(position);
        holder.bindData(activity);
    }

    @Override
    public int getItemCount() {
        return mTCActivitiesList == null ? 0 : mTCActivitiesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        View itemView;

        @Bind(R.id.iv_photo)
        ImageView image;

        @Bind(R.id.tv_image_count)
        TextView imageCount;

        @Bind(R.id.tv_title)
        TextView title;

        @Bind(R.id.tv_like_count)
        TextView like_count;

        @Bind(R.id.tv_comment_count)
        TextView comment_count;

        @Bind(R.id.tv_author_name)
        TextView author_name;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }

        public void bindData(final TCActivity activity) {
            if (activity != null) {
                final TCPost post = activity.getPost();
                List<Long> ids = activity.getSite_id_array();
                boolean isFavorited = ids != null && ids.contains(NumberUtil.toLong(
                        PreferenceUtil.getString(LoginRequest.DATA_SAVE_TUCHONG_CURRENT_USER_ID,"")));
                post.setIs_favorite(isFavorited);

                TCSite site = SitesMapCache.getSite(post.getAuthor_id());
                if (site != null) {
                    author_name.setText(site.getName());
                }
                image.setImageResource(0);
                title.setText(post.getTitle());
                like_count.setText(String.valueOf(post.getFavorites()));
                comment_count.setText(String.valueOf(post.getComments()));
                int imgCount = post.getImage_count();
                if (imgCount > 0) {
                    TCImage tcImage = post.getImages().get(0);
                    String url = String.format(TuChongApi.PHOTO_URL_LARGE, post.getAuthor_id(), tcImage.getImg_id());
                    RequestManager.loadImage(url, RequestManager.getImageListener(image, null, mFailedDrawable));
                }

                if(imgCount > 1){
                    imageCount.setText(mContext.getString(R.string.item_image_count, imgCount));
                    imageCount.setVisibility(View.VISIBLE);
                }else{
                    imageCount.setVisibility(View.GONE);
                }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        if (mContext instanceof MainActivity) {
                            ((MainActivity) mContext).launchDetailFragment(post, image);
                        }
                    }
                });
            }
        }
    }
}
