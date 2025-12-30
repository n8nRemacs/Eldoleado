package androidx.appcompat.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowInsets;
import android.widget.OverScroller;
import androidx.annotation.RestrictTo;
import androidx.appcompat.view.menu.o;
import androidx.core.graphics.C22771k;
import androidx.core.view.C22823h0;
import androidx.core.view.J0;
import com.avito.android.R;
import com.avito.android.beduin.common.component.input.BeduinInputModel;

@SuppressLint({"UnknownNullness"})
@RestrictTo
/* loaded from: classes.dex */
public class ActionBarOverlayLayout extends ViewGroup implements T, androidx.core.view.K, androidx.core.view.I, androidx.core.view.J {

    /* renamed from: D, reason: collision with root package name */
    public static final int[] f21964D = {R.attr.actionBarSize, android.R.attr.windowContentOverlay};

    /* renamed from: E, reason: collision with root package name */
    public static final androidx.core.view.J0 f21965E;

    /* renamed from: F, reason: collision with root package name */
    public static final Rect f21966F;

    /* renamed from: A, reason: collision with root package name */
    public final Runnable f21967A;

    /* renamed from: B, reason: collision with root package name */
    public final androidx.core.view.L f21968B;

    /* renamed from: C, reason: collision with root package name */
    public final f f21969C;

    /* renamed from: b, reason: collision with root package name */
    public int f21970b;

    /* renamed from: c, reason: collision with root package name */
    public int f21971c;

    /* renamed from: d, reason: collision with root package name */
    public ContentFrameLayout f21972d;

    /* renamed from: e, reason: collision with root package name */
    public ActionBarContainer f21973e;

    /* renamed from: f, reason: collision with root package name */
    public U f21974f;

    /* renamed from: g, reason: collision with root package name */
    public Drawable f21975g;

    /* renamed from: h, reason: collision with root package name */
    public boolean f21976h;

    /* renamed from: i, reason: collision with root package name */
    public boolean f21977i;

    /* renamed from: j, reason: collision with root package name */
    public boolean f21978j;

    /* renamed from: k, reason: collision with root package name */
    public boolean f21979k;

    /* renamed from: l, reason: collision with root package name */
    public int f21980l;

    /* renamed from: m, reason: collision with root package name */
    public int f21981m;

    /* renamed from: n, reason: collision with root package name */
    public final Rect f21982n;

    /* renamed from: o, reason: collision with root package name */
    public final Rect f21983o;

    /* renamed from: p, reason: collision with root package name */
    public final Rect f21984p;

    /* renamed from: q, reason: collision with root package name */
    public final Rect f21985q;

    /* renamed from: r, reason: collision with root package name */
    @j.N
    public androidx.core.view.J0 f21986r;

    /* renamed from: s, reason: collision with root package name */
    @j.N
    public androidx.core.view.J0 f21987s;

    /* renamed from: t, reason: collision with root package name */
    @j.N
    public androidx.core.view.J0 f21988t;

    /* renamed from: u, reason: collision with root package name */
    @j.N
    public androidx.core.view.J0 f21989u;

    /* renamed from: v, reason: collision with root package name */
    public d f21990v;

    /* renamed from: w, reason: collision with root package name */
    public OverScroller f21991w;

    /* renamed from: x, reason: collision with root package name */
    public ViewPropertyAnimator f21992x;

    /* renamed from: y, reason: collision with root package name */
    public final AnimatorListenerAdapter f21993y;

    /* renamed from: z, reason: collision with root package name */
    public final Runnable f21994z;

