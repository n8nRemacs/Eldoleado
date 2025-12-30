package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;
import androidx.annotation.RestrictTo;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.view.menu.h;
import androidx.appcompat.view.menu.o;
import androidx.appcompat.widget.ActionMenuPresenter;
import androidx.appcompat.widget.LinearLayoutCompat;
import com.avito.android.beduin.common.component.input.BeduinInputModel;

/* loaded from: classes.dex */
public class ActionMenuView extends LinearLayoutCompat implements h.b, androidx.appcompat.view.menu.p {

    /* renamed from: A, reason: collision with root package name */
    public final int f22022A;

    /* renamed from: B, reason: collision with root package name */
    public e f22023B;

    /* renamed from: q, reason: collision with root package name */
    public androidx.appcompat.view.menu.h f22024q;

    /* renamed from: r, reason: collision with root package name */
    public Context f22025r;

    /* renamed from: s, reason: collision with root package name */
    public int f22026s;

    /* renamed from: t, reason: collision with root package name */
    public boolean f22027t;

    /* renamed from: u, reason: collision with root package name */
    public ActionMenuPresenter f22028u;

    /* renamed from: v, reason: collision with root package name */
    public o.a f22029v;

    /* renamed from: w, reason: collision with root package name */
    public h.a f22030w;

    /* renamed from: x, reason: collision with root package name */
    public boolean f22031x;

    /* renamed from: y, reason: collision with root package name */
    public int f22032y;

    /* renamed from: z, reason: collision with root package name */
    public final int f22033z;

    @RestrictTo
    public interface a {
        boolean a();

        boolean i();
    }

    public static class c extends LinearLayoutCompat.b {

        /* renamed from: a, reason: collision with root package name */
        @ViewDebug.ExportedProperty
        public boolean f22034a;

        /* renamed from: b, reason: collision with root package name */
        @ViewDebug.ExportedProperty
        public int f22035b;

        /* renamed from: c, reason: collision with root package name */
        @ViewDebug.ExportedProperty
        public int f22036c;

        /* renamed from: d, reason: collision with root package name */
        @ViewDebug.ExportedProperty
        public boolean f22037d;

        /* renamed from: e, reason: collision with root package name */
        @ViewDebug.ExportedProperty
        public boolean f22038e;

        /* renamed from: f, reason: collision with root package name */
        public boolean f22039f;
    }

    public class d implements h.a {
        public d() {
        }

        @Override // androidx.appcompat.view.menu.h.a
        public final void a(@j.N androidx.appcompat.view.menu.h hVar) {
            h.a aVar = ActionMenuView.this.f22030w;
            if (aVar != null) {
                aVar.a(hVar);
            }
        }

        @Override // androidx.appcompat.view.menu.h.a
        public final boolean b(@j.N androidx.appcompat.view.menu.h hVar, @j.N MenuItem menuItem) {
            e eVar = ActionMenuView.this.f22023B;
            return eVar != null && eVar.onMenuItemClick(menuItem);
        }
    }

    public interface e {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    public ActionMenuView(@j.N Context context, @j.P AttributeSet attributeSet) {
        super(context, attributeSet);
        setBaselineAligned(false);
        float f12 = context.getResources().getDisplayMetrics().density;
        this.f22033z = (int) (56.0f * f12);
        this.f22022A = (int) (f12 * 4.0f);
        this.f22025r = context;
        this.f22026s = 0;
    }

    public static c n() {
        c cVar = new c(-2, -2);
        cVar.f22034a = false;
        ((LinearLayout.LayoutParams) cVar).gravity = 16;
        return cVar;
    }

    public static c o(ViewGroup.LayoutParams layoutParams) {
        c cVar;
        if (layoutParams == null) {
            return n();
        }
        if (layoutParams instanceof c) {
            c cVar2 = (c) layoutParams;
            cVar = new c(cVar2);
            cVar.f22034a = cVar2.f22034a;
        } else {
            cVar = new c(layoutParams);
        }
        if (((LinearLayout.LayoutParams) cVar).gravity <= 0) {
            ((LinearLayout.LayoutParams) cVar).gravity = 16;
        }
        return cVar;
    }

