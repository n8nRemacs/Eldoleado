package androidx.browser.customtabs;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.customtabs.a;
import com.vk.id.internal.auth.AuthActivity$callback$1;
import j.N;
import j.P;

/* compiled from: CustomTabsClient.java */
/* loaded from: classes.dex */
class e extends a.b {

    /* renamed from: a, reason: collision with root package name */
    public final Handler f22827a = new Handler(Looper.getMainLooper());

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ AuthActivity$callback$1 f22828b;

    /* compiled from: CustomTabsClient.java */
    public class a implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ Bundle f22829b;

        public a(Bundle bundle) {
            this.f22829b = bundle;
        }

        @Override // java.lang.Runnable
        public final void run() {
            e.this.f22828b.onUnminimized(this.f22829b);
        }
    }

    /* compiled from: CustomTabsClient.java */
    public class b implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ int f22831b;

        /* renamed from: c, reason: collision with root package name */
        public final /* synthetic */ Bundle f22832c;

        public b(int i12, Bundle bundle) {
            this.f22831b = i12;
            this.f22832c = bundle;
        }

        @Override // java.lang.Runnable
        public final void run() {
            e.this.f22828b.onNavigationEvent(this.f22831b, this.f22832c);
        }
    }

    /* compiled from: CustomTabsClient.java */
    public class c implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ String f22834b;

        /* renamed from: c, reason: collision with root package name */
        public final /* synthetic */ Bundle f22835c;

        public c(String str, Bundle bundle) {
            this.f22834b = str;
            this.f22835c = bundle;
        }

        @Override // java.lang.Runnable
        public final void run() {
            e.this.f22828b.extraCallback(this.f22834b, this.f22835c);
        }
    }

    /* compiled from: CustomTabsClient.java */
    public class d implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ Bundle f22837b;

        public d(Bundle bundle) {
            this.f22837b = bundle;
        }

        @Override // java.lang.Runnable
        public final void run() {
            e.this.f22828b.onMessageChannelReady(this.f22837b);
        }
    }

    /* compiled from: CustomTabsClient.java */
    /* renamed from: androidx.browser.customtabs.e$e, reason: collision with other inner class name */
    public class RunnableC1525e implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ String f22839b;

        /* renamed from: c, reason: collision with root package name */
        public final /* synthetic */ Bundle f22840c;

        public RunnableC1525e(String str, Bundle bundle) {
            this.f22839b = str;
            this.f22840c = bundle;
        }

        @Override // java.lang.Runnable
        public final void run() {
            e.this.f22828b.onPostMessage(this.f22839b, this.f22840c);
        }
    }

    /* compiled from: CustomTabsClient.java */
    public class f implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ int f22842b;

        /* renamed from: c, reason: collision with root package name */
        public final /* synthetic */ Uri f22843c;

        /* renamed from: d, reason: collision with root package name */
        public final /* synthetic */ boolean f22844d;

        /* renamed from: e, reason: collision with root package name */
        public final /* synthetic */ Bundle f22845e;

        public f(int i12, Uri uri, boolean z12, Bundle bundle) {
            this.f22842b = i12;
            this.f22843c = uri;
            this.f22844d = z12;
            this.f22845e = bundle;
        }

        @Override // java.lang.Runnable
        public final void run() {
            e.this.f22828b.onRelationshipValidationResult(this.f22842b, this.f22843c, this.f22844d, this.f22845e);
        }
    }

    /* compiled from: CustomTabsClient.java */
    public class g implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ int f22847b;

        /* renamed from: c, reason: collision with root package name */
        public final /* synthetic */ int f22848c;

        /* renamed from: d, reason: collision with root package name */
        public final /* synthetic */ Bundle f22849d;

        public g(int i12, int i13, Bundle bundle) {
            this.f22847b = i12;
            this.f22848c = i13;
            this.f22849d = bundle;
        }

        @Override // java.lang.Runnable
        public final void run() {
            e.this.f22828b.onActivityResized(this.f22847b, this.f22848c, this.f22849d);
        }
    }

    /* compiled from: CustomTabsClient.java */
    public class h implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ Bundle f22851b;

        public h(Bundle bundle) {
            this.f22851b = bundle;
        }

        @Override // java.lang.Runnable
        public final void run() {
            e.this.f22828b.onWarmupCompleted(this.f22851b);
        }
    }

    /* compiled from: CustomTabsClient.java */
    public class i implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ int f22853b;

        /* renamed from: c, reason: collision with root package name */
        public final /* synthetic */ int f22854c;

        /* renamed from: d, reason: collision with root package name */
        public final /* synthetic */ int f22855d;

        /* renamed from: e, reason: collision with root package name */
        public final /* synthetic */ int f22856e;

        /* renamed from: f, reason: collision with root package name */
        public final /* synthetic */ int f22857f;

        /* renamed from: g, reason: collision with root package name */
        public final /* synthetic */ Bundle f22858g;

        public i(int i12, int i13, int i14, int i15, int i16, Bundle bundle) {
            this.f22853b = i12;
            this.f22854c = i13;
            this.f22855d = i14;
            this.f22856e = i15;
            this.f22857f = i16;
            this.f22858g = bundle;
        }

        @Override // java.lang.Runnable
        public final void run() {
            e.this.f22828b.onActivityLayout(this.f22853b, this.f22854c, this.f22855d, this.f22856e, this.f22857f, this.f22858g);
        }
    }

    /* compiled from: CustomTabsClient.java */
    public class j implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ Bundle f22860b;

        public j(Bundle bundle) {
            this.f22860b = bundle;
        }

        @Override // java.lang.Runnable
        public final void run() {
            e.this.f22828b.onMinimized(this.f22860b);
        }
    }

    public e(AuthActivity$callback$1 authActivity$callback$1) {
        this.f22828b = authActivity$callback$1;
    }

    @Override // android.support.customtabs.a
    public final Bundle C(@N String str, @P Bundle bundle) {
        AuthActivity$callback$1 authActivity$callback$1 = this.f22828b;
        if (authActivity$callback$1 == null) {
            return null;
        }
        return authActivity$callback$1.extraCallbackWithResult(str, bundle);
    }

    @Override // android.support.customtabs.a
    public final void C1(@N Bundle bundle) {
        if (this.f22828b == null) {
            return;
        }
        this.f22827a.post(new j(bundle));
    }

    @Override // android.support.customtabs.a
    public final void F1(@N Bundle bundle) {
        if (this.f22828b == null) {
            return;
        }
        this.f22827a.post(new a(bundle));
    }

    @Override // android.support.customtabs.a
    public final void G2(int i12, int i13, @P Bundle bundle) {
        if (this.f22828b == null) {
            return;
        }
        this.f22827a.post(new g(i12, i13, bundle));
    }

    @Override // android.support.customtabs.a
    public final void K1(String str, Bundle bundle) {
        if (this.f22828b == null) {
            return;
        }
        this.f22827a.post(new RunnableC1525e(str, bundle));
    }

    @Override // android.support.customtabs.a
    public final void L1(Bundle bundle) {
        if (this.f22828b == null) {
            return;
        }
        this.f22827a.post(new d(bundle));
    }

    @Override // android.support.customtabs.a
    public final void N2(int i12, Bundle bundle) {
        if (this.f22828b == null) {
            return;
        }
        this.f22827a.post(new b(i12, bundle));
    }

    @Override // android.support.customtabs.a
    public final void W1(int i12, int i13, int i14, int i15, int i16, @N Bundle bundle) {
        if (this.f22828b == null) {
            return;
        }
        this.f22827a.post(new i(i12, i13, i14, i15, i16, bundle));
    }

    @Override // android.support.customtabs.a
    public final void c4(int i12, Uri uri, boolean z12, @P Bundle bundle) {
        if (this.f22828b == null) {
            return;
        }
        this.f22827a.post(new f(i12, uri, z12, bundle));
    }

    @Override // android.support.customtabs.a
    public final void f1(String str, Bundle bundle) {
        if (this.f22828b == null) {
            return;
        }
        this.f22827a.post(new c(str, bundle));
    }

    @Override // android.support.customtabs.a
    public final void p3(@N Bundle bundle) {
        if (this.f22828b == null) {
            return;
        }
        this.f22827a.post(new h(bundle));
    }
}
