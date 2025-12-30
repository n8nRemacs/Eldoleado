package androidx.appcompat.app;

import android.content.Context;
import android.location.LocationManager;
import j.N;
import j.k0;

/* compiled from: TwilightManager.java */
/* loaded from: classes.dex */
class I {

    /* renamed from: d, reason: collision with root package name */
    public static I f21504d;

    /* renamed from: a, reason: collision with root package name */
    public final Context f21505a;

    /* renamed from: b, reason: collision with root package name */
    public final LocationManager f21506b;

    /* renamed from: c, reason: collision with root package name */
    public final a f21507c = new a();

    /* compiled from: TwilightManager.java */
    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public boolean f21508a;

        /* renamed from: b, reason: collision with root package name */
        public long f21509b;
    }

    @k0
    public I(@N Context context, @N LocationManager locationManager) {
        this.f21505a = context;
        this.f21506b = locationManager;
    }
}
