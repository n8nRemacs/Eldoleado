package androidx.camera.video.internal.audio;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioRecordingConfiguration;
import android.os.Build;
import androidx.camera.core.C20140q0;
import androidx.camera.video.internal.audio.AudioStream;
import androidx.core.util.z;
import j.N;
import j.P;
import j.X;
import j.a0;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: AudioStreamImpl.java */
@X
/* loaded from: classes.dex */
public class l implements AudioStream {

    /* renamed from: a, reason: collision with root package name */
    public final AudioRecord f25000a;

    /* renamed from: b, reason: collision with root package name */
    public final androidx.camera.video.internal.audio.a f25001b;

    /* renamed from: c, reason: collision with root package name */
    public final AtomicBoolean f25002c = new AtomicBoolean(false);

    /* renamed from: d, reason: collision with root package name */
    public final AtomicBoolean f25003d = new AtomicBoolean(false);

    /* renamed from: e, reason: collision with root package name */
    public final AtomicReference<Boolean> f25004e = new AtomicReference<>(null);

    /* renamed from: f, reason: collision with root package name */
    public final int f25005f;

    /* renamed from: g, reason: collision with root package name */
    public final int f25006g;

    /* renamed from: h, reason: collision with root package name */
    @P
    public AudioStream.a f25007h;

    /* renamed from: i, reason: collision with root package name */
    @P
    public Executor f25008i;

    /* renamed from: j, reason: collision with root package name */
    public long f25009j;

    /* renamed from: k, reason: collision with root package name */
    @P
    public AudioManager.AudioRecordingCallback f25010k;

    /* compiled from: AudioStreamImpl.java */
    @X
    public class a extends AudioManager.AudioRecordingCallback {
        public a() {
        }

        @Override // android.media.AudioManager.AudioRecordingCallback
        public final void onRecordingConfigChanged(List<AudioRecordingConfiguration> list) {
            for (AudioRecordingConfiguration audioRecordingConfiguration : list) {
                int iA2 = androidx.camera.video.internal.compat.b.a(audioRecordingConfiguration);
                l lVar = l.this;
                if (iA2 == lVar.f25000a.getAudioSessionId()) {
                    lVar.c(androidx.camera.video.internal.compat.e.b(audioRecordingConfiguration));
                    return;
                }
            }
        }
    }

    @a0
    public l(@N androidx.camera.video.internal.audio.a aVar, @P Context context) throws AudioStream.AudioStreamException, IllegalArgumentException {
        int iF2 = aVar.f();
        int iE2 = aVar.e();
        int iB2 = aVar.b();
        if (iF2 > 0 && iE2 > 0) {
            if (AudioRecord.getMinBufferSize(iF2, iE2 == 1 ? 16 : 12, iB2) > 0) {
                this.f25001b = aVar;
                this.f25006g = aVar.d();
                int minBufferSize = AudioRecord.getMinBufferSize(aVar.f(), aVar.e() == 1 ? 16 : 12, aVar.b());
                z.g(null, minBufferSize > 0);
                int i12 = minBufferSize * 2;
                this.f25005f = i12;
                int i13 = Build.VERSION.SDK_INT;
                AudioFormat audioFormatBuild = new AudioFormat.Builder().setSampleRate(aVar.f()).setChannelMask(aVar.e() == 1 ? 16 : 12).setEncoding(aVar.b()).build();
                AudioRecord.Builder builderB = androidx.camera.video.internal.compat.a.b();
                if (i13 >= 31 && context != null) {
                    androidx.camera.video.internal.compat.f.c(builderB, context);
                }
                androidx.camera.video.internal.compat.a.d(builderB, aVar.c());
                androidx.camera.video.internal.compat.a.c(builderB, audioFormatBuild);
                androidx.camera.video.internal.compat.a.e(builderB, i12);
                AudioRecord audioRecordA = androidx.camera.video.internal.compat.a.a(builderB);
                this.f25000a = audioRecordA;
                if (audioRecordA.getState() == 1) {
                    return;
                }
                audioRecordA.release();
                throw new AudioStream.AudioStreamException("Unable to initialize AudioRecord");
            }
        }
        throw new UnsupportedOperationException(String.format("The combination of sample rate %d, channel count %d and audio format %d is not supported.", Integer.valueOf(aVar.f()), Integer.valueOf(aVar.e()), Integer.valueOf(aVar.b())));
    }

    @Override // androidx.camera.video.internal.audio.AudioStream
    public final void a(@P AudioStream.a aVar, @P Executor executor) {
        z.g("AudioStream can not be started when setCallback.", !this.f25003d.get());
        b();
        this.f25007h = aVar;
        this.f25008i = executor;
        if (Build.VERSION.SDK_INT >= 29) {
            AudioManager.AudioRecordingCallback audioRecordingCallback = this.f25010k;
            AudioRecord audioRecord = this.f25000a;
            if (audioRecordingCallback != null) {
                androidx.camera.video.internal.compat.e.d(audioRecord, audioRecordingCallback);
            }
            if (this.f25010k == null) {
                this.f25010k = new a();
            }
            androidx.camera.video.internal.compat.e.c(audioRecord, executor, this.f25010k);
        }
    }

