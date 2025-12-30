package androidx.appcompat.view.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.annotation.RestrictTo;
import androidx.core.view.C22825i0;
import j.N;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: MenuBuilder.java */
@RestrictTo
/* loaded from: classes.dex */
public class h implements K0.a {

    /* renamed from: y, reason: collision with root package name */
    public static final int[] f21830y = {1, 4, 5, 3, 2, 0};

    /* renamed from: a, reason: collision with root package name */
    public final Context f21831a;

    /* renamed from: b, reason: collision with root package name */
    public final Resources f21832b;

    /* renamed from: c, reason: collision with root package name */
    public boolean f21833c;

    /* renamed from: d, reason: collision with root package name */
    public final boolean f21834d;

    /* renamed from: e, reason: collision with root package name */
    public a f21835e;

    /* renamed from: f, reason: collision with root package name */
    public final ArrayList<k> f21836f;

    /* renamed from: g, reason: collision with root package name */
    public final ArrayList<k> f21837g;

    /* renamed from: h, reason: collision with root package name */
    public boolean f21838h;

    /* renamed from: i, reason: collision with root package name */
    public final ArrayList<k> f21839i;

    /* renamed from: j, reason: collision with root package name */
    public final ArrayList<k> f21840j;

    /* renamed from: k, reason: collision with root package name */
    public boolean f21841k;

    /* renamed from: m, reason: collision with root package name */
    public CharSequence f21843m;

    /* renamed from: n, reason: collision with root package name */
    public Drawable f21844n;

    /* renamed from: o, reason: collision with root package name */
    public View f21845o;

    /* renamed from: v, reason: collision with root package name */
    public k f21852v;

    /* renamed from: x, reason: collision with root package name */
    public boolean f21854x;

    /* renamed from: l, reason: collision with root package name */
    public int f21842l = 0;

    /* renamed from: p, reason: collision with root package name */
    public boolean f21846p = false;

    /* renamed from: q, reason: collision with root package name */
    public boolean f21847q = false;

    /* renamed from: r, reason: collision with root package name */
    public boolean f21848r = false;

    /* renamed from: s, reason: collision with root package name */
    public boolean f21849s = false;

    /* renamed from: t, reason: collision with root package name */
    public final ArrayList<k> f21850t = new ArrayList<>();

    /* renamed from: u, reason: collision with root package name */
    public final CopyOnWriteArrayList<WeakReference<o>> f21851u = new CopyOnWriteArrayList<>();

    /* renamed from: w, reason: collision with root package name */
    public boolean f21853w = false;

    /* compiled from: MenuBuilder.java */
    @RestrictTo
    public interface a {
        void a(@N h hVar);

        boolean b(@N h hVar, @N MenuItem menuItem);
    }

    /* compiled from: MenuBuilder.java */
    @RestrictTo
    public interface b {
        boolean e(k kVar);
    }

    public h(Context context) {
        boolean z12 = false;
        this.f21831a = context;
        Resources resources = context.getResources();
        this.f21832b = resources;
        this.f21836f = new ArrayList<>();
        this.f21837g = new ArrayList<>();
        this.f21838h = true;
        this.f21839i = new ArrayList<>();
        this.f21840j = new ArrayList<>();
        this.f21841k = true;
        if (resources.getConfiguration().keyboard != 1 && C22825i0.a(ViewConfiguration.get(context), context)) {
            z12 = true;
        }
        this.f21834d = z12;
    }

    public k a(int i12, int i13, int i14, CharSequence charSequence) {
        int i15;
        int i16 = ((-65536) & i14) >> 16;
        if (i16 < 0 || i16 >= 6) {
            throw new IllegalArgumentException("order does not contain a valid category.");
        }
        int i17 = (f21830y[i16] << 16) | (65535 & i14);
        k kVar = new k(this, i12, i13, i14, i17, charSequence, this.f21842l);
        ArrayList<k> arrayList = this.f21836f;
        int size = arrayList.size() - 1;
        while (true) {
            if (size < 0) {
                i15 = 0;
                break;
            }
            if (arrayList.get(size).f21864d <= i17) {
                i15 = size + 1;
                break;
            }
            size--;
        }
        arrayList.add(i15, kVar);
        p(true);
        return kVar;
    }

