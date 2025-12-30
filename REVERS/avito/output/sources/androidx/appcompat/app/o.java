package androidx.appcompat.app;

import android.content.Context;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class o implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f21557b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Context f21558c;

    public /* synthetic */ o(Context context, int i12) {
        this.f21557b = i12;
        this.f21558c = context;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0043  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void run() {
        /*
            r6 = this;
            android.content.Context r0 = r6.f21558c
            int r1 = r6.f21557b
            switch(r1) {
                case 0: goto Lb;
                default: goto L7;
            }
        L7:
            androidx.appcompat.app.p.I(r0)
            return
        Lb:
            int r1 = android.os.Build.VERSION.SDK_INT
            r2 = 1
            r3 = 33
            if (r1 < r3) goto L5d
            androidx.appcompat.app.p$d r4 = androidx.appcompat.app.p.f21559b
            android.content.ComponentName r4 = new android.content.ComponentName
            java.lang.String r5 = "androidx.appcompat.app.AppLocalesMetadataHolderService"
            r4.<init>(r0, r5)
            android.content.pm.PackageManager r5 = r0.getPackageManager()
            int r5 = r5.getComponentEnabledSetting(r4)
            if (r5 == r2) goto L5d
            if (r1 < r3) goto L36
            java.lang.Object r1 = androidx.appcompat.app.p.k()
            if (r1 == 0) goto L3b
            android.os.LocaleList r1 = androidx.appcompat.app.p.b.a(r1)
            androidx.core.os.q r1 = androidx.core.os.q.g(r1)
            goto L3d
        L36:
            androidx.core.os.q r1 = androidx.appcompat.app.p.f21561d
            if (r1 == 0) goto L3b
            goto L3d
        L3b:
            androidx.core.os.q r1 = androidx.core.os.q.f44804b
        L3d:
            boolean r1 = r1.c()
            if (r1 == 0) goto L56
            java.lang.String r1 = androidx.core.app.C22754l.b(r0)
            java.lang.String r3 = "locale"
            java.lang.Object r3 = r0.getSystemService(r3)
            if (r3 == 0) goto L56
            android.os.LocaleList r1 = androidx.appcompat.app.p.a.a(r1)
            androidx.appcompat.app.p.b.b(r3, r1)
        L56:
            android.content.pm.PackageManager r0 = r0.getPackageManager()
            r0.setComponentEnabledSetting(r4, r2, r2)
        L5d:
            androidx.appcompat.app.p.f21564g = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.o.run():void");
    }
}