    public final void b() {
        z.g("AudioStream has been released.", !this.f25002c.get());
    }

    public final void c(boolean z12) {
        Executor executor = this.f25008i;
        AudioStream.a aVar = this.f25007h;
        if (executor == null || aVar == null || Objects.equals(this.f25004e.getAndSet(Boolean.valueOf(z12)), Boolean.valueOf(z12))) {
            return;
        }
        executor.execute(new k(0, aVar, z12));
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x005a  */
    @Override // androidx.camera.video.internal.audio.AudioStream
    @j.N
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final androidx.camera.video.internal.audio.AudioStream.b read(@j.N java.nio.ByteBuffer r11) {
        /*
            r10 = this;
            r10.b()
            java.util.concurrent.atomic.AtomicBoolean r0 = r10.f25003d
            boolean r0 = r0.get()
            java.lang.String r1 = "AudioStream has not been started."
            androidx.core.util.z.g(r1, r0)
            android.media.AudioRecord r0 = r10.f25000a
            int r1 = r10.f25005f
            int r1 = r0.read(r11, r1)
            r2 = 0
            if (r1 <= 0) goto L6a
            r11.limit(r1)
            androidx.camera.core.impl.w0 r11 = androidx.camera.video.internal.compat.quirk.e.f25058a
            java.lang.Class<androidx.camera.video.internal.compat.quirk.b> r4 = androidx.camera.video.internal.compat.quirk.b.class
            androidx.camera.core.impl.v0 r11 = r11.b(r4)
            r4 = -1
            if (r11 == 0) goto L2a
            goto L55
        L2a:
            android.media.AudioTimestamp r11 = new android.media.AudioTimestamp
            r11.<init>()
            r6 = 0
            int r0 = androidx.camera.video.internal.compat.b.b(r0, r11, r6)
            if (r0 != 0) goto L4f
            androidx.camera.video.internal.audio.a r0 = r10.f25001b
            int r0 = r0.f()
            long r6 = r10.f25009j
            long r8 = r11.framePosition
            long r6 = r6 - r8
            long r6 = androidx.camera.video.internal.audio.m.a(r0, r6)
            long r8 = r11.nanoTime
            long r8 = r8 + r6
            int r11 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r11 >= 0) goto L4d
            goto L56
        L4d:
            r2 = r8
            goto L56
        L4f:
            r11 = 5
            java.lang.String r0 = "AudioStreamImpl"
            androidx.camera.core.C20140q0.d(r11, r0)
        L55:
            r2 = r4
        L56:
            int r11 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r11 != 0) goto L5e
            long r2 = java.lang.System.nanoTime()
        L5e:
            long r4 = r10.f25009j
            long r6 = (long) r1
            int r11 = r10.f25006g
            long r6 = androidx.camera.video.internal.audio.m.b(r11, r6)
            long r6 = r6 + r4
            r10.f25009j = r6
        L6a:
            androidx.camera.video.internal.audio.o r11 = new androidx.camera.video.internal.audio.o
            r11.<init>(r1, r2)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.video.internal.audio.l.read(java.nio.ByteBuffer):androidx.camera.video.internal.audio.AudioStream$b");
    }

    @Override // androidx.camera.video.internal.audio.AudioStream
    public final void release() {
        AudioManager.AudioRecordingCallback audioRecordingCallback;
        if (this.f25002c.getAndSet(true)) {
            return;
        }
        int i12 = Build.VERSION.SDK_INT;
        AudioRecord audioRecord = this.f25000a;
        if (i12 >= 29 && (audioRecordingCallback = this.f25010k) != null) {
            androidx.camera.video.internal.compat.e.d(audioRecord, audioRecordingCallback);
        }
        audioRecord.release();
    }

    @Override // androidx.camera.video.internal.audio.AudioStream
    public final void start() throws IllegalStateException, AudioStream.AudioStreamException {
        b();
        AtomicBoolean atomicBoolean = this.f25003d;
        if (atomicBoolean.getAndSet(true)) {
            return;
        }
        AudioRecord audioRecord = this.f25000a;
        audioRecord.startRecording();
        boolean z12 = false;
        if (audioRecord.getRecordingState() != 3) {
            atomicBoolean.set(false);
            throw new AudioStream.AudioStreamException("Unable to start AudioRecord with state: " + audioRecord.getRecordingState());
        }
        this.f25009j = 0L;
        this.f25004e.set(null);
        if (Build.VERSION.SDK_INT >= 29) {
            AudioRecordingConfiguration audioRecordingConfigurationA = androidx.camera.video.internal.compat.e.a(audioRecord);
            z12 = audioRecordingConfigurationA != null && androidx.camera.video.internal.compat.e.b(audioRecordingConfigurationA);
        }
        c(z12);
    }

    @Override // androidx.camera.video.internal.audio.AudioStream
    public final void stop() throws IllegalStateException {
        b();
        if (this.f25003d.getAndSet(false)) {
            AudioRecord audioRecord = this.f25000a;
            audioRecord.stop();
            if (audioRecord.getRecordingState() != 1) {
                audioRecord.getRecordingState();
                C20140q0.d(5, "AudioStreamImpl");
            }
        }
    }
}
