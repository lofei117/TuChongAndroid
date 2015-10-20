package info.lofei.app.tuchong.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.activity.ImageActivity;
import info.lofei.app.tuchong.data.RequestManager;
import info.lofei.app.tuchong.model.TCAuthor;
import info.lofei.app.tuchong.model.TCComment;
import info.lofei.app.tuchong.model.TCImage;
import info.lofei.app.tuchong.model.TCPost;
import info.lofei.app.tuchong.vendor.TuChongApi;

/**
 * TODO comment here.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-07-08 13:38
 */
public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.BaseViewHolder> {

    private Context mContext;

    private TCPost mPost;

    private List<TCComment> mCommentList;

    public DetailAdapter(final Context context, final TCPost post) {
        mPost = post;
        mContext = context;
    }

    public void fillData(List<TCComment> commentList) {
        mCommentList = commentList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(final int position) {
        if (position == 0) {
            return R.layout.header_detail;
        } else if (position >= getImageCount() + getHeaderCount()) {
            return R.layout.item_comment_detail;
        }
        return R.layout.item_image_detail;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(mContext).inflate(viewType, parent, false);
        DetailAdapter.BaseViewHolder viewHolder = null;
        switch (viewType) {
            case R.layout.header_detail:
                viewHolder = new HeaderViewHolder(view);
                break;
            case R.layout.item_comment_detail:
                viewHolder = new CommentViewHolder(view);
                break;
            default:
                viewHolder = new ImageViewHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return mPost == null ? 0 : (getImageCount() + getHeaderCount() + getCommentCount());
    }

    private int getHeaderCount() {
        return 1;
    }

    private int getImageCount() {
        return mPost.getImage_count();
    }

    private int getCommentCount() {
        return mCommentList == null ? 0 : mCommentList.size();
    }

    abstract class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        abstract void bindData(int position);
    }

    class HeaderViewHolder extends BaseViewHolder {

        @Bind(R.id.tv_title)
        TextView title;

        @Bind(R.id.tv_excerpt)
        TextView excerpt;

        public HeaderViewHolder(final View itemView) {
            super(itemView);
        }

        @Override
        void bindData(final int position) {
            if (mPost.getTitle().isEmpty()) {
                title.setVisibility(View.GONE);
            } else if (mPost.getExcerpt().isEmpty()){
                excerpt.setVisibility(View.GONE);
                title.setText(mPost.getTitle());
            }
            excerpt.setText(mPost.getExcerpt());
        }
    }

    class CommentViewHolder extends BaseViewHolder {

        @Bind(R.id.iv_avatar)
        ImageView avatar;

        @Bind(R.id.tv_comment_author)
        TextView author;

        @Bind(R.id.tv_comment_detail)
        TextView comment;

        @Bind(R.id.tv_comment_time)
        TextView time;

        public CommentViewHolder(final View itemView) {
            super(itemView);
        }

        @Override
        void bindData(int position) {
            position -= (getHeaderCount() + getImageCount());
            TCComment tcComment = mCommentList.get(position);
            if (tcComment != null) {
                TCAuthor tcAuthor = tcComment.getAuthor();
                RequestManager.loadImage(tcAuthor.getIconUrl(), RequestManager.getImageListener(avatar, null, null));
                if (tcComment.getReplyTo() == null || tcComment.getReplyTo().isEmpty()) {
                    author.setText(tcAuthor.getName());
                } else {
                    author.setText(mContext.getString(R.string.reply_to, tcAuthor.getName(), tcComment.getReplyTo().get(0).getName()));
                }
                comment.setText(tcComment.getContent());
                time.setText(tcComment.getCreatedAt());
            }
        }
    }

    class ImageViewHolder extends BaseViewHolder {

        @Bind(R.id.tv_title)
        TextView title;

        @Bind(R.id.tv_excerpt)
        TextView excerpt;

        @Bind(R.id.iv_photo)
        ImageView image;

        public ImageViewHolder(final View itemView) {
            super(itemView);
        }

        @Override
        void bindData(final int position) {
//            if (position == 0) {
//                ViewCompat.setTransitionName(image, mContext.getString(R.string.transition_image));
//                ((AppCompatActivity) mContext).supportStartPostponedEnterTransition();
//            } else {
//                ViewCompat.setTransitionName(image, null);
//            }
            if       (mPost.getType().equalsIgnoreCase(TCPost.TEXT)) {
                title.setText(mPost.getTitle());
                excerpt.setText(mPost.getExcerpt());
                title.setVisibility(View.VISIBLE);
                excerpt.setVisibility(View.VISIBLE);
            } else {
                title.setVisibility(View.GONE);
                excerpt.setVisibility(View.GONE);
            }
            TCImage tcImage = mPost.getImages().get(position - getHeaderCount());
            final String url = String.format(TuChongApi.PHOTO_URL_LARGE, mPost.getAuthor_id(), tcImage.getImg_id());
            RequestManager.loadImage(url, RequestManager.getImageListener(image, null, null));

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    Intent intent = new Intent(mContext, ImageActivity.class);
                    intent.putExtra(ImageActivity.BUNDLE_EXTRA_IMAGE_URL, url);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, image,
                                mContext.getString(R.string.transition_image));
                        mContext.startActivity(intent, transitionActivityOptions.toBundle());
                    } else {
                        mContext.startActivity(intent);
                    }
                }
            });

            image.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View v) {
                    Toast.makeText(v.getContext(), "get exif data", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }
    }
}
