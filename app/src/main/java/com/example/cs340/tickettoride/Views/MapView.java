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
        routeIdToPoints.put("seat_vanc_1", new Point[]{new Point(60, 84)});
        routeIdToPoints.put("seat_vanc_2", new Point[]{new Point(74, 84)});
        routeIdToPoints.put("port_seat_1", new Point[]{new Point(49, 136)});
        routeIdToPoints.put("port_seat_2", new Point[]{new Point(61, 141)});
        routeIdToPoints.put("sanfran_seat_1", new Point[]{new Point(19, 331), new Point(16, 295), new Point(13, 259), new Point(17, 228), new Point(25, 190)});
        routeIdToPoints.put("sanfran_seat_2", new Point[]{new Point(32, 331), new Point(26, 295), new Point(27, 259), new Point(31, 228), new Point(41, 190)});
        routeIdToPoints.put("la_sanfran_1", new Point[]{new Point(35, 392), new Point(55, 423), new Point(78, 451)});
        routeIdToPoints.put("la_sanfran_2", new Point[]{new Point(48, 384), new Point(65, 419), new Point(88, 444)});
        routeIdToPoints.put("seat_calg", new Point[]{new Point(94, 113), new Point(133, 111), new Point(168, 97), new Point(189, 68)});
        routeIdToPoints.put("calg_hel", new Point[]{new Point(218, 65), new Point(241, 94), new Point(263, 119), new Point(288, 148)});
        routeIdToPoints.put("seat_hel", new Point[]{new Point(96, 130), new Point(128, 139), new Point(164, 137), new Point(203, 153), new Point(235, 162), new Point(269, 169)});
        routeIdToPoints.put("hel_wini", new Point[]{new Point(326, 147), new Point(348, 125), new Point(374, 99), new Point(400, 72)});
        routeIdToPoints.put("hel_slc", new Point[]{new Point(285, 201), new Point(265, 233), new Point(246, 265)});
        routeIdToPoints.put("port_slc", new Point[]{new Point(217, 270), new Point(195, 243), new Point(172, 216), new Point(142, 194), new Point(110, 176), new Point(73, 168)});
        routeIdToPoints.put("sanfran_slc_1", new Point[]{new Point(62, 343), new Point(94, 332), new Point(128, 322), new Point(163, 310), new Point(194, 299)});
        routeIdToPoints.put("sanfran_slc_2", new Point[]{new Point(67, 355), new Point(100, 346), new Point(133, 336), new Point(169, 323), new Point(199, 312)});
        routeIdToPoints.put("la_lv", new Point[]{new Point(116, 432), new Point(143, 411)});
        routeIdToPoints.put("slc_lv", new Point[]{new Point(194, 394), new Point(217, 363), new Point(225, 329)});
        routeIdToPoints.put("la_phoen", new Point[]{new Point(133, 457), new Point(168, 453), new Point(206, 459)});
        routeIdToPoints.put("phoen_elpaso", new Point[]{new Point(250, 481), new Point(288, 491), new Point(324, 500)});
        routeIdToPoints.put("phoen_santa", new Point[]{new Point(259, 459), new Point(291, 445), new Point(324, 430)});
        routeIdToPoints.put("slc_denver_1", new Point[]{new Point(256, 292), new Point(292, 299), new Point(328, 306)});
        routeIdToPoints.put("slc_denver_2", new Point[]{new Point(257, 307), new Point(292, 312), new Point(324, 319)});
        routeIdToPoints.put("phoen_denver", new Point[]{new Point(240, 446), new Point(254, 416), new Point(271, 387), new Point(298, 359), new Point(330, 340)});
        routeIdToPoints.put("la_elpaso", new Point[]{new Point(134, 485), new Point(162, 501), new Point(196, 514), new Point(231, 520), new Point(270, 524), new Point(306, 519)});
        routeIdToPoints.put("elpaso_santa", new Point[]{new Point(348, 483), new Point(348, 442)});
        routeIdToPoints.put("santa_denver", new Point[]{new Point(352, 391), new Point(352, 359)});
        routeIdToPoints.put("denver_hel", new Point[]{new Point(352, 298), new Point(338, 262), new Point(326, 230), new Point(311, 197)});
        routeIdToPoints.put("wini_sault", new Point[]{new Point(462, 55), new Point(496, 60), new Point(531, 70), new Point(566, 75), new Point(600, 83), new Point(635, 91)});
        routeIdToPoints.put("wini_dulu", new Point[]{new Point(443, 70), new Point(466, 96), new Point(495, 122), new Point(519, 146)});
        routeIdToPoints.put("hel_dulu", new Point[]{new Point(330, 172), new Point(365, 171), new Point(401, 171), new Point(436, 169), new Point(471, 170), new Point(507, 168)});
        routeIdToPoints.put("hel_oma", new Point[]{new Point(338, 191), new Point(374, 206), new Point(406, 217), new Point(438, 231), new Point(473, 236)});
        routeIdToPoints.put("denver_oma", new Point[]{new Point(381, 308), new Point(411, 289), new Point(444, 275), new Point(481, 265)});
        routeIdToPoints.put("denver_kc_1", new Point[]{new Point(393, 327), new Point(428, 328), new Point(464, 323), new Point(496, 315)});
        routeIdToPoints.put("denver_kc_2", new Point[]{new Point(393, 342), new Point(428, 343), new Point(462, 339), new Point(501, 326)});
        routeIdToPoints.put("denver_oc", new Point[]{new Point(374, 355), new Point(405, 377), new Point(442, 388), new Point(480, 393)});
        routeIdToPoints.put("santa_oc", new Point[]{new Point(382, 416), new Point(415, 415), new Point(453, 409)});
        routeIdToPoints.put("elpaso_oc", new Point[]{new Point(373, 499), new Point(408, 484), new Point(436, 466), new Point(464, 446), new Point(488, 418)});
        routeIdToPoints.put("elpaso_dal", new Point[]{new Point(398, 508), new Point(425, 504), new Point(464, 498), new Point(500, 495)});
        routeIdToPoints.put("elpaso_hous", new Point[]{new Point(370, 528), new Point(401, 541), new Point(439, 549), new Point(473, 552), new Point(508, 550), new Point(545, 539)});
        routeIdToPoints.put("hous_dal_1", new Point[]{new Point(541, 507)});
        routeIdToPoints.put("hous_dal_2", new Point[]{new Point(554, 497)});
        routeIdToPoints.put("dal_oc_1", new Point[]{new Point(515, 458), new Point(510, 422)});
        routeIdToPoints.put("dal_oc_2", new Point[]{new Point(532, 462), new Point(527, 422)});
        routeIdToPoints.put("oc_kc_1", new Point[]{new Point(509, 369), new Point(517, 340)});
        routeIdToPoints.put("oc_kc_2", new Point[]{new Point(527, 373), new Point(536, 336)});
        routeIdToPoints.put("kc_oma_1", new Point[]{new Point(514, 288)});
        routeIdToPoints.put("kc_oma_2", new Point[]{new Point(525, 282)});
        routeIdToPoints.put("oma_dulu_1", new Point[]{new Point(506, 230), new Point(521, 195)});
        routeIdToPoints.put("oma_dulu_2", new Point[]{new Point(522, 232), new Point(535, 200)});
        routeIdToPoints.put("dulu_saul", new Point[]{new Point(570, 143), new Point(604, 129), new Point(639, 114)});
        routeIdToPoints.put("saul_toro", new Point[]{new Point(697, 107), new Point(728, 111)});
        routeIdToPoints.put("saul_mont", new Point[]{new Point(682, 80), new Point(715, 62), new Point(749, 46), new Point(780, 35), new Point(818, 31)});
        routeIdToPoints.put("dulu_toro", new Point[]{new Point(561, 162), new Point(599, 154), new Point(633, 153), new Point(670, 146), new Point(702, 138), new Point(740, 132)});

    }
}
