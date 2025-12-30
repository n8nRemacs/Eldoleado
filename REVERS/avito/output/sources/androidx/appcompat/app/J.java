package androidx.appcompat.app;

import android.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import androidx.annotation.RestrictTo;
import androidx.appcompat.app.AbstractC19927a;
import androidx.appcompat.app.AppCompatDelegateImpl;
import androidx.appcompat.view.b;
import androidx.appcompat.view.menu.h;
import androidx.appcompat.widget.ActionBarContainer;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.U;
import androidx.core.view.A0;
import androidx.core.view.B0;
import androidx.core.view.C0;
import androidx.core.view.C22823h0;
import androidx.core.view.D0;
import androidx.core.view.y0;
import j.N;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import l.C43521a;

/* compiled from: WindowDecorActionBar.java */
@RestrictTo
/* loaded from: classes.dex */
public class J extends AbstractC19927a implements ActionBarOverlayLayout.d {

    /* renamed from: a, reason: collision with root package name */
    public Context f21512a;

    /* renamed from: b, reason: collision with root package name */
    public Context f21513b;

    /* renamed from: c, reason: collision with root package name */
    public ActionBarOverlayLayout f21514c;

    /* renamed from: d, reason: collision with root package name */
    public ActionBarContainer f21515d;

    /* renamed from: e, reason: collision with root package name */
    public U f21516e;

    /* renamed from: f, reason: collision with root package name */
    public ActionBarContextView f21517f;

    /* renamed from: g, reason: collision with root package name */
    public final View f21518g;

    /* renamed from: h, reason: collision with root package name */
    public boolean f21519h;

    /* renamed from: i, reason: collision with root package name */
    public d f21520i;

    /* renamed from: j, reason: collision with root package name */
    public d f21521j;

    /* renamed from: k, reason: collision with root package name */
    public b.a f21522k;

    /* renamed from: l, reason: collision with root package name */
    public boolean f21523l;

    /* renamed from: m, reason: collision with root package name */
    public final ArrayList<AbstractC19927a.d> f21524m;

    /* renamed from: n, reason: collision with root package name */
    public int f21525n;

    /* renamed from: o, reason: collision with root package name */
    public boolean f21526o;

    /* renamed from: p, reason: collision with root package name */
    public boolean f21527p;

    /* renamed from: q, reason: collision with root package name */
    public boolean f21528q;

    /* renamed from: r, reason: collision with root package name */
    public boolean f21529r;

    /* renamed from: s, reason: collision with root package name */
    public boolean f21530s;

    /* renamed from: t, reason: collision with root package name */
    public androidx.appcompat.view.h f21531t;

    /* renamed from: u, reason: collision with root package name */
    public boolean f21532u;

    /* renamed from: v, reason: collision with root package name */
    public boolean f21533v;

    /* renamed from: w, reason: collision with root package name */
    public final B0 f21534w;

    /* renamed from: x, reason: collision with root package name */
    public final B0 f21535x;

    /* renamed from: y, reason: collision with root package name */
    public final D0 f21536y;

    /* renamed from: z, reason: collision with root package name */
    public static final AccelerateInterpolator f21511z = new AccelerateInterpolator();

    /* renamed from: A, reason: collision with root package name */
    public static final DecelerateInterpolator f21510A = new DecelerateInterpolator();

    /* compiled from: WindowDecorActionBar.java */
    public class a extends C0 {
        public a() {
        }

        @Override // androidx.core.view.C0, androidx.core.view.B0
        public final void a(View view) {
            View view2;
            J j12 = J.this;
            if (j12.f21526o && (view2 = j12.f21518g) != null) {
                view2.setTranslationY(0.0f);
                j12.f21515d.setTranslationY(0.0f);
            }
            j12.f21515d.setVisibility(8);
            j12.f21515d.setTransitioning(false);
            j12.f21531t = null;
            b.a aVar = j12.f21522k;
            if (aVar != null) {
                ((AppCompatDelegateImpl.f) aVar).b(j12.f21521j);
                j12.f21521j = null;
                j12.f21522k = null;
            }
            ActionBarOverlayLayout actionBarOverlayLayout = j12.f21514c;
            if (actionBarOverlayLayout != null) {
                C22823h0.A(actionBarOverlayLayout);
            }
        }
    }

