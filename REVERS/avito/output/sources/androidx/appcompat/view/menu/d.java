package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import androidx.appcompat.view.menu.n;
import androidx.appcompat.view.menu.o;
import androidx.appcompat.widget.C19952h0;
import androidx.appcompat.widget.InterfaceC19950g0;
import com.avito.android.R;
import j.InterfaceC42150f;
import j.N;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: CascadingMenuPopup.java */
/* loaded from: classes.dex */
final class d extends m implements o, View.OnKeyListener, PopupWindow.OnDismissListener {

    /* renamed from: A, reason: collision with root package name */
    public boolean f21781A;

    /* renamed from: c, reason: collision with root package name */
    public final Context f21782c;

    /* renamed from: d, reason: collision with root package name */
    public final int f21783d;

    /* renamed from: e, reason: collision with root package name */
    public final int f21784e;

    /* renamed from: f, reason: collision with root package name */
    public final boolean f21785f;

    /* renamed from: g, reason: collision with root package name */
    public final Handler f21786g;

    /* renamed from: o, reason: collision with root package name */
    public View f21794o;

    /* renamed from: p, reason: collision with root package name */
    public View f21795p;

    /* renamed from: q, reason: collision with root package name */
    public int f21796q;

    /* renamed from: r, reason: collision with root package name */
    public boolean f21797r;

    /* renamed from: s, reason: collision with root package name */
    public boolean f21798s;

    /* renamed from: t, reason: collision with root package name */
    public int f21799t;

    /* renamed from: u, reason: collision with root package name */
    public int f21800u;

    /* renamed from: w, reason: collision with root package name */
    public boolean f21802w;

    /* renamed from: x, reason: collision with root package name */
    public o.a f21803x;

    /* renamed from: y, reason: collision with root package name */
    public ViewTreeObserver f21804y;

    /* renamed from: z, reason: collision with root package name */
    public PopupWindow.OnDismissListener f21805z;

    /* renamed from: h, reason: collision with root package name */
    public final ArrayList f21787h = new ArrayList();

    /* renamed from: i, reason: collision with root package name */
    public final ArrayList f21788i = new ArrayList();

    /* renamed from: j, reason: collision with root package name */
    public final ViewTreeObserver.OnGlobalLayoutListener f21789j = new a();

    /* renamed from: k, reason: collision with root package name */
    public final View.OnAttachStateChangeListener f21790k = new b();

    /* renamed from: l, reason: collision with root package name */
    public final InterfaceC19950g0 f21791l = new c();

    /* renamed from: m, reason: collision with root package name */
    public int f21792m = 0;

    /* renamed from: n, reason: collision with root package name */
    public int f21793n = 0;

    /* renamed from: v, reason: collision with root package name */
    public boolean f21801v = false;

