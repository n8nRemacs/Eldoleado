package androidx.camera.video;

import androidx.camera.video.O;
import androidx.core.util.InterfaceC22791e;
import java.util.concurrent.Executor;

/* compiled from: AutoValue_Recorder_RecordingRecord.java */
/* renamed from: androidx.camera.video.m, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20176m extends O.i {

    /* renamed from: h, reason: collision with root package name */
    public final C20183u f25310h;

    /* renamed from: i, reason: collision with root package name */
    public final Executor f25311i;

    /* renamed from: j, reason: collision with root package name */
    public final androidx.camera.core.internal.d f25312j;

    /* renamed from: k, reason: collision with root package name */
    public final boolean f25313k;

    /* renamed from: l, reason: collision with root package name */
    public final long f25314l;

    public C20176m(C20183u c20183u, @j.P Executor executor, @j.P androidx.camera.core.internal.d dVar, boolean z12, long j12) {
        if (c20183u == null) {
            throw new NullPointerException("Null getOutputOptions");
        }
        this.f25310h = c20183u;
        this.f25311i = executor;
        this.f25312j = dVar;
        this.f25313k = z12;
        this.f25314l = j12;
    }

    @Override // androidx.camera.video.O.i
    @j.P
    public final Executor c() {
        return this.f25311i;
    }

    @Override // androidx.camera.video.O.i
    @j.P
    public final InterfaceC22791e<m0> d() {
        return this.f25312j;
    }

    public final boolean equals(Object obj) {
        Executor executor;
        androidx.camera.core.internal.d dVar;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof O.i)) {
            return false;
        }
        O.i iVar = (O.i) obj;
        return this.f25310h.equals(iVar.f()) && ((executor = this.f25311i) != null ? executor.equals(iVar.c()) : iVar.c() == null) && ((dVar = this.f25312j) != null ? dVar.equals(iVar.d()) : iVar.d() == null) && this.f25313k == iVar.h() && !iVar.j() && this.f25314l == iVar.g();
    }

    @Override // androidx.camera.video.O.i
    @j.N
    public final AbstractC20186x f() {
        return this.f25310h;
    }

    @Override // androidx.camera.video.O.i
    public final long g() {
        return this.f25314l;
    }

    @Override // androidx.camera.video.O.i
    public final boolean h() {
        return this.f25313k;
    }

    public final int hashCode() {
        int iHashCode = (this.f25310h.f25335b.hashCode() ^ 1000003) * 1000003;
        Executor executor = this.f25311i;
        int iHashCode2 = (iHashCode ^ (executor == null ? 0 : executor.hashCode())) * 1000003;
        androidx.camera.core.internal.d dVar = this.f25312j;
        int iHashCode3 = (iHashCode2 ^ (dVar != null ? dVar.hashCode() : 0)) * 1000003;
        int i12 = this.f25313k ? 1231 : 1237;
        long j12 = this.f25314l;
        return ((((iHashCode3 ^ i12) * 1000003) ^ 1237) * 1000003) ^ ((int) (j12 ^ (j12 >>> 32)));
    }

    @Override // androidx.camera.video.O.i
    public final boolean j() {
        return false;
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("RecordingRecord{getOutputOptions=");
        sb2.append(this.f25310h);
        sb2.append(", getCallbackExecutor=");
        sb2.append(this.f25311i);
        sb2.append(", getEventListener=");
        sb2.append(this.f25312j);
        sb2.append(", hasAudioEnabled=");
        sb2.append(this.f25313k);
        sb2.append(", isPersistent=false, getRecordingId=");
        return AK.c.j(this.f25314l, "}", sb2);
    }
}
