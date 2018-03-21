package com.example.cs340.tickettoride.Views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.widget.ImageView;

import com.example.cs340.tickettoride.R;

import java.util.HashMap;
import java.util.Map;

import ClientModel.Player;
import Presenters.IMapPresenter;
import Presenters.MapPresenter;

/**
 * Created by ephraimkunz on 2/28/18.
 */

public class MapView implements IMapView{
    Context context;
    ImageView iv;
    Map<String, Point[]> routeIdToPoints;
    Bitmap bitmap;

    IMapPresenter presenter = new MapPresenter(this);

    @Override
    public void drawRouteAsClaimed(String routeId, Player.PlayerColors playerColor) {
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

    private int getGraphicsColorForPlayerColor(Player.PlayerColors playerColor) {
        return ColorUtility.getColorFromPlayer(playerColor);
    }

    @Override
    public void setup(Activity activity) {
        this.context = activity.getApplicationContext();
        this.iv = activity.findViewById(R.id.mapView);

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
        routeIdToPoints.put("sanfran_port_1", new Point[]{new Point(19, 331), new Point(16, 295), new Point(13, 259), new Point(17, 228), new Point(25, 190)});
        routeIdToPoints.put("sanfran_port_2", new Point[]{new Point(32, 331), new Point(26, 295), new Point(27, 259), new Point(31, 228), new Point(41, 190)});
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
        routeIdToPoints.put("mont_bost_1", new Point[]{new Point(874, 59), new Point(907, 85)});
        routeIdToPoints.put("mont_bost_2", new Point[]{new Point(883, 50), new Point(914, 71)});
        routeIdToPoints.put("bost_ny_1", new Point[]{new Point(908, 120), new Point(888, 151)});
        routeIdToPoints.put("bost_ny_2", new Point[]{new Point(920, 127), new Point(898, 156)});
        routeIdToPoints.put("ny_wash_1", new Point[]{new Point(875, 200), new Point(877, 231)});
        routeIdToPoints.put("ny_wash_2", new Point[]{new Point(887, 198), new Point(887, 231)});
        routeIdToPoints.put("ny_pitts_1", new Point[]{new Point(841, 175), new Point(813, 192)});
        routeIdToPoints.put("ny_pitts_2", new Point[]{new Point(850, 186), new Point(819, 204)});
        routeIdToPoints.put("toro_pitts", new Point[]{new Point(783, 147), new Point(785, 183)});
        routeIdToPoints.put("pitts_wash", new Point[]{new Point(822, 233), new Point(855, 247)});
        routeIdToPoints.put("pitts_ral", new Point[]{new Point(800, 247), new Point(810, 289)});
        routeIdToPoints.put("wash_ral_1", new Point[]{new Point(861, 276), new Point(838, 307)});
        routeIdToPoints.put("wash_ral_2", new Point[]{new Point(850, 316), new Point(874, 287)});
        routeIdToPoints.put("ral_atl_1", new Point[]{new Point(800, 343), new Point(773, 365)});
        routeIdToPoints.put("ral_atl_2", new Point[]{new Point(812, 350), new Point(786, 376)});
        routeIdToPoints.put("atl_char", new Point[]{new Point(792, 396), new Point(829, 398)});
        routeIdToPoints.put("ral_char", new Point[]{new Point(848, 347), new Point(863, 368)});
        routeIdToPoints.put("chic_stl_1", new Point[]{new Point(639, 252), new Point(619, 283)});
        routeIdToPoints.put("chic_stl_2", new Point[]{new Point(648, 261), new Point(628, 290)});
        routeIdToPoints.put("kc_stl_1", new Point[]{new Point(555, 305), new Point(586, 302)});
        routeIdToPoints.put("kc_stl_2", new Point[]{new Point(553, 318), new Point(589, 316)});
        routeIdToPoints.put("stl_lr", new Point[]{new Point(608, 339), new Point(601, 375)});
        routeIdToPoints.put("stl_nash", new Point[]{new Point(633, 335), new Point(676, 344)});
        routeIdToPoints.put("oc_lr", new Point[]{new Point(534, 398), new Point(573, 398)});
        routeIdToPoints.put("dal_lr", new Point[]{new Point(555, 458), new Point(575, 428)});
        routeIdToPoints.put("hous_nor", new Point[]{new Point(598, 524), new Point(632, 519)});
        routeIdToPoints.put("chico_pitts_1", new Point[]{new Point(681, 212), new Point(718, 205), new Point(755, 201)});
        routeIdToPoints.put("chico_pitts_2", new Point[]{new Point(686, 226), new Point(725, 220), new Point(759, 217)});
        routeIdToPoints.put("dulu_chico", new Point[]{new Point(561, 189), new Point(590, 202), new Point(629, 213)});
        routeIdToPoints.put("toro_mont", new Point[]{new Point(776, 98), new Point(801, 70), new Point(828, 51)});
        routeIdToPoints.put("lr_nash", new Point[]{new Point(625, 400), new Point(662, 391), new Point(691, 373)});
        routeIdToPoints.put("lr_nor", new Point[]{new Point(612, 426), new Point(629, 457), new Point(644, 492)});
        routeIdToPoints.put("mont_ny", new Point[]{new Point(855, 68), new Point(861, 108), new Point(864, 138)});
        routeIdToPoints.put("nash_ral", new Point[]{new Point(731, 337), new Point(765, 322), new Point(800, 318)});
        routeIdToPoints.put("toro_chico", new Point[]{new Point(756, 149), new Point(725, 163), new Point(692, 180), new Point(662, 204)});
        routeIdToPoints.put("chico_oma", new Point[]{new Point(633, 227), new Point(595, 223), new Point(560, 231), new Point(532, 252)});
        routeIdToPoints.put("atl_nor_1", new Point[]{new Point(734, 401), new Point(709, 428), new Point(688, 457), new Point(673, 490)});
        routeIdToPoints.put("atl_nor_2", new Point[]{new Point(747, 412), new Point(719, 439), new Point(698, 466), new Point(683, 499)});
        routeIdToPoints.put("char_mia", new Point[]{new Point(855, 421), new Point(857, 457), new Point(867, 493), new Point(880, 522)});
        routeIdToPoints.put("pitts_nash", new Point[]{new Point(777, 252), new Point(753, 276), new Point(727, 303), new Point(705, 330)});
        routeIdToPoints.put("nash_atl", new Point[]{new Point(734, 367)});
        routeIdToPoints.put("atl_mia", new Point[]{new Point(774, 415), new Point(799, 445), new Point(818, 472), new Point(843, 501), new Point(865, 528)});
        routeIdToPoints.put("stl_pitts", new Point[]{new Point(641, 308), new Point(672, 291), new Point(702, 270), new Point(736, 253), new Point(764, 234)});
        routeIdToPoints.put("nor_mia", new Point[]{new Point(699, 506), new Point(733, 494), new Point(769, 488), new Point(807, 500), new Point(831, 518), new Point(859, 543)});

    }
}
