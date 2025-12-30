package androidx.browser.customtabs;

import android.app.PendingIntent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.customtabs.a;
import j.N;
import j.P;

/* compiled from: CustomTabsSessionToken.java */
/* loaded from: classes.dex */
public class o {

    /* renamed from: a, reason: collision with root package name */
    @P
    public final android.support.customtabs.a f22880a;

    /* renamed from: b, reason: collision with root package name */
    @P
    public final PendingIntent f22881b;

    /* compiled from: CustomTabsSessionToken.java */
    public class a extends c {
        public a() {
        }

        @Override // androidx.browser.customtabs.c
        public final void extraCallback(@N String str, @P Bundle bundle) {
            try {
                o.this.f22880a.f1(str, bundle);
            } catch (RemoteException unused) {
            }
        }

        @Override // androidx.browser.customtabs.c
        @N
        public final Bundle extraCallbackWithResult(@N String str, @P Bundle bundle) {
            try {
                return o.this.f22880a.C(str, bundle);
            } catch (RemoteException unused) {
                return null;
            }
        }

        @Override // androidx.browser.customtabs.c
        public final void onActivityLayout(int i12, int i13, int i14, int i15, int i16, @N Bundle bundle) {
            try {
                o.this.f22880a.W1(i12, i13, i14, i15, i16, bundle);
            } catch (RemoteException unused) {
            }
        }

        @Override // androidx.browser.customtabs.c
        public final void onActivityResized(int i12, int i13, @N Bundle bundle) {
            try {
                o.this.f22880a.G2(i12, i13, bundle);
            } catch (RemoteException unused) {
            }
        }

        @Override // androidx.browser.customtabs.c
        public final void onMessageChannelReady(@P Bundle bundle) {
            try {
                o.this.f22880a.L1(bundle);
            } catch (RemoteException unused) {
            }
        }

        @Override // androidx.browser.customtabs.c
        public final void onMinimized(@N Bundle bundle) {
            try {
                o.this.f22880a.C1(bundle);
            } catch (RemoteException unused) {
            }
        }

        @Override // androidx.browser.customtabs.c
        public final void onPostMessage(@N String str, @P Bundle bundle) {
            try {
                o.this.f22880a.K1(str, bundle);
            } catch (RemoteException unused) {
            }
        }

        @Override // androidx.browser.customtabs.c
        public final void onRelationshipValidationResult(int i12, @N Uri uri, boolean z12, @P Bundle bundle) {
            try {
                o.this.f22880a.c4(i12, uri, z12, bundle);
            } catch (RemoteException unused) {
            }
        }

        @Override // androidx.browser.customtabs.c
        public final void onUnminimized(@N Bundle bundle) {
            try {
                o.this.f22880a.F1(bundle);
            } catch (RemoteException unused) {
            }
        }

        @Override // androidx.browser.customtabs.c
        public final void onWarmupCompleted(@N Bundle bundle) {
            try {
                o.this.f22880a.p3(bundle);
            } catch (RemoteException unused) {
            }
        }
    }

    public o(@P android.support.customtabs.a aVar, @P PendingIntent pendingIntent) {
        if (aVar == null && pendingIntent == null) {
            throw new IllegalStateException("CustomTabsSessionToken must have either a session id or a callback (or both).");
        }
        this.f22880a = aVar;
        this.f22881b = pendingIntent;
        if (aVar == null) {
            return;
        }
        new a();
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof o)) {
            return false;
        }
        o oVar = (o) obj;
        PendingIntent pendingIntent = oVar.f22881b;
        PendingIntent pendingIntent2 = this.f22881b;
        if ((pendingIntent2 == null) != (pendingIntent == null)) {
            return false;
        }
        if (pendingIntent2 != null) {
            return pendingIntent2.equals(pendingIntent);
        }
        android.support.customtabs.a aVar = this.f22880a;
        if (aVar == null) {
            throw new IllegalStateException("CustomTabSessionToken must have valid binder or pending session");
        }
        IBinder iBinderAsBinder = aVar.asBinder();
        android.support.customtabs.a aVar2 = oVar.f22880a;
        if (aVar2 != null) {
            return iBinderAsBinder.equals(aVar2.asBinder());
        }
        throw new IllegalStateException("CustomTabSessionToken must have valid binder or pending session");
    }

    public final int hashCode() {
        PendingIntent pendingIntent = this.f22881b;
        if (pendingIntent != null) {
            return pendingIntent.hashCode();
        }
        android.support.customtabs.a aVar = this.f22880a;
        if (aVar != null) {
            return aVar.asBinder().hashCode();
        }
        throw new IllegalStateException("CustomTabSessionToken must have valid binder or pending session");
    }

    /* compiled from: CustomTabsSessionToken.java */
    public static class b extends a.b {
        @Override // android.support.customtabs.a
        public final Bundle C(String str, Bundle bundle) {
            return null;
        }

        @Override // android.support.customtabs.a.b, android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }

        @Override // android.support.customtabs.a
        public final void C1(@N Bundle bundle) {
        }

        @Override // android.support.customtabs.a
        public final void F1(@N Bundle bundle) {
        }

        @Override // android.support.customtabs.a
        public final void L1(Bundle bundle) {
        }

        @Override // android.support.customtabs.a
        public final void p3(Bundle bundle) {
        }

        @Override // android.support.customtabs.a
        public final void K1(String str, Bundle bundle) {
        }

        @Override // android.support.customtabs.a
        public final void N2(int i12, Bundle bundle) {
        }

        @Override // android.support.customtabs.a
        public final void f1(String str, Bundle bundle) {
        }

        @Override // android.support.customtabs.a
        public final void G2(int i12, int i13, Bundle bundle) {
        }

        @Override // android.support.customtabs.a
        public final void c4(int i12, Uri uri, boolean z12, Bundle bundle) {
        }

        @Override // android.support.customtabs.a
        public final void W1(int i12, int i13, int i14, int i15, int i16, @N Bundle bundle) {
        }
    }
}
