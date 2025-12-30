package af0;

import Y61.k;
import androidx.compose.runtime.internal.P;
import com.avito.android.publish.InterfaceC33544d;
import com.avito.android.publish.slots.contact_info.ContactsData;
import com.avito.android.publish.slots.contact_info.d;
import com.avito.android.remote.model.category_parameters.CategoryParameters;
import com.avito.android.remote.model.category_parameters.PhoneParameter;
import com.avito.android.util.InterfaceC35945t1;
import com.avito.android.util.N3;
import javax.inject.Inject;
import kotlin.Metadata;
import kotlin.jvm.internal.s0;

/* compiled from: PhoneVerificationDataProvider.kt */
@P
@s0
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Laf0/b;", "Laf0/a;", "_avito_publish_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: af0.b, reason: case insensitive filesystem */
/* loaded from: classes16.dex */
public final class C19883b implements InterfaceC19882a {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final InterfaceC33544d f21112a;

    /* renamed from: b, reason: collision with root package name */
    @k
    public final com.avito.android.details.a f21113b;

    /* renamed from: c, reason: collision with root package name */
    @k
    public final InterfaceC35945t1<String> f21114c;

    @Inject
    public C19883b(@k InterfaceC33544d interfaceC33544d, @k com.avito.android.details.a aVar, @N3.c @k InterfaceC35945t1<String> interfaceC35945t1) {
        this.f21112a = interfaceC33544d;
        this.f21113b = aVar;
        this.f21114c = interfaceC35945t1;
    }

    @Override // af0.InterfaceC19882a
    @k
    public final d.b a() {
        String strA;
        PhoneParameter phoneParameter;
        ContactsData contactsDataB = this.f21112a.b();
        CategoryParameters categoryParametersC0 = this.f21113b.C0();
        String value = (categoryParametersC0 == null || (phoneParameter = (PhoneParameter) categoryParametersC0.getFirstParameterOfType(PhoneParameter.class)) == null) ? null : phoneParameter.getValue();
        if (value == null || (strA = this.f21114c.a(value)) == null) {
            strA = "";
        }
        return new d.b(strA, contactsDataB != null ? contactsDataB.f243209e : null, contactsDataB != null ? contactsDataB.f243212h : false);
    }
}
