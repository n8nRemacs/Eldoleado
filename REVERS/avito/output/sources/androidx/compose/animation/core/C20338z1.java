package androidx.compose.animation.core;

import androidx.compose.animation.core.C20288i1;
import androidx.compose.runtime.InterfaceC22118l0;
import kotlin.Metadata;

/* compiled from: Effects.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001¨\u0006\u0002¸\u0006\u0000"}, d2 = {"androidx/compose/runtime/m0", "Landroidx/compose/runtime/l0;", "runtime_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.core.z1, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20338z1 implements InterfaceC22118l0 {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ C20288i1 f26439a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ C20288i1.d f26440b;

    public C20338z1(C20288i1 c20288i1, C20288i1.d dVar) {
        this.f26439a = c20288i1;
        this.f26440b = dVar;
    }

    @Override // androidx.compose.runtime.InterfaceC22118l0
    public final void dispose() {
        this.f26439a.f26325i.remove(this.f26440b);
    }
}
