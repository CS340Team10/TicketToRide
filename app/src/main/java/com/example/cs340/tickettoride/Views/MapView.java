package com.example.cs340.tickettoride.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.widget.ImageView;

import com.example.cs340.tickettoride.R;

import java.util.HashMap;
import java.util.Map;

import common.PlayerAttributes;

/**
 * Created by ephraimkunz on 2/28/18.
 */

public class MapView implements IMapView{
    Context context;
    ImageView iv;
    Map<String, Point[]> routeIdToPoints;
    Bitmap bitmap;

    @Override
    public void drawRouteAsClaimed(String routeId, PlayerAttributes.Color playerColor) {
        // Get points
        Point[] points = routeIdToPoints.get(routeId);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        int drawColor = getGraphicsColorForPlayerColor(playerColor);
        paint.setColor(drawColor);

        Canvas canvas = new Canvas(bitmap);
        for (Point p : points) {
            canvas.drawCircle(p.x, p.y, 10, paint);
        }

        iv.setAdjustViewBounds(true);
        iv.setImageBitmap(bitmap);
    }

    private int getGraphicsColorForPlayerColor(PlayerAttributes.Color playerColor) {
        switch (playerColor) {
            case red: return Color.RED;
            case blue: return Color.BLUE;
            case black: return Color.BLACK;
            case green: return Color.GREEN;
            case yellow: return Color.YELLOW;
        }

        return Color.CYAN; // Should never hit
    }

    @Override
    public void setParams(Context context, ImageView view) {
        this.context = context;
        this.iv = view;

        setupBitmap();
    }

    private void setupBitmap() {
        // Init bitmap factory now with context
        BitmapFactory.Options myOptions = new BitmapFactory.Options();
        myOptions.inDither = true;
        myOptions.inScaled = false;
        myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// important

        Bitmap immutable = BitmapFactory.decodeResource(context.getResources(), R.drawable.ttr,myOptions);
        Bitmap workingBitmap = Bitmap.createBitmap(immutable);
        this.bitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);
    }

    public MapView() {

        routeIdToPoints = new HashMap<>();
        routeIdToPoints.put("vanc_calg", new Point[]{new Point(97, 51), new Point(137, 52), new Point(169, 47)});
        routeIdToPoints.put("calg_wini", new Point[]{new Point(228, 32), new Point(263, 21), new Point(298, 17), new Point(333, 16), new Point(367, 23), new Point(402, 33)});

    }
}
