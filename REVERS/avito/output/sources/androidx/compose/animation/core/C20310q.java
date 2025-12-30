package androidx.compose.animation.core;

import androidx.compose.animation.core.C20302n0;
import androidx.compose.runtime.F3;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: AnimationSpec.kt */
@Metadata(d1 = {"\u0000\u0002\n\u0000¨\u0006\u0000"}, d2 = {"animation-core_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.core.q, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20310q {
    public static C20278f0 a(M m12, RepeatMode repeatMode, long j12, int i12) {
        if ((i12 & 2) != 0) {
            repeatMode = RepeatMode.f26133b;
        }
        RepeatMode repeatMode2 = repeatMode;
        if ((i12 & 4) != 0) {
            j12 = U0.a(0);
        }
        return new C20278f0(m12, repeatMode2, j12, (C42822w) null);
    }

    @F3
    @Y61.k
    public static final <T> C20302n0<T> b(@Y61.k Y41.l<? super C20302n0.b<T>, kotlin.G0> lVar) {
        C20302n0.b bVar = new C20302n0.b();
        lVar.invoke(bVar);
        return new C20302n0<>(bVar);
    }

    public static M0 c() {
        return new M0(0);
    }

    public static R0 d(float f12, int i12, Object obj) {
        float f13 = (i12 & 1) != 0 ? 1.0f : 0.28f;
        if ((i12 & 2) != 0) {
            f12 = 1500.0f;
        }
        if ((i12 & 4) != 0) {
            obj = null;
        }
        return new R0(f13, f12, obj);
    }

    public static G1 e(int i12, int i13, N n12, int i14) {
        if ((i14 & 1) != 0) {
            i12 = 300;
        }
        if ((i14 & 2) != 0) {
            i13 = 0;
        }
        if ((i14 & 4) != 0) {
            n12 = Q.f26121a;
        }
        return new G1(i12, i13, n12);
    }
}
