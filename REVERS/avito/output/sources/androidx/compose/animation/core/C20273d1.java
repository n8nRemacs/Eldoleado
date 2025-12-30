package androidx.compose.animation.core;

import androidx.compose.runtime.C22082i3;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: SuspendAnimation.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0002\n\u0000¨\u0006\u0000"}, d2 = {"animation-core_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.core.d1, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20273d1 {

    /* compiled from: SuspendAnimation.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0006\u001a\u00020\u0003*\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00020\u0000H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"Landroidx/compose/animation/core/o;", "", "Landroidx/compose/animation/core/t;", "Lkotlin/G0;", "invoke", "(Landroidx/compose/animation/core/o;)V", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    /* renamed from: androidx.compose.animation.core.d1$a */
    public static final class a extends kotlin.jvm.internal.N implements Y41.l<C20304o<Float, C20318t>, kotlin.G0> {

        /* renamed from: l, reason: collision with root package name */
        public final /* synthetic */ Y41.p<Float, Float, kotlin.G0> f26246l;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public a(Y41.p<? super Float, ? super Float, kotlin.G0> pVar) {
            super(1);
            this.f26246l = pVar;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // Y41.l
        public final kotlin.G0 invoke(C20304o<Float, C20318t> c20304o) {
            C20304o<Float, C20318t> c20304o2 = c20304o;
            this.f26246l.invoke(((C22082i3) c20304o2.f26389e).getF42167b(), Float.valueOf(((C20318t) c20304o2.f26390f).f26403a));
            return kotlin.G0.f406611a;
        }
    }

    @Y61.l
    public static final Object a(float f12, float f13, float f14, @Y61.k InterfaceC20307p interfaceC20307p, @Y61.k Y41.p pVar, @Y61.k ContinuationImpl continuationImpl) {
        AbstractC20330x abstractC20330xC;
        int i12 = kotlin.jvm.internal.A.f406820a;
        H1<Float, C20318t> h12 = J1.f26075a;
        Float fBoxFloat = Boxing.boxFloat(f12);
        Float fBoxFloat2 = Boxing.boxFloat(f13);
        Float fBoxFloat3 = Boxing.boxFloat(f14);
        if (fBoxFloat3 == null || (abstractC20330xC = (AbstractC20330x) ((I1) h12).a().invoke(fBoxFloat3)) == null) {
            abstractC20330xC = ((AbstractC20330x) ((I1) h12).a().invoke(fBoxFloat)).c();
        }
        AbstractC20330x abstractC20330x = abstractC20330xC;
        Object objB = b(new r(h12, fBoxFloat, abstractC20330x, 0L, 0L, false, 56, null), new C20285h1((InterfaceC20307p<Float>) interfaceC20307p, h12, fBoxFloat, fBoxFloat2, abstractC20330x), Long.MIN_VALUE, new W0(pVar, h12), continuationImpl);
        if (objB != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            objB = kotlin.G0.f406611a;
        }
        return objB == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objB : kotlin.G0.f406611a;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:55:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x001a  */
    /* JADX WARN: Type inference failed for: r8v0, types: [T, androidx.compose.animation.core.o] */
    @Y61.l
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final java.lang.Object b(@Y61.k androidx.compose.animation.core.r r24, @Y61.k androidx.compose.animation.core.InterfaceC20286i r25, long r26, @Y61.k Y41.l r28, @Y61.k kotlin.coroutines.jvm.internal.ContinuationImpl r29) {
        /*
            Method dump skipped, instructions count: 377
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.core.C20273d1.b(androidx.compose.animation.core.r, androidx.compose.animation.core.i, long, Y41.l, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public static /* synthetic */ Object c(float f12, float f13, InterfaceC20307p interfaceC20307p, Y41.p pVar, ContinuationImpl continuationImpl, int i12) {
        if ((i12 & 8) != 0) {
            interfaceC20307p = C20310q.d(0.0f, 7, null);
        }
        return a(f12, f13, 0.0f, interfaceC20307p, pVar, continuationImpl);
    }

    @Y61.l
    public static final Object d(float f12, float f13, @Y61.k X x12, @Y61.k Y41.p<? super Float, ? super Float, kotlin.G0> pVar, @Y61.k Continuation<? super kotlin.G0> continuation) {
        I i12 = new I(x12);
        int i13 = kotlin.jvm.internal.A.f406820a;
        Object objB = b(C20315s.a(f12, f13, 28), new G(i12, J1.f26075a, Float.valueOf(f12), new C20318t(f13)), Long.MIN_VALUE, new a(pVar), (ContinuationImpl) continuation);
        return objB == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objB : kotlin.G0.f406611a;
    }

    @Y61.l
    public static final Object e(@Y61.k r rVar, @Y61.k H h12, boolean z12, @Y61.k Y41.l lVar, @Y61.k ContinuationImpl continuationImpl) {
        Object objB = b(rVar, new G(h12, rVar.f26396b, ((C22082i3) rVar.f26397c).getF42167b(), rVar.f26398d), z12 ? rVar.f26399e : Long.MIN_VALUE, lVar, continuationImpl);
        return objB == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objB : kotlin.G0.f406611a;
    }

    @Y61.l
    public static final Object f(@Y61.k r rVar, Object obj, @Y61.k InterfaceC20307p interfaceC20307p, boolean z12, @Y61.k Y41.l lVar, @Y61.k ContinuationImpl continuationImpl) {
        Object objB = b(rVar, new C20285h1((InterfaceC20307p<Object>) interfaceC20307p, rVar.f26396b, ((C22082i3) rVar.f26397c).getF42167b(), obj, rVar.f26398d), z12 ? rVar.f26399e : Long.MIN_VALUE, lVar, continuationImpl);
        return objB == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objB : kotlin.G0.f406611a;
    }

    public static /* synthetic */ Object g(r rVar, Object obj, InterfaceC20307p interfaceC20307p, boolean z12, Y41.l lVar, ContinuationImpl continuationImpl, int i12) {
        if ((i12 & 2) != 0) {
            interfaceC20307p = C20310q.d(0.0f, 7, null);
        }
        InterfaceC20307p interfaceC20307p2 = interfaceC20307p;
        if ((i12 & 4) != 0) {
            z12 = false;
        }
        boolean z13 = z12;
        if ((i12 & 8) != 0) {
            lVar = C20279f1.f26260l;
        }
        return f(rVar, obj, interfaceC20307p2, z13, lVar, continuationImpl);
    }

    public static final <T, V extends AbstractC20330x> void h(C20304o<T, V> c20304o, long j12, float f12, InterfaceC20286i<T, V> interfaceC20286i, r<T, V> rVar, Y41.l<? super C20304o<T, V>, kotlin.G0> lVar) {
        long f26065h = f12 == 0.0f ? interfaceC20286i.getF26065h() : (long) ((j12 - c20304o.f26387c) / f12);
        c20304o.f26391g = j12;
        ((C22082i3) c20304o.f26389e).setValue(interfaceC20286i.e(f26065h));
        c20304o.f26390f = (V) interfaceC20286i.g(f26065h);
        if (interfaceC20286i.c(f26065h)) {
            c20304o.f26392h = c20304o.f26391g;
            ((C22082i3) c20304o.f26393i).setValue(Boolean.FALSE);
        }
        j(c20304o, rVar);
        lVar.invoke(c20304o);
    }

    public static final float i(@Y61.k CoroutineContext coroutineContext) {
        androidx.compose.ui.x xVar = (androidx.compose.ui.x) coroutineContext.get(androidx.compose.ui.x.f43131z1);
        float scaleFactor = xVar != null ? xVar.getScaleFactor() : 1.0f;
        if (!(scaleFactor >= 0.0f)) {
            C20334y0.b("negative scale factor");
        }
        return scaleFactor;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <T, V extends AbstractC20330x> void j(@Y61.k C20304o<T, V> c20304o, @Y61.k r<T, V> rVar) {
        ((C22082i3) rVar.f26397c).setValue(((C22082i3) c20304o.f26389e).getF42167b());
        V v12 = rVar.f26398d;
        V v13 = c20304o.f26390f;
        int f26428e = v12.getF26416d();
        for (int i12 = 0; i12 < f26428e; i12++) {
            v12.e(v13.a(i12), i12);
        }
        rVar.f26400f = c20304o.f26392h;
        rVar.f26399e = c20304o.f26391g;
        rVar.f26401g = ((Boolean) ((C22082i3) c20304o.f26393i).getF42167b()).booleanValue();
    }
}
