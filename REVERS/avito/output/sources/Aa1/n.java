package aa1;

import Ba1.C13123a;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import androidx.camera.core.T;
import androidx.camera.core.z0;
import da1.C39690b;
import kotlin.G0;
import kotlin.Z;
import kotlin.jvm.internal.l0;
import okhttp3.internal.http2.Http2Connection;
import wa1.C49586a;
import wa1.C49587b;
import wa1.u;
import xa1.AbstractC49913g;
import xa1.C49914h;
import ya1.C50207c;

/* loaded from: classes9.dex */
public final class n implements T.a {

    /* renamed from: b, reason: collision with root package name */
    public final C39690b f20957b;

    /* renamed from: c, reason: collision with root package name */
    public final C50207c f20958c;

    /* renamed from: d, reason: collision with root package name */
    public final t f20959d;

    /* renamed from: e, reason: collision with root package name */
    public final wa1.p f20960e;

    /* renamed from: f, reason: collision with root package name */
    public final C49587b f20961f;

    /* renamed from: g, reason: collision with root package name */
    public final C49586a f20962g;

    /* renamed from: h, reason: collision with root package name */
    public final C49914h f20963h;

    /* renamed from: i, reason: collision with root package name */
    public final C13123a f20964i;

    /* renamed from: j, reason: collision with root package name */
    public boolean f20965j;

    /* renamed from: k, reason: collision with root package name */
    public Rect f20966k;

    public n(Context context, C39690b c39690b) {
        this.f20957b = c39690b;
        Rect rectA = H91.b.a(context);
        u uVar = new u(rectA.width(), rectA.height());
        C50207c c50207c = new C50207c(context);
        this.f20958c = c50207c;
        this.f20959d = new t(context);
        this.f20960e = new wa1.p(context, c50207c, uVar);
        this.f20961f = new C49587b();
        this.f20962g = new C49586a(uVar, c50207c);
        this.f20963h = new C49914h();
        this.f20964i = new C13123a();
        this.f20965j = true;
    }

    /* JADX WARN: Type inference failed for: r15v10, types: [T, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v2, types: [T, android.graphics.Bitmap] */
    public final void a(z0 z0Var) throws Exception {
        C13123a c13123a = this.f20964i;
        long jNanoTime = System.nanoTime();
        l0.h hVar = new l0.h();
        try {
            ?? r42 = (Bitmap) c13123a.a("Transform frame", new h(this, z0Var));
            V41.b.a(z0Var, null);
            hVar.f406842b = r42;
            Rect rect = this.f20966k;
            if (rect != null) {
                hVar.f406842b = c13123a.a("Crop by photo frame", new g(this, hVar, rect));
            }
            boolean zBooleanValue = ((Boolean) c13123a.a("Brightness", new k(this, hVar))).booleanValue();
            s sVar = (s) c13123a.a("Passport detect", new l(this, hVar));
            boolean zBooleanValue2 = ((Boolean) c13123a.a("Flare detect", new i(sVar, this, hVar))).booleanValue();
            boolean zBooleanValue3 = ((Boolean) c13123a.a("Blurry detect", new j(sVar, this, hVar))).booleanValue();
            AbstractC49913g abstractC49913g = (AbstractC49913g) c13123a.a("Tilt detect", new m(sVar, this));
            pc1.b.f428696a.b(androidx.media3.exoplayer.analytics.m.i(1 / ((System.nanoTime() - jNanoTime) / Http2Connection.DEGRADED_PONG_TIMEOUT_NS), "Full FPS: "), new Object[0]);
            this.f20957b.invoke(new f(sVar.f20985a, sVar.f20986b, sVar.f20987c, sVar.f20989e, zBooleanValue3, sVar.f20988d, zBooleanValue2, zBooleanValue, abstractC49913g));
        } finally {
        }
    }

    @Override // androidx.camera.core.T.a
    public final void d(z0 z0Var) {
        if (!this.f20965j || z0Var.f24620i == 0 || z0Var.f24619h == 0) {
            z0Var.close();
            return;
        }
        try {
            int i12 = Z.f406624c;
            a(z0Var);
            G0 g02 = G0.f406611a;
        } catch (Throwable unused) {
            int i13 = Z.f406624c;
        }
    }
}
