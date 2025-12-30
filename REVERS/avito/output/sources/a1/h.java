package A1;

import Y61.k;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import kotlin.Metadata;
import kotlin.collections.C42745f0;
import kotlin.jvm.internal.s0;

/* compiled from: ViewModelImpl.kt */
@s0
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001B\t\b\u0016¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LA1/h;", "", "<init>", "()V", "lifecycle-viewmodel_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class h {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final e f39a;

    /* renamed from: b, reason: collision with root package name */
    @k
    public final LinkedHashMap f40b;

    /* renamed from: c, reason: collision with root package name */
    @k
    public final LinkedHashSet f41c;

    /* renamed from: d, reason: collision with root package name */
    public volatile boolean f42d;

    public h() {
        this.f39a = new e();
        this.f40b = new LinkedHashMap();
        this.f41c = new LinkedHashSet();
    }

    public static void a(AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (Exception e12) {
                throw new RuntimeException(e12);
            }
        }
    }

    public h(@k AutoCloseable... autoCloseableArr) {
        this.f39a = new e();
        this.f40b = new LinkedHashMap();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        this.f41c = linkedHashSet;
        C42745f0.i(linkedHashSet, autoCloseableArr);
    }
}
