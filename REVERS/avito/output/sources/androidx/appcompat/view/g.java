package androidx.appcompat.view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import androidx.annotation.RestrictTo;
import androidx.appcompat.app.r;
import androidx.appcompat.view.menu.k;
import androidx.appcompat.view.menu.l;
import androidx.appcompat.widget.D0;
import androidx.appcompat.widget.V;
import androidx.core.view.AbstractC22810b;
import com.avito.android.remote.model.messenger.context.ChannelContext;
import j.I;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import l.C43521a;
import okhttp3.internal.http2.Settings;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: SupportMenuInflater.java */
@RestrictTo
/* loaded from: classes.dex */
public class g extends MenuInflater {

    /* renamed from: e, reason: collision with root package name */
    public static final Class<?>[] f21674e;

    /* renamed from: f, reason: collision with root package name */
    public static final Class<?>[] f21675f;

    /* renamed from: a, reason: collision with root package name */
    public final Object[] f21676a;

    /* renamed from: b, reason: collision with root package name */
    public final Object[] f21677b;

    /* renamed from: c, reason: collision with root package name */
    public final Context f21678c;

    /* renamed from: d, reason: collision with root package name */
    public Object f21679d;

    /* compiled from: SupportMenuInflater.java */
    public static class a implements MenuItem.OnMenuItemClickListener {

        /* renamed from: d, reason: collision with root package name */
        public static final Class<?>[] f21680d = {MenuItem.class};

        /* renamed from: b, reason: collision with root package name */
        public Object f21681b;

        /* renamed from: c, reason: collision with root package name */
        public Method f21682c;

