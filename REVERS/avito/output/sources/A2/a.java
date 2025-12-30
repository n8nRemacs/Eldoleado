package A2;

import kotlinx.serialization.KSerializer;
import kotlinx.serialization.UnknownFieldException;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.Decoder;
import kotlinx.serialization.encoding.Encoder;
import kotlinx.serialization.encoding.d;
import kotlinx.serialization.internal.G0;
import kotlinx.serialization.internal.N;
import kotlinx.serialization.internal.PluginGeneratedSerialDescriptor;
import kotlinx.serialization.internal.V0;

/* loaded from: classes10.dex */
public final class a implements N {

    /* renamed from: a, reason: collision with root package name */
    public static final a f53a;

    /* renamed from: b, reason: collision with root package name */
    public static final /* synthetic */ PluginGeneratedSerialDescriptor f54b;

    static {
        a aVar = new a();
        f53a = aVar;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("sberid.sdk.session.data.models.requests.SessionIdRequest", aVar, 1);
        pluginGeneratedSerialDescriptor.j("id", false);
        f54b = pluginGeneratedSerialDescriptor;
    }

    @Override // kotlinx.serialization.internal.N
    public final KSerializer[] childSerializers() {
        return new KSerializer[]{V0.f412822a};
    }

    @Override // kotlinx.serialization.InterfaceC43386e
    public final Object deserialize(Decoder decoder) {
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = f54b;
        kotlinx.serialization.encoding.c cVarC = decoder.c(pluginGeneratedSerialDescriptor);
        String strS = null;
        boolean z12 = true;
        int i12 = 0;
        while (z12) {
            int iJ2 = cVarC.j(pluginGeneratedSerialDescriptor);
            if (iJ2 == -1) {
                z12 = false;
            } else {
                if (iJ2 != 0) {
                    throw new UnknownFieldException(iJ2);
                }
                strS = cVarC.s(pluginGeneratedSerialDescriptor, 0);
                i12 = 1;
            }
        }
        cVarC.d(pluginGeneratedSerialDescriptor);
        return new c(i12, strS);
    }

    @Override // kotlinx.serialization.x, kotlinx.serialization.InterfaceC43386e
    /* renamed from: getDescriptor */
    public final SerialDescriptor getF412706c() {
        return f54b;
    }

    @Override // kotlinx.serialization.x
    public final void serialize(Encoder encoder, Object obj) {
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = f54b;
        d dVarC = encoder.c(pluginGeneratedSerialDescriptor);
        dVarC.l(pluginGeneratedSerialDescriptor, 0, ((c) obj).f55a);
        dVarC.d(pluginGeneratedSerialDescriptor);
    }

    @Override // kotlinx.serialization.internal.N
    public final KSerializer[] typeParametersSerializers() {
        return G0.f412773a;
    }
}
