package info.lofei.app.tuchong.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import info.lofei.app.tuchong.model.TCSite;
import info.lofei.app.tuchong.vendor.TuChongApi;

/**
 * TODO comment here.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-07-08 13:38
 */
public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.BaseViewHolder> {

    public static final int ITEM_COUNT_IN_ONE_LINE = 3;
    private Context mContext;

    private TCPost mPost;

    private List<TCComment> mCommentList;

    public DetailAdapter(final Context context, final TCPost post) {
        mPost = post;
        mContext = context;
    }

    public void fillCommentsData(List<TCComment> commentList) {
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
            case R.layout.item_image_detail:
                viewHolder = new PhotoImagesHolder(view);
                break;
            default:
                viewHolder = new PhotoImagesHolder(view);
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
        return mPost == null ? 0 :
                (getImageCount() + getHeaderCount() + getCommentCount());
    }

    private int getHeaderCount() {
        return 1;
    }

    private int getImageCount() {
        int line = mPost.getImage_count() / ITEM_COUNT_IN_ONE_LINE +
                (mPost.getImage_count() % ITEM_COUNT_IN_ONE_LINE == 0 ? 0 : 1);
        return line;
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
        TextView postTitle;

        @Bind(R.id.tv_excerpt)
        TextView postExcerpt;

        @Bind(R.id.iv_avatar)
        ImageView postAuthorAvatar;

        @Bind(R.id.tv_post_author_name)
        TextView postAuthorName;

        @Bind(R.id.tv_post_published_at)
        TextView postPublishedAt;

        public HeaderViewHolder(final View itemView) {
            super(itemView);
        }

        private void setText(TextView textView, String str){
            textView.setText(str);
            textView.setVisibility(TextUtils.isEmpty(str) ? View.GONE : View.VISIBLE);
        }

        @Override
        void bindData(final int position) {

            postAuthorName.setVisibility(View.VISIBLE);
            postAuthorAvatar.setVisibility(View.VISIBLE);
            postTitle.setVisibility(View.VISIBLE);
            postTitle.setVisibility(View.VISIBLE);
            postExcerpt.setVisibility(View.VISIBLE);
            postPublishedAt.setVisibility(View.VISIBLE);

            setText(postTitle, mPost.getTitle());
            setText(postExcerpt, mPost.getParsedContent());
            setText(postExcerpt, mPost.getExcerpt());

            postPublishedAt.setText(mPost.getPublished_at());
            TCAuthor author = mPost.getAuthor();

            TCSite site = mPost.getSite();

            if(author != null){
                postAuthorName.setText(author.getName());
                RequestManager.loadImage(author.getIconUrl(),
                        RequestManager.getImageListener(postAuthorAvatar, null, null));
                //postAuthorAvatar.setOnClickListener(this);
            }

            if(site != null){
                postAuthorName.setText(site.getName());
                RequestManager.loadImage(site.getIcon(),
                        RequestManager.getImageListener(postAuthorAvatar, null, null));
                //postAuthorAvatar.setOnClickListener(this);
            }

            if(author == null && site == null){
                postAuthorName.setVisibility(View.GONE);
                postAuthorAvatar.setVisibility(View.GONE);
            }

            postExcerpt.setText(mPost.getExcerpt());
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

    class PhotoImagesHolder extends BaseViewHolder {

        @Bind(R.id.iv_photo_layout)
        ViewGroup imageLayout;

        @Bind(R.id.left_photo_view)
        ImageView leftPhotoView;

        @Bind(R.id.middle_photo_view)
        ImageView middlePhotoView;

        @Bind(R.id.right_photo_view)
        ImageView rightPhotoView;



        public PhotoImagesHolder(final View itemView) {
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
            //todo  the TCPost.TEXT type post need to handle.

            if (mPost.getImages() == null || mPost.getImages().isEmpty()){
                return;
            }

            int baseItemIdx = (position - getHeaderCount()) * ITEM_COUNT_IN_ONE_LINE;

            for(int i = 0; i < ITEM_COUNT_IN_ONE_LINE; i++){
                int itemIdx = baseItemIdx + i;
                final ImageView image;
                switch (i){
                    case 0:
                        image = leftPhotoView;
                        break;
                    case 1:
                        image = middlePhotoView;
                        break;
                    case 2:
                        image = rightPhotoView;
                        break;
                    default:
                        image = leftPhotoView;
                        break;
                }

                image.setImageBitmap(null);

                if(itemIdx >= mPost.getImages().size() || itemIdx < 0){
                    image.setVisibility(baseItemIdx == 0 ? View.GONE : View.INVISIBLE);
                    continue;
                }

                image.setVisibility(View.VISIBLE);

                TCImage tcImage = mPost.getImages().get(itemIdx);

                final String url = String.format(TuChongApi.PHOTO_URL_LARGE, mPost.getAuthor_id(),
                        tcImage.getImg_id());
                RequestManager.loadImage(url, RequestManager.getImageListener(image, null, null));

                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        Intent intent = new Intent(mContext, ImageActivity.class);
                        intent.putExtra(ImageActivity.BUNDLE_EXTRA_IMAGE_URL, url);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            ActivityOptions transitionActivityOptions =
                                    ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, image,
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
}