    /* compiled from: WindowDecorActionBar.java */
    public class b extends C0 {
        public b() {
        }

        @Override // androidx.core.view.C0, androidx.core.view.B0
        public final void a(View view) {
            J j12 = J.this;
            j12.f21531t = null;
            j12.f21515d.requestLayout();
        }
    }

    /* compiled from: WindowDecorActionBar.java */
    public class c implements D0 {
        public c() {
        }

        @Override // androidx.core.view.D0
        public final void a() {
            ((View) J.this.f21515d.getParent()).invalidate();
        }
    }

    /* compiled from: WindowDecorActionBar.java */
    @RestrictTo
    public class d extends androidx.appcompat.view.b implements h.a {

        /* renamed from: d, reason: collision with root package name */
        public final Context f21540d;

        /* renamed from: e, reason: collision with root package name */
        public final androidx.appcompat.view.menu.h f21541e;

        /* renamed from: f, reason: collision with root package name */
        public b.a f21542f;

        /* renamed from: g, reason: collision with root package name */
        public WeakReference<View> f21543g;

        public d(Context context, b.a aVar) {
            this.f21540d = context;
            this.f21542f = aVar;
            androidx.appcompat.view.menu.h hVar = new androidx.appcompat.view.menu.h(context);
            hVar.f21842l = 1;
            this.f21541e = hVar;
            hVar.f21835e = this;
        }

        @Override // androidx.appcompat.view.menu.h.a
        public final void a(@N androidx.appcompat.view.menu.h hVar) {
            if (this.f21542f == null) {
                return;
            }
            i();
            J.this.f21517f.i();
        }

        @Override // androidx.appcompat.view.menu.h.a
        public final boolean b(@N androidx.appcompat.view.menu.h hVar, @N MenuItem menuItem) {
            b.a aVar = this.f21542f;
            if (aVar != null) {
                return ((AppCompatDelegateImpl.f) aVar).f21455a.a(this, menuItem);
            }
            return false;
        }

        @Override // androidx.appcompat.view.b
        public final void c() {
            J j12 = J.this;
            if (j12.f21520i != this) {
                return;
            }
            boolean z12 = j12.f21527p;
            boolean z13 = j12.f21528q;
            if (z12 || z13) {
                j12.f21521j = this;
                j12.f21522k = this.f21542f;
            } else {
                ((AppCompatDelegateImpl.f) this.f21542f).b(this);
            }
            this.f21542f = null;
            j12.z(false);
            ActionBarContextView actionBarContextView = j12.f21517f;
            if (actionBarContextView.f21953l == null) {
                actionBarContextView.g();
            }
            j12.f21514c.setHideOnContentScrollEnabled(j12.f21533v);
            j12.f21520i = null;
        }

        @Override // androidx.appcompat.view.b
        public final View d() {
            WeakReference<View> weakReference = this.f21543g;
            if (weakReference != null) {
                return weakReference.get();
            }
            return null;
        }

        @Override // androidx.appcompat.view.b
        public final androidx.appcompat.view.menu.h e() {
            return this.f21541e;
        }

        @Override // androidx.appcompat.view.b
        public final MenuInflater f() {
            return new androidx.appcompat.view.g(this.f21540d);
        }

        @Override // androidx.appcompat.view.b
        public final CharSequence g() {
            return J.this.f21517f.getSubtitle();
        }

        @Override // androidx.appcompat.view.b
        public final CharSequence h() {
            return J.this.f21517f.getTitle();
        }

