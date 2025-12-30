package androidx.camera.core.impl;

import D.h;
import android.util.ArrayMap;
import androidx.camera.core.impl.Config;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/* compiled from: OptionsBundle.java */
@j.X
/* loaded from: classes.dex */
public class s0 implements Config {

    /* renamed from: G, reason: collision with root package name */
    public static final r0 f24171G;

    /* renamed from: H, reason: collision with root package name */
    public static final s0 f24172H;

    /* renamed from: F, reason: collision with root package name */
    public final TreeMap<Config.a<?>, Map<Config.OptionPriority, Object>> f24173F;

    static {
        r0 r0Var = new r0(0);
        f24171G = r0Var;
        f24172H = new s0(new TreeMap(r0Var));
    }

    public s0(TreeMap<Config.a<?>, Map<Config.OptionPriority, Object>> treeMap) {
        this.f24173F = treeMap;
    }

    @j.N
    public static s0 Q(@j.N Config config) {
        if (s0.class.equals(config.getClass())) {
            return (s0) config;
        }
        TreeMap treeMap = new TreeMap(f24171G);
        for (Config.a<?> aVar : config.e()) {
            Set<Config.OptionPriority> setA = config.a(aVar);
            ArrayMap arrayMap = new ArrayMap();
            for (Config.OptionPriority optionPriority : setA) {
                arrayMap.put(optionPriority, config.d(aVar, optionPriority));
            }
            treeMap.put(aVar, arrayMap);
        }
        return new s0(treeMap);
    }

    @Override // androidx.camera.core.impl.Config
    @j.N
    public final Set<Config.OptionPriority> a(@j.N Config.a<?> aVar) {
        Map<Config.OptionPriority, Object> map = this.f24173F.get(aVar);
        return map == null ? Collections.emptySet() : Collections.unmodifiableSet(map.keySet());
    }

    @Override // androidx.camera.core.impl.Config
    public final void b(@j.N Ba1.B b12) {
        for (Map.Entry<Config.a<?>, Map<Config.OptionPriority, Object>> entry : this.f24173F.tailMap(Config.a.a(Void.class, "camera2.captureRequest.option.")).entrySet()) {
            if (!entry.getKey().c().startsWith("camera2.captureRequest.option.")) {
                return;
            }
            Config.a<?> key = entry.getKey();
            h.a aVar = (h.a) b12.f1488c;
            Config config = (Config) b12.f1489d;
            aVar.f2840a.T(key, config.i(key), config.f(key));
        }
    }

    @Override // androidx.camera.core.impl.Config
    public final boolean c(@j.N Config.a<?> aVar) {
        return this.f24173F.containsKey(aVar);
    }

    @Override // androidx.camera.core.impl.Config
    @j.P
    public final <ValueT> ValueT d(@j.N Config.a<ValueT> aVar, @j.N Config.OptionPriority optionPriority) {
        Map<Config.OptionPriority, Object> map = this.f24173F.get(aVar);
        if (map == null) {
            throw new IllegalArgumentException("Option does not exist: " + aVar);
        }
        if (map.containsKey(optionPriority)) {
            return (ValueT) map.get(optionPriority);
        }
        throw new IllegalArgumentException("Option does not exist: " + aVar + " with priority=" + optionPriority);
    }

    @Override // androidx.camera.core.impl.Config
    @j.N
    public final Set<Config.a<?>> e() {
        return Collections.unmodifiableSet(this.f24173F.keySet());
    }

    @Override // androidx.camera.core.impl.Config
    @j.P
    public final <ValueT> ValueT f(@j.N Config.a<ValueT> aVar) {
        Map<Config.OptionPriority, Object> map = this.f24173F.get(aVar);
        if (map != null) {
            return (ValueT) map.get((Config.OptionPriority) Collections.min(map.keySet()));
        }
        throw new IllegalArgumentException("Option does not exist: " + aVar);
    }

    @Override // androidx.camera.core.impl.Config
    @j.P
    public final <ValueT> ValueT g(@j.N Config.a<ValueT> aVar, @j.P ValueT valuet) {
        try {
            return (ValueT) f(aVar);
        } catch (IllegalArgumentException unused) {
            return valuet;
        }
    }

    @Override // androidx.camera.core.impl.Config
    @j.N
    public final Config.OptionPriority i(@j.N Config.a<?> aVar) {
        Map<Config.OptionPriority, Object> map = this.f24173F.get(aVar);
        if (map != null) {
            return (Config.OptionPriority) Collections.min(map.keySet());
        }
        throw new IllegalArgumentException("Option does not exist: " + aVar);
    }
}
