package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.RestrictTo;
import com.avito.android.R;
import com.avito.android.beduin.common.component.input.BeduinInputModel;
import l.C43521a;

@RestrictTo
/* loaded from: classes.dex */
public class ActionBarContainer extends FrameLayout {

    /* renamed from: b, reason: collision with root package name */
    public boolean f21941b;

    /* renamed from: c, reason: collision with root package name */
    public q0 f21942c;

    /* renamed from: d, reason: collision with root package name */
    public View f21943d;

    /* renamed from: e, reason: collision with root package name */
    public View f21944e;

    /* renamed from: f, reason: collision with root package name */
    public Drawable f21945f;

    /* renamed from: g, reason: collision with root package name */
    public Drawable f21946g;

    /* renamed from: h, reason: collision with root package name */
    public Drawable f21947h;

    /* renamed from: i, reason: collision with root package name */
    public final boolean f21948i;

    /* renamed from: j, reason: collision with root package name */
    public boolean f21949j;

    /* renamed from: k, reason: collision with root package name */
    public final int f21950k;

    @j.X
    public static class a {
    }

    public ActionBarContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setBackground(new C19941c(this));
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C43521a.m.f413352a);
        boolean z12 = false;
        this.f21945f = typedArrayObtainStyledAttributes.getDrawable(0);
        this.f21946g = typedArrayObtainStyledAttributes.getDrawable(2);
        this.f21950k = typedArrayObtainStyledAttributes.getDimensionPixelSize(13, -1);
        if (getId() == R.id.split_action_bar) {
            this.f21948i = true;
            this.f21947h = typedArrayObtainStyledAttributes.getDrawable(1);
        }
        typedArrayObtainStyledAttributes.recycle();
        if (!this.f21948i ? !(this.f21945f != null || this.f21946g != null) : this.f21947h == null) {
            z12 = true;
        }
        setWillNotDraw(z12);
    }

    public static int a(View view) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        return view.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.f21945f;
        if (drawable != null && drawable.isStateful()) {
            this.f21945f.setState(getDrawableState());
        }
        Drawable drawable2 = this.f21946g;
        if (drawable2 != null && drawable2.isStateful()) {
            this.f21946g.setState(getDrawableState());
        }
        Drawable drawable3 = this.f21947h;
        if (drawable3 == null || !drawable3.isStateful()) {
            return;
        }
        this.f21947h.setState(getDrawableState());
    }

    public View getTabContainer() {
        return this.f21942c;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.f21945f;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
        Drawable drawable2 = this.f21946g;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
        }
        Drawable drawable3 = this.f21947h;
        if (drawable3 != null) {
            drawable3.jumpToCurrentState();
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.f21943d = findViewById(R.id.action_bar);
        this.f21944e = findViewById(R.id.action_context_bar);
    }

    @Override // android.view.View
    public final boolean onHoverEvent(MotionEvent motionEvent) {
        super.onHoverEvent(motionEvent);
        return true;
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.f21941b || super.onInterceptTouchEvent(motionEvent);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0048 A[PHI: r0
  0x0048: PHI (r0v8 boolean) = (r0v1 boolean), (r0v1 boolean), (r0v0 boolean) binds: [B:31:0x00a5, B:33:0x00a9, B:15:0x0039] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void onLayout(boolean r5, int r6, int r7, int r8, int r9) {
        /*
            r4 = this;
            super.onLayout(r5, r6, r7, r8, r9)
            androidx.appcompat.widget.q0 r5 = r4.f21942c
            r7 = 8
            r9 = 1
            r0 = 0
            if (r5 == 0) goto L13
            int r1 = r5.getVisibility()
            if (r1 == r7) goto L13
            r1 = r9
            goto L14
        L13:
            r1 = r0
        L14:
            if (r5 == 0) goto L33
            int r2 = r5.getVisibility()
            if (r2 == r7) goto L33
            int r7 = r4.getMeasuredHeight()
            android.view.ViewGroup$LayoutParams r2 = r5.getLayoutParams()
            android.widget.FrameLayout$LayoutParams r2 = (android.widget.FrameLayout.LayoutParams) r2
            int r3 = r5.getMeasuredHeight()
            int r3 = r7 - r3
            int r2 = r2.bottomMargin
            int r3 = r3 - r2
            int r7 = r7 - r2
            r5.layout(r6, r3, r8, r7)
        L33:
            boolean r6 = r4.f21948i
            if (r6 == 0) goto L4b
            android.graphics.drawable.Drawable r5 = r4.f21947h
            if (r5 == 0) goto L48
            int r6 = r4.getMeasuredWidth()
            int r7 = r4.getMeasuredHeight()
            r5.setBounds(r0, r0, r6, r7)
            goto Lbe
        L48:
            r9 = r0
            goto Lbe
        L4b:
            android.graphics.drawable.Drawable r6 = r4.f21945f
            if (r6 == 0) goto La3
            android.view.View r6 = r4.f21943d
            int r6 = r6.getVisibility()
            if (r6 != 0) goto L75
            android.graphics.drawable.Drawable r6 = r4.f21945f
            android.view.View r7 = r4.f21943d
            int r7 = r7.getLeft()
            android.view.View r8 = r4.f21943d
            int r8 = r8.getTop()
            android.view.View r0 = r4.f21943d
            int r0 = r0.getRight()
            android.view.View r2 = r4.f21943d
            int r2 = r2.getBottom()
            r6.setBounds(r7, r8, r0, r2)
            goto La2
        L75:
            android.view.View r6 = r4.f21944e
            if (r6 == 0) goto L9d
            int r6 = r6.getVisibility()
            if (r6 != 0) goto L9d
            android.graphics.drawable.Drawable r6 = r4.f21945f
            android.view.View r7 = r4.f21944e
            int r7 = r7.getLeft()
            android.view.View r8 = r4.f21944e
            int r8 = r8.getTop()
            android.view.View r0 = r4.f21944e
            int r0 = r0.getRight()
            android.view.View r2 = r4.f21944e
            int r2 = r2.getBottom()
            r6.setBounds(r7, r8, r0, r2)
            goto La2
        L9d:
            android.graphics.drawable.Drawable r6 = r4.f21945f
            r6.setBounds(r0, r0, r0, r0)
        La2:
            r0 = r9
        La3:
            r4.f21949j = r1
            if (r1 == 0) goto L48
            android.graphics.drawable.Drawable r6 = r4.f21946g
            if (r6 == 0) goto L48
            int r7 = r5.getLeft()
            int r8 = r5.getTop()
            int r0 = r5.getRight()
            int r5 = r5.getBottom()
            r6.setBounds(r7, r8, r0, r5)
        Lbe:
            if (r9 == 0) goto Lc3
            r4.invalidate()
        Lc3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ActionBarContainer.onLayout(boolean, int, int, int, int):void");
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i12, int i13) {
        int iA2;
        int i14;
        if (this.f21943d == null && View.MeasureSpec.getMode(i13) == Integer.MIN_VALUE && (i14 = this.f21950k) >= 0) {
            i13 = View.MeasureSpec.makeMeasureSpec(Math.min(i14, View.MeasureSpec.getSize(i13)), BeduinInputModel.MIN_TEXT_VERSION);
        }
        super.onMeasure(i12, i13);
        if (this.f21943d == null) {
            return;
        }
        int mode = View.MeasureSpec.getMode(i13);
        q0 q0Var = this.f21942c;
        if (q0Var == null || q0Var.getVisibility() == 8 || mode == 1073741824) {
            return;
        }
        View view = this.f21943d;
        if (view == null || view.getVisibility() == 8 || view.getMeasuredHeight() == 0) {
            View view2 = this.f21944e;
            iA2 = (view2 == null || view2.getVisibility() == 8 || view2.getMeasuredHeight() == 0) ? 0 : a(this.f21944e);
        } else {
            iA2 = a(this.f21943d);
        }
        setMeasuredDimension(getMeasuredWidth(), Math.min(a(this.f21942c) + iA2, mode == Integer.MIN_VALUE ? View.MeasureSpec.getSize(i13) : Integer.MAX_VALUE));
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    public void setPrimaryBackground(Drawable drawable) {
        Drawable drawable2 = this.f21945f;
        if (drawable2 != null) {
            drawable2.setCallback(null);
            unscheduleDrawable(this.f21945f);
        }
        this.f21945f = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            View view = this.f21943d;
            if (view != null) {
                this.f21945f.setBounds(view.getLeft(), this.f21943d.getTop(), this.f21943d.getRight(), this.f21943d.getBottom());
            }
        }
        boolean z12 = false;
        if (!this.f21948i ? !(this.f21945f != null || this.f21946g != null) : this.f21947h == null) {
            z12 = true;
        }
        setWillNotDraw(z12);
        invalidate();
        invalidateOutline();
    }

    public void setSplitBackground(Drawable drawable) {
        Drawable drawable2;
        Drawable drawable3 = this.f21947h;
        if (drawable3 != null) {
            drawable3.setCallback(null);
            unscheduleDrawable(this.f21947h);
        }
        this.f21947h = drawable;
        boolean z12 = this.f21948i;
        boolean z13 = false;
        if (drawable != null) {
            drawable.setCallback(this);
            if (z12 && (drawable2 = this.f21947h) != null) {
                drawable2.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
            }
        }
        if (!z12 ? !(this.f21945f != null || this.f21946g != null) : this.f21947h == null) {
            z13 = true;
        }
        setWillNotDraw(z13);
        invalidate();
        invalidateOutline();
    }

    public void setStackedBackground(Drawable drawable) {
        Drawable drawable2;
        Drawable drawable3 = this.f21946g;
        if (drawable3 != null) {
            drawable3.setCallback(null);
            unscheduleDrawable(this.f21946g);
        }
        this.f21946g = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            if (this.f21949j && (drawable2 = this.f21946g) != null) {
                drawable2.setBounds(this.f21942c.getLeft(), this.f21942c.getTop(), this.f21942c.getRight(), this.f21942c.getBottom());
            }
        }
        boolean z12 = false;
        if (!this.f21948i ? !(this.f21945f != null || this.f21946g != null) : this.f21947h == null) {
            z12 = true;
        }
        setWillNotDraw(z12);
        invalidate();
        invalidateOutline();
    }

    public void setTabContainer(q0 q0Var) {
        q0 q0Var2 = this.f21942c;
        if (q0Var2 != null) {
            removeView(q0Var2);
        }
        this.f21942c = q0Var;
        if (q0Var != null) {
            addView(q0Var);
            ViewGroup.LayoutParams layoutParams = q0Var.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = -2;
            q0Var.setAllowCollapse(false);
        }
    }

    public void setTransitioning(boolean z12) {
        this.f21941b = z12;
        setDescendantFocusability(z12 ? 393216 : 262144);
    }

    @Override // android.view.View
    public void setVisibility(int i12) {
        super.setVisibility(i12);
        boolean z12 = i12 == 0;
        Drawable drawable = this.f21945f;
        if (drawable != null) {
            drawable.setVisible(z12, false);
        }
        Drawable drawable2 = this.f21946g;
        if (drawable2 != null) {
            drawable2.setVisible(z12, false);
        }
        Drawable drawable3 = this.f21947h;
        if (drawable3 != null) {
            drawable3.setVisible(z12, false);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final ActionMode startActionModeForChild(View view, ActionMode.Callback callback) {
        return null;
    }

    @Override // android.view.View
    public final boolean verifyDrawable(@j.N Drawable drawable) {
        Drawable drawable2 = this.f21945f;
        boolean z12 = this.f21948i;
        return (drawable == drawable2 && !z12) || (drawable == this.f21946g && this.f21949j) || ((drawable == this.f21947h && z12) || super.verifyDrawable(drawable));
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final ActionMode startActionModeForChild(View view, ActionMode.Callback callback, int i12) {
        if (i12 != 0) {
            return super.startActionModeForChild(view, callback, i12);
        }
        return null;
    }
}