    @Override // androidx.appcompat.view.menu.p
    @RestrictTo
    public final void b(androidx.appcompat.view.menu.h hVar) {
        this.f22024q = hVar;
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof c;
    }

    @Override // android.view.View
    public final boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return false;
    }

    @Override // androidx.appcompat.view.menu.h.b
    @RestrictTo
    public final boolean e(androidx.appcompat.view.menu.k kVar) {
        return this.f22024q.q(kVar, null, 0);
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup
    public final /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return n();
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup
    public final /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return o(layoutParams);
    }

    public Menu getMenu() {
        if (this.f22024q == null) {
            Context context = getContext();
            androidx.appcompat.view.menu.h hVar = new androidx.appcompat.view.menu.h(context);
            this.f22024q = hVar;
            hVar.f21835e = new d();
            ActionMenuPresenter actionMenuPresenter = new ActionMenuPresenter(context);
            this.f22028u = actionMenuPresenter;
            actionMenuPresenter.f22001m = true;
            actionMenuPresenter.f22002n = true;
            o.a bVar = this.f22029v;
            if (bVar == null) {
                bVar = new b();
            }
            actionMenuPresenter.f21774f = bVar;
            this.f22024q.b(this.f22028u, this.f22025r);
            ActionMenuPresenter actionMenuPresenter2 = this.f22028u;
            actionMenuPresenter2.f21777i = this;
            this.f22024q = actionMenuPresenter2.f21772d;
        }
        return this.f22024q;
    }

    @j.P
    public Drawable getOverflowIcon() {
        getMenu();
        ActionMenuPresenter actionMenuPresenter = this.f22028u;
        ActionMenuPresenter.d dVar = actionMenuPresenter.f21998j;
        if (dVar != null) {
            return dVar.getDrawable();
        }
        if (actionMenuPresenter.f22000l) {
            return actionMenuPresenter.f21999k;
        }
        return null;
    }

    public int getPopupTheme() {
        return this.f22026s;
    }

