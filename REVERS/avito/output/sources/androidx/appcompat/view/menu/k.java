package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.RestrictTo;
import androidx.core.view.AbstractC22810b;
import j.N;
import j.P;
import java.util.ArrayList;
import m.C43852a;

/* compiled from: MenuItemImpl.java */
@RestrictTo
/* loaded from: classes.dex */
public final class k implements K0.b {

    /* renamed from: A, reason: collision with root package name */
    public AbstractC22810b f21858A;

    /* renamed from: B, reason: collision with root package name */
    public MenuItem.OnActionExpandListener f21859B;

    /* renamed from: a, reason: collision with root package name */
    public final int f21861a;

    /* renamed from: b, reason: collision with root package name */
    public final int f21862b;

    /* renamed from: c, reason: collision with root package name */
    public final int f21863c;

    /* renamed from: d, reason: collision with root package name */
    public final int f21864d;

    /* renamed from: e, reason: collision with root package name */
    public CharSequence f21865e;

    /* renamed from: f, reason: collision with root package name */
    public CharSequence f21866f;

    /* renamed from: g, reason: collision with root package name */
    public Intent f21867g;

    /* renamed from: h, reason: collision with root package name */
    public char f21868h;

    /* renamed from: j, reason: collision with root package name */
    public char f21870j;

    /* renamed from: l, reason: collision with root package name */
    public Drawable f21872l;

    /* renamed from: n, reason: collision with root package name */
    public final h f21874n;

    /* renamed from: o, reason: collision with root package name */
    public t f21875o;

    /* renamed from: p, reason: collision with root package name */
    public MenuItem.OnMenuItemClickListener f21876p;

    /* renamed from: q, reason: collision with root package name */
    public CharSequence f21877q;

    /* renamed from: r, reason: collision with root package name */
    public CharSequence f21878r;

    /* renamed from: y, reason: collision with root package name */
    public int f21885y;

    /* renamed from: z, reason: collision with root package name */
    public View f21886z;

    /* renamed from: i, reason: collision with root package name */
    public int f21869i = 4096;

    /* renamed from: k, reason: collision with root package name */
    public int f21871k = 4096;

    /* renamed from: m, reason: collision with root package name */
    public int f21873m = 0;

    /* renamed from: s, reason: collision with root package name */
    public ColorStateList f21879s = null;

    /* renamed from: t, reason: collision with root package name */
    public PorterDuff.Mode f21880t = null;

    /* renamed from: u, reason: collision with root package name */
    public boolean f21881u = false;

    /* renamed from: v, reason: collision with root package name */
    public boolean f21882v = false;

    /* renamed from: w, reason: collision with root package name */
    public boolean f21883w = false;

    /* renamed from: x, reason: collision with root package name */
    public int f21884x = 16;

    /* renamed from: C, reason: collision with root package name */
    public boolean f21860C = false;

    /* compiled from: MenuItemImpl.java */
    public class a implements AbstractC22810b.InterfaceC1755b {
        public a() {
        }
    }

    public k(h hVar, int i12, int i13, int i14, int i15, CharSequence charSequence, int i16) {
        this.f21874n = hVar;
        this.f21861a = i13;
        this.f21862b = i12;
        this.f21863c = i14;
        this.f21864d = i15;
        this.f21865e = charSequence;
        this.f21885y = i16;
    }

    public static void c(int i12, int i13, String str, StringBuilder sb2) {
        if ((i12 & i13) == i13) {
            sb2.append(str);
        }
    }

    @Override // K0.b
    public final AbstractC22810b a() {
        return this.f21858A;
    }

    @Override // K0.b
    @N
    public final K0.b b(AbstractC22810b abstractC22810b) {
        AbstractC22810b abstractC22810b2 = this.f21858A;
        if (abstractC22810b2 != null) {
            abstractC22810b2.f45027b = null;
            abstractC22810b2.f45026a = null;
        }
        this.f21886z = null;
        this.f21858A = abstractC22810b;
        this.f21874n.p(true);
        AbstractC22810b abstractC22810b3 = this.f21858A;
        if (abstractC22810b3 != null) {
            abstractC22810b3.h(new a());
        }
        return this;
    }

    @Override // android.view.MenuItem
    public final boolean collapseActionView() {
        if ((this.f21885y & 8) == 0) {
            return false;
        }
        if (this.f21886z == null) {
            return true;
        }
        MenuItem.OnActionExpandListener onActionExpandListener = this.f21859B;
        if (onActionExpandListener == null || onActionExpandListener.onMenuItemActionCollapse(this)) {
            return this.f21874n.d(this);
        }
        return false;
    }