    @Override // android.view.Menu
    public final MenuItem add(CharSequence charSequence) {
        return a(0, 0, 0, charSequence);
    }

    @Override // android.view.Menu
    public final int addIntentOptions(int i12, int i13, int i14, ComponentName componentName, Intent[] intentArr, Intent intent, int i15, MenuItem[] menuItemArr) {
        int i16;
        PackageManager packageManager = this.f21831a.getPackageManager();
        List<ResolveInfo> listQueryIntentActivityOptions = packageManager.queryIntentActivityOptions(componentName, intentArr, intent, 0);
        int size = listQueryIntentActivityOptions != null ? listQueryIntentActivityOptions.size() : 0;
        if ((i15 & 1) == 0) {
            removeGroup(i12);
        }
        for (int i17 = 0; i17 < size; i17++) {
            ResolveInfo resolveInfo = listQueryIntentActivityOptions.get(i17);
            int i18 = resolveInfo.specificIndex;
            Intent intent2 = new Intent(i18 < 0 ? intent : intentArr[i18]);
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            intent2.setComponent(new ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name));
            k kVarA = a(i12, i13, i14, resolveInfo.loadLabel(packageManager));
            kVarA.setIcon(resolveInfo.loadIcon(packageManager));
            kVarA.f21867g = intent2;
            if (menuItemArr != null && (i16 = resolveInfo.specificIndex) >= 0) {
                menuItemArr[i16] = kVarA;
            }
        }
        return size;
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(CharSequence charSequence) {
        return addSubMenu(0, 0, 0, charSequence);
    }

    public final void b(o oVar, Context context) {
        this.f21851u.add(new WeakReference<>(oVar));
        oVar.i(context, this);
        this.f21841k = true;
    }

