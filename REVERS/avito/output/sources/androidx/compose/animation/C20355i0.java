package androidx.compose.animation;

import androidx.compose.animation.core.C20310q;
import androidx.compose.ui.InterfaceC22215f;
import androidx.compose.ui.unit.u;
import com.avito.android.beduin.common.component.input.BeduinInputModel;
import kotlin.Metadata;

/* compiled from: AnimationModifier.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0002\n\u0000¨\u0006\u0000"}, d2 = {"animation_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.i0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20355i0 {

    /* renamed from: a, reason: collision with root package name */
    public static final long f26469a;

    static {
        long j12 = BeduinInputModel.MIN_TEXT_VERSION;
        u.a aVar = androidx.compose.ui.unit.u.f42871b;
        f26469a = (j12 & 4294967295L) | (j12 << 32);
    }

    public static androidx.compose.ui.v a(androidx.compose.ui.v vVar) {
        u.a aVar = androidx.compose.ui.unit.u.f42871b;
        androidx.compose.animation.core.R0 r0D = C20310q.d(400.0f, 1, androidx.compose.ui.unit.u.a(androidx.compose.animation.core.b2.b()));
        androidx.compose.ui.v vVarB = androidx.compose.ui.draw.k.b(vVar);
        InterfaceC22215f.f39074a.getClass();
        return vVarB.d0(new SizeAnimationModifierElement(r0D, InterfaceC22215f.a.f39076b));
    }

    public static final boolean b(long j12) {
        return !androidx.compose.ui.unit.u.c(j12, f26469a);
    }
}