    /* compiled from: CascadingMenuPopup.java */
    public class a implements ViewTreeObserver.OnGlobalLayoutListener {
        public a() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public final void onGlobalLayout() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            d dVar = d.this;
            if (dVar.a()) {
                ArrayList arrayList = dVar.f21788i;
                if (arrayList.size() <= 0 || ((C1518d) arrayList.get(0)).f21809a.f22530z) {
                    return;
                }
                View view = dVar.f21795p;
                if (view == null || !view.isShown()) {
                    dVar.dismiss();
                    return;
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((C1518d) it.next()).f21809a.show();
                }
            }
        }
    }

    /* compiled from: CascadingMenuPopup.java */
    public class c implements InterfaceC19950g0 {
        public c() {
        }

        @Override // androidx.appcompat.widget.InterfaceC19950g0
        public final void b(@N h hVar, @N k kVar) {
            d dVar = d.this;
            dVar.f21786g.removeCallbacksAndMessages(null);
            ArrayList arrayList = dVar.f21788i;
            int size = arrayList.size();
            int i12 = 0;
            while (true) {
                if (i12 >= size) {
                    i12 = -1;
                    break;
                } else if (hVar == ((C1518d) arrayList.get(i12)).f21810b) {
                    break;
                } else {
                    i12++;
                }
            }
            if (i12 == -1) {
                return;
            }
            int i13 = i12 + 1;
            dVar.f21786g.postAtTime(new androidx.appcompat.view.menu.e(this, i13 < arrayList.size() ? (C1518d) arrayList.get(i13) : null, kVar, hVar), hVar, SystemClock.uptimeMillis() + 200);
        }

        @Override // androidx.appcompat.widget.InterfaceC19950g0
        public final void c(@N h hVar, @N k kVar) {
            d.this.f21786g.removeCallbacksAndMessages(hVar);
        }
    }

    /* compiled from: CascadingMenuPopup.java */
    /* renamed from: androidx.appcompat.view.menu.d$d, reason: collision with other inner class name */
    public static class C1518d {

        /* renamed from: a, reason: collision with root package name */
        public final C19952h0 f21809a;

        /* renamed from: b, reason: collision with root package name */
        public final h f21810b;

        /* renamed from: c, reason: collision with root package name */
        public final int f21811c;

        public C1518d(@N C19952h0 c19952h0, @N h hVar, int i12) {
            this.f21809a = c19952h0;
            this.f21810b = hVar;
            this.f21811c = i12;
        }
    }

    /* compiled from: CascadingMenuPopup.java */
    @Retention(RetentionPolicy.SOURCE)
    public @interface e {
    }

    public d(@N Context context, @N View view, @InterfaceC42150f int i12, boolean z12) {
        this.f21782c = context;
        this.f21794o = view;
        this.f21784e = i12;
        this.f21785f = z12;
        this.f21796q = view.getLayoutDirection() != 1 ? 1 : 0;
        Resources resources = context.getResources();
        this.f21783d = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
        this.f21786g = new Handler();
    }

    @Override // androidx.appcompat.view.menu.r
    public final boolean a() {
        ArrayList arrayList = this.f21788i;
        return arrayList.size() > 0 && ((C1518d) arrayList.get(0)).f21809a.f22505A.isShowing();
    }

    @Override // androidx.appcompat.view.menu.o
    public final void b(h hVar, boolean z12) {
        ArrayList arrayList = this.f21788i;
        int size = arrayList.size();
        int i12 = 0;
        while (true) {
            if (i12 >= size) {
                i12 = -1;
                break;
            } else if (hVar == ((C1518d) arrayList.get(i12)).f21810b) {
                break;
            } else {
                i12++;
            }
        }
        if (i12 < 0) {
            return;
        }
        int i13 = i12 + 1;
        if (i13 < arrayList.size()) {
            ((C1518d) arrayList.get(i13)).f21810b.c(false);
        }
        C1518d c1518d = (C1518d) arrayList.remove(i12);
        c1518d.f21810b.r(this);
        boolean z13 = this.f21781A;
        C19952h0 c19952h0 = c1518d.f21809a;
        if (z13) {
            c19952h0.t();
            c19952h0.f22505A.setAnimationStyle(0);
        }
        c19952h0.dismiss();
        int size2 = arrayList.size();
        if (size2 > 0) {
            this.f21796q = ((C1518d) arrayList.get(size2 - 1)).f21811c;
        } else {
            this.f21796q = this.f21794o.getLayoutDirection() == 1 ? 0 : 1;
        }
        if (size2 != 0) {
            if (z12) {
                ((C1518d) arrayList.get(0)).f21810b.c(false);
                return;
            }
            return;
        }
        dismiss();
        o.a aVar = this.f21803x;
        if (aVar != null) {
            aVar.b(hVar, true);
        }
        ViewTreeObserver viewTreeObserver = this.f21804y;
        if (viewTreeObserver != null) {
            if (viewTreeObserver.isAlive()) {
                this.f21804y.removeGlobalOnLayoutListener(this.f21789j);
            }
            this.f21804y = null;
        }
        this.f21795p.removeOnAttachStateChangeListener(this.f21790k);
        ((n.a) this.f21805z).onDismiss();
    }

    @Override // androidx.appcompat.view.menu.o
    public final void c(boolean z12) {
        Iterator it = this.f21788i.iterator();
        while (it.hasNext()) {
            ListAdapter adapter = ((C1518d) it.next()).f21809a.f22508d.getAdapter();
            if (adapter instanceof HeaderViewListAdapter) {
                adapter = ((HeaderViewListAdapter) adapter).getWrappedAdapter();
            }
            ((g) adapter).notifyDataSetChanged();
        }
    }

    @Override // androidx.appcompat.view.menu.r
    public final ListView d() {
        ArrayList arrayList = this.f21788i;
        if (arrayList.isEmpty()) {
            return null;
        }
        return ((C1518d) androidx.appcompat.app.r.j(1, arrayList)).f21809a.f22508d;
    }

    @Override // androidx.appcompat.view.menu.r
    public final void dismiss() {
        ArrayList arrayList = this.f21788i;
        int size = arrayList.size();
        if (size > 0) {
            C1518d[] c1518dArr = (C1518d[]) arrayList.toArray(new C1518d[size]);
            for (int i12 = size - 1; i12 >= 0; i12--) {
                C1518d c1518d = c1518dArr[i12];
                if (c1518d.f21809a.f22505A.isShowing()) {
                    c1518d.f21809a.dismiss();
                }
            }
        }
    }

    @Override // androidx.appcompat.view.menu.o
    public final boolean f(t tVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Iterator it = this.f21788i.iterator();
        while (it.hasNext()) {
            C1518d c1518d = (C1518d) it.next();
            if (tVar == c1518d.f21810b) {
                c1518d.f21809a.f22508d.requestFocus();
                return true;
            }
        }
        if (!tVar.hasVisibleItems()) {
            return false;
        }
        k(tVar);
        o.a aVar = this.f21803x;
        if (aVar != null) {
            aVar.c(tVar);
        }
        return true;
    }

    @Override // androidx.appcompat.view.menu.o
    public final boolean h() {
        return false;
    }

    @Override // androidx.appcompat.view.menu.o
    public final void j(o.a aVar) {
        this.f21803x = aVar;
    }

    @Override // androidx.appcompat.view.menu.m
    public final void k(h hVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        hVar.b(this, this.f21782c);
        if (a()) {
            u(hVar);
        } else {
            this.f21787h.add(hVar);
        }
    }

    @Override // androidx.appcompat.view.menu.m
    public final void m(@N View view) {
        if (this.f21794o != view) {
            this.f21794o = view;
            this.f21793n = Gravity.getAbsoluteGravity(this.f21792m, view.getLayoutDirection());
        }
    }

    @Override // androidx.appcompat.view.menu.m
    public final void n(boolean z12) {
        this.f21801v = z12;
    }

    @Override // androidx.appcompat.view.menu.m
    public final void o(int i12) {
        if (this.f21792m != i12) {
            this.f21792m = i12;
            this.f21793n = Gravity.getAbsoluteGravity(i12, this.f21794o.getLayoutDirection());
        }
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public final void onDismiss() {
        C1518d c1518d;
        ArrayList arrayList = this.f21788i;
        int size = arrayList.size();
        int i12 = 0;
        while (true) {
            if (i12 >= size) {
                c1518d = null;
                break;
            }
            c1518d = (C1518d) arrayList.get(i12);
            if (!c1518d.f21809a.f22505A.isShowing()) {
                break;
            } else {
                i12++;
            }
        }
        if (c1518d != null) {
            c1518d.f21810b.c(false);
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
        this.f21797r = true;
        this.f21799t = i12;
    }

    @Override // androidx.appcompat.view.menu.m
    public final void q(PopupWindow.OnDismissListener onDismissListener) {
        this.f21805z = onDismissListener;
    }

    @Override // androidx.appcompat.view.menu.m
    public final void r(boolean z12) {
        this.f21802w = z12;
    }

    @Override // androidx.appcompat.view.menu.m
    public final void s(int i12) {
        this.f21798s = true;
        this.f21800u = i12;
    }

    @Override // androidx.appcompat.view.menu.r
    public final void show() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (a()) {
            return;
        }
        ArrayList arrayList = this.f21787h;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            u((h) it.next());
        }
        arrayList.clear();
        View view = this.f21794o;
        this.f21795p = view;
        if (view != null) {
            boolean z12 = this.f21804y == null;
            ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
            this.f21804y = viewTreeObserver;
            if (z12) {
                viewTreeObserver.addOnGlobalLayoutListener(this.f21789j);
            }
            this.f21795p.addOnAttachStateChangeListener(this.f21790k);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0119  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0152  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void u(@j.N androidx.appcompat.view.menu.h r18) throws java.lang.IllegalAccessException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            Method dump skipped, instructions count: 431
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.view.menu.d.u(androidx.appcompat.view.menu.h):void");
    }

    /* compiled from: CascadingMenuPopup.java */
    public class b implements View.OnAttachStateChangeListener {
        public b() {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewDetachedFromWindow(View view) {
            d dVar = d.this;
            ViewTreeObserver viewTreeObserver = dVar.f21804y;
            if (viewTreeObserver != null) {
                if (!viewTreeObserver.isAlive()) {
                    dVar.f21804y = view.getViewTreeObserver();
                }
                dVar.f21804y.removeGlobalOnLayoutListener(dVar.f21789j);
            }
            view.removeOnAttachStateChangeListener(this);
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewAttachedToWindow(View view) {
        }
    }
}
