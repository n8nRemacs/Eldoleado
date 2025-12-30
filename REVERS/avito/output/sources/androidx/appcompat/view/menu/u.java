package androidx.appcompat.view.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import androidx.annotation.RestrictTo;

/* compiled from: SubMenuWrapperICS.java */
@RestrictTo
/* loaded from: classes.dex */
class u extends q implements SubMenu {

    /* renamed from: e, reason: collision with root package name */
    public final K0.c f21935e;

    public u(Context context, K0.c cVar) {
        super(context, cVar);
        this.f21935e = cVar;
    }

    @Override // android.view.SubMenu
    public final void clearHeader() {
        this.f21935e.clearHeader();
    }

    @Override // android.view.SubMenu
    public final MenuItem getItem() {
        return c(this.f21935e.getItem());
    }

    @Override // android.view.SubMenu
    public final SubMenu setHeaderIcon(int i12) {
        this.f21935e.setHeaderIcon(i12);
        return this;
    }

    @Override // android.view.SubMenu
    public final SubMenu setHeaderTitle(int i12) {
        this.f21935e.setHeaderTitle(i12);
        return this;
    }

    @Override // android.view.SubMenu
    public final SubMenu setHeaderView(View view) {
        this.f21935e.setHeaderView(view);
        return this;
    }

    @Override // android.view.SubMenu
    public final SubMenu setIcon(int i12) {
        this.f21935e.setIcon(i12);
        return this;
    }

    @Override // android.view.SubMenu
    public final SubMenu setHeaderIcon(Drawable drawable) {
        this.f21935e.setHeaderIcon(drawable);
        return this;
    }

    @Override // android.view.SubMenu
    public final SubMenu setHeaderTitle(CharSequence charSequence) {
        this.f21935e.setHeaderTitle(charSequence);
        return this;
    }

    @Override // android.view.SubMenu
    public final SubMenu setIcon(Drawable drawable) {
        this.f21935e.setIcon(drawable);
        return this;
    }
}
