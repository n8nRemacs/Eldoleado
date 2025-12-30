package ab1;

import Za1.C;
import com.huawei.hms.adapter.internal.AvailableCode;
import com.vk.push.core.test.SendTestPushResult;
import com.vk.push.core.test.TestPushPayload;
import kotlin.C42729a0;
import kotlin.G0;
import kotlin.Z;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlinx.coroutines.T;

@DebugMetadata(c = "ru.rustore.sdk.pushclient.internal.data.repository.TestPushRepository$sendTestPush$2", f = "TestPushRepository.kt", i = {}, l = {AvailableCode.USER_IGNORE_PREVIOUS_POPUP, AvailableCode.APP_IS_BACKGROUND_OR_LOCKED}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes9.dex */
public final class p extends SuspendLambda implements Y41.p<T, Continuation<? super Z<? extends SendTestPushResult>>, Object> {

    /* renamed from: q, reason: collision with root package name */
    public int f21054q;

    /* renamed from: r, reason: collision with root package name */
    public final /* synthetic */ q f21055r;

    /* renamed from: s, reason: collision with root package name */
    public final /* synthetic */ String f21056s;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public p(q qVar, String str, Continuation continuation) {
        super(2, continuation);
        this.f21055r = qVar;
        this.f21056s = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<G0> create(Object obj, Continuation<?> continuation) {
        return new p(this.f21055r, this.f21056s, continuation);
    }

    @Override // Y41.p
    public final Object invoke(T t12, Continuation<? super Z<? extends SendTestPushResult>> continuation) {
        return ((p) create(t12, continuation)).invokeSuspend(G0.f406611a);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object objO;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i12 = this.f21054q;
        String str = this.f21056s;
        if (i12 == 0) {
            C42729a0.b(obj);
            q qVar = this.f21055r;
            qVar.f21059c.info("send test push to token = " + com.vk.push.core.utils.q.a(str));
            this.f21054q = 1;
            obj = qVar.f21057a.invoke(this);
            if (obj == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i12 != 1) {
                if (i12 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                C42729a0.b(obj);
                objO = ((Z) obj).f406625b;
                return Z.a(objO);
            }
            C42729a0.b(obj);
        }
        TestPushPayload testPushPayload = new TestPushPayload(null, null, null, null);
        this.f21054q = 2;
        objO = ((C) obj).o(str, testPushPayload, this);
        if (objO == coroutine_suspended) {
            return coroutine_suspended;
        }
        return Z.a(objO);
    }
}
