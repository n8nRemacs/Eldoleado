package androidx.camera.core.impl;

import G.c;
import android.hardware.camera2.CaptureRequest;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

@j.X
/* loaded from: classes.dex */
public interface Config {

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public static final class OptionPriority {

        /* renamed from: b, reason: collision with root package name */
        public static final OptionPriority f23919b;

        /* renamed from: c, reason: collision with root package name */
        public static final OptionPriority f23920c;

        /* renamed from: d, reason: collision with root package name */
        public static final OptionPriority f23921d;

        /* renamed from: e, reason: collision with root package name */
        public static final /* synthetic */ OptionPriority[] f23922e;

        static {
            OptionPriority optionPriority = new OptionPriority("ALWAYS_OVERRIDE", 0);
            f23919b = optionPriority;
            OptionPriority optionPriority2 = new OptionPriority("REQUIRED", 1);
            f23920c = optionPriority2;
            OptionPriority optionPriority3 = new OptionPriority("OPTIONAL", 2);
            f23921d = optionPriority3;
            f23922e = new OptionPriority[]{optionPriority, optionPriority2, optionPriority3};
        }

        public OptionPriority() {
            throw null;
        }

        public static OptionPriority valueOf(String str) {
            return (OptionPriority) Enum.valueOf(OptionPriority.class, str);
        }

        public static OptionPriority[] values() {
            return (OptionPriority[]) f23922e.clone();
        }
    }

    @VY0.c
    public static abstract class a<T> {
        @j.N
        public static a a(@j.N Class cls, @j.N String str) {
            return new C20087d(str, cls, null);
        }

        @j.N
        public static a b(@j.N String str, @j.P CaptureRequest.Key key) {
            return new C20087d(str, Object.class, key);
        }

        @j.N
        public abstract String c();

        @j.P
        public abstract Object d();

        @j.N
        public abstract Class<T> e();
    }

    public interface b {
    }

    static void E(@j.N n0 n0Var, @j.N Config config, @j.N Config config2, @j.N a<?> aVar) {
        if (!Objects.equals(aVar, InterfaceC20088d0.f24101o)) {
            n0Var.T(aVar, config2.i(aVar), config2.f(aVar));
            return;
        }
        G.c cVarA = (G.c) config2.g(aVar, null);
        G.c cVar = (G.c) config.g(aVar, null);
        OptionPriority optionPriorityI = config2.i(aVar);
        if (cVarA == null) {
            cVarA = cVar;
        } else if (cVar != null) {
            c.b bVar = new c.b();
            bVar.f6127a = cVar.f6125a;
            bVar.f6128b = cVar.f6126b;
            G.a aVar2 = cVarA.f6125a;
            if (aVar2 != null) {
                bVar.f6127a = aVar2;
            }
            G.d dVar = cVarA.f6126b;
            if (dVar != null) {
                bVar.f6128b = dVar;
            }
            cVarA = bVar.a();
        }
        n0Var.T(aVar, optionPriorityI, cVarA);
    }

    @j.N
    static s0 P(@j.P Config config, @j.P Config config2) {
        if (config == null && config2 == null) {
            return s0.f24172H;
        }
        n0 n0VarS = config2 != null ? n0.S(config2) : n0.R();
        if (config != null) {
            Iterator<a<?>> it = config.e().iterator();
            while (it.hasNext()) {
                E(n0VarS, config2, config, it.next());
            }
        }
        return s0.Q(n0VarS);
    }

    @j.N
    Set<OptionPriority> a(@j.N a<?> aVar);

    void b(@j.N Ba1.B b12);

    boolean c(@j.N a<?> aVar);

    @j.P
    <ValueT> ValueT d(@j.N a<ValueT> aVar, @j.N OptionPriority optionPriority);

    @j.N
    Set<a<?>> e();

    @j.P
    <ValueT> ValueT f(@j.N a<ValueT> aVar);

    @j.P
    <ValueT> ValueT g(@j.N a<ValueT> aVar, @j.P ValueT valuet);

    @j.N
    OptionPriority i(@j.N a<?> aVar);
}
