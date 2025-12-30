package A70;

import Y61.k;
import com.avito.android.social.D;
import com.avito.android.social.InterfaceC35017i;
import com.avito.android.social.InterfaceC35029v;
import com.avito.android.social.S;
import com.avito.android.social.X;
import hD.C40806a;
import java.util.ArrayList;
import javax.inject.Inject;
import kotlin.Metadata;
import kotlin.collections.C42756l;
import kotlin.jvm.internal.s0;

/* compiled from: SocialLogoutManager.kt */
@s0
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\b\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LA70/a;", "", "_avito_phone-reverification-info_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes15.dex */
public final class a {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final ArrayList f121a;

    @Inject
    public a(@k InterfaceC35017i interfaceC35017i, @k InterfaceC35029v interfaceC35029v, @k X x12, @k S s5, @k C40806a c40806a) {
        this.f121a = C42756l.B(new D[]{interfaceC35017i, interfaceC35029v, x12, c40806a.w().invoke().booleanValue() ? s5 : null});
    }
}