    @RestrictTo
    public int getWindowAnimations() {
        return 0;
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat
    /* renamed from: j */
    public final /* bridge */ /* synthetic */ LinearLayoutCompat.b generateDefaultLayoutParams() {
        return n();
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat
    /* renamed from: k */
    public final LinearLayoutCompat.b generateLayoutParams(AttributeSet attributeSet) {
        return new c(getContext(), attributeSet);
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat
    /* renamed from: l */
    public final /* bridge */ /* synthetic */ LinearLayoutCompat.b generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return o(layoutParams);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ActionMenuPresenter actionMenuPresenter = this.f22028u;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.c(false);
            if (this.f22028u.n()) {
                this.f22028u.m();
                this.f22028u.p();
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ActionMenuPresenter actionMenuPresenter = this.f22028u;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.m();
            ActionMenuPresenter.a aVar = actionMenuPresenter.f22009u;
            if (aVar == null || !aVar.b()) {
                return;
            }
            aVar.f21907i.dismiss();
        }
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z12, int i12, int i13, int i14, int i15) {
        int width;
        int paddingLeft;
        if (!this.f22031x) {
            super.onLayout(z12, i12, i13, i14, i15);
            return;
        }
        int childCount = getChildCount();
        int i16 = (i15 - i13) / 2;
        int dividerWidth = getDividerWidth();
        int i17 = i14 - i12;
        int paddingRight = (i17 - getPaddingRight()) - getPaddingLeft();
        boolean z13 = getLayoutDirection() == 1;
        int i18 = 0;
        int i19 = 0;
        for (int i22 = 0; i22 < childCount; i22++) {
            View childAt = getChildAt(i22);
            if (childAt.getVisibility() != 8) {
                c cVar = (c) childAt.getLayoutParams();
                if (cVar.f22034a) {
                    int measuredWidth = childAt.getMeasuredWidth();
                    if (p(i22)) {
                        measuredWidth += dividerWidth;
                    }
                    int measuredHeight = childAt.getMeasuredHeight();
                    if (z13) {
                        paddingLeft = getPaddingLeft() + ((LinearLayout.LayoutParams) cVar).leftMargin;
                        width = paddingLeft + measuredWidth;
                    } else {
                        width = (getWidth() - getPaddingRight()) - ((LinearLayout.LayoutParams) cVar).rightMargin;
                        paddingLeft = width - measuredWidth;
                    }
                    int i23 = i16 - (measuredHeight / 2);
                    childAt.layout(paddingLeft, i23, width, measuredHeight + i23);
                    paddingRight -= measuredWidth;
                    i18 = 1;
                } else {
                    paddingRight -= (childAt.getMeasuredWidth() + ((LinearLayout.LayoutParams) cVar).leftMargin) + ((LinearLayout.LayoutParams) cVar).rightMargin;
                    p(i22);
                    i19++;
                }
            }
        }
        if (childCount == 1 && i18 == 0) {
            View childAt2 = getChildAt(0);
            int measuredWidth2 = childAt2.getMeasuredWidth();
            int measuredHeight2 = childAt2.getMeasuredHeight();
            int i24 = (i17 / 2) - (measuredWidth2 / 2);
            int i25 = i16 - (measuredHeight2 / 2);
            childAt2.layout(i24, i25, measuredWidth2 + i24, measuredHeight2 + i25);
            return;
        }
        int i26 = i19 - (i18 ^ 1);
        int iMax = Math.max(0, i26 > 0 ? paddingRight / i26 : 0);
        if (z13) {
            int width2 = getWidth() - getPaddingRight();
            for (int i27 = 0; i27 < childCount; i27++) {
                View childAt3 = getChildAt(i27);
                c cVar2 = (c) childAt3.getLayoutParams();
                if (childAt3.getVisibility() != 8 && !cVar2.f22034a) {
                    int i28 = width2 - ((LinearLayout.LayoutParams) cVar2).rightMargin;
                    int measuredWidth3 = childAt3.getMeasuredWidth();
                    int measuredHeight3 = childAt3.getMeasuredHeight();
                    int i29 = i16 - (measuredHeight3 / 2);
                    childAt3.layout(i28 - measuredWidth3, i29, i28, measuredHeight3 + i29);
                    width2 = i28 - ((measuredWidth3 + ((LinearLayout.LayoutParams) cVar2).leftMargin) + iMax);
                }
            }
            return;
        }
        int paddingLeft2 = getPaddingLeft();
        for (int i32 = 0; i32 < childCount; i32++) {
            View childAt4 = getChildAt(i32);
            c cVar3 = (c) childAt4.getLayoutParams();
            if (childAt4.getVisibility() != 8 && !cVar3.f22034a) {
                int i33 = paddingLeft2 + ((LinearLayout.LayoutParams) cVar3).leftMargin;
                int measuredWidth4 = childAt4.getMeasuredWidth();
                int measuredHeight4 = childAt4.getMeasuredHeight();
                int i34 = i16 - (measuredHeight4 / 2);
                childAt4.layout(i33, i34, i33 + measuredWidth4, measuredHeight4 + i34);
                paddingLeft2 = measuredWidth4 + ((LinearLayout.LayoutParams) cVar3).rightMargin + iMax + i33;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r4v28 */
    /* JADX WARN: Type inference failed for: r4v29, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r4v31 */
    /* JADX WARN: Type inference failed for: r4v36 */
    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.View
    public final void onMeasure(int i12, int i13) {
        int i14;
        boolean z12;
        int i15;
        boolean z13;
        int i16;
        int i17;
        int i18;
        ?? r42;
        int i19;
        int i22;
        int i23;
        androidx.appcompat.view.menu.h hVar;
        boolean z14 = this.f22031x;
        boolean z15 = View.MeasureSpec.getMode(i12) == 1073741824;
        this.f22031x = z15;
        if (z14 != z15) {
            this.f22032y = 0;
        }
        int size = View.MeasureSpec.getSize(i12);
        if (this.f22031x && (hVar = this.f22024q) != null && size != this.f22032y) {
            this.f22032y = size;
            hVar.p(true);
        }
        int childCount = getChildCount();
        if (!this.f22031x || childCount <= 0) {
            for (int i24 = 0; i24 < childCount; i24++) {
                c cVar = (c) getChildAt(i24).getLayoutParams();
                ((LinearLayout.LayoutParams) cVar).rightMargin = 0;
                ((LinearLayout.LayoutParams) cVar).leftMargin = 0;
            }
            super.onMeasure(i12, i13);
            return;
        }
        int mode = View.MeasureSpec.getMode(i13);
        int size2 = View.MeasureSpec.getSize(i12);
        int size3 = View.MeasureSpec.getSize(i13);
        int paddingRight = getPaddingRight() + getPaddingLeft();
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i13, paddingBottom, -2);
        int i25 = size2 - paddingRight;
        int i26 = this.f22033z;
        int i27 = i25 / i26;
        int i28 = i25 % i26;
        if (i27 == 0) {
            setMeasuredDimension(i25, 0);
            return;
        }
        int i29 = (i28 / i27) + i26;
        int childCount2 = getChildCount();
        int iMax = 0;
        int i32 = 0;
        int iMax2 = 0;
        int i33 = 0;
        boolean z16 = false;
        int i34 = 0;
        long j12 = 0;
        while (true) {
            i14 = this.f22022A;
            if (i33 >= childCount2) {
                break;
            }
            View childAt = getChildAt(i33);
            int i35 = size3;
            int i36 = i25;
            if (childAt.getVisibility() == 8) {
                i19 = mode;
                i22 = paddingBottom;
            } else {
                boolean z17 = childAt instanceof ActionMenuItemView;
                int i37 = i32 + 1;
                if (z17) {
                    childAt.setPadding(i14, 0, i14, 0);
                }
                c cVar2 = (c) childAt.getLayoutParams();
                cVar2.f22039f = false;
                cVar2.f22036c = 0;
                cVar2.f22035b = 0;
                cVar2.f22037d = false;
                ((LinearLayout.LayoutParams) cVar2).leftMargin = 0;
                ((LinearLayout.LayoutParams) cVar2).rightMargin = 0;
                cVar2.f22038e = z17 && !TextUtils.isEmpty(((ActionMenuItemView) childAt).getText());
                int i38 = cVar2.f22034a ? 1 : i27;
                c cVar3 = (c) childAt.getLayoutParams();
                i19 = mode;
                i22 = paddingBottom;
                int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(childMeasureSpec) - paddingBottom, View.MeasureSpec.getMode(childMeasureSpec));
                ActionMenuItemView actionMenuItemView = z17 ? (ActionMenuItemView) childAt : null;
                boolean z18 = (actionMenuItemView == null || TextUtils.isEmpty(actionMenuItemView.getText())) ? false : true;
                if (i38 <= 0 || (z18 && i38 < 2)) {
                    i23 = 0;
                } else {
                    childAt.measure(View.MeasureSpec.makeMeasureSpec(i38 * i29, BeduinInputModel.MIN_TEXT_VERSION), iMakeMeasureSpec);
                    int measuredWidth = childAt.getMeasuredWidth();
                    i23 = measuredWidth / i29;
                    if (measuredWidth % i29 != 0) {
                        i23++;
                    }
                    if (z18 && i23 < 2) {
                        i23 = 2;
                    }
                }
                cVar3.f22037d = !cVar3.f22034a && z18;
                cVar3.f22035b = i23;
                childAt.measure(View.MeasureSpec.makeMeasureSpec(i23 * i29, 1073741824), iMakeMeasureSpec);
                iMax2 = Math.max(iMax2, i23);
                if (cVar2.f22037d) {
                    i34++;
                }
                if (cVar2.f22034a) {
                    z16 = true;
                }
                i27 -= i23;
                iMax = Math.max(iMax, childAt.getMeasuredHeight());
                if (i23 == 1) {
                    j12 |= 1 << i33;
                }
                i32 = i37;
            }
            i33++;
            size3 = i35;
            i25 = i36;
            paddingBottom = i22;
            mode = i19;
        }
        int i39 = mode;
        int i42 = i25;
        int i43 = size3;
        boolean z19 = z16 && i32 == 2;
        boolean z22 = false;
        while (i34 > 0 && i27 > 0) {
            int i44 = Integer.MAX_VALUE;
            int i45 = 0;
            int i46 = 0;
            long j13 = 0;
            while (i46 < childCount2) {
                c cVar4 = (c) getChildAt(i46).getLayoutParams();
                boolean z23 = z22;
                if (cVar4.f22037d) {
                    int i47 = cVar4.f22035b;
                    if (i47 < i44) {
                        j13 = 1 << i46;
                        i44 = i47;
                        i45 = 1;
                    } else if (i47 == i44) {
                        j13 |= 1 << i46;
                        i45++;
                    }
                }
                i46++;
                z22 = z23;
            }
            z12 = z22;
            j12 |= j13;
            if (i45 > i27) {
                break;
            }
            int i48 = i44 + 1;
            int i49 = 0;
            while (i49 < childCount2) {
                View childAt2 = getChildAt(i49);
                c cVar5 = (c) childAt2.getLayoutParams();
                int i52 = iMax;
                int i53 = childMeasureSpec;
                int i54 = childCount2;
                long j14 = 1 << i49;
                if ((j13 & j14) != 0) {
                    if (z19 && cVar5.f22038e) {
                        r42 = 1;
                        r42 = 1;
                        if (i27 == 1) {
                            childAt2.setPadding(i14 + i29, 0, i14, 0);
                        }
                    } else {
                        r42 = 1;
                    }
                    cVar5.f22035b += r42;
                    cVar5.f22039f = r42;
                    i27--;
                } else if (cVar5.f22035b == i48) {
                    j12 |= j14;
                }
                i49++;
                childMeasureSpec = i53;
                iMax = i52;
                childCount2 = i54;
            }
            z22 = true;
        }
        z12 = z22;
        int i55 = iMax;
        int i56 = childMeasureSpec;
        int i57 = childCount2;
        boolean z24 = !z16 && i32 == 1;
        if (i27 <= 0 || j12 == 0 || (i27 >= i32 - 1 && !z24 && iMax2 <= 1)) {
            i15 = i57;
            z13 = z12;
        } else {
            float fBitCount = Long.bitCount(j12);
            if (!z24) {
                if ((j12 & 1) != 0 && !((c) getChildAt(0).getLayoutParams()).f22038e) {
                    fBitCount -= 0.5f;
                }
                int i58 = i57 - 1;
                if ((j12 & (1 << i58)) != 0 && !((c) getChildAt(i58).getLayoutParams()).f22038e) {
                    fBitCount -= 0.5f;
                }
            }
            int i59 = fBitCount > 0.0f ? (int) ((i27 * i29) / fBitCount) : 0;
            boolean z25 = z12;
            i15 = i57;
            for (int i62 = 0; i62 < i15; i62++) {
                if ((j12 & (1 << i62)) != 0) {
                    View childAt3 = getChildAt(i62);
                    c cVar6 = (c) childAt3.getLayoutParams();
                    if (childAt3 instanceof ActionMenuItemView) {
                        cVar6.f22036c = i59;
                        cVar6.f22039f = true;
                        if (i62 == 0 && !cVar6.f22038e) {
                            ((LinearLayout.LayoutParams) cVar6).leftMargin = (-i59) / 2;
                        }
                        z25 = true;
                    } else {
                        if (cVar6.f22034a) {
                            cVar6.f22036c = i59;
                            cVar6.f22039f = true;
                            ((LinearLayout.LayoutParams) cVar6).rightMargin = (-i59) / 2;
                            z25 = true;
                        } else {
                            if (i62 != 0) {
                                ((LinearLayout.LayoutParams) cVar6).leftMargin = i59 / 2;
                            }
                            if (i62 != i15 - 1) {
                                ((LinearLayout.LayoutParams) cVar6).rightMargin = i59 / 2;
                            }
                        }
                    }
                }
            }
            z13 = z25;
        }
        if (z13) {
            int i63 = 0;
            while (i63 < i15) {
                View childAt4 = getChildAt(i63);
                c cVar7 = (c) childAt4.getLayoutParams();
                if (cVar7.f22039f) {
                    i18 = i56;
                    childAt4.measure(View.MeasureSpec.makeMeasureSpec((cVar7.f22035b * i29) + cVar7.f22036c, 1073741824), i18);
                } else {
                    i18 = i56;
                }
                i63++;
                i56 = i18;
            }
        }
        if (i39 != 1073741824) {
            i17 = i42;
            i16 = i55;
        } else {
            i16 = i43;
            i17 = i42;
        }
        setMeasuredDimension(i17, i16);
    }

    @RestrictTo
    public final boolean p(int i12) {
        boolean zI2 = false;
        if (i12 == 0) {
            return false;
        }
        KeyEvent.Callback childAt = getChildAt(i12 - 1);
        KeyEvent.Callback childAt2 = getChildAt(i12);
        if (i12 < getChildCount() && (childAt instanceof a)) {
            zI2 = ((a) childAt).i();
        }
        return (i12 <= 0 || !(childAt2 instanceof a)) ? zI2 : zI2 | ((a) childAt2).a();
    }

    @RestrictTo
    public void setExpandedActionViewsExclusive(boolean z12) {
        this.f22028u.f22006r = z12;
    }

    public void setOnMenuItemClickListener(e eVar) {
        this.f22023B = eVar;
    }

    public void setOverflowIcon(@j.P Drawable drawable) {
        getMenu();
        ActionMenuPresenter actionMenuPresenter = this.f22028u;
        ActionMenuPresenter.d dVar = actionMenuPresenter.f21998j;
        if (dVar != null) {
            dVar.setImageDrawable(drawable);
        } else {
            actionMenuPresenter.f22000l = true;
            actionMenuPresenter.f21999k = drawable;
        }
    }

    @RestrictTo
    public void setOverflowReserved(boolean z12) {
        this.f22027t = z12;
    }

    public void setPopupTheme(@j.f0 int i12) {
        if (this.f22026s != i12) {
            this.f22026s = i12;
            if (i12 == 0) {
                this.f22025r = getContext();
            } else {
                this.f22025r = new ContextThemeWrapper(getContext(), i12);
            }
        }
    }

    @RestrictTo
    public void setPresenter(ActionMenuPresenter actionMenuPresenter) {
        this.f22028u = actionMenuPresenter;
        actionMenuPresenter.f21777i = this;
        this.f22024q = actionMenuPresenter.f21772d;
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new c(getContext(), attributeSet);
    }

    public static class b implements o.a {
        @Override // androidx.appcompat.view.menu.o.a
        public final boolean c(@j.N androidx.appcompat.view.menu.h hVar) {
            return false;
        }

        @Override // androidx.appcompat.view.menu.o.a
        public final void b(@j.N androidx.appcompat.view.menu.h hVar, boolean z12) {
        }
    }
}