    public class a extends AnimatorListenerAdapter {
        public a() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
            ActionBarOverlayLayout actionBarOverlayLayout = ActionBarOverlayLayout.this;
            actionBarOverlayLayout.f21992x = null;
            actionBarOverlayLayout.f21979k = false;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            ActionBarOverlayLayout actionBarOverlayLayout = ActionBarOverlayLayout.this;
            actionBarOverlayLayout.f21992x = null;
            actionBarOverlayLayout.f21979k = false;
        }
    }

    public class b implements Runnable {
        public b() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            ActionBarOverlayLayout actionBarOverlayLayout = ActionBarOverlayLayout.this;
            actionBarOverlayLayout.k();
            actionBarOverlayLayout.f21992x = actionBarOverlayLayout.f21973e.animate().translationY(0.0f).setListener(actionBarOverlayLayout.f21993y);
        }
    }

    public class c implements Runnable {
        public c() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            ActionBarOverlayLayout actionBarOverlayLayout = ActionBarOverlayLayout.this;
            actionBarOverlayLayout.k();
            actionBarOverlayLayout.f21992x = actionBarOverlayLayout.f21973e.animate().translationY(-actionBarOverlayLayout.f21973e.getHeight()).setListener(actionBarOverlayLayout.f21993y);
        }
    }

    public interface d {
        void a();

        void b(int i12);

        void c(boolean z12);

        void d();

        void e();
    }

    public static class e extends ViewGroup.MarginLayoutParams {
    }

    public static final class f extends View {
        @Override // android.view.View
        public final int getWindowSystemUiVisibility() {
            return 0;
        }
    }

    static {
        J0.b bVar = new J0.b();
        bVar.c(C22771k.b(0, 1, 0, 1));
        f21965E = bVar.a();
        f21966F = new Rect();
    }

    public ActionBarOverlayLayout(@j.N Context context, @j.P AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f21971c = 0;
        this.f21982n = new Rect();
        this.f21983o = new Rect();
        this.f21984p = new Rect();
        this.f21985q = new Rect();
        new Rect();
        new Rect();
        new Rect();
        new Rect();
        androidx.core.view.J0 j02 = androidx.core.view.J0.f44939b;
        this.f21986r = j02;
        this.f21987s = j02;
        this.f21988t = j02;
        this.f21989u = j02;
        this.f21993y = new a();
        this.f21994z = new b();
        this.f21967A = new c();
        l(context);
        this.f21968B = new androidx.core.view.L();
        f fVar = new f(context);
        fVar.setWillNotDraw(true);
        this.f21969C = fVar;
        addView(fVar);
    }

    public static boolean j(@j.N View view, @j.N Rect rect, boolean z12) {
        boolean z13;
        e eVar = (e) view.getLayoutParams();
        int i12 = ((ViewGroup.MarginLayoutParams) eVar).leftMargin;
        int i13 = rect.left;
        if (i12 != i13) {
            ((ViewGroup.MarginLayoutParams) eVar).leftMargin = i13;
            z13 = true;
        } else {
            z13 = false;
        }
        int i14 = ((ViewGroup.MarginLayoutParams) eVar).topMargin;
        int i15 = rect.top;
        if (i14 != i15) {
            ((ViewGroup.MarginLayoutParams) eVar).topMargin = i15;
            z13 = true;
        }
        int i16 = ((ViewGroup.MarginLayoutParams) eVar).rightMargin;
        int i17 = rect.right;
        if (i16 != i17) {
            ((ViewGroup.MarginLayoutParams) eVar).rightMargin = i17;
            z13 = true;
        }
        if (z12) {
            int i18 = ((ViewGroup.MarginLayoutParams) eVar).bottomMargin;
            int i19 = rect.bottom;
            if (i18 != i19) {
                ((ViewGroup.MarginLayoutParams) eVar).bottomMargin = i19;
                return true;
            }
        }
        return z13;
    }

    @Override // androidx.appcompat.widget.T
    public final boolean a() {
        m();
        return this.f21974f.a();
    }

    @Override // androidx.appcompat.widget.T
    public final boolean b() {
        m();
        return this.f21974f.b();
    }

    @Override // androidx.appcompat.widget.T
    public final boolean c() {
        m();
        return this.f21974f.c();
    }

    @Override // android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof e;
    }

    @Override // androidx.appcompat.widget.T
    public final boolean d() {
        m();
        return this.f21974f.d();
    }

    @Override // android.view.View
    public final void draw(@j.N Canvas canvas) {
        int translationY;
        super.draw(canvas);
        if (this.f21975g != null) {
            if (this.f21973e.getVisibility() == 0) {
                translationY = (int) (this.f21973e.getTranslationY() + this.f21973e.getBottom() + 0.5f);
            } else {
                translationY = 0;
            }
            this.f21975g.setBounds(0, translationY, getWidth(), this.f21975g.getIntrinsicHeight() + translationY);
            this.f21975g.draw(canvas);
        }
    }

    @Override // androidx.appcompat.widget.T
    public final boolean e() {
        m();
        return this.f21974f.e();
    }

    @Override // androidx.appcompat.widget.T
    public final void f(androidx.appcompat.view.menu.h hVar, o.a aVar) {
        m();
        this.f21974f.f(hVar, aVar);
    }

    @Override // android.view.View
    public final boolean fitSystemWindows(Rect rect) {
        return super.fitSystemWindows(rect);
    }

    @Override // androidx.appcompat.widget.T
    public final void g() {
        m();
        this.f21974f.g();
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new e(-1, -1);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new e(getContext(), attributeSet);
    }

    public int getActionBarHideOffset() {
        ActionBarContainer actionBarContainer = this.f21973e;
        if (actionBarContainer != null) {
            return -((int) actionBarContainer.getTranslationY());
        }
        return 0;
    }

    @Override // android.view.ViewGroup
    public int getNestedScrollAxes() {
        return this.f21968B.a();
    }

    public CharSequence getTitle() {
        m();
        return this.f21974f.getTitle();
    }

    @Override // androidx.appcompat.widget.T
    public final void h(int i12) {
        m();
        if (i12 == 2) {
            this.f21974f.getClass();
        } else if (i12 == 5) {
            this.f21974f.getClass();
        } else {
            if (i12 != 109) {
                return;
            }
            setOverlayMode(true);
        }
    }

    @Override // androidx.appcompat.widget.T
    public final void i() {
        m();
        this.f21974f.j();
    }

    public final void k() {
        removeCallbacks(this.f21994z);
        removeCallbacks(this.f21967A);
        ViewPropertyAnimator viewPropertyAnimator = this.f21992x;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
        }
    }

    public final void l(Context context) {
        TypedArray typedArrayObtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(f21964D);
        this.f21970b = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, 0);
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(1);
        this.f21975g = drawable;
        setWillNotDraw(drawable == null);
        typedArrayObtainStyledAttributes.recycle();
        this.f21991w = new OverScroller(context);
    }

    public final void m() {
        U wrapper;
        if (this.f21972d == null) {
            this.f21972d = (ContentFrameLayout) findViewById(R.id.action_bar_activity_content);
            this.f21973e = (ActionBarContainer) findViewById(R.id.action_bar_container);
            KeyEvent.Callback callbackFindViewById = findViewById(R.id.action_bar);
            if (callbackFindViewById instanceof U) {
                wrapper = (U) callbackFindViewById;
            } else {
                if (!(callbackFindViewById instanceof Toolbar)) {
                    throw new IllegalStateException("Can't make a decor toolbar out of ".concat(callbackFindViewById.getClass().getSimpleName()));
                }
                wrapper = ((Toolbar) callbackFindViewById).getWrapper();
            }
            this.f21974f = wrapper;
        }
    }

    @Override // android.view.View
    @j.X
    public final WindowInsets onApplyWindowInsets(@j.N WindowInsets windowInsets) {
        m();
        androidx.core.view.J0 j0S = androidx.core.view.J0.s(this, windowInsets);
        boolean zJ2 = j(this.f21973e, new Rect(j0S.i(), j0S.k(), j0S.j(), j0S.h()), false);
        Rect rect = this.f21982n;
        C22823h0.b(this, j0S, rect);
        androidx.core.view.J0 j0M = j0S.m(rect.left, rect.top, rect.right, rect.bottom);
        this.f21986r = j0M;
        boolean z12 = true;
        if (!this.f21987s.equals(j0M)) {
            this.f21987s = this.f21986r;
            zJ2 = true;
        }
        Rect rect2 = this.f21983o;
        if (rect2.equals(rect)) {
            z12 = zJ2;
        } else {
            rect2.set(rect);
        }
        if (z12) {
            requestLayout();
        }
        return j0S.a().c().b().r();
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        l(getContext());
        C22823h0.A(this);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        k();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z12, int i12, int i13, int i14, int i15) {
        int childCount = getChildCount();
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        for (int i16 = 0; i16 < childCount; i16++) {
            View childAt = getChildAt(i16);
            if (childAt.getVisibility() != 8) {
                e eVar = (e) childAt.getLayoutParams();
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                int i17 = ((ViewGroup.MarginLayoutParams) eVar).leftMargin + paddingLeft;
                int i18 = ((ViewGroup.MarginLayoutParams) eVar).topMargin + paddingTop;
                childAt.layout(i17, i18, measuredWidth + i17, measuredHeight + i18);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x00a8  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void onMeasure(int r12, int r13) {
        /*
            Method dump skipped, instructions count: 347
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ActionBarOverlayLayout.onMeasure(int, int):void");
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onNestedFling(View view, float f12, float f13, boolean z12) {
        if (!this.f21978j || !z12) {
            return false;
        }
        this.f21991w.fling(0, 0, 0, (int) f13, 0, 0, BeduinInputModel.MIN_TEXT_VERSION, Integer.MAX_VALUE);
        if (this.f21991w.getFinalY() > this.f21973e.getHeight()) {
            k();
            ((c) this.f21967A).run();
        } else {
            k();
            ((b) this.f21994z).run();
        }
        this.f21979k = true;
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onNestedPreFling(View view, float f12, float f13) {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedPreScroll(View view, int i12, int i13, int[] iArr) {
    }

    @Override // androidx.core.view.J
    public final void onNestedScroll(View view, int i12, int i13, int i14, int i15, int i16, int[] iArr) {
        onNestedScroll(view, i12, i13, i14, i15, i16);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedScrollAccepted(View view, View view2, int i12) {
        this.f21968B.f44968a = i12;
        this.f21980l = getActionBarHideOffset();
        k();
        d dVar = this.f21990v;
        if (dVar != null) {
            dVar.a();
        }
    }

    @Override // androidx.core.view.I
    public final boolean onStartNestedScroll(View view, View view2, int i12, int i13) {
        return i13 == 0 && onStartNestedScroll(view, view2, i12);
    }

    @Override // androidx.core.view.I
    public final void onStopNestedScroll(View view, int i12) {
        if (i12 == 0) {
            onStopNestedScroll(view);
        }
    }

    @Override // android.view.View
    @Deprecated
    public final void onWindowSystemUiVisibilityChanged(int i12) {
        super.onWindowSystemUiVisibilityChanged(i12);
        m();
        int i13 = this.f21981m ^ i12;
        this.f21981m = i12;
        boolean z12 = (i12 & 4) == 0;
        boolean z13 = (i12 & 256) != 0;
        d dVar = this.f21990v;
        if (dVar != null) {
            dVar.c(!z13);
            if (z12 || !z13) {
                this.f21990v.d();
            } else {
                this.f21990v.e();
            }
        }
        if ((i13 & 256) == 0 || this.f21990v == null) {
            return;
        }
        C22823h0.A(this);
    }

    @Override // android.view.View
    public final void onWindowVisibilityChanged(int i12) {
        super.onWindowVisibilityChanged(i12);
        this.f21971c = i12;
        d dVar = this.f21990v;
        if (dVar != null) {
            dVar.b(i12);
        }
    }

    public void setActionBarHideOffset(int i12) {
        k();
        this.f21973e.setTranslationY(-Math.max(0, Math.min(i12, this.f21973e.getHeight())));
    }

    public void setActionBarVisibilityCallback(d dVar) {
        this.f21990v = dVar;
        if (getWindowToken() != null) {
            this.f21990v.b(this.f21971c);
            int i12 = this.f21981m;
            if (i12 != 0) {
                onWindowSystemUiVisibilityChanged(i12);
                C22823h0.A(this);
            }
        }
    }

    public void setHasNonEmbeddedTabs(boolean z12) {
        this.f21977i = z12;
    }

    public void setHideOnContentScrollEnabled(boolean z12) {
        if (z12 != this.f21978j) {
            this.f21978j = z12;
            if (z12) {
                return;
            }
            k();
            setActionBarHideOffset(0);
        }
    }

    public void setIcon(int i12) {
        m();
        this.f21974f.setIcon(i12);
    }

    public void setLogo(int i12) {
        m();
        this.f21974f.p(i12);
    }

    public void setOverlayMode(boolean z12) {
        this.f21976h = z12;
    }

    @Override // androidx.appcompat.widget.T
    public void setWindowCallback(Window.Callback callback) {
        m();
        this.f21974f.setWindowCallback(callback);
    }

    @Override // androidx.appcompat.widget.T
    public void setWindowTitle(CharSequence charSequence) {
        m();
        this.f21974f.setWindowTitle(charSequence);
    }

    @Override // android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override // androidx.core.view.I
    public final void onNestedPreScroll(View view, int i12, int i13, int[] iArr, int i14) {
    }

    @Override // androidx.core.view.I
    public final void onNestedScroll(View view, int i12, int i13, int i14, int i15, int i16) {
        if (i16 == 0) {
            onNestedScroll(view, i12, i13, i14, i15);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onStartNestedScroll(View view, View view2, int i12) {
        if ((i12 & 2) == 0 || this.f21973e.getVisibility() != 0) {
            return false;
        }
        return this.f21978j;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onStopNestedScroll(View view) {
        if (!this.f21978j || this.f21979k) {
            return;
        }
        if (this.f21980l <= this.f21973e.getHeight()) {
            k();
            postDelayed(this.f21994z, 600L);
        } else {
            k();
            postDelayed(this.f21967A, 600L);
        }
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new e(layoutParams);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedScroll(View view, int i12, int i13, int i14, int i15) {
        int i16 = this.f21980l + i13;
        this.f21980l = i16;
        setActionBarHideOffset(i16);
    }

    public void setIcon(Drawable drawable) {
        m();
        this.f21974f.setIcon(drawable);
    }

    @Override // androidx.core.view.I
    public final void onNestedScrollAccepted(View view, View view2, int i12, int i13) {
        if (i13 == 0) {
            onNestedScrollAccepted(view, view2, i12);
        }
    }

    public void setShowingForActionMode(boolean z12) {
    }

    public void setUiOptions(int i12) {
    }
}
