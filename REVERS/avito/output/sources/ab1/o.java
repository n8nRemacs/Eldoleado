package ab1;

import com.avito.android.beduin.common.component.input.BeduinInputModel;
import com.huawei.hms.adapter.internal.AvailableCode;
import kotlin.Z;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@DebugMetadata(c = "ru.rustore.sdk.pushclient.internal.data.repository.TestPushRepository", f = "TestPushRepository.kt", i = {}, l = {AvailableCode.ERROR_ON_ACTIVITY_RESULT}, m = "sendTestPush-0E7RQCE", n = {}, s = {})
/* loaded from: classes9.dex */
public final class o extends ContinuationImpl {

    /* renamed from: q, reason: collision with root package name */
    public /* synthetic */ Object f21051q;

    /* renamed from: r, reason: collision with root package name */
    public final /* synthetic */ q f21052r;

    /* renamed from: s, reason: collision with root package name */
    public int f21053s;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public o(q qVar, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f21052r = qVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Y61.l
    public final Object invokeSuspend(@Y61.k Object obj) {
        this.f21051q = obj;
        this.f21053s |= BeduinInputModel.MIN_TEXT_VERSION;
        Object objA = this.f21052r.a(null, this);
        return objA == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objA : Z.a(objA);
    }
}