        @Override // androidx.appcompat.view.b
        public final void i() {
            if (J.this.f21520i != this) {
                return;
            }
            androidx.appcompat.view.menu.h hVar = this.f21541e;
            hVar.w();
            try {
                ((AppCompatDelegateImpl.f) this.f21542f).c(this, hVar);
            } finally {
                hVar.v();
            }
        }

        @Override // androidx.appcompat.view.b
        public final boolean j() {
            return J.this.f21517f.f21961t;
        }

        @Override // androidx.appcompat.view.b
        public final void k(View view) {
            J.this.f21517f.setCustomView(view);
            this.f21543g = new WeakReference<>(view);
        }

        @Override // androidx.appcompat.view.b
        public final void l(int i12) {
            m(J.this.f21512a.getResources().getString(i12));
        }

        @Override // androidx.appcompat.view.b
        public final void m(CharSequence charSequence) {
            J.this.f21517f.setSubtitle(charSequence);
        }

        @Override // androidx.appcompat.view.b
        public final void n(int i12) {
            o(J.this.f21512a.getResources().getString(i12));
        }

        @Override // androidx.appcompat.view.b
        public final void o(CharSequence charSequence) {
            J.this.f21517f.setTitle(charSequence);
        }

        @Override // androidx.appcompat.view.b
        public final void p(boolean z12) {
            this.f21655c = z12;
            J.this.f21517f.setTitleOptional(z12);
        }
    }

    /* compiled from: WindowDecorActionBar.java */
    @RestrictTo
    public class e extends AbstractC19927a.f {
    }

    public J(Activity activity, boolean z12) {
        new ArrayList();
        this.f21524m = new ArrayList<>();
        this.f21525n = 0;
        this.f21526o = true;
        this.f21530s = true;
        this.f21534w = new a();
        this.f21535x = new b();
        this.f21536y = new c();
        View decorView = activity.getWindow().getDecorView();
        A(decorView);
        if (z12) {
            return;
        }
        this.f21518g = decorView.findViewById(R.id.content);
    }

