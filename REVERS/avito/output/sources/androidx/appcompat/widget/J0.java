package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.annotation.RestrictTo;
import androidx.appcompat.view.menu.o;
import androidx.appcompat.widget.ActionMenuPresenter;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.Toolbar.f;
import androidx.core.view.C22823h0;
import com.avito.android.R;
import l.C43521a;
import m.C43852a;

/* compiled from: ToolbarWidgetWrapper.java */
@RestrictTo
/* loaded from: classes.dex */
public class J0 implements U {

    /* renamed from: a, reason: collision with root package name */
    public final Toolbar f22165a;

    /* renamed from: b, reason: collision with root package name */
    public int f22166b;

    /* renamed from: c, reason: collision with root package name */
    public final View f22167c;

    /* renamed from: d, reason: collision with root package name */
    public Drawable f22168d;

    /* renamed from: e, reason: collision with root package name */
    public Drawable f22169e;

    /* renamed from: f, reason: collision with root package name */
    public Drawable f22170f;

    /* renamed from: g, reason: collision with root package name */
    public boolean f22171g;

    /* renamed from: h, reason: collision with root package name */
    public CharSequence f22172h;

    /* renamed from: i, reason: collision with root package name */
    public final CharSequence f22173i;

    /* renamed from: j, reason: collision with root package name */
    public final CharSequence f22174j;

    /* renamed from: k, reason: collision with root package name */
    public Window.Callback f22175k;

    /* renamed from: l, reason: collision with root package name */
    public boolean f22176l;

    /* renamed from: m, reason: collision with root package name */
    public ActionMenuPresenter f22177m;

    /* renamed from: n, reason: collision with root package name */
    public final int f22178n;

    /* renamed from: o, reason: collision with root package name */
    public final Drawable f22179o;

    /* compiled from: ToolbarWidgetWrapper.java */
    public class a extends androidx.core.view.C0 {

        /* renamed from: a, reason: collision with root package name */
        public boolean f22180a = false;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ int f22181b;

        public a(int i12) {
            this.f22181b = i12;
        }

        @Override // androidx.core.view.C0, androidx.core.view.B0
        public final void a(View view) {
            if (this.f22180a) {
                return;
            }
            J0.this.f22165a.setVisibility(this.f22181b);
        }

        @Override // androidx.core.view.C0, androidx.core.view.B0
        public final void b(View view) {
            J0.this.f22165a.setVisibility(0);
        }

        @Override // androidx.core.view.C0, androidx.core.view.B0
        public final void c(View view) {
            this.f22180a = true;
        }
    }