    public final Drawable d(Drawable drawable) {
        if (drawable != null && this.f21883w && (this.f21881u || this.f21882v)) {
            drawable = drawable.mutate();
            if (this.f21881u) {
                drawable.setTintList(this.f21879s);
            }
            if (this.f21882v) {
                drawable.setTintMode(this.f21880t);
            }
            this.f21883w = false;
        }
        return drawable;
    }

    public final boolean e() {
        AbstractC22810b abstractC22810b;
        if ((this.f21885y & 8) == 0) {
            return false;
        }
        if (this.f21886z == null && (abstractC22810b = this.f21858A) != null) {
            this.f21886z = abstractC22810b.d(this);
        }
        return this.f21886z != null;
    }

    @Override // android.view.MenuItem
    public final boolean expandActionView() {
        if (!e()) {
            return false;
        }
        MenuItem.OnActionExpandListener onActionExpandListener = this.f21859B;
        if (onActionExpandListener == null || onActionExpandListener.onMenuItemActionExpand(this)) {
            return this.f21874n.f(this);
        }
        return false;
    }

    public final boolean f() {
        return (this.f21884x & 32) == 32;
    }

    public final void g(boolean z12) {
        this.f21884x = (z12 ? 4 : 0) | (this.f21884x & (-5));
    }

    @Override // android.view.MenuItem
    public final ActionProvider getActionProvider() {
        throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.getActionProvider()");
    }

    @Override // android.view.MenuItem
    public final View getActionView() {
        View view = this.f21886z;
        if (view != null) {
            return view;
        }
        AbstractC22810b abstractC22810b = this.f21858A;
        if (abstractC22810b == null) {
            return null;
        }
        View viewD = abstractC22810b.d(this);
        this.f21886z = viewD;
        return viewD;
    }

    @Override // K0.b, android.view.MenuItem
    public final int getAlphabeticModifiers() {
        return this.f21871k;
    }

    @Override // android.view.MenuItem
    public final char getAlphabeticShortcut() {
        return this.f21870j;
    }

    @Override // K0.b, android.view.MenuItem
    public final CharSequence getContentDescription() {
        return this.f21877q;
    }

    @Override // android.view.MenuItem
    public final int getGroupId() {
        return this.f21862b;
    }

    @Override // android.view.MenuItem
    public final Drawable getIcon() {
        Drawable drawable = this.f21872l;
        if (drawable != null) {
            return d(drawable);
        }
        int i12 = this.f21873m;
        if (i12 == 0) {
            return null;
        }
        Drawable drawableA = C43852a.a(this.f21874n.f21831a, i12);
        this.f21873m = 0;
        this.f21872l = drawableA;
        return d(drawableA);
    }

    @Override // K0.b, android.view.MenuItem
    public final ColorStateList getIconTintList() {
        return this.f21879s;
    }

    @Override // K0.b, android.view.MenuItem
    public final PorterDuff.Mode getIconTintMode() {
        return this.f21880t;
    }

    @Override // android.view.MenuItem
    public final Intent getIntent() {
        return this.f21867g;
    }

    @Override // android.view.MenuItem
    @ViewDebug.CapturedViewProperty
    public final int getItemId() {
        return this.f21861a;
    }

    @Override // android.view.MenuItem
    public final ContextMenu.ContextMenuInfo getMenuInfo() {
        return null;
    }

    @Override // K0.b, android.view.MenuItem
    public final int getNumericModifiers() {
        return this.f21869i;
    }

    @Override // android.view.MenuItem
    public final char getNumericShortcut() {
        return this.f21868h;
    }

    @Override // android.view.MenuItem
    public final int getOrder() {
        return this.f21863c;
    }

    @Override // android.view.MenuItem
    public final SubMenu getSubMenu() {
        return this.f21875o;
    }

    @Override // android.view.MenuItem
    @ViewDebug.CapturedViewProperty
    public final CharSequence getTitle() {
        return this.f21865e;
    }

    @Override // android.view.MenuItem
    public final CharSequence getTitleCondensed() {
        CharSequence charSequence = this.f21866f;
        return charSequence != null ? charSequence : this.f21865e;
    }

    @Override // K0.b, android.view.MenuItem
    public final CharSequence getTooltipText() {
        return this.f21878r;
    }

