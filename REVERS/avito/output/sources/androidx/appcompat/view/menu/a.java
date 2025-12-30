package androidx.appcompat.view.menu;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import androidx.annotation.RestrictTo;
import androidx.core.view.AbstractC22810b;
import j.N;
import j.P;

/* compiled from: ActionMenuItem.java */
@RestrictTo
/* loaded from: classes.dex */
public class a implements K0.b {

    /* renamed from: a, reason: collision with root package name */
    public CharSequence f21754a;

    /* renamed from: b, reason: collision with root package name */
    public CharSequence f21755b;

    /* renamed from: c, reason: collision with root package name */
    public Intent f21756c;

    /* renamed from: d, reason: collision with root package name */
    public char f21757d;

    /* renamed from: e, reason: collision with root package name */
    public int f21758e;

    /* renamed from: f, reason: collision with root package name */
    public char f21759f;

    /* renamed from: g, reason: collision with root package name */
    public int f21760g;

    /* renamed from: h, reason: collision with root package name */
    public Drawable f21761h;

    /* renamed from: i, reason: collision with root package name */
    public Context f21762i;

    /* renamed from: j, reason: collision with root package name */
    public CharSequence f21763j;

    /* renamed from: k, reason: collision with root package name */
    public CharSequence f21764k;

    /* renamed from: l, reason: collision with root package name */
    public ColorStateList f21765l;

    /* renamed from: m, reason: collision with root package name */
    public PorterDuff.Mode f21766m;

    /* renamed from: n, reason: collision with root package name */
    public boolean f21767n;

    /* renamed from: o, reason: collision with root package name */
    public boolean f21768o;

    /* renamed from: p, reason: collision with root package name */
    public int f21769p;

    @Override // K0.b
    public final AbstractC22810b a() {
        return null;
    }

    @Override // K0.b
    @N
    public final K0.b b(AbstractC22810b abstractC22810b) {
        throw new UnsupportedOperationException();
    }

    public final void c() {
        Drawable drawable = this.f21761h;
        if (drawable != null) {
            if (this.f21767n || this.f21768o) {
                this.f21761h = drawable;
                Drawable drawableMutate = drawable.mutate();
                this.f21761h = drawableMutate;
                if (this.f21767n) {
                    drawableMutate.setTintList(this.f21765l);
                }
                if (this.f21768o) {
                    this.f21761h.setTintMode(this.f21766m);
                }
            }
        }
    }

    @Override // android.view.MenuItem
    public final boolean collapseActionView() {
        return false;
    }

    @Override // android.view.MenuItem
    public final boolean expandActionView() {
        return false;
    }

    @Override // android.view.MenuItem
    public final ActionProvider getActionProvider() {
        throw new UnsupportedOperationException();
    }

    @Override // android.view.MenuItem
    public final View getActionView() {
        return null;
    }

    @Override // K0.b, android.view.MenuItem
    public final int getAlphabeticModifiers() {
        return this.f21760g;
    }

    @Override // android.view.MenuItem
    public final char getAlphabeticShortcut() {
        return this.f21759f;
    }

    @Override // K0.b, android.view.MenuItem
    public final CharSequence getContentDescription() {
        return this.f21763j;
    }

    @Override // android.view.MenuItem
    public final int getGroupId() {
        return 0;
    }

    @Override // android.view.MenuItem
    public final Drawable getIcon() {
        return this.f21761h;
    }

    @Override // K0.b, android.view.MenuItem
    public final ColorStateList getIconTintList() {
        return this.f21765l;
    }

    @Override // K0.b, android.view.MenuItem
    public final PorterDuff.Mode getIconTintMode() {
        return this.f21766m;
    }

    @Override // android.view.MenuItem
    public final Intent getIntent() {
        return this.f21756c;
    }

    @Override // android.view.MenuItem
    public final int getItemId() {
        return R.id.home;
    }

    @Override // android.view.MenuItem
    public final ContextMenu.ContextMenuInfo getMenuInfo() {
        return null;
    }

    @Override // K0.b, android.view.MenuItem
    public final int getNumericModifiers() {
        return this.f21758e;
    }

    @Override // android.view.MenuItem
    public final char getNumericShortcut() {
        return this.f21757d;
    }

    @Override // android.view.MenuItem
    public final int getOrder() {
        return 0;
    }

    @Override // android.view.MenuItem
    public final SubMenu getSubMenu() {
        return null;
    }

    @Override // android.view.MenuItem
    public final CharSequence getTitle() {
        return this.f21754a;
    }

    @Override // android.view.MenuItem
    public final CharSequence getTitleCondensed() {
        CharSequence charSequence = this.f21755b;
        return charSequence != null ? charSequence : this.f21754a;
    }

    @Override // K0.b, android.view.MenuItem
    public final CharSequence getTooltipText() {
        return this.f21764k;
    }

    @Override // android.view.MenuItem
    public final boolean hasSubMenu() {
        return false;
    }

