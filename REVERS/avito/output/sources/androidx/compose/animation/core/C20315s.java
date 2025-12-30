package androidx.compose.animation.core;

import androidx.compose.runtime.C22082i3;
import kotlin.Metadata;

/* compiled from: AnimationState.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0002\n\u0000¨\u0006\u0000"}, d2 = {"animation-core_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.core.s, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20315s {
    public static r a(float f12, float f13, int i12) {
        if ((i12 & 2) != 0) {
            f13 = 0.0f;
        }
        int i13 = kotlin.jvm.internal.A.f406820a;
        return new r(J1.f26075a, Float.valueOf(f12), new C20318t(f13), Long.MIN_VALUE, Long.MIN_VALUE, false);
    }

    public static r b(r rVar, float f12, float f13, int i12) {
        if ((i12 & 1) != 0) {
            f12 = ((Number) ((C22082i3) rVar.f26397c).getF42167b()).floatValue();
        }
        if ((i12 & 2) != 0) {
            f13 = ((C20318t) rVar.f26398d).f26403a;
        }
        return new r(rVar.f26396b, Float.valueOf(f12), new C20318t(f13), rVar.f26399e, rVar.f26400f, rVar.f26401g);
    }
}
