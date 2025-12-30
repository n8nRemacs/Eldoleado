package ab1;

import kotlin.C42729a0;
import kotlin.G0;
import kotlin.Z;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlinx.coroutines.T;

@DebugMetadata(c = "ru.rustore.sdk.pushclient.internal.data.repository.VkpnsTopicRepository$subscribeToTopic$2", f = "VkpnsTopicRepository.kt", i = {}, l = {20, 23}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes9.dex */
public final class s extends SuspendLambda implements Y41.p<T, Continuation<? super Z<? extends G0>>, Object> {

    /* renamed from: q, reason: collision with root package name */
    public int f21063q;

    /* renamed from: r, reason: collision with root package name */
    public final /* synthetic */ v f21064r;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public s(v vVar, Continuation continuation) {
        super(2, continuation);
        this.f21064r = vVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<G0> create(Object obj, Continuation<?> continuation) {
        return new s(this.f21064r, continuation);
    }

    @Override // Y41.p
    public final Object invoke(T t12, Continuation<? super Z<? extends G0>> continuation) {
        return new s(this.f21064r, continuation).invokeSuspend(G0.f406611a);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object bVar;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i12 = this.f21063q;
        v vVar = this.f21064r;
        if (i12 == 0) {
            C42729a0.b(obj);
            qb1.g gVar = vVar.f21072b;
            this.f21063q = 1;
            obj = gVar.a(this);
            if (obj == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i12 != 1) {
                if (i12 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                C42729a0.b(obj);
                bVar = ((Z) obj).f406625b;
                return Z.a(bVar);
            }
            C42729a0.b(obj);
        }
        String str = (String) obj;
        if (str != null) {
            vVar.f21074d.info("Start subscribe to topic null");
            this.f21063q = 2;
            bVar = vVar.f21071a.a(str, this);
            if (bVar == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            vVar.f21074d.a("Unable to subscribe to topic, token is not exists. You need to get Push Token before use subscribeToTopic", null);
            int i13 = Z.f406624c;
            bVar = new Z.b(new Exception("Unable to subscribe to topic, token is not exists. You need to get Push Token before use subscribeToTopic"));
        }
        return Z.a(bVar);
    }
}