    @Override // android.view.MenuItem
    public final boolean isActionViewExpanded() {
        return false;
    }

    @Override // android.view.MenuItem
    public final boolean isCheckable() {
        return (this.f21769p & 1) != 0;
    }

    @Override // android.view.MenuItem
    public final boolean isChecked() {
        return (this.f21769p & 2) != 0;
    }

    @Override // android.view.MenuItem
    public final boolean isEnabled() {
        return (this.f21769p & 16) != 0;
    }

    @Override // android.view.MenuItem
    public final boolean isVisible() {
        return (this.f21769p & 8) == 0;
    }

    @Override // android.view.MenuItem
    public final MenuItem setActionProvider(ActionProvider actionProvider) {
        throw new UnsupportedOperationException();
    }

    @Override // android.view.MenuItem
    @N
    public final MenuItem setActionView(View view) {
        throw new UnsupportedOperationException();
    }

    @Override // android.view.MenuItem
    public final MenuItem setAlphabeticShortcut(char c12) {
        this.f21759f = Character.toLowerCase(c12);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setCheckable(boolean z12) {
        this.f21769p = (z12 ? 1 : 0) | (this.f21769p & (-2));
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setChecked(boolean z12) {
        this.f21769p = (z12 ? 2 : 0) | (this.f21769p & (-3));
        return this;
    }

    @Override // K0.b, android.view.MenuItem
    @N
    public final K0.b setContentDescription(CharSequence charSequence) {
        this.f21763j = charSequence;
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setEnabled(boolean z12) {
        this.f21769p = (z12 ? 16 : 0) | (this.f21769p & (-17));
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setIcon(Drawable drawable) {
        this.f21761h = drawable;
        c();
        return this;
    }

    @Override // K0.b, android.view.MenuItem
    @N
    public final MenuItem setIconTintList(@P ColorStateList colorStateList) {
        this.f21765l = colorStateList;
        this.f21767n = true;
        c();
        return this;
    }

    @Override // K0.b, android.view.MenuItem
    @N
    public final MenuItem setIconTintMode(PorterDuff.Mode mode) {
        this.f21766m = mode;
        this.f21768o = true;
        c();
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setIntent(Intent intent) {
        this.f21756c = intent;
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setNumericShortcut(char c12) {
        this.f21757d = c12;
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
        throw new UnsupportedOperationException();
    }

    @Override // android.view.MenuItem
    public final MenuItem setShortcut(char c12, char c13) {
        this.f21757d = c12;
        this.f21759f = Character.toLowerCase(c13);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setTitle(CharSequence charSequence) {
        this.f21754a = charSequence;
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setTitleCondensed(CharSequence charSequence) {
        this.f21755b = charSequence;
        return this;
    }

    @Override // K0.b, android.view.MenuItem
    @N
    public final K0.b setTooltipText(CharSequence charSequence) {
        this.f21764k = charSequence;
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setVisible(boolean z12) {
        this.f21769p = (this.f21769p & 8) | (z12 ? 0 : 8);
        return this;
    }

    @Override // android.view.MenuItem
    @N
    public final MenuItem setActionView(int i12) {
        throw new UnsupportedOperationException();
    }

    @Override // K0.b, android.view.MenuItem
    @N
    public final MenuItem setAlphabeticShortcut(char c12, int i12) {
        this.f21759f = Character.toLowerCase(c12);
        this.f21760g = KeyEvent.normalizeMetaState(i12);
        return this;
    }

    @Override // android.view.MenuItem
    @N
    public final MenuItem setContentDescription(CharSequence charSequence) {
        this.f21763j = charSequence;
        return this;
    }

    @Override // K0.b, android.view.MenuItem
    @N
    public final MenuItem setNumericShortcut(char c12, int i12) {
        this.f21757d = c12;
        this.f21758e = KeyEvent.normalizeMetaState(i12);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setTitle(int i12) {
        this.f21754a = this.f21762i.getResources().getString(i12);
        return this;
    }

    @Override // android.view.MenuItem
    @N
    public final MenuItem setTooltipText(CharSequence charSequence) {
        this.f21764k = charSequence;
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setIcon(int i12) {
        this.f21761h = androidx.core.content.d.getDrawable(this.f21762i, i12);
        c();
        return this;
    }

    @Override // K0.b, android.view.MenuItem
    @N
    public final MenuItem setShortcut(char c12, char c13, int i12, int i13) {
        this.f21757d = c12;
        this.f21758e = KeyEvent.normalizeMetaState(i12);
        this.f21759f = Character.toLowerCase(c13);
        this.f21760g = KeyEvent.normalizeMetaState(i13);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        return this;
    }

    @Override // android.view.MenuItem
    public final void setShowAsAction(int i12) {
    }

    @Override // android.view.MenuItem
    @N
    public final MenuItem setShowAsActionFlags(int i12) {
        return this;
    }
}
