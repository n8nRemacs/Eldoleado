package AJ;

import Y61.k;
import Y61.l;
import com.avito.avcalls.rtc.connecting.IceCandidate;
import com.avito.avcalls.rtc.connecting.Sdp;
import com.avito.avcalls.rtc.media.e;
import com.avito.avcalls.rtc.media.h;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.G0;
import kotlin.Metadata;
import kotlin.jvm.internal.L;
import kotlin.jvm.internal.s0;
import org.webrtc.DtmfSender;
import org.webrtc.MediaStreamTrack;
import org.webrtc.PeerConnection;
import org.webrtc.RtpReceiver;
import org.webrtc.RtpSender;
import org.webrtc.RtpTransceiver;
import org.webrtc.VideoTrack;
import vJ.C49221a;
import vJ.f;
import vJ.g;
import zJ.j;
import zJ.m;

/* compiled from: PeerConnectionImpl.kt */
@s0
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAJ/c;", "Lcom/avito/avcalls/rtc/peer_connection/a;", "_avito_iac-avcalls_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes13.dex */
public final /* data */ class c implements com.avito.avcalls.rtc.peer_connection.a {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final String f282a;

    /* renamed from: b, reason: collision with root package name */
    @k
    public final PeerConnection f283b;

    public c(@k String str, @k PeerConnection peerConnection) {
        this.f282a = str;
        this.f283b = peerConnection;
    }

    @Override // com.avito.avcalls.rtc.peer_connection.a
    @l
    public final h B(@k com.avito.avcalls.rtc.media.l lVar, @k ArrayList arrayList) {
        RtpSender rtpSenderAddTrack = this.f283b.addTrack(((m) lVar.g()).f444019a, arrayList);
        if (rtpSenderAddTrack != null) {
            return new j(rtpSenderAddTrack);
        }
        return null;
    }

    @Override // com.avito.avcalls.rtc.peer_connection.a
    public final void D(@k com.avito.avcalls.rtc.connecting.c cVar, @k e eVar) {
        g.f440678a.getClass();
        this.f283b.createAnswer(new f(cVar), ((zJ.f) eVar).f444016a);
    }

    @Override // com.avito.avcalls.rtc.peer_connection.a
    public final void c(boolean z12) {
        this.f283b.setAudioRecording(z12);
    }

    @Override // com.avito.avcalls.rtc.peer_connection.a
    public final void close() {
        this.f283b.close();
    }

    @Override // com.avito.avcalls.rtc.peer_connection.a
    @l
    public final h e(@k com.avito.avcalls.rtc.media.b bVar, @k ArrayList arrayList) {
        RtpSender rtpSenderAddTrack = this.f283b.addTrack(((zJ.c) bVar.g()).f444014a, arrayList);
        if (rtpSenderAddTrack != null) {
            return new j(rtpSenderAddTrack);
        }
        return null;
    }

    public final boolean equals(@l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof c)) {
            return false;
        }
        c cVar = (c) obj;
        return L.f(this.f282a, cVar.f282a) && L.f(this.f283b, cVar.f283b);
    }

    @Override // com.avito.avcalls.rtc.peer_connection.a
    public final void f(@k com.avito.avcalls.rtc.connecting.c cVar, @k Sdp sdp) {
        g.f440678a.getClass();
        f fVar = new f(cVar);
        vJ.e.f440675a.getClass();
        this.f283b.setLocalDescription(fVar, vJ.e.a(sdp));
    }

    @Override // com.avito.avcalls.rtc.peer_connection.a
    @k
    /* renamed from: getId, reason: from getter */
    public final String getF282a() {
        return this.f282a;
    }

    public final int hashCode() {
        return this.f283b.hashCode() + (this.f282a.hashCode() * 31);
    }

    @Override // com.avito.avcalls.rtc.peer_connection.a
    public final void i(@k com.avito.avcalls.rtc.connecting.b bVar) {
        vJ.d.f440674a.getClass();
        this.f283b.setConfiguration(vJ.d.a(bVar));
    }

    @Override // com.avito.avcalls.rtc.peer_connection.a
    public final void k(boolean z12) {
        this.f283b.setAudioPlayout(z12);
    }

    @Override // com.avito.avcalls.rtc.peer_connection.a
    public final void l(@k IceCandidate iceCandidate) {
        C49221a.f440670a.getClass();
        String str = iceCandidate.f333060a;
        if (str == null) {
            str = "";
        }
        this.f283b.addIceCandidate(new org.webrtc.IceCandidate(str, iceCandidate.f333061b, iceCandidate.f333062c));
    }

    @Override // com.avito.avcalls.rtc.peer_connection.a
    public final void n(@k com.avito.avcalls.rtc.connecting.c cVar, @k e eVar) {
        g.f440678a.getClass();
        this.f283b.createOffer(new f(cVar), ((zJ.f) eVar).f444016a);
    }

    @Override // com.avito.avcalls.rtc.peer_connection.a
    @l
    public final com.avito.avcalls.rtc.media.d q(@k com.avito.avcalls.rtc.media.b bVar) {
        Object next;
        DtmfSender dtmfSenderDtmf;
        Iterator<T> it = this.f283b.getSenders().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            MediaStreamTrack mediaStreamTrackTrack = ((RtpSender) next).track();
            if (L.f(mediaStreamTrackTrack != null ? mediaStreamTrackTrack.id() : null, bVar.getId())) {
                break;
            }
        }
        RtpSender rtpSender = (RtpSender) next;
        if (rtpSender == null || (dtmfSenderDtmf = rtpSender.dtmf()) == null) {
            return null;
        }
        return new zJ.e(dtmfSenderDtmf);
    }

    @Override // com.avito.avcalls.rtc.peer_connection.a
    public final void s(@k com.avito.avcalls.rtc.connecting.c cVar, @k Sdp sdp) {
        g.f440678a.getClass();
        f fVar = new f(cVar);
        vJ.e.f440675a.getClass();
        this.f283b.setRemoteDescription(fVar, vJ.e.a(sdp));
    }

    @k
    public final String toString() {
        return "PeerConnectionImpl(id=" + this.f282a + ", rtcPeerConnection=" + this.f283b + ')';
    }

    @Override // com.avito.avcalls.rtc.peer_connection.a
    public final void u(@k Y41.l<? super com.avito.avcalls.stats.a, G0> lVar) {
        this.f283b.getStats(new b(0, lVar));
    }

    @Override // com.avito.avcalls.rtc.peer_connection.a
    @l
    public final com.avito.avcalls.rtc.media.l w() {
        Object next;
        RtpReceiver receiver;
        Iterator<T> it = this.f283b.getTransceivers().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (((RtpTransceiver) next).getMediaType() == MediaStreamTrack.MediaType.MEDIA_TYPE_VIDEO) {
                break;
            }
        }
        RtpTransceiver rtpTransceiver = (RtpTransceiver) next;
        MediaStreamTrack mediaStreamTrackTrack = (rtpTransceiver == null || (receiver = rtpTransceiver.getReceiver()) == null) ? null : receiver.track();
        VideoTrack videoTrack = mediaStreamTrackTrack instanceof VideoTrack ? (VideoTrack) mediaStreamTrackTrack : null;
        if (videoTrack == null) {
            return null;
        }
        return new m(videoTrack);
    }
}