    public final void c(boolean z12) {
        if (this.f21849s) {
            return;
        }
        this.f21849s = true;
        CopyOnWriteArrayList<WeakReference<o>> copyOnWriteArrayList = this.f21851u;
        Iterator<WeakReference<o>> it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            WeakReference<o> next = it.next();
            o oVar = next.get();
            if (oVar == null) {
                copyOnWriteArrayList.remove(next);
            } else {
                oVar.b(this, z12);
            }
        }
        this.f21849s = false;
    }

    @Override // android.view.Menu
    public final void clear() {
        k kVar = this.f21852v;
        if (kVar != null) {
            d(kVar);
        }
        this.f21836f.clear();
        p(true);
    }

    public final void clearHeader() {
        this.f21844n = null;
        this.f21843m = null;
        this.f21845o = null;
        p(false);
    }

    @Override // android.view.Menu
    public final void close() {
        c(true);
    }

    public boolean d(k kVar) {
        CopyOnWriteArrayList<WeakReference<o>> copyOnWriteArrayList = this.f21851u;
        boolean zE2 = false;
        if (!copyOnWriteArrayList.isEmpty() && this.f21852v == kVar) {
            w();
            Iterator<WeakReference<o>> it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                WeakReference<o> next = it.next();
                o oVar = next.get();
                if (oVar == null) {
                    copyOnWriteArrayList.remove(next);
                } else {
                    zE2 = oVar.e(kVar);
                    if (zE2) {
                        break;
                    }
                }
            }
            v();
            if (zE2) {
                this.f21852v = null;
            }
        }
        return zE2;
    }

    public boolean e(@N h hVar, @N MenuItem menuItem) {
        a aVar = this.f21835e;
        return aVar != null && aVar.b(hVar, menuItem);
    }

    public boolean f(k kVar) {
        CopyOnWriteArrayList<WeakReference<o>> copyOnWriteArrayList = this.f21851u;
        boolean zG2 = false;
        if (copyOnWriteArrayList.isEmpty()) {
            return false;
        }
        w();
        Iterator<WeakReference<o>> it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            WeakReference<o> next = it.next();
            o oVar = next.get();
            if (oVar == null) {
                copyOnWriteArrayList.remove(next);
            } else {
                zG2 = oVar.g(kVar);
                if (zG2) {
                    break;
                }
            }
        }
        v();
        if (zG2) {
            this.f21852v = kVar;
        }
        return zG2;
    }

    @Override // android.view.Menu
    public final MenuItem findItem(int i12) {
        MenuItem menuItemFindItem;
        ArrayList<k> arrayList = this.f21836f;
        int size = arrayList.size();
        for (int i13 = 0; i13 < size; i13++) {
            k kVar = arrayList.get(i13);
            if (kVar.f21861a == i12) {
                return kVar;
            }
            if (kVar.hasSubMenu() && (menuItemFindItem = kVar.f21875o.findItem(i12)) != null) {
                return menuItemFindItem;
            }
        }
        return null;
    }

    public final k g(int i12, KeyEvent keyEvent) {
        ArrayList<k> arrayList = this.f21850t;
        arrayList.clear();
        h(arrayList, i12, keyEvent);
        if (arrayList.isEmpty()) {
            return null;
        }
        int metaState = keyEvent.getMetaState();
        KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData();
        keyEvent.getKeyData(keyData);
        int size = arrayList.size();
        if (size == 1) {
            return arrayList.get(0);
        }
        boolean zN2 = n();
        for (int i13 = 0; i13 < size; i13++) {
            k kVar = arrayList.get(i13);
            char c12 = zN2 ? kVar.f21870j : kVar.f21868h;
            char[] cArr = keyData.meta;
            if ((c12 == cArr[0] && (metaState & 2) == 0) || ((c12 == cArr[2] && (metaState & 2) != 0) || (zN2 && c12 == '\b' && i12 == 67))) {
                return kVar;
            }
        }
        return null;
    }

    @Override // android.view.Menu
    public final MenuItem getItem(int i12) {
        return this.f21836f.get(i12);
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0069  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void h(java.util.ArrayList r17, int r18, android.view.KeyEvent r19) {
        /*
            r16 = this;
            r0 = r17
            r1 = r18
            r2 = r19
            boolean r3 = r16.n()
            int r4 = r19.getModifiers()
            android.view.KeyCharacterMap$KeyData r5 = new android.view.KeyCharacterMap$KeyData
            r5.<init>()
            boolean r6 = r2.getKeyData(r5)
            r7 = 67
            if (r6 != 0) goto L1e
            if (r1 == r7) goto L1e
            return
        L1e:
            r6 = r16
            java.util.ArrayList<androidx.appcompat.view.menu.k> r8 = r6.f21836f
            int r9 = r8.size()
            r10 = 0
            r11 = r10
        L28:
            if (r11 >= r9) goto L7a
            java.lang.Object r12 = r8.get(r11)
            androidx.appcompat.view.menu.k r12 = (androidx.appcompat.view.menu.k) r12
            boolean r13 = r12.hasSubMenu()
            if (r13 == 0) goto L3b
            androidx.appcompat.view.menu.t r13 = r12.f21875o
            r13.h(r0, r1, r2)
        L3b:
            if (r3 == 0) goto L40
            char r13 = r12.f21870j
            goto L42
        L40:
            char r13 = r12.f21868h
        L42:
            if (r3 == 0) goto L47
            int r14 = r12.f21871k
            goto L49
        L47:
            int r14 = r12.f21869i
        L49:
            r15 = 69647(0x1100f, float:9.7596E-41)
            r7 = r4 & r15
            r14 = r14 & r15
            if (r7 != r14) goto L69
            if (r13 == 0) goto L69
            char[] r7 = r5.meta
            char r14 = r7[r10]
            if (r13 == r14) goto L6c
            r14 = 2
            char r7 = r7[r14]
            if (r13 == r7) goto L6c
            if (r3 == 0) goto L69
            r7 = 8
            if (r13 != r7) goto L69
            r7 = 67
            if (r1 != r7) goto L77
            goto L6e
        L69:
            r7 = 67
            goto L77
        L6c:
            r7 = 67
        L6e:
            boolean r13 = r12.isEnabled()
            if (r13 == 0) goto L77
            r0.add(r12)
        L77:
            int r11 = r11 + 1
            goto L28
        L7a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.view.menu.h.h(java.util.ArrayList, int, android.view.KeyEvent):void");
    }

    @Override // android.view.Menu
    public final boolean hasVisibleItems() {
        if (this.f21854x) {
            return true;
        }
        ArrayList<k> arrayList = this.f21836f;
        int size = arrayList.size();
        for (int i12 = 0; i12 < size; i12++) {
            if (arrayList.get(i12).isVisible()) {
                return true;
            }
        }
        return false;
    }

    public final void i() {
        ArrayList<k> arrayListL = l();
        if (this.f21841k) {
            CopyOnWriteArrayList<WeakReference<o>> copyOnWriteArrayList = this.f21851u;
            Iterator<WeakReference<o>> it = copyOnWriteArrayList.iterator();
            boolean zH2 = false;
            while (it.hasNext()) {
                WeakReference<o> next = it.next();
                o oVar = next.get();
                if (oVar == null) {
                    copyOnWriteArrayList.remove(next);
                } else {
                    zH2 |= oVar.h();
                }
            }
            ArrayList<k> arrayList = this.f21839i;
            ArrayList<k> arrayList2 = this.f21840j;
            if (zH2) {
                arrayList.clear();
                arrayList2.clear();
                int size = arrayListL.size();
                for (int i12 = 0; i12 < size; i12++) {
                    k kVar = arrayListL.get(i12);
                    if (kVar.f()) {
                        arrayList.add(kVar);
                    } else {
                        arrayList2.add(kVar);
                    }
                }
            } else {
                arrayList.clear();
                arrayList2.clear();
                arrayList2.addAll(l());
            }
            this.f21841k = false;
        }
    }

    @Override // android.view.Menu
    public final boolean isShortcutKey(int i12, KeyEvent keyEvent) {
        return g(i12, keyEvent) != null;
    }

    public String j() {
        return "android:menu:actionviewstates";
    }

    @N
    public final ArrayList<k> l() {
        boolean z12 = this.f21838h;
        ArrayList<k> arrayList = this.f21837g;
        if (!z12) {
            return arrayList;
        }
        arrayList.clear();
        ArrayList<k> arrayList2 = this.f21836f;
        int size = arrayList2.size();
        for (int i12 = 0; i12 < size; i12++) {
            k kVar = arrayList2.get(i12);
            if (kVar.isVisible()) {
                arrayList.add(kVar);
            }
        }
        this.f21838h = false;
        this.f21841k = true;
        return arrayList;
    }

    public boolean m() {
        return this.f21853w;
    }

    public boolean n() {
        return this.f21833c;
    }

    public boolean o() {
        return this.f21834d;
    }

    public void p(boolean z12) {
        if (this.f21846p) {
            this.f21847q = true;
            if (z12) {
                this.f21848r = true;
                return;
            }
            return;
        }
        if (z12) {
            this.f21838h = true;
            this.f21841k = true;
        }
        CopyOnWriteArrayList<WeakReference<o>> copyOnWriteArrayList = this.f21851u;
        if (copyOnWriteArrayList.isEmpty()) {
            return;
        }
        w();
        Iterator<WeakReference<o>> it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            WeakReference<o> next = it.next();
            o oVar = next.get();
            if (oVar == null) {
                copyOnWriteArrayList.remove(next);
            } else {
                oVar.c(z12);
            }
        }
        v();
    }

    @Override // android.view.Menu
    public final boolean performIdentifierAction(int i12, int i13) {
        return q(findItem(i12), null, i13);
    }

    @Override // android.view.Menu
    public final boolean performShortcut(int i12, KeyEvent keyEvent, int i13) {
        k kVarG = g(i12, keyEvent);
        boolean zQ2 = kVarG != null ? q(kVarG, null, i13) : false;
        if ((i13 & 2) != 0) {
            c(true);
        }
        return zQ2;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0018  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean q(android.view.MenuItem r7, androidx.appcompat.view.menu.o r8, int r9) {
        /*
            r6 = this;
            androidx.appcompat.view.menu.k r7 = (androidx.appcompat.view.menu.k) r7
            r0 = 0
            if (r7 == 0) goto Lc4
            boolean r1 = r7.isEnabled()
            if (r1 != 0) goto Ld
            goto Lc4
        Ld:
            android.view.MenuItem$OnMenuItemClickListener r1 = r7.f21876p
            r2 = 1
            if (r1 == 0) goto L1a
            boolean r1 = r1.onMenuItemClick(r7)
            if (r1 == 0) goto L1a
        L18:
            r1 = r2
            goto L39
        L1a:
            androidx.appcompat.view.menu.h r1 = r7.f21874n
            boolean r3 = r1.e(r1, r7)
            if (r3 == 0) goto L23
            goto L18
        L23:
            android.content.Intent r3 = r7.f21867g
            if (r3 == 0) goto L2d
            android.content.Context r1 = r1.f21831a     // Catch: android.content.ActivityNotFoundException -> L2d
            r1.startActivity(r3)     // Catch: android.content.ActivityNotFoundException -> L2d
            goto L18
        L2d:
            androidx.core.view.b r1 = r7.f21858A
            if (r1 == 0) goto L38
            boolean r1 = r1.e()
            if (r1 == 0) goto L38
            goto L18
        L38:
            r1 = r0
        L39:
            androidx.core.view.b r3 = r7.f21858A
            if (r3 == 0) goto L45
            boolean r4 = r3.a()
            if (r4 == 0) goto L45
            r4 = r2
            goto L46
        L45:
            r4 = r0
        L46:
            boolean r5 = r7.e()
            if (r5 == 0) goto L58
            boolean r7 = r7.expandActionView()
            r1 = r1 | r7
            if (r1 == 0) goto Lc3
            r6.c(r2)
            goto Lc3
        L58:
            boolean r5 = r7.hasSubMenu()
            if (r5 != 0) goto L69
            if (r4 == 0) goto L61
            goto L69
        L61:
            r7 = r9 & 1
            if (r7 != 0) goto Lc3
            r6.c(r2)
            goto Lc3
        L69:
            r9 = r9 & 4
            if (r9 != 0) goto L70
            r6.c(r0)
        L70:
            boolean r9 = r7.hasSubMenu()
            if (r9 != 0) goto L84
            androidx.appcompat.view.menu.t r9 = new androidx.appcompat.view.menu.t
            android.content.Context r5 = r6.f21831a
            r9.<init>(r5, r6, r7)
            r7.f21875o = r9
            java.lang.CharSequence r5 = r7.f21865e
            r9.setHeaderTitle(r5)
        L84:
            androidx.appcompat.view.menu.t r7 = r7.f21875o
            if (r4 == 0) goto L8b
            r3.f(r7)
        L8b:
            java.util.concurrent.CopyOnWriteArrayList<java.lang.ref.WeakReference<androidx.appcompat.view.menu.o>> r9 = r6.f21851u
            boolean r3 = r9.isEmpty()
            if (r3 == 0) goto L94
            goto Lbd
        L94:
            if (r8 == 0) goto L9a
            boolean r0 = r8.f(r7)
        L9a:
            java.util.Iterator r8 = r9.iterator()
        L9e:
            boolean r3 = r8.hasNext()
            if (r3 == 0) goto Lbd
            java.lang.Object r3 = r8.next()
            java.lang.ref.WeakReference r3 = (java.lang.ref.WeakReference) r3
            java.lang.Object r4 = r3.get()
            androidx.appcompat.view.menu.o r4 = (androidx.appcompat.view.menu.o) r4
            if (r4 != 0) goto Lb6
            r9.remove(r3)
            goto L9e
        Lb6:
            if (r0 != 0) goto L9e
            boolean r0 = r4.f(r7)
            goto L9e
        Lbd:
            r1 = r1 | r0
            if (r1 != 0) goto Lc3
            r6.c(r2)
        Lc3:
            return r1
        Lc4:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.view.menu.h.q(android.view.MenuItem, androidx.appcompat.view.menu.o, int):boolean");
    }

    public final void r(o oVar) {
        CopyOnWriteArrayList<WeakReference<o>> copyOnWriteArrayList = this.f21851u;
        Iterator<WeakReference<o>> it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            WeakReference<o> next = it.next();
            o oVar2 = next.get();
            if (oVar2 == null || oVar2 == oVar) {
                copyOnWriteArrayList.remove(next);
            }
        }
    }

    @Override // android.view.Menu
    public final void removeGroup(int i12) {
        ArrayList<k> arrayList = this.f21836f;
        int size = arrayList.size();
        int i13 = 0;
        int i14 = 0;
        while (true) {
            if (i14 >= size) {
                i14 = -1;
                break;
            } else if (arrayList.get(i14).f21862b == i12) {
                break;
            } else {
                i14++;
            }
        }
        if (i14 >= 0) {
            int size2 = arrayList.size() - i14;
            while (true) {
                int i15 = i13 + 1;
                if (i13 >= size2 || arrayList.get(i14).f21862b != i12) {
                    break;
                }
                if (i14 >= 0) {
                    ArrayList<k> arrayList2 = this.f21836f;
                    if (i14 < arrayList2.size()) {
                        arrayList2.remove(i14);
                    }
                }
                i13 = i15;
            }
            p(true);
        }
    }

    @Override // android.view.Menu
    public final void removeItem(int i12) {
        ArrayList<k> arrayList = this.f21836f;
        int size = arrayList.size();
        int i13 = 0;
        while (true) {
            if (i13 >= size) {
                i13 = -1;
                break;
            } else if (arrayList.get(i13).f21861a == i12) {
                break;
            } else {
                i13++;
            }
        }
        if (i13 >= 0) {
            ArrayList<k> arrayList2 = this.f21836f;
            if (i13 >= arrayList2.size()) {
                return;
            }
            arrayList2.remove(i13);
            p(true);
        }
    }

    public final void s(Bundle bundle) {
        MenuItem menuItemFindItem;
        if (bundle == null) {
            return;
        }
        SparseArray<Parcelable> sparseParcelableArray = bundle.getSparseParcelableArray(j());
        int size = this.f21836f.size();
        for (int i12 = 0; i12 < size; i12++) {
            MenuItem item = getItem(i12);
            View actionView = item.getActionView();
            if (actionView != null && actionView.getId() != -1) {
                actionView.restoreHierarchyState(sparseParcelableArray);
            }
            if (item.hasSubMenu()) {
                ((t) item.getSubMenu()).s(bundle);
            }
        }
        int i13 = bundle.getInt("android:menu:expandedactionview");
        if (i13 <= 0 || (menuItemFindItem = findItem(i13)) == null) {
            return;
        }
        menuItemFindItem.expandActionView();
    }

    @Override // android.view.Menu
    public final void setGroupCheckable(int i12, boolean z12, boolean z13) {
        ArrayList<k> arrayList = this.f21836f;
        int size = arrayList.size();
        for (int i13 = 0; i13 < size; i13++) {
            k kVar = arrayList.get(i13);
            if (kVar.f21862b == i12) {
                kVar.g(z13);
                kVar.setCheckable(z12);
            }
        }
    }

    @Override // android.view.Menu
    public void setGroupDividerEnabled(boolean z12) {
        this.f21853w = z12;
    }

    @Override // android.view.Menu
    public final void setGroupEnabled(int i12, boolean z12) {
        ArrayList<k> arrayList = this.f21836f;
        int size = arrayList.size();
        for (int i13 = 0; i13 < size; i13++) {
            k kVar = arrayList.get(i13);
            if (kVar.f21862b == i12) {
                kVar.setEnabled(z12);
            }
        }
    }

    @Override // android.view.Menu
    public final void setGroupVisible(int i12, boolean z12) {
        ArrayList<k> arrayList = this.f21836f;
        int size = arrayList.size();
        boolean z13 = false;
        for (int i13 = 0; i13 < size; i13++) {
            k kVar = arrayList.get(i13);
            if (kVar.f21862b == i12) {
                int i14 = kVar.f21884x;
                int i15 = (i14 & (-9)) | (z12 ? 0 : 8);
                kVar.f21884x = i15;
                if (i14 != i15) {
                    z13 = true;
                }
            }
        }
        if (z13) {
            p(true);
        }
    }

    @Override // android.view.Menu
    public void setQwertyMode(boolean z12) {
        this.f21833c = z12;
        p(false);
    }

    @Override // android.view.Menu
    public final int size() {
        return this.f21836f.size();
    }

    public final void t(Bundle bundle) {
        int size = this.f21836f.size();
        SparseArray<? extends Parcelable> sparseArray = null;
        for (int i12 = 0; i12 < size; i12++) {
            MenuItem item = getItem(i12);
            View actionView = item.getActionView();
            if (actionView != null && actionView.getId() != -1) {
                if (sparseArray == null) {
                    sparseArray = new SparseArray<>();
                }
                actionView.saveHierarchyState(sparseArray);
                if (item.isActionViewExpanded()) {
                    bundle.putInt("android:menu:expandedactionview", item.getItemId());
                }
            }
            if (item.hasSubMenu()) {
                ((t) item.getSubMenu()).t(bundle);
            }
        }
        if (sparseArray != null) {
            bundle.putSparseParcelableArray(j(), sparseArray);
        }
    }

    public final void u(int i12, CharSequence charSequence, int i13, Drawable drawable, View view) {
        if (view != null) {
            this.f21845o = view;
            this.f21843m = null;
            this.f21844n = null;
        } else {
            if (i12 > 0) {
                this.f21843m = this.f21832b.getText(i12);
            } else if (charSequence != null) {
                this.f21843m = charSequence;
            }
            if (i13 > 0) {
                this.f21844n = androidx.core.content.d.getDrawable(this.f21831a, i13);
            } else if (drawable != null) {
                this.f21844n = drawable;
            }
            this.f21845o = null;
        }
        p(false);
    }

    public final void v() {
        this.f21846p = false;
        if (this.f21847q) {
            this.f21847q = false;
            p(this.f21848r);
        }
    }

    public final void w() {
        if (this.f21846p) {
            return;
        }
        this.f21846p = true;
        this.f21847q = false;
        this.f21848r = false;
    }

    @Override // android.view.Menu
    public final MenuItem add(int i12) {
        return a(0, 0, 0, this.f21832b.getString(i12));
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(int i12) {
        return addSubMenu(0, 0, 0, this.f21832b.getString(i12));
    }

    @Override // android.view.Menu
    public final MenuItem add(int i12, int i13, int i14, CharSequence charSequence) {
        return a(i12, i13, i14, charSequence);
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i12, int i13, int i14, CharSequence charSequence) {
        k kVarA = a(i12, i13, i14, charSequence);
        t tVar = new t(this.f21831a, this, kVarA);
        kVarA.f21875o = tVar;
        tVar.setHeaderTitle(kVarA.f21865e);
        return tVar;
    }

    @Override // android.view.Menu
    public final MenuItem add(int i12, int i13, int i14, int i15) {
        return a(i12, i13, i14, this.f21832b.getString(i15));
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(int i12, int i13, int i14, int i15) {
        return addSubMenu(i12, i13, i14, this.f21832b.getString(i15));
    }

    public h k() {
        return this;
    }
}
