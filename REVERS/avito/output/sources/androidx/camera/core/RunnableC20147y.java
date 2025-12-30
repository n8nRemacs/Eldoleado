package androidx.camera.core;

import androidx.concurrent.futures.b;
import com.my.tracker.obfuscated.C37848w;
import java.util.Map;
import java.util.concurrent.Executor;

/* compiled from: R8$$SyntheticClass */
/* renamed from: androidx.camera.core.y, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC20147y implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f24584b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ long f24585c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Object f24586d;

    /* renamed from: e, reason: collision with root package name */
    public final /* synthetic */ Object f24587e;

    /* renamed from: f, reason: collision with root package name */
    public final /* synthetic */ Object f24588f;

    public /* synthetic */ RunnableC20147y(C20148z c20148z, Executor executor, long j12, b.a aVar) {
        this.f24584b = 0;
        this.f24586d = c20148z;
        this.f24587e = executor;
        this.f24585c = j12;
        this.f24588f = aVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        long j12 = this.f24585c;
        Object obj = this.f24588f;
        Object obj2 = this.f24587e;
        Object obj3 = this.f24586d;
        switch (this.f24584b) {
            case 0:
                C20148z c20148z = (C20148z) obj3;
                Executor executor = (Executor) obj2;
                executor.execute(new RunnableC20146x(c20148z, c20148z.f24606i, executor, (b.a) obj, this.f24585c));
                break;
            case 1:
                int i12 = com.avito.android.analytics.screens.image.f.f90619o;
                ((com.avito.android.analytics.screens.image.f) obj3).b(j12, (String) obj2, (String) obj);
                break;
            case 2:
                ((C37848w) obj3).a((String) obj2, (String) obj, j12);
                break;
            default:
                ((C37848w) obj3).a((Map) obj2, (String) obj, j12);
                break;
        }
    }

    public /* synthetic */ RunnableC20147y(Object obj, Object obj2, String str, long j12, int i12) {
        this.f24584b = i12;
        this.f24586d = obj;
        this.f24587e = obj2;
        this.f24588f = str;
        this.f24585c = j12;
    }
}
