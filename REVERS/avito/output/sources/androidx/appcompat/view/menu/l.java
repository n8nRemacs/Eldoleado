package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.ActionProvider;
import android.view.CollapsibleActionView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.RestrictTo;
import androidx.core.view.AbstractC22810b;
import j.N;
import java.lang.reflect.Method;

/* compiled from: MenuItemWrapperICS.java */
@RestrictTo
/* loaded from: classes.dex */
public class l extends androidx.appcompat.view.menu.c implements MenuItem {

    /* renamed from: d, reason: collision with root package name */
    public final K0.b f21888d;

    /* renamed from: e, reason: collision with root package name */
    public Method f21889e;

    /* compiled from: MenuItemWrapperICS.java */
    public class a extends AbstractC22810b implements ActionProvider.VisibilityListener {

        /* renamed from: c, reason: collision with root package name */
        public AbstractC22810b.InterfaceC1755b f21890c;

        /* renamed from: d, reason: collision with root package name */
        public final ActionProvider f21891d;

        public a(ActionProvider actionProvider) {
            this.f21891d = actionProvider;
        }

        @Override // androidx.core.view.AbstractC22810b
        public final boolean a() {
            return this.f21891d.hasSubMenu();
        }

        @Override // androidx.core.view.AbstractC22810b
        public final boolean b() {
            return this.f21891d.isVisible();
        }

        @Override // androidx.core.view.AbstractC22810b
        @N
        public final View c() {
            return this.f21891d.onCreateActionView();
        }

        @Override // androidx.core.view.AbstractC22810b
        public final View d(k kVar) {
            return this.f21891d.onCreateActionView(kVar);
        }

        @Override // androidx.core.view.AbstractC22810b
        public final boolean e() {
            return this.f21891d.onPerformDefaultAction();
        }

        @Override // androidx.core.view.AbstractC22810b
        public final void f(t tVar) {
            this.f21891d.onPrepareSubMenu(l.this.d(tVar));
        }

        @Override // androidx.core.view.AbstractC22810b
        public final boolean g() {
            return this.f21891d.overridesItemVisibility();
        }

        @Override // androidx.core.view.AbstractC22810b
        public final void h(AbstractC22810b.InterfaceC1755b interfaceC1755b) {
            this.f21890c = interfaceC1755b;
            this.f21891d.setVisibilityListener(this);
        }

        @Override // android.view.ActionProvider.VisibilityListener
        public final void onActionProviderVisibilityChanged(boolean z12) {
            AbstractC22810b.InterfaceC1755b interfaceC1755b = this.f21890c;
            if (interfaceC1755b != null) {
                h hVar = k.this.f21874n;
                hVar.f21838h = true;
                hVar.p(true);
            }
        }
    }

    /* compiled from: MenuItemWrapperICS.java */
    public static class b extends FrameLayout implements androidx.appcompat.view.c {

        /* renamed from: b, reason: collision with root package name */
        public final CollapsibleActionView f21893b;

        /* JADX WARN: Multi-variable type inference failed */
        public b(View view) {
            super(view.getContext());
            this.f21893b = (CollapsibleActionView) view;
            addView(view);
        }

        @Override // androidx.appcompat.view.c
        public final void a() {
            this.f21893b.onActionViewCollapsed();
        }

        @Override // androidx.appcompat.view.c
        public final void c() {
            this.f21893b.onActionViewExpanded();
        }
    }

    /* compiled from: MenuItemWrapperICS.java */
    public class c implements MenuItem.OnActionExpandListener {

        /* renamed from: b, reason: collision with root package name */
        public final MenuItem.OnActionExpandListener f21894b;

        public c(MenuItem.OnActionExpandListener onActionExpandListener) {
            this.f21894b = onActionExpandListener;
        }

        @Override // android.view.MenuItem.OnActionExpandListener
        public final boolean onMenuItemActionCollapse(MenuItem menuItem) {
            return this.f21894b.onMenuItemActionCollapse(l.this.c(menuItem));
        }

