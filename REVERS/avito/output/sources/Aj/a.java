package AJ;

import Y61.k;
import android.content.Context;
import com.avito.avcalls.rtc.connecting.RtcException;
import com.avito.avcalls.rtc.media.e;
import com.avito.avcalls.rtc.media.j;
import com.avito.avcalls.rtc.media.l;
import kotlin.G0;
import kotlin.Metadata;
import org.webrtc.DefaultVideoDecoderFactory;
import org.webrtc.DefaultVideoEncoderFactory;
import org.webrtc.EglBase;
import org.webrtc.Logging;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;
import uJ.C48908a;
import xJ.C49846a;
import yJ.C50120a;
import zJ.C50475a;
import zJ.f;
import zJ.m;

/* compiled from: PeerConnectionFactoryImpl.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAJ/a;", "Lcom/avito/avcalls/rtc/peer_connection/b;", "_avito_iac-avcalls_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes13.dex */
public final class a implements com.avito.avcalls.rtc.peer_connection.b {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final PeerConnectionFactory f279a;

    public a(@k Context context) {
        PeerConnectionFactory.initialize(PeerConnectionFactory.InitializationOptions.builder(context).setInjectableLogger(new C50120a(), Logging.Severity.LS_VERBOSE).createInitializationOptions());
        PeerConnectionFactory.Builder builder = PeerConnectionFactory.builder();
        builder.setOptions(new PeerConnectionFactory.Options());
        EglBase eglBaseA = C49846a.f442334a.a();
        builder.setVideoEncoderFactory(new DefaultVideoEncoderFactory(eglBaseA.getEglBaseContext(), true, false));
        builder.setVideoDecoderFactory(new DefaultVideoDecoderFactory(eglBaseA.getEglBaseContext()));
        this.f279a = builder.createPeerConnectionFactory();
    }

    @Override // com.avito.avcalls.rtc.peer_connection.b
    @k
    public final com.avito.avcalls.rtc.media.a A(@k e eVar) {
        return new C50475a(this.f279a.createAudioSource(((f) eVar).f444016a));
    }

    @Override // com.avito.avcalls.rtc.peer_connection.b
    @k
    public final l C(@k j jVar) {
        return new m(this.f279a.createVideoTrack("selfVideo", ((zJ.k) jVar).f444018a));
    }

    @Override // com.avito.avcalls.rtc.peer_connection.b
    @k
    public final com.avito.avcalls.rtc.peer_connection.a E(@k String str, @k com.avito.avcalls.rtc.connecting.b bVar, @k Y41.l<? super com.avito.avcalls.rtc.connecting.a, G0> lVar) throws RtcException.CantCreatePeerConnectionException {
        vJ.d.f440674a.getClass();
        PeerConnection peerConnectionCreatePeerConnection = this.f279a.createPeerConnection(vJ.d.a(bVar), new C48908a(lVar));
        if (peerConnectionCreatePeerConnection != null) {
            return new c(str, peerConnectionCreatePeerConnection);
        }
        throw new RtcException.CantCreatePeerConnectionException();
    }

    @Override // com.avito.avcalls.rtc.peer_connection.b
    @k
    public final j h() {
        return new zJ.k(this.f279a.createVideoSource(false));
    }

    @Override // com.avito.avcalls.rtc.peer_connection.b
    @k
    public final com.avito.avcalls.rtc.media.b t(@k com.avito.avcalls.rtc.media.a aVar) {
        return new zJ.c(this.f279a.createAudioTrack("selfAudio", ((C50475a) aVar).f444013a));
    }

    @Override // com.avito.avcalls.rtc.peer_connection.b
    @k
    public final e x() {
        return new f(null, 1, null);
    }
}