        @Override // android.view.MenuItem.OnMenuItemClickListener
        public final boolean onMenuItemClick(MenuItem menuItem) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            Method method = this.f21682c;
            try {
                Class<?> returnType = method.getReturnType();
                Class<?> cls = Boolean.TYPE;
                Object obj = this.f21681b;
                if (returnType == cls) {
                    return ((Boolean) method.invoke(obj, menuItem)).booleanValue();
                }
                method.invoke(obj, menuItem);
                return true;
            } catch (Exception e12) {
                throw new RuntimeException(e12);
            }
        }
    }

    /* compiled from: SupportMenuInflater.java */
    public class b {

        /* renamed from: A, reason: collision with root package name */
        public CharSequence f21683A;

        /* renamed from: B, reason: collision with root package name */
        public CharSequence f21684B;

        /* renamed from: a, reason: collision with root package name */
        public final Menu f21688a;

        /* renamed from: h, reason: collision with root package name */
        public boolean f21695h;

        /* renamed from: i, reason: collision with root package name */
        public int f21696i;

        /* renamed from: j, reason: collision with root package name */
        public int f21697j;

        /* renamed from: k, reason: collision with root package name */
        public CharSequence f21698k;

        /* renamed from: l, reason: collision with root package name */
        public CharSequence f21699l;

        /* renamed from: m, reason: collision with root package name */
        public int f21700m;

        /* renamed from: n, reason: collision with root package name */
        public char f21701n;

        /* renamed from: o, reason: collision with root package name */
        public int f21702o;

        /* renamed from: p, reason: collision with root package name */
        public char f21703p;

        /* renamed from: q, reason: collision with root package name */
        public int f21704q;

        /* renamed from: r, reason: collision with root package name */
        public int f21705r;

        /* renamed from: s, reason: collision with root package name */
        public boolean f21706s;

        /* renamed from: t, reason: collision with root package name */
        public boolean f21707t;

        /* renamed from: u, reason: collision with root package name */
        public boolean f21708u;

        /* renamed from: v, reason: collision with root package name */
        public int f21709v;

        /* renamed from: w, reason: collision with root package name */
        public int f21710w;

        /* renamed from: x, reason: collision with root package name */
        public String f21711x;

        /* renamed from: y, reason: collision with root package name */
        public String f21712y;

        /* renamed from: z, reason: collision with root package name */
        public AbstractC22810b f21713z;

        /* renamed from: C, reason: collision with root package name */
        public ColorStateList f21685C = null;

        /* renamed from: D, reason: collision with root package name */
        public PorterDuff.Mode f21686D = null;

        /* renamed from: b, reason: collision with root package name */
        public int f21689b = 0;

        /* renamed from: c, reason: collision with root package name */
        public int f21690c = 0;

        /* renamed from: d, reason: collision with root package name */
        public int f21691d = 0;

        /* renamed from: e, reason: collision with root package name */
        public int f21692e = 0;

        /* renamed from: f, reason: collision with root package name */
        public boolean f21693f = true;

        /* renamed from: g, reason: collision with root package name */
        public boolean f21694g = true;

        public b(Menu menu) {
            this.f21688a = menu;
        }

        public final <T> T a(String str, Class<?>[] clsArr, Object[] objArr) throws NoSuchMethodException, SecurityException {
            try {
                Constructor<?> constructor = Class.forName(str, false, g.this.f21678c.getClassLoader()).getConstructor(clsArr);
                constructor.setAccessible(true);
                return (T) constructor.newInstance(objArr);
            } catch (Exception unused) {
                return null;
            }
        }

        public final void b(MenuItem menuItem) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            boolean z12 = false;
            menuItem.setChecked(this.f21706s).setVisible(this.f21707t).setEnabled(this.f21708u).setCheckable(this.f21705r >= 1).setTitleCondensed(this.f21699l).setIcon(this.f21700m);
            int i12 = this.f21709v;
            if (i12 >= 0) {
                menuItem.setShowAsAction(i12);
            }
            String str = this.f21712y;
            g gVar = g.this;
            if (str != null) {
                if (gVar.f21678c.isRestricted()) {
                    throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
                }
                if (gVar.f21679d == null) {
                    gVar.f21679d = g.a(gVar.f21678c);
                }
                Object obj = gVar.f21679d;
                String str2 = this.f21712y;
                a aVar = new a();
                aVar.f21681b = obj;
                Class<?> cls = obj.getClass();
                try {
                    aVar.f21682c = cls.getMethod(str2, a.f21680d);
                    menuItem.setOnMenuItemClickListener(aVar);
                } catch (Exception e12) {
                    StringBuilder sbA = r.A("Couldn't resolve menu item onClick handler ", str2, " in class ");
                    sbA.append(cls.getName());
                    InflateException inflateException = new InflateException(sbA.toString());
                    inflateException.initCause(e12);
                    throw inflateException;
                }
            }
            if (this.f21705r >= 2) {
                if (menuItem instanceof k) {
                    ((k) menuItem).g(true);
                } else if (menuItem instanceof l) {
                    l lVar = (l) menuItem;
                    try {
                        Method method = lVar.f21889e;
                        K0.b bVar = lVar.f21888d;
                        if (method == null) {
                            lVar.f21889e = bVar.getClass().getDeclaredMethod("setExclusiveCheckable", Boolean.TYPE);
                        }
                        lVar.f21889e.invoke(bVar, Boolean.TRUE);
                    } catch (Exception unused) {
                    }
                }
            }
            String str3 = this.f21711x;
            if (str3 != null) {
                menuItem.setActionView((View) a(str3, g.f21674e, gVar.f21676a));
                z12 = true;
            }
            int i13 = this.f21710w;
            if (i13 > 0 && !z12) {
                menuItem.setActionView(i13);
            }
            AbstractC22810b abstractC22810b = this.f21713z;
            if (abstractC22810b != null && (menuItem instanceof K0.b)) {
                ((K0.b) menuItem).b(abstractC22810b);
            }
            CharSequence charSequence = this.f21683A;
            boolean z13 = menuItem instanceof K0.b;
            if (z13) {
                ((K0.b) menuItem).setContentDescription(charSequence);
            } else {
                menuItem.setContentDescription(charSequence);
            }
            CharSequence charSequence2 = this.f21684B;
            if (z13) {
                ((K0.b) menuItem).setTooltipText(charSequence2);
            } else {
                menuItem.setTooltipText(charSequence2);
            }
            char c12 = this.f21701n;
            int i14 = this.f21702o;
            if (z13) {
                ((K0.b) menuItem).setAlphabeticShortcut(c12, i14);
            } else {
                menuItem.setAlphabeticShortcut(c12, i14);
            }
            char c13 = this.f21703p;
            int i15 = this.f21704q;
            if (z13) {
                ((K0.b) menuItem).setNumericShortcut(c13, i15);
            } else {
                menuItem.setNumericShortcut(c13, i15);
            }
            PorterDuff.Mode mode = this.f21686D;
            if (mode != null) {
                if (z13) {
                    ((K0.b) menuItem).setIconTintMode(mode);
                } else {
                    menuItem.setIconTintMode(mode);
                }
            }
            ColorStateList colorStateList = this.f21685C;
            if (colorStateList != null) {
                if (z13) {
                    ((K0.b) menuItem).setIconTintList(colorStateList);
                } else {
                    menuItem.setIconTintList(colorStateList);
                }
            }
        }
    }

    static {
        Class<?>[] clsArr = {Context.class};
        f21674e = clsArr;
        f21675f = clsArr;
    }

    public g(Context context) {
        super(context);
        this.f21678c = context;
        Object[] objArr = {context};
        this.f21676a = objArr;
        this.f21677b = objArr;
    }

    public static Object a(Object obj) {
        return (!(obj instanceof Activity) && (obj instanceof ContextWrapper)) ? a(((ContextWrapper) obj).getBaseContext()) : obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r4v16 */
    public final void b(XmlResourceParser xmlResourceParser, AttributeSet attributeSet, Menu menu) throws XmlPullParserException, IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        ?? r42;
        int i12;
        boolean z12;
        ColorStateList colorStateList;
        b bVar = new b(menu);
        int eventType = xmlResourceParser.getEventType();
        while (true) {
            r42 = 1;
            i12 = 2;
            if (eventType == 2) {
                String name = xmlResourceParser.getName();
                if (!name.equals(ChannelContext.MENU)) {
                    throw new RuntimeException("Expecting menu, got ".concat(name));
                }
                eventType = xmlResourceParser.next();
            } else {
                eventType = xmlResourceParser.next();
                if (eventType == 1) {
                    break;
                }
            }
        }
        boolean z13 = false;
        boolean z14 = false;
        String str = null;
        while (!z13) {
            if (eventType == r42) {
                throw new RuntimeException("Unexpected end of document");
            }
            if (eventType != i12) {
                if (eventType != 3) {
                    z12 = r42;
                } else {
                    String name2 = xmlResourceParser.getName();
                    if (z14 && name2.equals(str)) {
                        z12 = r42;
                        z14 = false;
                        str = null;
                    } else {
                        if (name2.equals("group")) {
                            bVar.f21689b = 0;
                            bVar.f21690c = 0;
                            bVar.f21691d = 0;
                            bVar.f21692e = 0;
                            bVar.f21693f = r42;
                            bVar.f21694g = r42;
                        } else if (name2.equals("item")) {
                            if (!bVar.f21695h) {
                                AbstractC22810b abstractC22810b = bVar.f21713z;
                                if (abstractC22810b == null || !abstractC22810b.a()) {
                                    bVar.f21695h = r42;
                                    bVar.b(bVar.f21688a.add(bVar.f21689b, bVar.f21696i, bVar.f21697j, bVar.f21698k));
                                } else {
                                    bVar.f21695h = r42;
                                    bVar.b(bVar.f21688a.addSubMenu(bVar.f21689b, bVar.f21696i, bVar.f21697j, bVar.f21698k).getItem());
                                }
                            }
                        } else if (name2.equals(ChannelContext.MENU)) {
                            z12 = r42;
                            z13 = z12;
                        }
                        z12 = r42;
                    }
                }
                eventType = xmlResourceParser.next();
                r42 = z12;
                z14 = z14;
            } else {
                if (!z14) {
                    String name3 = xmlResourceParser.getName();
                    boolean zEquals = name3.equals("group");
                    g gVar = g.this;
                    if (zEquals) {
                        TypedArray typedArrayObtainStyledAttributes = gVar.f21678c.obtainStyledAttributes(attributeSet, C43521a.m.f413367p);
                        bVar.f21689b = typedArrayObtainStyledAttributes.getResourceId(r42, 0);
                        bVar.f21690c = typedArrayObtainStyledAttributes.getInt(3, 0);
                        bVar.f21691d = typedArrayObtainStyledAttributes.getInt(4, 0);
                        bVar.f21692e = typedArrayObtainStyledAttributes.getInt(5, 0);
                        bVar.f21693f = typedArrayObtainStyledAttributes.getBoolean(2, r42);
                        bVar.f21694g = typedArrayObtainStyledAttributes.getBoolean(0, r42);
                        typedArrayObtainStyledAttributes.recycle();
                        z12 = r42;
                        i12 = 2;
                    } else if (name3.equals("item")) {
                        Context context = gVar.f21678c;
                        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, C43521a.m.f413368q);
                        D0 d02 = new D0(typedArrayObtainStyledAttributes2, context);
                        bVar.f21696i = typedArrayObtainStyledAttributes2.getResourceId(2, 0);
                        bVar.f21697j = (typedArrayObtainStyledAttributes2.getInt(6, bVar.f21691d) & Settings.DEFAULT_INITIAL_WINDOW_SIZE) | (typedArrayObtainStyledAttributes2.getInt(5, bVar.f21690c) & (-65536));
                        bVar.f21698k = typedArrayObtainStyledAttributes2.getText(7);
                        bVar.f21699l = typedArrayObtainStyledAttributes2.getText(8);
                        bVar.f21700m = typedArrayObtainStyledAttributes2.getResourceId(0, 0);
                        String string = typedArrayObtainStyledAttributes2.getString(9);
                        bVar.f21701n = string == null ? (char) 0 : string.charAt(0);
                        bVar.f21702o = typedArrayObtainStyledAttributes2.getInt(16, 4096);
                        String string2 = typedArrayObtainStyledAttributes2.getString(10);
                        bVar.f21703p = string2 == null ? (char) 0 : string2.charAt(0);
                        bVar.f21704q = typedArrayObtainStyledAttributes2.getInt(20, 4096);
                        if (typedArrayObtainStyledAttributes2.hasValue(11)) {
                            bVar.f21705r = typedArrayObtainStyledAttributes2.getBoolean(11, false) ? 1 : 0;
                        } else {
                            bVar.f21705r = bVar.f21692e;
                        }
                        bVar.f21706s = typedArrayObtainStyledAttributes2.getBoolean(3, false);
                        bVar.f21707t = typedArrayObtainStyledAttributes2.getBoolean(4, bVar.f21693f);
                        bVar.f21708u = typedArrayObtainStyledAttributes2.getBoolean(1, bVar.f21694g);
                        bVar.f21709v = typedArrayObtainStyledAttributes2.getInt(21, -1);
                        bVar.f21712y = typedArrayObtainStyledAttributes2.getString(12);
                        bVar.f21710w = typedArrayObtainStyledAttributes2.getResourceId(13, 0);
                        bVar.f21711x = typedArrayObtainStyledAttributes2.getString(15);
                        String string3 = typedArrayObtainStyledAttributes2.getString(14);
                        if (string3 != null && bVar.f21710w == 0 && bVar.f21711x == null) {
                            bVar.f21713z = (AbstractC22810b) bVar.a(string3, f21675f, gVar.f21677b);
                        } else {
                            bVar.f21713z = null;
                        }
                        bVar.f21683A = typedArrayObtainStyledAttributes2.getText(17);
                        bVar.f21684B = typedArrayObtainStyledAttributes2.getText(22);
                        if (typedArrayObtainStyledAttributes2.hasValue(19)) {
                            bVar.f21686D = V.c(typedArrayObtainStyledAttributes2.getInt(19, -1), bVar.f21686D);
                            colorStateList = null;
                        } else {
                            colorStateList = null;
                            bVar.f21686D = null;
                        }
                        if (typedArrayObtainStyledAttributes2.hasValue(18)) {
                            bVar.f21685C = d02.a(18);
                        } else {
                            bVar.f21685C = colorStateList;
                        }
                        d02.f();
                        bVar.f21695h = false;
                        i12 = 2;
                        z12 = true;
                    } else {
                        i12 = 2;
                        if (name3.equals(ChannelContext.MENU)) {
                            z12 = true;
                            bVar.f21695h = true;
                            SubMenu subMenuAddSubMenu = bVar.f21688a.addSubMenu(bVar.f21689b, bVar.f21696i, bVar.f21697j, bVar.f21698k);
                            bVar.b(subMenuAddSubMenu.getItem());
                            b(xmlResourceParser, attributeSet, subMenuAddSubMenu);
                        } else {
                            z12 = true;
                            str = name3;
                            z14 = true;
                        }
                    }
                    eventType = xmlResourceParser.next();
                    r42 = z12;
                    z14 = z14;
                }
                z12 = r42;
            }
            eventType = xmlResourceParser.next();
            r42 = z12;
            z14 = z14;
        }
    }

    @Override // android.view.MenuInflater
    public final void inflate(@I int i12, Menu menu) {
        if (!(menu instanceof K0.a)) {
            super.inflate(i12, menu);
            return;
        }
        XmlResourceParser layout = null;
        boolean z12 = false;
        try {
            try {
                layout = this.f21678c.getResources().getLayout(i12);
                AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(layout);
                if (menu instanceof androidx.appcompat.view.menu.h) {
                    androidx.appcompat.view.menu.h hVar = (androidx.appcompat.view.menu.h) menu;
                    if (!hVar.f21846p) {
                        hVar.w();
                        z12 = true;
                    }
                }
                b(layout, attributeSetAsAttributeSet, menu);
                if (z12) {
                    ((androidx.appcompat.view.menu.h) menu).v();
                }
                layout.close();
            } catch (IOException e12) {
                throw new InflateException("Error inflating menu XML", e12);
            } catch (XmlPullParserException e13) {
                throw new InflateException("Error inflating menu XML", e13);
            }
        } catch (Throwable th2) {
            if (z12) {
                ((androidx.appcompat.view.menu.h) menu).v();
            }
            if (layout != null) {
                layout.close();
            }
            throw th2;
        }
    }
}