        @Override // android.view.MenuItem.OnActionExpandListener
        public final boolean onMenuItemActionExpand(MenuItem menuItem) {
            return this.f21894b.onMenuItemActionExpand(l.this.c(menuItem));
        }
    }

    /* compiled from: MenuItemWrapperICS.java */
    public class d implements MenuItem.OnMenuItemClickListener {

        /* renamed from: b, reason: collision with root package name */
        public final MenuItem.OnMenuItemClickListener f21896b;

        public d(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
            this.f21896b = onMenuItemClickListener;
        }

        @Override // android.view.MenuItem.OnMenuItemClickListener
        public final boolean onMenuItemClick(MenuItem menuItem) {
            return this.f21896b.onMenuItemClick(l.this.c(menuItem));
        }
    }

    public l(Context context, K0.b bVar) {
        super(context);
        if (bVar == null) {
            throw new IllegalArgumentException("Wrapped Object can not be null.");
        }
        this.f21888d = bVar;
    }

    @Override // android.view.MenuItem
    public final boolean collapseActionView() {
        return this.f21888d.collapseActionView();
    }

    @Override // android.view.MenuItem
    public final boolean expandActionView() {
        return this.f21888d.expandActionView();
    }

    @Override // android.view.MenuItem
    public final ActionProvider getActionProvider() {
        AbstractC22810b abstractC22810bA = this.f21888d.a();
        if (abstractC22810bA instanceof a) {
            return ((a) abstractC22810bA).f21891d;
        }
        return null;
    }

    @Override // android.view.MenuItem
    public final View getActionView() {
        View actionView = this.f21888d.getActionView();
        return actionView instanceof b ? (View) ((b) actionView).f21893b : actionView;
    }

    @Override // android.view.MenuItem
    public final int getAlphabeticModifiers() {
        return this.f21888d.getAlphabeticModifiers();
    }

    @Override // android.view.MenuItem
    public final char getAlphabeticShortcut() {
        return this.f21888d.getAlphabeticShortcut();
    }

    @Override // android.view.MenuItem
    public final CharSequence getContentDescription() {
        return this.f21888d.getContentDescription();
    }

    @Override // android.view.MenuItem
    public final int getGroupId() {
        return this.f21888d.getGroupId();
    }

    @Override // android.view.MenuItem
    public final Drawable getIcon() {
        return this.f21888d.getIcon();
    }

    @Override // android.view.MenuItem
    public final ColorStateList getIconTintList() {
        return this.f21888d.getIconTintList();
    }

    @Override // android.view.MenuItem
    public final PorterDuff.Mode getIconTintMode() {
        return this.f21888d.getIconTintMode();
    }

    @Override // android.view.MenuItem
    public final Intent getIntent() {
        return this.f21888d.getIntent();
    }

    @Override // android.view.MenuItem
    public final int getItemId() {
        return this.f21888d.getItemId();
    }

    @Override // android.view.MenuItem
    public final ContextMenu.ContextMenuInfo getMenuInfo() {
        return this.f21888d.getMenuInfo();
    }

    @Override // android.view.MenuItem
    public final int getNumericModifiers() {
        return this.f21888d.getNumericModifiers();
    }

    @Override // android.view.MenuItem
    public final char getNumericShortcut() {
        return this.f21888d.getNumericShortcut();
    }

    @Override // android.view.MenuItem
    public final int getOrder() {
        return this.f21888d.getOrder();
    }

    @Override // android.view.MenuItem
    public final SubMenu getSubMenu() {
        return d(this.f21888d.getSubMenu());
    }

    @Override // android.view.MenuItem
    public final CharSequence getTitle() {
        return this.f21888d.getTitle();
    }

    @Override // android.view.MenuItem
    public final CharSequence getTitleCondensed() {
        return this.f21888d.getTitleCondensed();
    }

    @Override // android.view.MenuItem
    public final CharSequence getTooltipText() {
        return this.f21888d.getTooltipText();
    }

