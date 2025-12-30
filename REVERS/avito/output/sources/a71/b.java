package A71;

import Y61.k;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.InterfaceC42819t;
import org.koin.mp.d;

/* compiled from: KClassExt.kt */
@Metadata(d1 = {"\u0000\u0002\n\u0000¨\u0006\u0000"}, d2 = {"koin-core"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class b {

    /* renamed from: a, reason: collision with root package name */
    @k
    public static final ConcurrentHashMap f127a;

    static {
        d.f421487a.getClass();
        f127a = new ConcurrentHashMap();
    }

    @k
    public static final String a(@k kotlin.reflect.d<?> dVar) {
        ConcurrentHashMap concurrentHashMap = f127a;
        String str = (String) concurrentHashMap.get(dVar);
        if (str != null) {
            return str;
        }
        d.f421487a.getClass();
        String name = ((InterfaceC42819t) dVar).f().getName();
        concurrentHashMap.put(dVar, name);
        return name;
    }
}
