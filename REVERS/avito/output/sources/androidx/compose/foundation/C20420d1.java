package androidx.compose.foundation;

import androidx.compose.foundation.C20409a1;
import androidx.compose.foundation.interaction.d;
import androidx.compose.ui.layout.I0;
import androidx.compose.ui.node.AbstractC22443w0;
import androidx.compose.ui.node.C22421l;
import kotlin.Metadata;
import kotlin.jvm.internal.l0;
import kotlinx.coroutines.C43259k;

/* compiled from: Focusable.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.foundation.d1, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final /* synthetic */ class C20420d1 extends kotlin.jvm.internal.H implements Y41.p<androidx.compose.ui.focus.f0, androidx.compose.ui.focus.f0, kotlin.G0> {
    public final void f(androidx.compose.ui.focus.f0 f0Var, androidx.compose.ui.focus.f0 f0Var2) {
        boolean zA2;
        C20527h1 c20527h1O2;
        C20409a1 c20409a1 = (C20409a1) this.receiver;
        C20409a1.a aVar = C20409a1.f26937y;
        if (c20409a1.f42893o && (zA2 = f0Var2.a()) != f0Var.a()) {
            Y41.l<Boolean, kotlin.G0> lVar = c20409a1.f26939s;
            if (lVar != null) {
                lVar.invoke(Boolean.valueOf(zA2));
            }
            if (zA2) {
                C43259k.d(c20409a1.Y1(), null, null, new C20423e1(c20409a1, null), 3);
                l0.h hVar = new l0.h();
                androidx.compose.ui.node.E0.a(c20409a1, new C20426f1(hVar, c20409a1));
                androidx.compose.ui.layout.I0 i02 = (androidx.compose.ui.layout.I0) hVar.f406842b;
                c20409a1.f26941u = i02 != null ? i02.a() : null;
                AbstractC22443w0 abstractC22443w0 = c20409a1.f26942v;
                if (abstractC22443w0 != null && abstractC22443w0.j1().f42893o && (c20527h1O2 = c20409a1.o2()) != null) {
                    c20527h1O2.k2(c20409a1.f26942v);
                }
            } else {
                I0.a aVar2 = c20409a1.f26941u;
                if (aVar2 != null) {
                    aVar2.release();
                }
                c20409a1.f26941u = null;
                C20527h1 c20527h1O22 = c20409a1.o2();
                if (c20527h1O22 != null) {
                    c20527h1O22.k2(null);
                }
            }
            C22421l.g(c20409a1).Z();
            androidx.compose.foundation.interaction.m mVar = c20409a1.f26938r;
            if (mVar != null) {
                if (!zA2) {
                    d.a aVar3 = c20409a1.f26940t;
                    if (aVar3 != null) {
                        c20409a1.n2(mVar, new d.b(aVar3));
                        c20409a1.f26940t = null;
                        return;
                    }
                    return;
                }
                d.a aVar4 = c20409a1.f26940t;
                if (aVar4 != null) {
                    c20409a1.n2(mVar, new d.b(aVar4));
                    c20409a1.f26940t = null;
                }
                d.a aVar5 = new d.a();
                c20409a1.n2(mVar, aVar5);
                c20409a1.f26940t = aVar5;
            }
        }
    }

    @Override // Y41.p
    public final /* bridge */ /* synthetic */ kotlin.G0 invoke(androidx.compose.ui.focus.f0 f0Var, androidx.compose.ui.focus.f0 f0Var2) {
        f(f0Var, f0Var2);
        return kotlin.G0.f406611a;
    }
}
