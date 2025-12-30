package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.view.menu.o;
import androidx.appcompat.widget.C19952h0;
import androidx.appcompat.widget.W;
import com.avito.android.R;
import java.lang.reflect.InvocationTargetException;

/* compiled from: StandardMenuPopup.java */
/* loaded from: classes.dex */
final class s extends m implements PopupWindow.OnDismissListener, AdapterView.OnItemClickListener, o, View.OnKeyListener {

    /* renamed from: c, reason: collision with root package name */
    public final Context f21912c;

    /* renamed from: d, reason: collision with root package name */
    public final h f21913d;

    /* renamed from: e, reason: collision with root package name */
    public final g f21914e;

    /* renamed from: f, reason: collision with root package name */
    public final boolean f21915f;

    /* renamed from: g, reason: collision with root package name */
    public final int f21916g;

    /* renamed from: h, reason: collision with root package name */
    public final int f21917h;

    /* renamed from: i, reason: collision with root package name */
    public final C19952h0 f21918i;

    /* renamed from: l, reason: collision with root package name */
    public PopupWindow.OnDismissListener f21921l;

    /* renamed from: m, reason: collision with root package name */
    public View f21922m;

    /* renamed from: n, reason: collision with root package name */
    public View f21923n;

    /* renamed from: o, reason: collision with root package name */
    public o.a f21924o;

    /* renamed from: p, reason: collision with root package name */
    public ViewTreeObserver f21925p;

    /* renamed from: q, reason: collision with root package name */
    public boolean f21926q;

    /* renamed from: r, reason: collision with root package name */
    public boolean f21927r;

    /* renamed from: s, reason: collision with root package name */
    public int f21928s;

    /* renamed from: u, reason: collision with root package name */
    public boolean f21930u;

    /* renamed from: j, reason: collision with root package name */
    public final ViewTreeObserver.OnGlobalLayoutListener f21919j = new a();

    /* renamed from: k, reason: collision with root package name */
    public final View.OnAttachStateChangeListener f21920k = new b();

    /* renamed from: t, reason: collision with root package name */
    public int f21929t = 0;

