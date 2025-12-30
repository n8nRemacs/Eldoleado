package A31;

import kotlin.G0;
import kotlin.Q;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.InterfaceC43172j;
import kotlinx.coroutines.flow.Z1;

/* loaded from: classes8.dex */
public final class c implements InterfaceC43172j {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ e f81b;

    public c(e eVar) {
        this.f81b = eVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlinx.coroutines.flow.InterfaceC43172j
    public final Object emit(Object obj, Continuation continuation) {
        Object value;
        Q q12 = (Q) obj;
        if (q12 == null) {
            return G0.f406611a;
        }
        e eVar = this.f81b;
        eVar.getClass();
        String str = (String) q12.f406619b;
        Integer num = (Integer) q12.f406620c;
        Z1 z12 = eVar.f85J;
        ((b) z12.getValue()).getClass();
        b bVar = new b(num, str);
        do {
            value = z12.getValue();
        } while (!z12.N3(value, bVar));
        return G0.f406611a;
    }
}
