package androidx.appcompat.view;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

/* compiled from: ActionMode.java */
/* loaded from: classes.dex */
public abstract class b {

    /* renamed from: b, reason: collision with root package name */
    public Object f21654b;

    /* renamed from: c, reason: collision with root package name */
    public boolean f21655c;

    /* compiled from: ActionMode.java */
    public interface a {
        boolean a(b bVar, MenuItem menuItem);

        void b(b bVar);

        boolean c(b bVar, androidx.appcompat.view.menu.h hVar);
    }

    public abstract void c();

    public abstract View d();

    public abstract androidx.appcompat.view.menu.h e();

    public abstract MenuInflater f();

    public abstract CharSequence g();

    public abstract CharSequence h();

    public abstract void i();

    public boolean j() {
        return false;
    }

    public abstract void k(View view);

    public abstract void l(int i12);

    public abstract void m(CharSequence charSequence);

    public abstract void n(int i12);

    public abstract void o(CharSequence charSequence);

    public void p(boolean z12) {
        this.f21655c = z12;
    }
}
