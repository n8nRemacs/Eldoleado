package A60;

import Ju.InterfaceC14249c;
import Y61.k;
import Y61.l;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;
import kotlin.jvm.internal.L;

/* compiled from: DeeplinkProcessingResult.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\b6\u0018\u00002\u00020\u0001:\u0001\u0004B\t\b\u0004¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, d2 = {"LA60/a;", "", "<init>", "()V", "a", "_avito_payment-core_public"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes15.dex */
public abstract class a {

    /* compiled from: DeeplinkProcessingResult.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LA60/a$a;", "LJu/c$b;", "_avito_payment-core_public"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: A60.a$a, reason: collision with other inner class name */
    public static final /* data */ class C0007a implements InterfaceC14249c.b {

        /* renamed from: b, reason: collision with root package name */
        @k
        public final b f104b;

        public C0007a(@k b bVar) {
            this.f104b = bVar;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof C0007a) && L.f(this.f104b, ((C0007a) obj).f104b);
        }

        public final int hashCode() {
            return this.f104b.hashCode();
        }

        @k
        public final String toString() {
            return "Result(processingResult=" + this.f104b + ')';
        }
    }

    public /* synthetic */ a(C42822w c42822w) {
        this();
    }

    public a() {
    }
}
