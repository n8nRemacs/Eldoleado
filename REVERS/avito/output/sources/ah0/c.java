package aH0;

import com.avito.android.remote.model.category_parameters.AddressParameter;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: AddressEditInfoResult.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0004\b\u0004\u0010\u0005R\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"LaH0/c;", "", "LaH0/f;", AddressParameter.TYPE, "<init>", "(LaH0/f;)V", "LaH0/f;", "a", "()LaH0/f;", "_avito_user-address_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final /* data */ class c {

    @com.google.gson.annotations.c(AddressParameter.TYPE)
    @Y61.k
    private final f address;

    public c(@Y61.k f fVar) {
        this.address = fVar;
    }

    @Y61.k
    /* renamed from: a, reason: from getter */
    public final f getAddress() {
        return this.address;
    }

    public final boolean equals(@Y61.l Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof c) && L.f(this.address, ((c) obj).address);
    }

    public final int hashCode() {
        return this.address.hashCode();
    }

    @Y61.k
    public final String toString() {
        return "AddressEditInfoResult(address=" + this.address + ')';
    }
}
