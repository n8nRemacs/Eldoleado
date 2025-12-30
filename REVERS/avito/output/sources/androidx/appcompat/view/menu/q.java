package androidx.appcompat.view.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import androidx.annotation.RestrictTo;
import androidx.collection.o1;

/* compiled from: MenuWrapperICS.java */
@RestrictTo
/* loaded from: classes.dex */
public class q extends c implements Menu {

    /* renamed from: d, reason: collision with root package name */
    public final K0.a f21911d;

    public q(Context context, K0.a aVar) {
        super(context);
        if (aVar == null) {
            throw new IllegalArgumentException("Wrapped Object can not be null.");
        }
        this.f21911d = aVar;
    }

    @Override // android.view.Menu
    public final MenuItem add(CharSequence charSequence) {
        return c(this.f21911d.add(charSequence));
    }

    @Override // android.view.Menu
    public final int addIntentOptions(int i12, int i13, int i14, ComponentName componentName, Intent[] intentArr, Intent intent, int i15, MenuItem[] menuItemArr) {
        MenuItem[] menuItemArr2 = menuItemArr != null ? new MenuItem[menuItemArr.length] : null;
        int iAddIntentOptions = this.f21911d.addIntentOptions(i12, i13, i14, componentName, intentArr, intent, i15, menuItemArr2);
        if (menuItemArr2 != null) {
            int length = menuItemArr2.length;
            for (int i16 = 0; i16 < length; i16++) {
                menuItemArr[i16] = c(menuItemArr2[i16]);
            }
        }
        return iAddIntentOptions;
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(CharSequence charSequence) {
        return d(this.f21911d.addSubMenu(charSequence));
    }

    @Override // android.view.Menu
    public final void clear() {
        o1<K0.b, MenuItem> o1Var = this.f21779b;
        if (o1Var != null) {
            o1Var.clear();
        }
        o1<K0.c, SubMenu> o1Var2 = this.f21780c;
        if (o1Var2 != null) {
            o1Var2.clear();
        }
        this.f21911d.clear();
    }

    @Override // android.view.Menu
    public final void close() {
        this.f21911d.close();
    }

    @Override // android.view.Menu
    public final MenuItem findItem(int i12) {
        return c(this.f21911d.findItem(i12));
    }

    @Override // android.view.Menu
    public final MenuItem getItem(int i12) {
        return c(this.f21911d.getItem(i12));
    }

    @Override // android.view.Menu
    public final boolean hasVisibleItems() {
        return this.f21911d.hasVisibleItems();
    }

    @Override // android.view.Menu
    public final boolean isShortcutKey(int i12, KeyEvent keyEvent) {
        return this.f21911d.isShortcutKey(i12, keyEvent);
    }

    @Override // android.view.Menu
    public final boolean performIdentifierAction(int i12, int i13) {
        return this.f21911d.performIdentifierAction(i12, i13);
    }

    @Override // android.view.Menu
    public final boolean performShortcut(int i12, KeyEvent keyEvent, int i13) {
        return this.f21911d.performShortcut(i12, keyEvent, i13);
    }

    @Override // android.view.Menu
    public final void removeGroup(int i12) {
        if (this.f21779b != null) {
            int i13 = 0;
            while (true) {
                o1<K0.b, MenuItem> o1Var = this.f21779b;
                if (i13 >= o1Var.f25790d) {
                    break;
                }
                if (o1Var.f(i13).getGroupId() == i12) {
                    this.f21779b.h(i13);
                    i13--;
                }
                i13++;
            }
        }
        this.f21911d.removeGroup(i12);
    }

    @Override // android.view.Menu
    public final void removeItem(int i12) {
        if (this.f21779b != null) {
            int i13 = 0;
            while (true) {
                o1<K0.b, MenuItem> o1Var = this.f21779b;
                if (i13 >= o1Var.f25790d) {
                    break;
                }
                if (o1Var.f(i13).getItemId() == i12) {
                    this.f21779b.h(i13);
                    break;
                }
                i13++;
            }
        }
        this.f21911d.removeItem(i12);
    }

    @Override // android.view.Menu
    public final void setGroupCheckable(int i12, boolean z12, boolean z13) {
        this.f21911d.setGroupCheckable(i12, z12, z13);
    }

    @Override // android.view.Menu
    public final void setGroupEnabled(int i12, boolean z12) {
        this.f21911d.setGroupEnabled(i12, z12);
    }

    @Override // android.view.Menu
    public final void setGroupVisible(int i12, boolean z12) {
        this.f21911d.setGroupVisible(i12, z12);
    }

    @Override // android.view.Menu
    public final void setQwertyMode(boolean z12) {
        this.f21911d.setQwertyMode(z12);
    }

    @Override // android.view.Menu
    public final int size() {
        return this.f21911d.size();
    }

    @Override // android.view.Menu
    public final MenuItem add(int i12) {
        return c(this.f21911d.add(i12));
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(int i12) {
        return d(this.f21911d.addSubMenu(i12));
    }

    @Override // android.view.Menu
    public final MenuItem add(int i12, int i13, int i14, CharSequence charSequence) {
        return c(this.f21911d.add(i12, i13, i14, charSequence));
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(int i12, int i13, int i14, CharSequence charSequence) {
        return d(this.f21911d.addSubMenu(i12, i13, i14, charSequence));
    }

    @Override // android.view.Menu
    public final MenuItem add(int i12, int i13, int i14, int i15) {
        return c(this.f21911d.add(i12, i13, i14, i15));
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(int i12, int i13, int i14, int i15) {
        return d(this.f21911d.addSubMenu(i12, i13, i14, i15));
    }
}
