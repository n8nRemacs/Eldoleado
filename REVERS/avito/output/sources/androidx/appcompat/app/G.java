package androidx.appcompat.app;

import android.content.Context;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import androidx.appcompat.app.AbstractC19927a;
import androidx.appcompat.app.AppCompatDelegateImpl;
import androidx.appcompat.view.menu.h;
import androidx.appcompat.view.menu.o;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.J0;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.A0;
import androidx.core.view.C22823h0;
import j.N;
import j.P;
import java.util.ArrayList;
import java.util.WeakHashMap;

/* compiled from: ToolbarActionBar.java */
/* loaded from: classes.dex */
class G extends AbstractC19927a {

    /* renamed from: a, reason: collision with root package name */
    public final J0 f21485a;

    /* renamed from: b, reason: collision with root package name */
    public final Window.Callback f21486b;

    /* renamed from: c, reason: collision with root package name */
    public final e f21487c;

    /* renamed from: d, reason: collision with root package name */
    public boolean f21488d;

    /* renamed from: e, reason: collision with root package name */
    public boolean f21489e;

    /* renamed from: f, reason: collision with root package name */
    public boolean f21490f;

    /* renamed from: g, reason: collision with root package name */
    public final ArrayList<AbstractC19927a.d> f21491g = new ArrayList<>();

    /* renamed from: h, reason: collision with root package name */
    public final Runnable f21492h = new a();

    /* renamed from: i, reason: collision with root package name */
    public final Toolbar.h f21493i;

