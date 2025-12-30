package androidx.appcompat.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import androidx.annotation.RestrictTo;
import com.avito.android.R;
import l.C43521a;

/* compiled from: ScrollingTabContainerView.java */
@RestrictTo
/* loaded from: classes.dex */
public class q0 extends HorizontalScrollView implements AdapterView.OnItemSelectedListener {

    /* compiled from: ScrollingTabContainerView.java */
    public class a extends BaseAdapter {
        @Override // android.widget.Adapter
        public final int getCount() {
            throw null;
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i12) {
            throw null;
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i12) {
            return i12;
        }

        @Override // android.widget.Adapter
        public final View getView(int i12, View view, ViewGroup viewGroup) {
            if (view == null) {
                getItem(i12);
                throw null;
            }
            getItem(i12);
            throw null;
        }
    }

    /* compiled from: ScrollingTabContainerView.java */
    public class b implements View.OnClickListener {
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            ((c) view).getClass();
            throw null;
        }
    }

    /* compiled from: ScrollingTabContainerView.java */
    public class c extends LinearLayout {
        @Override // android.view.View
        public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.setClassName("androidx.appcompat.app.ActionBar$Tab");
        }

        @Override // android.view.View
        public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName("androidx.appcompat.app.ActionBar$Tab");
        }

        @Override // android.widget.LinearLayout, android.view.View
        public final void onMeasure(int i12, int i13) {
            super.onMeasure(i12, i13);
            throw null;
        }

        @Override // android.view.View
        public final void setSelected(boolean z12) {
            boolean z13 = isSelected() != z12;
            super.setSelected(z12);
            if (z13 && z12) {
                sendAccessibilityEvent(4);
            }
        }
    }

    static {
        new DecelerateInterpolator();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) throws Resources.NotFoundException {
        super.onConfigurationChanged(configuration);
        Context context = getContext();
        androidx.appcompat.view.a aVar = new androidx.appcompat.view.a();
        aVar.f21653a = context;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(null, C43521a.m.f413352a, R.attr.actionBarStyle, 0);
        int layoutDimension = typedArrayObtainStyledAttributes.getLayoutDimension(13, 0);
        Resources resources = context.getResources();
        if (!aVar.f21653a.getResources().getBoolean(R.bool.abc_action_bar_embed_tabs)) {
            layoutDimension = Math.min(layoutDimension, resources.getDimensionPixelSize(R.dimen.abc_action_bar_stacked_max_height));
        }
        typedArrayObtainStyledAttributes.recycle();
        setContentHeight(layoutDimension);
        context.getResources().getDimensionPixelSize(R.dimen.abc_action_bar_stacked_tab_max_width);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onItemSelected(AdapterView<?> adapterView, View view, int i12, long j12) {
        ((c) view).getClass();
        throw null;
    }

    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i12, int i13) {
        setFillViewport(View.MeasureSpec.getMode(i12) == 1073741824);
        throw null;
    }

    public void setContentHeight(int i12) {
        requestLayout();
    }

    public void setTabSelected(int i12) {
        throw null;
    }

    /* compiled from: ScrollingTabContainerView.java */
    public class d extends AnimatorListenerAdapter {
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            throw null;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void setAllowCollapse(boolean z12) {
    }
}
