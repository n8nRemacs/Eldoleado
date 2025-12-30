package ab1;

import com.avito.android.beduin.common.component.input.BeduinInputModel;
import kotlin.Z;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@DebugMetadata(c = "ru.rustore.sdk.pushclient.internal.data.repository.VkpnsTopicRepository", f = "VkpnsTopicRepository.kt", i = {}, l = {32}, m = "unsubscribeFromTopic-gIAlu-s", n = {}, s = {})
/* loaded from: classes9.dex */
public final class t extends ContinuationImpl {

    /* renamed from: q, reason: collision with root package name */
    public /* synthetic */ Object f21065q;

    /* renamed from: r, reason: collision with root package name */
    public final /* synthetic */ v f21066r;

    /* renamed from: s, reason: collision with root package name */
    public int f21067s;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public t(v vVar, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f21066r = vVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Y61.l
    public final Object invokeSuspend(@Y61.k Object obj) {
        this.f21065q = obj;
        this.f21067s |= BeduinInputModel.MIN_TEXT_VERSION;
        Object objB = this.f21066r.b(null, this);
        return objB == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objB : Z.a(objB);
    }
}