    public J0(Toolbar toolbar, boolean z12) {
        Drawable drawable;
        this.f22178n = 0;
        this.f22165a = toolbar;
        this.f22172h = toolbar.getTitle();
        this.f22173i = toolbar.getSubtitle();
        this.f22171g = this.f22172h != null;
        this.f22170f = toolbar.getNavigationIcon();
        D0 d0E = D0.e(toolbar.getContext(), null, C43521a.m.f413352a, R.attr.actionBarStyle);
        int i12 = 15;
        this.f22179o = d0E.b(15);
        if (z12) {
            TypedArray typedArray = d0E.f22121b;
            CharSequence text = typedArray.getText(27);
            if (!TextUtils.isEmpty(text)) {
                setTitle(text);
            }
            CharSequence text2 = typedArray.getText(25);
            if (!TextUtils.isEmpty(text2)) {
                this.f22173i = text2;
                if ((this.f22166b & 8) != 0) {
                    toolbar.setSubtitle(text2);
                }
            }
            Drawable drawableB = d0E.b(20);
            if (drawableB != null) {
                this.f22169e = drawableB;
                s();
            }
            Drawable drawableB2 = d0E.b(17);
            if (drawableB2 != null) {
                setIcon(drawableB2);
            }
            if (this.f22170f == null && (drawable = this.f22179o) != null) {
                q(drawable);
            }
            h(typedArray.getInt(10, 0));
            int resourceId = typedArray.getResourceId(9, 0);
            if (resourceId != 0) {
                View viewInflate = LayoutInflater.from(toolbar.getContext()).inflate(resourceId, (ViewGroup) toolbar, false);
                View view = this.f22167c;
                if (view != null && (this.f22166b & 16) != 0) {
                    toolbar.removeView(view);
                }
                this.f22167c = viewInflate;
                if (viewInflate != null && (this.f22166b & 16) != 0) {
                    toolbar.addView(viewInflate);
                }
                h(this.f22166b | 16);
            }
            int layoutDimension = typedArray.getLayoutDimension(13, 0);
            if (layoutDimension > 0) {
                ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
                layoutParams.height = layoutDimension;
                toolbar.setLayoutParams(layoutParams);
            }
            int dimensionPixelOffset = typedArray.getDimensionPixelOffset(7, -1);
            int dimensionPixelOffset2 = typedArray.getDimensionPixelOffset(3, -1);
            if (dimensionPixelOffset >= 0 || dimensionPixelOffset2 >= 0) {
                int iMax = Math.max(dimensionPixelOffset, 0);
                int iMax2 = Math.max(dimensionPixelOffset2, 0);
                if (toolbar.f22408u == null) {
                    toolbar.f22408u = new C19966o0();
                }
                toolbar.f22408u.a(iMax, iMax2);
            }
            int resourceId2 = typedArray.getResourceId(28, 0);
            if (resourceId2 != 0) {
                Context context = toolbar.getContext();
                toolbar.f22400m = resourceId2;
                AppCompatTextView appCompatTextView = toolbar.f22390c;
                if (appCompatTextView != null) {
                    appCompatTextView.setTextAppearance(context, resourceId2);
                }
            }
            int resourceId3 = typedArray.getResourceId(26, 0);
            if (resourceId3 != 0) {
                Context context2 = toolbar.getContext();
                toolbar.f22401n = resourceId3;
                AppCompatTextView appCompatTextView2 = toolbar.f22391d;
                if (appCompatTextView2 != null) {
                    appCompatTextView2.setTextAppearance(context2, resourceId3);
                }
            }
            int resourceId4 = typedArray.getResourceId(22, 0);
            if (resourceId4 != 0) {
                toolbar.setPopupTheme(resourceId4);
            }
        } else {
            if (toolbar.getNavigationIcon() != null) {
                this.f22179o = toolbar.getNavigationIcon();
            } else {
                i12 = 11;
            }
            this.f22166b = i12;
        }
        d0E.f();
        if (R.string.abc_action_bar_up_description != this.f22178n) {
            this.f22178n = R.string.abc_action_bar_up_description;
            if (TextUtils.isEmpty(toolbar.getNavigationContentDescription())) {
                int i13 = this.f22178n;
                this.f22174j = i13 != 0 ? this.f22165a.getContext().getString(i13) : null;
                r();
            }
        }
        this.f22174j = toolbar.getNavigationContentDescription();
        toolbar.setNavigationOnClickListener(new I0(this));
    }

    @Override // androidx.appcompat.widget.U
    public final boolean a() {
        ActionMenuPresenter actionMenuPresenter;
        ActionMenuView actionMenuView = this.f22165a.f22389b;
        return (actionMenuView == null || (actionMenuPresenter = actionMenuView.f22028u) == null || !actionMenuPresenter.m()) ? false : true;
    }

    @Override // androidx.appcompat.widget.U
    public final boolean b() {
        ActionMenuPresenter actionMenuPresenter;
        ActionMenuView actionMenuView = this.f22165a.f22389b;
        return (actionMenuView == null || (actionMenuPresenter = actionMenuView.f22028u) == null || (actionMenuPresenter.f22010v == null && !actionMenuPresenter.n())) ? false : true;
    }

    @Override // androidx.appcompat.widget.U
    public final boolean c() {
        ActionMenuView actionMenuView;
        Toolbar toolbar = this.f22165a;
        return toolbar.getVisibility() == 0 && (actionMenuView = toolbar.f22389b) != null && actionMenuView.f22027t;
    }

    @Override // androidx.appcompat.widget.U
    public final void collapseActionView() {
        Toolbar.f fVar = this.f22165a.f22381N;
        androidx.appcompat.view.menu.k kVar = fVar == null ? null : fVar.f22421c;
        if (kVar != null) {
            kVar.collapseActionView();
        }
    }

