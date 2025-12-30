package androidx.camera.camera2.internal;

import android.text.TextUtils;
import androidx.camera.camera2.internal.C20039w;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: R8$$SyntheticClass */
/* renamed from: androidx.camera.camera2.internal.s, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC20031s implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f23430b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ C20039w f23431c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ ArrayList f23432d;

    public /* synthetic */ RunnableC20031s(C20039w c20039w, ArrayList arrayList, int i12) {
        this.f23430b = i12;
        this.f23431c = c20039w;
        this.f23432d = arrayList;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f23430b) {
            case 0:
                ArrayList arrayList = this.f23432d;
                C20039w c20039w = this.f23431c;
                C20026p c20026p = c20039w.f23473i;
                try {
                    c20039w.H(arrayList);
                    return;
                } finally {
                    c20026p.n();
                }
            default:
                C20039w c20039w2 = this.f23431c;
                ArrayList arrayList2 = this.f23432d;
                c20039w2.getClass();
                ArrayList arrayList3 = new ArrayList();
                Iterator it = arrayList2.iterator();
                boolean z12 = false;
                while (it.hasNext()) {
                    C20039w.h hVar = (C20039w.h) it.next();
                    if (c20039w2.f23466b.f(hVar.d())) {
                        c20039w2.f23466b.f23990a.remove(hVar.d());
                        arrayList3.add(hVar.d());
                        if (hVar.e() == androidx.camera.core.v0.class) {
                            z12 = true;
                        }
                    }
                }
                if (arrayList3.isEmpty()) {
                    return;
                }
                c20039w2.s("Use cases [" + TextUtils.join(", ", arrayList3) + "] now DETACHED for camera");
                if (z12) {
                    c20039w2.f23473i.f23394h.f23444e = null;
                }
                c20039w2.p();
                if (c20039w2.f23466b.e().isEmpty()) {
                    c20039w2.f23473i.f23398l.f23315c = false;
                } else {
                    c20039w2.L();
                }
                if (!c20039w2.f23466b.d().isEmpty()) {
                    c20039w2.K();
                    c20039w2.D();
                    if (c20039w2.f23470f == C20039w.f.f23501e) {
                        c20039w2.A();
                        return;
                    }
                    return;
                }
                c20039w2.f23473i.n();
                c20039w2.D();
                c20039w2.f23473i.v(false);
                c20039w2.f23478n = c20039w2.y();
                c20039w2.s("Closing camera.");
                int iOrdinal = c20039w2.f23470f.ordinal();
                if (iOrdinal == 1) {
                    androidx.core.util.z.g(null, c20039w2.f23476l == null);
                    c20039w2.E(C20039w.f.f23498b);
                    return;
                }
                C20039w.f fVar = C20039w.f.f23503g;
                if (iOrdinal != 2) {
                    if (iOrdinal == 3 || iOrdinal == 4) {
                        c20039w2.E(fVar);
                        c20039w2.q();
                        return;
                    } else if (iOrdinal != 6) {
                        c20039w2.s("close() ignored due to being in state: " + c20039w2.f23470f);
                        return;
                    }
                }
                boolean zA2 = c20039w2.f23474j.a();
                c20039w2.E(fVar);
                if (zA2) {
                    androidx.core.util.z.g(null, c20039w2.x());
                    c20039w2.t();
                    return;
                }
                return;
        }
    }
}
