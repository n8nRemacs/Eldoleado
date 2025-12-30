package androidx.compose.animation.core;

import androidx.annotation.RestrictTo;
import androidx.compose.runtime.C22040c3;
import androidx.compose.runtime.C22066f2;
import androidx.compose.runtime.C22072g3;
import androidx.compose.runtime.C22082i3;
import androidx.compose.runtime.C22126m3;
import androidx.compose.runtime.C22128n0;
import androidx.compose.runtime.F3;
import androidx.compose.runtime.I3;
import androidx.compose.runtime.InterfaceC22118l0;
import androidx.compose.runtime.InterfaceC22192v1;
import androidx.compose.runtime.InterfaceC22200x1;
import androidx.compose.runtime.InterfaceC22204y1;
import androidx.compose.runtime.X2;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;
import kotlinx.coroutines.C43259k;
import kotlinx.coroutines.CoroutineStart;

/* compiled from: Transition.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0005\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0004\u0003\u0004\u0005\u0006¨\u0006\u0007"}, d2 = {"Landroidx/compose/animation/core/i1;", "S", "", "a", "b", "c", "d", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
@F3
/* renamed from: androidx.compose.animation.core.i1, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20288i1<S> {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final F1<S> f26317a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.l
    public final C20288i1<?> f26318b;

    /* renamed from: c, reason: collision with root package name */
    @Y61.l
    public final String f26319c;

    /* renamed from: d, reason: collision with root package name */
    @Y61.k
    public final InterfaceC22204y1 f26320d;

    /* renamed from: e, reason: collision with root package name */
    @Y61.k
    public final InterfaceC22204y1 f26321e;

    /* renamed from: f, reason: collision with root package name */
    @Y61.k
    public final InterfaceC22200x1 f26322f;

    /* renamed from: g, reason: collision with root package name */
    @Y61.k
    public final InterfaceC22200x1 f26323g;

    /* renamed from: h, reason: collision with root package name */
    @Y61.k
    public final InterfaceC22204y1 f26324h;

    /* renamed from: i, reason: collision with root package name */
    @Y61.k
    public final androidx.compose.runtime.snapshots.D<C20288i1<S>.d<?, ?>> f26325i;

    /* renamed from: j, reason: collision with root package name */
    @Y61.k
    public final androidx.compose.runtime.snapshots.D<C20288i1<?>> f26326j;

    /* renamed from: k, reason: collision with root package name */
    @Y61.k
    public final InterfaceC22204y1 f26327k;

    /* renamed from: l, reason: collision with root package name */
    @Y61.k
    public final I3 f26328l;

    /* compiled from: Transition.kt */
    @kotlin.jvm.internal.s0
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0087\u0004\u0018\u0000*\u0004\b\u0001\u0010\u0001*\b\b\u0002\u0010\u0003*\u00020\u00022\u00020\u0004:\u0001\u0005¨\u0006\u0006"}, d2 = {"Landroidx/compose/animation/core/i1$a;", "T", "Landroidx/compose/animation/core/x;", "V", "", "a", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    @RestrictTo
    /* renamed from: androidx.compose.animation.core.i1$a */
    public final class a<T, V extends AbstractC20330x> {

        /* renamed from: a, reason: collision with root package name */
        @Y61.k
        public final H1<T, V> f26329a;

        /* renamed from: b, reason: collision with root package name */
        @Y61.k
        public final InterfaceC22204y1 f26330b = C22126m3.g(null);

        /* compiled from: Transition.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0080\u0004\u0018\u0000*\u0004\b\u0003\u0010\u0001*\b\b\u0004\u0010\u0003*\u00020\u00022\b\u0012\u0004\u0012\u00028\u00030\u0004¨\u0006\u0005"}, d2 = {"Landroidx/compose/animation/core/i1$a$a;", "T", "Landroidx/compose/animation/core/x;", "V", "Landroidx/compose/runtime/I3;", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
        /* renamed from: androidx.compose.animation.core.i1$a$a, reason: collision with other inner class name */
        public final class C1552a<T, V extends AbstractC20330x> implements I3<T> {

            /* renamed from: b, reason: collision with root package name */
            @Y61.k
            public final C20288i1<S>.d<T, V> f26332b;

            /* renamed from: c, reason: collision with root package name */
            @Y61.k
            public kotlin.jvm.internal.N f26333c;

            /* renamed from: d, reason: collision with root package name */
            @Y61.k
            public kotlin.jvm.internal.N f26334d;

            /* JADX WARN: Multi-variable type inference failed */
            public C1552a(@Y61.k C20288i1<S>.d<T, V> dVar, @Y61.k Y41.l<? super b<S>, ? extends V<T>> lVar, @Y61.k Y41.l<? super S, ? extends T> lVar2) {
                this.f26332b = dVar;
                this.f26333c = (kotlin.jvm.internal.N) lVar;
                this.f26334d = (kotlin.jvm.internal.N) lVar2;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r0v0, types: [Y41.l, kotlin.jvm.internal.N] */
            /* JADX WARN: Type inference failed for: r1v4, types: [Y41.l, kotlin.jvm.internal.N] */
            /* JADX WARN: Type inference failed for: r1v5, types: [Y41.l, kotlin.jvm.internal.N] */
            /* JADX WARN: Type inference failed for: r3v1, types: [Y41.l, kotlin.jvm.internal.N] */
            public final void a(@Y61.k b<S> bVar) {
                Object objInvoke = this.f26334d.invoke(bVar.d());
                boolean zG2 = C20288i1.this.g();
                C20288i1<S>.d<T, V> dVar = this.f26332b;
                if (zG2) {
                    dVar.q(this.f26334d.invoke(bVar.a()), objInvoke, (V) this.f26333c.invoke(bVar));
                } else {
                    dVar.r(objInvoke, (V) this.f26333c.invoke(bVar));
                }
            }

            @Override // androidx.compose.runtime.I3
            /* renamed from: getValue */
            public final T getF42167b() {
                a(C20288i1.this.f());
                return (T) ((C22082i3) this.f26332b.f26345i).getF42167b();
            }
        }

        public a(@Y61.k H1<T, V> h12, @Y61.k String str) {
            this.f26329a = h12;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Y61.k
        public final C1552a a(@Y61.k Y41.l lVar, @Y61.k Y41.l lVar2) {
            InterfaceC22204y1 interfaceC22204y1 = this.f26330b;
            C1552a c1552a = (C1552a) ((C22082i3) interfaceC22204y1).getF42167b();
            C20288i1<S> c20288i1 = C20288i1.this;
            if (c1552a == null) {
                Object objInvoke = lVar2.invoke(c20288i1.f26317a.a());
                Object objInvoke2 = lVar2.invoke(c20288i1.f26317a.a());
                H1<T, V> h12 = this.f26329a;
                AbstractC20330x abstractC20330x = (AbstractC20330x) ((I1) h12).a().invoke(objInvoke2);
                abstractC20330x.d();
                C20288i1<S>.d<?, ?> dVar = c20288i1.new d<>(objInvoke, abstractC20330x, h12);
                c1552a = new C1552a(dVar, lVar, lVar2);
                ((C22082i3) interfaceC22204y1).setValue(c1552a);
                c20288i1.f26325i.add(dVar);
            }
            c1552a.f26334d = (kotlin.jvm.internal.N) lVar2;
            c1552a.f26333c = (kotlin.jvm.internal.N) lVar;
            c1552a.a(c20288i1.f());
            return c1552a;
        }
    }

    /* compiled from: Transition.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\bf\u0018\u0000*\u0004\b\u0001\u0010\u00012\u00020\u0002ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0003À\u0006\u0003"}, d2 = {"Landroidx/compose/animation/core/i1$b;", "S", "", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: androidx.compose.animation.core.i1$b */
    public interface b<S> {

        /* compiled from: Transition.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        /* renamed from: androidx.compose.animation.core.i1$b$a */
        public static final class a {
        }

        S a();

        default boolean b(S s5, S s12) {
            return s5.equals(a()) && s12.equals(d());
        }

        S d();
    }

    /* compiled from: Transition.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u00028\u00010\u0002¨\u0006\u0003"}, d2 = {"Landroidx/compose/animation/core/i1$c;", "S", "Landroidx/compose/animation/core/i1$b;", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: androidx.compose.animation.core.i1$c */
    public static final class c<S> implements b<S> {

        /* renamed from: a, reason: collision with root package name */
        public final S f26336a;

        /* renamed from: b, reason: collision with root package name */
        public final S f26337b;

        public c(S s5, S s12) {
            this.f26336a = s5;
            this.f26337b = s12;
        }

        @Override // androidx.compose.animation.core.C20288i1.b
        public final S a() {
            return this.f26336a;
        }

        @Override // androidx.compose.animation.core.C20288i1.b
        public final S d() {
            return this.f26337b;
        }

        public final boolean equals(@Y61.l Object obj) {
            if (obj instanceof b) {
                b bVar = (b) obj;
                if (kotlin.jvm.internal.L.f(this.f26336a, bVar.a())) {
                    if (kotlin.jvm.internal.L.f(this.f26337b, bVar.d())) {
                        return true;
                    }
                }
            }
            return false;
        }

        public final int hashCode() {
            S s5 = this.f26336a;
            int iHashCode = (s5 != null ? s5.hashCode() : 0) * 31;
            S s12 = this.f26337b;
            return iHashCode + (s12 != null ? s12.hashCode() : 0);
        }
    }

    /* compiled from: Transition.kt */
    @kotlin.jvm.internal.s0
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0087\u0004\u0018\u0000*\u0004\b\u0001\u0010\u0001*\b\b\u0002\u0010\u0003*\u00020\u00022\b\u0012\u0004\u0012\u00028\u00010\u0004¨\u0006\u0005"}, d2 = {"Landroidx/compose/animation/core/i1$d;", "T", "Landroidx/compose/animation/core/x;", "V", "Landroidx/compose/runtime/I3;", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    @F3
    /* renamed from: androidx.compose.animation.core.i1$d */
    public final class d<T, V extends AbstractC20330x> implements I3<T> {

        /* renamed from: b, reason: collision with root package name */
        @Y61.k
        public final H1<T, V> f26338b;

        /* renamed from: c, reason: collision with root package name */
        @Y61.k
        public final InterfaceC22204y1 f26339c;

        /* renamed from: d, reason: collision with root package name */
        @Y61.k
        public final InterfaceC22204y1 f26340d;

        /* renamed from: e, reason: collision with root package name */
        @Y61.k
        public final InterfaceC22204y1 f26341e;

        /* renamed from: f, reason: collision with root package name */
        @Y61.k
        public final InterfaceC22204y1 f26342f;

        /* renamed from: g, reason: collision with root package name */
        @Y61.k
        public final InterfaceC22192v1 f26343g;

        /* renamed from: h, reason: collision with root package name */
        public boolean f26344h;

        /* renamed from: i, reason: collision with root package name */
        @Y61.k
        public final InterfaceC22204y1 f26345i;

        /* renamed from: j, reason: collision with root package name */
        @Y61.k
        public V f26346j;

        /* renamed from: k, reason: collision with root package name */
        @Y61.k
        public final InterfaceC22200x1 f26347k;

        /* renamed from: l, reason: collision with root package name */
        public boolean f26348l;

        /* renamed from: m, reason: collision with root package name */
        @Y61.k
        public final R0 f26349m;

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r11v11, types: [java.lang.Object, java.util.Map] */
        public d(Object obj, @Y61.k AbstractC20330x abstractC20330x, @Y61.k H1 h12) {
            this.f26338b = h12;
            InterfaceC22204y1 interfaceC22204y1G = C22126m3.g(obj);
            this.f26339c = interfaceC22204y1G;
            T tInvoke = null;
            InterfaceC22204y1 interfaceC22204y1G2 = C22126m3.g(C20310q.d(0.0f, 7, null));
            this.f26340d = interfaceC22204y1G2;
            this.f26341e = C22126m3.g(new C20285h1((V) ((C22082i3) interfaceC22204y1G2).getF42167b(), (H1<Object, AbstractC20330x>) h12, obj, ((C22082i3) interfaceC22204y1G).getF42167b(), abstractC20330x));
            this.f26342f = C22126m3.g(Boolean.TRUE);
            this.f26343g = androidx.compose.runtime.Q1.a(-1.0f);
            this.f26345i = C22126m3.g(obj);
            this.f26346j = abstractC20330x;
            this.f26347k = X2.a(a().getF26065h());
            Float f12 = (Float) b2.f26221b.get(h12);
            if (f12 != null) {
                float fFloatValue = f12.floatValue();
                V vInvoke = h12.a().invoke(obj);
                int f26428e = vInvoke.getF26416d();
                for (int i12 = 0; i12 < f26428e; i12++) {
                    vInvoke.e(fFloatValue, i12);
                }
                tInvoke = this.f26338b.b().invoke(vInvoke);
            }
            this.f26349m = C20310q.d(0.0f, 3, tInvoke);
        }

        @Y61.k
        public final C20285h1<T, V> a() {
            return (C20285h1) ((C22082i3) this.f26341e).getF42167b();
        }

        public final void d(long j12) {
            if (((C22040c3) this.f26343g).f() == -1.0f) {
                this.f26348l = true;
                if (kotlin.jvm.internal.L.f(a().f26308c, a().f26309d)) {
                    j(a().f26308c);
                } else {
                    j(a().e(j12));
                    this.f26346j = (V) a().g(j12);
                }
            }
        }

        @Override // androidx.compose.runtime.I3
        /* renamed from: getValue */
        public final T getF42167b() {
            return (T) ((C22082i3) this.f26345i).getF42167b();
        }

        public final void j(T t12) {
            ((C22082i3) this.f26345i).setValue(t12);
        }

        public final void p(T t12, boolean z12) {
            C22082i3 c22082i3 = (C22082i3) this.f26339c;
            boolean zF2 = kotlin.jvm.internal.L.f(null, c22082i3.getF42167b());
            InterfaceC22200x1 interfaceC22200x1 = this.f26347k;
            InterfaceC22204y1 interfaceC22204y1 = this.f26341e;
            V v12 = this.f26349m;
            if (zF2) {
                ((C22082i3) interfaceC22204y1).setValue(new C20285h1(v12, this.f26338b, t12, t12, this.f26346j.c()));
                this.f26344h = true;
                ((C22072g3) interfaceC22200x1).n(a().getF26065h());
                return;
            }
            InterfaceC22204y1 interfaceC22204y12 = this.f26340d;
            if (!z12 || this.f26348l || (((V) ((C22082i3) interfaceC22204y12).getF42167b()) instanceof R0)) {
                v12 = (V) ((C22082i3) interfaceC22204y12).getF42167b();
            }
            C20288i1<S> c20288i1 = C20288i1.this;
            ((C22082i3) interfaceC22204y1).setValue(new C20285h1((InterfaceC20307p<Object>) (c20288i1.e() <= 0 ? v12 : new S0(v12, c20288i1.e())), this.f26338b, t12, c22082i3.getF42167b(), this.f26346j));
            ((C22072g3) interfaceC22200x1).n(a().getF26065h());
            this.f26344h = false;
            Boolean bool = Boolean.TRUE;
            InterfaceC22204y1 interfaceC22204y13 = c20288i1.f26324h;
            ((C22082i3) interfaceC22204y13).setValue(bool);
            if (c20288i1.g()) {
                androidx.compose.runtime.snapshots.D<C20288i1<S>.d<?, ?>> d12 = c20288i1.f26325i;
                int size = d12.size();
                long jMax = 0;
                for (int i12 = 0; i12 < size; i12++) {
                    C20288i1<S>.d<?, ?> dVar = d12.get(i12);
                    jMax = Math.max(jMax, ((C22072g3) dVar.f26347k).g());
                    dVar.d(0L);
                }
                ((C22082i3) interfaceC22204y13).setValue(Boolean.FALSE);
            }
        }

        public final void q(T t12, T t13, @Y61.k V<T> v12) {
            ((C22082i3) this.f26339c).setValue(t13);
            ((C22082i3) this.f26340d).setValue(v12);
            if (kotlin.jvm.internal.L.f(a().f26309d, t12) && kotlin.jvm.internal.L.f(a().f26308c, t13)) {
                return;
            }
            p(t12, false);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void r(T t12, @Y61.k V<T> v12) {
            if (this.f26344h && kotlin.jvm.internal.L.f(t12, null)) {
                return;
            }
            InterfaceC22204y1 interfaceC22204y1 = this.f26339c;
            boolean zF2 = kotlin.jvm.internal.L.f(((C22082i3) interfaceC22204y1).getF42167b(), t12);
            InterfaceC22192v1 interfaceC22192v1 = this.f26343g;
            if (zF2 && ((C22040c3) interfaceC22192v1).f() == -1.0f) {
                return;
            }
            ((C22082i3) interfaceC22204y1).setValue(t12);
            ((C22082i3) this.f26340d).setValue(v12);
            C22040c3 c22040c3 = (C22040c3) interfaceC22192v1;
            Object f42167b = c22040c3.f() == -3.0f ? t12 : ((C22082i3) this.f26345i).getF42167b();
            InterfaceC22204y1 interfaceC22204y12 = this.f26342f;
            p(f42167b, !((Boolean) ((C22082i3) interfaceC22204y12).getF42167b()).booleanValue());
            ((C22082i3) interfaceC22204y12).setValue(Boolean.valueOf(c22040c3.f() == -3.0f));
            if (c22040c3.f() >= 0.0f) {
                j(a().e((long) (c22040c3.f() * a().getF26065h())));
            } else if (c22040c3.f() == -3.0f) {
                j(t12);
            }
            this.f26344h = false;
            ((C22040c3) interfaceC22192v1).S6(-1.0f);
        }

        @Y61.k
        public final String toString() {
            return "current value: " + ((C22082i3) this.f26345i).getF42167b() + ", target: " + ((C22082i3) this.f26339c).getF42167b() + ", spec: " + ((V) ((C22082i3) this.f26340d).getF42167b());
        }
    }

    /* compiled from: Transition.kt */
    @kotlin.jvm.internal.s0
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0005\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\u00020\u0001H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"S", "Landroidx/compose/runtime/n0;", "Landroidx/compose/runtime/l0;", "invoke", "(Landroidx/compose/runtime/n0;)Landroidx/compose/runtime/l0;", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    /* renamed from: androidx.compose.animation.core.i1$e */
    public static final class e extends kotlin.jvm.internal.N implements Y41.l<C22128n0, InterfaceC22118l0> {

        /* renamed from: l, reason: collision with root package name */
        public final /* synthetic */ kotlinx.coroutines.T f26351l;

        /* renamed from: m, reason: collision with root package name */
        public final /* synthetic */ C20288i1<Object> f26352m;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public e(kotlinx.coroutines.T t12, C20288i1<Object> c20288i1) {
            super(1);
            this.f26351l = t12;
            this.f26352m = c20288i1;
        }

        @Override // Y41.l
        public final InterfaceC22118l0 invoke(C22128n0 c22128n0) {
            C43259k.d(this.f26351l, null, CoroutineStart.f410683e, new C20291j1(this.f26352m, null), 1);
            return new C20294k1();
        }
    }

    /* compiled from: Transition.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* renamed from: androidx.compose.animation.core.i1$f */
    public static final class f extends kotlin.jvm.internal.N implements Y41.p<androidx.compose.runtime.A, Integer, kotlin.G0> {

        /* renamed from: l, reason: collision with root package name */
        public final /* synthetic */ C20288i1<Object> f26353l;

        /* renamed from: m, reason: collision with root package name */
        public final /* synthetic */ Object f26354m;

        /* renamed from: n, reason: collision with root package name */
        public final /* synthetic */ int f26355n;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public f(C20288i1<Object> c20288i1, Object obj, int i12) {
            super(2);
            this.f26353l = c20288i1;
            this.f26354m = obj;
            this.f26355n = i12;
        }

        @Override // Y41.p
        public final kotlin.G0 invoke(androidx.compose.runtime.A a12, Integer num) {
            num.intValue();
            int iA2 = C22066f2.a(this.f26355n | 1);
            this.f26353l.a(iA2, a12, this.f26354m);
            return kotlin.G0.f406611a;
        }
    }

    /* compiled from: Transition.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "S", "invoke", "()Ljava/lang/Long;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    /* renamed from: androidx.compose.animation.core.i1$g */
    public static final class g extends kotlin.jvm.internal.N implements Y41.a<Long> {

        /* renamed from: l, reason: collision with root package name */
        public final /* synthetic */ C20288i1<S> f26356l;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public g(C20288i1<S> c20288i1) {
            super(0);
            this.f26356l = c20288i1;
        }

        @Override // Y41.a
        public final Long invoke() {
            return Long.valueOf(this.f26356l.b());
        }
    }

    @kotlin.X
    public C20288i1() {
        throw null;
    }

    public C20288i1(@Y61.k F1<S> f12, @Y61.l C20288i1<?> c20288i1, @Y61.l String str) {
        this.f26317a = f12;
        this.f26318b = c20288i1;
        this.f26319c = str;
        this.f26320d = C22126m3.g(f12.a());
        this.f26321e = C22126m3.g(new c(f12.a(), f12.a()));
        this.f26322f = X2.a(0L);
        this.f26323g = X2.a(Long.MIN_VALUE);
        Boolean bool = Boolean.FALSE;
        this.f26324h = C22126m3.g(bool);
        this.f26325i = new androidx.compose.runtime.snapshots.D<>();
        this.f26326j = new androidx.compose.runtime.snapshots.D<>();
        this.f26327k = C22126m3.g(bool);
        this.f26328l = C22126m3.d(new g(this));
        f12.c(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:39:0x008d  */
    @androidx.compose.runtime.InterfaceC22132o
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void a(int r11, @Y61.l androidx.compose.runtime.A r12, java.lang.Object r13) {
        /*
            Method dump skipped, instructions count: 238
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.core.C20288i1.a(int, androidx.compose.runtime.A, java.lang.Object):void");
    }

    public final long b() {
        androidx.compose.runtime.snapshots.D<C20288i1<S>.d<?, ?>> d12 = this.f26325i;
        int size = d12.size();
        long jMax = 0;
        for (int i12 = 0; i12 < size; i12++) {
            jMax = Math.max(jMax, ((C22072g3) d12.get(i12).f26347k).g());
        }
        androidx.compose.runtime.snapshots.D<C20288i1<?>> d13 = this.f26326j;
        int size2 = d13.size();
        for (int i13 = 0; i13 < size2; i13++) {
            jMax = Math.max(jMax, d13.get(i13).b());
        }
        return jMax;
    }

    @InterfaceC20296l0
    public final boolean c() {
        androidx.compose.runtime.snapshots.D<C20288i1<S>.d<?, ?>> d12 = this.f26325i;
        int size = d12.size();
        for (int i12 = 0; i12 < size; i12++) {
            d12.get(i12).getClass();
        }
        androidx.compose.runtime.snapshots.D<C20288i1<?>> d13 = this.f26326j;
        int size2 = d13.size();
        for (int i13 = 0; i13 < size2; i13++) {
            if (d13.get(i13).c()) {
                return true;
            }
        }
        return false;
    }

    @Y61.l
    /* renamed from: d, reason: from getter */
    public final String getF26319c() {
        return this.f26319c;
    }

    @RestrictTo
    public final long e() {
        C20288i1<?> c20288i1 = this.f26318b;
        return c20288i1 != null ? c20288i1.e() : this.f26322f.g();
    }

    @Y61.k
    public final b<S> f() {
        return (b) ((C22082i3) this.f26321e).getF42167b();
    }

    @RestrictTo
    public final boolean g() {
        return ((Boolean) ((C22082i3) this.f26327k).getF42167b()).booleanValue();
    }

    /* JADX WARN: Type inference failed for: r6v13, types: [V extends androidx.compose.animation.core.x, androidx.compose.animation.core.x] */
    public final void h(long j12, boolean z12) {
        InterfaceC22200x1 interfaceC22200x1 = this.f26323g;
        long jG2 = ((C22072g3) interfaceC22200x1).g();
        F1<S> f12 = this.f26317a;
        if (jG2 == Long.MIN_VALUE) {
            ((C22072g3) interfaceC22200x1).n(j12);
            ((C22082i3) f12.f26057a).setValue(Boolean.TRUE);
        } else if (!((Boolean) ((C22082i3) f12.f26057a).getF42167b()).booleanValue()) {
            ((C22082i3) f12.f26057a).setValue(Boolean.TRUE);
        }
        ((C22082i3) this.f26324h).setValue(Boolean.FALSE);
        androidx.compose.runtime.snapshots.D<C20288i1<S>.d<?, ?>> d12 = this.f26325i;
        int size = d12.size();
        boolean z13 = true;
        for (int i12 = 0; i12 < size; i12++) {
            C20288i1<S>.d<?, ?> dVar = d12.get(i12);
            boolean zBooleanValue = ((Boolean) ((C22082i3) dVar.f26342f).getF42167b()).booleanValue();
            InterfaceC22204y1 interfaceC22204y1 = dVar.f26342f;
            if (!zBooleanValue) {
                long f26065h = z12 ? dVar.a().getF26065h() : j12;
                dVar.j(dVar.a().e(f26065h));
                dVar.f26346j = dVar.a().g(f26065h);
                if (dVar.a().c(f26065h)) {
                    ((C22082i3) interfaceC22204y1).setValue(Boolean.TRUE);
                }
            }
            if (!((Boolean) ((C22082i3) interfaceC22204y1).getF42167b()).booleanValue()) {
                z13 = false;
            }
        }
        androidx.compose.runtime.snapshots.D<C20288i1<?>> d13 = this.f26326j;
        int size2 = d13.size();
        for (int i13 = 0; i13 < size2; i13++) {
            C20288i1<?> c20288i1 = d13.get(i13);
            Object f42167b = ((C22082i3) c20288i1.f26320d).getF42167b();
            F1<?> f13 = c20288i1.f26317a;
            if (!kotlin.jvm.internal.L.f(f42167b, f13.a())) {
                c20288i1.h(j12, z12);
            }
            if (!kotlin.jvm.internal.L.f(((C22082i3) c20288i1.f26320d).getF42167b(), f13.a())) {
                z13 = false;
            }
        }
        if (z13) {
            i();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void i() {
        ((C22072g3) this.f26323g).n(Long.MIN_VALUE);
        F1<S> f12 = this.f26317a;
        if (f12 instanceof C20319t0) {
            f12.b(((C22082i3) this.f26320d).getF42167b());
        }
        if (this.f26318b == null) {
            ((C22072g3) this.f26322f).n(0L);
        }
        ((C22082i3) f12.f26057a).setValue(Boolean.FALSE);
        androidx.compose.runtime.snapshots.D<C20288i1<?>> d12 = this.f26326j;
        int size = d12.size();
        for (int i12 = 0; i12 < size; i12++) {
            d12.get(i12).i();
        }
    }

    @X41.i
    @RestrictTo
    public final void j(Object obj, Object obj2) {
        ((C22072g3) this.f26323g).n(Long.MIN_VALUE);
        F1<S> f12 = this.f26317a;
        ((C22082i3) f12.f26057a).setValue(Boolean.FALSE);
        boolean zG2 = g();
        InterfaceC22204y1 interfaceC22204y1 = this.f26320d;
        if (!zG2 || !kotlin.jvm.internal.L.f(f12.a(), obj) || !kotlin.jvm.internal.L.f(((C22082i3) interfaceC22204y1).getF42167b(), obj2)) {
            if (!kotlin.jvm.internal.L.f(f12.a(), obj) && (f12 instanceof C20319t0)) {
                f12.b(obj);
            }
            ((C22082i3) interfaceC22204y1).setValue(obj2);
            ((C22082i3) this.f26327k).setValue(Boolean.TRUE);
            ((C22082i3) this.f26321e).setValue(new c(obj, obj2));
        }
        androidx.compose.runtime.snapshots.D<C20288i1<?>> d12 = this.f26326j;
        int size = d12.size();
        for (int i12 = 0; i12 < size; i12++) {
            C20288i1<?> c20288i1 = d12.get(i12);
            if (c20288i1.g()) {
                c20288i1.j(c20288i1.f26317a.a(), ((C22082i3) c20288i1.f26320d).getF42167b());
            }
        }
        androidx.compose.runtime.snapshots.D<C20288i1<S>.d<?, ?>> d13 = this.f26325i;
        int size2 = d13.size();
        for (int i13 = 0; i13 < size2; i13++) {
            d13.get(i13).d(0L);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void k(S s5) {
        InterfaceC22204y1 interfaceC22204y1 = this.f26320d;
        C22082i3 c22082i3 = (C22082i3) interfaceC22204y1;
        if (kotlin.jvm.internal.L.f(c22082i3.getF42167b(), s5)) {
            return;
        }
        ((C22082i3) this.f26321e).setValue(new c(c22082i3.getF42167b(), s5));
        F1<S> f12 = this.f26317a;
        if (!kotlin.jvm.internal.L.f(f12.a(), c22082i3.getF42167b())) {
            f12.b(c22082i3.getF42167b());
        }
        ((C22082i3) interfaceC22204y1).setValue(s5);
        if (((C22072g3) this.f26323g).g() == Long.MIN_VALUE) {
            ((C22082i3) this.f26324h).setValue(Boolean.TRUE);
        }
        androidx.compose.runtime.snapshots.D<C20288i1<S>.d<?, ?>> d12 = this.f26325i;
        int size = d12.size();
        for (int i12 = 0; i12 < size; i12++) {
            ((C22040c3) d12.get(i12).f26343g).S6(-2.0f);
        }
    }

    @Y61.k
    public final String toString() {
        androidx.compose.runtime.snapshots.D<C20288i1<S>.d<?, ?>> d12 = this.f26325i;
        int size = d12.size();
        String str = "Transition animation values: ";
        for (int i12 = 0; i12 < size; i12++) {
            str = str + d12.get(i12) + ", ";
        }
        return str;
    }

    public /* synthetic */ C20288i1(F1 f12, C20288i1 c20288i1, String str, int i12, C42822w c42822w) {
        this(f12, c20288i1, (i12 & 4) != 0 ? null : str);
    }

    public C20288i1(F1 f12, String str, int i12, C42822w c42822w) {
        this(f12, null, (i12 & 2) != 0 ? null : str);
    }

    public C20288i1(C20319t0 c20319t0, String str, int i12, C42822w c42822w) {
        this(c20319t0, null, (i12 & 2) != 0 ? null : str);
    }
}
