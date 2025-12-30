package androidx.camera.core.impl;

import android.util.Size;

@VY0.c
@j.X
/* loaded from: classes.dex */
public abstract class SurfaceConfig {

    public enum ConfigSize {
        VGA(0),
        s720p(1),
        PREVIEW(2),
        s1440p(3),
        RECORD(4),
        MAXIMUM(5),
        ULTRA_MAXIMUM(6),
        NOT_SUPPORT(7);


        /* renamed from: b, reason: collision with root package name */
        public final int f24040b;

        ConfigSize(int i12) {
            this.f24040b = i12;
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public static final class ConfigType {

        /* renamed from: b, reason: collision with root package name */
        public static final ConfigType f24041b;

        /* renamed from: c, reason: collision with root package name */
        public static final ConfigType f24042c;

        /* renamed from: d, reason: collision with root package name */
        public static final ConfigType f24043d;

        /* renamed from: e, reason: collision with root package name */
        public static final ConfigType f24044e;

        /* renamed from: f, reason: collision with root package name */
        public static final /* synthetic */ ConfigType[] f24045f;

        static {
            ConfigType configType = new ConfigType("PRIV", 0);
            f24041b = configType;
            ConfigType configType2 = new ConfigType("YUV", 1);
            f24042c = configType2;
            ConfigType configType3 = new ConfigType("JPEG", 2);
            f24043d = configType3;
            ConfigType configType4 = new ConfigType("RAW", 3);
            f24044e = configType4;
            f24045f = new ConfigType[]{configType, configType2, configType3, configType4};
        }

        public ConfigType() {
            throw null;
        }

        public static ConfigType valueOf(String str) {
            return (ConfigType) Enum.valueOf(ConfigType.class, str);
        }

        public static ConfigType[] values() {
            return (ConfigType[]) f24045f.clone();
        }
    }

    @j.N
    public static SurfaceConfig a(@j.N ConfigType configType, @j.N ConfigSize configSize) {
        return new C20107n(configType, configSize, 0L);
    }

    @j.N
    public static SurfaceConfig b(@j.N ConfigType configType, @j.N ConfigSize configSize, long j12) {
        return new C20107n(configType, configSize, j12);
    }

    @j.N
    public static SurfaceConfig f(int i12, int i13, @j.N Size size, @j.N I0 i02) {
        ConfigType configType = i13 == 35 ? ConfigType.f24042c : i13 == 256 ? ConfigType.f24043d : i13 == 32 ? ConfigType.f24044e : ConfigType.f24041b;
        ConfigSize configSize = ConfigSize.NOT_SUPPORT;
        int iA2 = androidx.camera.core.internal.utils.c.a(size);
        if (i12 == 1) {
            if (iA2 <= androidx.camera.core.internal.utils.c.a(((C20108o) i02).f24164b.get(Integer.valueOf(i13)))) {
                configSize = ConfigSize.s720p;
            } else {
                if (iA2 <= androidx.camera.core.internal.utils.c.a(((C20108o) i02).f24166d.get(Integer.valueOf(i13)))) {
                    configSize = ConfigSize.s1440p;
                }
            }
        } else if (iA2 <= androidx.camera.core.internal.utils.c.a(((C20108o) i02).f24163a)) {
            configSize = ConfigSize.VGA;
        } else {
            C20108o c20108o = (C20108o) i02;
            if (iA2 <= androidx.camera.core.internal.utils.c.a(c20108o.f24165c)) {
                configSize = ConfigSize.PREVIEW;
            } else if (iA2 <= androidx.camera.core.internal.utils.c.a(c20108o.f24167e)) {
                configSize = ConfigSize.RECORD;
            } else if (iA2 <= androidx.camera.core.internal.utils.c.a(i02.c().get(Integer.valueOf(i13)))) {
                configSize = ConfigSize.MAXIMUM;
            } else {
                Size size2 = c20108o.f24169g.get(Integer.valueOf(i13));
                if (size2 != null) {
                    if (iA2 <= size2.getHeight() * size2.getWidth()) {
                        configSize = ConfigSize.ULTRA_MAXIMUM;
                    }
                }
            }
        }
        return a(configType, configSize);
    }

    @j.N
    public abstract ConfigSize c();

    @j.N
    public abstract ConfigType d();

    public abstract long e();
}
