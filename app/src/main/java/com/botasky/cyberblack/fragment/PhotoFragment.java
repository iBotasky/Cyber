package com.botasky.cyberblack.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.botasky.cyberblack.R;
import com.botasky.cyberblack.util.ImageUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by botasky on 31/03/2017.
 */

public class PhotoFragment extends BaseFragment {
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;

    public static final String KEY = "url";

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ImageUtil.displayImageByUrlFitCenter(getContext(), getArguments().getString(KEY), ivPhoto);
    }

    public static PhotoFragment newInstance(String args) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY, args);
        PhotoFragment photoFragment = new PhotoFragment();
        photoFragment.setArguments(bundle);
        return photoFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_photo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        initView(rootView, savedInstanceState);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
