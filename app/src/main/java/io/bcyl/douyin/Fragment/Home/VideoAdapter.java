package io.bcyl.douyin.Fragment.Home;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.bcyl.douyin.R;
import io.bcyl.douyin.Utils.Constants;
import io.bcyl.douyin.Utils.VideoInfo;

public class VideoAdapter extends RecyclerView.Adapter<VideoViewHolder> {
//    List<VideoItem> videoItemList = new ArrayList<VideoItem>();
    List<VideoInfo> videoInfoList;
    Context context;
    private RecyclerView recyclerView;

//    public VideoAdapter(ArrayList<VideoItem> list, Context context) {
//        this.videoItemList = list;
//        this.context = context;
//    }

    public VideoAdapter(ArrayList<VideoInfo> list, Context context) {
        this.videoInfoList = list;
        this.context = context;
    }


    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_video_view, viewGroup, false);
        VideoViewHolder videoViewHolder = new VideoViewHolder(v);
        videoViewHolder.setAdapter(this);
        return videoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder videoViewHolder, int i) {
//        videoViewHolder.url = videoItemList.get(i).getVideoUrl();
//        videoViewHolder.usrName.setText(videoItemList.get(i).getUserName());
//        videoViewHolder.vidTitle.setText(videoItemList.get(i).getTitle());
        String vidTitleStr = videoInfoList.get(i).getExtraValue();
        Log.i("videoInfoList.get(i).getExtraValue()", vidTitleStr);
        if (vidTitleStr.split(Constants.DELIM).length > 1)
            vidTitleStr = videoInfoList.get(i).getExtraValue().split(Constants.DELIM)[1];
        else vidTitleStr = "";
        videoViewHolder.url = videoInfoList.get(i).getVideoUrl();
        videoViewHolder.usrName.setText(videoInfoList.get(i).getUserName());
        videoViewHolder.vidTitle.setText(vidTitleStr);
        videoViewHolder.initializePlayer();
    }

    @Override
    public void onViewRecycled(@NonNull VideoViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull VideoViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.getPlayer().play();
        holder.getVidTitle().setAlpha(1f);
        holder.getUsrName().setAlpha(1f);
        AnimatorSet as = new AnimatorSet();
        as.playTogether(
                ObjectAnimator.ofFloat(holder.getVidTitle(), "alpha", 1f,0.1f).setDuration(2000),
                ObjectAnimator.ofFloat(holder.getUsrName(), "alpha", 1f,0.1f).setDuration(2000));
        as.start();

//        holder.getVidTitle().postDelayed(new Runnable(){
//            @Override
//            public void run()
//            {
//                holder.getVidTitle().setVisibility(View.GONE);
//            }
//        }, 1000);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull VideoViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.getPlayer().pause();
        holder.getPlayer().seekTo(0);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    public void setRecyclerView(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
    }

    public RecyclerView getRecyclerView(){
        return recyclerView;
    }


    @Override
    public int getItemCount() {
        return videoInfoList.size();
    }


    public void setData(List<VideoInfo> vl) {
        videoInfoList.clear();
        videoInfoList.addAll(vl);
        notifyDataSetChanged();
    }


}
