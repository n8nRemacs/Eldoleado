package androidx.camera.core.impl;

import androidx.camera.core.impl.Config;
import java.util.Set;

/* compiled from: ReadableConfig.java */
@j.X
/* loaded from: classes.dex */
public interface x0 extends Config {
    @Override // androidx.camera.core.impl.Config
    @j.N
    default Set<Config.OptionPriority> a(@j.N Config.a<?> aVar) {
        return getConfig().a(aVar);
    }

    @Override // androidx.camera.core.impl.Config
    default void b(@j.N Ba1.B b12) {
        getConfig().b(b12);
    }

    @Override // androidx.camera.core.impl.Config
    default boolean c(@j.N Config.a<?> aVar) {
        return getConfig().c(aVar);
    }

    @Override // androidx.camera.core.impl.Config
    @j.P
    default <ValueT> ValueT d(@j.N Config.a<ValueT> aVar, @j.N Config.OptionPriority optionPriority) {
        return (ValueT) getConfig().d(aVar, optionPriority);
    }

    @Override // androidx.camera.core.impl.Config
    @j.N
    default Set<Config.a<?>> e() {
        return getConfig().e();
    }

    @Override // androidx.camera.core.impl.Config
    @j.P
    default <ValueT> ValueT f(@j.N Config.a<ValueT> aVar) {
        return (ValueT) getConfig().f(aVar);
    }

    @Override // androidx.camera.core.impl.Config
    @j.P
    default <ValueT> ValueT g(@j.N Config.a<ValueT> aVar, @j.P ValueT valuet) {
        return (ValueT) getConfig().g(aVar, valuet);
    }

    @j.N
    Config getConfig();

    @Override // androidx.camera.core.impl.Config
    @j.N
    default Config.OptionPriority i(@j.N Config.a<?> aVar) {
        return getConfig().i(aVar);
    }
}
