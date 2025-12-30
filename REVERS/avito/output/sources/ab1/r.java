package ab1;

import com.avito.android.beduin.common.component.input.BeduinInputModel;
import kotlin.Z;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@DebugMetadata(c = "ru.rustore.sdk.pushclient.internal.data.repository.VkpnsTopicRepository", f = "VkpnsTopicRepository.kt", i = {}, l = {19}, m = "subscribeToTopic-gIAlu-s", n = {}, s = {})
/* loaded from: classes9.dex */
public final class r extends ContinuationImpl {

    /* renamed from: q, reason: collision with root package name */
    public /* synthetic */ Object f21060q;

    /* renamed from: r, reason: collision with root package name */
    public final /* synthetic */ v f21061r;

    /* renamed from: s, reason: collision with root package name */
    public int f21062s;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public r(v vVar, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f21061r = vVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Y61.l
    public final Object invokeSuspend(@Y61.k Object obj) {
        this.f21060q = obj;
        this.f21062s |= BeduinInputModel.MIN_TEXT_VERSION;
        Object objA = this.f21061r.a(this);
        return objA == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objA : Z.a(objA);
    }
}
