package androidx.appcompat.view;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.LayoutInflater;
import com.avito.android.R;
import j.f0;

/* compiled from: ContextThemeWrapper.java */
/* loaded from: classes.dex */
public class d extends ContextWrapper {

    /* renamed from: f, reason: collision with root package name */
    public static Configuration f21656f;

    /* renamed from: a, reason: collision with root package name */
    public int f21657a;

    /* renamed from: b, reason: collision with root package name */
    public Resources.Theme f21658b;

    /* renamed from: c, reason: collision with root package name */
    public LayoutInflater f21659c;

    /* renamed from: d, reason: collision with root package name */
    public Configuration f21660d;

    /* renamed from: e, reason: collision with root package name */
    public Resources f21661e;

    public d() {
        super(null);
    }

    public final void a(Configuration configuration) {
        if (this.f21661e != null) {
            throw new IllegalStateException("getResources() or getAssets() has already been called");
        }
        if (this.f21660d != null) {
            throw new IllegalStateException("Override configuration has already been set");
        }
        this.f21660d = new Configuration(configuration);
    }

    @Override // android.content.ContextWrapper
    public final void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    public final void b() {
        if (this.f21658b == null) {
            this.f21658b = getResources().newTheme();
            Resources.Theme theme = getBaseContext().getTheme();
            if (theme != null) {
                this.f21658b.setTo(theme);
            }
        }
        this.f21658b.applyStyle(this.f21657a, true);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final AssetManager getAssets() {
        return getResources().getAssets();
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x002c  */
    @Override // android.content.ContextWrapper, android.content.Context
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public android.content.res.Resources getResources() {
        /*
            r3 = this;
            android.content.res.Resources r0 = r3.f21661e
            if (r0 != 0) goto L32
            android.content.res.Configuration r0 = r3.f21660d
            if (r0 == 0) goto L2c
            android.content.res.Configuration r1 = androidx.appcompat.view.d.f21656f
            if (r1 != 0) goto L16
            android.content.res.Configuration r1 = new android.content.res.Configuration
            r1.<init>()
            r2 = 0
            r1.fontScale = r2
            androidx.appcompat.view.d.f21656f = r1
        L16:
            android.content.res.Configuration r1 = androidx.appcompat.view.d.f21656f
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L1f
            goto L2c
        L1f:
            android.content.res.Configuration r0 = r3.f21660d
            android.content.Context r0 = r3.createConfigurationContext(r0)
            android.content.res.Resources r0 = r0.getResources()
            r3.f21661e = r0
            goto L32
        L2c:
            android.content.res.Resources r0 = super.getResources()
            r3.f21661e = r0
        L32:
            android.content.res.Resources r0 = r3.f21661e
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.view.d.getResources():android.content.res.Resources");
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final Object getSystemService(String str) {
        if (!"layout_inflater".equals(str)) {
            return getBaseContext().getSystemService(str);
        }
        if (this.f21659c == null) {
            this.f21659c = LayoutInflater.from(getBaseContext()).cloneInContext(this);
        }
        return this.f21659c;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final Resources.Theme getTheme() {
        Resources.Theme theme = this.f21658b;
        if (theme != null) {
            return theme;
        }
        if (this.f21657a == 0) {
            this.f21657a = R.style.Theme_AppCompat_Light;
        }
        b();
        return this.f21658b;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final void setTheme(int i12) {
        if (this.f21657a != i12) {
            this.f21657a = i12;
            b();
        }
    }

    public d(Context context, @f0 int i12) {
        super(context);
        this.f21657a = i12;
    }
}