    public final void A(View view) {
        U wrapper;
        ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) view.findViewById(com.avito.android.R.id.decor_content_parent);
        this.f21514c = actionBarOverlayLayout;
        if (actionBarOverlayLayout != null) {
            actionBarOverlayLayout.setActionBarVisibilityCallback(this);
        }
        KeyEvent.Callback callbackFindViewById = view.findViewById(com.avito.android.R.id.action_bar);
        if (callbackFindViewById instanceof U) {
            wrapper = (U) callbackFindViewById;
        } else {
            if (!(callbackFindViewById instanceof Toolbar)) {
                throw new IllegalStateException("Can't make a decor toolbar out of ".concat(callbackFindViewById != null ? callbackFindViewById.getClass().getSimpleName() : "null"));
            }
            wrapper = ((Toolbar) callbackFindViewById).getWrapper();
        }
        this.f21516e = wrapper;
        this.f21517f = (ActionBarContextView) view.findViewById(com.avito.android.R.id.action_context_bar);
        ActionBarContainer actionBarContainer = (ActionBarContainer) view.findViewById(com.avito.android.R.id.action_bar_container);
        this.f21515d = actionBarContainer;
        U u12 = this.f21516e;
        if (u12 == null || this.f21517f == null || actionBarContainer == null) {
            throw new IllegalStateException(getClass().getSimpleName().concat(" can only be used with a compatible window decor layout"));
        }
        this.f21512a = u12.getContext();
        if ((this.f21516e.l() & 4) != 0) {
            this.f21519h = true;
        }
        Context context = this.f21512a;
        androidx.appcompat.view.a aVar = new androidx.appcompat.view.a();
        aVar.f21653a = context;
        int i12 = context.getApplicationInfo().targetSdkVersion;
        this.f21516e.getClass();
        B(aVar.f21653a.getResources().getBoolean(com.avito.android.R.bool.abc_action_bar_embed_tabs));
        TypedArray typedArrayObtainStyledAttributes = this.f21512a.obtainStyledAttributes(null, C43521a.m.f413352a, com.avito.android.R.attr.actionBarStyle, 0);
        if (typedArrayObtainStyledAttributes.getBoolean(14, false)) {
            ActionBarOverlayLayout actionBarOverlayLayout2 = this.f21514c;
            if (!actionBarOverlayLayout2.f21976h) {
                throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to enable hide on content scroll");
            }
            this.f21533v = true;
            actionBarOverlayLayout2.setHideOnContentScrollEnabled(true);
        }
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(12, 0);
        if (dimensionPixelSize != 0) {
            C22823h0.H(this.f21515d, dimensionPixelSize);
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    public final void B(boolean z12) {
        if (z12) {
            this.f21515d.setTabContainer(null);
            this.f21516e.k();
        } else {
            this.f21516e.k();
            this.f21515d.setTabContainer(null);
        }
        this.f21516e.getClass();
        this.f21516e.i(false);
        this.f21514c.setHasNonEmbeddedTabs(false);
    }

    public final void C(boolean z12) {
        boolean z13 = this.f21529r || !(this.f21527p || this.f21528q);
        D0 d02 = this.f21536y;
        View view = this.f21518g;
        if (!z13) {
            if (this.f21530s) {
                this.f21530s = false;
                androidx.appcompat.view.h hVar = this.f21531t;
                if (hVar != null) {
                    hVar.a();
                }
                int i12 = this.f21525n;
                B0 b02 = this.f21534w;
                if (i12 != 0 || (!this.f21532u && !z12)) {
                    ((a) b02).a(null);
                    return;
                }
                this.f21515d.setAlpha(1.0f);
                this.f21515d.setTransitioning(true);
                androidx.appcompat.view.h hVar2 = new androidx.appcompat.view.h();
                float f12 = -this.f21515d.getHeight();
                if (z12) {
                    this.f21515d.getLocationInWindow(new int[]{0, 0});
                    f12 -= r12[1];
                }
                A0 a0A = C22823h0.a(this.f21515d);
                a0A.g(f12);
                View view2 = a0A.f44903a.get();
                if (view2 != null) {
                    view2.animate().setUpdateListener(d02 != null ? new y0(0, d02, view2) : null);
                }
                boolean z14 = hVar2.f21718e;
                ArrayList<A0> arrayList = hVar2.f21714a;
                if (!z14) {
                    arrayList.add(a0A);
                }
                if (this.f21526o && view != null) {
                    A0 a0A2 = C22823h0.a(view);
                    a0A2.g(f12);
                    if (!hVar2.f21718e) {
                        arrayList.add(a0A2);
                    }
                }
                AccelerateInterpolator accelerateInterpolator = f21511z;
                boolean z15 = hVar2.f21718e;
                if (!z15) {
                    hVar2.f21716c = accelerateInterpolator;
                }
                if (!z15) {
                    hVar2.f21715b = 250L;
                }
                C0 c02 = (C0) b02;
                if (!z15) {
                    hVar2.f21717d = c02;
                }
                this.f21531t = hVar2;
                hVar2.b();
                return;
            }
            return;
        }
        if (this.f21530s) {
            return;
        }
        this.f21530s = true;
        androidx.appcompat.view.h hVar3 = this.f21531t;
        if (hVar3 != null) {
            hVar3.a();
        }
        this.f21515d.setVisibility(0);
        int i13 = this.f21525n;
        B0 b03 = this.f21535x;
        if (i13 == 0 && (this.f21532u || z12)) {
            this.f21515d.setTranslationY(0.0f);
            float f13 = -this.f21515d.getHeight();
            if (z12) {
                this.f21515d.getLocationInWindow(new int[]{0, 0});
                f13 -= r12[1];
            }
            this.f21515d.setTranslationY(f13);
            androidx.appcompat.view.h hVar4 = new androidx.appcompat.view.h();
            A0 a0A3 = C22823h0.a(this.f21515d);
            a0A3.g(0.0f);
            View view3 = a0A3.f44903a.get();
            if (view3 != null) {
                view3.animate().setUpdateListener(d02 != null ? new y0(0, d02, view3) : null);
            }
            boolean z16 = hVar4.f21718e;
            ArrayList<A0> arrayList2 = hVar4.f21714a;
            if (!z16) {
                arrayList2.add(a0A3);
            }
            if (this.f21526o && view != null) {
                view.setTranslationY(f13);
                A0 a0A4 = C22823h0.a(view);
                a0A4.g(0.0f);
                if (!hVar4.f21718e) {
                    arrayList2.add(a0A4);
                }
            }
            DecelerateInterpolator decelerateInterpolator = f21510A;
            boolean z17 = hVar4.f21718e;
            if (!z17) {
                hVar4.f21716c = decelerateInterpolator;
            }
            if (!z17) {
                hVar4.f21715b = 250L;
            }
            C0 c03 = (C0) b03;
            if (!z17) {
                hVar4.f21717d = c03;
            }
            this.f21531t = hVar4;
            hVar4.b();
        } else {
            this.f21515d.setAlpha(1.0f);
            this.f21515d.setTranslationY(0.0f);
            if (this.f21526o && view != null) {
                view.setTranslationY(0.0f);
            }
            ((b) b03).a(null);
        }
        ActionBarOverlayLayout actionBarOverlayLayout = this.f21514c;
        if (actionBarOverlayLayout != null) {
            C22823h0.A(actionBarOverlayLayout);
        }
    }

    @Override // androidx.appcompat.widget.ActionBarOverlayLayout.d
    public final void a() {
        androidx.appcompat.view.h hVar = this.f21531t;
        if (hVar != null) {
            hVar.a();
            this.f21531t = null;
        }
    }

    @Override // androidx.appcompat.widget.ActionBarOverlayLayout.d
    public final void b(int i12) {
        this.f21525n = i12;
    }

    @Override // androidx.appcompat.widget.ActionBarOverlayLayout.d
    public final void c(boolean z12) {
        this.f21526o = z12;
    }

    @Override // androidx.appcompat.widget.ActionBarOverlayLayout.d
    public final void d() {
        if (this.f21528q) {
            this.f21528q = false;
            C(true);
        }
    }

    @Override // androidx.appcompat.widget.ActionBarOverlayLayout.d
    public final void e() {
        if (this.f21528q) {
            return;
        }
        this.f21528q = true;
        C(true);
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final boolean g() {
        U u12 = this.f21516e;
        if (u12 == null || !u12.o()) {
            return false;
        }
        this.f21516e.collapseActionView();
        return true;
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final void h(boolean z12) {
        if (z12 == this.f21523l) {
            return;
        }
        this.f21523l = z12;
        ArrayList<AbstractC19927a.d> arrayList = this.f21524m;
        int size = arrayList.size();
        for (int i12 = 0; i12 < size; i12++) {
            arrayList.get(i12).a();
        }
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final int i() {
        return this.f21516e.l();
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final Context j() {
        if (this.f21513b == null) {
            TypedValue typedValue = new TypedValue();
            this.f21512a.getTheme().resolveAttribute(com.avito.android.R.attr.actionBarWidgetTheme, typedValue, true);
            int i12 = typedValue.resourceId;
            if (i12 != 0) {
                this.f21513b = new ContextThemeWrapper(this.f21512a, i12);
            } else {
                this.f21513b = this.f21512a;
            }
        }
        return this.f21513b;
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final void k() {
        if (this.f21527p) {
            return;
        }
        this.f21527p = true;
        C(false);
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final void m() {
        Context context = this.f21512a;
        new androidx.appcompat.view.a().f21653a = context;
        B(context.getResources().getBoolean(com.avito.android.R.bool.abc_action_bar_embed_tabs));
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final boolean o(int i12, KeyEvent keyEvent) {
        androidx.appcompat.view.menu.h hVar;
        d dVar = this.f21520i;
        if (dVar == null || (hVar = dVar.f21541e) == null) {
            return false;
        }
        hVar.setQwertyMode(KeyCharacterMap.load(keyEvent.getDeviceId()).getKeyboardType() != 1);
        return hVar.performShortcut(i12, keyEvent, 0);
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final void r(boolean z12) {
        if (this.f21519h) {
            return;
        }
        s(z12);
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final void s(boolean z12) {
        int i12 = z12 ? 4 : 0;
        int iL2 = this.f21516e.l();
        this.f21519h = true;
        this.f21516e.h((i12 & 4) | (iL2 & (-5)));
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final void t() {
        this.f21516e.h(this.f21516e.l() & (-9));
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final void u(int i12) {
        this.f21516e.n(i12);
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final void v(boolean z12) {
        androidx.appcompat.view.h hVar;
        this.f21532u = z12;
        if (z12 || (hVar = this.f21531t) == null) {
            return;
        }
        hVar.a();
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final void w(CharSequence charSequence) {
        this.f21516e.setTitle(charSequence);
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final void x(CharSequence charSequence) {
        this.f21516e.setWindowTitle(charSequence);
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final androidx.appcompat.view.b y(b.a aVar) {
        d dVar = this.f21520i;
        if (dVar != null) {
            dVar.c();
        }
        this.f21514c.setHideOnContentScrollEnabled(false);
        this.f21517f.g();
        d dVar2 = new d(this.f21517f.getContext(), aVar);
        androidx.appcompat.view.menu.h hVar = dVar2.f21541e;
        hVar.w();
        try {
            if (!((AppCompatDelegateImpl.f) dVar2.f21542f).f21455a.e(dVar2, hVar)) {
                return null;
            }
            this.f21520i = dVar2;
            dVar2.i();
            this.f21517f.e(dVar2);
            z(true);
            return dVar2;
        } finally {
            hVar.v();
        }
    }

    public final void z(boolean z12) {
        A0 a0M;
        A0 a0H;
        if (z12) {
            if (!this.f21529r) {
                this.f21529r = true;
                ActionBarOverlayLayout actionBarOverlayLayout = this.f21514c;
                if (actionBarOverlayLayout != null) {
                    actionBarOverlayLayout.setShowingForActionMode(true);
                }
                C(false);
            }
        } else if (this.f21529r) {
            this.f21529r = false;
            ActionBarOverlayLayout actionBarOverlayLayout2 = this.f21514c;
            if (actionBarOverlayLayout2 != null) {
                actionBarOverlayLayout2.setShowingForActionMode(false);
            }
            C(false);
        }
        if (!this.f21515d.isLaidOut()) {
            if (z12) {
                this.f21516e.setVisibility(4);
                this.f21517f.setVisibility(0);
                return;
            } else {
                this.f21516e.setVisibility(0);
                this.f21517f.setVisibility(8);
                return;
            }
        }
        if (z12) {
            a0H = this.f21516e.m(4, 100L);
            a0M = this.f21517f.h(0, 200L);
        } else {
            a0M = this.f21516e.m(0, 200L);
            a0H = this.f21517f.h(8, 100L);
        }
        androidx.appcompat.view.h hVar = new androidx.appcompat.view.h();
        ArrayList<A0> arrayList = hVar.f21714a;
        arrayList.add(a0H);
        View view = a0H.f44903a.get();
        long duration = view != null ? view.animate().getDuration() : 0L;
        View view2 = a0M.f44903a.get();
        if (view2 != null) {
            view2.animate().setStartDelay(duration);
        }
        arrayList.add(a0M);
        hVar.b();
    }

    public J(Dialog dialog) {
        new ArrayList();
        this.f21524m = new ArrayList<>();
        this.f21525n = 0;
        this.f21526o = true;
        this.f21530s = true;
        this.f21534w = new a();
        this.f21535x = new b();
        this.f21536y = new c();
        A(dialog.getWindow().getDecorView());
    }
}
