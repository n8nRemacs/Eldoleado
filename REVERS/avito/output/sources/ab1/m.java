package ab1;

import com.avito.android.beduin.common.component.input.BeduinInputModel;
import kotlin.Z;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@DebugMetadata(c = "ru.rustore.sdk.pushclient.internal.data.repository.RegisterPushTokenRepository", f = "RegisterPushTokenRepository.kt", i = {}, l = {11}, m = "registerForPushes-iRXhn8M", n = {}, s = {})
/* loaded from: classes9.dex */
public final class m extends ContinuationImpl {

    /* renamed from: q, reason: collision with root package name */
    public /* synthetic */ Object f21047q;

    /* renamed from: r, reason: collision with root package name */
    public final /* synthetic */ n f21048r;

    /* renamed from: s, reason: collision with root package name */
    public int f21049s;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public m(n nVar, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f21048r = nVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Y61.l
    public final Object invokeSuspend(@Y61.k Object obj) {
        this.f21047q = obj;
        this.f21049s |= BeduinInputModel.MIN_TEXT_VERSION;
        Object objA = this.f21048r.a(null, this);
        return objA == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objA : Z.a(objA);
    }
}
