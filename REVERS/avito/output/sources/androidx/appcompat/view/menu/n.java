package androidx.appcompat.view.menu;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import androidx.annotation.RestrictTo;
import androidx.appcompat.view.menu.o;
import com.avito.android.R;
import j.InterfaceC42150f;
import j.N;

/* compiled from: MenuPopupHelper.java */
@RestrictTo
/* loaded from: classes.dex */
public class n implements j {

    /* renamed from: a, reason: collision with root package name */
    public final Context f21899a;

    /* renamed from: b, reason: collision with root package name */
    public final h f21900b;

    /* renamed from: c, reason: collision with root package name */
    public final boolean f21901c;

    /* renamed from: d, reason: collision with root package name */
    public final int f21902d;

    /* renamed from: e, reason: collision with root package name */
    public View f21903e;

    /* renamed from: g, reason: collision with root package name */
    public boolean f21905g;

    /* renamed from: h, reason: collision with root package name */
    public o.a f21906h;

    /* renamed from: i, reason: collision with root package name */
    public m f21907i;

    /* renamed from: j, reason: collision with root package name */
    public PopupWindow.OnDismissListener f21908j;

    /* renamed from: f, reason: collision with root package name */
    public int f21904f = 8388611;

    /* renamed from: k, reason: collision with root package name */
    public final PopupWindow.OnDismissListener f21909k = new a();

    /* compiled from: MenuPopupHelper.java */
    public class a implements PopupWindow.OnDismissListener {
        public a() {
        }

        @Override // android.widget.PopupWindow.OnDismissListener
        public final void onDismiss() {
            n.this.c();
        }
    }

    public n(@InterfaceC42150f int i12, @N Context context, @N View view, @N h hVar, boolean z12) {
        this.f21899a = context;
        this.f21900b = hVar;
        this.f21903e = view;
        this.f21901c = z12;
        this.f21902d = i12;
    }

    @N
    @RestrictTo
    public final m a() {
        m sVar;
        if (this.f21907i == null) {
            Context context = this.f21899a;
            Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
            Point point = new Point();
            defaultDisplay.getRealSize(point);
            if (Math.min(point.x, point.y) >= context.getResources().getDimensionPixelSize(R.dimen.abc_cascading_menus_min_smallest_width)) {
                sVar = new d(context, this.f21903e, this.f21902d, this.f21901c);
            } else {
                View view = this.f21903e;
                Context context2 = this.f21899a;
                boolean z12 = this.f21901c;
                sVar = new s(this.f21902d, context2, view, this.f21900b, z12);
            }
            sVar.k(this.f21900b);
            sVar.q(this.f21909k);
            sVar.m(this.f21903e);
            sVar.j(this.f21906h);
            sVar.n(this.f21905g);
            sVar.o(this.f21904f);
            this.f21907i = sVar;
        }
        return this.f21907i;
    }

    public final boolean b() {
        m mVar = this.f21907i;
        return mVar != null && mVar.a();
    }

    public void c() {
        this.f21907i = null;
        PopupWindow.OnDismissListener onDismissListener = this.f21908j;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    public final void d(boolean z12) {
        this.f21905g = z12;
        m mVar = this.f21907i;
        if (mVar != null) {
            mVar.n(z12);
        }
    }

    public final void e(int i12, int i13, boolean z12, boolean z13) {
        m mVarA = a();
        mVarA.r(z13);
        if (z12) {
            if ((Gravity.getAbsoluteGravity(this.f21904f, this.f21903e.getLayoutDirection()) & 7) == 5) {
                i12 -= this.f21903e.getWidth();
            }
            mVarA.p(i12);
            mVarA.s(i13);
            int i14 = (int) ((this.f21899a.getResources().getDisplayMetrics().density * 48.0f) / 2.0f);
            mVarA.f21898b = new Rect(i12 - i14, i13 - i14, i12 + i14, i13 + i14);
        }
        mVarA.show();
    }
}