    @Override // androidx.appcompat.widget.U
    public final boolean d() {
        return this.f22165a.w();
    }

    @Override // androidx.appcompat.widget.U
    public final boolean e() {
        ActionMenuPresenter actionMenuPresenter;
        ActionMenuView actionMenuView = this.f22165a.f22389b;
        return (actionMenuView == null || (actionMenuPresenter = actionMenuView.f22028u) == null || !actionMenuPresenter.n()) ? false : true;
    }

    @Override // androidx.appcompat.widget.U
    public final void f(androidx.appcompat.view.menu.h hVar, o.a aVar) {
        ActionMenuPresenter actionMenuPresenter = this.f22177m;
        Toolbar toolbar = this.f22165a;
        if (actionMenuPresenter == null) {
            this.f22177m = new ActionMenuPresenter(toolbar.getContext());
        }
        ActionMenuPresenter actionMenuPresenter2 = this.f22177m;
        actionMenuPresenter2.f21774f = aVar;
        if (hVar == null && toolbar.f22389b == null) {
            return;
        }
        toolbar.e();
        androidx.appcompat.view.menu.h hVar2 = toolbar.f22389b.f22024q;
        if (hVar2 == hVar) {
            return;
        }
        if (hVar2 != null) {
            hVar2.r(toolbar.f22380M);
            hVar2.r(toolbar.f22381N);
        }
        if (toolbar.f22381N == null) {
            toolbar.f22381N = toolbar.new f();
        }
        actionMenuPresenter2.f22006r = true;
        if (hVar != null) {
            hVar.b(actionMenuPresenter2, toolbar.f22398k);
            hVar.b(toolbar.f22381N, toolbar.f22398k);
        } else {
            actionMenuPresenter2.i(toolbar.f22398k, null);
            toolbar.f22381N.i(toolbar.f22398k, null);
            actionMenuPresenter2.c(true);
            toolbar.f22381N.c(true);
        }
        toolbar.f22389b.setPopupTheme(toolbar.f22399l);
        toolbar.f22389b.setPresenter(actionMenuPresenter2);
        toolbar.f22380M = actionMenuPresenter2;
        toolbar.x();
    }

    @Override // androidx.appcompat.widget.U
    public final void g() {
        this.f22176l = true;
    }

    @Override // androidx.appcompat.widget.U
    public final Context getContext() {
        return this.f22165a.getContext();
    }

    @Override // androidx.appcompat.widget.U
    public final CharSequence getTitle() {
        return this.f22165a.getTitle();
    }

    @Override // androidx.appcompat.widget.U
    public final void h(int i12) {
        View view;
        int i13 = this.f22166b ^ i12;
        this.f22166b = i12;
        if (i13 != 0) {
            if ((i13 & 4) != 0) {
                if ((i12 & 4) != 0) {
                    r();
                }
                int i14 = this.f22166b & 4;
                Toolbar toolbar = this.f22165a;
                if (i14 != 0) {
                    Drawable drawable = this.f22170f;
                    if (drawable == null) {
                        drawable = this.f22179o;
                    }
                    toolbar.setNavigationIcon(drawable);
                } else {
                    toolbar.setNavigationIcon((Drawable) null);
                }
            }
            if ((i13 & 3) != 0) {
                s();
            }
            int i15 = i13 & 8;
            Toolbar toolbar2 = this.f22165a;
            if (i15 != 0) {
                if ((i12 & 8) != 0) {
                    toolbar2.setTitle(this.f22172h);
                    toolbar2.setSubtitle(this.f22173i);
                } else {
                    toolbar2.setTitle((CharSequence) null);
                    toolbar2.setSubtitle((CharSequence) null);
                }
            }
            if ((i13 & 16) == 0 || (view = this.f22167c) == null) {
                return;
            }
            if ((i12 & 16) != 0) {
                toolbar2.addView(view);
            } else {
                toolbar2.removeView(view);
            }
        }
    }

    @Override // androidx.appcompat.widget.U
    public final void i(boolean z12) {
        this.f22165a.setCollapsible(z12);
    }

