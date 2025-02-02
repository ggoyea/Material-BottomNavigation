package it.sephiroth.android.library.bottomnavigation;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

import static android.util.Log.DEBUG;
import static android.util.Log.INFO;
import static it.sephiroth.android.library.bottomnavigation.MiscUtils.log;

/**
 * Created by alessandro on 4/10/16 at 2:12 PM.
 * Project: Material-BottomNavigation
 */
@Keep
@KeepClassMembers
public class TabletBehavior extends VerticalScrollingBehavior<BottomNavigation> {
    private static final String TAG = TabletBehavior.class.getSimpleName();
    private int topInset;
    private boolean enabled;
    private int width;
    private boolean translucentStatus;

    public TabletBehavior(final Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setLayoutValues(final int bottomNavWidth, final int topInset, final boolean translucentStatus) {
        log(TAG, INFO, "setLayoutValues(%d, %d)", bottomNavWidth, topInset);
        this.translucentStatus = translucentStatus;
        this.width = bottomNavWidth;
        this.topInset = topInset;
        this.enabled = true;
    }

    @Override
    public boolean layoutDependsOn(final CoordinatorLayout parent, final BottomNavigation child, final View dependency) {
        return AppBarLayout.class.isInstance(dependency);
    }

    @Override
    public boolean onDependentViewChanged(
        final CoordinatorLayout parent, final BottomNavigation child, final View dependency) {
        log(TAG, INFO, "onDependentViewChange: %s", dependency.getClass().getSimpleName());
        log(TAG, DEBUG, "top: %d, topInset: %d", dependency.getTop(), topInset);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
        params.topMargin = Math.max(dependency.getTop() + dependency.getHeight() - topInset, translucentStatus ? 0 : -topInset);

        if (translucentStatus) {
            if (params.topMargin < topInset) {
                child.setPadding(0, topInset - params.topMargin, 0, 0);
            } else {
                child.setPadding(0, 0, 0, 0);
            }
        }

        child.requestLayout();
        return true;
    }

    @Override
    public void onDependentViewRemoved(final CoordinatorLayout parent, final BottomNavigation child, final View dependency) {
        super.onDependentViewRemoved(parent, child, dependency);
    }

    @Override
    public boolean onLayoutChild(final CoordinatorLayout parent, final BottomNavigation child, final int layoutDirection) {
        log(TAG, INFO, "onLayoutChild: %d", layoutDirection);
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public void onNestedVerticalOverScroll(
        final CoordinatorLayout coordinatorLayout, final BottomNavigation child, @ScrollDirection final int direction,
        final int currentOverScroll, final int totalOverScroll) {

    }

    @Override
    public void onDirectionNestedPreScroll(
        final CoordinatorLayout coordinatorLayout, final BottomNavigation child, final View target, final int dx, final int dy,
        final int[] consumed,
        @ScrollDirection final int scrollDirection) {

    }

    @Override
    protected boolean onNestedDirectionFling(
        final CoordinatorLayout coordinatorLayout, final BottomNavigation child, final View target, final float velocityX,
        final float velocityY,
        @ScrollDirection final int scrollDirection) {
        return false;
    }
}
