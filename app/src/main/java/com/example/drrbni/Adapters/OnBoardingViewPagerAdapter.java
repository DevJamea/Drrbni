package com.example.drrbni.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.drrbni.Models.OnBoardingItem;
import com.example.drrbni.R;

import java.util.List;

public class OnBoardingViewPagerAdapter extends PagerAdapter {

   Context mContext ;
   List<OnBoardingItem> mListScreen;

    public OnBoardingViewPagerAdapter(Context mContext, List<OnBoardingItem> mListScreen) {
        this.mContext = mContext;
        this.mListScreen = mListScreen;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.fragment_onboarding_container,null);

        ImageView imgSlide = layoutScreen.findViewById(R.id.onboarding1_image);
        TextView title = layoutScreen.findViewById(R.id.onboarding1_title);
        TextView subtitle = layoutScreen.findViewById(R.id.onboarding1_subtitle);

        title.setText(mListScreen.get(position).getTitle());
        subtitle.setText(mListScreen.get(position).getDescription());
        imgSlide.setImageResource(mListScreen.get(position).getScreenImg());

        container.addView(layoutScreen);

        return layoutScreen;
    }

    @Override
    public int getCount() {
        return mListScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View)object);
    }
}
