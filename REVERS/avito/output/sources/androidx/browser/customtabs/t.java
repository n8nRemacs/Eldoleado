package androidx.browser.customtabs;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.customtabs.d;
import j.N;
import j.P;

/* compiled from: PostMessageService.java */
/* loaded from: classes.dex */
public class t extends Service {

    /* renamed from: b, reason: collision with root package name */
    public final d.b f22883b = new a();

    /* compiled from: PostMessageService.java */
    public class a extends d.b {
        @Override // android.support.customtabs.d
        public final void G(@N android.support.customtabs.a aVar, @P Bundle bundle) {
            aVar.L1(bundle);
        }

        @Override // android.support.customtabs.d
        public final void P3(@N android.support.customtabs.a aVar, @N String str, @P Bundle bundle) {
            aVar.K1(str, bundle);
        }
    }

    @Override // android.app.Service
    @N
    public final IBinder onBind(@P Intent intent) {
        return this.f22883b;
    }
}
