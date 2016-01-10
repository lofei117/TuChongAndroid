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

import butterknife.Bind;
import butterknife.ButterKnife;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.activity.PostDetailActivity;
import info.lofei.app.tuchong.data.RequestManager;
import info.lofei.app.tuchong.model.TCImage;
import info.lofei.app.tuchong.model.TCPost;
import info.lofei.app.tuchong.model.TCSite;
import info.lofei.app.tuchong.utils.SitesMapCache;
import info.lofei.app.tuchong.vendor.TuChongApi;

/**
 * Created by lofei on 6/18/15.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context mContext;

    private Drawable mFailedDrawable;

    private List<TCPost> mTCPostList;

    public CategoryAdapter(Context context) {
        mContext = context;
        mFailedDrawable = context.getResources().getDrawable(R.drawable.ic_dashboard);
    }

    public void fillData(List<TCPost> list) {
        mTCPostList = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_main, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TCPost post = mTCPostList.get(position);
        holder.bindData(post);
    }

    @Override
    public int getItemCount() {
        return mTCPostList == null ? 0 : mTCPostList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        View itemView;

        @Bind(R.id.iv_photo)
        ImageView image;

        @Bind(R.id.tv_title)
        TextView title;

        @Bind(R.id.tv_image_count)
        TextView imageCount;

        @Bind(R.id.tv_favorite_count)
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

        public void bindData(final TCPost post) {
            if (post != null) {
                TCSite site = SitesMapCache.getSite(post.getAuthorId());
                if (site != null) {
                    author_name.setText(site.getName());
                }
                image.setImageResource(0);
                title.setText(post.getTitle());
                like_count.setText(String.valueOf(post.getFavoriteCount()));
                comment_count.setText(String.valueOf(post.getCommentCount()));
                int imgCount = post.getImageCount();
                if (imgCount > 0) {
                    TCImage tcImage = post.getImages().get(0);
                    String url = String.format(TuChongApi.PHOTO_URL_LARGE, post.getAuthorId(), tcImage.getImageId());
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
                        PostDetailActivity.launch(v.getContext(), post);
                    }
                });
            }
        }
    }
}
