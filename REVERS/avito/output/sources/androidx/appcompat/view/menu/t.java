package androidx.appcompat.view.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import androidx.annotation.RestrictTo;
import j.N;

/* compiled from: SubMenuBuilder.java */
@RestrictTo
/* loaded from: classes.dex */
public class t extends h implements SubMenu {

    /* renamed from: A, reason: collision with root package name */
    public final k f21933A;

    /* renamed from: z, reason: collision with root package name */
    public final h f21934z;

    public t(Context context, h hVar, k kVar) {
        super(context);
        this.f21934z = hVar;
        this.f21933A = kVar;
    }

    @Override // androidx.appcompat.view.menu.h
    public final boolean d(k kVar) {
        return this.f21934z.d(kVar);
    }

    @Override // androidx.appcompat.view.menu.h
    public final boolean e(@N h hVar, @N MenuItem menuItem) {
        return super.e(hVar, menuItem) || this.f21934z.e(hVar, menuItem);
    }

    @Override // androidx.appcompat.view.menu.h
    public final boolean f(k kVar) {
        return this.f21934z.f(kVar);
    }

    @Override // android.view.SubMenu
    public final MenuItem getItem() {
        return this.f21933A;
    }

    @Override // androidx.appcompat.view.menu.h
    public final String j() {
        k kVar = this.f21933A;
        int i12 = kVar != null ? kVar.f21861a : 0;
        if (i12 == 0) {
            return null;
        }
        return AK.c.g(i12, "android:menu:actionviewstates:");
    }

    @Override // androidx.appcompat.view.menu.h
    public final h k() {
        return this.f21934z.k();
    }

    @Override // androidx.appcompat.view.menu.h
    public final boolean m() {
        return this.f21934z.m();
    }

    @Override // androidx.appcompat.view.menu.h
    public final boolean n() {
        return this.f21934z.n();
    }

    @Override // androidx.appcompat.view.menu.h
    public final boolean o() {
        return this.f21934z.o();
    }

    @Override // androidx.appcompat.view.menu.h, android.view.Menu
    public final void setGroupDividerEnabled(boolean z12) {
        this.f21934z.setGroupDividerEnabled(z12);
    }

    @Override // android.view.SubMenu
    public final SubMenu setHeaderIcon(Drawable drawable) {
        u(0, null, 0, drawable, null);
        return this;
    }

    @Override // android.view.SubMenu
    public final SubMenu setHeaderTitle(CharSequence charSequence) {
        u(0, charSequence, 0, null, null);
        return this;
    }

    @Override // android.view.SubMenu
    public final SubMenu setHeaderView(View view) {
        u(0, null, 0, null, view);
        return this;
    }

    @Override // android.view.SubMenu
    public final SubMenu setIcon(Drawable drawable) {
        this.f21933A.setIcon(drawable);
        return this;
    }

    @Override // androidx.appcompat.view.menu.h, android.view.Menu
    public final void setQwertyMode(boolean z12) {
        this.f21934z.setQwertyMode(z12);
    }

    @Override // android.view.SubMenu
    public final SubMenu setHeaderIcon(int i12) {
        u(0, null, i12, null, null);
        return this;
    }

    @Override // android.view.SubMenu
    public final SubMenu setHeaderTitle(int i12) {
        u(i12, null, 0, null, null);
        return this;
    }

    @Override // android.view.SubMenu
    public final SubMenu setIcon(int i12) {
        this.f21933A.setIcon(i12);
        return this;
    }
}
