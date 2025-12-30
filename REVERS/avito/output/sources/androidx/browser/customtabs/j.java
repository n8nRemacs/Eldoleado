package androidx.browser.customtabs;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.customtabs.b;
import android.support.customtabs.c;
import androidx.annotation.RestrictTo;
import androidx.browser.customtabs.j;
import androidx.collection.o1;
import j.N;
import j.P;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/* compiled from: CustomTabsService.java */
/* loaded from: classes.dex */
public abstract class j extends Service {

    /* renamed from: b, reason: collision with root package name */
    public final o1<IBinder, IBinder.DeathRecipient> f22874b = new o1<>();

    /* renamed from: c, reason: collision with root package name */
    public final b.AbstractBinderC1508b f22875c = new a();

    /* compiled from: CustomTabsService.java */
    public class a extends b.AbstractBinderC1508b {
        public a() {
        }

        @P
        public static PendingIntent f4(@P Bundle bundle) {
            if (bundle == null) {
                return null;
            }
            PendingIntent pendingIntent = (PendingIntent) bundle.getParcelable("android.support.customtabs.extra.SESSION_ID");
            bundle.remove("android.support.customtabs.extra.SESSION_ID");
            return pendingIntent;
        }

        @Override // android.support.customtabs.b
        public final boolean B2(long j12) {
            return j.this.i();
        }

        @Override // android.support.customtabs.b
        public final boolean I(int i12, @N Uri uri, @P Bundle bundle, @N android.support.customtabs.a aVar) {
            new o(aVar, f4(bundle));
            return j.this.e();
        }

        @Override // android.support.customtabs.b
        public final boolean J2(@N android.support.customtabs.a aVar) {
            return g4(aVar, null);
        }

        @Override // android.support.customtabs.b
        public final boolean L2(@P android.support.customtabs.a aVar, @P Uri uri, @P Bundle bundle, @P ArrayList arrayList) {
            new o(aVar, f4(bundle));
            return j.this.b();
        }

        @Override // android.support.customtabs.b
        public final Bundle N(@P Bundle bundle, @N String str) {
            return j.this.a();
        }

        @Override // android.support.customtabs.b
        public final boolean S0(int i12, @N Uri uri, @P Bundle bundle, @N android.support.customtabs.a aVar) {
            new o(aVar, f4(bundle));
            return j.this.h();
        }

        @Override // android.support.customtabs.b
        public final boolean U(@N android.support.customtabs.a aVar, @P Bundle bundle) {
            new o(aVar, f4(bundle));
            return j.this.g();
        }

        @Override // android.support.customtabs.b
        public final boolean Y3(@N android.support.customtabs.a aVar, @N Uri uri) {
            new o(aVar, null);
            new Bundle();
            return j.this.f();
        }

        @Override // android.support.customtabs.b
        public final void d4(android.support.customtabs.a aVar, @N Bundle bundle) {
            new o(aVar, f4(bundle));
        }

        @Override // android.support.customtabs.b
        public final void g2(@N android.support.customtabs.a aVar, @N IBinder iBinder, @N Bundle bundle) {
            c.b.e4(iBinder);
            new o(aVar, f4(bundle));
        }

        public final boolean g4(@N android.support.customtabs.a aVar, @P PendingIntent pendingIntent) {
            final o oVar = new o(aVar, pendingIntent);
            try {
                IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() { // from class: androidx.browser.customtabs.i
                    @Override // android.os.IBinder.DeathRecipient
                    public final void binderDied() {
                        j.a aVar2 = this.f22872a;
                        o oVar2 = oVar;
                        j jVar = j.this;
                        jVar.getClass();
                        try {
                            synchronized (jVar.f22874b) {
                                try {
                                    android.support.customtabs.a aVar3 = oVar2.f22880a;
                                    IBinder iBinderAsBinder = aVar3 == null ? null : aVar3.asBinder();
                                    if (iBinderAsBinder == null) {
                                        return;
                                    }
                                    iBinderAsBinder.unlinkToDeath(jVar.f22874b.get(iBinderAsBinder), 0);
                                    jVar.f22874b.remove(iBinderAsBinder);
                                } finally {
                                }
                            }
                        } catch (NoSuchElementException unused) {
                        }
                    }
                };
                synchronized (j.this.f22874b) {
                    aVar.asBinder().linkToDeath(deathRecipient, 0);
                    j.this.f22874b.put(aVar.asBinder(), deathRecipient);
                }
                return j.this.c();
            } catch (RemoteException unused) {
                return false;
            }
        }

        @Override // android.support.customtabs.b
        public final boolean k3(@N android.support.customtabs.a aVar, @N Uri uri, @N Bundle bundle) {
            new o(aVar, f4(bundle));
            if (bundle != null) {
                if (Build.VERSION.SDK_INT >= 33) {
                }
            }
            return j.this.f();
        }

        @Override // android.support.customtabs.b
        public final boolean l3(@N android.support.customtabs.a aVar, @P Bundle bundle) {
            return g4(aVar, f4(bundle));
        }

        @Override // android.support.customtabs.b
        public final int z0(@N android.support.customtabs.a aVar, @N String str, @P Bundle bundle) {
            new o(aVar, f4(bundle));
            return j.this.d();
        }
    }

    /* compiled from: CustomTabsService.java */
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface b {
    }

    /* compiled from: CustomTabsService.java */
    @Retention(RetentionPolicy.SOURCE)
    public @interface c {
    }

    /* compiled from: CustomTabsService.java */
    @Retention(RetentionPolicy.SOURCE)
    public @interface d {
    }

    @P
    public abstract Bundle a();

    public abstract boolean b();

    public abstract boolean c();

    public abstract int d();

    public abstract boolean e();

    public abstract boolean f();

    public abstract boolean g();

    public abstract boolean h();

    public abstract boolean i();

    @Override // android.app.Service
    @N
    public final IBinder onBind(@P Intent intent) {
        return this.f22875c;
    }
}
