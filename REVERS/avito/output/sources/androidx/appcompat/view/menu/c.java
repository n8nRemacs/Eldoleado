package androidx.appcompat.view.menu;

import android.content.Context;
import android.view.MenuItem;
import android.view.SubMenu;
import androidx.collection.o1;

/* compiled from: BaseMenuWrapper.java */
/* loaded from: classes.dex */
abstract class c {

    /* renamed from: a, reason: collision with root package name */
    public final Context f21778a;

    /* renamed from: b, reason: collision with root package name */
    public o1<K0.b, MenuItem> f21779b;

    /* renamed from: c, reason: collision with root package name */
    public o1<K0.c, SubMenu> f21780c;

    public c(Context context) {
        this.f21778a = context;
    }

    public final MenuItem c(MenuItem menuItem) {
        if (!(menuItem instanceof K0.b)) {
            return menuItem;
        }
        K0.b bVar = (K0.b) menuItem;
        if (this.f21779b == null) {
            this.f21779b = new o1<>();
        }
        MenuItem menuItem2 = this.f21779b.get(bVar);
        if (menuItem2 != null) {
            return menuItem2;
        }
        l lVar = new l(this.f21778a, bVar);
        this.f21779b.put(bVar, lVar);
        return lVar;
    }

    public final SubMenu d(SubMenu subMenu) {
        if (!(subMenu instanceof K0.c)) {
            return subMenu;
        }
        K0.c cVar = (K0.c) subMenu;
        if (this.f21780c == null) {
            this.f21780c = new o1<>();
        }
        SubMenu subMenu2 = this.f21780c.get(cVar);
        if (subMenu2 != null) {
            return subMenu2;
        }
        u uVar = new u(this.f21778a, cVar);
        this.f21780c.put(cVar, uVar);
        return uVar;
    }
}
