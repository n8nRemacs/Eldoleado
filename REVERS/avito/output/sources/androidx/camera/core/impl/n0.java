package androidx.camera.core.impl;

import android.util.ArrayMap;
import androidx.camera.core.impl.Config;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/* compiled from: MutableOptionsBundle.java */
@j.X
/* loaded from: classes.dex */
public final class n0 extends s0 implements InterfaceC20106m0 {
    public n0() {
        throw null;
    }

    @j.N
    public static n0 R() {
        return new n0(new TreeMap(s0.f24171G));
    }

    @j.N
    public static n0 S(@j.N Config config) {
        TreeMap treeMap = new TreeMap(s0.f24171G);
        for (Config.a<?> aVar : config.e()) {
            Set<Config.OptionPriority> setA = config.a(aVar);
            ArrayMap arrayMap = new ArrayMap();
            for (Config.OptionPriority optionPriority : setA) {
                arrayMap.put(optionPriority, config.d(aVar, optionPriority));
            }
            treeMap.put(aVar, arrayMap);
        }
        return new n0(treeMap);
    }

    @Override // androidx.camera.core.impl.InterfaceC20106m0
    public final <ValueT> void K(@j.N Config.a<ValueT> aVar, @j.P ValueT valuet) {
        T(aVar, Config.OptionPriority.f23921d, valuet);
    }

    public final <ValueT> void T(@j.N Config.a<ValueT> aVar, @j.N Config.OptionPriority optionPriority, @j.P ValueT valuet) {
        Config.OptionPriority optionPriority2;
        Config.OptionPriority optionPriority3;
        TreeMap<Config.a<?>, Map<Config.OptionPriority, Object>> treeMap = this.f24173F;
        Map<Config.OptionPriority, Object> map = treeMap.get(aVar);
        if (map == null) {
            ArrayMap arrayMap = new ArrayMap();
            treeMap.put(aVar, arrayMap);
            arrayMap.put(optionPriority, valuet);
            return;
        }
        Config.OptionPriority optionPriority4 = (Config.OptionPriority) Collections.min(map.keySet());
        if (Objects.equals(map.get(optionPriority4), valuet) || !((optionPriority4 == (optionPriority2 = Config.OptionPriority.f23919b) && optionPriority == optionPriority2) || (optionPriority4 == (optionPriority3 = Config.OptionPriority.f23920c) && optionPriority == optionPriority3))) {
            map.put(optionPriority, valuet);
            return;
        }
        throw new IllegalArgumentException("Option values conflicts: " + aVar.c() + ", existing value (" + optionPriority4 + ")=" + map.get(optionPriority4) + ", conflicting (" + optionPriority + ")=" + valuet);
    }
}
