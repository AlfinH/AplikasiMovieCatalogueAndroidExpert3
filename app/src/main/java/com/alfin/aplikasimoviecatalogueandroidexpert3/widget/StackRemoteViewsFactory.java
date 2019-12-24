package com.alfin.aplikasimoviecatalogueandroidexpert3.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.alfin.aplikasimoviecatalogueandroidexpert3.BuildConfig;
import com.alfin.aplikasimoviecatalogueandroidexpert3.R;
import com.alfin.aplikasimoviecatalogueandroidexpert3.helper.MappingHelper;
import com.alfin.aplikasimoviecatalogueandroidexpert3.model.MovieTvShow;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.alfin.aplikasimoviecatalogueandroidexpert3.db.DatabaseContract.MovieTvShowColumns.CONTENT_URI;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<MovieTvShow> movieTvShowList = new ArrayList<>();
    private final List<Bitmap> mWidgetItems = new ArrayList<>();
    private final Context mContext;
    private Cursor cursor;

    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        //required
    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null) {
            cursor.close();
            movieTvShowList.clear();
            mWidgetItems.clear();
        }

        final long identityToken = Binder.clearCallingIdentity();
        // querying ke database
        cursor = mContext.getContentResolver().query(CONTENT_URI, null, null, null, null);

        movieTvShowList = MappingHelper.mapCursorToArrayList(cursor);
        for (int i = 0; i < movieTvShowList.size(); i++) {
            try {
                URL url = new URL(BuildConfig.BASE_URL_W342 + movieTvShowList.get(i).getGambar());
                Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                mWidgetItems.add(image);
            } catch (IOException e) {
                System.out.println(e);
            }
        }

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        //required
    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        rv.setImageViewBitmap(R.id.imageView, mWidgetItems.get(position));

        Bundle extras = new Bundle();
        extras.putInt(FavoritesBannerWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}