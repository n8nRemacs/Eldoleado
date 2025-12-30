package androidx.browser.customtabs;

import android.content.ComponentName;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.customtabs.b;
import androidx.annotation.RestrictTo;
import java.util.ArrayList;

/* compiled from: CustomTabsSession.java */
/* loaded from: classes.dex */
public final class n {

    /* renamed from: a, reason: collision with root package name */
    public final android.support.customtabs.b f22877a;

    /* renamed from: b, reason: collision with root package name */
    public final android.support.customtabs.a f22878b;

    /* renamed from: c, reason: collision with root package name */
    public final ComponentName f22879c;

    /* compiled from: CustomTabsSession.java */
    @RestrictTo
    public static class b {
    }

    public n(android.support.customtabs.b bVar, android.support.customtabs.a aVar, ComponentName componentName) {
        this.f22877a = bVar;
        this.f22878b = aVar;
        this.f22879c = componentName;
    }

    /* compiled from: CustomTabsSession.java */
    public static class a extends b.AbstractBinderC1508b {
        @Override // android.support.customtabs.b
        public final boolean B2(long j12) {
            return false;
        }

        @Override // android.support.customtabs.b
        public final boolean I(int i12, Uri uri, Bundle bundle, android.support.customtabs.a aVar) {
            return false;
        }

        @Override // android.support.customtabs.b
        public final boolean J2(android.support.customtabs.a aVar) {
            return false;
        }

        @Override // android.support.customtabs.b
        public final boolean L2(android.support.customtabs.a aVar, Uri uri, Bundle bundle, ArrayList arrayList) {
            return false;
        }

        @Override // android.support.customtabs.b
        public final Bundle N(Bundle bundle, String str) {
            return null;
        }

        @Override // android.support.customtabs.b
        public final boolean S0(int i12, Uri uri, Bundle bundle, android.support.customtabs.a aVar) {
            return false;
        }

        @Override // android.support.customtabs.b
        public final boolean U(android.support.customtabs.a aVar, Bundle bundle) {
            return false;
        }

        @Override // android.support.customtabs.b
        public final boolean Y3(android.support.customtabs.a aVar, Uri uri) {
            return false;
        }

        @Override // android.support.customtabs.b
        public final boolean k3(android.support.customtabs.a aVar, Uri uri, Bundle bundle) {
            return false;
        }

        @Override // android.support.customtabs.b
        public final boolean l3(android.support.customtabs.a aVar, Bundle bundle) {
            return false;
        }

        @Override // android.support.customtabs.b
        public final int z0(android.support.customtabs.a aVar, String str, Bundle bundle) {
            return 0;
        }

        @Override // android.support.customtabs.b
        public final void d4(android.support.customtabs.a aVar, Bundle bundle) {
        }

        @Override // android.support.customtabs.b
        public final void g2(android.support.customtabs.a aVar, IBinder iBinder, Bundle bundle) {
        }
    }
}