    @Override // android.view.MenuItem
    public final boolean hasSubMenu() {
        return this.f21888d.hasSubMenu();
    }

    @Override // android.view.MenuItem
    public final boolean isActionViewExpanded() {
        return this.f21888d.isActionViewExpanded();
    }

    @Override // android.view.MenuItem
    public final boolean isCheckable() {
        return this.f21888d.isCheckable();
    }

    @Override // android.view.MenuItem
    public final boolean isChecked() {
        return this.f21888d.isChecked();
    }

    @Override // android.view.MenuItem
    public final boolean isEnabled() {
        return this.f21888d.isEnabled();
    }

    @Override // android.view.MenuItem
    public final boolean isVisible() {
        return this.f21888d.isVisible();
    }

    @Override // android.view.MenuItem
    public final MenuItem setActionProvider(ActionProvider actionProvider) {
        a aVar = new a(actionProvider);
        if (actionProvider == null) {
            aVar = null;
        }
        this.f21888d.b(aVar);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setActionView(View view) {
        if (view instanceof CollapsibleActionView) {
            view = new b(view);
        }
        this.f21888d.setActionView(view);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setAlphabeticShortcut(char c12) {
        this.f21888d.setAlphabeticShortcut(c12);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setCheckable(boolean z12) {
        this.f21888d.setCheckable(z12);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setChecked(boolean z12) {
        this.f21888d.setChecked(z12);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setContentDescription(CharSequence charSequence) {
        this.f21888d.setContentDescription(charSequence);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setEnabled(boolean z12) {
        this.f21888d.setEnabled(z12);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setIcon(Drawable drawable) {
        this.f21888d.setIcon(drawable);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setIconTintList(ColorStateList colorStateList) {
        this.f21888d.setIconTintList(colorStateList);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setIconTintMode(PorterDuff.Mode mode) {
        this.f21888d.setIconTintMode(mode);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setIntent(Intent intent) {
        this.f21888d.setIntent(intent);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setNumericShortcut(char c12) {
        this.f21888d.setNumericShortcut(c12);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
        this.f21888d.setOnActionExpandListener(onActionExpandListener != null ? new c(onActionExpandListener) : null);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        this.f21888d.setOnMenuItemClickListener(onMenuItemClickListener != null ? new d(onMenuItemClickListener) : null);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setShortcut(char c12, char c13) {
        this.f21888d.setShortcut(c12, c13);
        return this;
    }

    @Override // android.view.MenuItem
    public final void setShowAsAction(int i12) {
        this.f21888d.setShowAsAction(i12);
    }

    @Override // android.view.MenuItem
    public final MenuItem setShowAsActionFlags(int i12) {
        this.f21888d.setShowAsActionFlags(i12);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setTitle(CharSequence charSequence) {
        this.f21888d.setTitle(charSequence);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setTitleCondensed(CharSequence charSequence) {
        this.f21888d.setTitleCondensed(charSequence);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setTooltipText(CharSequence charSequence) {
        this.f21888d.setTooltipText(charSequence);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setVisible(boolean z12) {
        return this.f21888d.setVisible(z12);
    }

    @Override // android.view.MenuItem
    public final MenuItem setAlphabeticShortcut(char c12, int i12) {
        this.f21888d.setAlphabeticShortcut(c12, i12);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setIcon(int i12) {
        this.f21888d.setIcon(i12);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setNumericShortcut(char c12, int i12) {
        this.f21888d.setNumericShortcut(c12, i12);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setShortcut(char c12, char c13, int i12, int i13) {
        this.f21888d.setShortcut(c12, c13, i12, i13);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setTitle(int i12) {
        this.f21888d.setTitle(i12);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setActionView(int i12) {
        K0.b bVar = this.f21888d;
        bVar.setActionView(i12);
        View actionView = bVar.getActionView();
        if (actionView instanceof CollapsibleActionView) {
            bVar.setActionView(new b(actionView));
        }
        return this;
    }
}
