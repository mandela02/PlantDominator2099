package map.wayne.com.plantdominator2099.ui.activities;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import map.wayne.com.plantdominator2099.R;
import map.wayne.com.plantdominator2099.data.model.TreeData;

public class MarkerDrawer {
    public static void DrawMarker(final TreeData tree, final MarkerOptions options) {
        if (tree.getStatus() == 1) options.alpha(0.5f);
        switch (tree.getSize()) {
            case 1:
                switch (tree.getStatus()) {
                    case 1:
                        options.icon(BitmapDescriptorFactory.fromResource(R.drawable
                            .marker_bamboo_green));
                        break;
                    case 2:
                        options
                            .icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.marker_bamboo_red));
                        break;
                }
                break;
            case 2:
                switch (tree.getStatus()) {
                    case 1:
                        options.icon(BitmapDescriptorFactory.fromResource(R.drawable
                            .marker_bonsai_green));
                        break;
                    case 2:
                        options
                            .icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.marker_bonsai_red));
                        break;
                }
                break;
            case 3:
                switch (tree.getStatus()) {
                    case 1:
                        options.icon(BitmapDescriptorFactory.fromResource(R.drawable
                            .marker_flower_green));
                        break;
                    case 2:
                        options
                            .icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.marker_flower_red));
                        break;
                }
                break;
            case 4:
                switch (tree.getStatus()) {
                    case 1:
                        options.icon(BitmapDescriptorFactory.fromResource(R.drawable
                            .marker_grass_green));
                        break;
                    case 2:
                        options
                            .icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.marker_grass_red));
                        break;
                }
                break;
            case 5:
                switch (tree.getStatus()) {
                    case 1:
                        options.icon(BitmapDescriptorFactory.fromResource(R.drawable
                            .marker_old_tree_green));
                        break;
                    case 2:
                        options
                            .icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.marker_old_tree_red));
                        break;
                }
                break;
            case 6:
                switch (tree.getStatus()) {
                    case 1:
                        options.icon(BitmapDescriptorFactory.fromResource(R.drawable
                            .marker_pine_green));
                        break;
                    case 2:
                        options
                            .icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.marker_pine_red));
                        break;
                }
                break;
            case 7:
                switch (tree.getStatus()) {
                    case 1:
                        options.icon(BitmapDescriptorFactory.fromResource(R.drawable
                            .marker_rose_green));
                        break;
                    case 2:
                        options
                            .icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.marker_rose_red));
                        break;
                }
                break;
            case 8:
                switch (tree.getStatus()) {
                    case 1:
                        options.icon(BitmapDescriptorFactory.fromResource(R.drawable
                            .marker_sprout_green));
                        break;
                    case 2:
                        options
                            .icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.marker_sprout_red));
                        break;
                }
                break;
            case 9:
                switch (tree.getStatus()) {
                    case 1:
                        options.icon(BitmapDescriptorFactory.fromResource(R.drawable
                            .marker_tree_green));
                        break;
                    case 2:
                        options
                            .icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.marker_tree_red));
                        break;
                }
                break;
            case 10:
                switch (tree.getStatus()) {
                    case 1:
                        options.icon(BitmapDescriptorFactory.fromResource(R.drawable
                            .marker_willow_green));
                        break;
                    case 2:
                        options
                            .icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.marker_willow_red));
                        break;
                }
                break;
        }
    }

    public static void DrawMarkerChoosen(TreeData tree, Marker marker) {
        if (tree.getStatus() == 1) marker.setAlpha(0.5f);
            switch (tree.getSize()) {
                case 1:
                    marker
                        .setIcon(BitmapDescriptorFactory
                            .fromResource(R.drawable.marker_bamboo_yellow));
                    break;
                case 2:
                    marker
                        .setIcon(BitmapDescriptorFactory
                            .fromResource(R.drawable.marker_bonsai_yellow));
                    break;
                case 3:
                    marker
                        .setIcon(BitmapDescriptorFactory
                            .fromResource(R.drawable.marker_flower_yellow));
                    break;
                case 4:
                    marker
                        .setIcon(BitmapDescriptorFactory
                            .fromResource(R.drawable.marker_grass_yellow));
                    break;
                case 5:
                    marker
                        .setIcon(BitmapDescriptorFactory
                            .fromResource(R.drawable.marker_old_tree_yellow));
                    break;
                case 6:
                    marker
                        .setIcon(BitmapDescriptorFactory
                            .fromResource(R.drawable.marker_pine_yellow));
                    break;
                case 7:
                    marker
                        .setIcon(BitmapDescriptorFactory
                            .fromResource(R.drawable.marker_rose_yellow));
                    break;
                case 8:
                    marker
                        .setIcon(BitmapDescriptorFactory
                            .fromResource(R.drawable.marker_sprout_yellow));
                    break;
                case 9:
                    marker
                        .setIcon(BitmapDescriptorFactory
                            .fromResource(R.drawable.marker_tree_yellow));
                    break;
                case 10:
                    marker
                        .setIcon(BitmapDescriptorFactory
                            .fromResource(R.drawable.marker_willow_yellow));
                    break;
            }
        }

    public static void DrawMarkerMarker(final TreeData tree, final Marker marker) {
        if (tree.getStatus() == 1) marker.setAlpha(0.5f);
        switch (tree.getSize()) {
            case 1:
                switch (tree.getStatus()) {
                    case 1:
                        marker
                            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable
                            .marker_bamboo_green));
                        break;
                    case 2:
                        marker
                            .setIcon(BitmapDescriptorFactory
                                .fromResource(R.drawable.marker_bamboo_red));
                        break;
                }
                break;
            case 2:
                switch (tree.getStatus()) {
                    case 1:
                        marker
                            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable
                            .marker_bonsai_green));
                        break;
                    case 2:
                        marker
                            .setIcon(BitmapDescriptorFactory
                                .fromResource(R.drawable.marker_bonsai_red));
                        break;
                }
                break;
            case 3:
                switch (tree.getStatus()) {
                    case 1:
                        marker
                            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable
                            .marker_flower_green));
                        break;
                    case 2:
                        marker
                            .setIcon(BitmapDescriptorFactory
                                .fromResource(R.drawable.marker_flower_red));
                        break;
                }
                break;
            case 4:
                switch (tree.getStatus()) {
                    case 1:
                        marker
                            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable
                            .marker_grass_green));
                        break;
                    case 2:
                        marker
                            .setIcon(BitmapDescriptorFactory
                                .fromResource(R.drawable.marker_grass_red));
                        break;
                }
                break;
            case 5:
                switch (tree.getStatus()) {
                    case 1:
                        marker
                            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable
                            .marker_old_tree_green));
                        break;
                    case 2:
                        marker
                            .setIcon(BitmapDescriptorFactory
                                .fromResource(R.drawable.marker_old_tree_red));
                        break;
                }
                break;
            case 6:
                switch (tree.getStatus()) {
                    case 1:
                        marker
                            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable
                            .marker_pine_green));
                        break;
                    case 2:
                        marker
                            .setIcon(BitmapDescriptorFactory
                                .fromResource(R.drawable.marker_pine_red));
                        break;
                }
                break;
            case 7:
                switch (tree.getStatus()) {
                    case 1:
                        marker
                            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable
                            .marker_rose_green));
                        break;
                    case 2:
                        marker
                            .setIcon(BitmapDescriptorFactory
                                .fromResource(R.drawable.marker_rose_red));
                        break;
                }
                break;
            case 8:
                switch (tree.getStatus()) {
                    case 1:
                        marker
                            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable
                            .marker_sprout_green));
                        break;
                    case 2:
                        marker
                            .setIcon(BitmapDescriptorFactory
                                .fromResource(R.drawable.marker_sprout_red));
                        break;
                }
                break;
            case 9:
                switch (tree.getStatus()) {
                    case 1:
                        marker
                            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable
                            .marker_tree_green));
                        break;
                    case 2:
                        marker
                            .setIcon(BitmapDescriptorFactory
                                .fromResource(R.drawable.marker_tree_red));
                        break;
                }
                break;
            case 10:
                switch (tree.getStatus()) {
                    case 1:
                        marker
                            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable
                            .marker_willow_green));
                        break;
                    case 2:
                        marker
                            .setIcon(BitmapDescriptorFactory
                                .fromResource(R.drawable.marker_willow_red));
                        break;
                }
                break;
        }
    }
}
