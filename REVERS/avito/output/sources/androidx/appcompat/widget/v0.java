package androidx.appcompat.widget;

import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.C19943d;
import androidx.core.view.AbstractC22810b;

/* compiled from: ShareActionProvider.java */
/* loaded from: classes.dex */
public class v0 extends AbstractC22810b {

    /* compiled from: ShareActionProvider.java */
    public interface a {
    }

    /* compiled from: ShareActionProvider.java */
    public class b implements C19943d.f {
    }

    /* compiled from: ShareActionProvider.java */
    public class c implements MenuItem.OnMenuItemClickListener {
        @Override // android.view.MenuItem.OnMenuItemClickListener
        public final boolean onMenuItemClick(MenuItem menuItem) {
            throw null;
        }
    }

    @Override // androidx.core.view.AbstractC22810b
    public final View c() {
        new ActivityChooserView();
        throw null;
    }

    @Override // androidx.core.view.AbstractC22810b
    public final void f(androidx.appcompat.view.menu.t tVar) {
        tVar.clear();
        C19943d.b();
        throw null;
    }
}
