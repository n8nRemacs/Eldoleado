package androidx.camera.core.impl;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
@j.X
/* loaded from: classes.dex */
public final class Timebase {

    /* renamed from: b, reason: collision with root package name */
    public static final Timebase f24048b;

    /* renamed from: c, reason: collision with root package name */
    public static final Timebase f24049c;

    /* renamed from: d, reason: collision with root package name */
    public static final /* synthetic */ Timebase[] f24050d;

    static {
        Timebase timebase = new Timebase("UPTIME", 0);
        f24048b = timebase;
        Timebase timebase2 = new Timebase("REALTIME", 1);
        f24049c = timebase2;
        f24050d = new Timebase[]{timebase, timebase2};
    }

    public Timebase() {
        throw null;
    }

    public static Timebase valueOf(String str) {
        return (Timebase) Enum.valueOf(Timebase.class, str);
    }

    public static Timebase[] values() {
        return (Timebase[]) f24050d.clone();
    }
}