    @Override // androidx.appcompat.widget.U
    public final void j() {
        ActionMenuPresenter actionMenuPresenter;
        ActionMenuView actionMenuView = this.f22165a.f22389b;
        if (actionMenuView == null || (actionMenuPresenter = actionMenuView.f22028u) == null) {
            return;
        }
        actionMenuPresenter.m();
        ActionMenuPresenter.a aVar = actionMenuPresenter.f22009u;
        if (aVar == null || !aVar.b()) {
            return;
        }
        aVar.f21907i.dismiss();
    }

    @Override // androidx.appcompat.widget.U
    public final int l() {
        return this.f22166b;
    }

    @Override // androidx.appcompat.widget.U
    public final androidx.core.view.A0 m(int i12, long j12) {
        androidx.core.view.A0 a0A = C22823h0.a(this.f22165a);
        a0A.a(i12 == 0 ? 1.0f : 0.0f);
        a0A.c(j12);
        a0A.e(new a(i12));
        return a0A;
    }

    @Override // androidx.appcompat.widget.U
    public final void n(int i12) {
        q(i12 != 0 ? C43852a.a(this.f22165a.getContext(), i12) : null);
    }

    @Override // androidx.appcompat.widget.U
    public final boolean o() {
        return this.f22165a.l();
    }

    @Override // androidx.appcompat.widget.U
    public final void p(int i12) {
        this.f22169e = i12 != 0 ? C43852a.a(this.f22165a.getContext(), i12) : null;
        s();
    }

    public final void q(Drawable drawable) {
        this.f22170f = drawable;
        int i12 = this.f22166b & 4;
        Toolbar toolbar = this.f22165a;
        if (i12 == 0) {
            toolbar.setNavigationIcon((Drawable) null);
            return;
        }
        if (drawable == null) {
            drawable = this.f22179o;
        }
        toolbar.setNavigationIcon(drawable);
    }

    public final void r() {
        if ((this.f22166b & 4) != 0) {
            boolean zIsEmpty = TextUtils.isEmpty(this.f22174j);
            Toolbar toolbar = this.f22165a;
            if (zIsEmpty) {
                toolbar.setNavigationContentDescription(this.f22178n);
            } else {
                toolbar.setNavigationContentDescription(this.f22174j);
            }
        }
    }

    public final void s() {
        Drawable drawable;
        int i12 = this.f22166b;
        if ((i12 & 2) == 0) {
            drawable = null;
        } else if ((i12 & 1) == 0 || (drawable = this.f22169e) == null) {
            drawable = this.f22168d;
        }
        this.f22165a.setLogo(drawable);
    }

    @Override // androidx.appcompat.widget.U
    public final void setIcon(int i12) {
        setIcon(i12 != 0 ? C43852a.a(this.f22165a.getContext(), i12) : null);
    }

    @Override // androidx.appcompat.widget.U
    public final void setTitle(CharSequence charSequence) {
        this.f22171g = true;
        this.f22172h = charSequence;
        if ((this.f22166b & 8) != 0) {
            Toolbar toolbar = this.f22165a;
            toolbar.setTitle(charSequence);
            if (this.f22171g) {
                C22823h0.E(toolbar.getRootView(), charSequence);
            }
        }
    }

    @Override // androidx.appcompat.widget.U
    public final void setVisibility(int i12) {
        this.f22165a.setVisibility(i12);
    }

    @Override // androidx.appcompat.widget.U
    public final void setWindowCallback(Window.Callback callback) {
        this.f22175k = callback;
    }

    @Override // androidx.appcompat.widget.U
    public final void setWindowTitle(CharSequence charSequence) {
        if (this.f22171g) {
            return;
        }
        this.f22172h = charSequence;
        if ((this.f22166b & 8) != 0) {
            Toolbar toolbar = this.f22165a;
            toolbar.setTitle(charSequence);
            if (this.f22171g) {
                C22823h0.E(toolbar.getRootView(), charSequence);
            }
        }
    }

    @Override // androidx.appcompat.widget.U
    public final void setIcon(Drawable drawable) {
        this.f22168d = drawable;
        s();
    }

    @Override // androidx.appcompat.widget.U
    public final void k() {
    }
}