    /* compiled from: ToolbarActionBar.java */
    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            G g12 = G.this;
            Window.Callback callback = g12.f21486b;
            Menu menuZ = g12.z();
            androidx.appcompat.view.menu.h hVar = menuZ instanceof androidx.appcompat.view.menu.h ? (androidx.appcompat.view.menu.h) menuZ : null;
            if (hVar != null) {
                hVar.w();
            }
            try {
                menuZ.clear();
                if (!callback.onCreatePanelMenu(0, menuZ) || !callback.onPreparePanel(0, null, menuZ)) {
                    menuZ.clear();
                }
                if (hVar != null) {
                    hVar.v();
                }
            } catch (Throwable th2) {
                if (hVar != null) {
                    hVar.v();
                }
                throw th2;
            }
        }
    }

    /* compiled from: ToolbarActionBar.java */
    public class b implements Toolbar.h {
        public b() {
        }

        @Override // androidx.appcompat.widget.Toolbar.h
        public final boolean onMenuItemClick(MenuItem menuItem) {
            return G.this.f21486b.onMenuItemSelected(0, menuItem);
        }
    }

    /* compiled from: ToolbarActionBar.java */
    public final class c implements o.a {

        /* renamed from: b, reason: collision with root package name */
        public boolean f21496b;

        public c() {
        }

        @Override // androidx.appcompat.view.menu.o.a
        public final void b(@N androidx.appcompat.view.menu.h hVar, boolean z12) {
            if (this.f21496b) {
                return;
            }
            this.f21496b = true;
            G g12 = G.this;
            g12.f21485a.j();
            g12.f21486b.onPanelClosed(108, hVar);
            this.f21496b = false;
        }

        @Override // androidx.appcompat.view.menu.o.a
        public final boolean c(@N androidx.appcompat.view.menu.h hVar) {
            G.this.f21486b.onMenuOpened(108, hVar);
            return true;
        }
    }

    /* compiled from: ToolbarActionBar.java */
    public final class d implements h.a {
        public d() {
        }

        @Override // androidx.appcompat.view.menu.h.a
        public final void a(@N androidx.appcompat.view.menu.h hVar) {
            G g12 = G.this;
            boolean zP2 = g12.f21485a.f22165a.p();
            Window.Callback callback = g12.f21486b;
            if (zP2) {
                callback.onPanelClosed(108, hVar);
            } else if (callback.onPreparePanel(0, null, hVar)) {
                callback.onMenuOpened(108, hVar);
            }
        }

        @Override // androidx.appcompat.view.menu.h.a
        public final boolean b(@N androidx.appcompat.view.menu.h hVar, @N MenuItem menuItem) {
            return false;
        }
    }

    /* compiled from: ToolbarActionBar.java */
    public class e implements AppCompatDelegateImpl.d {
        public e() {
        }
    }

    public G(@N Toolbar toolbar, @P CharSequence charSequence, @N Window.Callback callback) {
        b bVar = new b();
        toolbar.getClass();
        J0 j02 = new J0(toolbar, false);
        this.f21485a = j02;
        callback.getClass();
        this.f21486b = callback;
        j02.f22175k = callback;
        toolbar.setOnMenuItemClickListener(bVar);
        j02.setWindowTitle(charSequence);
        this.f21487c = new e();
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final boolean f() {
        return this.f21485a.a();
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final boolean g() {
        J0 j02 = this.f21485a;
        if (!j02.f22165a.l()) {
            return false;
        }
        j02.collapseActionView();
        return true;
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final void h(boolean z12) {
        if (z12 == this.f21490f) {
            return;
        }
        this.f21490f = z12;
        ArrayList<AbstractC19927a.d> arrayList = this.f21491g;
        int size = arrayList.size();
        for (int i12 = 0; i12 < size; i12++) {
            arrayList.get(i12).a();
        }
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final int i() {
        return this.f21485a.f22166b;
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final Context j() {
        return this.f21485a.f22165a.getContext();
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final void k() {
        this.f21485a.setVisibility(8);
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final boolean l() {
        J0 j02 = this.f21485a;
        Toolbar toolbar = j02.f22165a;
        Runnable runnable = this.f21492h;
        toolbar.removeCallbacks(runnable);
        Toolbar toolbar2 = j02.f22165a;
        WeakHashMap<View, A0> weakHashMap = C22823h0.f45055a;
        toolbar2.postOnAnimation(runnable);
        return true;
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final void n() {
        this.f21485a.f22165a.removeCallbacks(this.f21492h);
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final boolean o(int i12, KeyEvent keyEvent) {
        Menu menuZ = z();
        if (menuZ == null) {
            return false;
        }
        menuZ.setQwertyMode(KeyCharacterMap.load(keyEvent.getDeviceId()).getKeyboardType() != 1);
        return menuZ.performShortcut(i12, keyEvent, 0);
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final boolean p(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 1) {
            q();
        }
        return true;
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final boolean q() {
        return this.f21485a.f22165a.w();
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final void s(boolean z12) {
        int i12 = z12 ? 4 : 0;
        J0 j02 = this.f21485a;
        j02.h((i12 & 4) | (j02.f22166b & (-5)));
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final void t() {
        J0 j02 = this.f21485a;
        j02.h(j02.f22166b & (-9));
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final void u(int i12) {
        this.f21485a.n(i12);
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final void w(CharSequence charSequence) {
        this.f21485a.setTitle(charSequence);
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final void x(CharSequence charSequence) {
        this.f21485a.setWindowTitle(charSequence);
    }

    public final Menu z() {
        boolean z12 = this.f21489e;
        J0 j02 = this.f21485a;
        if (!z12) {
            c cVar = new c();
            d dVar = new d();
            Toolbar toolbar = j02.f22165a;
            toolbar.f22382O = cVar;
            toolbar.f22383P = dVar;
            ActionMenuView actionMenuView = toolbar.f22389b;
            if (actionMenuView != null) {
                actionMenuView.f22029v = cVar;
                actionMenuView.f22030w = dVar;
            }
            this.f21489e = true;
        }
        return j02.f22165a.getMenu();
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final void m() {
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final void r(boolean z12) {
    }

    @Override // androidx.appcompat.app.AbstractC19927a
    public final void v(boolean z12) {
    }
}
