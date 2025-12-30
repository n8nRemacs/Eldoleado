package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.annotation.RestrictTo;
import androidx.appcompat.app.AbstractC19927a;
import androidx.appcompat.view.menu.h;
import androidx.appcompat.view.menu.o;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.K0;
import androidx.core.view.C22823h0;
import androidx.core.view.C22843w;
import androidx.core.view.InterfaceC22842v;
import androidx.customview.view.AbsSavedState;
import com.avito.android.R;
import com.avito.android.beduin.common.component.input.BeduinInputModel;
import j.InterfaceC42156l;
import j.InterfaceC42164u;
import j.InterfaceC42165v;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import l.C43521a;
import m.C43852a;

/* loaded from: classes.dex */
public class Toolbar extends ViewGroup implements InterfaceC22842v {

    /* renamed from: A, reason: collision with root package name */
    public ColorStateList f22368A;

    /* renamed from: B, reason: collision with root package name */
    public ColorStateList f22369B;

    /* renamed from: C, reason: collision with root package name */
    public boolean f22370C;

    /* renamed from: D, reason: collision with root package name */
    public boolean f22371D;

    /* renamed from: E, reason: collision with root package name */
    public final ArrayList<View> f22372E;

    /* renamed from: F, reason: collision with root package name */
    public final ArrayList<View> f22373F;

    /* renamed from: G, reason: collision with root package name */
    public final int[] f22374G;

    /* renamed from: H, reason: collision with root package name */
    public final C22843w f22375H;

    /* renamed from: I, reason: collision with root package name */
    public ArrayList<MenuItem> f22376I;

    /* renamed from: J, reason: collision with root package name */
    public h f22377J;

    /* renamed from: K, reason: collision with root package name */
    public final ActionMenuView.e f22378K;

    /* renamed from: L, reason: collision with root package name */
    public J0 f22379L;

    /* renamed from: M, reason: collision with root package name */
    public ActionMenuPresenter f22380M;

    /* renamed from: N, reason: collision with root package name */
    public f f22381N;

    /* renamed from: O, reason: collision with root package name */
    public o.a f22382O;

    /* renamed from: P, reason: collision with root package name */
    public h.a f22383P;

    /* renamed from: Q, reason: collision with root package name */
    public boolean f22384Q;

    /* renamed from: R, reason: collision with root package name */
    public OnBackInvokedCallback f22385R;

    /* renamed from: S, reason: collision with root package name */
    public OnBackInvokedDispatcher f22386S;

    /* renamed from: T, reason: collision with root package name */
    public boolean f22387T;

    /* renamed from: U, reason: collision with root package name */
    public final Runnable f22388U;

    /* renamed from: b, reason: collision with root package name */
    public ActionMenuView f22389b;

    /* renamed from: c, reason: collision with root package name */
    public AppCompatTextView f22390c;

    /* renamed from: d, reason: collision with root package name */
    public AppCompatTextView f22391d;

    /* renamed from: e, reason: collision with root package name */
    public AppCompatImageButton f22392e;

    /* renamed from: f, reason: collision with root package name */
    public AppCompatImageView f22393f;

    /* renamed from: g, reason: collision with root package name */
    public final Drawable f22394g;

    /* renamed from: h, reason: collision with root package name */
    public final CharSequence f22395h;

    /* renamed from: i, reason: collision with root package name */
    public AppCompatImageButton f22396i;

    /* renamed from: j, reason: collision with root package name */
    public View f22397j;

    /* renamed from: k, reason: collision with root package name */
    public Context f22398k;

    /* renamed from: l, reason: collision with root package name */
    public int f22399l;

    /* renamed from: m, reason: collision with root package name */
    public int f22400m;

    /* renamed from: n, reason: collision with root package name */
    public int f22401n;

    /* renamed from: o, reason: collision with root package name */
    public final int f22402o;

    /* renamed from: p, reason: collision with root package name */
    public final int f22403p;

    /* renamed from: q, reason: collision with root package name */
    public int f22404q;

    /* renamed from: r, reason: collision with root package name */
    public int f22405r;

    /* renamed from: s, reason: collision with root package name */
    public int f22406s;

    /* renamed from: t, reason: collision with root package name */
    public int f22407t;

    /* renamed from: u, reason: collision with root package name */
    public C19966o0 f22408u;

    /* renamed from: v, reason: collision with root package name */
    public int f22409v;

    /* renamed from: w, reason: collision with root package name */
    public int f22410w;

    /* renamed from: x, reason: collision with root package name */
    public final int f22411x;

    /* renamed from: y, reason: collision with root package name */
    public CharSequence f22412y;

    /* renamed from: z, reason: collision with root package name */
    public CharSequence f22413z;

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();

        /* renamed from: d, reason: collision with root package name */
        public int f22414d;

        /* renamed from: e, reason: collision with root package name */
        public boolean f22415e;