    /* compiled from: StandardMenuPopup.java */
    public class a implements ViewTreeObserver.OnGlobalLayoutListener {
        public a() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public final void onGlobalLayout() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            s sVar = s.this;
            if (sVar.a()) {
                C19952h0 c19952h0 = sVar.f21918i;
                if (c19952h0.f22530z) {
                    return;
                }
                View view = sVar.f21923n;
                if (view == null || !view.isShown()) {
                    sVar.dismiss();
                } else {
                    c19952h0.show();
                }
            }
        }
    }

    public s(int i12, Context context, View view, h hVar, boolean z12) {
        this.f21912c = context;
        this.f21913d = hVar;
        this.f21915f = z12;
        this.f21914e = new g(hVar, LayoutInflater.from(context), z12, R.layout.abc_popup_menu_item_layout);
        this.f21917h = i12;
        Resources resources = context.getResources();
        this.f21916g = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
        this.f21922m = view;
        this.f21918i = new C19952h0(context, null, i12);
        hVar.b(this, context);
    }

    @Override // androidx.appcompat.view.menu.r
    public final boolean a() {
        return !this.f21926q && this.f21918i.f22505A.isShowing();
    }

    @Override // androidx.appcompat.view.menu.o
    public final void b(h hVar, boolean z12) {
        if (hVar != this.f21913d) {
            return;
        }
        dismiss();
        o.a aVar = this.f21924o;
        if (aVar != null) {
            aVar.b(hVar, z12);
        }
    }

    @Override // androidx.appcompat.view.menu.o
    public final void c(boolean z12) {
        this.f21927r = false;
        g gVar = this.f21914e;
        if (gVar != null) {
            gVar.notifyDataSetChanged();
        }
    }

    @Override // androidx.appcompat.view.menu.r
    public final ListView d() {
        return this.f21918i.f22508d;
    }

    @Override // androidx.appcompat.view.menu.r
    public final void dismiss() {
        if (a()) {
            this.f21918i.dismiss();
        }
    }

    @Override // androidx.appcompat.view.menu.o
    public final boolean f(t tVar) {
        if (tVar.hasVisibleItems()) {
            View view = this.f21923n;
            n nVar = new n(this.f21917h, this.f21912c, view, tVar, this.f21915f);
            o.a aVar = this.f21924o;
            nVar.f21906h = aVar;
            m mVar = nVar.f21907i;
            if (mVar != null) {
                mVar.j(aVar);
            }
            nVar.d(m.t(tVar));
            nVar.f21908j = this.f21921l;
            this.f21921l = null;
            this.f21913d.c(false);
            C19952h0 c19952h0 = this.f21918i;
            int width = c19952h0.f22511g;
            int iJ2 = c19952h0.j();
            if ((Gravity.getAbsoluteGravity(this.f21929t, this.f21922m.getLayoutDirection()) & 7) == 5) {
                width += this.f21922m.getWidth();
            }
            if (!nVar.b()) {
                if (nVar.f21903e != null) {
                    nVar.e(width, iJ2, true, true);
                }
            }
            o.a aVar2 = this.f21924o;
            if (aVar2 != null) {
                aVar2.c(tVar);
            }
            return true;
        }
        return false;
    }

    @Override // androidx.appcompat.view.menu.o
    public final boolean h() {
        return false;
    }

    @Override // androidx.appcompat.view.menu.o
    public final void j(o.a aVar) {
        this.f21924o = aVar;
    }

    @Override // androidx.appcompat.view.menu.m
    public final void m(View view) {
        this.f21922m = view;
    }

    @Override // androidx.appcompat.view.menu.m
    public final void n(boolean z12) {
        this.f21914e.f21826d = z12;
    }

    @Override // androidx.appcompat.view.menu.m
    public final void o(int i12) {
        this.f21929t = i12;
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public final void onDismiss() {
        this.f21926q = true;
        this.f21913d.c(true);
        ViewTreeObserver viewTreeObserver = this.f21925p;
        if (viewTreeObserver != null) {
            if (!viewTreeObserver.isAlive()) {
                this.f21925p = this.f21923n.getViewTreeObserver();
            }
            this.f21925p.removeGlobalOnLayoutListener(this.f21919j);
            this.f21925p = null;
        }
        this.f21923n.removeOnAttachStateChangeListener(this.f21920k);
        PopupWindow.OnDismissListener onDismissListener = this.f21921l;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    @Override // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i12, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 1 || i12 != 82) {
            return false;
        }
        dismiss();
        return true;
    }

    @Override // androidx.appcompat.view.menu.m
    public final void p(int i12) {
        this.f21918i.f22511g = i12;
    }

    @Override // androidx.appcompat.view.menu.m
    public final void q(PopupWindow.OnDismissListener onDismissListener) {
        this.f21921l = onDismissListener;
    }

    @Override // androidx.appcompat.view.menu.m
    public final void r(boolean z12) {
        this.f21930u = z12;
    }

    @Override // androidx.appcompat.view.menu.m
    public final void s(int i12) {
        this.f21918i.h(i12);
    }

    @Override // androidx.appcompat.view.menu.r
    public final void show() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        View view;
        if (a()) {
            return;
        }
        if (this.f21926q || (view = this.f21922m) == null) {
            throw new IllegalStateException("StandardMenuPopup cannot be used without an anchor");
        }
        this.f21923n = view;
        C19952h0 c19952h0 = this.f21918i;
        c19952h0.f22505A.setOnDismissListener(this);
        c19952h0.f22521q = this;
        c19952h0.r();
        View view2 = this.f21923n;
        boolean z12 = this.f21925p == null;
        ViewTreeObserver viewTreeObserver = view2.getViewTreeObserver();
        this.f21925p = viewTreeObserver;
        if (z12) {
            viewTreeObserver.addOnGlobalLayoutListener(this.f21919j);
        }
        view2.addOnAttachStateChangeListener(this.f21920k);
        c19952h0.f22520p = view2;
        c19952h0.f22517m = this.f21929t;
        boolean z13 = this.f21927r;
        Context context = this.f21912c;
        g gVar = this.f21914e;
        if (!z13) {
            this.f21928s = m.l(gVar, context, this.f21916g);
            this.f21927r = true;
        }
        c19952h0.q(this.f21928s);
        c19952h0.f22505A.setInputMethodMode(2);
        Rect rect = this.f21898b;
        c19952h0.f22529y = rect != null ? new Rect(rect) : null;
        c19952h0.show();
        W w12 = c19952h0.f22508d;
        w12.setOnKeyListener(this);
        if (this.f21930u) {
            h hVar = this.f21913d;
            if (hVar.f21843m != null) {
                FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.abc_popup_menu_header_item_layout, (ViewGroup) w12, false);
                TextView textView = (TextView) frameLayout.findViewById(android.R.id.title);
                if (textView != null) {
                    textView.setText(hVar.f21843m);
                }
                frameLayout.setEnabled(false);
                w12.addHeaderView(frameLayout, null, false);
            }
        }
        c19952h0.o(gVar);
        c19952h0.show();
    }

    /* compiled from: StandardMenuPopup.java */
    public class b implements View.OnAttachStateChangeListener {
        public b() {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewDetachedFromWindow(View view) {
            s sVar = s.this;
            ViewTreeObserver viewTreeObserver = sVar.f21925p;
            if (viewTreeObserver != null) {
                if (!viewTreeObserver.isAlive()) {
                    sVar.f21925p = view.getViewTreeObserver();
                }
                sVar.f21925p.removeGlobalOnLayoutListener(sVar.f21919j);
            }
            view.removeOnAttachStateChangeListener(this);
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewAttachedToWindow(View view) {
        }
    }

    @Override // androidx.appcompat.view.menu.m
    public final void k(h hVar) {
    }
}
