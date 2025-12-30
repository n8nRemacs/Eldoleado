package androidx.camera.video.internal.workaround;

import androidx.camera.video.internal.compat.quirk.l;
import j.X;

/* compiled from: EncoderFinder.java */
@X
/* loaded from: classes.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    public final boolean f25269a;

    public b() {
        this.f25269a = ((l) androidx.camera.video.internal.compat.quirk.e.f25058a.b(l.class)) != null;
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x008c A[PHI: r10
  0x008c: PHI (r10v2 java.lang.Integer) = (r10v1 java.lang.Integer), (r10v7 java.lang.Integer) binds: [B:40:0x009e, B:35:0x008a] A[DONT_GENERATE, DONT_INLINE]] */
    @j.P
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String b(@j.N android.media.MediaFormat r13, @j.N android.media.MediaCodecInfo[] r14) throws java.lang.Throwable {
        /*
            java.lang.String r0 = "bitrate"
            java.lang.String r1 = "mime"
            java.lang.String r1 = r13.getString(r1)
            r2 = 5
            java.lang.String r3 = "EncoderFinder"
            r4 = 0
            if (r1 != 0) goto L12
            androidx.camera.core.C20140q0.d(r2, r3)
            return r4
        L12:
            int r5 = r14.length
            r6 = 0
            r7 = r6
        L15:
            if (r7 >= r5) goto La5
            r8 = r14[r7]
            boolean r9 = r8.isEncoder()
            if (r9 != 0) goto L21
            goto La1
        L21:
            android.media.MediaCodecInfo$CodecCapabilities r9 = r8.getCapabilitiesForType(r1)     // Catch: java.lang.Throwable -> L60 java.lang.IllegalArgumentException -> L62
            r10 = 1
            if (r9 == 0) goto L2a
            r11 = r10
            goto L2b
        L2a:
            r11 = r6
        L2b:
            java.lang.String r12 = "MIME type is not supported"
            androidx.core.util.z.a(r12, r11)     // Catch: java.lang.Throwable -> L60 java.lang.IllegalArgumentException -> L62
            boolean r11 = r13.containsKey(r0)     // Catch: java.lang.Throwable -> L60 java.lang.IllegalArgumentException -> L62
            if (r11 == 0) goto L64
            android.media.MediaCodecInfo$VideoCapabilities r11 = r9.getVideoCapabilities()     // Catch: java.lang.Throwable -> L60 java.lang.IllegalArgumentException -> L62
            if (r11 == 0) goto L3d
            goto L3e
        L3d:
            r10 = r6
        L3e:
            java.lang.String r12 = "Not video codec"
            androidx.core.util.z.a(r12, r10)     // Catch: java.lang.Throwable -> L60 java.lang.IllegalArgumentException -> L62
            int r10 = r13.getInteger(r0)     // Catch: java.lang.Throwable -> L60 java.lang.IllegalArgumentException -> L62
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch: java.lang.Throwable -> L60 java.lang.IllegalArgumentException -> L62
            android.util.Range r11 = r11.getBitrateRange()     // Catch: java.lang.Throwable -> L5d java.lang.IllegalArgumentException -> L9e
            java.lang.Comparable r11 = r11.clamp(r10)     // Catch: java.lang.Throwable -> L5d java.lang.IllegalArgumentException -> L9e
            java.lang.Integer r11 = (java.lang.Integer) r11     // Catch: java.lang.Throwable -> L5d java.lang.IllegalArgumentException -> L9e
            int r11 = r11.intValue()     // Catch: java.lang.Throwable -> L5d java.lang.IllegalArgumentException -> L9e
            r13.setInteger(r0, r11)     // Catch: java.lang.Throwable -> L5d java.lang.IllegalArgumentException -> L9e
            goto L66
        L5d:
            r14 = move-exception
            r4 = r10
            goto L94
        L60:
            r14 = move-exception
            goto L94
        L62:
            r10 = r4
            goto L9e
        L64:
            r11 = -1
            r10 = r4
        L66:
            boolean r9 = r9.isFormatSupported(r13)     // Catch: java.lang.Throwable -> L5d java.lang.IllegalArgumentException -> L9e
            if (r9 == 0) goto L8a
            java.lang.String r9 = "No encoder found that supports requested bitrate. Adjusting bitrate to nearest supported bitrate [requested: %dbps, nearest: %dbps]"
            java.lang.Integer r11 = java.lang.Integer.valueOf(r11)     // Catch: java.lang.Throwable -> L5d java.lang.IllegalArgumentException -> L9e
            java.lang.Object[] r11 = new java.lang.Object[]{r10, r11}     // Catch: java.lang.Throwable -> L5d java.lang.IllegalArgumentException -> L9e
            java.lang.String.format(r9, r11)     // Catch: java.lang.Throwable -> L5d java.lang.IllegalArgumentException -> L9e
            androidx.camera.core.C20140q0.d(r2, r3)     // Catch: java.lang.Throwable -> L5d java.lang.IllegalArgumentException -> L9e
            java.lang.String r14 = r8.getName()     // Catch: java.lang.Throwable -> L5d java.lang.IllegalArgumentException -> L9e
            if (r10 == 0) goto L89
            int r1 = r10.intValue()
            r13.setInteger(r0, r1)
        L89:
            return r14
        L8a:
            if (r10 == 0) goto La1
        L8c:
            int r8 = r10.intValue()
            r13.setInteger(r0, r8)
            goto La1
        L94:
            if (r4 == 0) goto L9d
            int r1 = r4.intValue()
            r13.setInteger(r0, r1)
        L9d:
            throw r14
        L9e:
            if (r10 == 0) goto La1
            goto L8c
        La1:
            int r7 = r7 + 1
            goto L15
        La5:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.video.internal.workaround.b.b(android.media.MediaFormat, android.media.MediaCodecInfo[]):java.lang.String");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0220  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0142  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x018c  */
    @j.N
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final android.media.MediaCodec a(@j.N android.media.MediaFormat r18) {
        /*
            Method dump skipped, instructions count: 606
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.video.internal.workaround.b.a(android.media.MediaFormat):android.media.MediaCodec");
    }
}
