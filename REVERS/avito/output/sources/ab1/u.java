package ab1;

import kotlin.C42729a0;
import kotlin.G0;
import kotlin.Z;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlinx.coroutines.T;

@DebugMetadata(c = "ru.rustore.sdk.pushclient.internal.data.repository.VkpnsTopicRepository$unsubscribeFromTopic$2", f = "VkpnsTopicRepository.kt", i = {}, l = {33, 36}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes9.dex */
public final class u extends SuspendLambda implements Y41.p<T, Continuation<? super Z<? extends G0>>, Object> {

    /* renamed from: q, reason: collision with root package name */
    public int f21068q;

    /* renamed from: r, reason: collision with root package name */
    public final /* synthetic */ v f21069r;

    /* renamed from: s, reason: collision with root package name */
    public final /* synthetic */ String f21070s;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public u(v vVar, String str, Continuation<? super u> continuation) {
        super(2, continuation);
        this.f21069r = vVar;
        this.f21070s = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<G0> create(Object obj, Continuation<?> continuation) {
        return new u(this.f21069r, this.f21070s, continuation);
    }

    @Override // Y41.p
    public final Object invoke(T t12, Continuation<? super Z<? extends G0>> continuation) {
        return new u(this.f21069r, this.f21070s, continuation).invokeSuspend(G0.f406611a);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object bVar;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i12 = this.f21068q;
        v vVar = this.f21069r;
        if (i12 == 0) {
            C42729a0.b(obj);
            qb1.g gVar = vVar.f21072b;
            this.f21068q = 1;
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
            T11.f fVar = vVar.f21074d;
            StringBuilder sb2 = new StringBuilder("Start unsubscribe from topic ");
            String str2 = this.f21070s;
            sb2.append(str2);
            fVar.info(sb2.toString());
            this.f21068q = 2;
            bVar = vVar.f21071a.b(str, str2, this);
            if (bVar == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            vVar.f21074d.a("Unable to unsubscribe from topic, token is not exists. You need to get Push Token before use unsubscribeFromTopic", null);
            int i13 = Z.f406624c;
            bVar = new Z.b(new Exception("Unable to unsubscribe from topic, token is not exists. You need to get Push Token before use unsubscribeFromTopic"));
        }
        return Z.a(bVar);
    }
}