    public final void h(boolean z12) {
        if (z12) {
            this.f21884x |= 32;
        } else {
            this.f21884x &= -33;
        }
    }

    @Override // android.view.MenuItem
    public final boolean hasSubMenu() {
        return this.f21875o != null;
    }

    @Override // android.view.MenuItem
    public final boolean isActionViewExpanded() {
        return this.f21860C;
    }

    @Override // android.view.MenuItem
    public final boolean isCheckable() {
        return (this.f21884x & 1) == 1;
    }

    @Override // android.view.MenuItem
    public final boolean isChecked() {
        return (this.f21884x & 2) == 2;
    }

    @Override // android.view.MenuItem
    public final boolean isEnabled() {
        return (this.f21884x & 16) != 0;
    }

    @Override // android.view.MenuItem
    public final boolean isVisible() {
        AbstractC22810b abstractC22810b = this.f21858A;
        return (abstractC22810b == null || !abstractC22810b.g()) ? (this.f21884x & 8) == 0 : (this.f21884x & 8) == 0 && this.f21858A.b();
    }

    @Override // android.view.MenuItem
    public final MenuItem setActionProvider(ActionProvider actionProvider) {
        throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.setActionProvider()");
    }

    @Override // android.view.MenuItem
    @N
    public final MenuItem setActionView(View view) {
        int i12;
        this.f21886z = view;
        this.f21858A = null;
        if (view != null && view.getId() == -1 && (i12 = this.f21861a) > 0) {
            view.setId(i12);
        }
        h hVar = this.f21874n;
        hVar.f21841k = true;
        hVar.p(true);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setAlphabeticShortcut(char c12) {
        if (this.f21870j == c12) {
            return this;
        }
        this.f21870j = Character.toLowerCase(c12);
        this.f21874n.p(false);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setCheckable(boolean z12) {
        int i12 = this.f21884x;
        int i13 = (z12 ? 1 : 0) | (i12 & (-2));
        this.f21884x = i13;
        if (i12 != i13) {
            this.f21874n.p(false);
        }
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setChecked(boolean z12) {
        int i12 = this.f21884x;
        if ((i12 & 4) != 0) {
            h hVar = this.f21874n;
            hVar.getClass();
            ArrayList<k> arrayList = hVar.f21836f;
            int size = arrayList.size();
            hVar.w();
            for (int i13 = 0; i13 < size; i13++) {
                k kVar = arrayList.get(i13);
                if (kVar.f21862b == this.f21862b && (kVar.f21884x & 4) != 0 && kVar.isCheckable()) {
                    boolean z13 = kVar == this;
                    int i14 = kVar.f21884x;
                    int i15 = (z13 ? 2 : 0) | (i14 & (-3));
                    kVar.f21884x = i15;
                    if (i14 != i15) {
                        kVar.f21874n.p(false);
                    }
                }
            }
            hVar.v();
        } else {
            int i16 = (i12 & (-3)) | (z12 ? 2 : 0);
            this.f21884x = i16;
            if (i12 != i16) {
                this.f21874n.p(false);
            }
        }
        return this;
    }

    @Override // android.view.MenuItem
    @N
    public final /* bridge */ /* synthetic */ MenuItem setContentDescription(CharSequence charSequence) {
        setContentDescription(charSequence);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setEnabled(boolean z12) {
        if (z12) {
            this.f21884x |= 16;
        } else {
            this.f21884x &= -17;
        }
        this.f21874n.p(false);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setIcon(Drawable drawable) {
        this.f21873m = 0;
        this.f21872l = drawable;
        this.f21883w = true;
        this.f21874n.p(false);
        return this;
    }

    @Override // K0.b, android.view.MenuItem
    @N
    public final MenuItem setIconTintList(@P ColorStateList colorStateList) {
        this.f21879s = colorStateList;
        this.f21881u = true;
        this.f21883w = true;
        this.f21874n.p(false);
        return this;
    }

    @Override // K0.b, android.view.MenuItem
    @N
    public final MenuItem setIconTintMode(PorterDuff.Mode mode) {
        this.f21880t = mode;
        this.f21882v = true;
        this.f21883w = true;
        this.f21874n.p(false);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setIntent(Intent intent) {
        this.f21867g = intent;
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setNumericShortcut(char c12) {
        if (this.f21868h == c12) {
            return this;
        }
        this.f21868h = c12;
        this.f21874n.p(false);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
        this.f21859B = onActionExpandListener;
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        this.f21876p = onMenuItemClickListener;
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setShortcut(char c12, char c13) {
        this.f21868h = c12;
        this.f21870j = Character.toLowerCase(c13);
        this.f21874n.p(false);
        return this;
    }

    @Override // android.view.MenuItem
    public final void setShowAsAction(int i12) {
        int i13 = i12 & 3;
        if (i13 != 0 && i13 != 1 && i13 != 2) {
            throw new IllegalArgumentException("SHOW_AS_ACTION_ALWAYS, SHOW_AS_ACTION_IF_ROOM, and SHOW_AS_ACTION_NEVER are mutually exclusive.");
        }
        this.f21885y = i12;
        h hVar = this.f21874n;
        hVar.f21841k = true;
        hVar.p(true);
    }

    @Override // android.view.MenuItem
    @N
    public final MenuItem setShowAsActionFlags(int i12) {
        setShowAsAction(i12);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setTitle(CharSequence charSequence) {
        this.f21865e = charSequence;
        this.f21874n.p(false);
        t tVar = this.f21875o;
        if (tVar != null) {
            tVar.setHeaderTitle(charSequence);
        }
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setTitleCondensed(CharSequence charSequence) {
        this.f21866f = charSequence;
        this.f21874n.p(false);
        return this;
    }

    @Override // android.view.MenuItem
    @N
    public final /* bridge */ /* synthetic */ MenuItem setTooltipText(CharSequence charSequence) {
        setTooltipText(charSequence);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setVisible(boolean z12) {
        int i12 = this.f21884x;
        int i13 = (z12 ? 0 : 8) | (i12 & (-9));
        this.f21884x = i13;
        if (i12 != i13) {
            h hVar = this.f21874n;
            hVar.f21838h = true;
            hVar.p(true);
        }
        return this;
    }

    public final String toString() {
        CharSequence charSequence = this.f21865e;
        if (charSequence != null) {
            return charSequence.toString();
        }
        return null;
    }

    @Override // K0.b, android.view.MenuItem
    @N
    public final K0.b setContentDescription(CharSequence charSequence) {
        this.f21877q = charSequence;
        this.f21874n.p(false);
        return this;
    }

    @Override // K0.b, android.view.MenuItem
    @N
    public final K0.b setTooltipText(CharSequence charSequence) {
        this.f21878r = charSequence;
        this.f21874n.p(false);
        return this;
    }

    @Override // K0.b, android.view.MenuItem
    @N
    public final MenuItem setAlphabeticShortcut(char c12, int i12) {
        if (this.f21870j == c12 && this.f21871k == i12) {
            return this;
        }
        this.f21870j = Character.toLowerCase(c12);
        this.f21871k = KeyEvent.normalizeMetaState(i12);
        this.f21874n.p(false);
        return this;
    }

    @Override // K0.b, android.view.MenuItem
    @N
    public final MenuItem setNumericShortcut(char c12, int i12) {
        if (this.f21868h == c12 && this.f21869i == i12) {
            return this;
        }
        this.f21868h = c12;
        this.f21869i = KeyEvent.normalizeMetaState(i12);
        this.f21874n.p(false);
        return this;
    }

    @Override // K0.b, android.view.MenuItem
    @N
    public final MenuItem setShortcut(char c12, char c13, int i12, int i13) {
        this.f21868h = c12;
        this.f21869i = KeyEvent.normalizeMetaState(i12);
        this.f21870j = Character.toLowerCase(c13);
        this.f21871k = KeyEvent.normalizeMetaState(i13);
        this.f21874n.p(false);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setIcon(int i12) {
        this.f21872l = null;
        this.f21873m = i12;
        this.f21883w = true;
        this.f21874n.p(false);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setTitle(int i12) {
        setTitle(this.f21874n.f21831a.getString(i12));
        return this;
    }

    @Override // android.view.MenuItem
    @N
    public final MenuItem setActionView(int i12) {
        int i13;
        Context context = this.f21874n.f21831a;
        View viewInflate = LayoutInflater.from(context).inflate(i12, (ViewGroup) new LinearLayout(context), false);
        this.f21886z = viewInflate;
        this.f21858A = null;
        if (viewInflate != null && viewInflate.getId() == -1 && (i13 = this.f21861a) > 0) {
            viewInflate.setId(i13);
        }
        h hVar = this.f21874n;
        hVar.f21841k = true;
        hVar.p(true);
        return this;
    }
}