        public class a implements Parcelable.ClassLoaderCreator<SavedState> {
            @Override // android.os.Parcelable.ClassLoaderCreator
            public final SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i12) {
                return new SavedState[i12];
            }

            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f22414d = parcel.readInt();
            this.f22415e = parcel.readInt() != 0;
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i12) {
            super.writeToParcel(parcel, i12);
            parcel.writeInt(this.f22414d);
            parcel.writeInt(this.f22415e ? 1 : 0);
        }
    }

    public class a implements ActionMenuView.e {
        public a() {
        }

        @Override // androidx.appcompat.widget.ActionMenuView.e
        public final boolean onMenuItemClick(MenuItem menuItem) {
            Toolbar toolbar = Toolbar.this;
            Iterator<androidx.core.view.C> it = toolbar.f22375H.f45093b.iterator();
            while (it.hasNext()) {
                if (it.next().d(menuItem)) {
                    return true;
                }
            }
            h hVar = toolbar.f22377J;
            if (hVar != null) {
                return hVar.onMenuItemClick(menuItem);
            }
            return false;
        }
    }

    public class b implements Runnable {
        public b() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            Toolbar.this.w();
        }
    }

    public class c implements h.a {
        public c() {
        }

        @Override // androidx.appcompat.view.menu.h.a
        public final void a(@j.N androidx.appcompat.view.menu.h hVar) {
            Toolbar toolbar = Toolbar.this;
            ActionMenuPresenter actionMenuPresenter = toolbar.f22389b.f22028u;
            if (actionMenuPresenter == null || !actionMenuPresenter.n()) {
                Iterator<androidx.core.view.C> it = toolbar.f22375H.f45093b.iterator();
                while (it.hasNext()) {
                    it.next().c(hVar);
                }
            }
            h.a aVar = toolbar.f22383P;
            if (aVar != null) {
                aVar.a(hVar);
            }
        }

        @Override // androidx.appcompat.view.menu.h.a
        public final boolean b(@j.N androidx.appcompat.view.menu.h hVar, @j.N MenuItem menuItem) {
            h.a aVar = Toolbar.this.f22383P;
            return false;
        }
    }

    public class d implements View.OnClickListener {
        public d() {
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            f fVar = Toolbar.this.f22381N;
            androidx.appcompat.view.menu.k kVar = fVar == null ? null : fVar.f22421c;
            if (kVar != null) {
                kVar.collapseActionView();
            }
        }
    }

    @j.X
    public static class e {
        @InterfaceC42164u
        @j.P
        public static OnBackInvokedDispatcher a(@j.N View view) {
            return view.findOnBackInvokedDispatcher();
        }

        @InterfaceC42164u
        @j.N
        public static OnBackInvokedCallback b(@j.N final Runnable runnable) {
            Objects.requireNonNull(runnable);
            return new OnBackInvokedCallback() { // from class: androidx.appcompat.widget.F0
                @Override // android.window.OnBackInvokedCallback
                public final void onBackInvoked() {
                    runnable.run();
                }
            };
        }

        @InterfaceC42164u
        public static void c(@j.N Object obj, @j.N Object obj2) {
            ((OnBackInvokedDispatcher) obj).registerOnBackInvokedCallback(1000000, (OnBackInvokedCallback) obj2);
        }

        @InterfaceC42164u
        public static void d(@j.N Object obj, @j.N Object obj2) {
            ((OnBackInvokedDispatcher) obj).unregisterOnBackInvokedCallback((OnBackInvokedCallback) obj2);
        }
    }

    public static class g extends AbstractC19927a.b {

        /* renamed from: b, reason: collision with root package name */
        public int f22423b;
    }

    public interface h {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    public Toolbar(@j.N Context context, @j.P AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.toolbarStyle);
    }

    public static g g() {
        g gVar = new g(-2, -2);
        gVar.f22423b = 0;
        gVar.f21545a = 8388627;
        return gVar;
    }

    private ArrayList<MenuItem> getCurrentMenuItems() {
        ArrayList<MenuItem> arrayList = new ArrayList<>();
        Menu menu = getMenu();
        for (int i12 = 0; i12 < menu.size(); i12++) {
            arrayList.add(menu.getItem(i12));
        }
        return arrayList;
    }

    private MenuInflater getMenuInflater() {
        return new androidx.appcompat.view.g(getContext());
    }

    public static g h(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof g) {
            g gVar = (g) layoutParams;
            g gVar2 = new g(gVar);
            gVar2.f22423b = 0;
            gVar2.f22423b = gVar.f22423b;
            return gVar2;
        }
        if (layoutParams instanceof AbstractC19927a.b) {
            g gVar3 = new g((AbstractC19927a.b) layoutParams);
            gVar3.f22423b = 0;
            return gVar3;
        }
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
            g gVar4 = new g(layoutParams);
            gVar4.f22423b = 0;
            return gVar4;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        g gVar5 = new g(marginLayoutParams);
        gVar5.f22423b = 0;
        ((ViewGroup.MarginLayoutParams) gVar5).leftMargin = marginLayoutParams.leftMargin;
        ((ViewGroup.MarginLayoutParams) gVar5).topMargin = marginLayoutParams.topMargin;
        ((ViewGroup.MarginLayoutParams) gVar5).rightMargin = marginLayoutParams.rightMargin;
        ((ViewGroup.MarginLayoutParams) gVar5).bottomMargin = marginLayoutParams.bottomMargin;
        return gVar5;
    }

    public static int j(View view) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        return marginLayoutParams.getMarginEnd() + marginLayoutParams.getMarginStart();
    }

    public static int k(View view) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        return marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
    }

    public final void a(int i12, ArrayList arrayList) {
        boolean z12 = getLayoutDirection() == 1;
        int childCount = getChildCount();
        int absoluteGravity = Gravity.getAbsoluteGravity(i12, getLayoutDirection());
        arrayList.clear();
        if (!z12) {
            for (int i13 = 0; i13 < childCount; i13++) {
                View childAt = getChildAt(i13);
                g gVar = (g) childAt.getLayoutParams();
                if (gVar.f22423b == 0 && v(childAt)) {
                    int i14 = gVar.f21545a;
                    int layoutDirection = getLayoutDirection();
                    int absoluteGravity2 = Gravity.getAbsoluteGravity(i14, layoutDirection) & 7;
                    if (absoluteGravity2 != 1 && absoluteGravity2 != 3 && absoluteGravity2 != 5) {
                        absoluteGravity2 = layoutDirection == 1 ? 5 : 3;
                    }
                    if (absoluteGravity2 == absoluteGravity) {
                        arrayList.add(childAt);
                    }
                }
            }
            return;
        }
        for (int i15 = childCount - 1; i15 >= 0; i15--) {
            View childAt2 = getChildAt(i15);
            g gVar2 = (g) childAt2.getLayoutParams();
            if (gVar2.f22423b == 0 && v(childAt2)) {
                int i16 = gVar2.f21545a;
                int layoutDirection2 = getLayoutDirection();
                int absoluteGravity3 = Gravity.getAbsoluteGravity(i16, layoutDirection2) & 7;
                if (absoluteGravity3 != 1 && absoluteGravity3 != 3 && absoluteGravity3 != 5) {
                    absoluteGravity3 = layoutDirection2 == 1 ? 5 : 3;
                }
                if (absoluteGravity3 == absoluteGravity) {
                    arrayList.add(childAt2);
                }
            }
        }
    }

    @Override // androidx.core.view.InterfaceC22842v
    @j.K
    public final void addMenuProvider(@j.N androidx.core.view.C c12) {
        C22843w c22843w = this.f22375H;
        c22843w.f45093b.add(c12);
        c22843w.f45092a.run();
    }

    public final void b(View view, boolean z12) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        g gVarG = layoutParams == null ? g() : !checkLayoutParams(layoutParams) ? h(layoutParams) : (g) layoutParams;
        gVarG.f22423b = 1;
        if (!z12 || this.f22397j == null) {
            addView(view, gVarG);
        } else {
            view.setLayoutParams(gVarG);
            this.f22373F.add(view);
        }
    }

    public final void c() {
        if (this.f22396i == null) {
            AppCompatImageButton appCompatImageButton = new AppCompatImageButton(getContext(), null, R.attr.toolbarNavigationButtonStyle);
            this.f22396i = appCompatImageButton;
            appCompatImageButton.setImageDrawable(this.f22394g);
            this.f22396i.setContentDescription(this.f22395h);
            g gVarG = g();
            gVarG.f21545a = (this.f22402o & 112) | 8388611;
            gVarG.f22423b = 2;
            this.f22396i.setLayoutParams(gVarG);
            this.f22396i.setOnClickListener(new d());
        }
    }

    @Override // android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return super.checkLayoutParams(layoutParams) && (layoutParams instanceof g);
    }

    public final void d() {
        e();
        ActionMenuView actionMenuView = this.f22389b;
        if (actionMenuView.f22024q == null) {
            androidx.appcompat.view.menu.h hVar = (androidx.appcompat.view.menu.h) actionMenuView.getMenu();
            if (this.f22381N == null) {
                this.f22381N = new f();
            }
            this.f22389b.setExpandedActionViewsExclusive(true);
            hVar.b(this.f22381N, this.f22398k);
            x();
        }
    }

    public final void e() {
        if (this.f22389b == null) {
            ActionMenuView actionMenuView = new ActionMenuView(getContext(), null);
            this.f22389b = actionMenuView;
            actionMenuView.setPopupTheme(this.f22399l);
            this.f22389b.setOnMenuItemClickListener(this.f22378K);
            ActionMenuView actionMenuView2 = this.f22389b;
            o.a aVar = this.f22382O;
            c cVar = new c();
            actionMenuView2.f22029v = aVar;
            actionMenuView2.f22030w = cVar;
            g gVarG = g();
            gVarG.f21545a = (this.f22402o & 112) | 8388613;
            this.f22389b.setLayoutParams(gVarG);
            b(this.f22389b, false);
        }
    }

    public final void f() {
        if (this.f22392e == null) {
            this.f22392e = new AppCompatImageButton(getContext(), null, R.attr.toolbarNavigationButtonStyle);
            g gVarG = g();
            gVarG.f21545a = (this.f22402o & 112) | 8388611;
            this.f22392e.setLayoutParams(gVarG);
        }
    }

    @Override // android.view.ViewGroup
    public final /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return g();
    }

    @Override // android.view.ViewGroup
    public final /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return h(layoutParams);
    }

    @j.P
    public CharSequence getCollapseContentDescription() {
        AppCompatImageButton appCompatImageButton = this.f22396i;
        if (appCompatImageButton != null) {
            return appCompatImageButton.getContentDescription();
        }
        return null;
    }

    @j.P
    public Drawable getCollapseIcon() {
        AppCompatImageButton appCompatImageButton = this.f22396i;
        if (appCompatImageButton != null) {
            return appCompatImageButton.getDrawable();
        }
        return null;
    }

    public int getContentInsetEnd() {
        C19966o0 c19966o0 = this.f22408u;
        if (c19966o0 != null) {
            return c19966o0.f22595g ? c19966o0.f22589a : c19966o0.f22590b;
        }
        return 0;
    }

    public int getContentInsetEndWithActions() {
        int i12 = this.f22410w;
        return i12 != Integer.MIN_VALUE ? i12 : getContentInsetEnd();
    }

    public int getContentInsetLeft() {
        C19966o0 c19966o0 = this.f22408u;
        if (c19966o0 != null) {
            return c19966o0.f22589a;
        }
        return 0;
    }

    public int getContentInsetRight() {
        C19966o0 c19966o0 = this.f22408u;
        if (c19966o0 != null) {
            return c19966o0.f22590b;
        }
        return 0;
    }

    public int getContentInsetStart() {
        C19966o0 c19966o0 = this.f22408u;
        if (c19966o0 != null) {
            return c19966o0.f22595g ? c19966o0.f22590b : c19966o0.f22589a;
        }
        return 0;
    }

    public int getContentInsetStartWithNavigation() {
        int i12 = this.f22409v;
        return i12 != Integer.MIN_VALUE ? i12 : getContentInsetStart();
    }

    public int getCurrentContentInsetEnd() {
        androidx.appcompat.view.menu.h hVar;
        ActionMenuView actionMenuView = this.f22389b;
        return (actionMenuView == null || (hVar = actionMenuView.f22024q) == null || !hVar.hasVisibleItems()) ? getContentInsetEnd() : Math.max(getContentInsetEnd(), Math.max(this.f22410w, 0));
    }

    public int getCurrentContentInsetLeft() {
        return getLayoutDirection() == 1 ? getCurrentContentInsetEnd() : getCurrentContentInsetStart();
    }

    public int getCurrentContentInsetRight() {
        return getLayoutDirection() == 1 ? getCurrentContentInsetStart() : getCurrentContentInsetEnd();
    }

    public int getCurrentContentInsetStart() {
        return getNavigationIcon() != null ? Math.max(getContentInsetStart(), Math.max(this.f22409v, 0)) : getContentInsetStart();
    }

    public Drawable getLogo() {
        AppCompatImageView appCompatImageView = this.f22393f;
        if (appCompatImageView != null) {
            return appCompatImageView.getDrawable();
        }
        return null;
    }

    public CharSequence getLogoDescription() {
        AppCompatImageView appCompatImageView = this.f22393f;
        if (appCompatImageView != null) {
            return appCompatImageView.getContentDescription();
        }
        return null;
    }

    public Menu getMenu() {
        d();
        return this.f22389b.getMenu();
    }

    @j.k0
    @j.P
    public View getNavButtonView() {
        return this.f22392e;
    }

    @j.P
    public CharSequence getNavigationContentDescription() {
        AppCompatImageButton appCompatImageButton = this.f22392e;
        if (appCompatImageButton != null) {
            return appCompatImageButton.getContentDescription();
        }
        return null;
    }

    @j.P
    public Drawable getNavigationIcon() {
        AppCompatImageButton appCompatImageButton = this.f22392e;
        if (appCompatImageButton != null) {
            return appCompatImageButton.getDrawable();
        }
        return null;
    }

    public ActionMenuPresenter getOuterActionMenuPresenter() {
        return this.f22380M;
    }

    @j.P
    public Drawable getOverflowIcon() {
        d();
        return this.f22389b.getOverflowIcon();
    }

    public Context getPopupContext() {
        return this.f22398k;
    }

    @j.f0
    public int getPopupTheme() {
        return this.f22399l;
    }

    public CharSequence getSubtitle() {
        return this.f22413z;
    }

    @j.k0
    @j.P
    public final TextView getSubtitleTextView() {
        return this.f22391d;
    }

    public CharSequence getTitle() {
        return this.f22412y;
    }

    public int getTitleMarginBottom() {
        return this.f22407t;
    }

    public int getTitleMarginEnd() {
        return this.f22405r;
    }

    public int getTitleMarginStart() {
        return this.f22404q;
    }

    public int getTitleMarginTop() {
        return this.f22406s;
    }

    @j.k0
    @j.P
    public final TextView getTitleTextView() {
        return this.f22390c;
    }

    @RestrictTo
    public U getWrapper() {
        if (this.f22379L == null) {
            this.f22379L = new J0(this, true);
        }
        return this.f22379L;
    }

    public final int i(View view, int i12) {
        g gVar = (g) view.getLayoutParams();
        int measuredHeight = view.getMeasuredHeight();
        int i13 = i12 > 0 ? (measuredHeight - i12) / 2 : 0;
        int i14 = gVar.f21545a & 112;
        if (i14 != 16 && i14 != 48 && i14 != 80) {
            i14 = this.f22411x & 112;
        }
        if (i14 == 48) {
            return getPaddingTop() - i13;
        }
        if (i14 == 80) {
            return (((getHeight() - getPaddingBottom()) - measuredHeight) - ((ViewGroup.MarginLayoutParams) gVar).bottomMargin) - i13;
        }
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int height = getHeight();
        int iMax = (((height - paddingTop) - paddingBottom) - measuredHeight) / 2;
        int i15 = ((ViewGroup.MarginLayoutParams) gVar).topMargin;
        if (iMax < i15) {
            iMax = i15;
        } else {
            int i16 = (((height - paddingBottom) - measuredHeight) - iMax) - paddingTop;
            int i17 = ((ViewGroup.MarginLayoutParams) gVar).bottomMargin;
            if (i16 < i17) {
                iMax = Math.max(0, iMax - (i17 - i16));
            }
        }
        return paddingTop + iMax;
    }

    public final boolean l() {
        f fVar = this.f22381N;
        return (fVar == null || fVar.f22421c == null) ? false : true;
    }

    public void m(@j.L int i12) {
        getMenuInflater().inflate(i12, getMenu());
    }

    @j.K
    public final void n() {
        Iterator<MenuItem> it = this.f22376I.iterator();
        while (it.hasNext()) {
            getMenu().removeItem(it.next().getItemId());
        }
        Menu menu = getMenu();
        ArrayList<MenuItem> currentMenuItems = getCurrentMenuItems();
        MenuInflater menuInflater = getMenuInflater();
        Iterator<androidx.core.view.C> it2 = this.f22375H.f45093b.iterator();
        while (it2.hasNext()) {
            it2.next().b(menu, menuInflater);
        }
        ArrayList<MenuItem> currentMenuItems2 = getCurrentMenuItems();
        currentMenuItems2.removeAll(currentMenuItems);
        this.f22376I = currentMenuItems2;
    }

    public final boolean o(View view) {
        return view.getParent() == this || this.f22373F.contains(view);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        x();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.f22388U);
        x();
    }

    @Override // android.view.View
    public final boolean onHoverEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 9) {
            this.f22371D = false;
        }
        if (!this.f22371D) {
            boolean zOnHoverEvent = super.onHoverEvent(motionEvent);
            if (actionMasked == 9 && !zOnHoverEvent) {
                this.f22371D = true;
            }
        }
        if (actionMasked == 10 || actionMasked == 3) {
            this.f22371D = false;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x0298 A[LOOP:0: B:105:0x0296->B:106:0x0298, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:109:0x02b5 A[LOOP:1: B:108:0x02b3->B:109:0x02b5, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:112:0x02d3 A[LOOP:2: B:111:0x02d1->B:112:0x02d3, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:116:0x0314  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0321 A[LOOP:3: B:120:0x031f->B:121:0x0321, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0101  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x011e  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x012b  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0130  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01a2  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01b1  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0221  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onLayout(boolean r19, int r20, int r21, int r22, int r23) {
        /*
            Method dump skipped, instructions count: 818
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.Toolbar.onLayout(boolean, int, int, int, int):void");
    }

    @Override // android.view.View
    public void onMeasure(int i12, int i13) {
        char c12;
        char c13;
        int iJ2;
        int iMax;
        int iCombineMeasuredStates;
        int iJ3;
        int iK2;
        int iCombineMeasuredStates2;
        int iMax2;
        int i14 = 0;
        if (getLayoutDirection() == 1) {
            c13 = 1;
            c12 = 0;
        } else {
            c12 = 1;
            c13 = 0;
        }
        if (v(this.f22392e)) {
            t(this.f22392e, i12, 0, i13, this.f22403p);
            iJ2 = j(this.f22392e) + this.f22392e.getMeasuredWidth();
            iMax = Math.max(0, k(this.f22392e) + this.f22392e.getMeasuredHeight());
            iCombineMeasuredStates = View.combineMeasuredStates(0, this.f22392e.getMeasuredState());
        } else {
            iJ2 = 0;
            iMax = 0;
            iCombineMeasuredStates = 0;
        }
        if (v(this.f22396i)) {
            t(this.f22396i, i12, 0, i13, this.f22403p);
            iJ2 = j(this.f22396i) + this.f22396i.getMeasuredWidth();
            iMax = Math.max(iMax, k(this.f22396i) + this.f22396i.getMeasuredHeight());
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, this.f22396i.getMeasuredState());
        }
        int currentContentInsetStart = getCurrentContentInsetStart();
        int iMax3 = Math.max(currentContentInsetStart, iJ2);
        int iMax4 = Math.max(0, currentContentInsetStart - iJ2);
        int[] iArr = this.f22374G;
        iArr[c13] = iMax4;
        if (v(this.f22389b)) {
            t(this.f22389b, i12, iMax3, i13, this.f22403p);
            iJ3 = j(this.f22389b) + this.f22389b.getMeasuredWidth();
            iMax = Math.max(iMax, k(this.f22389b) + this.f22389b.getMeasuredHeight());
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, this.f22389b.getMeasuredState());
        } else {
            iJ3 = 0;
        }
        int currentContentInsetEnd = getCurrentContentInsetEnd();
        int iMax5 = iMax3 + Math.max(currentContentInsetEnd, iJ3);
        iArr[c12] = Math.max(0, currentContentInsetEnd - iJ3);
        if (v(this.f22397j)) {
            iMax5 += s(this.f22397j, i12, iMax5, i13, 0, iArr);
            iMax = Math.max(iMax, k(this.f22397j) + this.f22397j.getMeasuredHeight());
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, this.f22397j.getMeasuredState());
        }
        if (v(this.f22393f)) {
            iMax5 += s(this.f22393f, i12, iMax5, i13, 0, iArr);
            iMax = Math.max(iMax, k(this.f22393f) + this.f22393f.getMeasuredHeight());
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, this.f22393f.getMeasuredState());
        }
        int childCount = getChildCount();
        for (int i15 = 0; i15 < childCount; i15++) {
            View childAt = getChildAt(i15);
            if (((g) childAt.getLayoutParams()).f22423b == 0 && v(childAt)) {
                iMax5 += s(childAt, i12, iMax5, i13, 0, iArr);
                iMax = Math.max(iMax, k(childAt) + childAt.getMeasuredHeight());
                iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, childAt.getMeasuredState());
            }
        }
        int i16 = this.f22406s + this.f22407t;
        int i17 = this.f22404q + this.f22405r;
        if (v(this.f22390c)) {
            s(this.f22390c, i12, iMax5 + i17, i13, i16, iArr);
            int iJ4 = j(this.f22390c) + this.f22390c.getMeasuredWidth();
            iK2 = k(this.f22390c) + this.f22390c.getMeasuredHeight();
            iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates, this.f22390c.getMeasuredState());
            iMax2 = iJ4;
        } else {
            iK2 = 0;
            iCombineMeasuredStates2 = iCombineMeasuredStates;
            iMax2 = 0;
        }
        if (v(this.f22391d)) {
            iMax2 = Math.max(iMax2, s(this.f22391d, i12, iMax5 + i17, i13, iK2 + i16, iArr));
            iK2 += k(this.f22391d) + this.f22391d.getMeasuredHeight();
            iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates2, this.f22391d.getMeasuredState());
        }
        int iMax6 = Math.max(iMax, iK2);
        int paddingRight = getPaddingRight() + getPaddingLeft();
        int paddingBottom = getPaddingBottom() + getPaddingTop() + iMax6;
        int iResolveSizeAndState = View.resolveSizeAndState(Math.max(paddingRight + iMax5 + iMax2, getSuggestedMinimumWidth()), i12, (-16777216) & iCombineMeasuredStates2);
        int iResolveSizeAndState2 = View.resolveSizeAndState(Math.max(paddingBottom, getSuggestedMinimumHeight()), i13, iCombineMeasuredStates2 << 16);
        if (!this.f22384Q) {
            i14 = iResolveSizeAndState2;
            break;
        }
        int childCount2 = getChildCount();
        for (int i18 = 0; i18 < childCount2; i18++) {
            View childAt2 = getChildAt(i18);
            if (v(childAt2) && childAt2.getMeasuredWidth() > 0 && childAt2.getMeasuredHeight() > 0) {
                i14 = iResolveSizeAndState2;
                break;
            }
        }
        setMeasuredDimension(iResolveSizeAndState, i14);
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        MenuItem menuItemFindItem;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.f45355b);
        ActionMenuView actionMenuView = this.f22389b;
        androidx.appcompat.view.menu.h hVar = actionMenuView != null ? actionMenuView.f22024q : null;
        int i12 = savedState.f22414d;
        if (i12 != 0 && this.f22381N != null && hVar != null && (menuItemFindItem = hVar.findItem(i12)) != null) {
            menuItemFindItem.expandActionView();
        }
        if (savedState.f22415e) {
            Runnable runnable = this.f22388U;
            removeCallbacks(runnable);
            post(runnable);
        }
    }

    @Override // android.view.View
    public final void onRtlPropertiesChanged(int i12) {
        super.onRtlPropertiesChanged(i12);
        if (this.f22408u == null) {
            this.f22408u = new C19966o0();
        }
        C19966o0 c19966o0 = this.f22408u;
        boolean z12 = i12 == 1;
        if (z12 == c19966o0.f22595g) {
            return;
        }
        c19966o0.f22595g = z12;
        if (!c19966o0.f22596h) {
            c19966o0.f22589a = c19966o0.f22593e;
            c19966o0.f22590b = c19966o0.f22594f;
            return;
        }
        if (z12) {
            int i13 = c19966o0.f22592d;
            if (i13 == Integer.MIN_VALUE) {
                i13 = c19966o0.f22593e;
            }
            c19966o0.f22589a = i13;
            int i14 = c19966o0.f22591c;
            if (i14 == Integer.MIN_VALUE) {
                i14 = c19966o0.f22594f;
            }
            c19966o0.f22590b = i14;
            return;
        }
        int i15 = c19966o0.f22591c;
        if (i15 == Integer.MIN_VALUE) {
            i15 = c19966o0.f22593e;
        }
        c19966o0.f22589a = i15;
        int i16 = c19966o0.f22592d;
        if (i16 == Integer.MIN_VALUE) {
            i16 = c19966o0.f22594f;
        }
        c19966o0.f22590b = i16;
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        ActionMenuPresenter actionMenuPresenter;
        androidx.appcompat.view.menu.k kVar;
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        f fVar = this.f22381N;
        if (fVar != null && (kVar = fVar.f22421c) != null) {
            savedState.f22414d = kVar.f21861a;
        }
        ActionMenuView actionMenuView = this.f22389b;
        savedState.f22415e = (actionMenuView == null || (actionMenuPresenter = actionMenuView.f22028u) == null || !actionMenuPresenter.n()) ? false : true;
        return savedState;
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.f22370C = false;
        }
        if (!this.f22370C) {
            boolean zOnTouchEvent = super.onTouchEvent(motionEvent);
            if (actionMasked == 0 && !zOnTouchEvent) {
                this.f22370C = true;
            }
        }
        if (actionMasked == 1 || actionMasked == 3) {
            this.f22370C = false;
        }
        return true;
    }

    public final boolean p() {
        ActionMenuPresenter actionMenuPresenter;
        ActionMenuView actionMenuView = this.f22389b;
        return (actionMenuView == null || (actionMenuPresenter = actionMenuView.f22028u) == null || !actionMenuPresenter.n()) ? false : true;
    }

    public final int q(View view, int i12, int i13, int[] iArr) {
        g gVar = (g) view.getLayoutParams();
        int i14 = ((ViewGroup.MarginLayoutParams) gVar).leftMargin - iArr[0];
        int iMax = Math.max(0, i14) + i12;
        iArr[0] = Math.max(0, -i14);
        int i15 = i(view, i13);
        int measuredWidth = view.getMeasuredWidth();
        view.layout(iMax, i15, iMax + measuredWidth, view.getMeasuredHeight() + i15);
        return measuredWidth + ((ViewGroup.MarginLayoutParams) gVar).rightMargin + iMax;
    }

    public final int r(View view, int i12, int i13, int[] iArr) {
        g gVar = (g) view.getLayoutParams();
        int i14 = ((ViewGroup.MarginLayoutParams) gVar).rightMargin - iArr[1];
        int iMax = i12 - Math.max(0, i14);
        iArr[1] = Math.max(0, -i14);
        int i15 = i(view, i13);
        int measuredWidth = view.getMeasuredWidth();
        view.layout(iMax - measuredWidth, i15, iMax, view.getMeasuredHeight() + i15);
        return iMax - (measuredWidth + ((ViewGroup.MarginLayoutParams) gVar).leftMargin);
    }

    @Override // androidx.core.view.InterfaceC22842v
    @j.K
    public final void removeMenuProvider(@j.N androidx.core.view.C c12) {
        this.f22375H.a(c12);
    }

    public final int s(View view, int i12, int i13, int i14, int i15, int[] iArr) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int i16 = marginLayoutParams.leftMargin - iArr[0];
        int i17 = marginLayoutParams.rightMargin - iArr[1];
        int iMax = Math.max(0, i17) + Math.max(0, i16);
        iArr[0] = Math.max(0, -i16);
        iArr[1] = Math.max(0, -i17);
        view.measure(ViewGroup.getChildMeasureSpec(i12, getPaddingRight() + getPaddingLeft() + iMax + i13, marginLayoutParams.width), ViewGroup.getChildMeasureSpec(i14, getPaddingBottom() + getPaddingTop() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i15, marginLayoutParams.height));
        return view.getMeasuredWidth() + iMax;
    }

    public void setBackInvokedCallbackEnabled(boolean z12) {
        if (this.f22387T != z12) {
            this.f22387T = z12;
            x();
        }
    }

    public void setCollapseContentDescription(@j.e0 int i12) {
        setCollapseContentDescription(i12 != 0 ? getContext().getText(i12) : null);
    }

    public void setCollapseIcon(@InterfaceC42165v int i12) {
        setCollapseIcon(C43852a.a(getContext(), i12));
    }

    @RestrictTo
    public void setCollapsible(boolean z12) {
        this.f22384Q = z12;
        requestLayout();
    }

    public void setContentInsetEndWithActions(int i12) {
        if (i12 < 0) {
            i12 = BeduinInputModel.MIN_TEXT_VERSION;
        }
        if (i12 != this.f22410w) {
            this.f22410w = i12;
            if (getNavigationIcon() != null) {
                requestLayout();
            }
        }
    }

    public void setContentInsetStartWithNavigation(int i12) {
        if (i12 < 0) {
            i12 = BeduinInputModel.MIN_TEXT_VERSION;
        }
        if (i12 != this.f22409v) {
            this.f22409v = i12;
            if (getNavigationIcon() != null) {
                requestLayout();
            }
        }
    }

    public void setLogo(@InterfaceC42165v int i12) {
        setLogo(C43852a.a(getContext(), i12));
    }

    public void setLogoDescription(@j.e0 int i12) {
        setLogoDescription(getContext().getText(i12));
    }

    public void setNavigationContentDescription(@j.e0 int i12) {
        setNavigationContentDescription(i12 != 0 ? getContext().getText(i12) : null);
    }

    public void setNavigationIcon(@InterfaceC42165v int i12) {
        setNavigationIcon(C43852a.a(getContext(), i12));
    }

    public void setNavigationOnClickListener(View.OnClickListener onClickListener) {
        f();
        this.f22392e.setOnClickListener(onClickListener);
    }

    public void setOnMenuItemClickListener(h hVar) {
        this.f22377J = hVar;
    }

    public void setOverflowIcon(@j.P Drawable drawable) {
        d();
        this.f22389b.setOverflowIcon(drawable);
    }

    public void setPopupTheme(@j.f0 int i12) {
        if (this.f22399l != i12) {
            this.f22399l = i12;
            if (i12 == 0) {
                this.f22398k = getContext();
            } else {
                this.f22398k = new ContextThemeWrapper(getContext(), i12);
            }
        }
    }

    public void setSubtitle(@j.e0 int i12) {
        setSubtitle(getContext().getText(i12));
    }

    public void setSubtitleTextColor(@InterfaceC42156l int i12) {
        setSubtitleTextColor(ColorStateList.valueOf(i12));
    }

    public void setTitle(@j.e0 int i12) {
        setTitle(getContext().getText(i12));
    }

    public void setTitleMarginBottom(int i12) {
        this.f22407t = i12;
        requestLayout();
    }

    public void setTitleMarginEnd(int i12) {
        this.f22405r = i12;
        requestLayout();
    }

    public void setTitleMarginStart(int i12) {
        this.f22404q = i12;
        requestLayout();
    }

    public void setTitleMarginTop(int i12) {
        this.f22406s = i12;
        requestLayout();
    }

    public void setTitleTextColor(@InterfaceC42156l int i12) {
        setTitleTextColor(ColorStateList.valueOf(i12));
    }

    public final void t(View view, int i12, int i13, int i14, int i15) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i12, getPaddingRight() + getPaddingLeft() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i13, marginLayoutParams.width);
        int childMeasureSpec2 = ViewGroup.getChildMeasureSpec(i14, getPaddingBottom() + getPaddingTop() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, marginLayoutParams.height);
        int mode = View.MeasureSpec.getMode(childMeasureSpec2);
        if (mode != 1073741824 && i15 >= 0) {
            if (mode != 0) {
                i15 = Math.min(View.MeasureSpec.getSize(childMeasureSpec2), i15);
            }
            childMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(i15, 1073741824);
        }
        view.measure(childMeasureSpec, childMeasureSpec2);
    }

    public final void u(int i12, int i13) {
        if (this.f22408u == null) {
            this.f22408u = new C19966o0();
        }
        C19966o0 c19966o0 = this.f22408u;
        c19966o0.f22596h = false;
        if (i12 != Integer.MIN_VALUE) {
            c19966o0.f22593e = i12;
            c19966o0.f22589a = i12;
        }
        if (i13 != Integer.MIN_VALUE) {
            c19966o0.f22594f = i13;
            c19966o0.f22590b = i13;
        }
    }

    public final boolean v(View view) {
        return (view == null || view.getParent() != this || view.getVisibility() == 8) ? false : true;
    }

    public final boolean w() {
        ActionMenuPresenter actionMenuPresenter;
        ActionMenuView actionMenuView = this.f22389b;
        return (actionMenuView == null || (actionMenuPresenter = actionMenuView.f22028u) == null || !actionMenuPresenter.p()) ? false : true;
    }

    public final void x() {
        OnBackInvokedDispatcher onBackInvokedDispatcher;
        if (Build.VERSION.SDK_INT >= 33) {
            OnBackInvokedDispatcher onBackInvokedDispatcherA = e.a(this);
            boolean z12 = l() && onBackInvokedDispatcherA != null && isAttachedToWindow() && this.f22387T;
            if (z12 && this.f22386S == null) {
                if (this.f22385R == null) {
                    this.f22385R = e.b(new E0(this, 0));
                }
                e.c(onBackInvokedDispatcherA, this.f22385R);
                this.f22386S = onBackInvokedDispatcherA;
                return;
            }
            if (z12 || (onBackInvokedDispatcher = this.f22386S) == null) {
                return;
            }
            e.d(onBackInvokedDispatcher, this.f22385R);
            this.f22386S = null;
        }
    }

    public Toolbar(@j.N Context context, @j.P AttributeSet attributeSet, int i12) {
        super(context, attributeSet, i12);
        this.f22411x = 8388627;
        this.f22372E = new ArrayList<>();
        this.f22373F = new ArrayList<>();
        this.f22374G = new int[2];
        this.f22375H = new C22843w(new E0(this, 1));
        this.f22376I = new ArrayList<>();
        this.f22378K = new a();
        this.f22388U = new b();
        Context context2 = getContext();
        int[] iArr = C43521a.m.f413376y;
        D0 d0E = D0.e(context2, attributeSet, iArr, i12);
        C22823h0.B(this, context, iArr, attributeSet, d0E.f22121b, i12);
        TypedArray typedArray = d0E.f22121b;
        this.f22400m = typedArray.getResourceId(28, 0);
        this.f22401n = typedArray.getResourceId(19, 0);
        this.f22411x = typedArray.getInteger(0, 8388627);
        this.f22402o = typedArray.getInteger(2, 48);
        int dimensionPixelOffset = typedArray.getDimensionPixelOffset(22, 0);
        dimensionPixelOffset = typedArray.hasValue(27) ? typedArray.getDimensionPixelOffset(27, dimensionPixelOffset) : dimensionPixelOffset;
        this.f22407t = dimensionPixelOffset;
        this.f22406s = dimensionPixelOffset;
        this.f22405r = dimensionPixelOffset;
        this.f22404q = dimensionPixelOffset;
        int dimensionPixelOffset2 = typedArray.getDimensionPixelOffset(25, -1);
        if (dimensionPixelOffset2 >= 0) {
            this.f22404q = dimensionPixelOffset2;
        }
        int dimensionPixelOffset3 = typedArray.getDimensionPixelOffset(24, -1);
        if (dimensionPixelOffset3 >= 0) {
            this.f22405r = dimensionPixelOffset3;
        }
        int dimensionPixelOffset4 = typedArray.getDimensionPixelOffset(26, -1);
        if (dimensionPixelOffset4 >= 0) {
            this.f22406s = dimensionPixelOffset4;
        }
        int dimensionPixelOffset5 = typedArray.getDimensionPixelOffset(23, -1);
        if (dimensionPixelOffset5 >= 0) {
            this.f22407t = dimensionPixelOffset5;
        }
        this.f22403p = typedArray.getDimensionPixelSize(13, -1);
        int dimensionPixelOffset6 = typedArray.getDimensionPixelOffset(9, BeduinInputModel.MIN_TEXT_VERSION);
        int dimensionPixelOffset7 = typedArray.getDimensionPixelOffset(5, BeduinInputModel.MIN_TEXT_VERSION);
        int dimensionPixelSize = typedArray.getDimensionPixelSize(7, 0);
        int dimensionPixelSize2 = typedArray.getDimensionPixelSize(8, 0);
        if (this.f22408u == null) {
            this.f22408u = new C19966o0();
        }
        C19966o0 c19966o0 = this.f22408u;
        c19966o0.f22596h = false;
        if (dimensionPixelSize != Integer.MIN_VALUE) {
            c19966o0.f22593e = dimensionPixelSize;
            c19966o0.f22589a = dimensionPixelSize;
        }
        if (dimensionPixelSize2 != Integer.MIN_VALUE) {
            c19966o0.f22594f = dimensionPixelSize2;
            c19966o0.f22590b = dimensionPixelSize2;
        }
        if (dimensionPixelOffset6 != Integer.MIN_VALUE || dimensionPixelOffset7 != Integer.MIN_VALUE) {
            c19966o0.a(dimensionPixelOffset6, dimensionPixelOffset7);
        }
        this.f22409v = typedArray.getDimensionPixelOffset(10, BeduinInputModel.MIN_TEXT_VERSION);
        this.f22410w = typedArray.getDimensionPixelOffset(6, BeduinInputModel.MIN_TEXT_VERSION);
        this.f22394g = d0E.b(4);
        this.f22395h = typedArray.getText(3);
        CharSequence text = typedArray.getText(21);
        if (!TextUtils.isEmpty(text)) {
            setTitle(text);
        }
        CharSequence text2 = typedArray.getText(18);
        if (!TextUtils.isEmpty(text2)) {
            setSubtitle(text2);
        }
        this.f22398k = getContext();
        setPopupTheme(typedArray.getResourceId(17, 0));
        Drawable drawableB = d0E.b(16);
        if (drawableB != null) {
            setNavigationIcon(drawableB);
        }
        CharSequence text3 = typedArray.getText(15);
        if (!TextUtils.isEmpty(text3)) {
            setNavigationContentDescription(text3);
        }
        Drawable drawableB2 = d0E.b(11);
        if (drawableB2 != null) {
            setLogo(drawableB2);
        }
        CharSequence text4 = typedArray.getText(12);
        if (!TextUtils.isEmpty(text4)) {
            setLogoDescription(text4);
        }
        if (typedArray.hasValue(29)) {
            setTitleTextColor(d0E.a(29));
        }
        if (typedArray.hasValue(20)) {
            setSubtitleTextColor(d0E.a(20));
        }
        if (typedArray.hasValue(14)) {
            m(typedArray.getResourceId(14, 0));
        }
        d0E.f();
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        Context context = getContext();
        g gVar = new g(context, attributeSet);
        gVar.f21545a = 0;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C43521a.m.f413353b);
        gVar.f21545a = typedArrayObtainStyledAttributes.getInt(0, 0);
        typedArrayObtainStyledAttributes.recycle();
        gVar.f22423b = 0;
        return gVar;
    }

    public void setCollapseContentDescription(@j.P CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            c();
        }
        AppCompatImageButton appCompatImageButton = this.f22396i;
        if (appCompatImageButton != null) {
            appCompatImageButton.setContentDescription(charSequence);
        }
    }

    public void setCollapseIcon(@j.P Drawable drawable) {
        if (drawable != null) {
            c();
            this.f22396i.setImageDrawable(drawable);
        } else {
            AppCompatImageButton appCompatImageButton = this.f22396i;
            if (appCompatImageButton != null) {
                appCompatImageButton.setImageDrawable(this.f22394g);
            }
        }
    }

    public void setLogo(Drawable drawable) {
        if (drawable != null) {
            if (this.f22393f == null) {
                this.f22393f = new AppCompatImageView(getContext());
            }
            if (!o(this.f22393f)) {
                b(this.f22393f, true);
            }
        } else {
            AppCompatImageView appCompatImageView = this.f22393f;
            if (appCompatImageView != null && o(appCompatImageView)) {
                removeView(this.f22393f);
                this.f22373F.remove(this.f22393f);
            }
        }
        AppCompatImageView appCompatImageView2 = this.f22393f;
        if (appCompatImageView2 != null) {
            appCompatImageView2.setImageDrawable(drawable);
        }
    }

    public void setLogoDescription(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence) && this.f22393f == null) {
            this.f22393f = new AppCompatImageView(getContext());
        }
        AppCompatImageView appCompatImageView = this.f22393f;
        if (appCompatImageView != null) {
            appCompatImageView.setContentDescription(charSequence);
        }
    }

    public void setNavigationContentDescription(@j.P CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            f();
        }
        AppCompatImageButton appCompatImageButton = this.f22392e;
        if (appCompatImageButton != null) {
            appCompatImageButton.setContentDescription(charSequence);
            K0.a.a(this.f22392e, charSequence);
        }
    }

    public void setNavigationIcon(@j.P Drawable drawable) {
        if (drawable != null) {
            f();
            if (!o(this.f22392e)) {
                b(this.f22392e, true);
            }
        } else {
            AppCompatImageButton appCompatImageButton = this.f22392e;
            if (appCompatImageButton != null && o(appCompatImageButton)) {
                removeView(this.f22392e);
                this.f22373F.remove(this.f22392e);
            }
        }
        AppCompatImageButton appCompatImageButton2 = this.f22392e;
        if (appCompatImageButton2 != null) {
            appCompatImageButton2.setImageDrawable(drawable);
        }
    }

    public void setSubtitle(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            AppCompatTextView appCompatTextView = this.f22391d;
            if (appCompatTextView != null && o(appCompatTextView)) {
                removeView(this.f22391d);
                this.f22373F.remove(this.f22391d);
            }
        } else {
            if (this.f22391d == null) {
                Context context = getContext();
                AppCompatTextView appCompatTextView2 = new AppCompatTextView(context);
                this.f22391d = appCompatTextView2;
                appCompatTextView2.setSingleLine();
                this.f22391d.setEllipsize(TextUtils.TruncateAt.END);
                int i12 = this.f22401n;
                if (i12 != 0) {
                    this.f22391d.setTextAppearance(context, i12);
                }
                ColorStateList colorStateList = this.f22369B;
                if (colorStateList != null) {
                    this.f22391d.setTextColor(colorStateList);
                }
            }
            if (!o(this.f22391d)) {
                b(this.f22391d, true);
            }
        }
        AppCompatTextView appCompatTextView3 = this.f22391d;
        if (appCompatTextView3 != null) {
            appCompatTextView3.setText(charSequence);
        }
        this.f22413z = charSequence;
    }

    public void setSubtitleTextColor(@j.N ColorStateList colorStateList) {
        this.f22369B = colorStateList;
        AppCompatTextView appCompatTextView = this.f22391d;
        if (appCompatTextView != null) {
            appCompatTextView.setTextColor(colorStateList);
        }
    }

    public void setTitle(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            AppCompatTextView appCompatTextView = this.f22390c;
            if (appCompatTextView != null && o(appCompatTextView)) {
                removeView(this.f22390c);
                this.f22373F.remove(this.f22390c);
            }
        } else {
            if (this.f22390c == null) {
                Context context = getContext();
                AppCompatTextView appCompatTextView2 = new AppCompatTextView(context);
                this.f22390c = appCompatTextView2;
                appCompatTextView2.setSingleLine();
                this.f22390c.setEllipsize(TextUtils.TruncateAt.END);
                int i12 = this.f22400m;
                if (i12 != 0) {
                    this.f22390c.setTextAppearance(context, i12);
                }
                ColorStateList colorStateList = this.f22368A;
                if (colorStateList != null) {
                    this.f22390c.setTextColor(colorStateList);
                }
            }
            if (!o(this.f22390c)) {
                b(this.f22390c, true);
            }
        }
        AppCompatTextView appCompatTextView3 = this.f22390c;
        if (appCompatTextView3 != null) {
            appCompatTextView3.setText(charSequence);
        }
        this.f22412y = charSequence;
    }

    public void setTitleTextColor(@j.N ColorStateList colorStateList) {
        this.f22368A = colorStateList;
        AppCompatTextView appCompatTextView = this.f22390c;
        if (appCompatTextView != null) {
            appCompatTextView.setTextColor(colorStateList);
        }
    }

    public class f implements androidx.appcompat.view.menu.o {

        /* renamed from: b, reason: collision with root package name */
        public androidx.appcompat.view.menu.h f22420b;

        /* renamed from: c, reason: collision with root package name */
        public androidx.appcompat.view.menu.k f22421c;

        public f() {
        }

        @Override // androidx.appcompat.view.menu.o
        public final void c(boolean z12) {
            if (this.f22421c != null) {
                androidx.appcompat.view.menu.h hVar = this.f22420b;
                if (hVar != null) {
                    int size = hVar.f21836f.size();
                    for (int i12 = 0; i12 < size; i12++) {
                        if (this.f22420b.getItem(i12) == this.f22421c) {
                            return;
                        }
                    }
                }
                e(this.f22421c);
            }
        }

        @Override // androidx.appcompat.view.menu.o
        public final boolean e(androidx.appcompat.view.menu.k kVar) {
            Toolbar toolbar = Toolbar.this;
            KeyEvent.Callback callback = toolbar.f22397j;
            if (callback instanceof androidx.appcompat.view.c) {
                ((androidx.appcompat.view.c) callback).a();
            }
            toolbar.removeView(toolbar.f22397j);
            toolbar.removeView(toolbar.f22396i);
            toolbar.f22397j = null;
            ArrayList<View> arrayList = toolbar.f22373F;
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                toolbar.addView(arrayList.get(size));
            }
            arrayList.clear();
            this.f22421c = null;
            toolbar.requestLayout();
            kVar.f21860C = false;
            kVar.f21874n.p(false);
            toolbar.x();
            return true;
        }

        @Override // androidx.appcompat.view.menu.o
        public final boolean f(androidx.appcompat.view.menu.t tVar) {
            return false;
        }

        @Override // androidx.appcompat.view.menu.o
        public final boolean g(androidx.appcompat.view.menu.k kVar) {
            Toolbar toolbar = Toolbar.this;
            toolbar.c();
            ViewParent parent = toolbar.f22396i.getParent();
            if (parent != toolbar) {
                if (parent instanceof ViewGroup) {
                    ((ViewGroup) parent).removeView(toolbar.f22396i);
                }
                toolbar.addView(toolbar.f22396i);
            }
            View actionView = kVar.getActionView();
            toolbar.f22397j = actionView;
            this.f22421c = kVar;
            ViewParent parent2 = actionView.getParent();
            if (parent2 != toolbar) {
                if (parent2 instanceof ViewGroup) {
                    ((ViewGroup) parent2).removeView(toolbar.f22397j);
                }
                g gVarG = Toolbar.g();
                gVarG.f21545a = (toolbar.f22402o & 112) | 8388611;
                gVarG.f22423b = 2;
                toolbar.f22397j.setLayoutParams(gVarG);
                toolbar.addView(toolbar.f22397j);
            }
            for (int childCount = toolbar.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = toolbar.getChildAt(childCount);
                if (((g) childAt.getLayoutParams()).f22423b != 2 && childAt != toolbar.f22389b) {
                    toolbar.removeViewAt(childCount);
                    toolbar.f22373F.add(childAt);
                }
            }
            toolbar.requestLayout();
            kVar.f21860C = true;
            kVar.f21874n.p(false);
            KeyEvent.Callback callback = toolbar.f22397j;
            if (callback instanceof androidx.appcompat.view.c) {
                ((androidx.appcompat.view.c) callback).c();
            }
            toolbar.x();
            return true;
        }

        @Override // androidx.appcompat.view.menu.o
        public final boolean h() {
            return false;
        }

        @Override // androidx.appcompat.view.menu.o
        public final void i(Context context, androidx.appcompat.view.menu.h hVar) {
            androidx.appcompat.view.menu.k kVar;
            androidx.appcompat.view.menu.h hVar2 = this.f22420b;
            if (hVar2 != null && (kVar = this.f22421c) != null) {
                hVar2.d(kVar);
            }
            this.f22420b = hVar;
        }

        @Override // androidx.appcompat.view.menu.o
        public final void b(androidx.appcompat.view.menu.h hVar, boolean z12) {
        }
    }
}
